// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.Plz;

/**
 * PlzValidator
 * [\w\-/]*
 */
public class PlzValidator extends AbstractWhitelistValidator<Plz, String> {

	/** [\w\-/]* */
	private static final String REGEXP = "[\\w\\-/]*";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}
}
