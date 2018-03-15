package src;

abstract class Expr {

    interface Visitor<R> {
        R visitAddorMult(AddorMult expr);

        R visitFactor(Factor expr);

        R visitFactorTail(FactorTail expr);

        R visitTerm(Term expr);

        R visitStmt(Stmt expr);

        R visitStmtList(StmtList expr);

        R visitProgram(Program expr);
    }

    static class AddorMult extends Expr {
        AddorMult(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAddorMult(this);
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }


    static class Factor extends Expr{
        Factor(Token lparen, Token ID, Token Rparen, Token lparen1, Token id, Token rparen){

            this.lparen = lparen1;
            this.id = id;
            this.rparen = rparen;
        }
        final Token lparen;
        final Token id;
        final Token rparen;


    }

    static class FactorTail extends Expr{
    }

    static class Term extends Expr{
    }

    static class Stmt extends Expr{
    }

    static class StmtList extends Expr{
    }

    static class Program extends Expr{
    }
}
