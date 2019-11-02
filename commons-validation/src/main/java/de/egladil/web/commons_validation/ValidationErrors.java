// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import java.util.Collection;

/**
 * ValidationErrors
 */
public class ValidationErrors {

	private Collection<InvalidProperty> invalidProperties;

	private String crossValidationError;

	/**
	 * Erzeugt eine Instanz von ValidationErrors
	 */
	public ValidationErrors() {

	}

	/**
	 * Erzeugt eine Instanz von ValidationErrors
	 */
	public ValidationErrors(final Collection<InvalidProperty> invalidProperties, final String crossValidationError) {

		super();
		this.invalidProperties = invalidProperties;
		this.crossValidationError = crossValidationError;
	}

	public Collection<InvalidProperty> getInvalidProperties() {

		return invalidProperties;
	}

	public String getCrossValidationError() {

		return crossValidationError;
	}

	void setInvalidProperties(final Collection<InvalidProperty> invalidProperties) {

		this.invalidProperties = invalidProperties;
	}

	void setCrossValidationError(final String crossValidationError) {

		this.crossValidationError = crossValidationError;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("ValidationErrors [invalidProperties=");
		builder.append(invalidProperties);
		builder.append(", crossValidationError=");
		builder.append(crossValidationError);
		builder.append("]");
		return builder.toString();
	}

}
