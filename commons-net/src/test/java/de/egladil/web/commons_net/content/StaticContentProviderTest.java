// =====================================================
// Projekt: commons
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.content;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_net.content.SimpleContentReader;
import de.egladil.web.commons_net.content.StaticContentProvider;
import de.egladil.web.commons_net.exception.RequestTimeoutException;

/**
 * StaticContentProviderTest
 */
public class StaticContentProviderTest {

	@Test
	void readContentFromPlainFile() throws RequestTimeoutException, IOException {

		// Arrange
		String url = "https://mathe-jung-alt.de/heike_winkelvoss_public_key.asc";
		StaticContentProvider scp = new StaticContentProvider(new SimpleContentReader());

		// Act
		byte[] content = scp.getStaticContent(url, 2000);

		// Assert
		final String string = new String(content);
		// System.out.println(string);
		assertTrue(string.startsWith("-----BEGIN PGP PUBLIC KEY BLOCK-----"));
	}

	@Test
	void readContentFromFileSystem() throws RequestTimeoutException, IOException {

		// Arrange
		String url = "file:///home/heike/.keystore/public/authprov_public_key.pem";

		StaticContentProvider scp = new StaticContentProvider(new SimpleContentReader());

		// Act
		byte[] content = scp.getStaticContent(url, 2000);

		// Assert
		final String string = new String(content);
		System.out.println(string);
		assertTrue(string.startsWith("-----BEGIN PUBLIC KEY-----"));
	}
}
