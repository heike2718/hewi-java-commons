// =====================================================
// Project: commons-crypto
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_crypto.impl;

import java.util.Map;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.lang.util.ByteSource;
import org.apache.shiro.lang.util.SimpleByteSource;

/**
 * PepperHashService
 */
public class PepperHashService extends DefaultHashService {

	private final SimpleByteSource pepper;

	private final String algorithmName;

	private final Map<String, Object> hashParameters;

	public PepperHashService(final String algorithmName, final String pepper, final Map<String, Object> hashParameters) {

		super();
		this.algorithmName = algorithmName;
		this.pepper = new SimpleByteSource(pepper);
		this.hashParameters = hashParameters;
	}

	@Override
	public Hash computeHash(final HashRequest request) {

		ByteSource userSalt = request.getSalt().get();
		byte[] pepperBytes = pepper.getBytes();
		byte[] combined = new byte[pepperBytes.length + userSalt.getBytes().length];

		System.arraycopy(pepperBytes, 0, combined, 0, pepperBytes.length);
		System.arraycopy(userSalt.getBytes(), 0, combined, pepperBytes.length, userSalt.getBytes().length);
		userSalt = new SimpleByteSource(combined);

		HashRequest mergedRequest = new SimpleHashRequest(algorithmName, request.getSource(), userSalt, hashParameters);

		return super.computeHash(mergedRequest);
	}

	/**
	 * @return
	 */
	public String getHashAlgorithmName() {

		return this.algorithmName;
	}
}
