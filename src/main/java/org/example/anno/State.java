package org.example.anno;

import org.example.validation.StateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {StateValidation.class}
)
public @interface State {
    String message() default "state参数的值只可以是已发布或者是草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
