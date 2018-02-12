package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TinyScanner {

    /**
     * Gets characters from input, matches them against regex patterns, returns the next token.
     * @throws IOException
     */
    public void nextToken() throws IOException {

        Tokenizer tokenizer = new Tokenizer();
        //Logic ids
        tokenizer.add("read|write|begin|end|if|then|else|endif|while|endwh", 1);

        //Left Paren
        tokenizer.add("\\(", 2);

        //Right Paren
        tokenizer.add("\\)", 3);

        //Math Operators
        tokenizer.add("\\+|-", 4);
        tokenizer.add("\\*|/", 5);

        //Assignment and Semicolon
        tokenizer.add(":=", 6);
        tokenizer.add(";", 7);
        tokenizer.add("[0-9]+", 8);
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 8);

        //Regex //A matches beginning of input
        String input = new Scanner(new File("testFiles/testLeximes.txt")).useDelimiter("\\A").next();


        System.out.println("Input: "+ input);
        try {
            tokenizer.tokenize(input);


        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }

    }

}



