// =====================================================
// Project: commons
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import de.egladil.web.commons_validation.SecUtils;
import de.egladil.web.commons_validation.annotations.ClientId;
import de.egladil.web.commons_validation.annotations.UuidString;

/**
 * OAuthClientCredentials
 */
public class OAuthClientCredentials {

	@NotBlank
	@ClientId
	@Size(max = 50)
	private String clientId;

	@NotBlank
	@ClientId
	@Size(max = 50)
	private String clientSecret;

	@UuidString
	@Size(max = 36)
	private String nonce;

	public static OAuthClientCredentials create(final String clientId, final String clientSecret, final String nonce) {

		OAuthClientCredentials result = new OAuthClientCredentials();
		result.clientId = clientId;
		result.clientSecret = clientSecret;
		result.nonce = nonce;
		return result;

	}

	public String getClientId() {

		return clientId;
	}

	public void setClientId(final String clientId) {

		this.clientId = clientId;
	}

	public String getClientSecret() {

		return clientSecret;
	}

	public void setClientSecret(final String clientSecret) {

		this.clientSecret = clientSecret;
	}

	public String getNonce() {

		return nonce;
	}

	public void setNonce(final String nonce) {

		this.nonce = nonce;
	}

	public void clean() {

		clientId = SecUtils.wipe(clientId);
		clientSecret = SecUtils.wipe(clientSecret);
	}

}
