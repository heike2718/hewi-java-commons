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

import de.egladil.web.commons_validation.LandKuerzelValidator;

/**
 * Whitelist für Kürzel: A-Z sowie 0-9.
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Constraint(validatedBy = { LandKuerzelValidator.class })
public @interface LandKuerzel {

	String message() default "{de.egladil.constraints.landkuerzel}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
