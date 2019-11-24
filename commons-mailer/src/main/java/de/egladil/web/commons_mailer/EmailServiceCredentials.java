// =====================================================
// Projekt: commons-mailer
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_mailer;

import java.util.Map;

/**
 * EmailServiceCredentials sind die Credentials beim Mailprovider.
 */
public class EmailServiceCredentials {

	public static final String KEY_HOST = "host";

	public static final String KEY_PORT = "port";

	public static final String KEY_USER = "user";

	public static final String KEY_PASSWORD = "password";

	private String host;

	private int port;

	private String user;

	private char[] password;

	private String fromAddress;

	/**
	 * Erzeugt eine neue Instanz mit den gegebenen Parametern.
	 *
	 * @param  host
	 *                     String der host des Mailproviders
	 * @param  port
	 *                     int der Port des Mailproviders
	 * @param  username
	 *                     String der Username zur Authentisierung beim Mailprovider
	 * @param  password
	 *                     char[] das Passwotz zur Authentisierung beim Mailprovider
	 * @param  fromAddress
	 *                     die Email-Adresse des Senders der Mail.
	 * @return             EmailServiceCredentials
	 */
	public static EmailServiceCredentials createInstance(final String host, final int port, final String username, final char[] password, final String fromAddress) {

		EmailServiceCredentials result = new EmailServiceCredentials();
		result.fromAddress = fromAddress;
		result.host = host;
		result.password = password;
		result.port = port;
		result.user = username;
		return result;
	}

	/**
	 * Erzeugt mit Hilfe der Map eine Instanz von EmailServiceCredentials. Die keys sind dabei die hier definierten public static
	 * final Strings.
	 *
	 * @param  properties
	 *                     Map
	 * @param  fromAddress
	 *                     String
	 * @return             EmailServiceCredentials
	 */
	public static EmailServiceCredentials createInstance(final Map<String, String> properties, final String fromAddress) {

		if (properties.get(KEY_HOST) == null) {

			throw new IllegalArgumentException("entry with key '" + KEY_HOST + "' missing");
		}

		if (properties.get(KEY_PASSWORD) == null) {

			throw new IllegalArgumentException("entry with key '" + KEY_PASSWORD + "' missing");
		}

		if (properties.get(KEY_PORT) == null) {

			throw new IllegalArgumentException("entry with key '" + KEY_PORT + "' missing");
		}

		if (properties.get(KEY_USER) == null) {

			throw new IllegalArgumentException("entry with key '" + KEY_USER + "' missing");
		}

		EmailServiceCredentials result = new EmailServiceCredentials();
		result.fromAddress = fromAddress;
		result.host = properties.get(KEY_HOST);
		result.password = properties.get(KEY_PASSWORD).toCharArray();
		result.port = Integer.valueOf(properties.get(KEY_PORT));
		result.user = properties.get(KEY_USER);
		return result;
	}

	/**
	 *
	 */
	private EmailServiceCredentials() {

	}

	public String getHost() {

		return host;
	}

	public int getPort() {

		return port;
	}

	public String getUser() {

		return user;
	}

	public char[] getPassword() {

		return password;
	}

	public String getFromAddress() {

		return fromAddress;
	}

	@Override
	public String toString() {

		return "EmailServiceCredentials [host=" + host + ", port=" + port + ", user=" + user + ", fromAddress=" + fromAddress + "]";
	}

}
