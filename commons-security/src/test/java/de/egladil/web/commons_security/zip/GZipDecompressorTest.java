// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_security.TestUtils;
import de.egladil.web.commons_security.zip.exception.SecurityRuntimeException;

/**
 * GZipDecompressorTest
 */
public class GZipDecompressorTest {

	// @Test
	void createLargeGZIP() throws IOException {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 10; i++) {

			for (int k = 0; k < 1073; k++) {

				sb.append("0");
			}
		}

		File result = File.createTempFile("large-gzip-", ".gz");

		byte[] data = sb.toString().getBytes();

		System.out.println("data.length=" + data.length);
		System.out.println("write to " + result.getAbsolutePath());

		try (GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(result));
			InputStream in = new ByteArrayInputStream(sb.toString().getBytes())) {

			byte[] buffer = new byte[1024];
			int len;

			while ((len = in.read(buffer)) > 0) {

				out.write(buffer, 0, len);
			}

			out.flush();
		}
	}

	@Test
	void should_decompressSucceed_when_sizeNotExceedsLimit() throws IOException {

		// Arrange
		byte[] data = TestUtils.readBytesFromClasspath("/10730-bytes-zeros.gz");
		File output = TempFileHelper.createTempFile("result");

		// Act
		new GZipDecompressor(10731).decompressToFile(data, output);

		// Assert
		assertTrue(output.isFile());
		assertTrue(output.canRead());
	}

	@Test
	void should_decompressStop_when_sizeExceedsLimit() throws IOException {

		// Arrange
		byte[] data = TestUtils.readBytesFromClasspath("/10240-bytes-zeros.gz");
		File output = TempFileHelper.createTempFile("result");

		// Act + Assert
		try {

			new GZipDecompressor(8000).decompressToFile(data, output);

		} catch (SecurityRuntimeException e) {

			assertEquals("decomressed data exceed limit of 8000 byte", e.getMessage());
			assertFalse(output.exists());
		} finally {

			TempFileHelper.deleteQuietly(output);
		}
	}

	@Test
	void zipAGZIP() throws IOException {

		File tempFile = TempFileHelper.createTempFile("zip-with-gzip", ".zip");

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempFile));
			InputStream in = getClass().getResourceAsStream("/10730-bytes-zeros.gz")) {

			ZipEntry zipEntry = new ZipEntry("file.gz");
			zos.putNextEntry(zipEntry);

			byte[] buffer = new byte[1024];
			int len;

			while ((len = in.read(buffer)) > 0) {

				zos.write(buffer, 0, len);
			}
			zos.closeEntry();
			zos.flush();
		}
	}

}
