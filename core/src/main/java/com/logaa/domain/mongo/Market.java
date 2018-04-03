package com.logaa.domain.mongo;

public class Market {

	private Long id;
	private Double close;
	private Double high;
	private Double low;
	private Double open;
	private Double volumefrom;
	private Double volumeto;
	private Double change;
	
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
