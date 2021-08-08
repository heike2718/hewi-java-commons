// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.csv;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;

/**
 * ConvertscriptRunner
 */
public class ConvertscriptRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConvertscriptRunner.class);

	private boolean deleteGeneratedFile = true;

	static ConvertscriptRunner createForTest() {

		ConvertscriptRunner result = new ConvertscriptRunner();
		result.deleteGeneratedFile = false;
		return result;
	}

	/**
	 * Führt das gegebene py-Script aus. Dieses erzeugt keinen outputStream.
	 *
	 * @param  pyFile
	 *                           File
	 * @param  execTimeoutMillis
	 *                           Timeout in Millisekunden.
	 * @return                   int den returnCode des py-Scripts.
	 */
	public int executePyScript(final File pyFile, final long execTimeoutMillis) {

		// Fail fast
		if (!pyFile.isFile() || !pyFile.canRead()) {

			String msg = "Beim Ausfuehren des Python-Skripts ist ein Fehler aufgetreten: Konnte Script " + pyFile.getAbsolutePath()
				+ " nicht finden oder lesen";
			LOGGER.error(msg);
			throw new ExceltoolsRuntimeException(msg);

		}

		String line = "python3 " + pyFile.getAbsolutePath();
		CommandLine cmdLine = CommandLine.parse(line);

		ExecuteWatchdog watchdog = new ExecuteWatchdog(execTimeoutMillis);

		ProcessErrorOutput processErrorOutput = new ProcessErrorOutput(0);
		PumpStreamHandler streamHandler = new PumpStreamHandler(processErrorOutput);

		DefaultExecutor executor = new DefaultExecutor();
		executor.setWatchdog(watchdog);
		executor.setStreamHandler(streamHandler);

		boolean success = false;

		try {

			int exitValue = executor.execute(cmdLine);

			LOGGER.info("{} executed with exitValue={}", pyFile.getAbsolutePath(), exitValue);

			success = true;

			return exitValue;
		} catch (IOException e) {

			String msg = "Beim Ausfuehren des Python3-Skripts ist ein Fehler aufgetreten: " + e.getMessage();
			LOGGER.error(msg, e);
			LOGGER.error("Python3-Skript: {}", pyFile.getAbsolutePath());
			throw new ExceltoolsRuntimeException(msg, e);

		} finally {

			if (deleteGeneratedFile && success) {

				boolean deleted = FileUtils.deleteQuietly(pyFile);

				if (deleted) {

					LOGGER.debug("pyFile {} geloescht", pyFile.getAbsolutePath());
				}
			}
		}
	}
}