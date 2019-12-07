// =====================================================
// Projekt: de.egladil.common.validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.LoginName;

/**
 * LoginNameValidatorTest
 */
public class LoginNameValidatorTest {

	private static final Logger LOG = LoggerFactory.getLogger(LoginNameValidatorTest.class);

	/*
	 * Unterstrich, Leerzeichen, Minus, Punkt, Ausrufezeichen, Semikolon, Komma, '@'
	 */

	private static final String INVALID_CHARS = "\"#$%&()*+/:<=>?[\\\\]^{|}~'`'";

	private static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZäöüßÄÖÜß0123456789_ -.!;,@";

	private class TestObject {

		@LoginName
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
	@DisplayName("invalidChars and validChars are disjunct")
	public void checkSetup() {

		Set<String> validChars = new HashSet<>();

		for (char c : VALID_CHARS.toCharArray()) {

			validChars.add(String.valueOf(c));
		}

		Set<String> invalidChars = new HashSet<>();

		for (char c : INVALID_CHARS.toCharArray()) {

			invalidChars.add(String.valueOf(c));
		}

		assertFalse(validChars.containsAll(invalidChars));
		assertFalse(invalidChars.containsAll(validChars));

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
	@DisplayName("passes when value too long")
	public void validate2() {

		// Arrange
		String wert = "";

		for (int i = 0; i < 256; i++) {

			wert += "A";
		}
		assertEquals("Testsetting falsch - brauchen 256 Zeichen", 256, wert.length());

		final TestObject testObject = new TestObject(wert);

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when value blank")
	public void validate3() {

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
	@DisplayName("passes when value valid")
	public void validate4() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		for (final char c : VALID_CHARS.toCharArray()) {

			TestObject testObject = null;

			if (' ' == c) {

				testObject = new TestObject("A");
			} else {

				testObject = new TestObject("" + c);
			}

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertTrue("Fehler bei [" + c + "]", errors.isEmpty());
		}

	}

	@Test
	@DisplayName("fails when value invalid")
	public void validate5() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();
		final int expectedNumber = 1;

		for (final char c : INVALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertFalse("Fehler bei [" + c + "]", errors.isEmpty());
			assertEquals("Fehler bei ['" + c + "']", expectedNumber, errors.size());

			final ConstraintViolation<TestObject> cv = errors.iterator().next();
			LOG.error(cv.getMessage());
			assertEquals("value", cv.getPropertyPath().toString());
		}

	}

}
