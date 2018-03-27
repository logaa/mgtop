package com.logaa.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.logaa.http.ApiRequest;
import com.logaa.http.ApiRequest.Verb;

@Component
public class CryptocompareHelper {

	static final String COIN_LIST_URL = "https://www.cryptocompare.com/api/data/coinlist";
	
	public List<Coin> getCoinList(){
		List<Coin> coins = new ArrayList<>();
		CoinListResult clr = new Gson().fromJson(new ApiRequest(COIN_LIST_URL, Verb.GET).send().getResult(), CoinListResult.class);
		if(clr != null && clr.getType().intValue() == 100){
			clr.getData().forEach((k, v) -> {
				if(StringUtils.isNotBlank(v.getUrl())){
					v.setUrl(clr.getBaseLinkUrl() + v.getUrl());
				}
				if(StringUtils.isNotBlank(v.getImageUrl())){
					v.setImageUrl(clr.getBaseImageUrl() + v.getImageUrl());
				}
				coins.add(v);
			});
		}
		return coins;
	}
	
	class CoinListResult{
		
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
		Map<String, Coin> data;
		
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

		public Map<String, Coin> getData() {
			return data;
		}

		public void setData(Map<String, Coin> data) {
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
	
	public class Coin{
		
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
}
