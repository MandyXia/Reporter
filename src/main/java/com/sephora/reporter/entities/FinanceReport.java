package com.sephora.reporter.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FinanceReport")
public class FinanceReport extends BaseEntity {
	@Column(name = "TargetFile")
	private String targetFile;
	
	@Column(name = "GenerateDate")
	private Date generateDate;
	
	@ManyToOne
	@JoinColumn(name = "sourceId")
	private FinanceSource financeSource;

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public Date getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}

	public FinanceSource getFinanceSource() {
		return financeSource;
	}

	public void setFinanceSource(FinanceSource financeSource) {
		this.financeSource = financeSource;
	}
}
