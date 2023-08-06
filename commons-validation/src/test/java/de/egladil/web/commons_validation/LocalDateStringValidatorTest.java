// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_validation.annotations.LocalDateString;

/**
 * LocalDateStringValidatorTest
 */
public class LocalDateStringValidatorTest extends AbstractValidatorTest {

	private class TestObject {

		@LocalDateString
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
	void should_BeValid_when_Null() {

		// Arrange
		final TestObject testObject = new TestObject(null);

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	void should_NotBeValid_when_Blank() {

		// Arrange
		final TestObject testObject = new TestObject("");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());
		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		assertEquals("value", cv.getPropertyPath().toString());
		assertEquals("{de.egladil.constraints.invalidDate}", cv.getMessageTemplate());

	}

	@Test
	void should_BeValid_when_ValidDate() {

		// Arrange
		final TestObject testObject = new TestObject("29.02.2020");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	void should_NotBeValid_when_InvalidDate() {

		// Arrange
		final TestObject testObject = new TestObject("30.02.2020");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());
		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		assertEquals("value", cv.getPropertyPath().toString());
		assertEquals("{de.egladil.constraints.invalidDate}", cv.getMessageTemplate());

	}
}
