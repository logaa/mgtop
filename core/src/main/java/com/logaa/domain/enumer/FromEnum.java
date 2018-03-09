package com.logaa.domain.enumer;

public enum FromEnum {

	JINSE("JINSE", "金色财经"),
	BBTC("BBTC", "巴比特");
	
	private String key;
	
	private String value;
	
	FromEnum(String key, String value){
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
	
}
