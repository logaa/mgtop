package com.logaa.domain.po.love2io;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.logaa.domain.rdb.love2io.Posts;

public class PostsResult {

	private Page page;
	
	private List<Posts> result;
	
	public class Page{
		@SerializedName(value = "next_page")
		private Integer nextPage;
		@SerializedName(value = "cur_page")
		private Integer curPage;
		@SerializedName(value = "prev_page")
		private Integer prevPage;
		public Integer getNextPage() {
			return nextPage;
		}
		public void setNextPage(Integer nextPage) {
			this.nextPage = nextPage;
		}
		public Integer getCurPage() {
			return curPage;
		}
		public void setCurPage(Integer curPage) {
			this.curPage = curPage;
		}
		public Integer getPrevPage() {
			return prevPage;
		}
		public void setPrevPage(Integer prevPage) {
			this.prevPage = prevPage;
		}
	}
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<Posts> getResult() {
		return result;
	}
	public void setResult(List<Posts> result) {
		this.result = result;
	}
	
}
