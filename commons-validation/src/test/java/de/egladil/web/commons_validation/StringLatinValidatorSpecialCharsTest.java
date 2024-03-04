// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.StringLatin;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * StringLatinValidatorSpecialCharsTest
 */
public class StringLatinValidatorSpecialCharsTest {

	private static final Logger LOG = LoggerFactory.getLogger(StringLatinValidatorSpecialCharsTest.class);

	private static final String INVALID_CHARS = "";

	// Leerzeichen, Minus, Unterstrich, Punkt, Komma, Apostrophe, at
	private static final String VALID_CHARS = "\"@- _.,'`'‘+:;=()[]{}!#$%&*?\\^|~°$%§";

	private class TestObject {

		@StringLatin
		private final String value;

		/**
		 * Erzeugt eine Instanz von TestObject
		 */
		public TestObject(final String value) {

			super();
			this.value = value;
		}
	}

	@Test
	@DisplayName("passes when value null")
	public void validate1() {

		// Arrange
		final TestObject testObject = new TestObject(null);

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when value blank")
	public void validate2() {

		// Arrange
		final TestObject testObject = new TestObject("");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when email")
	public void validateEmail() {

		// Arrange
		final TestObject testObject = new TestObject("public@egladil.de");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when value valid")
	public void validate3() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		for (final char c : VALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertTrue(errors.isEmpty(), "Fehler bei [" + c + "]");
		}

	}

	@Test
	@DisplayName("fails when value invalid")
	public void validate4() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		for (final char c : INVALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertFalse(errors.isEmpty(), "Fehler bei [" + c + "]");
			assertEquals(1, errors.size());

			final ConstraintViolation<TestObject> cv = errors.iterator().next();
			LOG.debug(cv.getMessage());
			assertEquals("value", cv.getPropertyPath().toString());
		}

	}

	@Test
	@DisplayName("fails when value is uri")
	public void validate5() {

		// Arrange
		final TestObject testObject = new TestObject("http://www.evil-url.com/malware.php");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("passes wenn name mit diakritischem Zeichen")
	public void validateName() {

		final TestObject testObject = new TestObject("Noée");
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());

	}
}
