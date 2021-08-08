// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.error;

/**
 * ExceltoolsRuntimeException
 */
public class ExceltoolsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExceltoolsRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	public ExceltoolsRuntimeException(final String message) {

		super(message);

	}

}
