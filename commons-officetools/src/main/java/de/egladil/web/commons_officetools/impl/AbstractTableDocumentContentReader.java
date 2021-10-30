// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.egladil.web.commons_officetools.TableDocumentContentReader;
import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;
import de.egladil.web.commons_officetools.files.OfficeToolsFileUtils;

/**
 * AbstractTableDocumentContentReader
 */
public abstract class AbstractTableDocumentContentReader implements TableDocumentContentReader {

	@Override
	public List<String> readContentAsLines(final String pathFile) {

		return readContentAsLines(new File(pathFile));
	}

	@Override
	public List<String> readContentAsLines(final File file) {

		try (InputStream in = OfficeToolsFileUtils.readFile(file)) {

			return readContentAsLines(in);

		} catch (IOException e) {

			throw new OfficeToolsRuntimeException("Konnte Datei nicht parsen: " + e.getMessage(), e);
		}
	}

	@Override
	public abstract List<String> readContentAsLines(InputStream in);

}
