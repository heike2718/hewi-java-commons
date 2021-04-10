// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.egladil.web.commons_messager.internal.ConfigurationKeyComparator;

/**
 * ConfigurationDescription
 */
public class ConfigurationDescription {

	private final String name;

	private List<ConfigurationKey> configurationKeys = new ArrayList<>();

	/**
	 * @param name
	 */
	public ConfigurationDescription(final String name) {

		if (StringUtils.isBlank(name)) {

			throw new IllegalArgumentException("name blank");

		}
		this.name = name;
	}

	/**
	 * @param configurationKeys
	 *                          the configurationKeys to set
	 */
	public void addConfigurationKey(final ConfigurationKey configurationKey) {

		if (!this.configurationKeys.contains(configurationKey)) {

			this.configurationKeys.add(configurationKey);
		}
	}

	public Optional<ConfigurationKey> findConfigurationKey(final String key) {

		return this.configurationKeys.stream().filter(k -> key.equals(k.name())).findFirst();

	}

	/**
	 * Gibt einen String zurück, der nett in ein Log geschrieben werden kann.
	 *
	 * @return String
	 */
	public String print() {

		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(": ");

		List<String> logsFromKeys = this.getConfigurationKeys().stream().map(key -> key.toString()).collect(Collectors.toList());

		sb.append(StringUtils.join(logsFromKeys));

		return sb.toString();
	}

	public String getName() {

		return name;
	}

	public List<ConfigurationKey> getConfigurationKeys() {

		Collections.sort(configurationKeys, new ConfigurationKeyComparator());

		return configurationKeys;
	}

}
