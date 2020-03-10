// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import de.egladil.web.commons_validation.annotations.JWTString;

/**
 * JWTStringValidator
 */
public class JWTStringValidator extends AbstractWhitelistValidator<JWTString, String> {

	@Override
	protected String getWhitelist() {

		return "[a-zA-Z0-9-_]+?.[a-zA-Z0-9-_]+?.([a-zA-Z0-9-_]+)[/a-zA-Z0-9-_]+?$";
	}

}
