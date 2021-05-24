// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_security.zip.exception.IORuntimeException;
import de.egladil.web.commons_security.zip.exception.SecurityRuntimeException;

/**
 * GZipDecompressor
 */
public class GZipDecompressor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GZipDecompressor.class);

	private final int maximumAllowedDecompressedSize;

	public GZipDecompressor(final int maximumAllowedDecompressedSize) {

		super();
		this.maximumAllowedDecompressedSize = maximumAllowedDecompressedSize;
	}

	public void decompressToFile(final byte[] data, final File result) {

		int byteCount = 0;

		try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(data));
			FileOutputStream out = new FileOutputStream(result)) {

			byte[] buf = new byte[1024 * 4];
			int len = 0;

			while ((len = in.read(buf)) > 0) {

				out.write(buf, 0, len);
				byteCount += len;

				if (byteCount >= maximumAllowedDecompressedSize) {

					out.flush();

					TempFileHelper.deleteQuietly(result);

					String msg = "decomressed data exceed limit of " + maximumAllowedDecompressedSize + " byte";
					LOGGER.warn(msg);
					throw new SecurityRuntimeException(msg);

				}
			}

		} catch (IOException e) {

			throw new IORuntimeException("Exception while extracting gzip: " + e.getMessage(), e);
		}
	}

	/**
	 * @param  data
	 * @return
	 */
	public byte[] decompressToByteArray(final byte[] data) {

		int byteCount = 0;

		try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(data));
			ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			byte[] buf = new byte[1024 * 4];
			int len = 0;

			while ((len = in.read(buf)) > 0) {

				out.write(buf, 0, len);
				byteCount += len;

				if (byteCount >= maximumAllowedDecompressedSize) {

					out.flush();

					String msg = "decomressed data exceed limit of " + maximumAllowedDecompressedSize + " byte";
					LOGGER.warn(msg);
					throw new SecurityRuntimeException(msg);

				}
			}

			return out.toByteArray();

		} catch (IOException e) {

			throw new IORuntimeException("Exception while extracting gzip: " + e.getMessage(), e);
		}
	}

}
