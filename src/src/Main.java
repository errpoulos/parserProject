package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        TinyScanner tinyScanner = new TinyScanner();
        tinyScanner.add("read|write|begin|end(wh|if)|then|if|else|while", 1);
        //Left Paren
        tinyScanner.add("\\(", 2);
        //Right Paren
        tinyScanner.add("\\)", 3);
        //Math Operators
        tinyScanner.add("\\+|-", 4);
        tinyScanner.add("\\*|/", 5);
        //Assignment and Semicolon
        tinyScanner.add(":=", 6);
        tinyScanner.add(";", 7);
        tinyScanner.add("[0-9]+", 8);
        tinyScanner.add("[\\w]+", 8);

        String input = new Scanner(new File(args[0])).useDelimiter("\\A").next();
        System.out.println("Input: " + input);
        tinyScanner.nextToken(input);
        try {
            int i = 0;
            for (TinyScanner.Token tok : tinyScanner.getTokens()) {
                i++;
                if(i%2==0) {
                    System.out.println("Token ID:" + tok.token + " Token Literal:" + "(" + tok.sequence + ")");
                }else{
                    System.out.print("");}
            }
        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }
    }
}

