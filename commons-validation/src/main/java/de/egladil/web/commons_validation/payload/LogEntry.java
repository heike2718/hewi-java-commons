// =====================================================
// Project: checklistenserver
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation.payload;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * LogEntry ist ein LogEntry, der in einer meiner Angular-Apps erzeugt und geposted wird
 */
public class LogEntry {

	private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss,SSS");

	private long timestamp;

	private String clientAccessToken;

	private String message;

	private TSLogLevel level;

	public String getMessage() {

		return message;
	}

	public void setMessage(final String message) {

		this.message = message;
	}

	public TSLogLevel getLevel() {

		return level;
	}

	public void setLevel(final TSLogLevel level) {

		this.level = level;
	}

	public long getTimestamp() {

		return timestamp;
	}

	public void setTimestamp(final long timestamp) {

		this.timestamp = timestamp;
	}

	public String getClientAccessToken() {

		return clientAccessToken;
	}

	public void setClientAccessToken(final String clientAccessToken) {

		this.clientAccessToken = clientAccessToken;
	}

	@Override
	public String toString() {

		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

		return ldt.format(DT_FORMATTER)
			+ " - " + clientAccessToken + " -  " + message;
	}

}
