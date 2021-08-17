// =====================================================
// Project: commons-openofficetools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_openofficetools.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

/**
 * OORows2LinesMapper
 */
public class OORows2LinesMapper implements Function<List<OpenOfficeTableElement>, List<String>> {

	@Override
	public List<String> apply(final List<OpenOfficeTableElement> arg0) {

		List<String> result = new ArrayList<>();

		for (OpenOfficeTableElement element : arg0) {

			if (!element.isEmptyRow()) {

				List<OpenOfficeTableElement> row = element.getChildElements();

				List<String> mappedRow = new ArrayList<>(row.size());

				for (int i = 0; i < row.size(); i++) {

					OpenOfficeTableElement cell = row.get(i);

					if (cell.isFormula()) {

						// mappedRow.add("");
					} else {

						mappedRow.add(cell.getContent());
					}
				}

				String zeile = StringUtils.join(mappedRow, ';');
				result.add(zeile);
			}
		}

		return result;
	}

}
