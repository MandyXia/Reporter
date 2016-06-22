package com.sephora.reporter.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FinanceSource")
public class FinanceSource extends BaseEntity {
	@Column(name = "SourceDate")
	private Date sourceDate;
	
	@Column(name = "SAPFile")
	private String sapFile;
	
	@Column(name = "SalesFile")
	private String salesFile;
	
	@Column(name = "SalesTotal")
	private double salesTotal;
	
	@OneToMany
	private List<FinanceReport> targetReports;

	public Date getSourceDate() {
		return sourceDate;
	}

	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}

	public String getSapFile() {
		return sapFile;
	}

	public void setSapFile(String sapFile) {
		this.sapFile = sapFile;
	}

	public String getSalesFile() {
		return salesFile;
	}

	public void setSalesFile(String salesFile) {
		this.salesFile = salesFile;
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
