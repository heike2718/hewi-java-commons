// =====================================================
// Project: commons-openofficetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_openofficetools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.egladil.web.commons_openofficetools.exceptions.OOParserRuntimeException;
import de.egladil.web.commons_openofficetools.parser.OORows2LinesMapper;
import de.egladil.web.commons_openofficetools.parser.OpenOfficeParser;
import de.egladil.web.commons_openofficetools.parser.OpenOfficeTableElement;

/**
 * OpenOfficeContentReader
 */
public class OpenOfficeContentReader {

	/**
	 * Liest das OpenOfficeFile ein und extrahiert die Zeilen zu kommaseparierten Strings.
	 *
	 * @param  File
	 *              das File im Filesystem
	 * @return      List
	 */
	public List<String> readContentAsLines(final File file) {

		try (InputStream in = new FileInputStream(file)) {

			return readContentAsLines(in);

		} catch (IOException e) {

			throw new OOParserRuntimeException("Fehler beim Lesen der ODF-Datei: " + e.getMessage(), e);
		}

	}

	/**
	 * Liest das OpenOfficeFile ein und extrahiert die Zeilen zu kommaseparierten Strings.
	 *
	 * @param  pathFile
	 *                  String absoluter Pfad zu einer ODF-Datei.
	 * @return          List
	 */
	public List<String> readContentAsLines(final String pathFile) {

		return readContentAsLines(new File(pathFile));

	}

	List<String> readContentAsLines(final InputStream in) {

		try {

			final String xml = new OpenOfficeParser().getContentSafe(in, 6000000);
			final List<OpenOfficeTableElement> extrahierteElemente = new OpenOfficeParser().parseContent(xml);

			// Act
			List<String> zeilen = new OORows2LinesMapper().apply(extrahierteElemente);

			return zeilen;
		} catch (IOException | XMLStreamException | ParserConfigurationException | SAXException e) {

			throw new OOParserRuntimeException("Fehler beim Lesen der ODF-Datei: " + e.getMessage(), e);
		}

	}

}
