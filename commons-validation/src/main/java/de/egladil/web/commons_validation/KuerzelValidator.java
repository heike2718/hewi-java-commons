// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.Kuerzel;

/**
 * KuerzelValidator. Es gibt keine Längenbeschränkunguen und keine Not-Null-Beschränkungen. Diese müssen als zusätzliche
 * Annotationen gesetzt werden!
 */
public class KuerzelValidator extends AbstractWhitelistValidator<Kuerzel, String> {

	private static final String REGEXP = "[A-Z0-9\\,-]*";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}
}
