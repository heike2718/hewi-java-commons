// =====================================================
// Project: commons-auth
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_auth.clientauth;

import de.egladil.web.commons_auth.config.AuthproviderConfiguration;

/**
 * IClientAccessTokenService
 */
public interface IClientAccessTokenService {

	/**
	 * Handshake mit nonce zum authenticationprovider.
	 *
	 * @param  authproviderConfiguration
	 * @return                           String
	 */
	String orderAccessToken(AuthproviderConfiguration authproviderConfiguration);

}
