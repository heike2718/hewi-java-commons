// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.xlsx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * XlsxEncodingDetectorTest
 */
public class XlsxEncodingDetectorTest {

	@Test
	void should_detectEncodingReturnUtf8_when_UTF8() throws Exception {

		// Arrange
		File file = new File("/home/heike/mkv/upload/original-files/klassenlisten/klassenliste.xlsx");
		assertTrue(file.isFile());
		assertTrue(file.canWrite());
		assertTrue(file.canRead());

		// Act
		Optional<String> optEncoding = new XlsxEncodingDetector().detectEncoding(file.getAbsolutePath());

		// Assert
		assertEquals("UTF-8", optEncoding.get());

	}

	@Test
	void should_detectEncoding_when_standard() throws Exception {

		// Arrange
		File file = new File(
			"/home/heike/git/hewi-java-commons/java-17/hewi-java-commons/commons-officetools/src/test/resources/msoffice/excel-codepages/excel-codepage-test-01.xlsx");
		assertTrue(file.isFile());
		assertTrue(file.canWrite());
		assertTrue(file.canRead());

		// Act
		Optional<String> optEncoding = new XlsxEncodingDetector().detectEncoding(file.getAbsolutePath());

		// Assert
		assertEquals("UTF-8", optEncoding.get());

	}

	@Test
	void should_detectEncoding_when_us_ascii() throws Exception {

		// Arrange
		File file = new File(
			"/home/heike/git/hewi-java-commons/java-17/hewi-java-commons/commons-officetools/src/test/resources/msoffice/excel-codepages/excel-codepage-test-05-US-ASCII.xlsx");
		assertTrue(file.isFile());
		assertTrue(file.canWrite());
		assertTrue(file.canRead());

		// Act
		Optional<String> optEncoding = new XlsxEncodingDetector().detectEncoding(file.getAbsolutePath());

		// Assert
		assertEquals("UTF-8", optEncoding.get());

	}

}
