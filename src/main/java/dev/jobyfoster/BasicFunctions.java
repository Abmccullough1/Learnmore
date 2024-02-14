package dev.jobyfoster;

import java.util.Scanner;

public class BasicFunctions {


    static void print(String text) {
        System.out.println(text);
    }

    static String input() {
        Scanner i = new Scanner(System.in);
        return i.next();
    }

    public static String prompt(String question){
        System.out.print(question);
        return input();
    }


}
