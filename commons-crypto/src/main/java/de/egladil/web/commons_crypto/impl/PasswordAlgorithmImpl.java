// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto.impl;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.AbstractCryptHash;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashSpi.HashFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.SimpleHashProvider;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.crypto.support.hashes.argon2.Argon2HashProvider;
import org.apache.shiro.lang.util.ByteSource;
import org.apache.shiro.lang.util.SimpleByteSource;

import de.egladil.web.commons_crypto.CryptoVersion;
import de.egladil.web.commons_crypto.PasswordAlgorithm;

/**
 * PasswordAlgorithmImpl
 */
public class PasswordAlgorithmImpl implements PasswordAlgorithm {

	private final Random random = new SecureRandom();

	private final Integer numberIterations;

	private final String pepper;

	private final String algorithmName;

	/**
	 * Erzeugt eine Instanz von PasswordAlgorithmImpl
	 */
	public PasswordAlgorithmImpl(final String pepper, final String algorithmName, final int numberIterations) {

		if (StringUtils.isBlank(pepper)) {

			throw new IllegalArgumentException("pepper blank");
		}

		if (StringUtils.isBlank(algorithmName)) {

			throw new IllegalArgumentException("algorithmName blank");
		}

		if (numberIterations <= 0) {

			throw new IllegalArgumentException("numberIterations <= 0");
		}

		this.pepper = pepper;
		this.algorithmName = algorithmName;
		this.numberIterations = Integer.valueOf(numberIterations);
	}

	@Override
	public boolean verifyPassword(final char[] password, final String persistentHashValue, final String persistentSalt, final CryptoVersion cryptoVersion) {

		switch (cryptoVersion) {

		case SHA_256:

			return verifyPasswordSha256(password, persistentHashValue, persistentSalt);

		case ARGON_2:
			return verifyPasswordArgon2(password, persistentHashValue);

		default:
			throw new IllegalArgumentException("unexpected CryptoVersion " + cryptoVersion.toString());
		}

	}

	@Override
	public Hash hashPassword(final char[] password, final ByteSource salt, final CryptoVersion cryptpVersion) {

		if (password == null || password.length == 0) {

			throw new IllegalArgumentException("password null oder leer");
		}

		switch (cryptpVersion) {

		case SHA_256:

			return this.hashPasswordSha256(password, salt);

		case ARGON_2:
			return this.hashPasswordArgon2(password);

		default:
			throw new IllegalArgumentException("unexpected CryptoVersion " + cryptpVersion.toString());
		}
	}

	Hash hashPasswordSha256(final char[] password, final ByteSource salt) {

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("SimpleHash.iterations", numberIterations);
		// parameters.put("SimpleHash.secretSalt", pepper);

		final SimpleByteSource passwdByteSource = new SimpleByteSource(pepper + new String(password));

		final HashRequest hashRequest = new SimpleHashRequest(algorithmName, passwdByteSource, salt, parameters);

		HashFactory hashFactory = new SimpleHashProvider().newHashFactory(random);

		final Hash hash = hashFactory.generate(hashRequest);
		return hash;

	}

	Hash hashPasswordArgon2(final char[] password) {

		String pepperedPassword = getPepperedPassword(password);
		System.out.println(">>>>> hashPasswordArgon2: " + pepperedPassword + " <<<<<");
		final SimpleByteSource passwdByteSource = new SimpleByteSource(pepperedPassword);
		HashRequest hashRequest = new HashRequest.Builder()
			.setSource(passwdByteSource)
			.build();

		HashFactory hashFactory = new Argon2HashProvider().newHashFactory(random);

		final Hash hash = hashFactory.generate(hashRequest);

		System.out.println(">>>>> hashPasswordArgon2: " + ((AbstractCryptHash) hash).formatToCryptString() + " <<<<<");

		return hash;

	}

	boolean verifyPasswordSha256(final char[] password, final String persistentHashValue, final String persistentSalt) {

		ByteSource bsPwd = ByteSource.Util.bytes(new String(password));
		ByteSource bsSalt = ByteSource.Util.bytes(Base64.getDecoder().decode(persistentSalt));

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("SimpleHash.iterations", numberIterations);

		HashRequest hashRequest = new SimpleHashRequest(algorithmName, bsPwd, bsSalt, parameters);
		Hash expectedHash = this.computeHash(hashRequest);

		if (expectedHash == null) {
			throw new IllegalArgumentException("password null oder leer");
		}

		final String expectedHashValue = new SimpleByteSource(expectedHash.getBytes()).toBase64();

		if (MessageDigest.isEqual(expectedHashValue.getBytes(), persistentHashValue.getBytes())) {

			return true;
		}
		return false;
	}

	boolean verifyPasswordArgon2(final char[] password, final String persistentHashValue) {

		PasswordService passwordService = new DefaultPasswordService();
		String pepperedPassword = getPepperedPassword(password);
		System.out.println(">>>>> verifyPasswordArgon2: " + pepperedPassword + " <<<<<");
		System.out.println(">>>>> verifyPasswordArgon2: persistentHashValue = " + persistentHashValue + " <<<<<");
		return passwordService.passwordsMatch(pepperedPassword, persistentHashValue);
	}

	private String getPepperedPassword(final char[] password) {

		return pepper + new String(password);
	}

	public String getPepper() {

		return pepper;
	}

	ByteSource combinePepperAndSalt(final ByteSource pepper, final ByteSource publicSalt) {

		byte[] privateSaltBytes = pepper != null ? pepper.getBytes() : null;
		int privateSaltLength = privateSaltBytes != null ? privateSaltBytes.length : 0;

		byte[] publicSaltBytes = publicSalt != null ? publicSalt.getBytes() : null;
		int extraBytesLength = publicSaltBytes != null ? publicSaltBytes.length : 0;

		int length = privateSaltLength + extraBytesLength;

		if (length <= 0) {

			return null;
		}

		byte[] combined = new byte[length];

		int i = 0;

		for (int j = 0; j < privateSaltLength; j++) {

			assert privateSaltBytes != null;
			combined[i++] = privateSaltBytes[j];
		}

		for (int j = 0; j < extraBytesLength; j++) {

			assert publicSaltBytes != null;
			combined[i++] = publicSaltBytes[j];
		}

		return ByteSource.Util.bytes(combined);
	}

	Hash computeHash(final HashRequest request) {

		if (request == null || request.getSource() == null || request.getSource().isEmpty()) {

			return null;
		}

		ByteSource source = request.getSource();

		ByteSource publicSalt = request.getSalt().get();
		ByteSource privateSalt = new SimpleByteSource(pepper);
		ByteSource combinedPepperAndSalt = combinePepperAndSalt(privateSalt, publicSalt);

		Hash computed = new SimpleHash(algorithmName, source, combinedPepperAndSalt, numberIterations);

		SimpleHash result = new SimpleHash(algorithmName);
		result.setBytes(computed.getBytes());
		result.setIterations(numberIterations);
		// Only expose the public salt - not the real/combined salt that might have been used:
		result.setSalt(publicSalt);

		return result;
	}

}
