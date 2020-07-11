// =====================================================
// Project: authprovider
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ExchangeTokenResponse
 */
public class ExchangeTokenResponse {

	@JsonProperty
	private String nonce;

	@JsonProperty
	private String jwt;

	public static ExchangeTokenResponse create(final String jwt, final String nonce) {

		ExchangeTokenResponse result = new ExchangeTokenResponse();
		result.jwt = jwt;
		result.nonce = nonce;
		return result;
	}

}
