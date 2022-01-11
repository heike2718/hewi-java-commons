// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.openoffice.ods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import de.egladil.web.commons_officetools.exceptions.OfficeToolsSecurityException;

/**
 * ODSParserTest
 */
public class ODSParserTest {

	@Test
	void checkVulnerableXXEEntity() {

		// Arrange
		final String xml = "<!--?xml version=\"1.0\" ?--><!DOCTYPE replace [<!ENTITY example \"Doe\"> ]><userInfo><firstName>John</firstName><lastName>&example;</lastName></userInfo>";

		// Act
		final Throwable ex = assertThrows(OfficeToolsSecurityException.class, () -> {

			new ODSParser().checkVulnerableXml(xml);
		});
		assertEquals("XML enthält DOCTYPE", ex.getMessage());
	}

	@Test
	void checkVulnerableXXEFileDisclosure() {

		// Arrange
		final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE foo [<!ELEMENT foo ANY ><!ENTITY xxe SYSTEM \"file:///dev/random\">]><foo>&xxe;</foo>";

		// Act
		final Throwable ex = assertThrows(OfficeToolsSecurityException.class, () -> {

			new ODSParser().checkVulnerableXml(xml);
		});
		assertEquals("XML enthält DOCTYPE", ex.getMessage());
	}

	@Test
	void checkVulnerableXXEDenialOfService() {

		// Arrange
		final String xml = "<!--?xml version=\"1.0\" ?--><!DOCTYPE lolz [<!ENTITY lol \"lol\"><!ELEMENT lolz (#PCDATA)><!ENTITY lol1 \"&lol;&lol;&lol;&lol;&lol;&lol;&lol;<!ENTITY lol2 \"&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;\"><!ENTITY lol3 \"&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;\"><!ENTITY lol4 \"&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;\"><!ENTITY lol5 \"&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;\"><!ENTITY lol6 \"&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;\"><!ENTITY lol7 \"&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;\"><!ENTITY lol8 \"&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;\"><!ENTITY lol9 \"&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;\"><tag>&lol9;</tag>";

		// Act
		final Throwable ex = assertThrows(OfficeToolsSecurityException.class, () -> {

			new ODSParser().checkVulnerableXml(xml);
		});
		assertEquals("XML enthält DOCTYPE", ex.getMessage());
	}

	@Test
	void parseContentKlappt() throws IOException, ParserConfigurationException, SAXException, XMLStreamException {

		try (InputStream in = getClass().getResourceAsStream("/openoffice/auswertung.ods")) {

			final String xml = new ODSParser().getContentSafe(in, 6000000);

			final List<OpenOfficeTableElement> extrahierteElemente = new ODSParser().parseContent(xml);

			assertEquals(20, extrahierteElemente.size());

			for (int index = 0; index < extrahierteElemente.size(); index++) {

				final OpenOfficeTableElement element = extrahierteElemente.get(index);

				switch (index) {

				case 0:
					assertFalse(element.isHeadline());
					assertEquals(1, element.size());
					assertEquals("in die farbigen Zellen eingeben: ", element.getChildElements().get(index).getContent());
					break;

				case 1:
					assertFalse(element.isHeadline());
					assertEquals(1, element.size());
					assertEquals("In der letzten Spalte stehen anschließend die Punkte für das jeweilige Kind",
						element.getChildElements().get(0).getContent());
					break;

				case 2:
					assertFalse(element.isHeadline());
					assertEquals(1, element.size());
					assertEquals(
						"Gleich nach dem Ausfüllen und Speichern über die Minikänguru-Anwendung hochladen: https://mathe-jung-alt.de/mkv",
						element.getChildElements().get(0).getContent());
					break;

				case 3:
					assertTrue(element.isEmptyRow());
					assertFalse(element.isHeadline());
					assertEquals(0, element.size());
					break;

				case 4:
					assertFalse(element.isEmptyRow());
					assertTrue(element.isHeadline());
					assertEquals(14, element.size());
					checkHeadline(element.getChildElements());
					break;

				case 5:
					assertFalse(element.isEmptyRow());
					assertFalse(element.isHeadline());
					assertEquals(26, element.size());
					checkKind1(element.getChildElements());
					break;

				case 6:
					assertFalse(element.isEmptyRow());
					assertFalse(element.isHeadline());
					assertEquals(26, element.size());
					checkKind2(element.getChildElements());
					break;

				case 7:
					assertTrue(element.isEmptyRow());
					assertFalse(element.isHeadline());
					assertEquals(13, element.size());
					checkEmptyRow(element.getChildElements());
					break;

				case 8:
					assertTrue(element.isEmptyRow());
					assertFalse(element.isHeadline());
					assertEquals(13, element.size());
					checkEmptyRow(element.getChildElements());
					break;

				default:
					assertEquals(0, element.size());
					assertFalse(element.isHeadline());
					assertTrue(element.isEmptyRow());
					break;
				}
			}
		}
	}

	/**
	 * @param cells
	 */
	private void checkHeadline(final List<OpenOfficeTableElement> cells) {

		for (int i = 0; i < cells.size(); i++) {

			final OpenOfficeTableElement child = cells.get(i);
			assertEquals(OOElementType.CELL, child.getType());
			assertFalse(child.isFormula());

			switch (i) {

			case 0:
				assertEquals("Name", child.getContent());
				break;

			case 1:
				assertEquals("A-1", child.getContent());
				break;

			case 2:
				assertEquals("A-2", child.getContent());
				break;

			case 3:
				assertEquals("A-3", child.getContent());
				break;

			case 4:
				assertEquals("A-4", child.getContent());
				break;

			case 5:
				assertEquals("B-1", child.getContent());
				break;

			case 6:
				assertEquals("B-2", child.getContent());
				break;

			case 7:
				assertEquals("B-3", child.getContent());
				break;

			case 8:
				assertEquals("B-4", child.getContent());
				break;

			case 9:
				assertEquals("C-1", child.getContent());
				break;

			case 10:
				assertEquals("C-2", child.getContent());
				break;

			case 11:
				assertEquals("C-3", child.getContent());
				break;

			case 12:
				assertEquals("C-4", child.getContent());
				break;

			case 13:
				assertEquals("Punkte", child.getContent());
				break;

			default:
				break;
			}
		}
	}

	/**
	 * @param cells
	 */
	private void checkKind1(final List<OpenOfficeTableElement> cells) {

		for (int i = 0; i < cells.size(); i++) {

			final OpenOfficeTableElement child = cells.get(i);
			assertEquals(OOElementType.CELL, child.getType());

			switch (i) {

			case 0:
				assertFalse(child.isFormula());
				assertEquals("Albert Einstein", child.getContent());
				break;

			case 1:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 2:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 3:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 4:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 5:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 6:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 7:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 8:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 9:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 10:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 11:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 12:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 13:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 14:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 15:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 16:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 17:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 18:
				assertTrue(child.isFormula());
				assertEquals("5", child.getContent());
				break;

			case 19:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 20:
				assertTrue(child.isFormula());
				assertEquals("5", child.getContent());
				break;

			case 21:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 22:
				assertTrue(child.isFormula());
				assertEquals("5", child.getContent());
				break;

			case 23:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 24:
				assertTrue(child.isFormula());
				assertEquals("5", child.getContent());
				break;

			case 25:
				assertTrue(child.isFormula());
				assertEquals("60", child.getContent());
				break;

			default:
				break;
			}
		}
	}

	/**
	 * @param cells
	 */
	private void checkKind2(final List<OpenOfficeTableElement> cells) {

		for (int i = 0; i < cells.size(); i++) {

			final OpenOfficeTableElement child = cells.get(i);
			assertEquals(OOElementType.CELL, child.getType());

			switch (i) {

			case 0:
				assertFalse(child.isFormula());
				assertEquals("Max Planck", child.getContent());
				break;

			case 1:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 2:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 3:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 4:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 5:
				assertFalse(child.isFormula());
				assertEquals("n", child.getContent());
				break;

			case 6:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 7:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 8:
				assertTrue(child.isFormula());
				assertEquals("3", child.getContent());
				break;

			case 9:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 10:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 11:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 12:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 13:
				assertFalse(child.isFormula());
				assertEquals("f", child.getContent());
				break;

			case 14:
				assertTrue(child.isFormula());
				assertEquals("-1", child.getContent());
				break;

			case 15:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 16:
				assertTrue(child.isFormula());
				assertEquals("4", child.getContent());
				break;

			case 17:
				assertFalse(child.isFormula());
				assertEquals("r", child.getContent());
				break;

			case 18:
				assertTrue(child.isFormula());
				assertEquals("5", child.getContent());
				break;

			case 19:
				assertFalse(child.isFormula());
				assertEquals("n", child.getContent());
				break;

			case 20:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 21:
				assertFalse(child.isFormula());
				assertEquals("f", child.getContent());
				break;

			case 22:
				assertTrue(child.isFormula());
				assertEquals("-1,25", child.getContent());
				break;

			case 23:
				assertFalse(child.isFormula());
				assertEquals("f", child.getContent());
				break;

			case 24:
				assertTrue(child.isFormula());
				assertEquals("-1,25", child.getContent());
				break;

			case 25:
				assertTrue(child.isFormula());
				assertEquals("34,5", child.getContent());
				break;

			default:
				break;
			}
		}
	}

	/**
	 * @param cells
	 */
	private void checkEmptyRow(final List<OpenOfficeTableElement> cells) {

		for (int i = 0; i < cells.size(); i++) {

			final OpenOfficeTableElement child = cells.get(i);
			assertEquals(OOElementType.CELL, child.getType());

			switch (i) {

			case 0:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 1:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 2:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 3:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 4:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 5:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 6:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 7:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 8:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 9:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 10:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 11:
				assertTrue(child.isFormula());
				assertEquals("0", child.getContent());
				break;

			case 12:
				assertTrue(child.isFormula());
				assertEquals("12", child.getContent());
				break;

			default:
				break;
			}
		}
	}

}
