package com.sephora.reporter.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FinanceSource")
public class FinanceSource extends BaseEntity {
	@Column(name = "SourceDate")
	private Date sourceDate;

	@Column(name = "SAPFileName")
	private String sapFileName;

	@Column(name = "SAPFilePath")
	private String sapFilePath;

	@Column(name = "SalesFileName")
	private String salesFileName;

	@Column(name = "SalesFilePath")
	private String salesFilePath;

	@Column(name = "SalesTotal")
	private double salesTotal;

	@OneToMany(fetch = FetchType.EAGER)
	private List<FinanceReport> targetReports;

	public Date getSourceDate() {
		return sourceDate;
	}

	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}

	public String getSapFileName() {
		return sapFileName;
	}

	public void setSapFileName(String sapFileName) {
		this.sapFileName = sapFileName;
	}

	public String getSapFilePath() {
		return sapFilePath;
	}

	public void setSapFilePath(String sapFilePath) {
		this.sapFilePath = sapFilePath;
	}

	public String getSalesFileName() {
		return salesFileName;
	}

	public void setSalesFileName(String salesFileName) {
		this.salesFileName = salesFileName;
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
