// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.Row;

import de.egladil.web.commons_officetools.FileType;
import de.egladil.web.commons_officetools.msoffice.excel.xls.XlsPoiWrapper;
import de.egladil.web.commons_officetools.msoffice.excel.xlsx.XlsxPoiWrapper;

/**
 * PoiWrapper
 */
public interface PoiWrapper {

	/**
	 * Wandelt den InputStream in die Liste der Poi-Rows um.
	 *
	 * @param  in
	 * @return
	 * @throws IOException
	 * @throws UnsupportedFileFormatException
	 */
	List<Row> getRows(final InputStream in) throws IOException, UnsupportedFileFormatException;

	static PoiWrapper createInstance(final FileType fileType) {

		switch (fileType) {

		case EXCEL_ALT:

			return new XlsPoiWrapper();

		case EXCEL_NEU:
			return new XlsxPoiWrapper();

		default:
			throw new IllegalArgumentException("Only Excel stuff is supported.");
		}
	}
}
