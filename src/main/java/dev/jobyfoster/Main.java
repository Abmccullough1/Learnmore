package dev.jobyfoster;
import java.util.Scanner;

public class Main {

    static void print(String text) {
        System.out.println(text);
    }

    static String input() {
        Scanner i = new Scanner(System.in);
        return i.nextLine();
    }
    public static void main(String[] args) {
        String Directive = "";
        print("");
        print("Welcome to LearnMore!");
        print("The new way to learn.");
        print("");
        print("Please Sign In");
        print("Username: ");
        String InputUserName = input();
        print("Password: ");
        String InputPassword = input();
//        print("Testing the inputs " + InputUserName + " " + InputPassword);


//      Add way to check users in database

        print("Welcome Back, " + InputUserName + "!");
        while (!Directive.equals("log out")) {
            print("What would you like to do?");
            print("< Study >, < Share >, < Search >, < Log Out >");
            Directive = input().toLowerCase();
        }



        Notemaker notes = new Notemaker();
        notes.db.establishDBConnection();
//        notes.db.executeUpdate();
//        notes.createUser("joby", "password");
    }
}