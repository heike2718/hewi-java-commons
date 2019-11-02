// =====================================================
// Projekt: commons
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.content;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import de.egladil.web.commons_net.exception.RequestTimeoutException;

/**
 * StaticContentProvider
 */
public class StaticContentProvider {

	private final ContentReader contentReader;

	/**
	 * Erzeugt eine Instanz von StaticContentProvider
	 */
	public StaticContentProvider(final ContentReader contentReader) {

		this.contentReader = contentReader;
	}

	/**
	 * Holt ein String-Data-Objekt aus dem endpoint
	 *
	 * @param  endpoint
	 *                                 URL zu einer Resource, die ein ResponsePayload zurückliefert, dessen data- Objekt ein String
	 *                                 ist.
	 * @param  timeout
	 *                                 int Timeoit in milliseconds
	 * @return                         byte[] das Data-Object
	 * @throws IOException
	 * @throws RequestTimeoutException
	 *                                 falls die conn zu lange braucht.
	 */
	public byte[] getStaticContent(final String endpoint, final int timeout) throws IOException, RequestTimeoutException {

		URL url = new URL(endpoint);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("Accept", "*/*");
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);

		byte[] cert = this.contentReader.getBytes(conn);
		return cert;

	}
}
