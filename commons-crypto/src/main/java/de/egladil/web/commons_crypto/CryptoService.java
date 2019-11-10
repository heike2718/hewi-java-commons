// =====================================================
// Projekt: commons-crypto
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_crypto;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;

/**
 * CryptoService
 */
public interface CryptoService {

	/**
	 * Berechnet einen Hash.<br>
	 * <br>
	 * Den PasswordAlgorithm erhält man mittels PasswordAlgorithmBuilder.
	 *
	 * @param  algorithm
	 *                   PasswordAlgorithm
	 * @param  password
	 *                   char[] Der Aufrufer muss ihn nach dem Aufruf nullen.
	 * @param  salt
	 *                   ByteSource
	 * @return           Hash
	 */
	Hash hashPassword(PasswordAlgorithm algorithm, char[] password, ByteSource salt);

	/**
	 * Prüft das gegebene Passwort gegen das persistierte Passwort.<br>
	 * <br>
	 * Den PasswordAlgorithm erhält man mittels PasswordAlgorithmBuilder.
	 *
	 * @param  algorithm
	 *                             PasswordAlgorithm
	 * @param  password
	 *                             char[] der Aufrufer ist verantwortlich für das Löschen des char[]
	 * @param  persistentHashValue
	 *                             String der Base64-encodete Passworthash aus der DB.
	 * @param  persistentSalt
	 *                             String das Base64-encodete Salt aus der DB
	 * @return                     boolean
	 */
	boolean verifyPassword(PasswordAlgorithm algorithm, char[] password, String persistentHashValue, String persistentSalt);

	/**
	 * Generiert einen Zufallsstring gegeben Länge mit den Zeichen aus charPool. Basiert auf Random.
	 *
	 * @param  laenge
	 *                  int die Länge. Muss mindestens gleich 6 sein.
	 * @param  charPool
	 *                  die verwendeten Zeichen. Muss Mindestlänge 26 haben.
	 * @return          String
	 */
	String generateKuerzel(final int length, final char[] charPool);

	/**
	 * Generiert einen Zufallsstring der geforderten Länge aus der gegebenen Zeichenmenge.
	 *
	 * @param  algorithm
	 *                   der Name des Algorithmus, mit dem SecureRandom initialisiert wird.
	 * @param  length
	 *                   int
	 * @param  charPool
	 *                   char[]
	 * @return
	 */
	String generateRandomString(String algorithm, int length, char[] charPool);

	/**
	 * Base64-encodet die most significant bytes einer UUID. Das Ergebnis ist 10 oder 11 Zeichen lang und kann z.B. als
	 * Einmalpasswort verwendet werden.
	 *
	 * @return String.
	 */
	String generateShortUuid();

	/**
	 * Generiert ein Salz der gegebenen Länge. Zu Testzwecken nicht private.
	 *
	 * @param  saltLengthBits
	 *                        int ist in Bits, d.h. Ergebnis ist 1/8 so lang.
	 * @return
	 */
	char[] generateSalt(final int saltLengthBits);

	/**
	 * Erzeugt eine SessionID
	 *
	 * @return      String
	 */
	String generateSessionId();

}
