// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security.zip.exception;

/**
 * SecurityRuntimeException
 */
public class SecurityRuntimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public SecurityRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	/**
	 * @param message
	 */
	public SecurityRuntimeException(final String message) {

		super(message);

	}

}
