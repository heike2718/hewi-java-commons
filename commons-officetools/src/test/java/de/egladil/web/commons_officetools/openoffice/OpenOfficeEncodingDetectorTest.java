// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.openoffice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * OpenOfficeEncodingDetectorTest
 */
public class OpenOfficeEncodingDetectorTest {

	@Test
	void should_detectEncodingReturnUtf8_when_utf8() {

		// Arrange
		File file = new File("/home/heike/mkv/upload/original-files/klassenlisten/klassenliste.ods");

		// Act
		Optional<String> optEncoding = new OpenOfficeEncodingDetector().detectEncoding(file.getAbsolutePath());

		// Assert
		assertTrue(optEncoding.isPresent());
		assertEquals("UTF-8", optEncoding.get());

	}

}
