// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.LandKuerzel;

/**
 * LandkuerzelValidatorTest
 */
public class LandkuerzelValidatorTest extends AbstractValidatorTest {

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

	@BeforeEach
	public void setUp() {

		super.setUp();
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

		for (int i = 0; i < 9; i++) {

			wert += "A";
		}
		assertEquals(9, wert.length());

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
	@DisplayName("passes when value valid")
	public void validate4() {

		for (final char c : VALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject("" + c);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertTrue(errors.isEmpty(), "Fehler bei [" + c + "]");
		}

	}

	@Test
	@DisplayName("fails when value invald")
	public void validate5() {

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

}
