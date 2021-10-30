// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.exceptions;

/**
 * OfficeToolsRuntimeException
 */
public class OfficeToolsRuntimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OfficeToolsRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	public OfficeToolsRuntimeException(final String message) {

		super(message);

	}

}
