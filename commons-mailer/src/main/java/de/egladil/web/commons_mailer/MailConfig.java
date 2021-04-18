// =====================================================
// Project: output-telegram-pipe
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_mailer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MailConfig
 */
public class MailConfig {

	@JsonProperty
	private boolean activated;

	@JsonProperty
	private String host;

	@JsonProperty
	private int port;

	@JsonProperty
	private String user;

	@JsonProperty
	private String pwd;

	public String host() {

		return host;
	}

	public MailConfig withHost(final String mailhost) {

		this.host = mailhost;
		return this;
	}

	public int port() {

		return port;
	}

	public MailConfig withPort(final int mailport) {

		this.port = mailport;
		return this;
	}

	public String user() {

		return user;
	}

	public MailConfig withMailuser(final String mailuser) {

		this.user = mailuser;
		return this;
	}

	public String pwd() {

		return pwd;
	}

	public MailConfig withPwd(final String mailpassword) {

		this.pwd = mailpassword;
		return this;
	}

	/**
	 * @return the mailActivated
	 */
	public boolean isMailActivated() {

		return activated;
	}

	/**
	 * @param mailActivated
	 *                      the mailActivated to set
	 */
	public MailConfig withMailActivated(final boolean mailActivated) {

		this.activated = mailActivated;
		return this;
	}

}
