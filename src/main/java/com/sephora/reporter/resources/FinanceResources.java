package com.sephora.reporter.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
				return o1.getSourceDate().compareTo(o2.getSourceDate());
			}
		});
		return result;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/sap")
	public Response uploadSapFile(@FormDataParam("sapfile") InputStream sapfile,
			@FormDataParam("sapfile") FormDataContentDisposition disposition, @FormDataParam("date") Date date) {
		if (date == null) {
			date = new Date();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String folder = sdf.format(date);

		String fileName = "sapfile-" + disposition.getFileName();
		String fileFullName = PathUtils.reformatPath(PathUtils.getRoot() + "/sources/" + folder + "/" + fileName);
		try (FileOutputStream fo = new FileOutputStream(new File(fileFullName))) {
			byte[] buffer = new byte[8192];
			int len = -1;
			while ((len = sapfile.read(buffer, 0, 8192)) != -1) {
				fo.write(buffer, 0, len);
			}
			fo.flush();
		} catch (IOException e) {
		}

		SimpleDateFormat sqlsdf = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date lookDate = java.sql.Date.valueOf(sqlsdf.format(date));
		FinanceSource source = repo.findBySourceDate(lookDate);
		if (source == null) {
			source = new FinanceSource();
			source.setSourceDate(lookDate);
		}
		source.setSapFileName(fileName);
		source.setSapFilePath(fileFullName);
		repo.saveAndFlush(source);

		return Response.status(201).entity("SAP File:" + fileFullName + " successfully uploaded.").build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/sales")
	public Response uploadSalesFile(@FormDataParam("salesfile") InputStream salesfile,
			@FormDataParam("salesfile") FormDataContentDisposition disposition, @FormDataParam("date") Date date) {
		if (date == null) {
			date = new Date();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String folder = sdf.format(date);

		String fileName = "salesfile-" + disposition.getFileName();
		String fileFullName = PathUtils.reformatPath(PathUtils.getRoot() + "/sources/" + folder + "/" + fileName);
		try (FileOutputStream fo = new FileOutputStream(new File(fileFullName))) {
			byte[] buffer = new byte[8192];
			int len = -1;
			while ((len = salesfile.read(buffer, 0, 8192)) != -1) {
				fo.write(buffer, 0, len);
			}
			fo.flush();
		} catch (IOException e) {
		}

		SimpleDateFormat sqlsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.sql.Date lookDate = java.sql.Date.valueOf(sqlsdf.format(date));
		FinanceSource source = repo.findBySourceDate(lookDate);
		if (source == null) {
			source = new FinanceSource();
			source.setSourceDate(lookDate);
		}
		source.setSalesFileName(fileName);
		source.setSalesFilePath(fileFullName);
		repo.saveAndFlush(source);

		return Response.status(201).entity("Sales File:" + fileFullName + " successfully uploaded.").build();
	}
}
