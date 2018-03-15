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

    TinyScanner(String input) {
        this.input = input;
    }

    List<Token> nextToken() throws IOException {
        while (!isAtEnd()) {
            start = current;
            scanToken();

        }

        tokens.add(new Token(EOF, "", line));

        return tokens;

    }

    private boolean isAtEnd() {
        return current >= input.length();
    }

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

    private char advance() {
        current++;
        return input.charAt(current - 1);
    }


    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = input.substring(start, current);
        tokens.add(new Token(type, text, line));
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (input.charAt(current) != expected) return false;
        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return input.charAt(current);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();
        String text = input.substring(start, current);

        TokenType type = keywords.get(text);
        if (type == null) type = ID;
        addToken(type);

    }

    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER,
                Double.parseDouble(input.substring(start, current)));
    }

    private char peekNext() {
        if (current + 1 >= input.length()) return '\0';
        return input.charAt(current + 1);
    }

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("read", READ);
        keywords.put("write", WRITE);
        keywords.put("begin", BEGIN);
        keywords.put("end", END);
        keywords.put("if", IF);
        keywords.put("then", THEN);
        keywords.put("else", ELSE);
        keywords.put("endif", ENDIF);
        keywords.put("while", WHILE);
        keywords.put("endwh", ENDIF);
    }

}



