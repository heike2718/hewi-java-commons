// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager.exception;

/**
 * MessagerException
 */
public class MessagerException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public MessagerException(final String message) {

		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessagerException(final String message, final Throwable cause) {

		super(message, cause);

	}

}
