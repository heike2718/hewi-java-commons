// =====================================================
// Project: commons-auth
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_auth.exception;

/**
 * AuthRuntimeException
 */
public class AuthRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -816300796100692562L;

	/**
	 * @param message
	 * @param cause
	 */
	public AuthRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	/**
	 * @param message
	 */
	public AuthRuntimeException(final String message) {

		super(message);

	}

}
