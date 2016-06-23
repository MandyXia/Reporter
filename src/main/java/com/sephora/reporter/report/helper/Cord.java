package com.sephora.reporter.report.helper;

public class Cord {
	public static final int FROM_SAP = 1;
	public static final int FROM_SALES = 2;
	public static final int FROM_FORMULA = 3;
	public static final int FROM_ALLOCATE = 4;
	public static final int FROM_OTHER = 5;
	
	private int rowOffset;
	private String name;
	private int from;
	/**
	 * If from sales, then would be "vat, cogs"
	 * If from sap, then would be the detail name of the SAPRecord (costElem)
	 * If from formula, then return the formula index
	 */
	private String ref;
	
	public Cord(int rowOffset, String name, int from, String ref) {
		this.rowOffset = rowOffset;
		this.name = name;
		this.from = from;
		this.ref = ref;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
}
