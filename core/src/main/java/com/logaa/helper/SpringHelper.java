package com.logaa.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import com.logaa.event.BaseEvent;


public class SpringHelper implements ApplicationContextAware{

	protected static final Logger logger = LoggerFactory.getLogger(SpringHelper.class);

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static void setContext(ApplicationContext applicationContext){
		if(context == null) context = applicationContext;
	}
	
	/**
	 * 根据beanId获取配置在系统中的对象实例。
	 * @param beanId
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	public static Object getBean(String beanId) {
		try{
			return context.getBean(beanId);
		} catch(Exception ex){
			logger.error("getBean:{},{}", beanId, ex.getMessage());
		}
		return null;
	}

	/**
	 * 获取Spring容器的Bean
	 * @param beanClass
	 * @return T
	 * @exception
	 * @since 1.0.0
	 */
	public static <T> T getBean(Class<T> beanClass) {
		try{
			return  context.getBean(beanClass);
		}catch(Exception ex){
			logger.error("getBean:{},{}", beanClass, ex.getMessage());
		}
		return null;
	}
	
	public static void publishEvent(ApplicationEvent event){
		if(event!=null){
			context.publishEvent(event);
			//如果该事件是BaseEvent的子类，则再发布一次父类
			if(event instanceof BaseEvent){
				BaseEvent baseEvent = (BaseEvent)event;
				context.publishEvent(baseEvent);
			}
		}
	}	
	
	public static void cleanup(){
		context = null;
	}
}
