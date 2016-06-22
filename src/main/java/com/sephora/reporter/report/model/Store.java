package com.sephora.reporter.report.model;

public class Store {
	private String code;
	private String name;
	
	public Store(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public Store(String textValue) {
		this.code = textValue.substring(0, 4);
		this.name = textValue.substring(4).trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.code + " " + this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Store) {
			Store os = (Store) o;
			if (this.code != null && this.code.equals(os.code) && this.name != null && this.name.equals(os.name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int r = 0;
		if (this.code != null) {
			r += this.code.hashCode();
		}
		
		if (this.name != null) {
			r += this.name.hashCode();
		}
		
		return r;
	}
}
