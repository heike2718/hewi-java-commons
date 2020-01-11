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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.DeutscherName;

/**
 * KuerzelValidatorTest
 */
public class DeutscherNameValidatorTest {

	private static final Logger LOG = LoggerFactory.getLogger(DeutscherNameValidatorTest.class);

	private static final String INVALID_CHARS = "#$%*+/:;<=>?[\\\\]^{|}~'`'!";

	private static final String VALID_CHARS = " ,äöüßÄÖÜabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-.\"&@()_";

	private Validator validator;

	private class TestObject {

		@DeutscherName
		private final String value;

		/**
		 * Erzeugt eine Instanz von TestObject
		 */
		public TestObject(final String value) {

			super();
			this.value = value;
		}
	}

	@BeforeEach
	public void setUp() {

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	@DisplayName("passes when value null")
	public void validate1() {

		// Arrange
		final TestObject testObject = new TestObject(null);

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

		for (int i = 0; i < 101; i++) {

			wert += "A";
		}
		assertEquals("Testsetting falsch - brauchen 101 Zeichen", 101, wert.length());

		final TestObject testObject = new TestObject(wert);

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

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when value leerzeichen")
	public void validate4() {

		// Arrange
		final TestObject testObject = new TestObject(" ");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when value valid")
	public void validate5() {
		// Arrange

		for (final char c : VALID_CHARS.toCharArray()) {

			TestObject testObject = null;
			testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertTrue("Fehler bei [" + c + "]", errors.isEmpty());
		}

	}

	@Test
	@DisplayName("fails when value invalid")
	public void validate6() {

		// Arrange
		final int expectedNumber = 1;

		for (final char c : INVALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertFalse("Fehler bei [" + c + "]", errors.isEmpty());
			assertEquals("Fehler bei ['" + c + "']", expectedNumber, errors.size());

			final ConstraintViolation<TestObject> cv = errors.iterator().next();
			LOG.debug(cv.getMessage());
			assertEquals("value", cv.getPropertyPath().toString());
		}
	}

	@Test
	@DisplayName("fails when value uri")
	public void validate7() {

		// Arrange
		final TestObject testObject = new TestObject("http://www.evil-url.com/malware.php");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}
}
