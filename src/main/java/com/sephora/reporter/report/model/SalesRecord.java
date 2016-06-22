package com.sephora.reporter.report.model;

import java.util.Date;

public class SalesRecord {
	private Store store;
	private Date month;
	private double vat;
	private double cogs;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getCogs() {
		return cogs;
	}

	public void setCogs(double cogs) {
		this.cogs = cogs;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}
}
