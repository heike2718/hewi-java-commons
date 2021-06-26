// =====================================================
// Project: commons-openofficetools
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_openofficetools;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenOfficeTableElement
 */
public class OpenOfficeTableElement {

	private final OOElementType type;

	private String content;

	private boolean formula;

	private List<OpenOfficeTableElement> childElements = new ArrayList<>();

	/**
	 * OpenOfficeTableElement
	 */
	public OpenOfficeTableElement(final OOElementType type) {

		this.type = type;
	}

	public void addChild(final OpenOfficeTableElement child) {

		if (child != null) {

			childElements.add(OpenOfficeTableElement.createCopy(child));
		}
	}

	public final String getContent() {

		return content;
	}

	public final void setContent(final String content) {

		this.content = content;
	}

	public final OOElementType getType() {

		return type;
	}

	public final List<OpenOfficeTableElement> getChildElements() {

		return childElements;
	}

	public final boolean isFormula() {

		return formula;
	}

	public final void setFormula(final boolean formula) {

		this.formula = formula;
	}

	public boolean isEmptyRow() {

		if (OOElementType.ROW == this.type) {

			if (this.childElements.isEmpty()) {

				return true;
			}

			for (final OpenOfficeTableElement child : this.childElements) {

				if (!child.isFormula()) {

					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean isHeadline() {

		if (OOElementType.ROW == this.type) {

			for (final OpenOfficeTableElement child : this.childElements) {

				if ("A-1".equals(child.getContent())) {

					return true;
				}
			}
			return false;
		}
		return false;
	}

	/**
	 * @return in Anzahl der Kindelemente
	 */
	public int size() {

		return childElements.size();
	}

	public static OpenOfficeTableElement createCopy(final OpenOfficeTableElement element) {

		final OpenOfficeTableElement result = new OpenOfficeTableElement(element.getType());
		result.setContent(element.getContent());
		result.setFormula(element.isFormula());

		if (OOElementType.ROW == element.getType()) {

			for (final OpenOfficeTableElement child : element.getChildElements()) {

				result.addChild(child);
			}
		}
		return result;
	}

	/**
	 * @param  spaltenindex
	 *                      int
	 * @return              OpenOfficeTableElement
	 */
	public OpenOfficeTableElement get(final int spaltenindex) {

		if (spaltenindex > this.size()) {

			return null;
		}
		return this.childElements.get(spaltenindex);
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("OpenOfficeTableElement [type=");
		builder.append(type);
		builder.append(", content=");
		builder.append(content);
		builder.append(", formula=");
		builder.append(formula);
		builder.append("]");
		return builder.toString();
	}

}
