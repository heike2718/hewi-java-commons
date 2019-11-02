// =====================================================
// Projekt: commons-validation
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_validation;

/**
 * InvalidProperties.<br>
 * <br>
 * Das Attribut 'name' bestimmt, an welchem Form-Element die 'message' angezeigt wird.
 */
public class InvalidProperty {

	private int sortnumber;

	private String name;

	private String message;

	/**
	 * Erzeugt eine Instanz von InvalidProperties
	 */
	public InvalidProperty() {

	}

	/**
	 * Erzeugt eine Instanz von InvalidProperties
	 */
	public InvalidProperty(final String fieldName, final String message, final int sortnumber) {

		this.name = fieldName;
		this.message = message;
		this.sortnumber = sortnumber;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		InvalidProperty other = (InvalidProperty) obj;

		if (message == null) {

			if (other.message != null) {

				return false;
			}
		} else if (!message.equals(other.message)) {

			return false;
		}

		if (name == null) {

			if (other.name != null) {

				return false;
			}
		} else if (!name.equals(other.name)) {

			return false;
		}
		return true;
	}

	public String getName() {

		return name;
	}

	public String getMessage() {

		return message;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("InvalidProperty [name=");
		builder.append(name);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

	public int getSortnumber() {

		return sortnumber;
	}
}
