// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import javax.validation.ConstraintValidatorContext;

import de.egladil.web.commons_validation.annotations.Passphrase;

/**
 * PassphraseValdator
 */
public class PassphraseValdator extends AbstractWhitelistValidator<Passphrase, String> {

	/**
	 * ^.{24,200}$
	 */
	private static final String REGEXP = "^.{8,200}$";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		if (value == null) {

			return true;
		}

		if (!super.isValid(value, context)) {

			return false;
		}
		final String trimmed = value.trim();

		if (trimmed.length() < value.length()) {

			return false;
		}
		return true;
	}
}
