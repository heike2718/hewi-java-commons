// =====================================================
// Project: commons-crypto
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_crypto;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import de.egladil.web.commons_crypto.exception.CommonCryptoException;

/**
 * JWTService
 */
public interface JWTService {

	/**
	 * @param  token
	 * @param  publicKeyData
	 * @return
	 * @throws JWTVerificationException
	 * @throws TokenExpiredException
	 * @throws CommonCryptoException
	 */
	DecodedJWT verify(final String token, final byte[] publicKeyData) throws JWTVerificationException, CommonCryptoException;

}
