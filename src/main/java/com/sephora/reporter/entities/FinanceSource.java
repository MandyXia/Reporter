package com.sephora.reporter.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FinanceSource")
public class FinanceSource extends BaseEntity {
	@Column(name = "SourceYear")
	private int sourceYear;
	
	@Column(name = "SourceMonth")
	private int sourceMonth;

	@Column(name = "SAPFilePath")
	private String sapFilePath;

	@Column(name = "SalesFilePath")
	private String salesFilePath;

	@Column(name = "SalesTotal")
	private double salesTotal;

	@OneToMany(fetch = FetchType.EAGER)
	private List<FinanceReport> targetReports;

	public int getSourceYear() {
		return sourceYear;
	}

	public void setSourceYear(int sourceYear) {
		this.sourceYear = sourceYear;
	}

	public int getSourceMonth() {
		return sourceMonth;
	}

	public void setSourceMonth(int sourceMonth) {
		this.sourceMonth = sourceMonth;
	}

	public String getSapFilePath() {
		return sapFilePath;
	}

	public void setSapFilePath(String sapFilePath) {
		this.sapFilePath = sapFilePath;
	}

	public String getSalesFilePath() {
		return salesFilePath;
	}

	public void setSalesFilePath(String salesFilePath) {
		this.salesFilePath = salesFilePath;
	}

	public double getSalesTotal() {
		return salesTotal;
	}

	public void setSalesTotal(double salesTotal) {
		this.salesTotal = salesTotal;
	}

	public List<FinanceReport> getTargetReports() {
		return targetReports;
	}

	public void setTargetReports(List<FinanceReport> targetReports) {
		this.targetReports = targetReports;
	}
}
