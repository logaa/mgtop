package com.logaa.domain.enumer.cryptocompare;

import java.util.Date;

import com.logaa.util.date.DateUtils;

public enum Period {
	D1,
	D3,
	W1,
	W2,
	M1,
	M3,
	M6,
	Y1,
	Y2,
	Y3;
	
	public static int getDay(String period){
		Date date = new Date();
		switch (period) {
		case "D1":
			return DateUtils.getDateDiff(date, DateUtils.addDays(date, -1));
		case "D3":
			return DateUtils.getDateDiff(date, DateUtils.addDays(date, -3));
		case "W1":
			return DateUtils.getDateDiff(date, DateUtils.addDays(date, -7));
		case "W2":
			return DateUtils.getDateDiff(date, DateUtils.addDays(date, -14));
		case "M1":
			return DateUtils.getDateDiff(date, DateUtils.addMonth(date, -1));
		case "M3":
			return DateUtils.getDateDiff(date, DateUtils.addMonth(date, -3));
		case "M6":
			return DateUtils.getDateDiff(date, DateUtils.addMonth(date, -6));
		case "Y1":
			return DateUtils.getDateDiff(date, DateUtils.addYear(date, -1));
		case "Y2":
			return DateUtils.getDateDiff(date, DateUtils.addYear(date, -2));
		case "Y3":
			return DateUtils.getDateDiff(date, DateUtils.addYear(date, -3));
		default:
			return 1;
		}
	}
	
	public static Date getDate(String period){
		Date date = new Date();
		switch (period) {
		case "D1":
			return DateUtils.addDays(date, -1);
		case "D3":
			return DateUtils.addDays(date, -3);
		case "W1":
			return DateUtils.addDays(date, -7);
		case "W2":
			return DateUtils.addDays(date, -14);
		case "M1":
			return DateUtils.addMonth(date, -1);
		case "M3":
			return DateUtils.addMonth(date, -3);
		case "M6":
			return DateUtils.addMonth(date, -6);
		case "Y1":
			return DateUtils.addYear(date, -1);
		case "Y2":
			return DateUtils.addYear(date, -2);
		case "Y3":
			return DateUtils.addYear(date, -3);
		default:
			return date;
		}
	}
}
