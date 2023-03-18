// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto.impl;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import de.egladil.web.commons_crypto.CryptoService;
import de.egladil.web.commons_crypto.PasswordAlgorithm;
import de.egladil.web.commons_crypto.exception.CommonCryptoException;

/**
 * CryptoServiceImpl Wrapper für Apache Shiro
 */
@RequestScoped
public class CryptoServiceImpl implements CryptoService {

	/**
	 * Erzeugt eine Instanz von CryptoServiceImpl
	 */
	public CryptoServiceImpl() {

		Security.addProvider(new BouncyCastleProvider());
	}

	@Override
	public Hash hashPassword(final PasswordAlgorithm algorithm, final char[] password, final ByteSource salt) {

		return algorithm.hashPassword(password, salt);
	}

	@Override
	public boolean verifyPassword(final PasswordAlgorithm algorithm, final char[] password, final String persistentHashValue, final String persistentSalt) {

		return algorithm.verifyPassword(password, persistentHashValue, persistentSalt);
	}

	@Override
	public String generateKuerzel(final int length, final char[] charPool) {

		if (charPool == null) {

			throw new IllegalArgumentException("charPool darf nicht null sein");
		}

		if (charPool.length < 26) {

			throw new IllegalArgumentException("charPool muss mindestlaenge 26 haben");
		}
		final StringBuilder sb = new StringBuilder();

		for (int loop = 0; loop < length; loop++) {

			final int index = new Random().nextInt(charPool.length);
			sb.append(charPool[index]);
		}
		final String nonce = sb.toString();
		return nonce;
	}

	@Override
	public String generateShortUuid() {

		final UUID uuid = UUID.randomUUID();
		final long msb = uuid.getMostSignificantBits();
		final byte[] uuidBytes = ByteBuffer.allocate(8).putLong(msb).array();
		final String encoded = Base64.getEncoder().encodeToString(uuidBytes).replaceAll("\\+", "").replaceAll("\\=", "")
			.replaceAll("/", "");
		return encoded;
	}

	@Override
	public String generateRandomString(final String algorithm, final int length, final char[] charPool) {

		try {

			SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
			// nach ESAPI
			StringBuilder sb = new StringBuilder();

			for (int loop = 0; loop < length; loop++) {

				int index = secureRandom.nextInt(charPool.length);
				sb.append(charPool[index]);
			}
			return sb.toString();
		} catch (final Exception e) {

			throw new CommonCryptoException("Fehler beim generieren eines Zufallsstrings: " + e.getMessage(), e);
		}
	}

	@Override
	public char[] generateSalt(final int saltLengthBits) {

		final int byteSize = saltLengthBits / 8; // generatedSaltSize is in *bits* - convert to byte size:
		return new SecureRandomNumberGenerator().nextBytes(byteSize).toBase64().toCharArray();
	}

	@Override
	public String generateSessionId() {

		char[] result = generateSalt(128);
		return new String(result);
	}

}
