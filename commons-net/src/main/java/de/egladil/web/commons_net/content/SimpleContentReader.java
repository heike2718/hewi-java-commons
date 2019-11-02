//=====================================================
// Projekt: commons
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.commons_net.content;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * SimpleContentReader schiebt die byte[] des InputStreams durch.
 */
public class SimpleContentReader implements ContentReader {

	@Override
	public byte[] getBytes(final URLConnection conn) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (InputStream is = conn.getInputStream()) {
			byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
			int n;

			while ((n = is.read(byteChunk)) > 0) {
				baos.write(byteChunk, 0, n);
			}
			return baos.toByteArray();
		}
	}
}

