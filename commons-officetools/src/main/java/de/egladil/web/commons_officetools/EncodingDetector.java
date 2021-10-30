// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools;

import java.util.Optional;

import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;
import de.egladil.web.commons_officetools.msoffice.excel.xls.XlsEncodingDetector;
import de.egladil.web.commons_officetools.msoffice.excel.xlsx.XlsxEncodingDetector;
import de.egladil.web.commons_officetools.openoffice.OpenOfficeEncodingDetector;

/**
 * EncodingDetector
 */
public interface EncodingDetector {

	/**
	 * Ermittelt das Encoding des gegebenen Files, falls es dazu irgendeinen Hinweis gibt.
	 *
	 * @param  pathToFile
	 * @return            Optional
	 */
	Optional<String> detectEncoding(final String pathToFile);

	/**
	 * Gibt den für den FileType zuständigen EncodingDetector zurück.
	 *
	 * @param  fileType
	 * @return
	 */
	static EncodingDetector getEncodingDetector(final FileType fileType) {

		switch (fileType) {

		case EXCEL_ALT:
			return new XlsEncodingDetector();

		case EXCEL_NEU:
			return new XlsxEncodingDetector();

		case ODS:
		case ODT:
			return new OpenOfficeEncodingDetector();

		default:
			throw new OfficeToolsRuntimeException("Für " + fileType + " gibt es noch keinen EncodingDetector");
		}
	}
}
