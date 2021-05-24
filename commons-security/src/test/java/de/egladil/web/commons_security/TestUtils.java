// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security;

import java.io.IOException;
import java.io.InputStream;

/**
 * TestUtils
 */
public class TestUtils {

	public static byte[] readBytesFromClasspath(final String classpathLocation) throws IOException {

		try (InputStream in = TestUtils.class.getResourceAsStream(classpathLocation)) {

			return in.readAllBytes();
		}
	}
}
