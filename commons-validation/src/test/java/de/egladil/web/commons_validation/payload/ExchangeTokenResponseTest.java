// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation.payload;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ExchangeTokenResponseTest
 */
public class ExchangeTokenResponseTest {

	@Test
	void should_SerializeAsJson() throws JsonProcessingException {

		// Arrange
		String jwt = "gjlsgdcuw-";
		String nonce = "jakgjg";
		ExchangeTokenResponse exchangeTokenResponse = ExchangeTokenResponse.create(jwt, nonce);

		// Act
		String serialization = new ObjectMapper().writeValueAsString(exchangeTokenResponse);

		// Assert
		// {"nonce":"jakgjg","jwt":"gjlsgdcuw-"}
		assertEquals("{\"nonce\":\"jakgjg\",\"jwt\":\"gjlsgdcuw-\"}", serialization);
	}

}
