package com.sephora.reporter.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.sephora.reporter.entities.FinanceSource;
import com.sephora.reporter.repositories.FinanceSourceRepository;
import com.sephora.reporter.utils.PathUtils;

@Path("/finance")
public class FinanceResources {
	@Autowired
	private FinanceSourceRepository repo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FinanceSource> getMonthRecords() {
		List<FinanceSource> result = repo.findAll();
		Collections.sort(result, new Comparator<FinanceSource>() {
			@Override
			public int compare(FinanceSource o1, FinanceSource o2) {
				if (o1.getSourceYear() != o2.getSourceYear()) {
					return o1.getSourceYear() - o2.getSourceYear();
				} else {
					return o1.getSourceMonth() - o2.getSourceMonth();
				}
			}
		});
		return result;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/sap")
	public Response uploadSapFile(@FormDataParam("sapfile") InputStream sapfile,
			@FormDataParam("sapfile") FormDataContentDisposition disposition, @FormDataParam("year") int year, @FormDataParam("month") int month) {
		if (year == 0 || month == 0) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}
		
		String fileName = "sap-" + year + "-" + month + ".xlsx";
		String folder = PathUtils.getRoot() + "/sources/" + year + "/";
		new File(folder).mkdirs();
		
		String fileFullName = PathUtils.reformatPath(folder + fileName);
		try (FileOutputStream fo = new FileOutputStream(fileFullName)) {
			byte[] buffer = new byte[8192];
			int len = -1;
			while ((len = sapfile.read(buffer, 0, 8192)) != -1) {
				fo.write(buffer, 0, len);
			}
			fo.flush();
		} catch (IOException e) {
		}

		FinanceSource source = repo.findBySourceYearAndSourceMonth(year, month);
		if (source == null) {
			source = new FinanceSource();
			source.setSourceYear(year);
			source.setSourceMonth(month);
		}
		source.setSapFilePath(fileFullName);
		repo.saveAndFlush(source);

		return Response.status(201).entity("SAP File:" + fileFullName + " successfully uploaded.").build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/sales")
	public Response uploadSalesFile(@FormDataParam("salesfile") InputStream salesfile,
			@FormDataParam("salesfile") FormDataContentDisposition disposition, @FormDataParam("year") int year, @FormDataParam("month") int month) {
		if (year == 0 || month == 0) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}
		
		String fileName = "sales-" + year + "-" + month + ".xlsx";
		String folder = PathUtils.getRoot() + "/sources/" + year + "/";
		new File(folder).mkdirs();
		
		String fileFullName = PathUtils.reformatPath(folder + fileName);
		try (FileOutputStream fo = new FileOutputStream(fileFullName)) {
			byte[] buffer = new byte[8192];
			int len = -1;
			while ((len = salesfile.read(buffer, 0, 8192)) != -1) {
				fo.write(buffer, 0, len);
			}
			fo.flush();
		} catch (IOException e) {
		}

		FinanceSource source = repo.findBySourceYearAndSourceMonth(year, month);
		if (source == null) {
			source = new FinanceSource();
			source.setSourceYear(year);
			source.setSourceMonth(month);
		}
		source.setSalesFilePath(fileFullName);
		repo.saveAndFlush(source);

		return Response.status(201).entity("Sales File:" + fileFullName + " successfully uploaded.").build();
	}
}
