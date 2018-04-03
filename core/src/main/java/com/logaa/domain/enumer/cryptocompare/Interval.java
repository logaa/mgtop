package com.logaa.domain.enumer.cryptocompare;

public enum Interval {

	M("M", "histominute"), // minute
	H("H", "histohour"), // hour
	D("D", "histoday");  // day
	
	private String key;
	private String value;
	
	Interval(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static String getValue(String key){
		switch (key) {
		case "M":
			return M.value;
		case "H":
			return H.value;
		case "D":
			return D.value;
		default:
			return null;
		}
	}
}
