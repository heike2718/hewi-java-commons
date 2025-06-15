// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.AbstractCryptHash;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.support.hashes.argon2.Argon2HashProvider;
import org.apache.shiro.lang.util.ByteSource;
import org.apache.shiro.lang.util.SimpleByteSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_crypto.CryptoVersion;
import de.egladil.web.commons_crypto.PasswordAlgorithm;
import de.egladil.web.commons_crypto.PasswordAlgorithmBuilder;

/**
 * CryptoServiceImplTest
 */
public class CryptoServiceImplTest {

	private CryptoServiceImpl service = new CryptoServiceImpl();

	@Nested
	class Sha256Tests {

		//@Test
		void hashPassword() {

			// Arrange
			String cryptoAlgorithm = "SHA-256";
			// String pepper = "GmpxYkYuleJs4LLwbjwz";
			String pepper = "z0eiPZVJxq/xhYD1RkXACJMKqtmzMQQ9blaR+ozXMk8=";
			final ByteSource salt = new SimpleByteSource(service.generateSalt(128));

			final String base64Salt = salt.toBase64();
			System.out.println("Base64-Salt=" + base64Salt);
			// char[] password = "errätst du nie hehehe".toCharArray();
			char[] password = "start123".toCharArray();
			// int iterations = 40;
			int iterations = 4098;

			PasswordAlgorithm passworAlgorithm = PasswordAlgorithmBuilder.instance().withAlgorithmName(cryptoAlgorithm)
				.withNumberIterations(iterations).withPepper(pepper).build();

			final Hash computedHash = service.hashPassword(passworAlgorithm, password, salt, CryptoVersion.SHA_256);

			final String base64Hash = computedHash.toBase64();
			System.out.println("Base64-Hash=" + base64Hash);

			// prüfen
			assertTrue(service.verifyPassword(passworAlgorithm, password, base64Hash, base64Salt, CryptoVersion.SHA_256));
		}

	}

	@Nested
	class ArgonTests {

		// @Test
		void hashPassword() {

			// Arrange
			// String pepper = "GmpxYkYuleJs4LLwbjwz";
			String pepper = "z0eiPZVJxq/xhYD1RkXACJMKqtmzMQQ9blaR+ozXMk8=";

			// char[] password = "errätst du nie hehehe".toCharArray();
			char[] password = "start123".toCharArray();
			// int iterations = 40;
			int iterations = 4098;

			PasswordAlgorithm passworAlgorithm = PasswordAlgorithmBuilder.instance()
				.withAlgorithmName(Argon2HashProvider.Parameters.DEFAULT_ALGORITHM_NAME)
				.withNumberIterations(iterations).withPepper(pepper).build();

			final Hash computedHash = service.hashPassword(passworAlgorithm, password, null, CryptoVersion.ARGON_2);

			AbstractCryptHash argon2Hash = (AbstractCryptHash) computedHash;

			System.out.println("computedHashValue=" + argon2Hash.formatToCryptString());

			assertTrue(
				service.verifyPassword(passworAlgorithm, password, argon2Hash.formatToCryptString(), null, CryptoVersion.ARGON_2));
		}
	}

	@Test
	void verifyPasswordLeer() {

		// Arrange
		String cryptoAlgorithm = "SHA-256";
		String pepper = "GmpxYkYuleJs4LLwbjwz";
		final ByteSource salt = new SimpleByteSource(service.generateSalt(128));

		final String base64Salt = salt.toBase64();
		System.out.println("Base64-Salt=" + base64Salt);
		char[] password = "".toCharArray();
		Integer iterations = 40;

		PasswordAlgorithm passworAlgorithm = PasswordAlgorithmBuilder.instance().withAlgorithmName(cryptoAlgorithm)
			.withNumberIterations(iterations.intValue()).withPepper(pepper).build();

		final Throwable ex = assertThrows(IllegalArgumentException.class, () -> {

			service.verifyPassword(passworAlgorithm, password, "odgoqgod", base64Salt, CryptoVersion.SHA_256);
		});

		assertEquals("password null oder leer", ex.getMessage());

	}

	@Nested
	@DisplayName("test generateRandomString")
	class GenerateRandomString {

		@Test
		@DisplayName("should create string with given length")
		void generateClientId() {

			// Arrange
			final int length = 44;
			final String algorithm = "SHA1PRNG";
			final String charpool = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghjklmnopqrstuvwxyz0123456789"; //

			// Act
			final String zufallsstring = service.generateRandomString(algorithm, length, charpool.toCharArray());

			// Assert
			assertNotNull(zufallsstring);

			System.out.println(zufallsstring);
		}
	}

	@Nested
	@DisplayName("test generateSalt")
	class GenerateSalt {
		@Test
		@DisplayName("should create salt with given length")
		void generateSalt1() {

			// Arrange
			final int saltLengthByte = 128;

			// Act
			final char[] actual = service.generateSalt(saltLengthByte);

			// Assert
			assertNotNull(actual);

			final String str = new String(actual);

			System.out.println(str);
		}
	}

	@Nested
	class PlainShiroTests {

		@Test
		void testArgon2() {

			// Arrange
			String pepper = "my-secret-pepper";
			String pepperedPassword = pepper + "start123";

			DefaultHashService hashService = new DefaultHashService();
			hashService.setDefaultAlgorithmName(Argon2HashProvider.Parameters.DEFAULT_ALGORITHM_NAME);

			DefaultPasswordService passwordService = new DefaultPasswordService();
			passwordService.setHashService(hashService);

			// Act
			String result = passwordService.encryptPassword(pepperedPassword);

			System.out.println("encrypted password=" + result);

			// Verify
			boolean matches = passwordService.passwordsMatch(pepperedPassword, result);

			assertTrue(matches);
		}

	}

	@Test
	public void testIterationsSha256Hash() {

		final byte[] salt = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		final Hash hash = new Sha256Hash("Hello Sha256", salt, 10);

		final byte[] expectedHash = { 24, 4, -97, -61, 70, 28, -29, 85, 110, 0, -107, -8, -12, -93, -121, 99, -5, 23, 60, 46, -23,
			92, 67, -51, 65, 95, 84, 87, 49, -35, -78, -115 };
		final String expectedHex = "18049fc3461ce3556e0095f8f4a38763fb173c2ee95c43cd415f545731ddb28d";
		final String expectedBase64 = "GASfw0Yc41VuAJX49KOHY/sXPC7pXEPNQV9UVzHdso0=";

		assertArrayEquals(expectedHash, hash.getBytes());
		assertEquals(expectedHex, hash.toHex());
		assertEquals(expectedBase64, hash.toBase64());

		System.out.println("Sha256 with salt and 10 iterations of 'Hello Sha256': " + hash);
	}

	private void assertArrayEquals(final byte[] erstes, final byte[] zweites) {

		for (int i = 0; i < erstes.length; i++) {

			assertEquals(erstes[i], zweites[i], "Fehler bei " + i);
		}
	}

	@Nested
	@DisplayName("test generateShortUuid")
	class GenerateShortUuid {

		@RepeatedTest(100)
		@DisplayName("should run many times without error")
		public void generateShortUuid_klappt() {

			final String regexp = "[a-zA-Z0-9\\-]*";
			final Pattern pattern = Pattern.compile(regexp);

			final String strValue = service.generateShortUuid();

			final Matcher matcher = pattern.matcher(strValue);
			final boolean matches = matcher.matches();

			assertTrue(matches, "Fehler bei " + strValue);
		}
	}

}
