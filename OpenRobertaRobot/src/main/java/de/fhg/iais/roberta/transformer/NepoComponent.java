package de.fhg.iais.roberta.transformer;

import java.lang.annotation.*;

import de.fhg.iais.roberta.typecheck.BlocklyType;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface NepoComponent {
    boolean required() default true;

    String fieldName() default "";

    BlocklyType fieldType() default BlocklyType.NOTHING;
}
