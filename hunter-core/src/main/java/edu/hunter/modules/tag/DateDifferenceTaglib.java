/**   
 * @Title: DateFormatTaglib.java
 * @Package com.lto.scfo.core.tag
 * @date 2011-4-28 下午04:01:32
 * @version V1.0   
 * Copyright 2003 by LongTopOnline Corporation.
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * LongTopOnline Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with LongTopOnline.
 */

package edu.hunter.modules.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.hunter.modules.common.util.DateUtil;

/**
 * 
 * @ClassName：DateDifferenceTaglib
 * @Description：查询时间差倒计时
 * @author：ShiSongBin
 * @date：2013-11-8 上午8:53:06
 * 
 */
@SuppressWarnings("serial")
public class DateDifferenceTaglib extends TagSupport {
	/**
	 * 日期字符串，只支持形如yyyy-MM-dd HH:mm:ss
	 */
	private String date;

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		String showDate = "";
		if (!((date != null) && !"".equals(date))) {
			showDate = "-";
		} else {
			Date date_time = DateUtil.stringToDate(date, "yyyy-MM-dd HH:mm:ss",
					"yyyy-MM-dd HH:mm:ss");
			Date timeNow = new Date();
			long timeDistance = date_time.getTime() - timeNow.getTime();
			if (timeDistance <= 0) {
				showDate = "-";
			} else {
				long hour = (long) Math.floor(timeDistance / 3600000);
				timeDistance -= hour * 3600000;
				long minute = (long) Math.floor(timeDistance / 60000);
				timeDistance -= minute * 60000;
				String hourStr = "";
				String minuteStr = "";
				if (hour < 10) {
					hourStr = "0" + hour;
				} else {
					hourStr = hour + "";
				}
				if (minute < 10) {
					minuteStr = "0" + minute;
				} else {
					minuteStr = minute + "";
				}
				showDate = hourStr + "小时" + minuteStr + "分";
			}
		}
		if (out != null) {
			try {
				out.write(showDate);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
