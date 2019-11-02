//=====================================================
// Projekt: authenticationprovider
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.commons_net;

/**
 * CommonTestUtils
 */
public final class CommonTestUtils {

	/**
	 * Erzeugt eine Instanz von CommonTestUtils
	 */
	private CommonTestUtils() {
	}

	/**
	 * Gibt dem Pfad zum Dev-Config-Verzeichnis zurück.<br>
	 * <br>
	 * <b>Achtung: </b>Nur in Tests verwenden!!!!
	 *
	 * @return
	 */
	public static String getDevConfigRoot() {
		final String osName = System.getProperty("os.name");

		if (osName.contains("Windows")) {
			return "kein_verzeichnis";
		}
		return "/home/heike/git/konfigurationen/authprovider";
	}
}
