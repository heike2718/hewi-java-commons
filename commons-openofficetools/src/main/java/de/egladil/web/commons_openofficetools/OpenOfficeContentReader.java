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
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import de.egladil.web.commons_openofficetools.exceptions.OOParserRuntimeException;
import de.egladil.web.commons_openofficetools.parser.OORows2LinesMapper;
import de.egladil.web.commons_openofficetools.parser.OpenOfficeParser;
import de.egladil.web.commons_openofficetools.parser.OpenOfficeTableElement;
import de.egladil.web.commons_openofficetools.parser.XmlDeclarationParser;

/**
 * OpenOfficeContentReader
 */
public class OpenOfficeContentReader {

	/**
	 * Liest das OpenOfficeFile ein und extrahiert die Zeilen zu kommaseparierten Strings.<br>
	 * <br>
	 * <strong>Achtung: </strong>
	 * <ul>
	 * <li>Leere Zeilen werden als leerer String extrahiert</li><br>
	 * <li>Numerische Zellen werden als double-String extrahiert, enthalten also einen Dezimalpunkt. Eine Konvertierung in einen
	 * anderen numerischen Typ oder einen String ohne Dezimalpunkt muss der Aufrufer
	 * vornehmen</li><br>
	 * <li>Boolsche Werte: die Formel wird als String extrahiert, ist also sinnlos!!!</li>
	 * </ul>
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

	/**
	 * Ermittelt das Encoding des gegebenen Files.
	 *
	 * @param  pathOfFile
	 *                    String
	 * @return            Optional
	 */
	public Optional<String> detectEncoding(final String pathOfFile) {

		try (InputStream in = new FileInputStream(new File(pathOfFile))) {

			final String xml = new OpenOfficeParser().getContentSafe(in, 6000000);
			int indexOfRootTag = xml.indexOf("<office:document-content");

			String xmlDeclaration = xml.substring(0, indexOfRootTag);

			return new XmlDeclarationParser().getEncodingFromXmlDeclaration(xmlDeclaration);

		} catch (IOException e) {

			throw new OOParserRuntimeException("Fehler beim Lesen der ODF-Datei: " + e.getMessage(), e);
		}
	}
}
