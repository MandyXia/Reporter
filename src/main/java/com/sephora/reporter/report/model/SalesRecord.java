package com.sephora.reporter.report.model;

public class SalesRecord {
	private int storeCode;
	private int year;
	private int month;
	private double vatmtd;
	private double vatytd;
	private double cogsmtd;
	private double cogsytd;

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

	public double getVatmtd() {
		return vatmtd;
	}

	public void setVatmtd(double vatmtd) {
		this.vatmtd = vatmtd;
	}

	public double getVatytd() {
		return vatytd;
	}

	public void setVatytd(double vatytd) {
		this.vatytd = vatytd;
	}

	public double getCogsmtd() {
		return cogsmtd;
	}

	public void setCogsmtd(double cogsmtd) {
		this.cogsmtd = cogsmtd;
	}

	public double getCogsytd() {
		return cogsytd;
	}

	public void setCogsytd(double cogsytd) {
		this.cogsytd = cogsytd;
	}
}
