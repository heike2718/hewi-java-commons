// =====================================================
// Projekt: de.egladil.tools.parser
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_openofficetools.exceptions;

/**
 * OOParserSecurityException
 */
public class OOParserSecurityException extends RuntimeException {

	/* serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * OOParserSecurityException
	 */
	public OOParserSecurityException(final String message) {

		super(message);
	}

	/**
	 * OOParserSecurityException
	 */
	public OOParserSecurityException(final Throwable cause) {

		super(cause);
	}

	/**
	 * OOParserSecurityException
	 */
	public OOParserSecurityException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
