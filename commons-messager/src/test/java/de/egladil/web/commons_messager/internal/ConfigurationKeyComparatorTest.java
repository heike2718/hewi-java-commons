// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_messager.ConfigurationKey;

/**
 * ConfigurationKeyComparatorTest
 */
public class ConfigurationKeyComparatorTest {

	@Test
	void should_compareThrowNPE_when_key1Null() {

		// Arrange
		ConfigurationKey key = new ConfigurationKey("Österreich");

		ConfigurationKeyComparator comparator = new ConfigurationKeyComparator();

		// Act
		try {

			comparator.compare(null, key);
			fail("keine NPE");
		} catch (NullPointerException e) {

			assertEquals("ConfigurationKey key1", e.getMessage());
		}

	}

	@Test
	void should_compareThrowNPE_when_key2Null() {

		// Arrange
		ConfigurationKey key = new ConfigurationKey("Österreich");

		ConfigurationKeyComparator comparator = new ConfigurationKeyComparator();

		// Act
		try {

			comparator.compare(key, null);
			fail("keine NPE");
		} catch (NullPointerException e) {

			assertEquals("ConfigurationKey key2", e.getMessage());
		}

	}

	@Test
	void should_compareUseGermanLocal() {

		// Arrange
		ConfigurationKey key1 = new ConfigurationKey("Österreich");
		ConfigurationKey key2 = new ConfigurationKey("Osten");

		ConfigurationKeyComparator comparator = new ConfigurationKeyComparator();

		// Act
		int result = comparator.compare(key1, key2);

		assertTrue(result > 0);

	}

}
