// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.xlsx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.egladil.web.commons_officetools.msoffice.excel.PoiWrapper;

/**
 * XlsxPoiWrapper
 */
public class XlsxPoiWrapper implements PoiWrapper {

	@Override
	public List<Row> getRows(final InputStream in) throws IOException, UnsupportedFileFormatException {

		final List<Row> result = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(in)) {

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.iterator();
			iterator.forEachRemaining(row -> result.add(row));
		}

		return result;
	}

}
