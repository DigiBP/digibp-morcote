package ch.fhnw.digibp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnection {

    public static void main(String[] args) {



        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-70-127.eu-west-1.compute.amazonaws.com:5432/dd786fen583a5s?ssl=true\n", "mtqjbarwkwzsld", "2b51f440930a3711f9cabf9884d82feda3b70e9b35c9e9fb2d5f6f989fef445a"))
        {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select c_id   from customer ORDER BY c_id desc");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("c_id"));
            }

        }catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

        StringSplitter("1012.4;1013.1;1012.2;1012.1;");
    }


    private static void StringSplitter(String OrderChaos){
        String[] getAllOrders = new String[15];
        String firstPart = OrderChaos.split(";")[0];
        System.out.println(firstPart.toString());

    }
}