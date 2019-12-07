// =====================================================
// Projekt: commons-mailer
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_mailer.impl;

import java.util.Collection;
import java.util.Properties;

import javax.enterprise.context.Dependent;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_mailer.CommonEmailService;
import de.egladil.web.commons_mailer.EmailDaten;
import de.egladil.web.commons_mailer.EmailServiceCredentials;
import de.egladil.web.commons_mailer.exception.EmailConfigurationException;
import de.egladil.web.commons_mailer.exception.EmailException;
import de.egladil.web.commons_mailer.exception.InvalidMailAddressException;

/**
 * CommonEmailServiceImpl
 */
@Dependent
public class CommonEmailServiceImpl implements CommonEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(CommonEmailServiceImpl.class);

	@Override
	public synchronized boolean sendMail(final EmailDaten maildaten, final EmailServiceCredentials credentials) throws EmailException, InvalidMailAddressException {

		if (maildaten == null) {

			throw new IllegalArgumentException("maildaten null");
		}

		if (credentials == null) {

			throw new IllegalArgumentException("maildaten null");
		}

		checkCredentials(credentials);

		final Collection<String> hiddenEmpfaenger = maildaten.getHiddenEmpfaenger();

		if (maildaten.getEmpfaenger() == null && (hiddenEmpfaenger == null || hiddenEmpfaenger.isEmpty())) {

			throw new EmailException("Es muss mindestens einen Empfänger oder versteckten Empfänger geben.");
		}

		final Properties mailProperties = createProperties(credentials);

		char[] pwd = credentials.getPassword();

		try {

			Session session = Session.getDefaultInstance(mailProperties);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(credentials.getFromAddress()));

			if (maildaten.getEmpfaenger() != null) {

				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(maildaten.getEmpfaenger(), true));
			}
			msg.setSubject(maildaten.getBetreff(), "UTF-8");
			msg.setText(maildaten.getText(), "UTF-8");

			if (hiddenEmpfaenger != null && !hiddenEmpfaenger.isEmpty()) {

				Address[] addresses = new Address[hiddenEmpfaenger.size()];
				int index = 0;

				for (String str : hiddenEmpfaenger) {

					addresses[index] = new InternetAddress(str, true);
					index++;
				}
				msg.addRecipients(Message.RecipientType.BCC, addresses);
			} else {

				LOG.debug("HiddenEmpfaender waren null oder leer: wird ignoriert");
			}

			// setSystpropLogging("error");
			Transport.send(msg, credentials.getUser(), new String(pwd));
			LOG.info("Mail gesendet an {}", maildaten.getEmpfaenger());
			return true;
		} catch (SendFailedException e) {

			throw new InvalidMailAddressException("Mailversand: es gab ungültige Empfänger", e);
		} catch (MessagingException e) {

			String msg = "Mail an [empfaenger=" + maildaten.alleEmpfaengerFuersLog() + "] konnte nicht versendet werden. "
				+ mailProperties.toString() + ": " + e.getMessage();
			LOG.error("Fehler beim Versenden einer Mail: {}", msg);
			throw new EmailException(msg, e);
		} finally {

			pwd = new char[0];
		}
	}

	private void checkCredentials(final EmailServiceCredentials credentials) {

		if (StringUtils.isBlank(credentials.getHost())) {

			throw new EmailConfigurationException("EmailServiceCredentials.host ist nicht gesetzt");
		}

		if (StringUtils.isBlank(credentials.getFromAddress())) {

			throw new EmailConfigurationException("EmailServiceCredentials.fromAddress ist nicht gesetzt");
		}

		if (StringUtils.isBlank(credentials.getUser())) {

			throw new EmailConfigurationException("EmailServiceCredentials.user ist nicht gesetzt");
		}

		if (credentials.getPassword() == null || credentials.getPassword().length < 2) {

			throw new EmailConfigurationException("EmailServiceCredentials.password ist nicht gesetzt");
		}
	}

	private Properties createProperties(final EmailServiceCredentials credentials) {

		final Properties mailProperties = new Properties();

		mailProperties.setProperty("mail.smtp.host", credentials.getHost());
		mailProperties.setProperty("mail.smtp.auth", "true");
		// SSL-Konfiguration
		mailProperties.put("mail.smtp.starttls.enable", "true");
		int port = credentials.getPort();
		mailProperties.put("mail.smtp.port", port);
		mailProperties.put("mail.smtp.socketFactory.port", port);
		// mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		mailProperties.put("mail.smtp.socketFactory.fallback", "true");
		return mailProperties;
	}
}
