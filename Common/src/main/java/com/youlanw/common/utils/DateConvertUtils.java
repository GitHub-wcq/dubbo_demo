package com.youlanw.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class DateConvertUtils {
	private static int weeks = 0;
	
	private static Pattern datePattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
	/**
	 * 判断是否是一个正常的日期字符串
	 * <br>分隔符“-”
	 * @param dateStr
	 * @return
	 */
	public static boolean isDateString(String dateStr){
		if(StringUtils.isNotBlank(dateStr)){
			return datePattern.matcher(dateStr).matches();
		}
		return false;
	}
	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws java.text.ParseException
	 * @throws ParseException
	 */
	public static Long daysBetweenr(Date smdate, Date bdate) {
		if(smdate==null)
		{
			return (long)0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Long.parseLong(String.valueOf(between_days));
	}
	/**
	 * 根据当天取得任何前几天时间
	 * 
	 * @return
	 */
	public static Date getAnyDayDate(int arg) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -arg);
		return calendar.getTime();
	}
	
	/**
	 * 日期型格式化成字符型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat formatter = null;
		if (date == null) {
			return null;
		} else if (StringUtils.isEmpty(format) || format.length() < 5) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			formatter = new SimpleDateFormat(format);
		}
		return formatter.format(date);
	}

	/**
	 * 字符型格式化成日期型
	 * 
	 * @param datetimestr
	 * @param format
	 * @return
	 */
	public static Date parse(String datetimestr, String format) {
		return parse(datetimestr, format, java.util.Date.class);
	}

	@SuppressWarnings("unchecked")
	public static <T extends java.util.Date> T parse(String dateString,
			String dateFormat, Class<T> targetResultType) {
		if (StringUtils.isEmpty(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			java.util.Date t = targetResultType.getConstructor(long.class)
					.newInstance(time);
			return (T) t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:" + dateFormat
					+ " parse datestring:" + dateString;
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:"
					+ targetResultType.getName(), e);
		}
	}

	/**
	 * 格式化成一天的第一分钟
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date firstDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 格式化成某天的第一分钟
	 * @param sdate
	 * @return
	 */
	public static Date firstDate(String sdate) {
		Date date = parse(sdate, "yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化成一天的最后1分钟
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date endDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return parse(formatter.format(date) + " 23:59:59",
				"yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 格式化成某天的最后一分钟
	 * @param sdate
	 * @return
	 */
	public static Date endDate(String sdate) {
		Date date = parse(sdate, "yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return parse(formatter.format(date) + " 23:59:59",
				"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 取当前时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}
	/**
	 * 获取明天的日期
	 * @return
	 */
	public static Date getTomorrow(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}
	
	/**
	 * 获取后天的日期
	 * @return
	 */
	public static Date getTheDayAfterTomorrow(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 2);
		return cal.getTime();
	}
	/**
	 * 计算时间差[日期]
	 * 
	 * @param now
	 * @param returnDate
	 * @return
	 */
	public static int daysBetween(Date now, Date returnDate) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(now);
		cReturnDate.setTime(returnDate);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToDays(intervalMs);
	}

	/**
	 * 计算时间差[时间]
	 * 
	 * @param now
	 * @param returnDate
	 * @return
	 */
	public static int hoursBetween(Date now, Date returnDate) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(now);
		cReturnDate.setTime(returnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToHours(intervalMs);
	}
	/**
	 * 取当月第一天
	 * @return
	 */
	public static Date getFirstDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	/**
	 * 取当月最后一天
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
        return ca.getTime();
	}
	/**
	 * 今年第一天
	 * @return
	 */
	public static Date getFirstDayOfYear(){
		Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);
	}
	/** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }
    /**
     * 今年最后一天
     * @return
     */
	public static Date getLastDayOfYear(){
		Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
	}
	/** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }
	/**
	 * 计算小时数
	 * 
	 * @param intervalMs
	 * @return
	 */
	private static int millisecondsToHours(long intervalMs) {
		return (int) (intervalMs / (1000 * 3600));
	}

	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}

	private static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}
	/**
	 * 昨日日期
	 * @return
	 */
	public String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return yesterday;
	}
	/**
	 * 上月第一天
	 * @return
	 */
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}
	/**
	 * 上月最后一天
	 * @return
	 */
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(2, -1);
		lastDate.set(5, 1);
		lastDate.roll(5, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}
	/**
	 * 本周周一日期
	 * @return
	 */
	public static Date getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus);
		Date monday = currentDate.getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String preMonday = sdf.format(monday);
//		return preMonday;
		return monday;
	}
	/**
	 * 本周日日期
	 * @return
	 */
	public static Date getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 6);
		Date sunday = currentDate.getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String preMonday = sdf.format(sunday);
//		return preMonday;
		return sunday;
	}
	/**
	 * 当天日期
	 * @param dateformat
	 * @return
	 */
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		String hehe = dateFormat.format(now);
		return hehe;
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(7) - 1;
		if (dayOfWeek == 1) {
			return 0;
		}
		return (1 - dayOfWeek);
	}

	/**
	 * 上周周日
	 * @return
	 */
	public static Date getPreviousWeekSunday() {
		weeks = 0;
		weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + weeks);
		Date sunday = currentDate.getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String preMonday = sdf.format(monday);
//		return preMonday;
		return sunday;
	}
	/**
	 * 上周周一
	 * @return
	 */
	public static Date getPreviousWeekday() {
		weeks = 0;
		weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String preMonday = sdf.format(monday);
//		return preMonday;
		return monday;
	}
	
	 /** 
     * 根据开始时间和结束时间返回时间段内的时间集合 日期倒序排
     *  
     * @param beginDate 
     * @param endDate 
     * @return List 
     */  
    public static List<Date> getDatesBetweenTwoDateDesc(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        if(!beginDate.equals(endDate)){
        	lDate.add(endDate);// 把开始时间加入集合  
        }
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(endDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
            // 测试此日期是否在指定日期之前
            if (beginDate.before(cal.getTime())) {  
                lDate.add(cal.getTime());  
            } else {  
                break;  
            }  
        }  
        lDate.add(beginDate);// 把结束时间加入集合  
        return lDate;  
    }  
    /**
     * 根据开始时间和结束时间返回时间段内的时间集合 日期正序排
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getDatesBetweenTwoDateAsc(Date beginDate, Date endDate) {  
    	List<Date> lDate = new ArrayList<Date>();  
    	if(!endDate.equals(beginDate)){
    		lDate.add(beginDate);// 把开始时间加入集合  
    	}
    	Calendar cal = Calendar.getInstance();  
    	// 使用给定的 Date 设置此 Calendar 的时间  
    	cal.setTime(beginDate);  
    	boolean bContinue = true;  
    	while (bContinue) {  
    		// 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
    		cal.add(Calendar.DAY_OF_MONTH, 1);  
    		// 测试此日期是否在指定日期之后  
    		if (endDate.after(cal.getTime())) {  
    			lDate.add(cal.getTime());  
    		} else {  
    			break;  
    		}  
    	}  
    	lDate.add(endDate);// 把结束时间加入集合  
    	return lDate;  
    }  
}
