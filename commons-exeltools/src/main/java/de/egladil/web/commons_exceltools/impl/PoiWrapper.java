// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;

import de.egladil.web.commons_exceltools.FileType;
import de.egladil.web.commons_exceltools.xls.XlsPoiWrapper;
import de.egladil.web.commons_exceltools.xlsx.XlsxPoiWrapper;

/**
 * PoiWrapper
 */
public interface PoiWrapper {

	/**
	 * @param  in
	 *            InputStream von einem xls- oder xlsx-File.
	 * @return    List
	 */
	List<Row> getRows(InputStream in) throws IOException;

	/**
	 * Ermittelt das Encoding.
	 *
	 * @param  pathOfFile
	 *                    String absoluter Pfad der Datei.
	 * @return            Optional
	 */
	Optional<String> detectEncoding(String pathOfFile);

	/**
	 * Teilt mit, für welchen FileType dies ein Wrapper ist. Ist nützlich für Tests.
	 *
	 * @return
	 */
	FileType wrapperFor();

	static PoiWrapper createByFileType(final FileType fileType) {

		if (fileType == null) {

			throw new IllegalArgumentException("fileType is null");
		}

		switch (fileType) {

		case EXCEL_ALT:
			return new XlsPoiWrapper();

		case EXCEL_NEU:
			return new XlsxPoiWrapper();

		default:
			break;
		}

		throw new IllegalArgumentException("unbekannter FileType " + fileType);
	}
}
