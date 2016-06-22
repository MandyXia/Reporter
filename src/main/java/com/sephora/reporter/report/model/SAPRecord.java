package com.sephora.reporter.report.model;

public class SAPRecord {
	private String costElem;
	private double mtd;
	private double ytd;

	public String getCostElem() {
		return costElem;
	}

	public void setCostElem(String costElem) {
		this.costElem = costElem;
	}

	public double getMtd() {
		return mtd;
	}

	public void setMtd(double mtd) {
		this.mtd = mtd;
	}

	public double getYtd() {
		return ytd;
	}

	public void setYtd(double ytd) {
		this.ytd = ytd;
	}
}
