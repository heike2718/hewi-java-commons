// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.UrlValidator;

import de.egladil.web.commons_validation.annotations.StringLatin;

/**
 * WhitelistValidator.<br>
 * <br>
 * Strings, die URLs sind, sind nicht gültig!
 */
public class StringLatinValidator extends AbstractWhitelistValidator<StringLatin, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		if (value == null) {

			return true;
		}

		if (!(value instanceof String)) {

			return false;
		}
		String strValue = (String) value;

		if (strValue.isEmpty()) {

			return true;
		}

		UrlValidator urlValidator = new UrlValidator();

		if (urlValidator.isValid(value)) {

			return false;
		}

		Matcher matcher = getPattern().matcher(strValue);
		boolean matches = matcher.matches();
		return matches;
	}

	@Override
	protected String getWhitelist() {

		return StringLatinConstants.NAME_WHITELIST_REGEXP;
	}

}
