// =====================================================
// Project: commons-security
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_security.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_security.TestUtils;
import de.egladil.web.commons_security.zip.exception.IORuntimeException;
import de.egladil.web.commons_security.zip.exception.UnsupportedUnzipOperationException;

/**
 * SizeLimitedOneEntryZipDecompressorTest
 */
public class SizeLimitedOneEntryZipDecompressorTest {

	@Test
	void should_unzipStop_when_lengthLimitIsReached_mediumZipBomb() throws IOException {

		// Arrange
		byte[] data = TestUtils.readBytesFromClasspath("/zipbomb-decompress-at-your-own-risk.txt");
		int maxSize = 131072;

		// Act + Assert
		try {

			new SizeLimitedOneEntryZipDecompressor(maxSize).unzip("afdfwzi", data);
			fail("keine UnsupportedUnzipOperationException");
		} catch (UnsupportedUnzipOperationException e) {

			assertEquals("only ZipEntries up to 131072 bytes may be extracted", e.getMessage());
		}
	}

	@Test
	void should_unzipStop_when_lengthLimitIsReached_42kZipBomb() throws IOException {

		// Arrange
		byte[] data = TestUtils.readBytesFromClasspath("/never-ever-try-to-decompress.txt");
		int maxSize = 2097152;

		// Act + Assert
		try {

			new SizeLimitedOneEntryZipDecompressor(maxSize).unzip("afdfwzi", data);
			fail("keine UnsupportedUnzipOperationException");
		} catch (UnsupportedUnzipOperationException e) {

			assertEquals("only ZipFiles with a single entry may be extracted", e.getMessage());
		}
	}

	@Test
	void should_unzipSucceed() throws IOException {

		// Arrange
		byte[] data = TestUtils.readBytesFromClasspath("/jpg.zip");
		int maxSize = 67108864;

		// Act
		byte[] result = new SizeLimitedOneEntryZipDecompressor(maxSize).unzip("fqzdfqzu", data);

		// Assert
		assertEquals(1445910, result.length);

	}

	@Test
	void should_unzipStop_when_thereIsMoreThanOneEntry() throws IOException {

		// Arrange
		byte[] data = TestUtils.readBytesFromClasspath("/two-entries.zip");
		int maxSize = 2097152;

		// Act + Assert
		try {

			new SizeLimitedOneEntryZipDecompressor(maxSize).unzip("afdfwzi", data);
			fail("keine UnsupportedUnzipOperationException");
		} catch (UnsupportedUnzipOperationException e) {

			assertEquals("only ZipFiles with a single entry may be extracted", e.getMessage());
		}
	}

	@Test
	void should_throwException_when_fileIsNotAZipFile() throws IOException {

		byte[] data = TestUtils.readBytesFromClasspath("/test.gz");
		int maxSize = 2048;

		// Act + Assert
		try {

			new SizeLimitedOneEntryZipDecompressor(maxSize).unzip("afdfwzi", data);
			fail("keine IORuntimeException");
		} catch (IORuntimeException e) {

			assertEquals("Could not unzip this file. Maybe it is not a .zip? zip END header not found", e.getMessage());
		}

	}
}
