package dev.jobyfoster;
import java.util.Scanner;
import static dev.jobyfoster.BasicFunctions.*;

public class Main {
    public static void main(String[] args) {
        Notemaker notes = new Notemaker();
        notes.db.establishDBConnection();
        LearnMore();
//        notes.db.executeUpdate();
//        notes.createUser("joby", "password");
    }
}