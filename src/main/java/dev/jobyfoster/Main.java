package dev.jobyfoster;
import static dev.jobyfoster.BasicFunctions.*;
import java.sql.SQLException;
import java.io.*;
import java.nio.file.*;

public class Main {

    static Notemaker notes = new Notemaker();
    static void login(){
        print("Please Sign In");
        int loginAttempts = 0;
        boolean withold = false;
        String LastAttemptName = "";
        while (true) {
            if (loginAttempts > 3) {
                print("You have failed logging in too many times!");
                System.exit(1);
            }
            System.out.printf("Attempts: %s%n", loginAttempts);

            String InputUserName = prompt("Username: ");
            String InputPassword = prompt("Password: ");
            if(notes.db.checkSignIn(InputUserName, InputPassword)) {
                notes.currentUser = notes.db.getUserID(InputUserName);
                if (notes.currentUser == 0) {
                    System.out.println("UserID not found... exiting program.");
                    System.exit(1);
                } else {
                    System.out.printf("Successfully logged in, %s!", InputUserName);
                    break;
                }
            } else {
                loginAttempts++;
                if (!withold) {
                    LastAttemptName = InputUserName;
                    withold = true;
                }
                if (!LastAttemptName.equals(InputUserName)) {
                    loginAttempts = 0;
                    withold = false;
                }

            }
        }

    }

    static void register() throws SQLException {
        print("Please Enter Your Login Credentials");
        while(true) {
            String newUserUsername = prompt("Username: ");
            String newUserPassword = prompt("Password: ");
            String newUserPasswordConfirmation = prompt("Repeat Password: ");

            if (newUserPassword.equals(newUserPasswordConfirmation)) {
                notes.db.createUser(newUserUsername, newUserPassword);
                break;
            } else {
                print("The passwords did not match!");
            }
        }
    }

    static void welcome() throws SQLException {
        boolean loggedIn = false;
        print("");
        print("Welcome to LearnMore!");
        print("The new way to learn.");
        print("");
        while (!loggedIn) {
            String choice = prompt("Would you like to login, register, or exit: ");
            if(choice.equalsIgnoreCase("login")) {
                login();
                loggedIn = true;
            } else if (choice.equalsIgnoreCase("register")) {
                register();
                login();
                loggedIn = true;
            } else if (choice.equalsIgnoreCase("exit")) {
                System.out.println("See ya next time!");
                System.exit(1);
            } else {
                print("Invalid input!");
            }
        }
    }
    static void LearnMore() throws SQLException, IOException {
        String Directive = "";
        welcome();
        while (!Directive.equalsIgnoreCase("log out")) {
            print(" What would you like to do?");
            print("Generate, Share, Browse, Log Out");
            Directive = input().toLowerCase();
            switch (Directive.toLowerCase()) {
                case "generate":
                    print("We will now take you through prompting to create your learning sheet!");
                    print("The learning sheet includes an overview, detailed explanations, interactive elements, self-assessment questions, and additional resources, all formatted in markdown for clarity and ease of use.");
                    print("What do you want to learn?");
                    String sheetTopic = prompt("> I want to learn ");

                    print("What is your current understanding of this topic? (Beginner, Intermediate, Advanced)");
                    String learningLevel = prompt("> My current understanding is ");

                    print("What is your preferred learning style? (Visual, Auditory, Reading/Writing, Kinesthetic)");
                    String learningStyle = prompt("> My preferred learning style is ");

                    print("What is your goal in learning this topic? (e.g., to apply it in a project, to gain a comprehensive understanding, to pass an exam)");
                    String interestGoal = prompt("> My goal is ");

                    String sheet = notes.getLearningSheet(sheetTopic, learningLevel, learningStyle, interestGoal);
                    print(sheet);
                    String save_to_database = prompt("Would you like to save this sheet? Yes or No. ").toLowerCase();
                    if (save_to_database.equals("yes")){
                        print("Saved to profile, check 'sheets' to find.");
                    } else if (save_to_database.equals("no")) {
                        print("File was not saved to computer.");
                    }
                    else {
                        print("Invalid Action");
                    }
                    String save_to_computer = prompt("Would you like to save this sheet to your computer? Yes or No. ").toLowerCase();
                    if (save_to_computer.equals("yes")) {
                        String username = System.getProperty("user.name");
                        if (System.getProperty("os.name").equals("Mac OS X")) {
                            String FileName = prompt("What do you want to name the file? ");
                            String path = "/Users/"+username+"/Documents/"+FileName+".MD";
                            File MD = new File(path);
                            if (MD.createNewFile()) {
                                print("File created in documents.");
                            }
                            Files.writeString(Path.of(path), sheet);

                        } else if (System.getProperty("os.name").equals("Windows 11")) {
                            String FileName = prompt("What do you want to name the file? ");
                            String path = "C:\\Users\\"+username+"\\Documents\\"+FileName+".MD";
                            File MD = new File(path);
                            if (MD.createNewFile()) {
                                print("File created in documents.");
                            }
                            Files.writeString(Path.of(path), sheet);
                        }

                    }
                    else if (save_to_computer.equals("no")) {
                        print("File was not saved to computer.");
                    }
                    else {
                        print("Invalid Action");
                    }
                    break;


                case "sheets":
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
    public static void main(String[] args) throws SQLException, IOException {
        LearnMore();
    }
}