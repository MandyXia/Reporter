package com.sephora.reporter.report;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
import com.sephora.reporter.report.model.Store;

public class SalesReader {
	private static final int START_ROW = 4;
	private Workbook wb;
	private Date month;
	
	public SalesReader(String path, Date month) {
		this.month = month;
		try {
			this.wb = WorkbookFactory.create(new File(path));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Store, SalesRecord> readAll() {
		Sheet sheet = this.wb.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		Map<Store, SalesRecord> result = new HashMap<>();
		for (int r = START_ROW;r < lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell nameCell = row.getCell(2);
			if (nameCell == null || StringUtils.isEmpty(nameCell.getStringCellValue())) {
				break;
			}
			
			SalesRecord record = new SalesRecord();
			Cell vatCell = row.getCell(3);
			Cell cogsCell = row.getCell(4);
			
			Store store = new Store(nameCell.getStringCellValue());
			record.setStore(store);
			record.setMonth(month);
			record.setVat(vatCell.getNumericCellValue());
			record.setCogs(cogsCell.getNumericCellValue());
			result.put(store, record);
		}
		return result;
	}
	
	public SalesRecord read(Store store) {
		Sheet sheet = this.wb.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		SalesRecord record = null;
		for (int r = START_ROW;r < lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell nameCell = row.getCell(2);
			if (nameCell == null || StringUtils.isEmpty(nameCell.getStringCellValue())) {
				break;
			}
			
			if (nameCell.getStringCellValue().contains(store.getCode())) {
				record = new SalesRecord();
				record.setMonth(month);
				record.setStore(store);
				record.setVat(row.getCell(3).getNumericCellValue());
				record.setCogs(row.getCell(4).getNumericCellValue());
				break;
			}
		}
		return record;
	}
}
