// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import de.egladil.web.commons_validation.annotations.LocalDateString;

/**
 * LocalDateStringValidator
 */
public class LocalDateStringValidator implements ConstraintValidator<LocalDateString, String> {

	private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		if (value == null) {

			return true;
		}

		try {

			TemporalAccessor ld = FORMATTER.parse(value);

			String reverse = FORMATTER.format(ld);

			return reverse.equals(value);

		} catch (DateTimeParseException e) {

			return false;
		}
	}

}
