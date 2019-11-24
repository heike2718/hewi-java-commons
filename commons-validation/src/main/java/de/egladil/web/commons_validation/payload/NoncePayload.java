// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation.payload;

import de.egladil.web.commons_validation.annotations.UuidString;

/**
 * NoncePayload
 */
public class NoncePayload {

	@UuidString
	private String nonce;

	public static NoncePayload create(final String nonce) {

		NoncePayload result = new NoncePayload();
		result.nonce = nonce;
		return result;
	}

	public NoncePayload() {

	}

	public String getNonce() {

		return nonce;
	}

}
