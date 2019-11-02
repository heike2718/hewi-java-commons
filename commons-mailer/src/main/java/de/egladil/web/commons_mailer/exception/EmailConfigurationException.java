// =====================================================
// Project: commons-mailer
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_mailer.exception;

/**
 * EmailConfigurationException
 */
public class EmailConfigurationException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public EmailConfigurationException(final String message) {

		super(message);
	}

}
