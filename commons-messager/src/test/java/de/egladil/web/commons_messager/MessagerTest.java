// =====================================================
// Project: commons-messager
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_messager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * MessagerTest
 */
public class MessagerTest {

	@Test
	void should_createMessageSenderOfTypeThrowNPE_when_parameterNull() {

		try {

			Messager.createMessageSenderOfType(null);
			fail("keine NullPointerException");

		} catch (NullPointerException e) {

			assertEquals("MessagerType type", e.getMessage());
		}

	}

	@Test
	void should_createMessageSenderOfTypeReturnTelegramMessageSender_when_TypeTelegram() {

		Messager messager = Messager.createMessageSenderOfType(MessagerType.TELEGRAM);

		assertNotNull(messager);
		assertEquals(MessagerType.TELEGRAM, messager.getType());

	}

}
