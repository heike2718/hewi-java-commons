// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager.telegram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_messager.ConfigurationDescription;
import de.egladil.web.commons_messager.ConfigurationKey;
import de.egladil.web.commons_messager.TelegramConfigKeys;

/**
 * TelegramMessagerTest
 */
public class TelegramMessagerTest {

	private TelegramMessager messager;

	@BeforeEach
	void setUp() {

		messager = new TelegramMessager();
	}

	@Test
	void should_getConfigDescriptionWork() {

		// Act
		ConfigurationDescription configDescription = messager.getConfigurationDescription();

		// Assert
		assertEquals("Telegramconfiguration", configDescription.getName());

		List<ConfigurationKey> configurationKeys = configDescription.getConfigurationKeys();
		assertEquals(4, configurationKeys.size());

		{

			ConfigurationKey key = configurationKeys.get(0);
			assertEquals(TelegramConfigKeys.QUERY_PARAM_CHAT_ID, key.name());
			assertNull(key.getDefaultValue());
		}

		{

			ConfigurationKey key = configurationKeys.get(1);
			assertEquals(TelegramConfigKeys.FROM_URI, key.name());
			assertEquals("https://api.telegram.org", key.getDefaultValue());
		}

		{

			ConfigurationKey key = configurationKeys.get(2);
			assertEquals(TelegramConfigKeys.PATH, key.name());
			assertEquals("/{token}/sendMessage", key.getDefaultValue());
		}

		{

			ConfigurationKey key = configurationKeys.get(3);
			assertEquals(TelegramConfigKeys.SECRET, key.name());
			assertNull(key.getDefaultValue());
		}
	}

	@Test
	void should_sendMessageThrowIllegalArgumentException_when_configurationNull() {

		// Arrange
		String text = "bla";

		// Act + Assert
		try {

			messager.sendMessage(text, null);
			fail("keine IllegalArgumentException");

		} catch (IllegalArgumentException e) {

			assertEquals("configuration darf nicht null sein.", e.getMessage());
		}

	}

	@Test
	void should_sendMessageThrowIllegalArgumentException_when_textNull() {

		// Arrange
		String text = null;
		Map<String, String> configuration = new HashMap<>();

		// Act + Assert
		try {

			messager.sendMessage(text, configuration);
			fail("keine IllegalArgumentException");

		} catch (IllegalArgumentException e) {

			assertEquals("messageText darf nicht blank sein.", e.getMessage());
		}

	}

	@Test
	void should_sendMessageThrowIllegalArgumentException_when_textBlank() {

		// Arrange
		String text = "  ";
		Map<String, String> configuration = new HashMap<>();

		// Act + Assert
		try {

			messager.sendMessage(text, configuration);
			fail("keine IllegalArgumentException");

		} catch (IllegalArgumentException e) {

			assertEquals("messageText darf nicht blank sein.", e.getMessage());
		}

	}

	@Test
	void should_buildConfigurationWork() throws IOException {

		// Arrange
		Properties props = this.loadProperties();

		// Act
		Map<String, String> configuration = messager.buildConfiguration(props);

		// Assert
		assertEquals(4, configuration.size());

		assertNotNull(configuration.get(TelegramConfigKeys.SECRET));
		assertEquals("https://api.telegram.org", configuration.get(TelegramConfigKeys.FROM_URI));
		assertEquals("/{token}/sendMessage", configuration.get(TelegramConfigKeys.PATH));
		assertNotNull(configuration.get(TelegramConfigKeys.QUERY_PARAM_CHAT_ID));

	}

	@Test
	void should_sendMessageWork() throws Exception {

		// Arrange
		Map<String, String> config = messager.buildConfiguration(this.loadProperties());
		String message = "Das ist ein Text aus dem JUnitTest vom " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

		// Act
		messager.sendMessage(message, config);

	}

	private Properties loadProperties() throws IOException {

		String pathProperties = "/home/heike/git/konfigurationen/telegram/telegram.properties/";

		try (InputStream in = new FileInputStream(new File(pathProperties))) {

			Properties props = new Properties();
			props.load(in);
			return props;
		}
	}

}
