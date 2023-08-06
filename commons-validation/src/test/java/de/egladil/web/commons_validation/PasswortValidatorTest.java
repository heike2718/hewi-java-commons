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
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.annotations.Passwort;

/**
 * PasswortValidatorTest
 */
public class PasswortValidatorTest {

	private static final Logger LOG = LoggerFactory.getLogger(PasswortValidatorTest.class);

	private static final String INVALID_CHARS = "ф";

	// !\"#$%&)(*+,-./:;<=>?@][^_'`'{|}~
	private static final String VALID_CHARS = " ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜabcdefghijklmnopqrstuvwxyzäöü0123456789!\"#$%&)(*+,-./:;<=>?@][^_'`'{|}~\\ ";
	// private static final String VALID_CHARS = "start12 ";

	private class TestObject {

		@Passwort
		private String value;

		/**
		 * Erzeugt eine Instanz von TestObject
		 */
		public TestObject() {
			// TODO Generierter Code

		}

		/**
		 * Erzeugt eine Instanz von TestObject
		 */
		public TestObject(final String value) {

			this.value = value;
		}

		/**
		 * Setzt die Membervariable
		 *
		 * @param value
		 *              neuer Wert der Membervariablen value
		 */
		public void setValue(final String value) {

			this.value = value;
		}

		/**
		 * Liefert die Membervariable value
		 *
		 * @return die Membervariable value
		 */
		protected String getValue() {

			return value;
		}
	}

	@Test
	@DisplayName("passes when value valid")
	public void validate1() {

		// Arrange
		final TestObject credentials = new TestObject();
		credentials.setValue("Qwertz!2");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("fails when value blank")
	public void validate2() {

		// Arrange
		final TestObject credentials = new TestObject();
		credentials.setValue("        ");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("passes when value null")
	public void validate3() {

		// Arrange
		final TestObject credentials = new TestObject();
		credentials.setValue(null);

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("fails when value too long")
	public void validate4() {

		// Arrange
		LOG.info("Testen Passwort mit Länge 101");
		final TestObject credentials = new TestObject();
		credentials
			.setValue("!\\\"#$%&'()*+,-./:;<3Äüh Seite 7 als Passwort ein Satz aus einem Buch Seite 7 als Passwort hehzie nddd");
		System.out.println("Länge = " + credentials.getValue().length());
		assertEquals(101, credentials.getValue().length());

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when password 7 zeichen")
	public void validate5() {

		// Arrange
		LOG.info("Testen Passwort mit Länge 7");
		final TestObject credentials = new TestObject();
		credentials.setValue("=>?@eR5");
		assertEquals(7, credentials.getValue().length());

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when 7 zeichen anderer String")
	public void validate6() {

		// Arrange
		LOG.info("Testen Passwort mit Länge 7");
		final TestObject credentials = new TestObject();
		credentials.setValue("7gD@[\\]");
		assertEquals(7, credentials.getValue().length());

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when führendes Leerzeichen")
	public void validate14() {

		// Arrange
		LOG.info("Testen Passwort mit führendem Leerzeichen");
		final TestObject credentials = new TestObject();
		credentials.setValue(" jetzt 6 hallo");
		assertEquals(14, credentials.getValue().length());
		assertEquals(13, credentials.getValue().trim().length());

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when endendes Leerzeichen")
	public void validate15() {

		// Arrange
		LOG.info("Testen Passwort mit endendem Leerzeichen");
		final TestObject credentials = new TestObject();
		credentials.setValue("jetzt 6 hallo ");
		assertEquals(14, credentials.getValue().length());
		assertEquals(13, credentials.getValue().trim().length());

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("passes when value without uppercase")
	public void validate7() {

		// Arrange
		LOG.info("Testen Passwort ohne Großbuchstaben");
		final TestObject credentials = new TestObject();
		credentials.setValue("7gd@[\\]!");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when value without lowercase")
	public void validate8() {

		// Arrange
		LOG.info("Testen Passwort ohne Kleinbuchstaben");
		final TestObject credentials = new TestObject();
		credentials.setValue("7GD@[\\]!");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("passes when contains space")
	public void validate13() {

		// Arrange
		LOG.info("Testen Passwort mit Leerzeichen");
		final TestObject credentials = new TestObject();
		credentials.setValue("7gD @[\\]!");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertTrue(errors.isEmpty());
	}

	@Test
	@DisplayName("fails when value without digit")
	public void validate9() {

		// Arrange
		LOG.info("Testen Passwort ohne Ziffer");
		final TestObject credentials = new TestObject();
		credentials.setValue("aGD@[\\]!");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("fails when start mit leerzeichen")
	public void validate12() {

		// Arrange
		LOG.info("Testen Passwort ohne Ziffer");
		final TestObject credentials = new TestObject();
		credentials.setValue(" ich beginne mit 1 Leerzeichen");

		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(credentials);

		// Assert
		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());

		final ConstraintViolation<TestObject> cv = errors.iterator().next();
		LOG.debug(cv.getMessage());
		assertEquals("value", cv.getPropertyPath().toString());
	}

	@Test
	@DisplayName("passes when value valid")
	public void validate10() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();
		final String prefix = "start";
		final String suffix = "12";

		for (final char c : VALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject(prefix + c + suffix);

			// Act
			final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

			// Assert
			assertTrue(errors.isEmpty(), "Fehler bei [" + c + "]");
		}
	}

	@Test
	@DisplayName("fails when value invalid")
	public void validate11() {

		// Arrange
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		final Validator validator = validatorFactory.getValidator();
		final String prefix = "start12";

		for (final char c : INVALID_CHARS.toCharArray()) {

			final TestObject testObject = new TestObject(prefix + c);

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
