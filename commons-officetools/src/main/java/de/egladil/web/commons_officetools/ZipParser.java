// =====================================================
// Projekt: de.egladil.tools.parser
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_officetools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_officetools.exceptions.OfficeToolsSecurityException;

/**
 * ZipParser
 */
public class ZipParser {

	private static final Logger LOG = LoggerFactory.getLogger(ZipParser.class);

	/**
	 * Prüft, ob es sich bei den Daten um eine Zip-Bombe handelt. Dabei wird Zip-Bombe definiert als Zip-Archiv, bei dem
	 * ein Entry auf oberster Ebene entpackt größer wäre als maxLengthExtracted + 1.
	 *
	 * @param  bytes
	 *                                   byte[] die Daten
	 * @param  maxLengthExtracted
	 *                                   int maximale akzeptierte Anzahl extrahierter bytes
	 * @throws IOException
	 * @throws OOParserSecurityException
	 */
	public void checkZipBomb(final byte[] bytes, final int maxLengthExtracted) throws IOException, OfficeToolsSecurityException {

		final long startTime = System.currentTimeMillis();

		final File tempFile = createTempFile(bytes);

		try {

			this.checkZipBomb(tempFile, maxLengthExtracted);
		} finally {

			tempFile.delete();
			final long duration = System.currentTimeMillis() - startTime;
			LOG.info("Ausführungsdauer: {} ms", duration);
		}
	}

	/**
	 * Prüft, ob es sich bei dem File um eine Zip-Bombe handelt. Dabei wird Zip-Bombe definiert als Zip-Archiv, bei dem
	 * ein Entry auf oberster Ebene entpackt größer wäre als maxLengthExtracted + 1.<br>
	 * <br>
	 * <strong>Achtung: </strong>das File muss vom Aufrufer gelöscht werden, wenn nicht mehr benötigt!
	 *
	 * @param  file
	 *                                   File
	 * @param  maxLengthExtracted
	 *                                   int
	 * @throws EgladilParserException
	 * @throws OOParserSecurityException
	 */
	public void checkZipBomb(final File file, final int maxLengthExtracted) throws IOException, OfficeToolsSecurityException {

		final long startTime = System.currentTimeMillis();

		try {

			final ZipFile zipFile = new ZipFile(file);
			@SuppressWarnings("rawtypes")
			final Enumeration entries = zipFile.entries();

			while (entries.hasMoreElements()) {

				final ZipEntry entry = (ZipEntry) entries.nextElement();
				checkEntry(zipFile, entry, maxLengthExtracted);
			}
		} catch (final ZipException e) {

			throw new OfficeToolsSecurityException(
				"Exception Erzeugen eines ZipFiles (vermutlich kein Zip-Archiv): " + e.getMessage(),
				e);
		} finally {

			final long duration = System.currentTimeMillis() - startTime;
			LOG.info("Ausführungsdauer: {} ms", duration);
		}
	}

	private void checkEntry(final ZipFile zipFile, final ZipEntry entry, final int maxLengthExtracted) throws IOException {

		if (entry.isDirectory()) {

			// ignore for the first moment not interested in subdirs because OpenOffice contains contents in first level
		} else {

			try (InputStream in = zipFile.getInputStream(entry)) {

				final byte[] buffer = new byte[1];
				long count = 0;
				int n = 0;
				final long size = entry.getSize();

				while (-1 != (n = in.read(buffer)) && count < size) {

					count += n;

					if (count > maxLengthExtracted + 1) {

						throw new OfficeToolsSecurityException(
							"ZipEntry '" + entry.getName() + "' ist größer als " + maxLengthExtracted
								+ " Bytes mit kleinem Puffer");
					}
				}
			}
		}
	}

	/**
	 * Liest den ZipEntry mit dem gegeben Namen. Falls es den ZipEntry gibt, wird er vor dem Entpacken auf potentielle
	 * Zip-Bombe getestet.
	 *
	 * @param  data
	 * @param  entryName
	 * @param  maxLengthExtracted
	 * @param  encoding
	 *                                   String
	 * @return                           String den Inhalt des ZipEntrys im gegebenen encoding.
	 * @throws IOException
	 * @throws OOParserSecurityException
	 */
	public String getContentOfEntrySafe(final byte[] data, final String entryName, final int maxLengthExtracted, final String encoding) throws IOException, OfficeToolsSecurityException {

		final long startTime = System.currentTimeMillis();

		final File tempFile = createTempFile(data);

		try (final ZipFile zipFile = new ZipFile(tempFile)) {

			@SuppressWarnings("rawtypes")
			final Enumeration entries = zipFile.entries();

			while (entries.hasMoreElements()) {

				final ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entryName.equals(entry.getName())) {

					checkEntry(zipFile, entry, maxLengthExtracted);
					final String content = this.getContent(zipFile, entry, encoding);
					return content;
				}
			}
			throw new OfficeToolsSecurityException(
				"Das Zip-Archiv enthält keinen ZipEntry mit Namen '" + entryName + "' in oberster Ebene.");
		} catch (final ZipException e) {

			throw new OfficeToolsSecurityException(
				"Exception Erzeugen eines ZipFiles (vermutlich kein Zip-Archiv): " + e.getMessage(),
				e);
		} finally {

			tempFile.delete();
			final long duration = System.currentTimeMillis() - startTime;
			LOG.info("Ausführungsdauer: {} ms", duration);
		}
	}

	private String getContent(final ZipFile zipFile, final ZipEntry zipEntry, final String encoding) throws IOException {

		try (InputStream in = zipFile.getInputStream(zipEntry); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, encoding);
			return sw.toString();
		}
	}

	private File createTempFile(final byte[] data) throws IOException {

		final File tempFile = File.createTempFile(UUID.randomUUID().toString().substring(0, 8), ".zip");
		fillTempFile(tempFile, data);
		LOG.debug("TempFile erzeugt: {}", tempFile.getAbsolutePath());
		return tempFile;
	}

	private void fillTempFile(final File tempFile, final byte[] data) throws IOException {

		try (ByteArrayInputStream in = new ByteArrayInputStream(data);
			final FileOutputStream fos = new FileOutputStream(tempFile)) {

			IOUtils.copy(in, fos);
			fos.flush();
			fos.close();
		}
	}
}
