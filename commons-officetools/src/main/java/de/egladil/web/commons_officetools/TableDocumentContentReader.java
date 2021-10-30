// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import de.egladil.web.commons_officetools.msoffice.excel.MSSpreadSheetContentReader;
import de.egladil.web.commons_officetools.openoffice.ods.ODSContentReader;

/**
 * TableDocumentContentReader
 */
public interface TableDocumentContentReader {

	String SEPARATION_CHAR = ";";

	/**
	 * Extrahiert aus einem Tabellenkalulationsdokument alle Strings und numerischen Inhalte aller Zeile als
	 * semikolenseparierte Liste von Strings. So ähnlich wie Python Pandas.
	 *
	 * @param  pathFile
	 *                  String absolute path to file
	 * @return          List
	 */
	List<String> readContentAsLines(final String pathFile);

	/**
	 * Extrahiert aus einem Tabellenkalulationsdokument alle Strings und numerischen Inhalte aller Zeile als
	 * semikolenseparierte Liste von Strings. So ähnlich wie Python Pandas.
	 *
	 * @param  file
	 *              File
	 * @return      List
	 */
	List<String> readContentAsLines(final File file);

	/**
	 * Extrahiert aus einem Tabellenkalulationsdokument alle Strings und numerischen Inhalte aller Zeile als
	 * semikolenseparierte Liste von Strings. So ähnlich wie Python Pandas.
	 *
	 * @param  in
	 *            InputStream
	 * @return    List
	 */
	List<String> readContentAsLines(final InputStream in);

	/**
	 * Gibt den passenden ContentReader zurück.
	 *
	 * @param  fileType
	 * @return
	 */
	static TableDocumentContentReader getContentReader(final FileType fileType) {

		switch (fileType) {

		case EXCEL_ALT:
		case EXCEL_NEU:
			return new MSSpreadSheetContentReader(fileType);

		case ODS:
			return new ODSContentReader();

		default:
			throw new IllegalArgumentException(
				"Only MSOffice, OpenOffice or LibreOffice table documents are supported.");
		}
	}
}
