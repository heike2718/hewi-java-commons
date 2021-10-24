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
	 * Liest das xls-File ein und extrahiert den Inhalt der Zeilen, die keine Formel sind, zu semikolonseparierten Strings.<br>
	 * <br>
	 * <strong>Achtung: </strong>
	 * <ul>
	 * <li>Leere Zeilen werden als leerer String extrahiert</li><br>
	 * <li>Numerische Zellen werden als double-String extrahiert, enthalten also einen Dezimalpunkt. Eine Konvertierung in einen
	 * anderen numerischen Typ oder einen String ohne Dezimalpunkt muss der Aufrufer
	 * vornehmen</li><br>
	 * <li>Boolsche Werte werden als Formel interpretiert und daher nicht extrahiert!!!</li>
	 * </ul>
	 *
	 * @param  fileType
	 *                  FileType
	 * @param  File
	 *                  das File im Filesystem
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
