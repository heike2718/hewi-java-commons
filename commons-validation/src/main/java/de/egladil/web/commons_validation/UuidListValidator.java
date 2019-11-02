// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.egladil.web.commons_validation.annotations.UuidList;

/**
 * UuidListValidator
 */
public class UuidListValidator implements ConstraintValidator<UuidList, List<?>> {

	private UuidStringValidator uuidStringValidator;

	@Override
	public void initialize(final UuidList constraintAnnotation) {

		ConstraintValidator.super.initialize(constraintAnnotation);
		this.uuidStringValidator = new UuidStringValidator();
	}

	@Override
	public boolean isValid(final List<?> value, final ConstraintValidatorContext context) {

		if (value == null) {

			return true;
		}

		if (!(value instanceof List)) {

			return false;
		}
		Optional<Boolean> optConstValidation = value.stream().map(v -> v.toString())
			.map(s -> !uuidStringValidator.isValid(s, context)).findFirst();
		return optConstValidation.isPresent();
	}

}
