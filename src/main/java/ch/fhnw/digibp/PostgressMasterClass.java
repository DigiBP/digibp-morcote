package ch.fhnw.digibp;

import java.sql.*;

public class PostgressMasterClass {

    public static void main(String[] args) {

    addUser("Loris", "Yannick","loris@yanniick.com", "FHNW-weg","25", 4600, "olten" );


    }

    public static void addUser(String fName, String lName, String eMail, String street, String housenumber, int postcode, String city){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-70-127.eu-west-1.compute.amazonaws.com:5432/dd786fen583a5s?ssl=true\n", "mtqjbarwkwzsld", "2b51f440930a3711f9cabf9884d82feda3b70e9b35c9e9fb2d5f6f989fef445a"))
        {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select c_id   from customer ORDER BY c_id desc");

            int highestID;

            if (resultSet.next()){
                highestID = resultSet.getInt("c_id");
            }
            else{
                highestID = 0;
            }

            highestID++;
            System.out.println(highestID);

            if (highestID > 0) {
                String query = "INSERT INTO customer (\"c_id\", \"c_firstname\", \"c_lastname\", \"c_email\", \"c_street\", \"c_housenumber\", \"c_postcode\", \"c_city\") VALUES ('"+highestID+"', '"+fName+"', '"+lName+"', '"+eMail+"', '"+street+"', '"+housenumber+"', '"+postcode+"', '"+city+"')";
                System.out.println(query);
                PreparedStatement pst = connection.prepareStatement(query);
                System.out.println("ID: "+highestID);
            }

        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

    }




    private static void StringSplitter(String OrderChaos){
        String[] getAllOrders = new String[15];
        String firstPart = OrderChaos.split(";")[0];
        System.out.println(firstPart.toString());

    }
}