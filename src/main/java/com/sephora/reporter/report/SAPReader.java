package com.sephora.reporter.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.StringUtils;

import com.sephora.reporter.report.model.SAPMonthRecord;
import com.sephora.reporter.report.model.SAPRecord;

public class SAPReader {
	private static final int START_ROW = 11;
	private Workbook wb;
	private int year;
	private int month;
	
	public SAPReader(String path, int year, int month) {
		this.year = year;
		this.month = month;
		try {
			this.wb = WorkbookFactory.create(new File(path));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public SAPMonthRecord read(int storeCode) {
		Sheet sheet = this.wb.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		SAPMonthRecord record = null;
		for (int r = START_ROW;r <= lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell costCell = row.getCell(2);
			if (costCell == null || StringUtils.isEmpty(costCell.getStringCellValue())) {
				break;
			}
			
			if (costCell.getCellStyle().getFillForegroundColor() == IndexedColors.YELLOW.index) {
				String storeVal = midString(costCell.getStringCellValue());
				if (!storeVal.startsWith("6")) {
					continue;
				}
				
				int sCode = Integer.parseInt(storeVal.substring(0, 4));
				if (sCode != storeCode) {
					continue;
				}
				
				record = new SAPMonthRecord();
				record.setStoreCode(sCode);
				record.setYear(year);
				record.setMonth(month);
				
				List<SAPRecord> inners = new ArrayList<>();
				record.setRecords(inners);
				
				for (int rb = r - 1;rb >= START_ROW;rb --) {
					Row bRow = sheet.getRow(rb);
					Cell bCell = bRow.getCell(2);
					if (bCell.getCellStyle().getFillForegroundColor() == IndexedColors.YELLOW.index) {
						break;
					}
					Cell mtdCell = row.getCell(3);
					Cell ytdCell = row.getCell(4);
					SAPRecord innerR = new SAPRecord();
					innerR.setCostElem(midString(bCell.getStringCellValue()));
					innerR.setMtd(mtdCell.getNumericCellValue());
					innerR.setYtd(ytdCell.getNumericCellValue());
					inners.add(innerR);
				}
				break;
			}
		}
		return record;
	}
	
	public Map<Integer, SAPMonthRecord> read() {
		Sheet sheet = this.wb.getSheetAt(0);
		int startRowNum = 11;
		int lastRowNum = sheet.getLastRowNum();
		Map<Integer, SAPMonthRecord> result = new HashMap<>();
		
		SAPMonthRecord smRecord = new SAPMonthRecord();
		List<SAPRecord> storeRecords = new ArrayList<>();
		smRecord.setRecords(storeRecords);
		
		for (int r = startRowNum;r <= lastRowNum;r ++) {
			Row row = sheet.getRow(r);
			Cell costElemCell = row.getCell(2);
			if (costElemCell == null || StringUtils.isEmpty(costElemCell.getStringCellValue())) {
				break;
			}
			Cell mtdCell = row.getCell(3);
			Cell ytdCell = row.getCell(4);
			
			// find this is the row of store
			if (costElemCell.getCellStyle().getFillForegroundColor() == IndexedColors.YELLOW.index) {
				String storeVal = midString(costElemCell.getStringCellValue());
				if (storeVal.startsWith("6")) {
					continue;
				}
				int sCode = Integer.parseInt(storeVal.substring(0, 4));
				smRecord.setYear(year);
				smRecord.setMonth(month);
				smRecord.setStoreCode(sCode);
				result.put(sCode, smRecord);
				
				smRecord = new SAPMonthRecord();
				storeRecords = new ArrayList<>();
				smRecord.setRecords(storeRecords);
			} else {
				SAPRecord record = new SAPRecord();
				record.setCostElem(midString(costElemCell.getStringCellValue()));
				record.setMtd(mtdCell.getNumericCellValue());
				record.setYtd(ytdCell.getNumericCellValue());
				storeRecords.add(record);
			}
		}
		return result;
	}
	
	private String midString(String textValue) {
		return textValue.substring(14);
	}
}
