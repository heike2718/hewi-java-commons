// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto.impl;

import java.security.MessageDigest;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import de.egladil.web.commons_crypto.PasswordAlgorithm;

/**
 * PasswordAlgorithmImpl
 */
public class PasswordAlgorithmImpl implements PasswordAlgorithm {

	private final String pepper;

	private final String algorithmName;

	private final int numberIterations;

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
		this.numberIterations = numberIterations;
	}

	@Override
	public boolean verifyPassword(final char[] password, final String persistentHashValue, final String persistentSalt) {

		final ByteSource salt = new SimpleByteSource(Base64.getDecoder().decode(persistentSalt));

		final Hash expectedHash = hashPassword(password, salt);

		final String expectedHashValue = new SimpleByteSource(expectedHash.getBytes()).toBase64();

		if (MessageDigest.isEqual(expectedHashValue.getBytes(), persistentHashValue.getBytes())) {

			return true;
		}
		return false;
	}

	@Override
	public Hash hashPassword(final char[] password, final ByteSource salt) {

		if (password == null || password.length == 0) {

			throw new IllegalArgumentException("password null oder leer");
		}

		final HashService hashService = getHashService();

		final SimpleByteSource passwdByteSource = new SimpleByteSource(password);
		final HashRequest hashRequest = new SimpleHashRequest(algorithmName, passwdByteSource, salt, numberIterations);

		final Hash hash = hashService.computeHash(hashRequest);
		return hash;
	}

	private HashService getHashService() {

		final DefaultHashService hashService = new DefaultHashService();
		hashService.setPrivateSalt(new SimpleByteSource(pepper));
		return hashService;
	}

}
