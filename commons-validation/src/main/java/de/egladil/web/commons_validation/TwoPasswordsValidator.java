// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import de.egladil.web.commons_validation.annotations.ValidPasswords;
import de.egladil.web.commons_validation.payload.TwoPasswords;

/**
 * RegistrationCredentialsValidator
 */
public class TwoPasswordsValidator implements ConstraintValidator<ValidPasswords, TwoPasswords> {

	@Override
	public boolean isValid(final TwoPasswords value, final ConstraintValidatorContext context) {

		if (value == null) {

			return true;
		}

		if (value.getPasswort() != null && !value.getPasswort().equals(value.getPasswortWdh())) {

			context.buildConstraintViolationWithTemplate("Die Passwörter stimmen nicht überein").addBeanNode()
				.addConstraintViolation();
			return false;

		}
		return true;
	}
}
