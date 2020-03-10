// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.Hausnummer;

/**
 * PlzValidator
 * [\w\-/]*
 */
public class HausnummerValidator extends AbstractWhitelistValidator<Hausnummer, String> {

	/** [\w\-/]* */
	private static final String REGEXP = "[\\w\\- _/]*";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}
}
