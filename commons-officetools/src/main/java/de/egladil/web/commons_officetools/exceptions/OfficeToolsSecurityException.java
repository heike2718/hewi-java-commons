// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.exceptions;

/**
 * OfficeToolsSecurityException
 */
public class OfficeToolsSecurityException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OfficeToolsSecurityException(final String message, final Throwable cause) {

		super(message, cause);

	}

	public OfficeToolsSecurityException(final String message) {

		super(message);

	}

}
