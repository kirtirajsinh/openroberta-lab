package testVisitor.annotation;

import de.fhg.iais.roberta.syntax.lang.expr.EmptyExpr;
import de.fhg.iais.roberta.syntax.lang.expr.Expr;
import de.fhg.iais.roberta.typecheck.BlocklyType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CreateAstToken {
    public static void main(String[] args) throws Exception {
        new CreateAstToken().run();
    }

    private void run() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = AstToken.class;
        AstToken tk = (AstToken) clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            AstComponent anno = field.getAnnotation(AstComponent.class);
            if (anno != null) {
                p(field.getName() + " has @AstComponent");
                p("  fieldName: " + anno.fieldName());
                p("  type: " + field.getType());
                EmptyExpr<Object> ee = EmptyExpr.make(BlocklyType.STRING);
            } else {
                p(field.getName() + " has NO @AstComponent");
            }
        }
        p("AstToken was: " + tk);
        tk.expr = EmptyExpr.make(BlocklyType.STRING);
        p("AstToken is now: " + tk);
    }

    private void p(Object o) {
        System.out.println(o.toString());
    }
}
