package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.TokenType.*;

class TinyScanner {
    private final String input;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    /**
     * constructor
     * @param input String of leximes to be scanned.
     */
    TinyScanner(String input) {
        this.input = input;
    }

    /**
     *
     * @return A list of tokens and their assosciated types.
     * @throws IOException if
     */
    List<Token> nextToken() throws IOException {
        while (!isAtEnd()) {
            start = current;
            scanToken();

        }

        tokens.add(new Token(EOF, "", line));

        return tokens;

    }


    /**
     * @return True if current index is greater or equal than length of input String.
     */
    private boolean isAtEnd() {
        return current >= input.length();
    }


    /**
     * Adds Leximes to list if tokens according to their category in TokenTypes.
     *
     * @throws IOException If character not in language is identified.
     */
    private void scanToken() throws IOException {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LPAREN);
                break;
            case ')':
                addToken(RPAPREN);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(ASTERISK);
                break;
            case '/':
                addToken(SLASH);
                break;
            case '\u0000':
                addToken(EPS);
            case ':':
                addToken(match('=') ? ASSIGN : ASSIGN);
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    throw new IOException("Unexpected character.");
                }
                break;
        }
    }

    /**
     * Increments the index of current.
     *
     * @return The character at index current - 1
     */
    private char advance() {
        current++;
        return input.charAt(current - 1);
    }

    /**
     * Adds Token type and token literal to list Tokens.
     *
     * @param type Type of token to be added.
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = input.substring(start, current);
        tokens.add(new Token(type, text, line));
    }

    /**
     * @param expected Character to be checked.
     * @return True if character at current index matches expected.
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (input.charAt(current) != expected) return false;
        current++;
        return true;
    }


    /**
     * @return character at current index.
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return input.charAt(current);
    }

    /**
     * @param c Character to be checked
     * @return C if it is a number between 0 and 9.
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * @param c Character to be checked
     * @return c if it is an uppercase or lowercase letter.
     */
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z');
    }

    /**
     *
     * @param c Character to be checked.
     * @return c if isAlpha or isDigit are true for c
     */
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    /**
     * Creates a string identifier if it does not match one of the keywords exactly and adds to tokens with type ID.
     */
    private void identifier() {
        while (isAlphaNumeric(peek())) advance();
        String text = input.substring(start, current);

        TokenType type = keywords.get(text);
        if (type == null) type = ID;
        addToken(type);

    }

    /**
     * Creates a token that is an uninterrupted String of digits and adds it to list of tokens with type NUMBER.
     */
    private void number() {
        while (isDigit(peek())) advance();
        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }
        addToken(NUMBER,
                Double.parseDouble(input.substring(start, current)));
    }

    /**
     *
     * @return 0 if next character is out of bounds, character at current index +1 otherwise.
     */
    private char peekNext() {
        if (current + 1 >= input.length()) return '\0';
        return input.charAt(current + 1);
    }

    /**
     * Hashmap of keywords, key is the string literal, value is token type of keyword.
     */
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("read", READ);
        keywords.put("write", WRITE);
        keywords.put("begin", BEGIN);
        keywords.put("end", EOF);
        keywords.put("end", END);
        keywords.put("if", IF);
        keywords.put("then", THEN);
        keywords.put("else", ELSE);
        keywords.put("endif", ENDIF);
        keywords.put("while", WHILE);
        keywords.put("endwh", ENDIF);
    }

}



