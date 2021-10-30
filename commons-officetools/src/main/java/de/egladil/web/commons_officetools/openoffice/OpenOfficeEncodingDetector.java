// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.openoffice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import de.egladil.web.commons_officetools.EncodingDetector;
import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;
import de.egladil.web.commons_officetools.files.XmlDeclarationParser;
import de.egladil.web.commons_officetools.openoffice.ods.ODSParser;

/**
 * OpenOfficeEncodingDetector
 */
public class OpenOfficeEncodingDetector implements EncodingDetector {

	@Override
	public Optional<String> detectEncoding(final String pathToFile) {

		try (InputStream in = new FileInputStream(new File(pathToFile))) {

			final String xml = new ODSParser().getContentSafe(in, 6000000);
			int indexOfRootTag = xml.indexOf("<office:document-content");

			String xmlDeclaration = xml.substring(0, indexOfRootTag);

			return new XmlDeclarationParser().getEncodingFromXmlDeclaration(xmlDeclaration);

		} catch (IOException e) {

			throw new OfficeToolsRuntimeException("Fehler beim Lesen der ODF-Datei: " + e.getMessage(), e);
		}
	}

}
