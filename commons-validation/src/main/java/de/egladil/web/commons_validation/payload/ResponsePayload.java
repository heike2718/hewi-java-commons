// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation.payload;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ResponsePayload
 */
@XmlRootElement(name = "response-payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponsePayload {

	@JsonProperty
	@XmlElement
	private MessagePayload message;

	@JsonProperty
	@XmlTransient
	private Object data;

	/**
	 * Erzeugt eine Instanz von ResponsePayload
	 */
	public ResponsePayload() {

	}

	/**
	 * Erzeugt eine Instanz von ResponsePayload
	 */
	private ResponsePayload(final MessagePayload message) {

		super();
		this.message = message;
	}

	/**
	 * Erzeugt eine Instanz von ResponsePayload
	 */
	public ResponsePayload(final MessagePayload message, final Object payload) {

		super();
		this.message = message;
		this.data = payload;
	}

	public MessagePayload getMessage() {

		return message;
	}

	public void setMessage(final MessagePayload message) {

		this.message = message;
	}

	public Object getData() {

		return data;
	}

	public void setData(final Object payload) {

		this.data = payload;
	}

	@JsonIgnore
	public boolean isOk() {

		return this.message.isOk();
	}

	public static ResponsePayload messageOnly(final MessagePayload messagePayload) {

		return new ResponsePayload(messagePayload);
	}

}
