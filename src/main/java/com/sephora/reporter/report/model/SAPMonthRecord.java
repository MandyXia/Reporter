package com.sephora.reporter.report.model;

import java.util.List;

public class SAPMonthRecord {
	private int storeCode;
	private int year;
	private int month;
	private double netSalesMp;
	private double netSalesYp;
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

	public double getNetSalesMp() {
		return netSalesMp;
	}

	public void setNetSalesMp(double netSalesMp) {
		this.netSalesMp = netSalesMp;
	}

	public double getNetSalesYp() {
		return netSalesYp;
	}

	public void setNetSalesYp(double netSalesYp) {
		this.netSalesYp = netSalesYp;
	}

	public List<SAPRecord> getRecords() {
		return records;
	}

	public void setRecords(List<SAPRecord> records) {
		this.records = records;
	}
}
