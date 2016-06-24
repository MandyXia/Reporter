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
	
	@Column(name = "SupplierPath")
	private String supplierPath;
	
	@Column(name = "DistrictPath")
	private String districtPath;

	@Column(name = "VIPTOTAL")
	private double viptotal;
	
	@Column(name = "ANIMTOTAL")
	private double animtotal;
	
	@Column(name = "STOCKTOTAL")
	private double stocktotal;
	
	@Column(name = "DISPLAYTOTAL")
	private double displaytotal;
	
	@Column(name = "INSTOTAL")
	private double instotal;
	
	@Column(name = "TAXTOTAL")
	private double taxtotal;

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

	public String getSupplierPath() {
		return supplierPath;
	}

	public void setSupplierPath(String supplierPath) {
		this.supplierPath = supplierPath;
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

	public String getDistrictPath() {
		return districtPath;
	}

	public void setDistrictPath(String districtPath) {
		this.districtPath = districtPath;
	}

	public double getViptotal() {
		return viptotal;
	}

	public void setViptotal(double viptotal) {
		this.viptotal = viptotal;
	}

	public double getAnimtotal() {
		return animtotal;
	}

	public void setAnimtotal(double animtotal) {
		this.animtotal = animtotal;
	}

	public double getStocktotal() {
		return stocktotal;
	}

	public void setStocktotal(double stocktotal) {
		this.stocktotal = stocktotal;
	}

	public double getDisplaytotal() {
		return displaytotal;
	}

	public void setDisplaytotal(double displaytotal) {
		this.displaytotal = displaytotal;
	}

	public double getInstotal() {
		return instotal;
	}

	public void setInstotal(double instotal) {
		this.instotal = instotal;
	}

	public double getTaxtotal() {
		return taxtotal;
	}

	public void setTaxtotal(double taxtotal) {
		this.taxtotal = taxtotal;
	}

	public List<FinanceReport> getTargetReports() {
		return targetReports;
	}

	public void setTargetReports(List<FinanceReport> targetReports) {
		this.targetReports = targetReports;
	}
}
