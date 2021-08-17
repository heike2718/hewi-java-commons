// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * ExcelToolsFileUtilsTest
 */
public class ExcelToolsFileUtilsTest {

	@Test
	void should_throwIOException_when_fileDoesNotExist() {

		File file = new File("/home/heike/bla.txt");

		try {

			ExcelToolsFileUtils.readFile(file);
			fail("keine IOException");
		} catch (IOException e) {

			assertEquals("/home/heike/bla.txt (Datei oder Verzeichnis nicht gefunden)", e.getMessage());
		}
	}

	@Test
	void should_throwIOException_when_fileCantRead() {

		File file = new File("/home/heike/mkv/auswertungen-testdaten/hidden.file");

		try {

			ExcelToolsFileUtils.readFile(file);
			fail("keine IOException");
		} catch (IOException e) {

			assertEquals("/home/heike/mkv/auswertungen-testdaten/hidden.file (Keine Berechtigung)",
				e.getMessage());
		}
	}

	@Test
	void should_throwIOException_when_fileIsDirectory() {

		File file = new File("/home/heike");

		try {

			ExcelToolsFileUtils.readFile(file);
			fail("keine IOException");
		} catch (IOException e) {

			assertEquals("/home/heike (Ist ein Verzeichnis)", e.getMessage());
		}
	}

	@Test
	void should_throwIOException_when_fileNull() {

		File file = null;

		try {

			ExcelToolsFileUtils.readFile(file);
			fail("keine IOException");
		} catch (IOException e) {

			assertEquals("file is null", e.getMessage());
		}
	}

}
