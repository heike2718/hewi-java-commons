// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import de.egladil.web.commons_officetools.FileType;
import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;
import de.egladil.web.commons_officetools.impl.AbstractTableDocumentContentReader;
import de.egladil.web.commons_officetools.msoffice.excel.mappers.RowStringConverter;

/**
 * MSSpreadSheetContentReader
 */
public class MSSpreadSheetContentReader extends AbstractTableDocumentContentReader {

	private final FileType fileType;

	public MSSpreadSheetContentReader(final FileType fileType) {

		this.fileType = fileType;
	}

	@Override
	public List<String> readContentAsLines(final InputStream in) {

		try {

			PoiWrapper poiWrapper = PoiWrapper.createInstance(fileType);

			List<Row> rows = poiWrapper.getRows(in);

			final List<String> result = new ArrayList<>();

			final RowStringConverter rowStringConverter = new RowStringConverter();
			rows.forEach(row -> result.add(rowStringConverter.apply(row)));

			return result;
		} catch (Exception e) {

			throw new OfficeToolsRuntimeException("Fehler beim Lesen einer MSOffice-Datei: " + e.getMessage(), e);
		}
	}
}
