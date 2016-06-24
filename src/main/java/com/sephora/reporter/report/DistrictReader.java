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

public class DistrictReader {
	private String districtPath;
	
	public DistrictReader(String path) {
		this.districtPath = path;
	}
	
	public Map<Integer, String> readAll() {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(new File(this.districtPath));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
		
		Sheet sheet = wb.getSheet("MPP no. of stores");
		int startRow = 4;
		int lastRow = sheet.getLastRowNum();
		Map<Integer, String> result = new HashMap<>();
		for (int r = startRow;r <= lastRow;r ++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				break;
			}
			
			Cell codeCell = row.getCell(1);
			Cell distCell = row.getCell(3);
			if (isCellEmpty(codeCell)) {
				break;
			}
			
			if (isCellEmpty(distCell) || codeCell.getCellType() == Cell.CELL_TYPE_STRING) {
				continue;
			}
			
			
			int code = (int) codeCell.getNumericCellValue();
			if (String.valueOf(code).startsWith("6")) {
				String dist = distCell.getStringCellValue();
				result.put(code, dist);
			}
		}
		return result;
	}
	
	private boolean isCellEmpty(Cell cell) {
		if (cell == null || (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && cell.getNumericCellValue() == 0d) || (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty())) {
			return true;
		} else {
			return false;
		}
	}
}
