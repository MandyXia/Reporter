package com.sephora.reporter.report;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.StringUtils;

public class SupplierReader {
	private Workbook wb;

	public SupplierReader(String path) {
		try {
			this.wb = WorkbookFactory.create(new File(path));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Double> readAll() {
		Map<Integer, Double> result = new HashMap<>();
		Sheet sheet = this.wb.getSheet("Total");
		if (sheet != null) {
			int lastRow = sheet.getLastRowNum();
			int totalCol = -1;
			for (int r = 0;r <= lastRow;r ++) {
				Row row = sheet.getRow(r);
				if (totalCol == -1) {
					int lastCellNum = row.getLastCellNum();
					for (int tc = 0;tc <= lastCellNum;tc ++) {
						Cell tcell = row.getCell(tc);
						if (tcell == null || tcell.getCellType() != Cell.CELL_TYPE_STRING || StringUtils.isEmpty(tcell.getStringCellValue()) || !"TOTAL".equalsIgnoreCase(tcell.getStringCellValue())) {
							continue;
						}
						totalCol = tc;
						break;
					}
					continue;
				}
				
				Cell storeCell = row.getCell(0);
				if (storeCell == null || storeCell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
					break;
				}
				
				int storeCode = (int) storeCell.getNumericCellValue();
				double total = row.getCell(totalCol).getNumericCellValue();
				result.put(storeCode, total);
			}
		}
		return result;
	}
}
