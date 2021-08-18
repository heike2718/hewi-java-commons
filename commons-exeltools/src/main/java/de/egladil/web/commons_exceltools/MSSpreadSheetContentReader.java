// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools;

import java.io.File;
import java.util.List;

import de.egladil.web.commons_exceltools.impl.MSSpreadSheetContentReaderImpl;

/**
 * MSSpreadSheetContentReader
 */
public interface MSSpreadSheetContentReader {

	/**
	 * Liest das xls-File ein und extrahiert den Inhalt der Zeilen, die keine Formel sind, zu semikolonseparierten Strings.
	 *
	 * @param  File
	 *                  das File im Filesystem
	 * @param  fileType
	 *                  FileType
	 * @return          List
	 */
	List<String> readContentAsLines(final File file, FileType fileType);

	/**
	 * Gibt die aktuelle Implementierung dieses Interfaces zurück.
	 *
	 * @return MSSpreadSheetContentReader
	 */
	static MSSpreadSheetContentReader getDefault() {

		return new MSSpreadSheetContentReaderImpl();
	}
}
