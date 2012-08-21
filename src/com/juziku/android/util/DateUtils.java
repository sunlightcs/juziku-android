package com.juziku.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class DateUtils {
	private static final String TAG = DateUtils.class.getSimpleName();
	
	public static String DATE_FORMAT_DAY = "yyyy-MM-dd";
	public static String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT_CHINADAY = "yyyy年MM月dd日";
	public static String DATE_FORMAT_CHINATIME = "yyyy年MM月dd日 HH时mm分ss秒";
	
	
	/**
	 * 格式化日期，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String getStringDate(Date date) {
		return getStringDate(date, null);
	}

	
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param formate
	 *            默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getStringDate(Date date, String format) {
		if (StringUtils.isEmpty(format))
			format = DATE_FORMAT_TIME;

		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	
	/**
	 * 格式化日期，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static Date getDateString(String value) {
		return getDateString(value, null);
	}

	/**
	 * 格式化日期，默认格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param value    被格式化日期
	 * @param format   格式化格式
	 */
	public static Date getDateString(String value, String format) {
		if (StringUtils.isEmpty(format)) {
			format = DATE_FORMAT_TIME;
		}
		DateFormat dateFormate = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormate.parse(value);
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		return date;
	}
	
	
	
	/**
	 * 根据长整形格式的时间，获取Date类型的时间
	 * 
	 * @param value  长整形时间（单位：秒）
	 */
	public static Date getDateLong(long value){
		return new Date(value * 1000);
	}
	
	
	/**
	 * 获取当前的时间（单位：秒）
	 */
	public static long getCurrentDate(){
		return System.currentTimeMillis()/1000;
	}
	
	
	/**
	 * 获取长整形格式的时间
	 * 
	 */
	public static long getLongDate(Date date){
		return date.getTime()/1000;
	}
	
	
	/**
	 *  获取长整形格式的时间，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static long getLongDateString(String value) {
		return getLongDateString(value, null);
	}

	/**
	 * 获取长整形格式的时间，默认格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param value    被格式化日期
	 * @param format   格式化格式
	 */
	public static long getLongDateString(String value, String format) {
		if (StringUtils.isEmpty(format)) {
			format = DATE_FORMAT_TIME;
		}
		DateFormat dateFormate = new SimpleDateFormat(format);
		long longDate = 0;
		try {
			Date date = dateFormate.parse(value);
			longDate = getLongDate(date);
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		return longDate;
	}
	
	
	/**
	 * 根据指定日期，获得指定天数差别的日期
	 * 
	 * @param strDate
	 *            参照日期
	 * @param days
	 *            差别的天数，正数表示获取之后的日期，负数表示获取之前的日期
	 * @return 获得的日期
	 */
	public static Date getDistDate(Date date, int days) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	
	
}
