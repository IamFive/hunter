package edu.hunter.modules.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	/**
	 * 
	 * @Title: Str20ToStr14
	 * @Description: TODO(将yyyy-MM-dd HH:mm:ss格式化yyyyMMddHHmmss)
	 * @param str
	 * @return String 返回类型
	 * @throws
	 */
	public static String Str20ToStr14(String str) {
		if ((str == null) || str.equals("")) {
			return null;
		}
		String a = str.replaceAll("-", "").replaceAll(" ", "")
				.replaceAll(":", "");
		return a;
	}

	/**
	 * @throws ParseException
	 * @Title: Str20ToStr14
	 * @Description: TODO(将yyyy-MM-dd HH:mm:ss格式化Date)
	 * @param str
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date Str20ToDate(String str) throws ParseException {
		if ((str == null) || str.equals("")) {
			return null;
		}
		String a = str.replaceAll("-", "").replaceAll(" ", "")
				.replaceAll(":", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = sdf.parse(a);
		return d;
	}

	/**
	 * @Title: getStringDate
	 * @Description: TODO(获取当前字符串的日期)
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public final static String getStringDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @Title: dateToString8
	 * @Description: TODO(将如期格式化为8位的字符串形式，如：20110415)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String dateToString8(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: dateToString14
	 * @Description: TODO(将如期格式化为14位的字符串形式，如：20110415101112)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String dateToString14(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: dateToString20
	 * @Description: TODO(将如期格式化为14位的字符串形式，如：2011-04-15 10:11:12)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String dateToString20(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: dateToString10
	 * @Description: TODO(将如期格式化为10位的字符串形式，如：2011-04-15)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String dateToString10(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: dateToString
	 * @Description: TODO(根据指定的格式化串来格式化日期)
	 * @param @param format 格式化串
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String dateToString(String format, Date date) {
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: string8ToDate
	 * @Description: TODO(将一个yyyyMMdd格式的字符串转行为日期)
	 * @param @param date
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date string8ToDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.length() == 14) {
			return string14ToDate(date);
		}
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: string14ToDate
	 * @Description: TODO(将一个yyyyMMddHHmmss格式的字符串转行为日期)
	 * @param @param date
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date string14ToDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.length() == 8) {
			return string8ToDate(date);
		}
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, 8));
		int house = Integer.parseInt(date.substring(8, 10));
		int minute = Integer.parseInt(date.substring(10, 12));
		int second = Integer.parseInt(date.substring(12));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, house);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: string19ToDate
	 * @Description: TODO(将一个yyyy-MM-dd HH:mm:ss格式的字符串转行为日期)
	 * @param @param date
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date string19ToDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.length() != 19) {
			return null;
		}
		date = dateToString14(stringToDate(date, "yyyy-MM-dd HH:mm:ss",
				"yyyyMMddHHmmss"));
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, 8));
		int house = Integer.parseInt(date.substring(8, 10));
		int minute = Integer.parseInt(date.substring(10, 12));
		int second = Integer.parseInt(date.substring(12));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, house);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: compareTo
	 * @Description: TODO(比较两个字符串(yyyyMMdd 或 yyyyMMddHHmmss)形似的日期)
	 * @param @param date
	 * @param @param compareDate
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int compareTo(String date, String compareDate) {
		Date d1 = DateUtil.string14ToDate(date);
		Date d2 = DateUtil.string14ToDate(compareDate);
		if ((d1 == null) || (d2 == null)) {
			return -100;
		}
		return d1.compareTo(d2);
	}

	public static int compareTo(Date date) {
		Date now = new Date();
		return now.compareTo(date);
	}
	
	/**
	 * 比较日期字符串格式为 yyyy-MM-dd HH:mm:ss的大小比较
	 * @param date
	 * @param compareDate
	 * @return 0 ==  <0 
	 */
	public static int compareToString(String date, String compareDate) {
		return StringToDate20(date).compareTo(StringToDate20(compareDate));
	}

	public static Date  StringToDate20(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定年份和月份对应的天数
	 * 
	 * @param year
	 *            指定的年份
	 * @param month
	 *            指定的月份
	 * @return int 返回天数
	 */
	public static int getDaysInMonth(int year, int month) {
		if ((month == 1) || (month == 3) || (month == 5) || (month == 7)
				|| (month == 8) || (month == 10) || (month == 12)) {
			return 31;
		} else if ((month == 4) || (month == 6) || (month == 9)
				|| (month == 11)) {
			return 30;
		} else {
			if ((((year % 4) == 0) && ((year % 100) != 0))
					|| ((year % 400) == 0)) {
				return 29;
			} else {
				return 28;
			}
		}
	}

	/**
	 * 根据所给的起止时间来计算间隔的天数
	 * 
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @return int 返回间隔天数
	 */
	public static int getIntervalDays(java.sql.Date startDate,
			java.sql.Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
		return intervalday;
	}

	/**
	 * 
	 * @Title：chaDay
	 * @Description：比较2个时间相差天数
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 *             long
	 */
	public static long chaDay(String startTime, String endTime)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startTime));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(endTime));
		long time2 = cal.getTimeInMillis();
		long between = (time2 - time1) / (1000 * 3600 * 24);
		return between;

	}

	/**
	 * 根据所给的起止时间来计算间隔的月数
	 * 
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @return int 返回间隔月数
	 */
	@SuppressWarnings("static-access")
	public static int getIntervalMonths(java.sql.Date startDate,
			java.sql.Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int startDateM = startCal.MONTH;
		int startDateY = startCal.YEAR;
		int enddatem = endCal.MONTH;
		int enddatey = endCal.YEAR;
		int interval = ((enddatey * 12) + enddatem)
				- ((startDateY * 12) + startDateM);
		return interval;
	}

	/**
	 * 返回当前日期时间字符串<br>
	 * 默认格式:yyyy-mm-dd hh:mm:ss
	 * 
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * 返回自定义格式的当前日期时间字符串
	 * 
	 * @param format
	 *            格式规则
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime(String format) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * 返回当前字符串型日期
	 * 
	 * @return String 返回的字符串型日期
	 */
	public static String getCurDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}

	/**
	 * 将字符串型日期转换为日期型
	 * 
	 * @param strDate
	 *            字符串型日期
	 * @param srcDateFormat
	 *            源日期格式
	 * @param dstDateFormat
	 *            目标日期格式
	 * @return Date 返回的util.Date型日期
	 */
	public static Date stringToDate(String strDate, String srcDateFormat,
			String dstDateFormat) {
		Date rtDate = null;
		Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate,
				new ParsePosition(0));
		String tmpString = null;
		if (tmpDate != null) {
			tmpString = (new SimpleDateFormat(dstDateFormat)).format(tmpDate);
		}
		if (tmpString != null) {
			rtDate = (new SimpleDateFormat(dstDateFormat)).parse(tmpString,
					new ParsePosition(0));
		}
		return rtDate;
	}

	/**
	 * @Title: changeDay
	 * @Description: TODO(天数加减)
	 * @param date
	 * @param offset
	 * @return Date
	 * @throws
	 */
	public static Date changeDay(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR,
				(calendar.get(Calendar.DAY_OF_YEAR) + offset));
		return calendar.getTime();
	}

	/**
	 * @Title: changeMin
	 * @Description: TODO(分钟加减)
	 * @param date
	 * @param offset
	 * @return Date
	 * @throws
	 */
	public static Date changeMin(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) + offset));
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title：changeSecond
	 * @Description：秒书加减
	 * @param date
	 * @param offset
	 * @return Date
	 */
	public static Date changeSecond(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, (calendar.get(Calendar.SECOND) + offset));
		return calendar.getTime();
	}

	/**
	 * @Title: formatTimeStampe
	 * @Description: TODO(将时间戳转换成普通日期)
	 * @param timestampString
	 * @return String
	 * @throws
	 */
	public static String formatTimeStampe(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	/**
	 * @Title: getTimeStamp
	 * @Description: TODO(获取当前时间戳)
	 * @return String
	 * @throws
	 */
	public static String getTimeStamp() {
		Date date = new Date();
		Long time = date.getTime();
		Long dateline = time / 1000;
		return String.valueOf(dateline);
	}

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(获取距离当前时间最近一个月的日期)
	 * @param now
	 * @return
	 */
	public static Date getOneMonth(Calendar now) {
		int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(now.get(Calendar.YEAR))) {
			months[1] = 29;
		}
		int nowmonth = now.MONTH;
		int month = nowmonth - 1;
		long time = now.getTimeInMillis()
				- ((long) (months[month]) * 24 * 3600 * 1000);
		return new Date(time);
	}

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(获取距离当前时间最近两个月的日期)
	 * @param now
	 * @return
	 */
	public static Date getTwoMonth(Calendar now) {
		int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(now.get(Calendar.YEAR))) {
			months[1] = 29;
		}
		int nowmonth = now.MONTH;
		int month1 = nowmonth - 1;
		int month2 = nowmonth;
		long time = now.getTimeInMillis()
				- ((long) ((months[month1]) + (months[month2])) * 24 * 3600 * 1000);
		return new Date(time);
	}

	/**
	 * 
	 * @Title: isLeapYear
	 * @Description: TODO(判断是否是闰年)
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		boolean tags = false;
		if ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0)) {
			tags = true;
		}
		return tags;
	}

	/**
	 * 
	 * @Title: changeDate
	 * @Description: TODO(获取一段时间后的时间)
	 * @param source
	 * @param sourcFormat
	 * @param c
	 * @param i
	 * @param returnFormat
	 * @return
	 */
	public static String changeDate(String source, String sourcFormat, int c,
			int interval, String returnFormat) {
		String str = "";
		try {
			SimpleDateFormat sd = new SimpleDateFormat(sourcFormat);
			Date sourceDate = sd.parse(source);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sourceDate);
			cal.add(c, interval);
			Date retDate = cal.getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat(returnFormat);
			str = sdf1.format(retDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;

	}

	/**
	 * 
	 * @Title: convertWeekByDate
	 * @Description: TODO(计算本周起始日期（礼拜一的日期）)
	 * @param time
	 */
	public static String convertWeekByDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // 设置时间格式

		Calendar cal = Calendar.getInstance();

		cal.setTime(time);

		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期

		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

		String imptimeBegin = sdf.format(cal.getTime());

		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		//
		// cal.add(Calendar.DATE, 6);
		//
		// String imptimeEnd = sdf.format(cal.getTime());
		//
		// System.out.println("所在周星期日的日期：" + imptimeEnd);

		return imptimeBegin;
	}

	/**
	 * 
	 * @Title: addDate
	 * @Description: TODO(给日期增加指定天数)
	 * @param time
	 * @param day
	 * @throws Exception
	 */
	public static String addDate(String time, int day) throws Exception {
		Calendar fromCal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		Date date = dateFormat.parse(time);
		fromCal.setTime(date);
		fromCal.add(Calendar.DATE, day);

		// System.out.println(dateFormat.format(fromCal.getTime()));

		return dateFormat.format(fromCal.getTime());
	}

	/**
	 * 
	 * @Title: addDate
	 * @Description: TODO(给日期增加指定天数)
	 * @param time
	 * @param day
	 * @throws Exception
	 */
	public static String addDate20(String time, int day) throws Exception {
		Calendar fromCal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse(time);
		fromCal.setTime(date);
		fromCal.add(Calendar.DATE, day);
		return dateFormat.format(fromCal.getTime());
	}

	/**
	 * 
	 * @Title: addDate
	 * @Description: TODO(给日期增加指定天数)
	 * @param time
	 * @param day
	 * @throws Exception
	 */
	public static String addDate10(String time, int day) throws Exception {
		Calendar fromCal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(time);
		fromCal.setTime(date);
		fromCal.add(Calendar.DATE, day);
		return dateFormat.format(fromCal.getTime());
	}

	/** 通过设置年月日时分秒来获得时间 **/
	public static Date getDate(int year, int month, int day, int hour,
			int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTime();
	}

	/**
	 * 获取指定日期后n天的凌晨
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDateNextDay(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.DAY_OF_YEAR, (c.get(c.DAY_OF_YEAR) + n));
		return c.getTime();
	}

	/**
	 * 
	 * @Title: bijiao
	 * @Description: TODO(比较日期大小，格式yyyy-MM-dd HH:mm:ss或yyyy-MM-dd)
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean bijiao(String str1, String str2) {
		boolean bo = false;
		str1 = str1.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		str2 = str2.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		long s1 = Long.parseLong(str1);
		long s2 = Long.parseLong(str2);
		if (s1 > s2) {
			bo = true;
		}
		return bo;
	}

	public static String daojishi(Date smallTime, Date bigTime) {
		long time_distance = bigTime.getTime() - smallTime.getTime();
		long int_day = (long) Math.floor(time_distance / 86400000);
		time_distance -= int_day * 86400000;
		long int_hour = (long) Math.floor(time_distance / 3600000);
		time_distance -= int_hour * 3600000;
		long int_minute = (long) Math.floor(time_distance / 60000);
		time_distance -= int_minute * 60000;
		long int_second = (long) Math.floor(time_distance / 1000);
		String str_day = "";
		String str_hour = "";
		String str_minute = "";
		String str_second = "";
		str_day = int_day + "";
		if (int_hour < 10) {
			str_hour = "0" + int_hour;
		} else {
			str_hour = int_hour + "";
		}
		if (int_minute < 10) {
			str_minute = "0" + int_minute;
		} else {
			str_minute = int_minute + "";
		}
		if (int_second < 10) {
			str_second = "0" + int_second;
		} else {
			str_second = int_second + "";
		}
		String str_time = str_day + "天" + str_hour + "时" + str_minute + "分"
				+ str_second + "秒";
		// String str_time = str_day + "天" + str_hour + "小时" + str_minute + "分钟"
		// + str_second + "秒";
		return str_time;

	}

	/**
	 * 
	 * @Title：ifOpTime
	 * @Description：开奖时间
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String getOpTime() throws Exception {
		String format = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String nowTime = sdf.format(new Date());
		boolean bo = bijiao(nowTime, "20:00:00");
		String optime = DateUtil.dateToString10(new Date());
		if (bo) {
			optime = DateUtil.addDate10(optime, 1);
		}
		return optime;
	}

	/**
	 * 
	 * @Title：bj
	 * @Description：减去当前时间 得到的小时数
	 * @param str
	 * @return long
	 */
	public static long bj(String str) {
		long diff = 0;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Date st = df.parse(str);
			diff = st.getTime() - date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff / (60000 * 60);
	}
	
	/**
	 * 
	 * @Description：获取当前的年份
	 * @return Integer
	 */
	public static Integer currentYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * @Description:根据日期获取时间日
	 * @param date
	 * @return
	 */
	public static int getDayByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @Description:根据日期获取时间月
	 * @param date
	 * @return
	 */
	public static int getMonthByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}
	
	/**
	 * @Description:根据日期获取时间年
	 * @param date
	 * @return
	 */
	public static int getYearByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 
	 * @Description：获取当前月份的天数
	 * @return Integer
	 */
	public static Integer monthOfDays(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @Title：addHourBj20
	 * @Description：给日期增加指定小时数 如果大于当前时间返回true
	 * @param time
	 * @param hour
	 * @return
	 * @throws Exception
	 *             boolean
	 */
	public static boolean addHourBj20(String time, int hour) throws Exception {
		Calendar fromCal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse(time);
		fromCal.setTime(date);
		fromCal.add(Calendar.HOUR_OF_DAY, hour);
		Calendar nowTime = Calendar.getInstance();
		int re = fromCal.compareTo(nowTime);
		if (re == 1) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		boolean bo = addHourBj20("2014-03-28 17:00:00", 1);
		System.out.println(bo);
	}
}
