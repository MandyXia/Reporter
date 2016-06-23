package com.sephora.reporter.report.helper;

import org.apache.poi.ss.util.CellAddress;

public abstract class Formula {
	protected String raw;
	
	public Formula(String raw) {
		this.raw = raw;
	}
	
	public abstract String render(CellAddress current);
}
