// =====================================================
// Projekt: de.egladil.mkv.service
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation.payload;

/**
 * HateoasLink für HATEOAS.
 */
public class HateoasLink {

	private String url;

	private String rel;

	private String method;

	private String mediatype;

	/**
	 * HateoasLink
	 */
	public HateoasLink() {

	}

	/**
	 * <strong>Beispiel für Resource AuswertungDownload mit downloadCode adf-65 </strong> { "downloadCode": "adf-65",
	 * "links": [ { "url": "/auswertungen/auswertung/adf-65", "rel": "self", "method" : "GET", "mediatype" :
	 * "application/json" } ] }
	 *
	 * @param url
	 *                  String die URL relativ zur application
	 * @param rel
	 *                  String ein Substantiv, das die Beziehung ausdrückt, in dem die Resource zur aktuellen Resource steht
	 * @param method
	 *                  String die HttpMethod, die angewendet werden kann.
	 * @param mediatype
	 *                  String kommaseparierte Liste von Mediatypes, mit denen die Resource zur Verfügung gestellt
	 *                  werden kann.
	 */
	public HateoasLink(final String url, final String rel, final String method, final String mediatype) {

		this.url = url;
		this.rel = rel;
		this.method = method;
		this.mediatype = mediatype;
	}

	public final String getUrl() {

		return url;
	}

	public final void setUrl(final String href) {

		this.url = href;
	}

	public final String getRel() {

		return rel;
	}

	public final void setRel(final String rel) {

		this.rel = rel;
	}

	public final String getMethod() {

		return method;
	}

	public final void setMethod(final String method) {

		this.method = method;
	}

	public final String getMediatype() {

		return mediatype;
	}

	public final void setMediatype(final String type) {

		this.mediatype = type;
	}

	@Override
	public String toString() {

		final StringBuilder builder = new StringBuilder();
		builder.append("HateoasLink [url=");
		builder.append(url);
		builder.append(", rel=");
		builder.append(rel);
		builder.append(", method=");
		builder.append(method);
		builder.append(", mediatype=");
		builder.append(mediatype);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((rel == null) ? 0 : rel.hashCode());
		result = prime * result + ((mediatype == null) ? 0 : mediatype.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {

			return true;
		}

		if (obj == null) {

			return false;
		}

		if (getClass() != obj.getClass()) {

			return false;
		}
		final HateoasLink other = (HateoasLink) obj;

		if (url == null) {

			if (other.url != null) {

				return false;
			}
		} else if (!url.equals(other.url)) {

			return false;
		}

		if (method == null) {

			if (other.method != null) {

				return false;
			}
		} else if (!method.equals(other.method)) {

			return false;
		}

		if (rel == null) {

			if (other.rel != null) {

				return false;
			}
		} else if (!rel.equals(other.rel)) {

			return false;
		}

		if (mediatype == null) {

			if (other.mediatype != null) {

				return false;
			}
		} else if (!mediatype.equals(other.mediatype)) {

			return false;
		}
		return true;
	}

}
