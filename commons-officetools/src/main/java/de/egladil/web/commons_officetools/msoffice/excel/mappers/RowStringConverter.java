// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_officetools.msoffice.excel.mappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import de.egladil.web.commons_officetools.TableDocumentContentReader;

/**
 * RowStringConverter
 */
public class RowStringConverter implements Function<Row, String> {

	@Override
	public String apply(final Row row) {

		Iterator<Cell> iterator = row.cellIterator();

		List<String> all = new ArrayList<>();

		while (iterator.hasNext()) {

			Cell cell = iterator.next();
			CellType cellType = cell.getCellType();

			switch (cellType) {

			case STRING:
				all.add(cell.getStringCellValue());
				break;

			case NUMERIC:
				all.add(cell.getNumericCellValue() + "");
				break;

			case BOOLEAN:
				all.add(cell.getBooleanCellValue() + "");
				break;

			case BLANK:
				all.add("");
				break;

			default:
				break;
			}
		}

		return StringUtils.join(all, TableDocumentContentReader.SEPARATION_CHAR);
	}
}
