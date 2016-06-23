package com.sephora.reporter.report.helper;

import com.sephora.reporter.entities.FinanceSource;

public class Allocs {
	public static double getAllocByName(String colname, FinanceSource fs) {
		if (colname.equalsIgnoreCase("VIP provision")) {
			return fs.getViptotal();
		} else if (colname.equalsIgnoreCase("Animation revenues")) {
			return fs.getAnimtotal();
		} else if (colname.equalsIgnoreCase("Stock depreciation")) {
			return fs.getStocktotal();
		} else if (colname.equalsIgnoreCase("Display")) {
			return fs.getDisplaytotal();
		} else if (colname.equalsIgnoreCase("Insurance & other")) {
			return fs.getInstotal();
		} else if (colname.equalsIgnoreCase("Taxes")) {
			return fs.getTaxtotal();
		} else {
			return 0;
		}
	}
}
