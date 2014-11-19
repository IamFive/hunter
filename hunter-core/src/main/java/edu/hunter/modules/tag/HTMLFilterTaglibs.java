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
 * 
 * @ClassName: HTMLFilterTaglibs
 * @Description: TODO(如果data长度小于<length>个字符，显示全部data;如果大于，显示<length>个字符)
 * @author fkl
 * @date 2013-5-16 下午07:14:10
 * 
 */
@SuppressWarnings("serial")
public class HTMLFilterTaglibs extends TagSupport {
	/** 需要格式化的数据 */
	private String data;

	private Integer length;

	@Override
	public int doEndTag() throws JspException {
		if (data == null || data.length() <= 0 || length == null || length <= 0) {
			return super.doEndTag();
		}
		JspWriter out = super.pageContext.getOut();
		String str = "";
		if (data.length() <= length) {
			str = data;
		} else {
			str = data.substring(0, length - 1) + "...";
		}
		try {
			out.write(str);
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
