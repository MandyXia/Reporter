package com.sephora.reporter.report.model;

import java.util.Date;
import java.util.List;

public class SAPMonthRecord {
	private Store store;
	private Date month;
	List<SAPRecord> records;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public List<SAPRecord> getRecords() {
		return records;
	}

	public void setRecords(List<SAPRecord> records) {
		this.records = records;
	}
}
