// =====================================================
// Project: de.egladil.mkv.service
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_testsupport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * RestClientUtils
 */
public final class RestClientUtils {

	/**
	 * Erzeugt eine Instanz von RestSSLUtils
	 */
	private RestClientUtils() {

	}

	/**
	 * Erzeugt einen SSL-fähigen RestClient.<br>
	 * <br>
	 * Vorbereitung: das Zertifikat muss in den lokalen Java-Keystore importiert
	 * werden.
	 * <ul>
	 * <li><strong>1.</strong> Über den Browser das Zertifikat als Datei Speichern
	 * (am besten Base64-encoded). Dateiname sei zertifikat.crt</li>
	 * <li><strong>2.</strong> CommandLine im Verzeichnis mit dem Zertifikat
	 * öffnen</li>
	 * <li><strong>3.</strong> keytool -import -alias bla -file zertifikat.cer
	 * -keystore %JAVA_HOME%\jre\lib\security\cacerts</li>
	 * <li><strong>4.</strong> Man wird nach dem Keystore-Passwort gefragt. Das ist
	 * 'changeit'</li>
	 * <li><strong>5</strong> Man wird gefragt, ob man dem Zertifikat vertraut:
	 * Ja/Yes</li>
	 * </ul>
	 *
	 * @return                          Client
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static Client createSSLCient(final String pathTruststoreFile) throws RuntimeException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException {

		try {

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			String trustStorePasswd = "changeit";

			try (FileInputStream fis = new FileInputStream(pathTruststoreFile)) {

				trustStore.load(fis, trustStorePasswd.toCharArray());

				// Default trust manager is PKIX (No SunX509)
				TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmf.init(trustStore);

				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, tmf.getTrustManagers(), null);

				return ClientBuilder.newBuilder().sslContext(sslContext).build();
			}
		} catch (KeyStoreException e) {

			throw new RuntimeException("Erzeugung eines SSL-fähigen Clients nicht möglich wegen " + e.getMessage(), e);
		}
	}
}
