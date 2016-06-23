package com.sephora.reporter.resources;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.sephora.reporter.entities.FinanceSource;
import com.sephora.reporter.entities.StoreRecord;
import com.sephora.reporter.report.ReportService;
import com.sephora.reporter.repositories.FinanceSourceRepository;
import com.sephora.reporter.repositories.StoreRecordRepository;

@Path("/report")
public class ReportResources {
	
	@Autowired
	private ReportService svc;
	
	@Autowired
	private FinanceSourceRepository repo;
	
	@Autowired
	private StoreRecordRepository storeRepo;
	
	@GET
	@Path("/time/year/{year}/month/{month}")
	public List<String> runYearReport(@PathParam("year") int year, @PathParam("month") int month) {
		if (year == 0 || month == 0) {
			return Arrays.asList("year, month are mandatory");
		}
		List<StoreRecord> stores = storeRepo.findByYearAndMonth(year, month);
		int[] storeCodes = new int[stores.size()];
		int i = 0;
		for (StoreRecord storer : stores) {
			storeCodes[i ++] = storer.getCode();
		}
		
		FinanceSource src = repo.findBySourceYearAndSourceMonth(year, month);
		if (src != null && src.getSapFilePath() != null && src.getSalesFilePath() != null) {
			return svc.runTimeReport(new FinanceSource[]{src}, storeCodes, true);
		} else {
			return Arrays.asList("No Source Available");
		}
	}
	
	@GET
	@Path("/time/year/{year}/month/{month}/store/{store}")
	public String runMonthReport(@PathParam("year") int year, @PathParam("month") int month, @PathParam("store") int store) {
		if (year == 0 || month == 0 || store == 0) {
			return "year, month, store code are mandatory";
		}
		
		FinanceSource src = repo.findBySourceYearAndSourceMonth(year, month);
		if (src != null && src.getSapFilePath() != null && src.getSalesFilePath() != null) {
			return svc.runTimeReportByStore(new FinanceSource[]{src}, store, true);
		} else {
			return "No Source Available";
		}
	}
}
