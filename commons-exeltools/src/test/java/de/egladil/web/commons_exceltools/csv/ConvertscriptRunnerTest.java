// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;

/**
 * ConvertscriptRunnerTest
 */
public class ConvertscriptRunnerTest extends AbstractCSVTest {

	private ConvertscriptRunner runner = ConvertscriptRunner.createForTest();

	@Test
	void should_runConvertscriptWork() {

		// Arrange
		String pathGeneratedFile = "/home/heike/mkv/py-tests/klassenliste-0a8732c8.csv";
		String nameTestscript = "test.py";

		File file = new File(pathGeneratedFile);

		if (file.exists()) {

			FileUtils.deleteQuietly(file);
		}

		assertFalse(new File(pathGeneratedFile).isFile());

		File pyFile = new File(PATH_WORKDIR + File.separator + nameTestscript);
		assertTrue(pyFile.isFile());
		assertTrue(pyFile.canRead());

		// Act
		int result = runner.executePyScript(pyFile, 3000);

		// Assert
		assertEquals(0, result);

		file = new File(pathGeneratedFile);
		assertTrue(file.isFile());
		assertTrue(file.canRead());
	}

	@Test
	void should_runConvertscriptThrowException_when_PyFileHasErrors() {

		// Arrange
		String nameTestscript = "invalid.py";

		File pyFile = new File(PATH_WORKDIR + File.separator + nameTestscript);
		assertTrue(pyFile.isFile());
		assertTrue(pyFile.canRead());

		// Act
		try {

			runner.executePyScript(pyFile, 3000);
			fail("keine ExceltoolsRuntimeException");
		} catch (ExceltoolsRuntimeException e) {

			// Assert
			assertEquals(
				"Beim Ausfuehren des Python-Skripts ist ein Fehler aufgetreten: Process exited with an error: 1 (Exit value: 1)",
				e.getMessage());
		}
	}

	@Test
	void should_runConvertscriptThrowException_when_PyFileDoesNotExist() {

		// Arrange
		String nameTestscript = "unknown.py";

		File pyFile = new File(PATH_WORKDIR + File.separator + nameTestscript);
		assertFalse(pyFile.isFile());

		// Act
		try {

			runner.executePyScript(pyFile, 3000);
			fail("keine ExceltoolsRuntimeException");
		} catch (ExceltoolsRuntimeException e) {

			// Assert
			assertEquals(
				"Beim Ausfuehren des Python-Skripts ist ein Fehler aufgetreten: Konnte Script /home/heike/mkv/py-tests/unknown.py nicht finden oder lesen",
				e.getMessage());
		}
	}
}
