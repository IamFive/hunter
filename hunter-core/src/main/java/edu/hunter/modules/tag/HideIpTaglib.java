/**   
 * @Title：ChangeContentTaglib.java 
 * @Package：edu.hunter.modules.tag 
 * @Description：
 * @author：ShiSongBin
 * @date：2013年11月30日 下午1:29:26 
 * @version：V1.0   
 */

package edu.hunter.modules.tag;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.hunter.modules.common.util.AppUtils;

/**
 * 
 * @ClassName：HideIpTaglib
 * @Description：隐藏Ip地址
 * @author：ShiSongBin
 * @date：2013年12月31日 下午4:45:27
 * 
 */

@SuppressWarnings("serial")
public class HideIpTaglib extends TagSupport {
	private String ip;// 输入的内容

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		String showText = "";
		if (isboolIp(ip)) {
			showText = getHideIp(ip);
		}
		if (out != null) {
			try {
				out.write(showText);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	/**
	 * 判断是否为合法IP
	 * 
	 * @return the ip
	 */
	public boolean isboolIp(String ipAddress) {
		if (AppUtils.isEmpty(ipAddress)) {
			return false;
		}
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

	public String getHideIp(String ip) {
		String[] str = ip.split("\\.");
		return str[0] + "." + str[1] + ".*.**"
				+ str[3].substring(str[3].length() - 1);

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
