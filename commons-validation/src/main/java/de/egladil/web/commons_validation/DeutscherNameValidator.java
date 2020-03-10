// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.DeutscherName;

/**
 * DeutscherNameValidator
 */
public class DeutscherNameValidator extends AbstractWhitelistValidator<DeutscherName, String> {

	private static final String REGEXP = "[\\w äöüßÄÖÜ\\-@&,.()\"]*";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}

}
