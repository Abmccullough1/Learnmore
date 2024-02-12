package dev.jobyfoster;

import java.sql.*;

public class DatabaseManager {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    public Connection db = DriverManager.getConnection(DB_URL, "postgres", "password");

    public void establishDBConnection() {
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Users");

            while (rs.next()) {
                String id = rs.getString(1);
                String username = rs.getString(2);
                String password = rs.getString(3);

                System.out.printf("ID: %s: User: %s Pass: %s", id, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void createUser(String username, String password) {
        Statement st = null;
        try {
            st = this.db.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            st.executeUpdate("insert into Users (username, password) values ('joby', 'password')");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.printf("You have successfully created user %s!", username);
    }
}
