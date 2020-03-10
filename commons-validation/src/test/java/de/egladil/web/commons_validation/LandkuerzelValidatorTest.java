// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.LandKuerzel;

/**
 * LandkuerzelValidatorTest
 */
public class LandkuerzelValidatorTest {

	private static final Logger LOG = LoggerFactory.getLogger(LandkuerzelValidatorTest.class);

	private static final String INVALID_CHARS = "!\"#$%&()*+/:;<=>?@[\\]^{|}~@ _.'`'abcdefghijklmnopqrstuvwxyzäöüßÄÖÜ0123456789";

	// Leerzeichen, Minus, Unterstrich, Punkt, Komma, Apostrophe
	private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-,";

	private class TestObject {

		@LandKuerzel
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
	@DisplayName("passes when value too long")
	public void validate2() {

		// Arrange
		String wert = "";

		for (int i = 0; i < 9; i++) {

			wert += "A";
		}
		assertEquals("Testsetting falsch - brauchen 9 Zeichen", 9, wert.length());

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

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertTrue("Fehler bei [" + c + "]", errors.isEmpty());
		}

	}

	@Test
	@DisplayName("fails when value invald")
	public void validate5() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		for (final char c : INVALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertFalse("Fehler bei [" + c + "]", errors.isEmpty());
			assertEquals(1, errors.size());

			final ConstraintViolation<TestObject> cv = errors.iterator().next();
			LOG.debug(cv.getMessage());
			assertEquals("value", cv.getPropertyPath().toString());
		}

	}

}
