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

import com.sephora.reporter.report.model.SalesRecord;

public class SalesReader {
	private static final int START_ROW = 3;
	private Workbook wb;
	private int year;
	private int month;
	
	public SalesReader(String path, int year, int month) {
		this.year = year;
		this.month = month;
		try {
			this.wb = WorkbookFactory.create(new File(path));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Integer, SalesRecord> readAll() {
		Sheet sheet = this.wb.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		Map<Integer, SalesRecord> result = new HashMap<>();
		boolean startRead = false;
		for (int r = START_ROW;r < lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell nameCell = row.getCell(1);
			if (nameCell == null || nameCell.getCellType() != Cell.CELL_TYPE_STRING || StringUtils.isEmpty(nameCell.getStringCellValue())) {
				if (startRead) {
					System.out.println("Already start, break");
					break;
				} else {
					System.out.println("Continue, not started");
					continue;
				}
			}
			
			String cellVal = nameCell.getStringCellValue();
			if (!cellVal.startsWith("6")) {
				continue;
			}
			
			startRead = true;
			
			String code = cellVal.substring(0, 4);
			int storeCode = Integer.parseInt(code);
			
			SalesRecord record = new SalesRecord();
			Cell vatCell = row.getCell(2);
			Cell vatyCell = row.getCell(3);
			Cell cogsCell = row.getCell(4);
			Cell cogsyCell = row.getCell(5);
			
			record.setStoreCode(storeCode);
			record.setYear(year);
			record.setMonth(month);
			record.setVatmtd(vatCell.getNumericCellValue());
			record.setVatytd(vatyCell.getNumericCellValue());
			record.setCogsmtd(cogsCell.getNumericCellValue());
			record.setCogsytd(cogsyCell.getNumericCellValue());
			result.put(storeCode, record);
		}
		
		return result;
	}
	
	public SalesRecord read(int storeCode) {
		Map<Integer, SalesRecord> result = readAll();
		for (Map.Entry<Integer, SalesRecord> entry : result.entrySet()) {
			if (entry.getKey() != null && entry.getKey() == storeCode) {
				return entry.getValue();
			}
		}
		return null;
	}
}
