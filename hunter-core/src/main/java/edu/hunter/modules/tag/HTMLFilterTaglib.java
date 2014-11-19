/**   
 * @Title: HTMLFilterTag.java
 * @Package com.zfh.core.tag
 * @date 2011-10-30 下午03:52:05
 * @version V1.0   
 */

package edu.hunter.modules.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @ClassName: HTMLFilterTag
 * @Description: TODO(显示前<length>个字符，不包括HTML标签)
 * @author whwang
 * @date 2011-10-30 下午03:52:05
 * 
 */
public class HTMLFilterTaglib extends TagSupport {
	/** 需要格式化的数据 */
	private String data;

	private Integer length;

	@Override
	public int doEndTag() throws JspException {
		if (data == null || data.length() <= 0 || length == null || length <= 0) {
			return super.doEndTag();
		}
		JspWriter out = super.pageContext.getOut();

		StringBuffer ret = new StringBuffer();
		int cntLength = 0;
		boolean isHTML = false;
		for (int i = 0; i < data.length(); i++) {
			if (cntLength >= length) {
				break;
			}
			if (data.charAt(i) == '<') {
				isHTML = true;
				continue;
			}
			if (data.charAt(i) == '>') {
				if (isHTML) {
					isHTML = false;
					continue;
				} else {
					ret.append("&gt;");
					cntLength++;
					continue;
				}
			}
			if (isHTML) {
				continue;
			}
			ret.append(data.charAt(i));
			cntLength++;
		}
		try {
			out.write(ret.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
