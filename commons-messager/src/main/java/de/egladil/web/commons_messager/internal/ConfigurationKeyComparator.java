// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager.internal;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import de.egladil.web.commons_messager.ConfigurationKey;

/**
 * ConfigurationKeyComparator
 */
public class ConfigurationKeyComparator implements Comparator<ConfigurationKey> {

	private final Locale locale = Locale.GERMAN;

	private final Collator collator = Collator.getInstance(locale);

	@Override
	public int compare(final ConfigurationKey key1, final ConfigurationKey key2) {

		if (key1 == null) {

			throw new NullPointerException("ConfigurationKey key1");
		}

		if (key2 == null) {

			throw new NullPointerException("ConfigurationKey key2");
		}

		return collator.compare(key1.name(), key2.name());
	}

}
