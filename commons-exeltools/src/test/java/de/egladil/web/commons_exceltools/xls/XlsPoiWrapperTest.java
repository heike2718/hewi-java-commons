// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.xls;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_exceltools.FileType;

/**
 * XlsPoiWrapperTest
 */
public class XlsPoiWrapperTest {

	@Test
	void should_returnTheExpectedFileType() {

		assertEquals(FileType.EXCEL_ALT, new XlsPoiWrapper().wrapperFor());

	}
}
