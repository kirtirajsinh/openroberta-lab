package testVisitor.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AstComponent {
    boolean required() default true;

    String fieldName() default "";
}
