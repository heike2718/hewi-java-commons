// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager.telegram;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_messager.ConfigurationDescription;
import de.egladil.web.commons_messager.ConfigurationKey;
import de.egladil.web.commons_messager.Messager;
import de.egladil.web.commons_messager.MessagerType;
import de.egladil.web.commons_messager.TelegramConfigKeys;
import de.egladil.web.commons_messager.exception.MessagerConfigurationException;
import de.egladil.web.commons_messager.exception.MessagerException;
import jakarta.ws.rs.core.UriBuilder;

/**
 * TelegramMessager
 */
public class TelegramMessager implements Messager {

	private static final Logger LOG = LoggerFactory.getLogger(TelegramMessager.class);

	private static final List<String> ALL_KEYS = Arrays.asList(new String[] { TelegramConfigKeys.FROM_URI, TelegramConfigKeys.PATH,
		TelegramConfigKeys.QUERY_PARAM_CHAT_ID, TelegramConfigKeys.SECRET });

	@Override
	public ConfigurationDescription getConfigurationDescription() {

		ConfigurationDescription result = new ConfigurationDescription("Telegramconfiguration");

		result
			.addConfigurationKey(new ConfigurationKey(TelegramConfigKeys.FROM_URI).withDescription("base url der telegram api")
				.withDefaultValue("https://api.telegram.org"));
		result.addConfigurationKey(
			new ConfigurationKey(TelegramConfigKeys.PATH).withDescription("der Teil, der hinter der baseUrl angehaengt wird")
				.withDefaultValue("/{token}/sendMessage"));
		result.addConfigurationKey(
			new ConfigurationKey(TelegramConfigKeys.QUERY_PARAM_CHAT_ID)
				.withDescription("ein queryParam der die ID des eigenen telegramChats enthaelt"));
		result.addConfigurationKey(
			new ConfigurationKey(TelegramConfigKeys.SECRET)
				.withDescription("das Passwort des TelegramChats"));

		return result;
	}

	@Override
	public void sendMessage(final String messageText, final Map<String, String> configuration) throws MessagerException {

		if (StringUtils.isBlank(messageText)) {

			throw new IllegalArgumentException("messageText darf nicht blank sein.");
		}

		if (configuration == null) {

			throw new IllegalArgumentException("configuration darf nicht null sein.");
		}

		HttpClient client = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(5))
			.version(HttpClient.Version.HTTP_2)
			.build();

		try {

			UriBuilder builder = UriBuilder
				.fromUri(configuration.get(TelegramConfigKeys.FROM_URI))
				.path(configuration.get(TelegramConfigKeys.PATH))
				.queryParam(TelegramConfigKeys.QUERY_PARAM_CHAT_ID,
					configuration.get(TelegramConfigKeys.QUERY_PARAM_CHAT_ID))
				.queryParam("text", messageText);

			String telegramSecret = configuration.get(TelegramConfigKeys.SECRET);

			HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.uri(builder.build("bot" + telegramSecret))
				.timeout(Duration.ofSeconds(5))
				.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			LOG.debug("" + response.statusCode());
			LOG.debug(response.body());
		} catch (IOException | InterruptedException e) {

			String msg = "Message konnte nicht gesendet werden: " + e.getMessage();
			throw new MessagerException(msg, e);
		} catch (Exception e) {

			String msg = "Unerwartete Exception beim telegrammen einer Message: " + e.getMessage();
			LOG.error(msg, e);
			throw new MessagerException(msg, e);
		}
	}

	@Override
	public List<String> getAllConfgigurationKeys() {

		return ALL_KEYS;
	}

	@Override
	public Map<String, String> buildConfiguration(final Properties secretProperties) {

		Map<String, String> result = new HashMap<>();

		ConfigurationDescription configDescr = getConfigurationDescription();

		for (String keyName : ALL_KEYS) {

			Object prop = secretProperties.get(keyName);

			if (prop != null) {

				result.put(keyName, prop.toString());

			} else {

				Optional<ConfigurationKey> optKey = configDescr.findConfigurationKey(keyName);

				if (optKey.isPresent()) {

					ConfigurationKey confKey = optKey.get();

					if (confKey.getDefaultValue() != null) {

						result.put(keyName, confKey.getDefaultValue());
					}
				}
			}
		}

		for (String keyName : ALL_KEYS) {

			if (result.get(keyName) == null) {

				throw new MessagerConfigurationException(
					"Konfiguration ist unvollstaendig: value zu key='" + keyName + "' fehlt");
			}

		}

		return result;
	}

	@Override
	public MessagerType getType() {

		return MessagerType.TELEGRAM;
	}

}
