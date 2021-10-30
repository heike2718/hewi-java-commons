// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.xlsx;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_officetools.EncodingDetector;
import de.egladil.web.commons_officetools.FileType;
import de.egladil.web.commons_officetools.exceptions.OfficeToolsRuntimeException;
import de.egladil.web.commons_officetools.files.XmlDeclarationParser;

/**
 * XlsxEncodingDetector
 */
public class XlsxEncodingDetector implements EncodingDetector {

	private static final Logger LOGGER = LoggerFactory.getLogger(XlsxEncodingDetector.class);

	@Override
	public Optional<String> detectEncoding(final String pathToFile) {

		try {

			OPCPackage pkg = OPCPackage.open(pathToFile);
			ArrayList<PackagePart> parts = pkg.getParts();

			Optional<PackagePart> optWorkbook = parts.stream().filter(p -> "/xl/workbook.xml".equals(p.getPartName().getName()))
				.findFirst();

			if (optWorkbook.isEmpty()) {

				LOGGER.warn("konnte workbook.xml nicht finden");
				return Optional.empty();
			}

			PackagePart workbook = optWorkbook.get();

			try (InputStream workbookIn = workbook.getInputStream(); StringWriter sw = new StringWriter()) {

				IOUtils.copy(workbookIn, sw, "UTF-8");

				String theXml = sw.toString();
				int indexOfRootTag = theXml.indexOf("<workbook");

				String xmlDeclaration = theXml.substring(0, indexOfRootTag);

				return new XmlDeclarationParser().getEncodingFromXmlDeclaration(xmlDeclaration);

			} catch (IOException e) {

				throw new OfficeToolsRuntimeException("Exception when parsing the workbook: " + e.getMessage(), e);
			}

		} catch (InvalidFormatException e) {

			LOGGER.error("File {} existiert nicht oder hat nicht den FileType {} ", pathToFile, FileType.EXCEL_NEU);
			throw new OfficeToolsRuntimeException(
				"Das Encoding der Datei " + pathToFile + " sich nicht ermitteln: " + e.getMessage(), e);
		}
	}

}
