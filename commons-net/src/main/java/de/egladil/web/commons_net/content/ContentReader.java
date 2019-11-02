//=====================================================
// Projekt: commons
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.commons_net.content;

import java.io.IOException;
import java.net.URLConnection;

/**
 * ContentReader
 */
public interface ContentReader {

	/**
	 * Liest den InputStream und wandelt ihn in byte[] um.
	 *
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	byte[] getBytes(final URLConnection conn) throws IOException;

}
