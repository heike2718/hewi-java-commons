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

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import de.egladil.web.commons_validation.DeutscherNameValidator;

/**
 * Whitelist für Namen mit deutschem Alphabeth , Ziffern. Leerzeichen, Bindestriche, Punkte, Komma. Geeignet z.B. für Länder, Orte,
 * Schulen.
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Constraint(validatedBy = { DeutscherNameValidator.class })
public @interface DeutscherName {

	String message() default "{de.egladil.constraints.username}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
