// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * ConfigurationKey
 */
public class ConfigurationKey {

	private final String name;

	private String defaultValue;

	private String description;

	/**
	 * @param name
	 */
	public ConfigurationKey(final String name) {

		if (StringUtils.isBlank(name)) {

			throw new IllegalArgumentException("name blank");
		}
		this.name = name;
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {

			return true;
		}

		if (obj == null) {

			return false;
		}

		if (getClass() != obj.getClass()) {

			return false;
		}
		ConfigurationKey other = (ConfigurationKey) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		if (defaultValue == null) {

			return "[key=" + name + ", description=" + description + "]";
		}
		return "[key=" + name + ", description=" + description + ", defaultValue=" + defaultValue + "]";

	}

	/**
	 * @return the name
	 */
	public String name() {

		return name;
	}

	/**
	 * @return the description
	 */
	public String description() {

		return description;
	}

	/**
	 * @param description
	 *                    the description to set
	 */
	public ConfigurationKey withDescription(final String description) {

		this.description = description;
		return this;
	}

	public String getDefaultValue() {

		return defaultValue;
	}

	public ConfigurationKey withDefaultValue(final String defaultValue) {

		this.defaultValue = defaultValue;
		return this;
	}

}
