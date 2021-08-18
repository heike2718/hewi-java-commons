// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_exceltools.FileType;

/**
 * PoiWrapperTest
 */
public class PoiWrapperTest {

	@Test
	void should_createReturnTheCorrectImpl_when_typeExcelAlt() {

		// Arrange
		FileType fileType = FileType.EXCEL_ALT;

		// Act
		PoiWrapper wrapper = PoiWrapper.createByFileType(fileType);

		// Assert
		assertEquals(fileType, wrapper.wrapperFor());
	}

	@Test
	void should_createReturnTheCorrectImpl_when_typeExcelNeu() {

		// Arrange
		FileType fileType = FileType.EXCEL_NEU;

		// Act
		PoiWrapper wrapper = PoiWrapper.createByFileType(fileType);

		// Assert
		assertEquals(fileType, wrapper.wrapperFor());
	}

	@Test
	void should_createThrowIllegalArgumentException_when_FileTypeNull() {

		try {

			PoiWrapper.createByFileType(null);

			fail("keine IllegalArgumentException");
		} catch (IllegalArgumentException e) {

			assertEquals("fileType is null", e.getMessage());
		}
	}

}
