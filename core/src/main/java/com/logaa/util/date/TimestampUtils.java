package com.logaa.util.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * TimeStamp工具类，提供TimeStamp与String、Date的转换
 */
public class TimestampUtils {

    /**
     * String转换为TimeStamp
     * @param value 待转换的String，格式必须为 yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错
     * @return java.sql.Timestamp
     */
    public static Timestamp string2Timestamp(String value){
    	if(StringUtils.isBlank(value)) return null;
    	return Timestamp.valueOf(value);
    }

    /**
     * 将Timestamp 转换为String类型，format为null则使用默认格式 yyyy-MM-dd HH:mm:ss
     * @param value 待转换的Timestamp
     * @param format String的格式
     * @return java.lang.String
     */
    public static String timestamp2String(Timestamp value, String format){
    	if(null == value) return "";
    	SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
    	return sdf.format(value);
    }
    
    /**
     * 将Timestamp 转换为String类型，format为null则使用默认格式 yyyy-MM-dd HH:mm:ss
     * @param value 待转换的Timestamp
     * @param format String的格式
     * @return java.lang.String
     */
    public static String timestamp2String(long value, String format){
    	SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
    	return sdf.format(new Timestamp(value));
    }

    /**
     * Date转换为Timestamp
     * @param date 待转换的Date
     */
    public static Timestamp date2Timestamp(Date date){
        if(date == null) return null;
        return new Timestamp(date.getTime());
    }

    /**
     * 当前 timestamp
     * @return timestamp
     */
    public static Long getTimestamp(){
    	return new Timestamp(new Date().getTime()).getTime();
    }
    
    /**
     * 指定时间获取timestamp
     * @param date 指定时间，默认当前时间
     * @param hour 指定小时
     * @param minute 指定分钟
     * @param second 指定秒
     * @return timestamp
     */
    public static Long getTimestamp(Date date, Integer hour, Integer minute, Integer second){
    	Calendar calender = Calendar.getInstance();
    	if(date != null) calender.setTime(date);
		if(hour != null) calender.set(Calendar.HOUR_OF_DAY, hour);
		if(minute != null) calender.set(Calendar.MINUTE, minute);
		if(second != null) calender.set(Calendar.SECOND, second);
    	return date2Timestamp(calender.getTime()).getTime();
    }
   
}
