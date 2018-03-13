package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class TestFakeLanguage {

    static boolean hasError = false;

    public static void main(String[] args) throws IOException {

        if (args.length > 1) {
            System.out.println("Usage: TestFakeLanguage [script]");
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hasError) System.exit(65);

    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (; ; ) {
            System.out.println(">");
            run(reader.readLine());
            hasError = false;
        }
    }

    private static void run(String src) throws IOException {
        TinyScanner ts = new TinyScanner(src);
        List<Token> tokens = ts.nextToken();
        // print the tokens.


//        TinyParser tp = new TinyParser(tinyScanner, tokens);
//        tp.program();
//        System.out.println(tokens);
    }



}

