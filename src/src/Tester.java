package src;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Tester {


    public static void main(String[] args) throws IOException {

        String input = new Scanner(new File(args[0])).useDelimiter("\\A").next();
        TinyScanner ts = new TinyScanner(input);
        List<Token> tokens = ts.nextToken();
        System.out.println(tokens);
        TinyParser tp = new TinyParser(ts, tokens);
        tp.parse();


    }


}





