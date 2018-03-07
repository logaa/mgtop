package com.logaa.quartz.enumer;

public enum JobEnum {
	
	PLAN_TYPE_ONE_TIME("one_time"),
	PLAN_TYPE_EXPR("expr"),
	PLAN_TYPE_EXPR_AT_ONCE("expr_at_once"),
	
	LOG_STATUS_SUCCESS("success"),
	LOG_STATUS_FAILURE("failure"),
	
	EXECUTE_METHOD_ID("methodId"),
	EXECUTE_TASK_NAME("taskName"),
	EXECUTE_METHOD_NAME("methodName"),
	EXECUTE_CLASS_NAME("className"),
	EXECUTE_PARAMS("params"),
	EXECUTE_PARAM_TYPES("paramTypes"),
	
	JOB_STATUS_STOP("stop"),
	JOB_STATUS_RUN("run"),
	JOB_STATUS_FORBIDDEN("forbidden");
	
	String value;
	
	JobEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
