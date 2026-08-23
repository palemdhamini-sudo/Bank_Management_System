package bankmanagement;

import java.sql.Connection;

import bankmanagement.database.DatabaseConnection;

public class TestConnection {

    public static void main(String[] args) {

        Connection connection =
                DatabaseConnection.getConnection();

        if (connection != null) {

            System.out.println(
                    "Database connected successfully!"
            );

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println(
                    "Could not connect to database."
            );
        }
    }
}