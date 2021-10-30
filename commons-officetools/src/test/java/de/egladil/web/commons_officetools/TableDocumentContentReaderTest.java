// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_officetools.msoffice.excel.MSSpreadSheetContentReader;
import de.egladil.web.commons_officetools.openoffice.ods.ODSContentReader;

/**
 * TableDocumentContentReaderTest
 */
public class TableDocumentContentReaderTest {

	@Test
	void should_getDocumentReaderReturnODS_when_FileTypeODS() {

		// Act
		TableDocumentContentReader reader = TableDocumentContentReader.getContentReader(FileType.ODS);

		// Assert
		assertTrue(reader instanceof ODSContentReader);
	}

	@Test
	void should_getDocumentReaderReturnMSSpreadsheet_when_FileTypeExcelAlt() {

		// Act
		TableDocumentContentReader reader = TableDocumentContentReader.getContentReader(FileType.EXCEL_ALT);

		// Assert
		assertTrue(reader instanceof MSSpreadSheetContentReader);
	}

	@Test
	void should_getDocumentReaderReturnMSSpreadsheet_when_FileTypeExcelNeu() {

		// Act
		TableDocumentContentReader reader = TableDocumentContentReader.getContentReader(FileType.EXCEL_NEU);

		// Assert
		assertTrue(reader instanceof MSSpreadSheetContentReader);
	}

	@Test
	void should_getDocumentReaderThrowException_when_FileTypeODS() {

		final Throwable ex = assertThrows(IllegalArgumentException.class, () -> {

			TableDocumentContentReader.getContentReader(FileType.ODT);
		});

		assertEquals("Only MSOffice, OpenOffice or LibreOffice table documents are supported.", ex.getMessage());
	}

}
