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
 * @ClassName：ChangeDateTaglib
 * @Description：时间天数变更
 * @author：ShiSongBin
 * @date：2013年12月31日 下午6:07:38
 * 
 */
@SuppressWarnings("serial")
public class ChangeDateTaglib extends TagSupport {
	/**
	 * 日期字符串，只支持形如yyyy-MM-dd HH:mm:ss或yyyy-MM-dd两种形式
	 */
	private String date;

	/**
	 * 日期格式串
	 */
	private String format;
	/**
	 * 变更时间
	 */
	private String changeNum;
	/**
	 * 变更类型 day min sec
	 */
	private String changeType;
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		String showDate = "";
		if (date == null) {
			showDate = "";
		}
		date = date.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		boolean isDigit = true;
		for (int i = 0; i < date.length(); i++) {
			if (!Character.isDigit(date.charAt(i))) {
				isDigit = false;
				break;
			}
		}
		if (!isDigit) {
			showDate = date;
		} else if (date.length() == 14) {
			if ((format == null) || "".equals(format.trim())) {
				format = "yyyy-MM-dd HH:mm:ss";
			}
			showDate = DateUtil.dateToString(format,
					DateUtil.string14ToDate(date));
		} else if (date.length() == 8) {
			if ((format == null) || "".equals(format.trim())) {
				format = "yyyy-MM-dd";
			}
			showDate = DateUtil.dateToString(format,
					DateUtil.string8ToDate(date));
		} else {
			showDate = date;
		}
		Date date = DateUtil.stringToDate(showDate, format, "yyyyMMddHHmmss");
		if ("day".equals(changeType)) {
			showDate = DateUtil.dateToString(format,
					DateUtil.changeDay(date, Integer.valueOf(changeNum)));
		} else if ("min".equals(changeType)) {
			showDate = DateUtil.dateToString(format,
					DateUtil.changeMin(date, Integer.valueOf(changeNum)));
		} else if ("sec".equals(changeType)) {
			showDate = DateUtil.dateToString(format,
					DateUtil.changeSecond(date, Integer.valueOf(changeNum)));
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}


	public String getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(String changeNum) {
		this.changeNum = changeNum;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	

}
