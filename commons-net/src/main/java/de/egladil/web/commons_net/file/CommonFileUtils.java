// =====================================================
// Projekt: authproider
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * CommonFileUtils
 */
public final class CommonFileUtils {

	/**
	 * Erzeugt eine Instanz von CommonFileUtils
	 */
	private CommonFileUtils() {

	}

	/**
	 * Liest die Daten aus dem File und gibt sie als bytes[] zurück.
	 *
	 * @param  file
	 *                     File nicht null und lesbare Datei.
	 * @return             byte[]
	 * @throws IOException
	 */
	public static final byte[] readBytes(final File file) throws IOException {

		if (file == null || !file.isFile() || !file.canRead()) {

			throw new IOException("file ist null oder keine Datei oder hat keine Leserechte für mich");
		}

		byte[] bytes = new byte[((int) file.length())];

		try (FileInputStream in = new FileInputStream(file); DataInputStream dis = new DataInputStream(in)) {

			dis.readFully(bytes);
		}

		return bytes;
	}

}
