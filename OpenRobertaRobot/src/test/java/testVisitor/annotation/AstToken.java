package testVisitor.annotation;

import de.fhg.iais.roberta.syntax.lang.expr.Expr;
import de.fhg.iais.roberta.syntax.lang.stmt.Stmt;

public class AstToken<V> {
    @AstComponent(fieldName="QAY")
    Expr<V> expr;

    @AstComponent(fieldName="WSX")
    Stmt<V> stmt;

    @AstComponent()
    Stmt<V> stmt2;

    Stmt<V> stmtNoAnno;

    public void print() {
        System.out.println("expr: " + expr);
        System.out.println("stmt: " + stmt);
    }

    @Override
    public String toString() {
        return "AstToken{" +
                "expr=" + expr +
                ", stmt=" + stmt +
                ", stmt2=" + stmt2 +
                ", stmtNoAnno=" + stmtNoAnno +
                '}';
    }
}
