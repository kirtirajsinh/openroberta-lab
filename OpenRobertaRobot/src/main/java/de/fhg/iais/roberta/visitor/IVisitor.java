package de.fhg.iais.roberta.visitor;

import de.fhg.iais.roberta.util.dbc.DbcException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Interface to be used with the visitor pattern to traverse an AST (and generate code, e.g.).
 */
public interface IVisitor<V> {
    default V visit(Object visitable) {
        String className = visitable.getClass().getSimpleName();
        String methodName = String.format("visit%s", className);
        try {
            Method m = getClass().getMethod(methodName, visitable.getClass());
            @SuppressWarnings("unchecked")
            V result = (V) m.invoke(this, new Object[]{visitable});
            return result;
        } catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
            throw new DbcException(String.format("visit Method \"%s\" not found on \"%s\" (Block \"%s\" not supported)", methodName, this.getClass().getSimpleName(), className), e);
        }
    }

}
