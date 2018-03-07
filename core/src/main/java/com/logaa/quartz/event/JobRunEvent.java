package com.logaa.quartz.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class JobRunEvent extends ApplicationEvent{
	
	public JobRunEvent(Object source){
		super(source);
	}
}
