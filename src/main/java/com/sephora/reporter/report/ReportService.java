package com.sephora.reporter.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.sephora.reporter.report.model.SAPRecord;
import com.sephora.reporter.report.model.Store;

public class ReportService {
	public static final int MODE_TIME = 1;
	public static final int MODE_STORE = 2;
	
	private Workbook wb;
	private String[] sapFiles;
	private String[] salesFiles;
	
	public ReportService(String[] sapFiles, String[] salesFiles, Date[] months) {
		this.sapFiles = sapFiles;
		this.salesFiles = salesFiles;
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
