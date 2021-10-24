// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.xls;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_exceltools.FileType;
import de.egladil.web.commons_exceltools.impl.PoiWrapper;

/**
 * XlsPoiWrapper
 */
public class XlsPoiWrapper implements PoiWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(XlsPoiWrapper.class);

	@Override
	public FileType wrapperFor() {

		return FileType.EXCEL_ALT;
	}

	@Override
	public List<Row> getRows(final InputStream in) throws IOException {

		final List<Row> result = new ArrayList<>();

		try (HSSFWorkbook workbook = new HSSFWorkbook(in)) {

			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();

			iterator.forEachRemaining(row -> result.add(row));
		}

		return result;
	}

	@Override
	public Optional<String> detectEncoding(final String pathOfFile) {

		LOGGER.warn("Encoding eines File mit FileType {} kann nicht ermittelt werden", wrapperFor());

		return Optional.empty();
	}
}
