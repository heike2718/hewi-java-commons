// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.exception.InvalidInputException;
import de.egladil.web.commons_validation.payload.MessagePayload;
import de.egladil.web.commons_validation.payload.ResponsePayload;

/**
 * ValidationDelegate für Validierung von Ein- und Ausgabe-Objekten.
 */
public class ValidationDelegate {

	private static final Logger LOG = LoggerFactory.getLogger(ValidationDelegate.class);

	private final Validator validator;

	/**
	 * Erzeugt eine Instanz von ValidationDelegate
	 */
	public ValidationDelegate() {

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	/**
	 * Whitelist-Validation des gegebenen values.
	 *
	 * @param value
	 *                  String darf nicht blank sein
	 * @param validator
	 *                  AbstractWhitelistValidator
	 * @param maxLength
	 *                  int - 0 bedeutet keine Längenprüfung.
	 */
	public <T extends Annotation> void validate(final String value, final AbstractWhitelistValidator<T, String> validator, final int maxLength) throws IllegalArgumentException, InvalidInputException {

		boolean valid = true;
		String msg = null;

		if (StringUtils.isBlank(value)) {

			valid = false;
			msg = "darf nicht leer sein";
		} else if (maxLength > 0 && value.length() > maxLength) {

			valid = false;
			msg = "darf nicht länger als " + maxLength + " Zeichen sein";
		} else {

			Matcher matcher = validator.getPattern().matcher(value);
			valid = matcher.matches();
			msg = "enthält unerlaubte Zeichen";
		}

		if (!valid) {

			ResponsePayload payload = new ResponsePayload(MessagePayload.error("Die Eingaben sind nicht korrekt."),
				Arrays.asList(new InvalidProperty[] { new InvalidProperty("CrossValidation", msg, 0) }));
			throw new InvalidInputException(payload);
		}
	}

	/**
	 * @param  payload
	 * @param  clazz
	 * @throws InvalidInputException:
	 *                                400-BAD REQUEST
	 */
	public <T> void check(final T payload, final Class<T> clazz) {

		if (payload == null) {

			LOG.error("Parameter payload darf nicht null sein!");
			throw new InvalidInputException(new ResponsePayload(MessagePayload.error("payload null"), payload));
		}
		final Set<ConstraintViolation<T>> errors = validator.validate(payload);
		handleValidationErrorsWithKleber(payload, errors, clazz);
	}

	/**
	 * @param  loggableObject
	 * @param  errors
	 * @throws IllegalArgumentException
	 * @throws InvalidInputException
	 */
	private <T> void handleValidationErrorsWithKleber(final T loggableObject, final Set<ConstraintViolation<T>> errors, final Class<T> clazz) throws IllegalArgumentException {

		if (!errors.isEmpty()) {

			final ValidationUtils validationUtils = new ValidationUtils();
			ResponsePayload responsePayload = validationUtils.toConstraintViolationMessage(errors, clazz);

			if (!responsePayload.isOk()) {

				LOG.warn("{}: {}", responsePayload.getMessage().getMessage(), responsePayload.getData().toString());
			}

			throw new InvalidInputException(responsePayload);
		}
	}
}
