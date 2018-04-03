package com.logaa.repository.rdb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.logaa.domain.rdb.job.JobDef;

@Repository
public interface JobDefRepository extends JpaRepository<JobDef, Long>{

	List<JobDef> findByGroupAndStatus(String group, String status);

}
