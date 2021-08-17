// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_exceltools.FileType;
import de.egladil.web.commons_exceltools.MSSpreadSheetContentReader;
import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;
import de.egladil.web.commons_exceltools.files.ExcelToolsFileUtils;
import de.egladil.web.commons_exceltools.mappers.RowStringConverter;

/**
 * MSSpreadSheetContentReaderImpl
 */
public class MSSpreadSheetContentReaderImpl implements MSSpreadSheetContentReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(MSSpreadSheetContentReaderImpl.class);

	@Override
	public List<String> readContentAsLines(final File file, final FileType fileType) {

		try (InputStream in = ExcelToolsFileUtils.readFile(file)) {

			return this.readContentAsLines(in, fileType);
		} catch (UnsupportedFileFormatException | IOException e) {

			LOGGER.error("FileType {0}: die  Datei {1} kann nicht verarbeitet werden: {2}", fileType,
				file.getAbsolutePath(), e.getMessage(), e);

			throw new ExceltoolsRuntimeException(
				"Die Datei " + file.getAbsolutePath() + " konnte nicht verarbeitet werden: " + e.getMessage(), e);

		}

	}

	/**
	 * @param  in
	 * @return
	 */
	List<String> readContentAsLines(final InputStream in, final FileType fileType) throws IOException {

		PoiWrapper poiWrapper = PoiWrapper.createByFileType(fileType);

		List<Row> rows = poiWrapper.getRows(in);

		final List<String> result = new ArrayList<>();

		final RowStringConverter rowStringConverter = new RowStringConverter();
		rows.forEach(row -> result.add(rowStringConverter.apply(row)));

		return result;
	}

}