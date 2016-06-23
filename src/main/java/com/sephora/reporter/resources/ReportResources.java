package com.sephora.reporter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.sephora.reporter.entities.FinanceSource;
import com.sephora.reporter.report.ReportService;
import com.sephora.reporter.repositories.FinanceSourceRepository;

@Path("/report")
public class ReportResources {
	
	@Autowired
	private ReportService svc;
	
	@Autowired
	private FinanceSourceRepository repo;
	
	@GET
	@Path("/time/{year}/{month}/{store}")
	public String runMonthReport(@PathParam("year") int year, @PathParam("month") int month, @PathParam("store") int store) {
		if (year == 0 || month == 0 || store == 0) {
			return "year, month, store code are mandatory";
		}
		
		FinanceSource src = repo.findBySourceYearAndSourceMonth(year, month);
		if (src != null && src.getSapFilePath() != null && src.getSalesFilePath() != null) {
			String sapf = src.getSapFilePath();
			String slf = src.getSalesFilePath();
			
			return svc.runTimeReportByStore(new String[]{sapf}, new String[]{slf}, new int[]{year}, new int[]{month}, store);
		} else {
			return "No Source Available";
		}
	}
}
