package dev.jobyfoster;
import java.util.Scanner;
import static dev.jobyfoster.BasicFunctions.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Notemaker notes = new Notemaker();
        notes.db.viewAllUsers();
    }
}