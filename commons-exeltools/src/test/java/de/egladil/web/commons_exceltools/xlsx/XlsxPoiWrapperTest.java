// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.xlsx;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
