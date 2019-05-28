package ch.fhnw.digibp;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;


public class InitVariables implements JavaDelegate {


    private DelegateExecution execution;
    private static int userID;

    public void execute(DelegateExecution execution) throws Exception {

        this.execution = execution;

        //execution.setVariable("FirstName", "OliWasHere");
        addUser("Loris", "Yannick","loris@yanniick.com", "FHNW-weg","25", 4600, "olten" );

    }

    public static void addUser(String fName, String lName, String eMail, String street, String housenumber, int postcode, String city){
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

    public static int getUserID(){
        return userID;
    }

    public static void setUserID(int newUserID){
        userID = newUserID;
    }

}

