// =====================================================
// Project: commons-crypto
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_crypto;

import org.eclipse.microprofile.jwt.Claims;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * DecodedJWTReader
 */
public class DecodedJWTReader {

	private final DecodedJWT decodedJWT;

	/**
	 * @param decodedJWT
	 */
	public DecodedJWTReader(final DecodedJWT decodedJWT) {

		this.decodedJWT = decodedJWT;
	}

	public String getFullName() {

		return decodedJWT.getClaim(Claims.full_name.name()).asString();
	}

	public String[] getGroups() {

		Claim groups = decodedJWT.getClaim(Claims.groups.name());
		return !groups.isNull() ? groups.asList(String.class).toArray(new String[0]) : new String[0];
	}

}
