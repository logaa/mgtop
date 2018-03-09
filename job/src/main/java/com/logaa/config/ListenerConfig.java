package com.logaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.logaa.content.ProjectStartListener;

@Configuration
public class ListenerConfig {

	@Bean
	public ProjectStartListener getProjectStartListener() {
		return new ProjectStartListener();
	}
	
}
