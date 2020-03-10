// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import de.egladil.web.commons_validation.PlzValidator;

/**
 * höchstens 10 Zeichen, Ziffern, Grundbuchstaben, Minus, Slash<br>
 * <br>
 * Wenn der Wert null ist, wird er als gültig angesehen. Es muss also zusätzlich eine NotNull-Anntotation angebracht
 * werden.
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Constraint(validatedBy = { PlzValidator.class })
public @interface Plz {

	String message() default "{de.egladil.constraints.invalidChars}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
