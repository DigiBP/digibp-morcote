package ch.fhnw.digibp;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class InitVariables implements JavaDelegate {


    private DelegateExecution execution;
    private static int userID;
    private static int scID;
    private static int cumulatedPrice=0;

    public void execute(DelegateExecution execution) throws Exception {

        this.execution = execution;

        String firsty =execution.getVariable("FirstName").toString();
        String lasty = execution.getVariable("LastName").toString();
        String maily = execution.getVariable("Mail").toString();
        String ordery = execution.getVariable("order").toString();
        String posty = execution.getVariable("PostCode").toString();
        String streety = execution.getVariable("Street").toString();
        String cityy = execution.getVariable("City").toString();
        String sNumbery = execution.getVariable("StreetNumber").toString();


        addUser(firsty,lasty,maily, streety, Integer.parseInt(sNumbery), posty, cityy);
        addShoppingCart();
        orderCreation(ordery);

        updateShoppingCart();




    }

    public static void addUser(String fName, String lName, String eMail, String street, int housenumber, String postcode, String city){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-70-127.eu-west-1.compute.amazonaws.com:5432/dd786fen583a5s?ssl=true\n", "mtqjbarwkwzsld", "2b51f440930a3711f9cabf9884d82feda3b70e9b35c9e9fb2d5f6f989fef445a"))
        {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select c_id   from customer ORDER BY c_id desc");

            if (resultSet.next()){
                setUserID(resultSet.getInt("c_id")+1);
            }
            else{
                setUserID(0);
            }

            System.out.println(getUserID());

            if (getUserID() > 0) {
                String query = "INSERT INTO customer (\"c_id\", \"c_firstname\", \"c_lastname\", \"c_email\", \"c_street\", \"c_housenumber\", \"c_postcode\", \"c_city\") VALUES ('"+getUserID()+"', '"+fName+"', '"+lName+"', '"+eMail+"', '"+street+"', '"+housenumber+"', '"+postcode+"', '"+city+"')";
                System.out.println(query);
                Statement pst = connection.createStatement();
                pst.execute(query);
                System.out.println("ID: "+getUserID());
            }

        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
    public static void addShoppingCart(){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-70-127.eu-west-1.compute.amazonaws.com:5432/dd786fen583a5s?ssl=true\n", "mtqjbarwkwzsld", "2b51f440930a3711f9cabf9884d82feda3b70e9b35c9e9fb2d5f6f989fef445a"))
        {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select sc_id   from shoppingcart ORDER BY sc_id desc");


            if (rs.next()){
                setscID(rs.getInt("sc_id")+1);
            }
            else{
                setscID(0);
            }


            if (getUserID() > 0) {
                String query = "INSERT INTO shoppingcart (\"sc_id\", \"fk_customer_id\", \"sc_cumulatedprice_swissrappen\") VALUES ('"+getscID()+"', '"+getUserID()+"', '"+0+"')";
                System.out.println(query);
                Statement pst = connection.createStatement();
                pst.execute(query);
                System.out.println("ID: "+getscID());
            }

        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

    }
    private static void orderCreation(String oC){

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-70-127.eu-west-1.compute.amazonaws.com:5432/dd786fen583a5s?ssl=true\n", "mtqjbarwkwzsld", "2b51f440930a3711f9cabf9884d82feda3b70e9b35c9e9fb2d5f6f989fef445a"))
        {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select o_id   from orders ORDER BY o_id desc");

            int currentOrderID;

            if (rs.next()){
                currentOrderID = rs.getInt("o_id")+1;
            }
            else{
                currentOrderID = 0;
            }

            Map<String, String> cleanList = StringSplitter(oC);

            for(Map.Entry<String, String> entry: cleanList.entrySet()) {
                String query = "INSERT INTO orders (\"o_id\", \"o_amountofitems\", \"fk_item_id\", \"fk_customer_id\",\"fk_shoppingcarts_id\") VALUES ('"+currentOrderID+"','"+entry.getValue()+"','"+entry.getKey()+"','"+getUserID()+"', '"+getscID()+"')";
                System.out.println(query);
                Statement pst = connection.createStatement();
                pst.execute(query);

                Statement stmt = connection.createStatement();
                ResultSet rset = stmt.executeQuery("select i_price_swissrappen from item where i_id="+entry.getKey()+"");

                if(rset.next()){
                    cumulatedPrice = cumulatedPrice+(rset.getInt("i_price_swissrappen")*Integer.parseInt(entry.getValue()));

                }

                currentOrderID++;
            }



        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }




    }

    private static void updateShoppingCart(){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-70-127.eu-west-1.compute.amazonaws.com:5432/dd786fen583a5s?ssl=true\n", "mtqjbarwkwzsld", "2b51f440930a3711f9cabf9884d82feda3b70e9b35c9e9fb2d5f6f989fef445a"))
        {

            String query = "UPDATE shoppingcart Set sc_cumulatedprice_swissrappen="+cumulatedPrice+" where sc_id="+getscID()+"";
            Statement upst = connection.createStatement();
            upst.execute(query);


        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }


    }

    private static Map<String, String> StringSplitter(String orderChaos) {

        Map<String, String> ordersList = new HashMap<>();

        String[] parts = orderChaos.split(";");

        for (int i = 0; i < parts.length; i++) {

            String[] order = parts[i].split("\\.");

            ordersList.put(order[0], order[1]);
        }

        return ordersList;
    }



    public String cumulatedAsString(){

        Integer cp=cumulatedPrice;
        return cp.toString();

    }

    public static int getUserID(){
        return userID;
    }

    public static void setUserID(int newUserID){
        userID = newUserID;
    }

    public static int getscID(){
        return scID;
    }

    public static void setscID(int newscID){
        scID = newscID;
    }

}

