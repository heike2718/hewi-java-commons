// =====================================================
// Project: de.egladil.tools.parser
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_openofficetools.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import de.egladil.web.commons_openofficetools.parser.OORows2LinesMapper;
import de.egladil.web.commons_openofficetools.parser.OpenOfficeParser;
import de.egladil.web.commons_openofficetools.parser.OpenOfficeTableElement;

/**
 * OORows2LinesMapperTest
 */
public class OORows2LinesMapperTest {

	@Test
	void testMap() throws IOException, ParserConfigurationException, SAXException, XMLStreamException {

		try (InputStream in = getClass().getResourceAsStream("/auswertung.ods")) {

			// Arrange
			final String xml = new OpenOfficeParser().getContentSafe(in, 6000000);
			final List<OpenOfficeTableElement> extrahierteElemente = new OpenOfficeParser().parseContent(xml);

			// Act
			List<String> zeilen = new OORows2LinesMapper().apply(extrahierteElemente);

			// Assert
			assertEquals(6, zeilen.size());

			zeilen.stream().forEach(z -> System.out.println(z));

		}
	}

}
