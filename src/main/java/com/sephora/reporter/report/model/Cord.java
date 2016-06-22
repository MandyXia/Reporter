package com.sephora.reporter.report.model;

public class Cord {
	private int rowOffset;
	private String name;
	
	public Cord(int rowOffset, String name) {
		this.rowOffset = rowOffset;
		this.name = name;
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
}
