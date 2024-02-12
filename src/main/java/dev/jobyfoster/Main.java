package dev.jobyfoster;

public class Main {
    public static void main(String[] args) {

        Notemaker notes = new Notemaker();
        notes.db.establishDBConnection();
        notes.db.executeUpdate();
//        notes.createUser("joby", "password");
    }
}