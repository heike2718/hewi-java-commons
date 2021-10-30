// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.files;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XmlDeclarationParser
 */
public class XmlDeclarationParser {

	private static final String ENCODING_ATTRIBUTE_NAME = "encoding";

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlDeclarationParser.class);

	/**
	 * @param  xmlDeclaration
	 * @return
	 */
	public Optional<String> getEncodingFromXmlDeclaration(final String xmlDeclaration) {

		if (xmlDeclaration == null) {

			return Optional.empty();
		}

		try {

			String striped = xmlDeclaration.trim().replace("<\\?xml", "");
			striped = striped.replaceAll("\\?>", "");

			String[] tokens = striped.split(" ");
			Optional<String> optEncodingToken = Arrays.stream(tokens).filter(t -> t.startsWith(ENCODING_ATTRIBUTE_NAME))
				.findFirst();

			if (optEncodingToken.isEmpty()) {

				return Optional.empty();
			}
			String encodingValue = optEncodingToken.get().split("=")[1];
			encodingValue = encodingValue.replaceAll("\"", "");

			return Optional.of(encodingValue);
		} catch (Exception e) {

			LOGGER.warn("Konnte encoding nicht ermitteln: " + e.getMessage(), e);
			return Optional.empty();
		}
	}

}
