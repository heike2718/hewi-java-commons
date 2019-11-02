// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.StringLatin;

/**
 * WhitelistValidator
 */
public class StringLatinValidator extends AbstractWhitelistValidator<StringLatin, String> {

	@Override
	protected String getWhitelist() {

		return StringLatinConstants.NAME_WHITELIST_REGEXP;
	}

}
