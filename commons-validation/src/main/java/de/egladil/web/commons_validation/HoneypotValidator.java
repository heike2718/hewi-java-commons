// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.egladil.web.commons_validation.annotations.Honeypot;

/**
 * HoneypotValidator
 */
public class HoneypotValidator implements ConstraintValidator<Honeypot, String> {

	@Override
	public void initialize(final Honeypot constraintAnnotation) {

		// nix zu tun
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		if (value == null) {

			return true;
		}
		return value.isEmpty();
	}
}
