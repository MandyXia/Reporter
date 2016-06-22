package com.sephora.reporter.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.sephora.reporter.report.model.Store;

public class SAPReader {
	private static final int START_ROW = 11;
	private Workbook wb;
	private Date month;
	
	public SAPReader(String path, Date month) {
		this.month = month;
		try {
			this.wb = WorkbookFactory.create(new File(path));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public SAPMonthRecord read(Store store) {
		Sheet sheet = this.wb.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		SAPMonthRecord record = null;
		for (int r = START_ROW;r <= lastRow;r ++) {
			Row row = sheet.getRow(r);
			Cell costCell = row.getCell(2);
			if (costCell == null || StringUtils.isEmpty(costCell.getStringCellValue())) {
				break;
			}
			
			if (costCell.getCellStyle().getFillForegroundColor() == IndexedColors.YELLOW.index && costCell.getStringCellValue().contains(store.getName())) {
				record = new SAPMonthRecord();
				record.setStore(store);
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
	
	public Map<Store, SAPMonthRecord> read() {
		Sheet sheet = this.wb.getSheetAt(0);
		int startRowNum = 11;
		int lastRowNum = sheet.getLastRowNum();
		Map<Store, SAPMonthRecord> result = new HashMap<>();
		
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
				Store store = new Store(costElemCell.getStringCellValue());
				smRecord.setMonth(month);
				smRecord.setStore(store);
				result.put(store, smRecord);
				
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
