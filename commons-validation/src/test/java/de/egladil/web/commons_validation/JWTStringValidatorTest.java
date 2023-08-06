// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_validation.annotations.JWTString;

/**
 * JWTStringValidatorTest
 */
public class JWTStringValidatorTest extends AbstractValidatorTest {

	private class TestObject {

		@JWTString
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
	void validJWTString() {

		// Arrange
		String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJkODg2ZWJhZS03NDU0LTRkYmItOTI5ZS1kZWQyZjM4OWRmZDYiLCJmdWxsX25hbWUiOiJraW5kIEbDvG5mIiwiaXNzIjoiaGVpa2UyNzE4L2F1dGhwcm92aWRlciIsImdyb3VwcyI6WyJMRUhSRVIiLCJTVEFOREFSRCJdLCJleHAiOjE1Nzc5NzcwNzYsImlhdCI6MTU3Nzk3NjI5Nn0.AMDbKa4g2nZa1SOky52WZTyqrwJ5Y3e6Vl2Euo2nnGOHabM1LK4uLTk88qTC0G138G32x5fjmCmQejaIQ7iiNBr6GLZFcXDlgK3eHfo_9w8hdf_VGq7-1wLBD9PsIAwMsD69rpF9RxBsQeHCkVkhQEi95GFMXlA_GDfyr9WMcQf3c5JeuqH9v61p-lA0FWsL9WmpopFdGoVLjj8fkrEawP79fHmFz5XR7iTGHR6bv7BydfVZG-Oh_NHeyG7uSuKX-89rzJpjwCVz607HGN2SdnymBt8UxwmWSAnO8MgwNqffudOsLBzQsXGMyycDdK4aP3jBASPXkc2gHRHsDGfXBA";

		TestObject testObject = new TestObject(jwt);

		// Act
		final Set<ConstraintViolation<TestObject>> errors = validator.validate(testObject);

		// Assert
		assertTrue(errors.isEmpty());

	}

}
