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
		double vatmall = 0d, vatyall = 0d, cogsmall = 0d, cogsyall = 0d;
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
			
			vatmall += vatCell.getNumericCellValue();
			vatyall += vatyCell.getNumericCellValue();
			cogsmall += cogsCell.getNumericCellValue();
			cogsyall += cogsyCell.getNumericCellValue();
		}
		
		for (Map.Entry<Integer, SalesRecord> entry : result.entrySet()) {
			SalesRecord sre = entry.getValue();
			if (vatmall != 0) {
				double vat = sre.getVatmtd();
				sre.setVatmp(vat / vatmall);
			}
			
			if (vatyall != 0) {
				double vaty = sre.getVatytd();
				sre.setVatyp(vaty / vatyall);
			}
			
			if (cogsmall != 0) {
				double cogs = sre.getCogsmtd();
				sre.setCogsmp(cogs / cogsmall);
			}
			
			if (cogsyall != 0) {
				double cogsy = sre.getCogsytd();
				sre.setCogsyp(cogsy / cogsyall);
			}
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
