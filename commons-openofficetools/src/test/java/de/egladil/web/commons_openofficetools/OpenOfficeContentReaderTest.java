// =====================================================
// Project: commons-openofficetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_openofficetools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * OpenOfficeContentReaderTest
 */
public class OpenOfficeContentReaderTest {

	@Test
	void should_readAuswertungWork() throws IOException {

		try (InputStream in = getClass().getResourceAsStream("/auswertung.ods")) {

			List<String> zeilen = new OpenOfficeContentReader().readContentAsLines(in);

			// Assert
			assertEquals(6, zeilen.size());

			zeilen.forEach(z -> System.out.println(z));

			for (int i = 0; i < 6; i++) {

				String[] tokens = zeilen.get(i).split(";");

				if (i < 3) {

					assertEquals(1, tokens.length, "Fehler bei Index " + i);
				}

				if (i == 3) {

					assertEquals(14, tokens.length, "Fehler bei Index " + i);
				}

				if (i > 3) {

					assertEquals(13, tokens.length, "Fehler bei Index " + i);
				}
			}
		}
	}

	@Test
	void should_readKlassenlisteWork() throws IOException {

		try (InputStream in = getClass().getResourceAsStream("/klassenliste.ods")) {

			List<String> zeilen = new OpenOfficeContentReader().readContentAsLines(in);

			// Assert
			assertEquals(13, zeilen.size());

			zeilen.forEach(z -> System.out.println(z));

			for (int i = 0; i < 13; i++) {

				String[] tokens = zeilen.get(i).split(";");
				assertEquals(4, tokens.length, "Fehler bei Index " + i);
			}
		}
	}

	@Test
	void should_readMixedTypesWork() throws IOException {

		try (InputStream in = getClass().getResourceAsStream("/mixed-celltypes.ods")) {

			List<String> zeilen = new OpenOfficeContentReader().readContentAsLines(in);

			// Assert
			assertEquals(2, zeilen.size());

			zeilen.forEach(z -> System.out.println(z));

			{

				String[] tokens = zeilen.get(0).split(";");
				assertEquals(4, tokens.length);
			}

			{

				String[] tokens = zeilen.get(1).split(";");
				assertEquals(3, tokens.length);
			}
		}
	}
}
