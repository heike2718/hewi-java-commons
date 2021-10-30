// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_officetools.FileType;
import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;

/**
 * MSSpreadSheetContentReaderTest
 */
public class MSSpreadSheetContentReaderTest {

	@Nested
	class XLSTests {

		@Test
		void should_readLines() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/auswertung.xls")) {

				List<String> lines = new MSSpreadSheetContentReader(FileType.EXCEL_ALT).readContentAsLines(in);

				assertEquals(33, lines.size());

				lines.forEach(l -> System.out.println(l));
			}

		}

		@Test
		void should_convertAKlassenlisteFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/klassenliste.xls")) {

				List<String> lines = new MSSpreadSheetContentReader(FileType.EXCEL_ALT).readContentAsLines(in);

				assertEquals(122, lines.size());

				lines.forEach(l -> System.out.println(l));

				for (int i = 0; i < lines.size(); i++) {

					String[] tokens = lines.get(i).split(";");

					if (i <= 8) {

						assertEquals(4, tokens.length, "Fehler bei Index " + i);

					} else {

						assertEquals(1, tokens.length, "Fehler bei Index " + i);
						assertTrue(StringUtils.isBlank(tokens[0]));
					}

				}

			}

		}

		@Test
		void should_convertMixedTypesWork() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/mixed-celltypes.xls")) {

				List<String> zeilen = new MSSpreadSheetContentReader(FileType.EXCEL_ALT).readContentAsLines(in);

				// Assert
				assertEquals(2, zeilen.size());

				zeilen.forEach(z -> System.out.println(z));

				{

					String[] tokens = zeilen.get(0).split(";");
					assertEquals(4, tokens.length);
				}

				{

					String[] tokens = zeilen.get(1).split(";");
					assertEquals(2, tokens.length);
				}
			}
		}

		@Test
		void should_readLinesThrowException_when_xlsFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/auswertung.xlsx")) {

				new MSSpreadSheetContentReader(FileType.EXCEL_ALT).readContentAsLines(in);
				fail("keine OfficeToolsRuntimeException");
			} catch (OfficeToolsRuntimeException e) {

				assertEquals(
					"Fehler beim Lesen einer MSOffice-Datei: The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)",
					e.getMessage());
			}

		}

		@Test
		void should_readLinesThrowException_when_notASpreadSheetFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/readme.txt")) {

				new MSSpreadSheetContentReader(FileType.EXCEL_ALT).readContentAsLines(in);
				fail("keine OfficeToolsRuntimeException");
			} catch (OfficeToolsRuntimeException e) {

				assertEquals(
					"Fehler beim Lesen einer MSOffice-Datei: Invalid header signature; read 0x61206E6564726577, expected 0xE11AB1A1E011CFD0 - Your file appears not to be a valid OLE2 document",
					e.getMessage());
			}

		}

	}

	@Nested
	class XLSXTests {

		@Test
		void should_convertAnExistingFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/auswertung.xlsx")) {

				List<String> lines = new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(in);

				assertEquals(123, lines.size());

				lines.forEach(l -> System.out.println(l));

			}

		}

		@Test
		void should_readLinesThrowException_when_xlsxFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/auswertung.xls")) {

				new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(in);
				fail("keine OfficeToolsRuntimeException");
			} catch (OfficeToolsRuntimeException e) {

				assertEquals(
					"Fehler beim Lesen einer MSOffice-Datei: The supplied data appears to be in the OLE2 Format. You are calling the part of POI that deals with OOXML (Office Open XML) Documents. You need to call a different part of POI to process this data (eg HSSF instead of XSSF)",
					e.getMessage());
			}

		}

		@Test
		void should_convertAKlassenlisteFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/klassenliste.xlsx")) {

				List<String> lines = new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(in);

				assertEquals(107, lines.size());

				lines.forEach(l -> System.out.println(l));

				for (int i = 0; i < lines.size(); i++) {

					String[] tokens = lines.get(i).split(";");

					if (i <= 13) {

						assertEquals(4, tokens.length, "Fehler bei Index " + i);

					} else {

						assertEquals(1, tokens.length, "Fehler bei Index " + i);
						assertTrue(StringUtils.isBlank(tokens[0]));
					}

				}

			}

		}

		@Test
		void should_convert_whenJiraExport() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/excel-codepages/excel-ecncoding-test.xlsx")) {

				List<String> lines = new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(in);

				assertEquals(19, lines.size());

				lines.forEach(l -> System.out.println(l));
			}

		}

		@Test
		void should_convertMixedTypesWork() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/mixed-celltypes.xlsx")) {

				List<String> zeilen = new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(in);

				// Assert
				assertEquals(2, zeilen.size());

				zeilen.forEach(z -> System.out.println(z));

				{

					String[] tokens = zeilen.get(0).split(";");
					assertEquals(4, tokens.length);
				}

				{

					String[] tokens = zeilen.get(1).split(";");
					assertEquals(2, tokens.length);
				}
			}
		}

		@Test
		void should_readLinesThrowException_when_notASpreadSheetFile() throws IOException {

			try (InputStream in = getClass().getResourceAsStream("/msoffice/readme.txt")) {

				new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(in);
				fail("keine OfficeToolsRuntimeException");
			} catch (OfficeToolsRuntimeException e) {

				assertEquals(
					"Fehler beim Lesen einer MSOffice-Datei: No valid entries or contents found, this is not a valid OOXML (Office Open XML) file",
					e.getMessage());
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

				new MSSpreadSheetContentReader(FileType.EXCEL_NEU).readContentAsLines(file);
				fail("keine ExceltoolsRuntimeException");
			} catch (OfficeToolsRuntimeException e) {

				assertEquals(
					"Fehler beim Lesen einer MSOffice-Datei: No valid entries or contents found, this is not a valid OOXML (Office Open XML) file",
					e.getMessage());
			}
		}

		@Test
		void should_readLinesThrowException_whenOpenOfficeFile() {

			File file = new File("/home/heike/git/testdaten/minikaenguru/auswertungen/korrekt/upload/auswertung.ods");

			try {

				new MSSpreadSheetContentReader(FileType.EXCEL_ALT).readContentAsLines(file);
				fail("keine ExceltoolsRuntimeException");
			} catch (OfficeToolsRuntimeException e) {

				assertEquals(
					"Fehler beim Lesen einer MSOffice-Datei: The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)",
					e.getMessage());
			}
		}
	}

}
