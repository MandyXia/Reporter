package com.sephora.reporter.report.helper;

public class Cords {
	public static Cord GROSS_SALES = new Cord(0, "Gross sales", Cord.FROM_SALES, "vat");
	public static Cord NET_SALES = new Cord(1, "Net sales", Cord.FROM_SAP, "TURNOVER WITHOUT TAX");
	public static Cord VIP_PROVISION = new Cord(2, "VIP provision", Cord.FROM_ALLOCATE, "VIP provision");
	public static Cord NET_SALES_AFTER_VIP_PROVISION = new Cord(3, "Net sales (after VIP provision)", Cord.FROM_FORMULA,
			"0");
	public static Cord DIRECT_COGS = new Cord(4, "Direct COGS", Cord.FROM_SALES, "cogs");
	public static Cord DIRECT_MARGIN = new Cord(5, "Direct Margin", Cord.FROM_FORMULA, "0");
	public static Cord OTHER_COGS = new Cord(6, "Other COGS", Cord.FROM_FORMULA, "1");
	public static Cord TOTAL_COGS = new Cord(7, "Total COGS", Cord.FROM_SAP, "COST OF GOODS SALES");
	public static Cord FRONT_MARGIN = new Cord(8, "Front Margin", Cord.FROM_FORMULA, "2");
	public static Cord SUPPLIES_DISCOUNTS = new Cord(9, "Suppliers discounts", Cord.FROM_OTHER, "");
	public static Cord ANIMATION_REVENUES = new Cord(10, "Animation revenues", Cord.FROM_ALLOCATE, "Animation revenues");
	public static Cord GROSS_MARGIN = new Cord(11, "Gross margin", Cord.FROM_FORMULA, "3");
	public static Cord STOCK_DEPRECIATION = new Cord(12, "Stock depreciation", Cord.FROM_ALLOCATE, "Stock depreciation");
	public static Cord SHRINKAGE = new Cord(13, "Shrinkage", Cord.FROM_SAP, "SHRINKAGE");
	public static Cord TEST = new Cord(14, "Test", Cord.FROM_SAP, "TEST");
	public static Cord GROSS_PROFIT = new Cord(15, "Gross profit", Cord.FROM_FORMULA, "4");
	public static Cord LANDLORD_CHARGES = new Cord(16, "Landlord charges", Cord.FROM_SAP, "LANDLORD CHARGES");
	public static Cord CONSTRUCTION_DEPRECIATION = new Cord(17, "Construction depreciation", Cord.FROM_SAP,
			"CONSTRUCTION DEPRECIATION");
	public static Cord SHOP_PAYROLL = new Cord(18, "Shop payroll", Cord.FROM_SAP, "PAYROLL");
	public static Cord DISPLAY = new Cord(19, "Display", Cord.FROM_ALLOCATE, "Display");
	public static Cord SECURITY = new Cord(20, "Security", Cord.FROM_SAP, "SECURITY");
	public static Cord CLEANING = new Cord(21, "Cleaning", Cord.FROM_SAP, "CLEANING");
	public static Cord EUS = new Cord(22, "Equipment, uniform, supplies", Cord.FROM_SAP,
			"SUNDRY MATERIAL PURCHASES SAP/SAP PACKING AND SUNDRY MATERIA/OFFICE EXPENSES/SAP STAFF UNIFORM AND COMMUNIC/OTHER OVERHEADS");
	public static Cord TRAVELS = new Cord(23, "Travels", Cord.FROM_SAP, "TRAVEL AND ENTERTAINMENT/TILL SHORTFALL");
	public static Cord CASH_COLLECTION = new Cord(24, "Cash collection", Cord.FROM_SAP, "CASH COLLECTION");
	public static Cord CONSTRUCTION_MAINTENANCE = new Cord(25, "Construction maintenance", Cord.FROM_SAP,
			"MAINTENANCE CONTRACTS/MAINTENANCE SUPPLIES/OTHER MAINTENANCE SERVICES AND");
	public static Cord IT_MAINTENANCE = new Cord(26, "IT maintenance", Cord.FROM_SAP, "IT STORE");
	public static Cord ENERGY_AND_WATER = new Cord(27, "Energy and water", Cord.FROM_SAP,
			"ENERGY AND WATER/TELEPHONE AND POSTAGE");
	public static Cord INSURANCE_OTHER = new Cord(28, "Insurance & other", Cord.FROM_ALLOCATE, "Insurance & other");
	public static Cord CREDIT_CARD_FEES = new Cord(29, "Credit card fees", Cord.FROM_SAP, "BANK COMMISSIONS");
	public static Cord TAXES = new Cord(30, "Taxes", Cord.FROM_ALLOCATE, "Taxes");
	public static Cord OTHER_SHOP_EXPENSES = new Cord(31, "Other shop expenses", Cord.FROM_FORMULA, "5");
	public static Cord SHOP_PROFIT = new Cord(33, "Shop Profit", Cord.FROM_FORMULA, "6");

	public static Cord[] ALL = new Cord[] { GROSS_SALES, NET_SALES, VIP_PROVISION, NET_SALES_AFTER_VIP_PROVISION,
			DIRECT_COGS, DIRECT_MARGIN, OTHER_COGS, TOTAL_COGS, FRONT_MARGIN, SUPPLIES_DISCOUNTS, ANIMATION_REVENUES,
			GROSS_MARGIN, STOCK_DEPRECIATION, SHRINKAGE, TEST, GROSS_PROFIT, LANDLORD_CHARGES,
			CONSTRUCTION_DEPRECIATION, SHOP_PAYROLL, DISPLAY, SECURITY, CLEANING, EUS, TRAVELS, CASH_COLLECTION,
			CONSTRUCTION_MAINTENANCE, IT_MAINTENANCE, ENERGY_AND_WATER, INSURANCE_OTHER, CREDIT_CARD_FEES, TAXES,
			OTHER_SHOP_EXPENSES, SHOP_PROFIT };
}
