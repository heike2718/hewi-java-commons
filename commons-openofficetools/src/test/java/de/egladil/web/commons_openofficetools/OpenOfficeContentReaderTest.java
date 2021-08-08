// =====================================================
// Project: commons-openofficetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_openofficetools;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * OpenOfficeContentReaderTest
 */
public class OpenOfficeContentReaderTest {

	@Test
	void should_readContentWork() throws IOException {

		try (InputStream in = getClass().getResourceAsStream("/auswertung.ods")) {

			List<String> zeilen = new OpenOfficeContentReader().readContentAsLines(in);

			// Assert
			assertEquals(6, zeilen.size());
		}

	}

}
