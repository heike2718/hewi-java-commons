// =====================================================
// Projekt: commons
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_net.utils;

import javax.ws.rs.container.ContainerRequestContext;

import org.apache.commons.lang3.StringUtils;

/**
 * CommonHttpUtils
 */
public final class CommonHttpUtils {

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
}
