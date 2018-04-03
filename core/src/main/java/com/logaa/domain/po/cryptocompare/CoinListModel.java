package com.logaa.domain.po.cryptocompare;

import com.google.gson.annotations.SerializedName;

public class CoinListModel {

	@SerializedName(value = "Id")
	String coinId;

	@SerializedName(value = "Url")
	String url;
	
	@SerializedName(value = "ImageUrl")
	String imageUrl;
	
	@SerializedName(value = "Name")
	String name;
	
	@SerializedName(value = "CoinName")
	String coinName;
	
	@SerializedName(value = "FullName")
	String fullName;
	
	@SerializedName(value = "Algorithm")
	String algorithm;
	
	@SerializedName(value = "ProofType")
	String proofType;
	
	@SerializedName(value = "SortOrder")
	String sortOrder;
	
	public String getCoinId() {
		return coinId;
	}
	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getProofType() {
		return proofType;
	}
	public void setProofType(String proofType) {
		this.proofType = proofType;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
