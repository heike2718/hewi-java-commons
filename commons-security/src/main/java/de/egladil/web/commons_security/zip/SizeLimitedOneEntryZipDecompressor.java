// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security.zip;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_security.zip.exception.IORuntimeException;
import de.egladil.web.commons_security.zip.exception.SecurityRuntimeException;
import de.egladil.web.commons_security.zip.exception.UnsupportedUnzipOperationException;

/**
 * SizeLimitedOneEntryZipDecompressor decomresses all ZipEntries from a ZipFile unless the cumulated size exceeds a given size
 * limit.
 */
public class SizeLimitedOneEntryZipDecompressor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SizeLimitedOneEntryZipDecompressor.class);

	private final int maximumAllowedDecompressedSize;

	public SizeLimitedOneEntryZipDecompressor(final int maximumAllowedDecompressedSize) {

		this.maximumAllowedDecompressedSize = maximumAllowedDecompressedSize;
	}

	/**
	 * Extracts data from a ZipFile containing only one ZipEntry if the length of the uncomressed byte-Array is not greater than
	 * maximumAllowedDecopressionSize.
	 *
	 * @param  identifier
	 *                    String
	 * @param  data
	 *                    byte[] a ZipFile
	 * @return            byte[]
	 */
	public byte[] unzip(final String identifier, final byte[] data) throws SecurityRuntimeException {

		File tempFile = TempFileHelper.createTempFile(identifier);

		try (InputStream in = new ByteArrayInputStream(data); FileOutputStream out = new FileOutputStream(tempFile)) {

			IOUtils.copy(in, out);

			return this.extractZip(tempFile);

		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);
			throw new SecurityRuntimeException("IOException beim Dekomprimieren: " + e.getMessage());

		} finally {

			TempFileHelper.deleteQuietly(tempFile);
		}
	}

	private byte[] extractZip(final File file) {

		try (ZipFile zipFile = new ZipFile(file)) {

			int zipEntryCount = getEntryCount(zipFile);

			if (zipEntryCount > 1) {

				throw new UnsupportedUnzipOperationException("only ZipFiles with a single entry may be extracted");
			}

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {

				ZipEntry entry = entries.nextElement();

				long uncomressedSize = entry.getSize();

				if (uncomressedSize > maximumAllowedDecompressedSize) {

					throw new UnsupportedUnzipOperationException(
						"only ZipEntries up to " + maximumAllowedDecompressedSize + " bytes may be extracted");
				}

				try (InputStream in = zipFile.getInputStream(entry)) {

					return in.readAllBytes();
				}
			}

			return null;
		} catch (ZipException e) {

			throw new IORuntimeException("Could not unzip this file. Maybe it is not a .zip? " + e.getMessage(), e);
		} catch (IOException e) {

			LOGGER.error("could not create ZipFile: " + e.getMessage(), e);
			throw new IORuntimeException("could not create ZipFile " + e.getMessage(), e);
		}

	}

	int getEntryCount(final ZipFile zipFile) {

		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		int result = 0;

		while (entries.hasMoreElements()) {

			result++;
			entries.nextElement();
		}

		return result;
	}
}
