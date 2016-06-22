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
		for (int r = START_ROW;r < lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell nameCell = row.getCell(0);
			if (nameCell == null || StringUtils.isEmpty(nameCell.getStringCellValue())) {
				break;
			}
			
			String cellVal = nameCell.getStringCellValue();
			if (!cellVal.startsWith("6")) {
				continue;
			}
			
			String code = cellVal.substring(0, 4);
			int storeCode = Integer.parseInt(code);
			
			SalesRecord record = new SalesRecord();
			Cell vatCell = row.getCell(1);
			Cell vatyCell = row.getCell(2);
			Cell cogsCell = row.getCell(3);
			Cell cogsyCell = row.getCell(4);
			
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
		Sheet sheet = this.wb.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		SalesRecord record = null;
		for (int r = START_ROW;r < lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell nameCell = row.getCell(0);
			if (nameCell == null || StringUtils.isEmpty(nameCell.getStringCellValue())) {
				break;
			}
			
			String cellVal = nameCell.getStringCellValue();
			if (!cellVal.startsWith("6")) {
				continue;
			}
			
			String code = cellVal.substring(0, 4);
			int sCode = Integer.parseInt(code);
			
			if (sCode == storeCode) {
				record = new SalesRecord();
				record.setYear(year);
				record.setMonth(month);
				record.setStoreCode(sCode);
				record.setVatmtd(row.getCell(1).getNumericCellValue());
				record.setVatytd(row.getCell(2).getNumericCellValue());
				record.setCogsmtd(row.getCell(3).getNumericCellValue());
				record.setCogsytd(row.getCell(4).getNumericCellValue());
				break;
			}
		}
		return record;
	}
}
