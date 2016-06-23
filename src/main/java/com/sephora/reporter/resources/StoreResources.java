package com.sephora.reporter.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.sephora.reporter.entities.StoreRecord;
import com.sephora.reporter.repositories.StoreRecordRepository;

@Path("/stores")
public class StoreResources {
	
	@Autowired
	private StoreRecordRepository repo;
	
	@GET
	@Path("/year/{year}/month/{month}")
	public List<StoreRecord> getStoresByYearAndMonth(@PathParam("year") int year, @PathParam("month") int month) {
		return repo.findByYearAndMonth(year, month);
	}
}
