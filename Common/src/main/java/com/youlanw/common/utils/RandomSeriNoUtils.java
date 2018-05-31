package com.youlanw.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 产生随机数工具类
 * 
 * @author Nicolas
 * @since 2014年4月1日
 */
public class RandomSeriNoUtils {
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";

	/**
	 * 返回一个uuid的简历code[32位长度]
	 * 
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 产生数字随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int genNumber(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}

	/**
	 * 产生4位随机数字
	 * 
	 * @return
	 */
	public synchronized static String generateNumber4() {
		String num = "" + (int) (Math.random() * 9000 + 1000);
		return num.length() == 3 ? (num + "0") : num;
	}

	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		String numberStr = null;
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		if (sb.toString().length() == length) {
			numberStr = sb.toString();
		} else {
			generateNumString(length);
		}
		return numberStr;
	}

	public static String getDateString() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String reTime = format.format(date);
		StringBuffer sb = new StringBuffer(reTime);
		String nDate = sb.substring(2, 8);
		return nDate;
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 以时间为精确度 线程安全的形式生成随机码
	 * 
	 * @param frontCode
	 * @return
	 */
	public static synchronized String genCodeByTime(String frontCode) {
		StringBuffer bufferStr = new StringBuffer(frontCode);
		bufferStr.append(DateConvertUtils.format(new Date(), "yyyyMMddHHmmssSS") + generateUpperString(3));
		return bufferStr.toString();
	}

	/**
	 * 时间精度的数字字符串
	 * 
	 * @return
	 */
	public static synchronized String genTimeCode() {
		StringBuffer bufferStr = new StringBuffer(generateUpperString(5));
		bufferStr.append(DateConvertUtils.format(new Date(), "yyyyMMddHHmmssSS"));
		return bufferStr.toString();
	}

	/**
	 * 根据月/日/时/分/秒产生排序号
	 * 
	 * @author Nicolas
	 * @return
	 */
	public static Integer genIntegerCodeByTime() {
		return Integer.valueOf(DateConvertUtils.format(new Date(), "MMddHHmmss"));
	}

	/**
	 * 根据年月日产生序号
	 * 
	 * @author Nicolas
	 * @return
	 */
	public static Integer genIntegerCodeByDate() {
		return Integer.valueOf(DateConvertUtils.format(new Date(), "yyyyMMdd"));
	}

	/**
	 * 返回区间的随机数 不包含end
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Integer genIntegerByStartAndEnd(Integer start, Integer end) {
		Random rand = new Random();
		return rand.nextInt(end - start) + start;
	}

	/**
	 * 根据当天和时间范围取得任何前几天时间范围内的随机时间
	 * 
	 * @param arg
	 * @param startHour
	 * @param endHour
	 * @return
	 */
	public static Date randomDateBetweenMinAndMax(int arg, int startHour, int endHour) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -arg);
		// 注意月份要减去1
		calendar.getTime().getTime();
		// 根据需求，这里要将时分秒设置为0
		calendar.set(Calendar.HOUR_OF_DAY, startHour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long min = calendar.getTime().getTime();
		;
		calendar.set(Calendar.HOUR_OF_DAY, endHour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.getTime().getTime();
		long max = calendar.getTime().getTime();
		// 得到大于等于min小于max的double值
		double randomDate = Math.random() * (max - min) + min;
		// 将double值舍入为整数，转化成long类型
		calendar.setTimeInMillis(Math.round(randomDate));
		return calendar.getTime();
	}
}
