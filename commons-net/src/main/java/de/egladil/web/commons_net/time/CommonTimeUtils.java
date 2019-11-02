// =====================================================
// Projekt: authprovider
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * CommonTimeUtils
 */
public final class CommonTimeUtils {

	public static final String DEFAULT_DATE_TIME_FORMAT = "dd.MM.yyyy kk:mm:ss";

	public static final String DEFAULT_DATE_MINUTES_FORMAT = "dd.MM.yyyy kk:mm";

	public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

	/**
	 * Erzeugt eine Instanz von CommonTimeUtils
	 */
	private CommonTimeUtils() {

	}

	/**
	 * Wandelt milliseconds in LocalDateTime in der system timezone um.
	 *
	 * @param  milliseconds
	 * @return              LocalDateTime
	 */
	public static LocalDateTime transformFromMilliseconds(final long milliseconds) {

		return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
	}

	/**
	 * Wandelt date in LocalDateTime in der system timezone um.
	 *
	 * @param  date
	 * @return      LocalDateTime
	 */
	public static LocalDateTime transformFromDate(final Date date) {

		if (date == null) {

			throw new IllegalArgumentException("date null");
		}

		return transformFromMilliseconds(date.getTime());
	}

	/**
	 * Wandelt ein LocalDateTime in ein Date um.
	 *
	 * @param  ldt
	 * @return
	 */
	public static Date transformFromLocalDateTime(final LocalDateTime ldt) {

		if (ldt == null) {

			throw new IllegalArgumentException("ldt null");
		}

		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Erzeugt ein abgeschlossenes Zeitintervall.
	 *
	 * @param  startTime
	 *                    Date
	 * @param  amount
	 *                    int Anzahl Zeiteinheiten muss >= 0 sein, 0 ist zulässig.
	 * @param  chronoUnit
	 *                    ChronoUnit
	 * @return            TimeInterval
	 */
	public static TimeInterval getInterval(final LocalDateTime startTime, final int amount, final ChronoUnit chronoUnit) {

		if (startTime == null) {

			throw new IllegalArgumentException("startTime null");
		}

		if (chronoUnit == null) {

			throw new IllegalArgumentException("chronoUnit null");
		}

		if (amount < 0) {

			throw new IllegalArgumentException("amount must be >=0");
		}
		Date startsAt = transformFromLocalDateTime(startTime);

		if (amount == 0) {

			return new TimeInterval(startsAt, startsAt);
		}
		LocalDateTime endTime = startTime.plus(amount, chronoUnit);
		Date endsAt = transformFromLocalDateTime(endTime);

		return new TimeInterval(startsAt, endsAt);
	}

	/**
	 * Jetzt in der system timezone.
	 *
	 * @return LocalDateTime
	 */
	public static LocalDateTime now() {

		LocalDateTime ldt = LocalDateTime.now(ZoneId.systemDefault());

		return ldt;

	}
}
