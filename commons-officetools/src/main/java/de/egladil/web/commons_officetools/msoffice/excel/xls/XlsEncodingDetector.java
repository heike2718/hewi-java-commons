// =====================================================
// Project: commons-officetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.xls;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_officetools.EncodingDetector;
import de.egladil.web.commons_officetools.FileType;

/**
 * XlsEncodingDetector
 */
public class XlsEncodingDetector implements EncodingDetector {

	private static final Logger LOGGER = LoggerFactory.getLogger(XlsEncodingDetector.class);

	@Override
	public Optional<String> detectEncoding(final String pathToFile) {

		LOGGER.warn("Encoding eines File mit FileType {} kann nicht ermittelt werden", FileType.EXCEL_ALT);

		return Optional.empty();
	}

}
