package src;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TinyScanner {

    private class TokenDetails {
        final Pattern regex;
        final int token;

        /**
         * Information about Tokens
         * @param regex The pattern used to match input string for a token.
         * @param token ID number given to each token group.
         */
        TokenDetails(Pattern regex, int token) {
            super();
            this.regex = regex;
            this.token = token;
        }
    }

    public class Token {
        public final int token;
        public final String sequence;

        Token(int token, String sequence) {
            super();
            this.token = token;
            this.sequence = sequence;
        }
    }

    // Linked list of details about each input token
    private LinkedList<TokenDetails> tokenDetails;
    private LinkedList<Token> tokens;

    TinyScanner() {
        tokenDetails = new LinkedList<>();
        tokens = new LinkedList<>();
    }

    /**
     * Adds a regex pattern to validate a token from the input.
     * @param regex The pattern to be matched.
     * @param token The pattern's ID number.
     */
    public void add(String regex, int token) {
        tokenDetails.add(new TokenDetails(Pattern.compile("^(" + regex + ")"), token));
    }

    /**
     * Tokenizes a String.
     * @param str String to be tokenized.
     */
    public void nextToken(String str) {
        String s = str.trim();
        tokens.clear();

        while (!s.equals("")) {
            boolean match = false;
            for (TokenDetails details : tokenDetails) {
                Matcher m = details.regex.matcher(s);
                if (m.find()) {
                    match = true;
                    String tok = m.group().trim();
                    tokens.add(new Token(details.token, tok));
                    s = m.replaceFirst("").trim();
                    tokens.add(new Token(details.token, tok));
                    break;
                }
            }
            if (!match)
                throw new RuntimeException("Invalid Character(s) found:\n" + s.charAt(0) + "\nremove from input before running again");
        }
    }
    public LinkedList<Token> getTokens() {
        return tokens;
    }
}




