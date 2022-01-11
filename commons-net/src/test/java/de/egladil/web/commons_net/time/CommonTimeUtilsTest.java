// =====================================================
// Projekt: authprovider
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.Test;

import de.egladil.web.commons_net.time.CommonTimeUtils;
import de.egladil.web.commons_net.time.TimeInterval;

/**
 * CommonTimeUtilsTest
 */
public class CommonTimeUtilsTest {

	@Test
	void getInterval360Minuten() throws ParseException {

		// Arrange
		Date start = new SimpleDateFormat(CommonTimeUtils.DEFAULT_DATE_FORMAT).parse("22.04.2019");
		Date end = new SimpleDateFormat(CommonTimeUtils.DEFAULT_DATE_TIME_FORMAT).parse("22.04.2019 06:00:00");

		// Act
		TimeInterval interval = CommonTimeUtils.getInterval(CommonTimeUtils.transformFromDate(start), 360, ChronoUnit.MINUTES);

		// Assert
		assertNotNull(interval);
		System.out.println(interval.toString());
		assertEquals(end.getTime(), interval.getEndTime().getTime());

	}

	@Test
	void getTimestamp() {

		// Arrange
		long expiresAt = 1555916117;

		// Act
		LocalDateTime ldt = CommonTimeUtils.transformFromDate(new Date(expiresAt));

		// Assert
		System.out.println(ldt.toString());

	}

	@Test
	void parse() throws ParseException {

		Date date = new SimpleDateFormat(CommonTimeUtils.DEFAULT_DATE_TIME_FORMAT).parse("14.07.2019 15:59:36");

		System.out.println("epoche= " + date.getTime());
	}

}
