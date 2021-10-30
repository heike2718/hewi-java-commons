// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * OfficeToolsFileUtils
 */
public class OfficeToolsFileUtils {

	/**
	 * Wandelt den Inhalt des Files in einen InputStream um. Der Aufrufer ist verantworlich für das Schließen des InputStreams.
	 *
	 * @param  file
	 *              File
	 * @return      InputStream
	 */
	public static InputStream readFile(final File file) throws IOException {

		if (file == null) {

			throw new IOException("file is null");
		}

		return FileUtils.openInputStream(file);
	}
}
