package src;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TinyScanner tinyScanner =  new TinyScanner(new FileReader(new File("testFiles/testLeximes.txt")));
        tinyScanner.nextToken();
    }
}

