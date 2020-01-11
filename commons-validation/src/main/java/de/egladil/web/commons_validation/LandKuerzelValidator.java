// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.LandKuerzel;

/**
 * LandKuerzelValidator
 */
public class LandKuerzelValidator extends AbstractWhitelistValidator<LandKuerzel, String> {

	private static final String REGEXP = "[A-Z\\-\\,]*";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}
}
