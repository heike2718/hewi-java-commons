// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * ConfigurationKeyTest
 */
public class ConfigurationKeyTest {

	@Test
	void should_constructorThrowIllegalArgumentException_when_nameNull() {

		try {

			new ConfigurationKey(null);
			fail("keine IllegalArgumentException");
		} catch (IllegalArgumentException e) {

			assertEquals("name blank", e.getMessage());
		}

	}

	@Test
	void should_constructorNameThrowIllegalArgumentException_when_nameBlank() {

		try {

			new ConfigurationKey("  ");
			fail("keine IllegalArgumentException");
		} catch (IllegalArgumentException e) {

			assertEquals("name blank", e.getMessage());
		}

	}

	@Test
	void should_equalBaseOnName() {

		// Arrange
		ConfigurationKey key1 = new ConfigurationKey("name").withDescription("descr1");
		ConfigurationKey key2 = new ConfigurationKey("name").withDescription("descr2");
		ConfigurationKey key3 = new ConfigurationKey("key").withDescription("descr1");

		// Assert
		assertTrue(key1.equals(key1));
		assertTrue(key1.equals(key2));
		assertFalse(key1.equals(key3));

		assertEquals(key1.hashCode(), key2.hashCode());

		assertFalse(key1.equals(null));
		assertFalse(key1.equals(new Object()));

	}

	@Test
	void should_toStringBeImplementedAsExpected() {

		// Arrange
		ConfigurationKey key = new ConfigurationKey("ein_name").withDescription("Beschreibung dieser Property");

		// Act * Assert
		assertEquals("[key=ein_name, description=Beschreibung dieser Property]", key.toString());

		assertEquals("ein_name", key.name());
		assertEquals("Beschreibung dieser Property", key.description());

	}

}
