// =====================================================
// Project: commons-crypto
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_crypto.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.lang.util.ByteSource;
import org.apache.shiro.lang.util.SimpleByteSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_crypto.CryptoVersion;
import de.egladil.web.commons_crypto.PasswordAlgorithm;
import de.egladil.web.commons_crypto.PasswordAlgorithmBuilder;

/**
 * PasswordAlgorithmImplTest
 */
public class PasswordAlgorithmImplTest {

	private static final String PEPPER = "z0eiPZVJxq/xhYD1RkXACJMKqtmzMQQ9blaR+ozXMk8=";

	@Nested
	class Sha256Tests {

		// @Test
		void hashPassword() {

			// Arrange
			String cryptoAlgorithm = "SHA-256";
			// String pepper = "GmpxYkYuleJs4LLwbjwz";
			int iterations = 4098;

			PasswordAlgorithm pwAlgorithm = PasswordAlgorithmBuilder.instance().withAlgorithmName(cryptoAlgorithm)
				.withNumberIterations(iterations).withPepper(PEPPER).build();

			final int byteSize = 128 / 8; // generatedSaltSize is in *bits* - convert to byte size:
			char[] charArray = new SecureRandomNumberGenerator().nextBytes(byteSize).toBase64().toCharArray();
			final ByteSource salt = new SimpleByteSource(charArray);

			final String base64Salt = salt.toBase64();
			System.out.println("Base64-Salt=" + base64Salt);
			// char[] password = "errätst du nie hehehe".toCharArray();
			char[] password = "start123".toCharArray();
			// int iterations = 40;
			final Hash computedHash = pwAlgorithm.hashPassword(password, salt, CryptoVersion.SHA_256);

			final String base64Hash = computedHash.toBase64();
			System.out.println("Base64-Hash=" + base64Hash);

			// prüfen
			boolean matches = pwAlgorithm.verifyPassword(password, base64Hash, base64Salt, CryptoVersion.SHA_256);
			assertTrue(matches);
		}

		@Test
		void verifyClientBenutzerprofil() {

			char[] password = "start123".toCharArray();
			String base64Hash = "PuDq/o0PkklF+yhrZqr2R1CrILBjh21NlirmmV1t7oA=";
			String base64Salt = "eFMwUWp5cTdad2t1NnpzZGtYQjIvUT09";

			String cryptoAlgorithm = "SHA-256";
			int iterations = 4098;

			PasswordAlgorithm pwAlgorithm = PasswordAlgorithmBuilder.instance().withAlgorithmName(cryptoAlgorithm)
				.withNumberIterations(iterations).withPepper(PEPPER).build();

			boolean matches = pwAlgorithm.verifyPassword(password, base64Hash, base64Salt, CryptoVersion.SHA_256);
			assertTrue(matches);
		}
	}

}
