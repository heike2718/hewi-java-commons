// =====================================================
// Project: output-telegram-pipe
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TelegramConfig
 */
public class TelegramConfig {

	@JsonProperty
	private String secret;

	@JsonProperty
	private String chatId;

	/**
	 * @return the secret
	 */
	public String secret() {

		return secret;
	}

	/**
	 * @param secret
	 *               the secret to set
	 */
	public TelegramConfig withSecret(final String secret) {

		this.secret = secret;
		return this;
	}

	/**
	 * @return the chatId
	 */
	public String chatId() {

		return chatId;
	}

	/**
	 * @param chatId
	 *               the chatId to set
	 */
	public TelegramConfig withChatId(final String chatId) {

		this.chatId = chatId;
		return this;
	}

}
