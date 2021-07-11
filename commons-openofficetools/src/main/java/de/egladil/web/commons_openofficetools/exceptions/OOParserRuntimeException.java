// =====================================================
// Project: commons-openofficetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_openofficetools.exceptions;

/**
 * OOParserRuntimeException
 */
public class OOParserRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OOParserRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	public OOParserRuntimeException(final String message) {

		super(message);

	}

}
