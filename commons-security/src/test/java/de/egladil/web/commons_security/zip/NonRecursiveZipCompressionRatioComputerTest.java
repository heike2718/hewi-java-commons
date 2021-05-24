// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_security.TestUtils;

/**
 * NonRecursiveZipCompressionRatioComputerTest
 */
public class NonRecursiveZipCompressionRatioComputerTest {

	private static final String OWNER_ID = "jlhafjlhl";

	@Test
	void should_getCompressionRatioReturnExpectedResult() throws Exception {

		// Arrange never-ever-try-to-decompress.txt
		String fileName = "zipbomb-decompress-at-your-own-risk.txt";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(1009), Long.valueOf(compressionRatio));
	}

	@Test
	void should_getCompressionRatioReturnExpectedResult_when_veryDangourusZipBomb() throws Exception {

		// Arrange
		String fileName = "never-ever-try-to-decompress.txt";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(128883), Long.valueOf(compressionRatio));
	}

	@Test
	void compressionRatioOfExcelFile() throws Exception {

		// Arrange
		String fileName = "excel.zip";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(4), Long.valueOf(compressionRatio));
	}

	@Test
	void compressionRatioOfOdsFile() throws Exception {

		// Arrange
		String fileName = "ods.zip";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(1), Long.valueOf(compressionRatio));
	}

	@Test
	void compressionRatioOfPdfWithTextFile() throws Exception {

		// Arrange
		String fileName = "pdf-text.zip";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(1), Long.valueOf(compressionRatio));
	}

	@Test
	void compressionRatioOfJpgFile() throws Exception {

		// Arrange
		String fileName = "jpg.zip";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(1), Long.valueOf(compressionRatio));
	}

	@Test
	void compressionRatioOfZipWithGZip() throws Exception {

		// Arrange
		String fileName = "zip-with-gzip.zip";
		byte[] upload = TestUtils.readBytesFromClasspath("/" + fileName);

		// Act
		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(OWNER_ID, upload);

		// Assert
		assertEquals(Long.valueOf(0), Long.valueOf(compressionRatio));
	}
}
