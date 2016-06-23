package com.sephora.reporter.report.helper;

import org.apache.poi.ss.util.CellAddress;

public class Formulas {
	public static final Formula SUM_PREVIOUS2ROW = new Formula("SUM(%s:%s)") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row - 2), col + (row - 1));
		}
	};

	public static final Formula NEXT_MINUS_PREVIOUS2 = new Formula("%s-%s") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row + 1), col + (row - 2));
		}
	};

	public static final Formula P2_ADD_P3 = new Formula("%s+%s") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row - 3), col + (row - 2));
		}
	};

	public static final Formula SUM_PREVIOUS3ROW = new Formula("SUM(%s:%s)") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row - 3), col + (row - 1));
		}
	};

	public static final Formula SUM_PREVIOUS4ROW = new Formula("SUM(%s:%s)") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row - 4), col + (row - 1));
		}
	};

	public static final Formula SUM_PREVIOUS10ROW = new Formula("SUM(%s:%s)") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row - 11), col + (row - 1));
		}
	};

	public static final Formula SUM_PREVIOUSMISCROW = new Formula("SUM(%s:%s)+SUM(%s:%s)") {
		@Override
		public String render(CellAddress current) {
			String add = current.toString();
			int row = current.getRow() + 1;
			String col = add.substring(0, add.indexOf(String.valueOf(row)));
			return String.format(raw, col + (row - 18), col + (row - 14), col + (row - 2), col + (row - 1));
		}
	};

	public static final Formula[] ALL = new Formula[] { SUM_PREVIOUS2ROW, NEXT_MINUS_PREVIOUS2, P2_ADD_P3,
			SUM_PREVIOUS3ROW, SUM_PREVIOUS4ROW, SUM_PREVIOUS10ROW, SUM_PREVIOUSMISCROW };
}
