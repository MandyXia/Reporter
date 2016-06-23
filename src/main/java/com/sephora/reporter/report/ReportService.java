package com.sephora.reporter.report;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.sephora.reporter.report.model.Cord;
import com.sephora.reporter.report.model.Cords;
import com.sephora.reporter.report.model.SAPMonthRecord;
import com.sephora.reporter.report.model.SAPRecord;
import com.sephora.reporter.report.model.SalesRecord;
import com.sephora.reporter.utils.PathUtils;

@Component
public class ReportService {
	private static final int START_ROW = 6;
	private static final int START_COL = 1;
	private static final CellRangeAddress TEMPLATE_RANGE = new CellRangeAddress(0, 38, 0, 0);

	public String runTimeReportByStore(String[] sapfiles, String[] salesfiles, int[] years, int[] months, int store) {
		int len = sapfiles.length;
		Workbook template = null;
		Workbook target = new XSSFWorkbook();
		try {
			template = WorkbookFactory.create(this.getClass().getResourceAsStream("/ProfitTemplate.xlsx"));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		int col = START_COL;
		for (int i = 0; i < len; i++) {
			/* create the sheet for this store */
			Sheet storeSheet = target.createSheet(store + "-" + years[i] + "-" + months[i]);

			copyTemplate(template.getSheetAt(0), storeSheet);

			SAPReader sr = new SAPReader(sapfiles[i], years[i], months[i]);
			SalesReader sl = new SalesReader(salesfiles[i], years[i], months[i]);

			SAPMonthRecord srrep = sr.read(store);
			SalesRecord slrep = sl.read(store);

			for (Cord cord : Cords.ALL) {
				int rowOffset = cord.getRowOffset();
				double rowVal = 0d;
				switch (cord.getFrom()) {
				case Cord.FROM_SAP:
					String sapName = cord.getRef();
					for (SAPRecord srrec : srrep.getRecords()) {
						if (srrec.getCostElem().equalsIgnoreCase(sapName)) {
							rowVal = srrec.getMtd();
							break;
						}
					}
					break;
				case Cord.FROM_SALES:
					String refSale = cord.getRef();
					if (refSale.equalsIgnoreCase("vat")) {
						rowVal = slrep.getVatmtd();
					} else if (refSale.equalsIgnoreCase("cogs")) {
						rowVal = slrep.getCogsmtd();
					}
					break;
				case Cord.FROM_ALLOCATE:
					String alloc = cord.getRef();
					// TODO: add alloc
					break;
				case Cord.FROM_FORMULA:
					String formula = cord.getRef();
					// TODO: add formula
					break;
				case Cord.FROM_OTHER:
					break;
				default:
					break;
				}
				Cell targetcell = storeSheet.getRow(START_ROW + rowOffset).getCell(col);
				targetcell.setCellValue(rowVal);
			}
		}

		String foName = PathUtils.getRoot() + "/" + store + ".xlsx";
		try (FileOutputStream fo = new FileOutputStream(foName)) {
			target.write(fo);
			target.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foName;
	}

	private void copyTemplate(Sheet origin, Sheet target) {
		int startRow = TEMPLATE_RANGE.getFirstRow();
		int endRow = TEMPLATE_RANGE.getLastRow();
		int startCol = TEMPLATE_RANGE.getFirstColumn();
		int endCol = TEMPLATE_RANGE.getLastColumn();

		for (int r = startRow; r <= endRow; r++) {
			for (int c = startCol; c <= endCol; c++) {
				Cell cell = origin.getRow(r).getCell(c);
				CellStyle style = cell.getCellStyle();
				String text = cell.getStringCellValue();

				Cell tcell = target.getRow(r).getCell(c);
				tcell.setCellStyle(style);
				tcell.setCellValue(text);
			}
		}
	}
}
