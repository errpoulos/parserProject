package src;

import java.util.List;

import static src.TokenType.*;

public class TinyParser {


    private static class ParseError extends RuntimeException {
    }

    private TinyScanner tinyScanner;
    private int current = 0;


    public TinyParser(TinyScanner tinyScanner) {
        this.tinyScanner = tinyScanner();

    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    public void program() throws ParseError {
        if (match(BEGIN, ID, READ, WRITE, EOF, IF, WHILE)) {
            stmt_list();
            match(EOF);
        } else {

        }
    }

    private void stmt_list() throws ParseError {
        if (match(ID, READ, WRITE, IF, WHILE)) {
            stmt();
            stmt_list();
        } else if (match(EOF)) {
            match(EOF);
        }
//        } else {}

    }

    private void stmt() throws ParseError {
        if (match(ID, NUMBER)) {
            match(ASSIGN);
            match(EXPRESSION);
        } else if (match(READ)) {
            match(READ);
            match(ID, NUMBER);
        } else if (match(WRITE)) {
            match(WRITE);
            expr();
        } else {
        }
    }

    private void expr() throws ParseError {
        if (match(ID, NUMBER, LPAREN)) {
            term();
            term_tail();
        } else {
            {
            }
        }
    }

    private void term_tail() {
        if (match(PLUS, MINUS)) {
            addOp();
            term();
            term_tail();
        } else if (match(ID, READ, WRITE, EOF)) {
            match(EOF);
        }
    }


    private void term() throws ParseError {
        if (match(ID, NUMBER, LPAREN)) {
            factor();
            factor_tail();
        } else {

        }
    }


    private void addOp() {
        if (match(PLUS)) {
            match(PLUS);
        } else if (match(MINUS)) {
            match((MINUS));
        }
    }

    private void multOp() {
        if (match(ASTERISK)) {
            match(ASTERISK);
        } else if (match(SLASH)) {
            match(SLASH);
        } else {
        }
    }


    private void factor_tail() throws ParseError {
        if (match(ASTERISK, SLASH)) {
            multOp();
            factor();
            factor_tail();
        } else if (match(PLUS, MINUS, RPAPREN, ID, NUMBER, READ, WRITE, EOF)) {
            match(EOF);
        } else {
        }
    }


    private void factor() throws ParseError {
        if (match(ID)) {
            match(ID);
        } else if (match(NUMBER)) {
            match(NUMBER);
        } else if (match(LPAREN)) {
            match(LPAREN);
            expr();
            match(RPAPREN);
        } else {
        }
    }

    private boolean check(TokenType tokenType) {
        if (isAtEnd()) return false;
        return peek().type == tokenType;
    }


    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return prev();
    }

    private Token prev() {
        return tokens.get(current - 1);
    }


}



