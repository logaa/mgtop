package com.logaa.domain.po.cryptocompare;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CryptoResult {
	
	@SerializedName(value = "Response")
	private String response;
	
	@SerializedName(value = "Type")
	private Integer type;
	
	@SerializedName(value = "Data")
	private List<CryptoModel> data;
	
	@SerializedName(value = "TimeTo")
	private Long timeTo;
	
	@SerializedName(value = "TimeFrom")
	private Long timeFrom;
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<CryptoModel> getData() {
		return data;
	}
	public void setData(List<CryptoModel> data) {
		this.data = data;
	}
	public Long getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(Long timeTo) {
		this.timeTo = timeTo;
	}
	public Long getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(Long timeFrom) {
		this.timeFrom = timeFrom;
	}
}
