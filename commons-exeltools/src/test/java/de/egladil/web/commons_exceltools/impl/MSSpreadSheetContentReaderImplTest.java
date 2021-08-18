// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_exceltools.FileType;
import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;

/**
 * MSSpreadSheetContentReaderImplTest
 */
public class MSSpreadSheetContentReaderImplTest {

	@Nested
	class XLSTests {

		@Test
		void should_readLines() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/auswertung.xls")) {

				List<String> lines = new MSSpreadSheetContentReaderImpl().readContentAsLines(in, FileType.EXCEL_ALT);

				assertEquals(33, lines.size());

				lines.forEach(l -> System.out.println(l));
			}

		}

		@Test
		void should_readLinesThrowException_when_xlsFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/auswertung.xlsx")) {

				new MSSpreadSheetContentReaderImpl().readContentAsLines(in, FileType.EXCEL_ALT);
				fail("keine OfficeXmlFileException");
			} catch (OfficeXmlFileException e) {

				assertEquals(
					"The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)",
					e.getMessage());
			}

		}

		@Test
		void should_readLinesThrowException_when_notASpreadSheetFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/readme.txt")) {

				new MSSpreadSheetContentReaderImpl().readContentAsLines(in, FileType.EXCEL_ALT);
				fail("keine NotOLE2FileException");
			} catch (NotOLE2FileException e) {

				assertEquals(
					"Invalid header signature; read 0x61206E6564726577, expected 0xE11AB1A1E011CFD0 - Your file appears not to be a valid OLE2 document",
					e.getMessage());
			}

		}

	}

	@Nested
	class XLSXTests {

		@Test
		void should_convertAnExistingFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/auswertung.xlsx")) {

				List<String> lines = new MSSpreadSheetContentReaderImpl().readContentAsLines(in, FileType.EXCEL_NEU);

				assertEquals(123, lines.size());

				lines.forEach(l -> System.out.println(l));

			}

		}

		@Test
		void should_readLinesThrowException_when_xlsxFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/auswertung.xls")) {

				new MSSpreadSheetContentReaderImpl().readContentAsLines(in, FileType.EXCEL_NEU);
				fail("keine NotOfficeXmlFileException");
			} catch (NotOfficeXmlFileException e) {

				assertEquals(
					"The supplied data appears to be in the OLE2 Format. You are calling the part of POI that deals with OOXML (Office Open XML) Documents. You need to call a different part of POI to process this data (eg HSSF instead of XSSF)",
					e.getMessage());
			}

		}

		@Test
		void should_readLinesThrowException_when_notASpreadSheetFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/readme.txt")) {

				new MSSpreadSheetContentReaderImpl().readContentAsLines(in, FileType.EXCEL_NEU);
				fail("keine NotOfficeXmlFileException");
			} catch (NotOfficeXmlFileException e) {

				assertEquals(
					"No valid entries or contents found, this is not a valid OOXML (Office Open XML) file", e.getMessage());
			}

		}

	}

	@Nested
	class ExceptionHandlingTests {

		@Test
		void should_readLinesThrowException_whenIOException() {

			File file = new File(
				"/home/heike/git/testdaten/minikaenguru/auswertungen/fehlerhaft/upload/mit-ueberschrift-fehlerhaft.csv/");

			try {

				new MSSpreadSheetContentReaderImpl().readContentAsLines(file, FileType.EXCEL_ALT);
				fail("keine ExceltoolsRuntimeException");
			} catch (ExceltoolsRuntimeException e) {

				assertEquals(
					"Die Datei /home/heike/git/testdaten/minikaenguru/auswertungen/fehlerhaft/upload/mit-ueberschrift-fehlerhaft.csv konnte nicht verarbeitet werden: Invalid header signature; read 0x312D413B656D614E, expected 0xE11AB1A1E011CFD0 - Your file appears not to be a valid OLE2 document",
					e.getMessage());
			}
		}

		@Test
		void should_readLinesThrowException_whenOpenOfficeFile() {

			File file = new File("/home/heike/git/testdaten/minikaenguru/auswertungen/korrekt/upload/auswertung.ods");

			try {

				new MSSpreadSheetContentReaderImpl().readContentAsLines(file, FileType.EXCEL_ALT);
				fail("keine ExceltoolsRuntimeException");
			} catch (ExceltoolsRuntimeException e) {

				assertEquals(
					"Die Datei /home/heike/git/testdaten/minikaenguru/auswertungen/korrekt/upload/auswertung.ods konnte nicht verarbeitet werden: The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)",
					e.getMessage());
			}
		}
	}

}
