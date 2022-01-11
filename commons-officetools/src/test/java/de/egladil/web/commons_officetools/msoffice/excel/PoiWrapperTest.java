// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_officetools.FileType;
import de.egladil.web.commons_officetools.msoffice.excel.xls.XlsPoiWrapper;
import de.egladil.web.commons_officetools.msoffice.excel.xlsx.XlsxPoiWrapper;

/**
 * PoiWrapperTest
 */
public class PoiWrapperTest {

	@Test
	void should_createInstanceReturnTheCorrectType_when_ExcelAlt() {

		// Act
		PoiWrapper result = PoiWrapper.createInstance(FileType.EXCEL_ALT);

		// Assert
		assertTrue(result instanceof XlsPoiWrapper);
	}

	@Test
	void should_createInstanceReturnTheCorrectType_when_ExcelNeu() {

		// Act
		PoiWrapper result = PoiWrapper.createInstance(FileType.EXCEL_NEU);

		// Assert
		assertTrue(result instanceof XlsxPoiWrapper);
	}

	@Test
	void should_createInstanceThrowException_when_FileTypeODT() {

		final Throwable ex = assertThrows(IllegalArgumentException.class, () -> {

			PoiWrapper.createInstance(FileType.ODT);
		});

		assertEquals("Only Excel stuff is supported.", ex.getMessage());
	}

	@Test
	void should_createInstanceThrowException_when_FileTypeODS() {

		final Throwable ex = assertThrows(IllegalArgumentException.class, () -> {

			PoiWrapper.createInstance(FileType.ODS);
		});

		assertEquals("Only Excel stuff is supported.", ex.getMessage());
	}

}
