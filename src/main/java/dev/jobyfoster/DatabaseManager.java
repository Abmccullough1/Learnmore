package dev.jobyfoster;

import javax.xml.transform.Result;
import java.sql.*;

public class DatabaseManager {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    public Connection db;

    {
        try {
            db = DriverManager.getConnection(DB_URL, "postgres", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewAllUsers() {
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Users");

            while (rs.next()) {
                String id = rs.getString(1);
                String username = rs.getString(2);
                String password = rs.getString(3);

                System.out.printf("ID: %s: User: %s Pass: %s%n", id, username, password);
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void createUser(String newUsername, String newPassword) throws SQLException {
        Statement st = null;
        try {
            st = this.db.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        String query = String.format("INSERT INTO Users (username, password) VALUES ('%s', '%s')", newUsername, newPassword);
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.printf("You have successfully created user %s!", newUsername);
        st.close();
    }

    public boolean checkSignIn(String username, String password) {
        boolean validLogin = false;
        String query = String.format("SELECT username, password FROM users WHERE username='%s'", username);
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
//                System.out.printf("%s %s", rs.getString(1), rs.getString(2));
                String storedPassword = rs.getString(2);
                if (storedPassword.equals(password)){
                    validLogin = true;
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return validLogin;
    }

    public int getUserID(String username) {
        int userID = 0;
        String query = String.format("SELECT user_id FROM users WHERE username='%s'", username);
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                userID = rs.getInt(1);

            }

        } catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }

        return userID;
    }

    public void createUsersTable() throws SQLException {
        Statement st;
        try {
            st = db.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            st.executeUpdate("CREATE TABLE users (user_id SERIAL PRIMARY KEY, username VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        st.close();
    }
}