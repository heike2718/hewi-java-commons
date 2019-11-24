// =====================================================
// Project: commons-crypto
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_crypto.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.jwt.Claims;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JWTServiceImplTest
 */
public class JWTServiceImplTest {

	private static final String VALID_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyMDcyMTU3NS04YzQ1LTQyMDEtYTAyNS03YTlmZWNlMWYyYWEiLCJmdWxsX25hbWUiOiJDaGVja2kgUGFsbGV0dGkiLCJpc3MiOiJoZWlrZTI3MTgvYXV0aHByb3ZpZGVyIiwiZ3JvdXBzIjpbIkFETUlOIiwiRUNIT0VSIiwiU1RBTkRBUkQiLCJTVUJTQ1JJQkVSIl0sImV4cCI6MzQ2NzkxMTA3NywiaWF0IjoxNTc0NDk1NTE3fQ.rrnRpkRi8YF7lhZMeZ-ffvE9Sie__7Me9Io4tWJ5NWKYxc3x6jP9Ji_G1GZLXUEpZVOd9sJRcaVjw1VKg0zThbDnMp4gZegoTMo6qw-hVkHFNOy8Lsv3qYQvUaW9Uj5slSd7wOkg_h_1p-BF_myRP11ZSpMnAyURfri-S_4g75uAezVvOxs0plqkFLYP3qNLiSAPDRirMDqkrbkMjQUekjtAwPIPXz_00nA_hK5_2qwov_ev6mbvf0bokTca01JO7EmK8KnChAHT3ujK04hBzPFsayhQsagEcpT6NJVKMwWy2UUwqvztoscUZr2ZpcwIRMFbWC49Wi-34aVBhfpZ6w";

	@Test
	void verifyValidJWT() throws IOException {

		// Arrange
		JWTServiceImpl service = new JWTServiceImpl();

		try (InputStream in = getClass().getResourceAsStream("/authprov_public_key.pem"); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, Charset.forName("UTF-8"));
			byte[] publicKeyData = sw.toString().getBytes();

			DecodedJWT decodedJWT = service.verify(VALID_JWT, publicKeyData);

			assertNotNull(decodedJWT);

			String uuid = decodedJWT.getSubject();
			assertEquals("20721575-8c45-4201-a025-7a9fece1f2aa", uuid);

			Claim groupsClaim = decodedJWT.getClaim(Claims.groups.name());

			String[] roles = groupsClaim.asArray(String.class);
			assertEquals(4, roles.length);

			assertEquals("ADMIN", roles[0]);
			assertEquals("ECHOER", roles[1]);
			assertEquals("STANDARD", roles[2]);
			assertEquals("SUBSCRIBER", roles[3]);

			String fullName = decodedJWT.getClaim(Claims.full_name.name()).asString();
			assertEquals("Checki Palletti", fullName);
		} catch (TokenExpiredException e) {

			System.err.println(
				"Mittels SessionServiceTest ein lange gültiges JWT besorgen. Dafür für checklistenapp in der DB die Gültigkeit auf x Jahre setzen.");
		}

	}

}
