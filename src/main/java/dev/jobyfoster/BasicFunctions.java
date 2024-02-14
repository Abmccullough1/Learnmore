package dev.jobyfoster;

import java.util.Scanner;
public class BasicFunctions {
static Notemaker n = new Notemaker();

    static void print(String text) {
        System.out.println(text);
    }

    static String input() {
        Scanner i = new Scanner(System.in);
        return i.nextLine();
    }

    static void welcome(){
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
    }
    static void LearnMore(){
        String Directive = "";
        welcome();
        while (!Directive.equals("log out")) {
            print("What would you like to do?");
            print("< Study >, < Share >, < browse >, < Log Out >");
            Directive = input().toLowerCase();
            switch (Directive) {
                case "study":
                    print("What are you studying? ");
                    String Study = input();
                    n.getLearningSheet(Study);
                    break;


                case "share":
                    print("test share");

                    // Actual code goes here

                    break;


                case "browse":
                    print("test browse");

                    // Actual code goes here

                    break;


                case "log out":
                    print("Logging Out...");
                    break;


                default:
                    print("Invalid Action");
            }
        }
    }


}
