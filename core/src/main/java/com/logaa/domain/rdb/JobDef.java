package com.logaa.domain.rdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.logaa.quartz.entity.IJobDefPo;

@Entity
@Table(name="t_job_def")
public class JobDef implements IJobDefPo{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	private String name;
	
	private String bean;
	
	@Column(name = "group_")
	private String group;
	
	private String type; // one_time=执行一次； expr=按cron表达式； expr_at_once=表达式并立即执行。
	
	private String expr;
	
	private String comment;
	
	private String status;// 状态。stop=停止；run=执行中；forbidden=禁用
	
	private Long lastRunTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Long lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	@Override
	public String getMethod() {
		return "executeJob";
	}

	@Override
	public boolean isClassName() {
		return false;
	}
	
}
