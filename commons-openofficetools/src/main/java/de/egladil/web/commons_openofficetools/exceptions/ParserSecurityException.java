// =====================================================
// Projekt: de.egladil.tools.parser
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_openofficetools.exceptions;

/**
 * ParserSecurityException
 */
public class ParserSecurityException extends RuntimeException {

	/* serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * ParserSecurityException
	 */
	public ParserSecurityException(final String message) {

		super(message);
	}

	/**
	 * ParserSecurityException
	 */
	public ParserSecurityException(final Throwable cause) {

		super(cause);
	}

	/**
	 * ParserSecurityException
	 */
	public ParserSecurityException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
