// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;

/**
 * ConvertscriptGenerator generiert ein excel2csv-Pythonscript welches ein gegebes ExcelFile in ein CSV-File umwandeln kann.
 */
public class ConvertscriptGenerator {

	private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertscriptGenerator.class);

	/**
	 * Generiert ein py-Script-File welches den Inhalt eines ExcelFiles in ein CVS-File schreiben kann.
	 *
	 * @param  pathWorkdir
	 *                                    String pfad zum Arbeitsverzeichnis, in dem das Excel-File liegt.
	 * @param  nameExcelFile
	 *                                    String name des ExcelFiles
	 * @return                            File left ist das py-File
	 *                                    sollen.
	 * @throws ExceltoolsRuntimeException
	 */
	public File generatePyFile(final String pathWorkdir, final String nameExcelFile, final String pathOutputFile) throws ExceltoolsRuntimeException {

		String substring = UUID.randomUUID().toString().substring(0, 8);
		String namePyFile = "excel2csv-" + substring + ".py";
		String pathExcelFile = pathWorkdir + File.separator + nameExcelFile;

		File pyFile = new File(pathWorkdir + File.separator + namePyFile);

		try (InputStream in = getClass().getResourceAsStream("/excel2csv.py.template");
			StringWriter sw = new StringWriter();
			OutputStream out = new FileOutputStream(pyFile)) {

			IOUtils.copy(in, sw, CHARSET_UTF_8);

			String content = sw.toString().replace("#0#", pathExcelFile);
			content = content.replace("#1#", pathOutputFile);

			IOUtils.write(content, out, CHARSET_UTF_8);
			out.flush();

			return pyFile;

		} catch (IOException e) {

			String msg = "Konnte kein py-Script generieren: " + e.getMessage();
			LOGGER.error(msg, e);
			throw new ExceltoolsRuntimeException(msg, e);
		}

	}

}
