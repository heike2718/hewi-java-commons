// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.Dateiname;

/**
 * DateinameValidator buchstaben ohne Umlaute, ziffern, unterstrich, punkt
 */
public class DateinameValidator extends AbstractWhitelistValidator<Dateiname, String> {

	private static final String REGEXP = "[a-zA-Z0-9\\.\\_-]*";

	@Override
	protected String getWhitelist() {

		return REGEXP;
	}
}
