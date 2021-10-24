// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.xlsx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_exceltools.FileType;

/**
 * XlsxPoiWrapperTest
 */
public class XlsxPoiWrapperTest {

	@Test
	void should_returnTheExpectedFileType() {

		assertEquals(FileType.EXCEL_NEU, new XlsxPoiWrapper().wrapperFor());

	}

	@Test
	void should_detectEncodingReturnUtf8_when_UTF8() throws Exception {

		// Arrange
		File file = new File("/home/heike/mkv/upload/original-files/klassenlisten/klassenliste.xlsx");
		assertTrue(file.isFile());
		assertTrue(file.canWrite());
		assertTrue(file.canRead());

		// Act
		Optional<String> optEncoding = new XlsxPoiWrapper().detectEncoding(file.getAbsolutePath());

		// Assert
		assertEquals("UTF-8", optEncoding.get());

	}

}
