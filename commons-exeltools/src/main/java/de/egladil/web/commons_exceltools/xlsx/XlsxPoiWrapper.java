// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.xlsx;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_exceltools.FileType;
import de.egladil.web.commons_exceltools.error.ExceltoolsRuntimeException;
import de.egladil.web.commons_exceltools.impl.PoiWrapper;
import de.egladil.web.commons_exceltools.utils.XmlDeclarationParser;

/**
 * XlsxPoiWrapper
 */
public class XlsxPoiWrapper implements PoiWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(XlsxPoiWrapper.class);

	@Override
	public FileType wrapperFor() {

		return FileType.EXCEL_NEU;
	}

	@Override
	public List<Row> getRows(final InputStream in) throws IOException {

		final List<Row> result = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(in)) {

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.iterator();
			iterator.forEachRemaining(row -> result.add(row));
		}

		return result;
	}

	@Override
	public Optional<String> detectEncoding(final String pathOfFile) {

		try {

			OPCPackage pkg = OPCPackage.open(pathOfFile);
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

				throw new ExceltoolsRuntimeException("Exception when parsing the workbook: " + e.getMessage(), e);
			}

		} catch (InvalidFormatException e) {

			LOGGER.error("File {} existiert nicht oder hat nicht den FileType {} ", pathOfFile, wrapperFor());
			throw new ExceltoolsRuntimeException(
				"Das Encoding der Datei " + pathOfFile + " sich nicht ermitteln: " + e.getMessage(), e);
		}
	}
}
