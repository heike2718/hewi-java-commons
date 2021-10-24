// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * XmlDeclarationParserTest
 */
public class XmlDeclarationParserTest {

	private final XmlDeclarationParser parser = new XmlDeclarationParser();

	@Test
	void should_getEncodingFromXmlDeclarationReturnEmpty_when_parameterNull() {

		// Act + Assert

		assertTrue(parser.getEncodingFromXmlDeclaration(null).isEmpty());

	}

	@Test
	void should_getEncodingFromXmlDeclarationReturnEmpty_when_parameterBlank() {

		// Act + Assert
		assertTrue(parser.getEncodingFromXmlDeclaration("   ").isEmpty());

	}

	@Test
	void should_getEncodingFromXmlDeclarationReturnEmpty_when_parameterDoesNotContainAttributeEncoding() {

		// Act + Assert
		assertTrue(parser.getEncodingFromXmlDeclaration("Kauder Welsch").isEmpty());

	}

	@Test
	void should_getEncodingFromXmlDeclarationReturnTheValue_when_parameterDoesContainAttributeEncoding() {

		// Act
		Optional<String> optResult = parser.getEncodingFromXmlDeclaration("Kauder Welsch encoding=\"BLA\"");

		// Assert
		assertTrue(optResult.isPresent());
		assertEquals("BLA", optResult.get());

	}

	@Test
	void should_getEncodingFromXmlDeclarationReturnTheValue_when_encodingAttributeIsTheLastAttribute() {

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

		// Act
		Optional<String> optResult = parser.getEncodingFromXmlDeclaration(xml);

		// Assert
		assertTrue(optResult.isPresent());
		assertEquals("UTF-8", optResult.get());
	}

}
