package com.logaa.quartz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.logaa.domain.mongo.JobRunHi;
import com.logaa.domain.rdb.JobDef;
import com.logaa.helper.SpringHelper;
import com.logaa.quartz.JobPersistenceSupport;
import com.logaa.quartz.entity.IJobDefPo;
import com.logaa.quartz.enumer.JobEnum;
import com.logaa.repository.rdb.JobDefRepository;
import com.logaa.util.date.TimestampUtils;

@Service(value = "jobPersistenceSupport")
public class JobPersistenceSupportImpl implements JobPersistenceSupport {

	@Autowired
	JobDefRepository jobDefRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void updateLastRunTime(Long jobDefId) {
		JobDef jobDef = jobDefRepository.findOne(jobDefId);
		if(jobDef != null){
			jobDef.setLastRunTime(TimestampUtils.getTimestamp());
			jobDefRepository.save(jobDef);
		}
	}

	@Override
	public List<IJobDefPo> findActivedJobDefPos(String group) { 
		List<JobDef> jobDefPos = jobDefRepository.findByGroupAndStatus(group, JobEnum.JOB_STATUS_RUN.getValue());
		List<IJobDefPo> iJobDefPos = new ArrayList<IJobDefPo>();
		iJobDefPos.addAll(jobDefPos);
		return iJobDefPos;
	}

	@Override
	public IJobDefPo getJobDefPo(String jobDefId) {
		return jobDefRepository.findOne(Long.parseLong(jobDefId));
	}

	@Override
	public void saveRunHistory(String key, String group, String status, String log) {
		JobRunHi jobRunHi = new JobRunHi();
		jobRunHi.setId(TimestampUtils.getTimestamp());
		jobRunHi.setcTime(TimestampUtils.getTimestamp());
		jobRunHi.setGroup(group);
		jobRunHi.setKey(key);
		jobRunHi.setStatus(status);
		jobRunHi.setLog(log);
		mongoTemplate.save(jobRunHi);
	}

}
