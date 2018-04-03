package com.logaa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;
import com.mongodb.WriteResult;


@Component
public class MongoDao{

	@Autowired
	MongoTemplate mongoTemplate;
	
	public <T> T findOne(String key, Object value, Class<T> clazz){
		return mongoTemplate.findOne(new Query()
				.addCriteria(Criteria.where(key).is(value)), clazz);
	}
	
	public <T> T findOne(String key, Object value, Class<T> clazz, String collectionName){
		return mongoTemplate.findOne(new Query()
				.addCriteria(Criteria.where(key).is(value)), clazz, collectionName);
	}
	
	public <T> T findOne(String key, Object value, Direction direction, String sort, Class<T> clazz){
		return mongoTemplate.findOne(new Query()
				.addCriteria(Criteria.where(key).is(value))
				.with(new Sort(new Sort.Order(direction, sort))), clazz);
	}
	
	public <T> T findOneOrderBy(Direction direction, String sort, Class<T> clazz){
		return mongoTemplate.findOne(new Query().limit(1)
				.with(new Sort(new Sort.Order(direction, sort))), clazz);
	}
	
	public <T> T findOneOrderBy(Direction direction, String sort, Class<T> clazz, String collectionName){
		return mongoTemplate.findOne(new Query().limit(1)
				.with(new Sort(new Sort.Order(direction, sort))), clazz, collectionName);
	}
	
	public <T> T findOne(String key, Object value, Direction direction, String sort, Class<T> clazz, String collectionName){
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
	
	public <T> List<T> find(int page, int size, Direction direction, String sort, Class<T> clazz){
		return mongoTemplate.find(new Query()
				.with(new Sort(new Sort.Order(direction, sort)))
				.skip(Integer.valueOf(page * size))
				.limit(size), clazz);
	}
	
	public <T> List<T> find(int page, int size, Direction direction, String sort, Class<T> clazz , String collectionName){
		return mongoTemplate.find(new Query()
				.with(new Sort(new Sort.Order(direction, sort)))
				.skip(Integer.valueOf(page * size))
				.limit(size), clazz, collectionName);
	}
	
	public <T> boolean exists(String key, Object value, Class<T> clazz){
		return mongoTemplate.exists(new Query()
				.addCriteria(Criteria.where(key).is(value)), clazz);
	}
	
	public boolean exists(String key, Object value, String collectionName){
		return mongoTemplate.exists(new Query()
				.addCriteria(Criteria.where(key).is(value)), collectionName);
	}
	
	public WriteResult remove(Object object){
		return mongoTemplate.remove(object);
	}
	
	public <T> WriteResult remove(String key, Object value, Class<T> clazz){
		return mongoTemplate.remove(new Query()
				.addCriteria(Criteria.where(key).is(value)), clazz);
	}
	
	public WriteResult remove(String key, Object value, String collectionName){
		return mongoTemplate.remove(new Query()
				.addCriteria(Criteria.where(key).is(value)), collectionName);
	}
	
	public <T> boolean collectionExists(Class<T> entityClass){
		return mongoTemplate.collectionExists(entityClass);
	}
	
	public boolean collectionExists(String collectionName){
		return mongoTemplate.collectionExists(collectionName);
	}
	
}
