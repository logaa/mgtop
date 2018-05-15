package com.logaa.domain.po;

import org.springframework.data.domain.Page;

public class BasePageView {
	
	long totalElement = 0;		// 总数据大小
	int page = 0;				// 当前页面
	int numberOfElements = 0;	// 返回大小
	int size = 10;				// 页面大小
	int totalPages = 0;  		// 总页数
	
	public void setPage(Page page){
		this.totalElement = page.getTotalPages();
		this.page = page.getNumber();
		this.numberOfElements = page.getNumberOfElements();
		this.size = page.getSize();
		this.totalPages = page.getTotalPages();
	}

	public long getTotalElement() {
		return totalElement;
	}
	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
