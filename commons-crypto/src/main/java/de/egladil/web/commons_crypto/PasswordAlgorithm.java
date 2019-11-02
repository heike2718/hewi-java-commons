// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;

/**
 * PasswordAlgorithm
 */
public interface PasswordAlgorithm {

	/**
	 * Prüft das gegebene Passwort gegen das persistierte Passwort.
	 *
	 * @param  password
	 *                             char[] der Aufrufer ist verantwortlich für das Löschen des char[]
	 * @param  persistentHashValue
	 *                             String der Base64-encodete Passworthash aus der DB.
	 * @param  persistentSalt
	 *                             String das Base64-encodete Salt aus der DB
	 * @return                     boolean
	 */
	boolean verifyPassword(char[] password, String persistentHashValue, String persistentSalt);

	/**
	 * Berechnet einen PasswordHash.
	 *
	 * @param  password
	 *                  der Aufrufer ist verantwortlich für das Löschen des char[]
	 * @param  salt
	 *                  ByteSource das Salz
	 * @return          Hash
	 */
	Hash hashPassword(final char[] password, final ByteSource salt);
}
