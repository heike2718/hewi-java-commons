// =====================================================
// Projekt: de.egladil.tools.parser
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_officetools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_officetools.exceptions.OfficeToolsSecurityException;

/**
 * ZipParserTest
 */
public class ZipParserTest {

	private byte[] getZeroBytes(final int numberBytes) {

		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < numberBytes; i++) {

			sb.append(0);
		}
		return sb.toString().getBytes();
	}

	@Test
	void testZipBomb() {

		// Arrange
		try {

			final int length = 128;
			final byte[] zipBomb = createZipBomb(length + 1);
			final ZipParser parser = new ZipParser();
			// Act + Assert

			parser.checkZipBomb(zipBomb, length);
			fail("keine OOParserSecurityException");
		} catch (final IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} catch (final OfficeToolsSecurityException e) {

			assertEquals("ZipEntry 'data' ist größer als 128 Bytes mit kleinem Puffer", e.getMessage());
		}
	}

	@Test
	void testKeineZipBomb() {

		// Arrange
		try {

			final int length = 128;
			final byte[] zipBomb = createZipBomb(length);
			final ZipParser parser = new ZipParser();
			// Act + Assert

			parser.checkZipBomb(zipBomb, length);
		} catch (final IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} catch (final OfficeToolsSecurityException e) {

			fail("OOParserSecurityException sollte nicht kommen");
		}
	}

	@Test
	void testKeinZipArchiv() {

		// Arrange
		try (InputStream in = getClass().getResourceAsStream("/openoffice/data");
			ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

			IOUtils.copy(in, bos);
			final byte[] data = bos.toByteArray();

			final int length = 1024;

			final ZipParser parser = new ZipParser();
			// Act + Assert

			parser.checkZipBomb(data, length);
			fail("keine IOException");
		} catch (final OfficeToolsSecurityException e) {

			assertEquals("Exception Erzeugen eines ZipFiles (vermutlich kein Zip-Archiv): zip END header not found",
				e.getMessage());
		} catch (final IOException e) {

			fail(e.getMessage() + ":  sollte nicht kommen");
		}
	}

	private byte[] createZipBomb(final int maxLength) throws IOException {

		final int maxLengthPlusOne = maxLength + 1;
		final byte[] zeroBytes = getZeroBytes(maxLengthPlusOne);

		try (InputStream in = new ByteArrayInputStream(zeroBytes); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

			final ZipEntry entry = new ZipEntry("data");

			final ZipOutputStream zos = new ZipOutputStream(bos);
			final BufferedInputStream entryStream = new BufferedInputStream(in, maxLengthPlusOne);
			zos.putNextEntry(entry);
			entryStream.close();

			IOUtils.copy(in, zos);
			zos.closeEntry();
			zos.flush();
			zos.close();
			return bos.toByteArray();
		}
	}

	void writeOwnZipBombToFile(final int length) throws IOException {

		try (final FileOutputStream fos = new FileOutputStream("/home/heike/temp/zipBomb-" + length + ".zip");
			ByteArrayInputStream in = new ByteArrayInputStream(createZipBomb(length))) {

			IOUtils.copy(in, fos);
			fos.flush();
		}
	}

}
