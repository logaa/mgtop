package com.logaa.quartz.entity;

public interface IJobDefPo {

	public Long getId();

	public String getName();

	public String getBean();

	public String getMethod();

	public boolean isClassName();

	public String getGroup();

	public String getType();

	public String getExpr();
}
