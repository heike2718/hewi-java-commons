// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import de.egladil.web.commons_messager.exception.MessagerConfigurationException;
import de.egladil.web.commons_messager.exception.MessagerException;
import de.egladil.web.commons_messager.telegram.TelegramMessager;

/**
 * Messager
 */
public interface Messager {

	/**
	 * Sendet eine Message mit dem gegebenen MessageText.
	 *
	 * @param messageText
	 *                      String der zu sendende Text
	 * @param configuration
	 *                      Map die Konfiguration, die die Implementierung benötigt.
	 */
	void sendMessage(String messageText, Map<String, String> configuration) throws MessagerException;

	/**
	 * Gibt die Beschreibung der spezifischen Konfiguration zurück.
	 *
	 * @return
	 */
	ConfigurationDescription getConfigurationDescription();

	/**
	 * Gibt alle key der Konfiguration zurück.
	 *
	 * @return List
	 */
	List<String> getAllConfgigurationKeys();

	/**
	 * Gibt die Konfiguration für das Senden einer Nachricht zurück.
	 *
	 * @param  secretProperties
	 * @return
	 */
	Map<String, String> buildConfiguration(Properties secretProperties) throws MessagerConfigurationException, MessagerException;

	/**
	 * @return MessagerType
	 */
	MessagerType getType();

	/**
	 * @param  type
	 * @return
	 */
	static Messager createMessageSenderOfType(final MessagerType type) {

		if (type == null) {

			throw new NullPointerException("MessagerType type");
		}

		switch (type) {

		case TELEGRAM:
			return new TelegramMessager();

		default:
			break;
		}

		throw new IllegalArgumentException("keine Implementierung fuer MessagerType " + type + " vorhanden");
	}

}
