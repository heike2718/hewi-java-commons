// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.xls;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import de.egladil.web.commons_officetools.msoffice.excel.PoiWrapper;

/**
 * XlsPoiWrapper
 */
public class XlsPoiWrapper implements PoiWrapper {

	@Override
	public List<Row> getRows(final InputStream in) throws IOException, UnsupportedFileFormatException {

		final List<Row> result = new ArrayList<>();

		try (HSSFWorkbook workbook = new HSSFWorkbook(in)) {

			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();

			iterator.forEachRemaining(row -> result.add(row));
		}

		return result;
	}

}
