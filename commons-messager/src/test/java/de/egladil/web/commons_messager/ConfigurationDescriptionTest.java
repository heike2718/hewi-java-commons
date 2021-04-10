// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * ConfigurationDescriptionTest
 */
public class ConfigurationDescriptionTest {

	@Test
	void should_constructorThrowIllegalArgumentException_when_nameNull() {

		try {

			new ConfigurationDescription(null);
			fail("keine IllegalArgumentException");
		} catch (IllegalArgumentException e) {

			assertEquals("name blank", e.getMessage());
		}
	}

	@Test
	void should_constructorThrowIllegalArgumentException_when_nameBlank() {

		try {

			new ConfigurationDescription("   ");
			fail("keine IllegalArgumentException");
		} catch (IllegalArgumentException e) {

			assertEquals("name blank", e.getMessage());
		}
	}

	@Test
	void should_addConfigurationKey_addTheKey() {

		// Arrange
		ConfigurationKey key = new ConfigurationKey("url").withDescription("bla");
		ConfigurationDescription configurationDescription = new ConfigurationDescription("Telegram Configuration");

		// Act
		configurationDescription.addConfigurationKey(key);

		// Assert
		List<ConfigurationKey> keys = configurationDescription.getConfigurationKeys();
		assertEquals(1, keys.size());

		ConfigurationKey theKey = keys.get(0);
		assertEquals(key, theKey);

	}

	@Test
	void should_addConfigurationKey_notAddAKeyTwice() {

		// Arrange
		ConfigurationKey key = new ConfigurationKey("url").withDescription("bla");
		ConfigurationDescription configurationDescription = new ConfigurationDescription("Irgendeine Konfiguration");

		// Act
		configurationDescription.addConfigurationKey(key);
		configurationDescription.addConfigurationKey(key);

		// Assert
		List<ConfigurationKey> keys = configurationDescription.getConfigurationKeys();
		assertEquals(1, keys.size());

		ConfigurationKey theKey = keys.get(0);
		assertEquals(key, theKey);

	}

	@Test
	void should_printWriteTheDescription() {

		// Arrange
		ConfigurationDescription configurationDescription = new ConfigurationDescription("Printable Configuration");
		configurationDescription.addConfigurationKey(new ConfigurationKey("url").withDescription("bladi"));
		configurationDescription.addConfigurationKey(new ConfigurationKey("secret").withDescription("blubb"));

		// Act
		String printed = configurationDescription.print();
		System.out.println(printed);

		// Assert
		assertEquals("Printable Configuration: [[key=secret, description=blubb], [key=url, description=bladi]]", printed);
	}

}
