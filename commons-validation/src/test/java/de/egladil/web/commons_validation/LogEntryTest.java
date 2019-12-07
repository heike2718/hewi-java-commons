// =====================================================
// Project: commons-validation
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_validation;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_validation.payload.LogEntry;
import de.egladil.web.commons_validation.payload.TSLogLevel;

/**
 * LogEntryTest
 */
public class LogEntryTest {

	@Test
	void testToString() {

		LogEntry le = new LogEntry();
		le.setLevel(TSLogLevel.Info);
		le.setMessage("Eine Info-Message");

		System.out.println(le.toString());

	}

}
