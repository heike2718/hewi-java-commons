// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import de.egladil.web.commons_validation.payload.LogEntry;
import de.egladil.web.commons_validation.payload.TSLogLevel;

/**
 * LogDelegate
 */
public class LogDelegate {

	public void log(final LogEntry logEntry, final Logger logger, final String clientId) {

		TSLogLevel level = logEntry.getLevel();

		String clientIdAbbr = StringUtils.abbreviate(clientId, 11);

		switch (level) {

		case All:
		case Debug:
			logger.debug("BrowserLog: {} - Client-ID={}", logEntry, clientIdAbbr);
			break;

		case Info:
			logger.info("BrowserLog: {} - Client-ID={}", logEntry, clientIdAbbr);
			break;

		case Warn:
			logger.warn("BrowserLog: {} - Client-ID={}", logEntry, clientIdAbbr);
			break;

		case Error:
		case Fatal:
			logger.error("BrowserLog: {} - Client-ID={}", logEntry, clientIdAbbr);
			break;

		default:
			break;
		}

	}

}
