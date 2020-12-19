// =====================================================
// Projekt: commons-mailer
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_mailer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DefaultEmailDaten ist eine Default-Implementierung, die man für das Interface verwenden kann.
 */
public class DefaultEmailDaten implements EmailDaten {

	private String empfaenger;

	private String betreff;

	private String text;

	private List<String> hiddenEmpfaenger = new ArrayList<>();

	public void addHiddenEmpfaenger(final String empfaenger) {

		if (empfaenger != null) {

			hiddenEmpfaenger.add(empfaenger);
		}
	}

	public void addHiddenEmpfaenger(final Collection<String> empfaenger) {

		if (empfaenger != null) {

			hiddenEmpfaenger.addAll(empfaenger);
		}
	}

	@Override
	public String getEmpfaenger() {

		return this.empfaenger;
	}

	@Override
	public String getBetreff() {

		return this.betreff;
	}

	@Override
	public String getText() {

		return this.text;
	}

	@Override
	public Collection<String> getHiddenEmpfaenger() {

		return this.hiddenEmpfaenger;
	}

	@Override
	public List<String> alleEmpfaengerFuersLog() {

		List<String> result = this.hiddenEmpfaenger.stream().collect(Collectors.toList());
		result.add(this.empfaenger);
		return result;
	}

	public void setEmpfaenger(final String empfaenger) {

		this.empfaenger = empfaenger;
	}

	public void setBetreff(final String betreff) {

		this.betreff = betreff;
	}

	public void setText(final String text) {

		this.text = text;
	}

}
