// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_crypto.PasswordAlgorithm;
import de.egladil.web.commons_crypto.PasswordAlgorithmBuilder;

/**
 * CryptoServiceImplTest
 */
public class CryptoServiceImplTest {

	private CryptoServiceImpl service = new CryptoServiceImpl();

	@Test
	@DisplayName("should hash and verify password")
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

		final Hash computedHash = service.hashPassword(passworAlgorithm, password, salt);

		final String base64Hash = computedHash.toBase64();
		System.out.println("Base64-Hash=" + base64Hash);

		// prüfen
		assertTrue(service.verifyPassword(passworAlgorithm, password, base64Hash, base64Salt));
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

			service.verifyPassword(passworAlgorithm, password, "odgoqgod", base64Salt);
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

			assertEquals("Fehler bei " + i, erstes[i], zweites[i]);
		}
	}

	@Test
	public void fullyConfiguredHasher() {

		final ByteSource originalPassword = ByteSource.Util.bytes("Secret");

		final byte[] baseSalt = { 1, 1, 1, 2, 2, 2, 3, 3, 3 };
		final int iterations = 10;

		final DefaultHashService hasher = new DefaultHashService();
		hasher.setPrivateSalt(new SimpleByteSource(baseSalt));
		hasher.setHashIterations(iterations);
		hasher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);

		// custom public salt
		final byte[] publicSalt = { 1, 3, 5, 7, 9 };
		final ByteSource salt = ByteSource.Util.bytes(publicSalt);

		// use hasher to compute password hash
		final HashRequest request = new SimpleHashRequest(hasher.getHashAlgorithmName(), originalPassword, salt,
			hasher.getHashIterations());
		final Hash response = hasher.computeHash(request);

		final byte[] expectedHash = { -108, 19, -40, 8, 89, -59, 115, -4, 78, 48, 110, 115, -117, 54, -80, 72, 44, 22, -100, -24,
			-23, -114, -24, -128, -95, -125, 2, -67, -40, 83, 90, -103 };
		assertArrayEquals(expectedHash, response.getBytes());

		final String expectedPwdHash = CodecSupport.toString(expectedHash);
		final String actualPwdHash = CodecSupport.toString(response.getBytes());

		assertEquals(expectedPwdHash, actualPwdHash);
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

			assertTrue("Fehler bei " + strValue, matches);
		}
	}

}
