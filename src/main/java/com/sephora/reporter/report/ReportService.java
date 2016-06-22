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
	private List<SAPReader> saprs;
	private List<SalesReader> salesrs;
	
	public ReportService(String[] sapFile, String[] salesFile) {
		saprs = new ArrayList<>();
		for (String sa : sapFile) {
			SAPReader sr = new SAPReader(sa);
			saprs.add(sr);
		}
		
		salesrs = new ArrayList<>();
		for (String sl : salesFile) {
			SalesReader ssr = new SalesReader(sl);
			salesrs.add(ssr);
		}
	}
	
	public void generate(List<Store> stores, List<Date> months, int mode) {
		if (mode == MODE_TIME) {
			//suppose only one store for now
			Store onestore = stores.get(0);
		}
			
		Map<Store, List<SAPRecord>> saps = sapreader.read();
	}
}
