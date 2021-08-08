// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.csv;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;

/**
 * ConvertscriptGeneratorTest
 */
public class ConvertscriptGeneratorTest extends AbstractCSVTest {

	private static final String NAME_SOURCEFILE = "klassenliste-iso.xlsx";

	private ConvertscriptGenerator generator = new ConvertscriptGenerator();

	private String pathCSVFile;

	@BeforeEach
	void setUp() {

		String substring = UUID.randomUUID().toString().substring(0, 8);
		pathCSVFile = PATH_WORKDIR + File.separator + "klassenliste-" + substring + ".csv";
	}

	@Test
	void should_generatePyFileGenerateThePythonScriptFile() throws IOException {

		// Arrange
		String pathSourceFile = "/home/heike/mkv/py-tests/klassenliste-iso.xlsx";

		// Act
		File pyFile = generator.generatePyFile(PATH_WORKDIR, NAME_SOURCEFILE, pathCSVFile);

		// Assert
		assertTrue(pyFile.exists());
		assertTrue(pyFile.isFile());
		assertTrue(pyFile.canRead());

		try (InputStream in = new FileInputStream(pyFile)) {

			String content = new String(in.readAllBytes());

			assertTrue(content.contains(NAME_SOURCEFILE));
			assertTrue(content.contains(pathSourceFile));

		}

		FileUtils.deleteQuietly(pyFile);

	}

	@Test
	void should_generatePyFileThrowException_when_workdirNotExists() {

		try {

			generator.generatePyFile("/home/heike/traumdir", NAME_SOURCEFILE, pathCSVFile);

			fail("keine ExceltoolsRuntimeException");
		} catch (ExceltoolsRuntimeException e) {

			System.out.println(e.getMessage());

		}
	}

	@Test
	void should_generatePyFileThrowException_when_workdirNotDirectory() {

		try {

			generator.generatePyFile(PATH_WORKDIR + File.separator + NAME_SOURCEFILE, NAME_SOURCEFILE, pathCSVFile);

			fail("keine ExceltoolsRuntimeException");
		} catch (ExceltoolsRuntimeException e) {

			System.out.println(e.getMessage());
		}
	}

}
