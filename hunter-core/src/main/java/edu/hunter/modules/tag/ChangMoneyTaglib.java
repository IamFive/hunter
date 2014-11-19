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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @ClassName：ChangMoneyTaglib
 * @Description：维护金额
 * @author：ShiSongBin
 * @date：2013年12月28日 下午1:58:04
 * 
 */
@SuppressWarnings("serial")
public class ChangMoneyTaglib extends TagSupport {
	/**
	 * Long 金额 分为单位
	 */
	private Long money;

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		String showDate = "";
		if (money == null) {
			showDate = "-";
		} else {
			if (money > 1000000) {
				String em = (money / 1000000.0) + "";
				showDate = em.substring(0, em.indexOf(".") + 2) + "万";
			} else {
				showDate = (money / 100.0) + "";
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

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

}
