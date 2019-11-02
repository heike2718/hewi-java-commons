// =====================================================
// Projekt: de.egladil.common.persistence
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation.payload;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * HateoasPayload
 */
public class HateoasPayload {

	private String id;

	private String url;

	@JsonProperty("link")
	private List<HateoasLink> links = new ArrayList<>();

	/**
	 * HateoasPayload
	 */
	public HateoasPayload() {

	}

	/**
	 * HateoasPayload
	 */
	public HateoasPayload(final String id, final String url) {

		this.id = id;
		this.url = url;
	}

	public void addLink(final HateoasLink link) {

		links.add(link);
	}

	public final String getId() {

		return id;
	}

	public final String getUrl() {

		return url;
	}

	public final List<HateoasLink> getLinks() {

		return links;
	}

	@Override
	public String toString() {

		final StringBuilder builder = new StringBuilder();
		builder.append("HateoasPayload [id=");
		builder.append(id);
		builder.append(", url=");
		builder.append(url);
		builder.append(", links=");
		builder.append(links);
		builder.append("]");
		return builder.toString();
	}

}
