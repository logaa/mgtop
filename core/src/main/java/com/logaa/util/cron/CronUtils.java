package com.logaa.util.cron;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;

public class CronUtils {

	public static boolean check(String cronExpr) {
		boolean isPass = false;
		try {
			new CronExpression(cronExpr).getNextValidTimeAfter(new Date());
			isPass = true;
		} catch (ParseException e) {
			isPass = false;
		}
		return isPass;
	}
}
