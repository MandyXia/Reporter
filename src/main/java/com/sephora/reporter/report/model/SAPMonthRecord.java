package com.sephora.reporter.report.model;

import java.util.List;

public class SAPMonthRecord {
	private int storeCode;
	private int year;
	private int month;
	List<SAPRecord> records;

	public int getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<SAPRecord> getRecords() {
		return records;
	}

	public void setRecords(List<SAPRecord> records) {
		this.records = records;
	}
}
