// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.openoffice.ods;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;
import de.egladil.web.commons_officetools.impl.AbstractTableDocumentContentReader;

/**
 * ODSContentReader
 */
public class ODSContentReader extends AbstractTableDocumentContentReader {

	@Override
	public List<String> readContentAsLines(final InputStream in) {

		try {

			ODSParser odsParser = new ODSParser();

			final String xml = odsParser.getContentSafe(in, 6000000);
			final List<OpenOfficeTableElement> extrahierteElemente = odsParser.parseContent(xml);

			// Act
			List<String> zeilen = new OORows2LinesMapper().apply(extrahierteElemente);

			return zeilen;
		} catch (IOException | XMLStreamException | ParserConfigurationException | SAXException e) {

			throw new OfficeToolsRuntimeException("Fehler beim Lesen der ODF-Datei: " + e.getMessage(), e);
		}
	}
}
