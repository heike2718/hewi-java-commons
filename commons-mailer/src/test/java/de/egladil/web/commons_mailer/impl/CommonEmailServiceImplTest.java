// =====================================================
// Project: commons-mailer
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_mailer.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_mailer.EmailDaten;
import de.egladil.web.commons_mailer.EmailDatenBuilder;
import de.egladil.web.commons_mailer.EmailServiceCredentials;

/**
 * CommonEmailServiceImplTest
 */
public class CommonEmailServiceImplTest {

	@Test
	void sendMailKlappt() throws IOException {

		// Arrange
		Path path = Path.of("/home/heike/git/konfigurationen/commons-mailer/mailprovider.properties");
		assertTrue(Files.exists(path));
		assertTrue(Files.isRegularFile(path));

		Map<String, String> properties = new HashMap<>();

		Files.lines(path, Charset.forName("UTF-8")).forEach(l -> {

			String[] tokens = l.split("=");
			properties.put(tokens[0], tokens[1]);

		});

		System.out.println(properties.toString());

		String fromAddress = "noreply@egladil.de";

		CommonEmailServiceImpl service = new CommonEmailServiceImpl();

		String to = "heike@egladil.de";
		String subject = "CommonEmailServiceImplTest";
		String text = "Das ist ein commons-mailer-Test. Hat anscheinend geklappt";

		EmailDaten emailDaten = new EmailDatenBuilder(to, subject).withText(text).build();
		EmailServiceCredentials credentials = EmailServiceCredentials.createInstance(properties, fromAddress);

		// Act + Assert
		assertTrue(service.sendMail(emailDaten, credentials));

	}

}
