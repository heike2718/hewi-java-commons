// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.xls;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * XlsEncodingDetectorTest
 */
public class XlsEncodingDetectorTest {

	@Test
	void should_detectEncodingReturnEmptyOptional() throws Exception {

		// Arrange
		File file = new File("/home/heike/mkv/upload/original-files/klassenlisten/klassenliste.xls");
		assertTrue(file.isFile());
		assertTrue(file.canWrite());
		assertTrue(file.canRead());

		// Act
		Optional<String> optEncoding = new XlsEncodingDetector().detectEncoding(file.getAbsolutePath());

		// Assert
		assertTrue(optEncoding.isEmpty());

	}

}
