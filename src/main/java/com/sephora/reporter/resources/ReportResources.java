package com.sephora.reporter.resources;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.sephora.reporter.entities.FinanceReport;
import com.sephora.reporter.entities.FinanceSource;
import com.sephora.reporter.report.ReportService;
import com.sephora.reporter.repositories.FinanceReportRepository;
import com.sephora.reporter.repositories.FinanceSourceRepository;

@Path("/report")
public class ReportResources {
	
	@Autowired
	private ReportService svc;
	
	@Autowired
	private FinanceSourceRepository repo;
	
	@Autowired
	private FinanceReportRepository reportRepo;
	
	@GET
	@Path("/time/year/{year}/month/{month}/store/{store}")
	public String runMonthReport(@PathParam("year") int year, @PathParam("month") int month, @PathParam("store") int store) {
		if (year == 0 || month == 0 || store == 0) {
			return "year, month, store code are mandatory";
		}
		
		FinanceSource src = repo.findBySourceYearAndSourceMonth(year, month);
		if (src != null && src.getSapFilePath() != null && src.getSalesFilePath() != null) {
			String targetFile = svc.runTimeReportByStore(new FinanceSource[]{src}, store, true);
			FinanceReport report = new FinanceReport();
			report.setTargetFile(targetFile);
			report.setGenerateDate(new Date(new java.util.Date().getTime()));
			reportRepo.saveAndFlush(report);
			
			List<FinanceReport> reports = src.getTargetReports();
			if (reports == null) {
				reports = new ArrayList<>();
				src.setTargetReports(reports);
			}
			reports.add(report);
			repo.saveAndFlush(src);
			
			return targetFile;
		} else {
			return "No Source Available";
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/download/{id}")
	public Response downloadReport(@PathParam("id") int id) {
		FinanceReport fr = reportRepo.findOne(id);
		if (fr != null) {
			String fname = fr.getTargetFile();
			File of = new File(fname);
			ResponseBuilder response = Response.ok((Object) of);
			response.header("Content-Disposition", "attachment; filename=" + fname.substring(fname.lastIndexOf("/") + 1));
			return response.build();
		} else {
			return Response.status(404).build();
		}
	}
}
