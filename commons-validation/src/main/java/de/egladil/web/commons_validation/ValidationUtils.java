// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.payload.MessagePayload;
import de.egladil.web.commons_validation.payload.ResponsePayload;

/**
 * ValidationUtils
 */
public class ValidationUtils {

	private static final Logger LOG = LoggerFactory.getLogger(ValidationUtils.class);

	/**
	 * Validation-Messages in verarbeitbarer Form extrahieren.
	 *
	 * @param  errors
	 * @param  bean
	 * @return
	 * @throws IllegalArgumentException
	 */
	private <T> Map<String, String> extractPropertiesAndMessages(final Set<ConstraintViolation<T>> errors, final Class<T> clazz) throws IllegalArgumentException {

		if (errors == null) {

			throw new IllegalArgumentException("errors darf nicht null sein");
		}

		if (clazz == null) {

			throw new IllegalArgumentException("clazz darf nicht null sein");
		}
		Set<String> fieldNames = fieldNames(clazz);
		Map<String, String> result = new HashMap<>();
		Iterator<ConstraintViolation<T>> iter = errors.iterator();

		while (iter.hasNext()) {

			ConstraintViolation<T> cv = iter.next();
			Path path = cv.getPropertyPath();
			String propName = path.toString();

			if ("kleber".equals(propName)) {

				LOG.warn("Possible BOT-Attac: {}={}", propName, cv.getInvalidValue().toString());
			}

			if (fieldNames.contains(propName)) {

				String message = result.get(propName);

				if (message == null) {

					message = cv.getMessage();
				} else {

					message += ", " + cv.getMessage();
				}
				result.put(propName, message);
			}
		}
		return result;
	}

	public <T> ResponsePayload toConstraintViolationMessage(final Set<ConstraintViolation<T>> errors, final Class<T> clazz) {

		Map<String, String> messages = this.extractPropertiesAndMessages(errors, clazz);

		Set<InvalidProperty> invalidProperties = new HashSet<>();

		if (!messages.isEmpty()) {

			int sortnr = 0;

			for (String key : messages.keySet()) {

				final String message = messages.get(key);
				StringUtils.join(Arrays.stream(StringUtils.split(message, ',')).collect(Collectors.toSet()), ',');
				InvalidProperty prop = new InvalidProperty(key,
					StringUtils.join(Arrays.stream(StringUtils.split(message, ',')).collect(Collectors.toSet()), ','), sortnr);
				invalidProperties.add(prop);
				sortnr++;
			}
		}
		String crossValidation = extractCrossValidationMessage(errors);

		if (StringUtils.isNotBlank(crossValidation)) {

			invalidProperties.add(new InvalidProperty("CrossValidation", crossValidation, invalidProperties.size() + 1));
		}

		List<InvalidProperty> data = new ArrayList<>(invalidProperties);
		Collections.sort(data, new InvalidPropertiesComparator());
		ResponsePayload result = new ResponsePayload(MessagePayload.error("Die Eingaben sind nicht korrekt."), data);
		return result;
	}

	<T> String extractCrossValidationMessage(final Set<ConstraintViolation<T>> errors) {

		Iterator<ConstraintViolation<T>> iter = errors.iterator();
		Set<String> alle = new HashSet<>();

		while (iter.hasNext()) {

			final ConstraintViolation<T> cv = iter.next();

			if (StringUtils.isBlank(cv.getPropertyPath().toString())) {

				if (isMostDescriptive(cv)) {

					alle.add(cv.getMessage());
				}
			}
		}
		return StringUtils.join(alle, ',');
	}

	<T> boolean isMostDescriptive(final ConstraintViolation<T> cv) {

		return cv.getMessage().equals(cv.getMessageTemplate());
	}

	@SuppressWarnings("rawtypes")
	private <T> Set<String> fieldNames(final Class<T> clazz) {

		Set<String> result = new HashSet<>();
		Class superClazz = clazz;

		while (superClazz != null) {

			Field[] fields = superClazz.getDeclaredFields();

			for (Field f : fields) {

				result.add(f.getName());
			}
			superClazz = superClazz.getSuperclass();
		}
		return result;
	}
}
