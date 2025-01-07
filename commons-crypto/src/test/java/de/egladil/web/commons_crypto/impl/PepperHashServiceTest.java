// =====================================================
// Project: commons-crypto
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_crypto.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.lang.util.ByteSource;
import org.junit.jupiter.api.Test;

/**
 * PepperHashServiceTest
 */
public class PepperHashServiceTest {

	@Test
	public void fullyConfiguredHasher() {

		final ByteSource originalPassword = ByteSource.Util.bytes("Secret");

		final byte[] baseSalt = { 1, 1, 1, 2, 2, 2, 3, 3, 3 };
		String pepper = new String(baseSalt);
		final int iterations = 10;

		Map<String, Object> params = new HashMap<>();
		params.put("iterations", Integer.valueOf(iterations));

		PepperHashService hasher = new PepperHashService(Sha256Hash.ALGORITHM_NAME, pepper, params);

		final byte[] publicSalt = { 1, 3, 5, 7, 9 };
		final ByteSource salt = ByteSource.Util.bytes(publicSalt);

		final HashRequest request = new SimpleHashRequest(hasher.getHashAlgorithmName(), originalPassword, salt,
			params);
		final Hash response = hasher.computeHash(request);

		boolean matches = response.matchesPassword(originalPassword);

		final String expectedPwdHash = "$shiro1$SHA-256$50000$AQEBAgICAwMDAQMFBwk=$bBHpWB+kQYFPSbJhjF5A0zuwkjt9TsrXdmMWcNQPmeQ=";
		final String actualPwdHash = new Shiro1CryptFormat().format(response);

		System.out.println("expectedPwdHash=" + expectedPwdHash);
		System.out.println("actualPwdHash=" + actualPwdHash);

		assertEquals(expectedPwdHash, actualPwdHash);
		assertTrue(matches);

	}

}
