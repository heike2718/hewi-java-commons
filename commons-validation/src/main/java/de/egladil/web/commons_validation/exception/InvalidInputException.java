// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation.exception;

import de.egladil.web.commons_validation.payload.ResponsePayload;

/**
 * InvalidInputException
 */
public class InvalidInputException extends RuntimeException {

	/* serialVersionUID */
	private static final long serialVersionUID = 1L;

	private final ResponsePayload responsePayload;

	/**
	 * Erzeugt eine Instanz von EmailException
	 */
	public InvalidInputException(final ResponsePayload payload) {

		this.responsePayload = payload;
	}

	public ResponsePayload getResponsePayload() {

		return responsePayload;
	}

}
