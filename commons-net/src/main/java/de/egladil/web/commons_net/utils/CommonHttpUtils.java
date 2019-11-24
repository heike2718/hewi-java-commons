// =====================================================
// Projekt: commons
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.utils;

import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_net.time.CommonTimeUtils;

/**
 * CommonHttpUtils
 */
public final class CommonHttpUtils {

	public static final String NAME_SESSIONID_COOKIE = "_SESSIONID";

	private static final String STAGE_DEV = "dev";

	private static final String SESSION_ID_HEADER = "X-SESSIONID";

	private static final Logger LOG = LoggerFactory.getLogger(CommonHttpUtils.class);

	/**
	 * Erzeugt eine Instanz von CommonHttpUtils
	 */
	private CommonHttpUtils() {

	}

	public static String extractOrigin(final String headerValue) {

		if (StringUtils.isBlank(headerValue)) {

			return null;
		}
		final String value = headerValue.replaceAll("http://", "").replaceAll("https://", "");
		final String[] token = StringUtils.split(value, "/");
		final String extractedOrigin = token == null ? value : token[0];
		return extractedOrigin;
	}

	/**
	 * Zu Logzwecken alle Headers dumpen.
	 *
	 * @param  requestContext
	 * @return
	 */
	public static String getRequestInfos(final ContainerRequestContext requestContext) {

		final StringBuffer sb = new StringBuffer();
		sb.append(" <--- Request Headers --- ");

		requestContext.getHeaders().entrySet().stream().forEach(e -> {

			String key = e.getKey();
			sb.append(key);
			sb.append(":");
			e.getValue().stream().forEach(v -> {

				sb.append(v);
				sb.append(", ");
			});
		});

		sb.append(" Headers Request ---> ");
		final String dump = sb.toString();
		return dump;
	}

	public static String getSessionId(final ContainerRequestContext requestContext, final String stage) {

		if (!STAGE_DEV.equals(stage)) {

			Map<String, Cookie> cookies = requestContext.getCookies();

			Cookie sessionCookie = cookies.get(NAME_SESSIONID_COOKIE);

			if (sessionCookie == null) {

				LOG.error("{}: Request ohne {}-Cookie", requestContext.getUriInfo(), NAME_SESSIONID_COOKIE);
				return null;
			}
		}

		String sessionIdHeader = requestContext.getHeaderString(SESSION_ID_HEADER);

		if (sessionIdHeader == null) {

			LOG.debug("{} dev: Request ohne Authorization-Header", requestContext.getUriInfo());

			return null;
		}

		LOG.debug("sessionId={}", sessionIdHeader);
		return sessionIdHeader;

	}

	public static NewCookie createSessionInvalidatedCookie(final String domain) {

		long dateInThePast = CommonTimeUtils.now().minus(10, ChronoUnit.YEARS).toEpochSecond(ZoneOffset.UTC);

		// @formatter:off
		NewCookie invalidationCookie = new NewCookie(CommonHttpUtils.NAME_SESSIONID_COOKIE,
			null,
			null,
			domain,
			1,
			null,
			0,
			new Date(dateInThePast),
			true,
			true);
//		 @formatter:on
		// NewCookie sessionCookie = new NewCookie("JSESSIONID", userSession.getSessionId());

		return invalidationCookie;
	}

	/**
	 * Erzeugt eine User-ID-Referenz, mit der der User für den Zeitraum der Session identifiziert werden kann.
	 *
	 * @return String
	 */
	public static String createUserIdReference() {

		long msb = UUID.randomUUID().getMostSignificantBits();
		return Long.toHexString(msb);
	}
}
