package com.logaa.domain.po.cryptocompare;

import java.util.Map;

public class CoinListPo {

	private String name;
	private String fullName;
	private String imageUrl;
	
	private Double open = 0d;
	private Double close = 0d;
	private Double high = 0d;
	private Double low = 0d;
	private Double volumefrom;
	private Double volumeto;
	private Double change;
	
	private Map<String, String> trend; // 趋势
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getVolumefrom() {
		return volumefrom;
	}
	public void setVolumefrom(Double volumefrom) {
		this.volumefrom = volumefrom;
	}
	public Double getVolumeto() {
		return volumeto;
	}
	public void setVolumeto(Double volumeto) {
		this.volumeto = volumeto;
	}
	public Double getChange() {
		return change;
	}
	public void setChange(Double change) {
		this.change = change;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Map<String, String> getTrend() {
		return trend;
	}
	public void setTrend(Map<String, String> trend) {
		this.trend = trend;
	}
	
}
