package com.sephora.reporter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.sephora.reporter.report.ReportService;

@Path("/report")
public class ReportResources {
	@GET
	@Path("/template")
	public String getTemplate() {
		return new ReportService().generate();
	}
}
