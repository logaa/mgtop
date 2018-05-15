package com.logaa.repository.rdb.gitee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logaa.domain.rdb.gitee.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

}
