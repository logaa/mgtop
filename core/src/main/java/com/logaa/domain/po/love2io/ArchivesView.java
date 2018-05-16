package com.logaa.domain.po.love2io;

import java.util.List;

public class ArchivesView {

	
	
	List<Posts> month;	
	
	class Posts{
		private String created; // yyyy-MM-dd HH:mm:dd
		private String description;
		private String title;
		private String authorUsername;
	}
}
