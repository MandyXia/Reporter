package com.sephora.reporter.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.sephora.reporter.report.model.SAPRecord;
import com.sephora.reporter.report.model.Store;

public class ReportService {
	private Workbook wb;
	private String[] sapFiles;
	private String[] salesFiles;
	
	public String runTimeReport(String[] sapfiles, String[] salesfiles, int[] years, int[] months, int[] stores) {
		
	}
	
	public String runTimeReportByStore(String[] sapfiles, String[] salesfiles, int[] years, int[] months, int store) {
		int len = sapfiles.length;
		List<SAPReader> saps = new ArrayList<>();
		List<SalesReader> sales = new ArrayList<>();
		for (int i = 0;i < len;i ++) {
			SAPReader sr = new SAPReader(sapfiles[i], years[i], months[i]);
			SalesReader sl = new SalesReader(salesfiles[i], years[i], months[i]);
		}
	}
	
	public void generate(List<Store> stores, List<Date> months, int mode) {
		if (mode == MODE_TIME) {
			//suppose only one store for now
			Store onestore = stores.get(0);
			
			saprs = new ArrayList<>();
			salesrs = new ArrayList<>();
			int len = months.length;
			for (int i = 0;i < len;i ++) {
				saprs.add(new SAPReader(sapFile[i], months[i]));
				salesrs.add(new SalesReader(salesFile[i], months[i]));
			}
		}
			
		Map<Store, List<SAPRecord>> saps = sapreader.read();
	}
}
