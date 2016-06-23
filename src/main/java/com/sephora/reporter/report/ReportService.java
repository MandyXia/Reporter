package com.sephora.reporter.report;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.sephora.reporter.entities.FinanceSource;
import com.sephora.reporter.report.helper.Allocs;
import com.sephora.reporter.report.helper.Cord;
import com.sephora.reporter.report.helper.Cords;
import com.sephora.reporter.report.helper.Formula;
import com.sephora.reporter.report.helper.Formulas;
import com.sephora.reporter.report.model.SAPMonthRecord;
import com.sephora.reporter.report.model.SAPRecord;
import com.sephora.reporter.report.model.SalesRecord;
import com.sephora.reporter.utils.PathUtils;

@Component
public class ReportService {
	private static final int START_ROW = 6;
	private static final int START_COL = 1;
	private static final CellRangeAddress TEMPLATE_RANGE = new CellRangeAddress(0, 39, 0, 0);
	
	public List<String> runTimeReport(FinanceSource[] fss, int[] stores, boolean isMonth) {
		List<String> result = new ArrayList<>();
		for (int store : stores) {
			result.add(runTimeReportByStore(fss, store, isMonth));
		}
		return result;
	}

	public String runTimeReportByStore(FinanceSource[] fss, int store, boolean isMonth) {
		String foName = PathUtils.getRoot() + "/" + store + ".xlsx";
		copyTemplateFile(getClass().getResourceAsStream("/ProfitTemplate.xlsx"), foName);
		
		int len = fss.length;
		Workbook target = null;
		try {
			target = WorkbookFactory.create(new FileInputStream(foName));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		int col = START_COL;
		for (int i = 0; i < len; i++) {
			/* create the sheet for this store */
			Sheet storeSheet = target.createSheet(store + "-" + fss[i].getSourceYear() + "-" + fss[i].getSourceMonth());
			copyTemplateRange(target.getSheetAt(0), storeSheet);

			SAPReader sr = new SAPReader(fss[i].getSapFilePath(), fss[i].getSourceYear(), fss[i].getSourceMonth());
			SalesReader sl = new SalesReader(fss[i].getSalesFilePath(), fss[i].getSourceYear(), fss[i].getSourceMonth());
			SupplierReader sp = new SupplierReader(fss[i].getSupplierPath());

			SAPMonthRecord srrep = sr.read(store);
			SalesRecord slrep = sl.read(store);
			Map<Integer, Double> suptotalMap = sp.readAll();
			double sdcount = suptotalMap.get(store);

			for (Cord cord : Cords.ALL) {
				int rowOffset = cord.getRowOffset();
				double rowVal = 0d;
				
				Row targetRow = storeSheet.getRow(START_ROW + rowOffset);
				if (targetRow == null) {
					targetRow = storeSheet.createRow(START_ROW + rowOffset);
				}
				Cell targetcell = targetRow.createCell(col, Cell.CELL_TYPE_NUMERIC);
				
				switch (cord.getFrom()) {
				case Cord.FROM_SAP:
					String sapName = cord.getRef();
					Set<String> sapNameSet = new HashSet<>();
					if (sapName.contains("/")) {
						String[] allnames = sapName.split("\\/");
						for (String an : allnames) {
							sapNameSet.add(an);
						}
					} else {
						sapNameSet.add(sapName);
					}
					
					double midVal = 0d;
					for (SAPRecord srrec : srrep.getRecords()) {
						if (sapNameSet.contains(srrec.getCostElem())) {
							if (isMonth) {
								midVal += srrec.getMtd();
							} else {
								midVal += srrec.getYtd();
							}
						}
					}
					rowVal = midVal;
					System.out.println("Row " + (rowOffset + START_ROW) + " From SAP of [" + sapName + "] with value [" + rowVal + "]");
					break;
				case Cord.FROM_SALES:
					String refSale = cord.getRef();
					if (refSale.equalsIgnoreCase("vat")) {
						if (isMonth) {
							rowVal = slrep.getVatmtd();
						} else {
							rowVal = slrep.getVatytd();
						}
					} else if (refSale.equalsIgnoreCase("cogs")) {
						if (isMonth) {
							rowVal = slrep.getCogsmtd();
						} else {
							rowVal = slrep.getCogsytd();
						}
					}
					System.out.println("Row " + (rowOffset + START_ROW) + " From Sales of [" + refSale + "] with value [" + rowVal + "]");
					break;
				case Cord.FROM_ALLOCATE:
					String alloc = cord.getRef();
					double total = Allocs.getAllocByName(alloc, fss[i]);
					if (isMonth) {
						System.out.println("P=" + srrep.getNetSalesMp());
						System.out.println("T=" + total);
						rowVal = srrep.getNetSalesMp() * total;
					} else {
						rowVal = srrep.getNetSalesYp() * total;
					}
					// TODO: add alloc
					System.out.println("Row " + (rowOffset + START_ROW) + " Alloc " + rowVal);
					break;
				case Cord.FROM_FORMULA:
					int formula = Integer.parseInt(cord.getRef());
					if (formula < Formulas.ALL.length) {
						Formula f = Formulas.ALL[formula];
						String fStr = f.render(targetcell.getAddress());
						targetcell.setCellFormula(fStr);
						System.out.println("Row " + (rowOffset + START_ROW) + " Formula [" + fStr + "]");
					}
					break;
				case Cord.FROM_SUP:
					rowVal = sdcount;
					System.out.println("Row " + (rowOffset + START_ROW) + " Supplier [" + rowVal + "]");
					break;
				case Cord.FROM_OTHER:
					System.out.println("Row " + (rowOffset + START_ROW) + " Other 0");
					break;
				default:
					break;
				}
				targetcell.setCellValue(rowVal);
			}
		}

		// This is important to evaluate them
		XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook)target);
		try (FileOutputStream fo = new FileOutputStream(foName)) {
			target.write(fo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return foName;
	}
	
	private void copyTemplateFile(InputStream is, String outPath) {
		try (FileOutputStream fo = new FileOutputStream(outPath)) {
			byte[] buffer = new byte[8192];
			int len = -1;
			while ((len = is.read(buffer, 0, 8192)) != -1) {
				fo.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void copyTemplateRange(Sheet origin, Sheet target) {
		int startRow = TEMPLATE_RANGE.getFirstRow();
		int endRow = TEMPLATE_RANGE.getLastRow();
		int startCol = TEMPLATE_RANGE.getFirstColumn();
		int endCol = TEMPLATE_RANGE.getLastColumn();

		for (int r = startRow; r <= endRow; r++) {
			for (int c = startCol; c <= endCol; c++) {
				Cell cell = origin.getRow(r).getCell(c);
				CellStyle style = cell.getCellStyle();
				String text = cell.getStringCellValue();
				
				Cell tcell = target.createRow(r).createCell(c, cell.getCellType());
				tcell.setCellStyle(style);
				tcell.setCellValue(text);
			}
		}
	}
}
