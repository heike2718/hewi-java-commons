// =====================================================
// Projekt: commons-mailer
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.commons_mailer.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Address;
import javax.mail.SendFailedException;

/**
 * InvalidMailAddressException
 */
public class InvalidMailAddressException extends RuntimeException {

	private final SendFailedException sendFailedException;

	/* serialVersionUID */
	private static final long serialVersionUID = 1L;

	public InvalidMailAddressException(final String message, final SendFailedException e) {

		super(message);
		this.sendFailedException = e;
	}

	public List<String> getAllInvalidAdresses() {

		Address[] addresses = sendFailedException.getInvalidAddresses();
		return toStringList(addresses);
	}

	public List<String> getAllValidSentAddresses() {

		Address[] addresses = sendFailedException.getValidSentAddresses();
		return toStringList(addresses);
	}

	public List<String> getAllValidUnsentAddresses() {

		Address[] addresses = sendFailedException.getValidUnsentAddresses();
		return toStringList(addresses);
	}

	private List<String> toStringList(final Address[] addresses) {

		List<String> result = new ArrayList<>();

		if (addresses != null) {

			Arrays.stream(addresses).map(a -> a.toString()).forEach(s -> {

				if (!result.contains(s)) {

					result.add(s);
				}
			});
		}
		return result;
	}
}
