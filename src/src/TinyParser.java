package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static src.TokenType.*;

public class TinyParser {


    private TinyScanner tinyScanner;
    private List<Token> tokens;
    private List<Expr> expressions = new ArrayList<>();
    Token lookahead;
    private int current = 0;
    private int start = 0;


    public TinyParser(TinyScanner tinyScanner, List<Token> tokens) {
        this.tinyScanner = tinyScanner;
        this.tokens = tokens;
    }


    public void parse() throws IOException {
        tokens = tinyScanner.nextToken();
        program();
        System.out.println(tokens);
    }

    public void program() throws ParseException {
        while (!isAtEnd())
            if (match(BEGIN, ID, READ, WRITE, EOF, IF, WHILE)) {
                stmt_list();
                System.out.println(tokens);

            } else {
                throw new ParseException("expected: token of type: BEGIN, ID, READ, WRITE, EOF, IF, WHILE");

            }
    }

    private void stmt_list() throws ParseException {
        if (match(ID, READ, WRITE, IF, WHILE)) {
            stmt();
            stmt_list();
        } else if (match(EOF)) {
            System.out.println();
        } else {
            throw new ParseException("expected token of type: ID, READ, WRITE, IF, WHILE");
        }


    }

    private void stmt() throws ParseException {
        if (match(ID, NUMBER)) {
            match(ID);
            match(ASSIGN);
            expr();
        } else if (match(READ)) {
            match(READ);
            match(ID, NUMBER);
        } else if (match(WRITE)) {
            expr();
        } else {
            throw new ParseException("expected token of type: ID, NUM, WRITE");
        }
    }

    private void expr() throws ParseException {
        if (match(ID, NUMBER, LPAREN)) {
            term();
            term_tail();
        } else {
            {
                throw new ParseException("expected token of type: ID, NUMBER, LPAREN");
            }
        }
    }

    private void term_tail()throws ParseException {
        if (match(PLUS, MINUS)) {
            addOp();
            term();
            term_tail();
        } else if (match(ID, READ, WRITE, EOF)) {
            match(EPS);
        } else {
            throw new ParseException("expectd token of type: LUS, MINUS");
        }
    }


    private void term() throws ParseException {
        if (match(ID, NUMBER, LPAREN)) {
            factor();
            factor_tail();
        } else {
            throw new ParseException("expectd token of type: ID, NUMBER, LPAREN");
        }
    }

    private void factor_tail() throws ParseException {
        if (match(ASTERISK, SLASH)) {
            multOp();
            factor();
            factor_tail();
        } else if (match(PLUS, MINUS, RPAPREN, ID, NUMBER, READ, WRITE, EOF)) {
            match(EPS);
        } else {
            throw new ParseException("expectd token of type: ASTERISK, SLASH");
        }
    }

    private void factor() throws ParseException {
        if (match(ID)) {
            match(ID);
        } else if (match(NUMBER)) {
            match(NUMBER);
        } else if (match(RPAPREN)) {
            expr();
            match(LPAREN);
        } else {
            throw new ParseException("expectd token of type: ID");
        }

    }


    private void multOp()throws ParseException {


        if (match(SLASH, ASTERISK)) {
            match(SLASH, ASTERISK);
        } else {
            throw new ParseException("expectd token of type:  SLASH, ASTERISK");
        }


    }

    private void addOp() throws ParseException{

        if (match(MINUS, PLUS)) {
            match(PLUS, MINUS);
        } else {
            throw new ParseException("expectd token of type: PLUS, MINUS");
        }

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


    private void addExpr(ExprType type) {
        expressions = expressions.subList(start, current);
        expressions.add(new Expr() {
        });
    }

}



