// =====================================================
// Project: commons-auth
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_auth.config;

/**
 * AuthproviderConfiguration
 */
public class AuthproviderConfiguration {

	private String authproviderUrl;

	private String authAppUrl;

	private String clientId;

	private String clientSecret;

	/**
	 * @return the authproviderUrl
	 */
	public String getAuthproviderUrl() {

		return authproviderUrl;
	}

	/**
	 * @param authproviderUrl
	 *                        the authproviderUrl to set
	 */
	public AuthproviderConfiguration withAuthproviderUrl(final String authproviderUrl) {

		this.authproviderUrl = authproviderUrl;
		return this;
	}

	/**
	 * @return the authAppUrl
	 */
	public String getAuthAppUrl() {

		return authAppUrl;
	}

	/**
	 * @param authAppUrl
	 *                   the authAppUrl to set
	 */
	public AuthproviderConfiguration withAuthAppUrl(final String authAppUrl) {

		this.authAppUrl = authAppUrl;
		return this;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {

		return clientId;
	}

	/**
	 * @param clientId
	 *                 the clientId to set
	 */
	public AuthproviderConfiguration withClientId(final String clientId) {

		this.clientId = clientId;
		return this;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {

		return clientSecret;
	}

	/**
	 * @param clientSecret
	 *                     the clientSecret to set
	 */
	public AuthproviderConfiguration withClientSecret(final String clientSecret) {

		this.clientSecret = clientSecret;
		return this;
	}

}
