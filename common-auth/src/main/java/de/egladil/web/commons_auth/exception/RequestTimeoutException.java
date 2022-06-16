// =====================================================
// Project: commons
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_auth.exception;

/**
 * RequestTimeoutException
 */
public class RequestTimeoutException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public RequestTimeoutException() {

	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestTimeoutException(final String message, final Throwable cause) {

		super(message, cause);
	}

	/**
	 * @param message
	 */
	public RequestTimeoutException(final String message) {

		super(message);
	}

}
