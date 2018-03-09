package com.logaa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;


@Component
public class MongoDao{

	@Autowired
	MongoTemplate mongoTemplate;
	
	public <T> T findOne(String key, String value, Class<T> clazz){
		return mongoTemplate.findOne(new Query()
				.addCriteria(Criteria.where(key).is(value)), clazz);
	}
	
	public <T> T findOne(String key, String value, Direction direction, String sort, Class<T> clazz){
		return mongoTemplate.findOne(new Query()
				.addCriteria(Criteria.where(key).is(value))
				.with(new Sort(new Sort.Order(direction, sort))), clazz);
	}
	
	public <T> T findOne(String key, String value, Direction direction, String sort, Class<T> clazz, String collectionName){
		return mongoTemplate.findOne(new Query()
				.addCriteria(Criteria.where(key).is(value))
				.with(new Sort(new Sort.Order(direction, sort))), clazz, collectionName);
	}
	
	public void save(Object obj){
		mongoTemplate.save(obj);
	}
	
	public void save(Object obj, String collectionName){
		mongoTemplate.save(obj, collectionName);
	}
}
