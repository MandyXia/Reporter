package com.sephora.reporter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

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
	@Path("/month/{month}")
	public String runMonthReport(@PathParam("month") int month, @QueryParam("store") int store) {
		int year = 2016;
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
