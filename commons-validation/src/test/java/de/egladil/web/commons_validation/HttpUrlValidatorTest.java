// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.HttpUrl;

/**
 * HttpUrlValidatorTest
 */
public class HttpUrlValidatorTest extends AbstractValidatorTest {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUrlValidatorTest.class);

	private class TestObject {

		@HttpUrl
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
	@DisplayName("passes when value blank")
	public void validate2() {

		// Arrange
		final TestObject testObject = new TestObject(" ");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when valid protokoll https")
	public void validate3() {

		// Arrange
		final TestObject testObject = new TestObject("https://mathe-jung-alt.de");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());

	}

	@Test
	@DisplayName("passes when valid protokoll http")
	public void validate4() {

		// Arrange
		final TestObject testObject = new TestObject("http://www.egladil.de");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());

	}

	@Test
	@DisplayName("fails when value without protokoll")
	public void validate5() {

		// Arrange
		final TestObject testObject = new TestObject("www.egladil.de");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());

	}

	@Test
	@DisplayName("passes when value without www")
	public void validate6() {

		// Arrange
		final TestObject testObject = new TestObject("http://mathe-jung-alt.de");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());

	}

	@Test
	@DisplayName("fails when verschachtelte Protokolle file inside http")
	public void validate7() {

		// Arrange
		final TestObject testObject = new TestObject("http://file://hulahula/");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when verschachtelte Protokolle file inside http inside file")
	public void validate15() {

		// Arrange
		final TestObject testObject = new TestObject("file://http://file://hulahula/");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when verschachtelte Protokolle http inside file")
	public void validate8() {

		// Arrange
		final TestObject testObject = new TestObject("file://http://hulahula/");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when verschachtelte Protokolle file inside file")
	public void validate9() {

		// Arrange
		final TestObject testObject = new TestObject("file://file://hulahula/");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when verschachtelte Protokolle http inside http")
	public void vaildate10() {

		// Arrange
		final TestObject testObject = new TestObject("http://http://hulahula/");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when mailadresse ohne domain")
	public void validate11() {

		// Arrange
		final TestObject testObject = new TestObject("bla@blubb");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when falsches protokoll")
	public void validate12() {

		// Arrange
		final TestObject testObject = new TestObject("horst://hulahula/");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when nur http")
	public void validate13() {

		// Arrange
		final TestObject testObject = new TestObject("http://");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when nur https")
	public void validate14() {

		// Arrange
		final TestObject testObject = new TestObject("https://");

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}
}
