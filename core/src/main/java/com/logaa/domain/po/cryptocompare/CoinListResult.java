package com.logaa.domain.po.cryptocompare;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class CoinListResult {
	
	@SerializedName(value = "Response")
	String response;
	
	@SerializedName(value = "BaseImageUrl")
	String baseImageUrl;
	
	@SerializedName(value = "BaseLinkUrl")
	String baseLinkUrl;
	
	@SerializedName(value = "Message")
	String message;
	
	@SerializedName(value = "Type")
	Integer type;
	
	@SerializedName(value = "Data")
	Map<String, CoinListModel> data;
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<String, CoinListModel> getData() {
		return data;
	}

	public void setData(Map<String, CoinListModel> data) {
		this.data = data;
	}

	public String getBaseImageUrl() {
		return baseImageUrl;
	}

	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}

	public String getBaseLinkUrl() {
		return baseLinkUrl;
	}

	public void setBaseLinkUrl(String baseLinkUrl) {
		this.baseLinkUrl = baseLinkUrl;
	}
}
