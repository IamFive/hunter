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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.hunter.modules.common.util.AppUtils;

/**
 * @ClassName：ChangeContentTaglib
 * @Description：改变页面内容标签
 * @author：ShiSongBin
 * @date：2013年11月30日 下午1:29:26
 * 
 */

@SuppressWarnings("serial")
public class ChangeContentTaglib extends TagSupport {
	private String content;// 输入的内容
	private String changeStr;// 改变成的内容
	private String filling;// 默认填充，none不填充 ( 默认填充：如4至10位每位都填充changeStr，不填充则就4至10替换成changeStr)
	private String changeBegin;// 如果为数字则为改变结束的位数 ， 字符串为改变开始字符串
	private String changeNum;// 开始后改变几位 为0则至结束
	private String containFirst;// 是否包含开始的第一位 默认为空不包含 yes包含

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		if ((changeBegin != null) && !"".equals(changeBegin)) {
			int change_num = (changeNum == null ? 0 : Integer.valueOf(changeNum));
			if (AppUtils.isNumeric(changeBegin)) {
				content = getContent(content, filling, Integer.valueOf(changeBegin) - 1, change_num, changeStr, containFirst);
			} else {
				int change_Begin = content.lastIndexOf(changeBegin);
				content = getContent(content, filling, change_Begin, change_num, changeStr, containFirst);
			}
		}
		if (out != null) {
			try {
				out.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	public String getContent(String contentText, String fill, int beginNum, int changeNum, String changeStr, String containFirstText) {
		if (!(AppUtils.isNotEmpty(containFirstText) && "yes".equals(containFirstText.toLowerCase()))) {
			beginNum += 1;
		}
		if ((contentText != null) && (contentText.length() > 0)) {
			if (contentText.length() > (beginNum + changeNum)) {
				int changeEnd = beginNum + changeNum;
				if (changeEnd > beginNum) {
					changeStr = getChangeStr(fill, changeEnd - beginNum, changeStr);
					contentText = contentText.substring(0, beginNum) + changeStr + contentText.substring(beginNum + changeNum);
				} else {
					changeStr = getChangeStr(fill, contentText.length() - beginNum, changeStr);
					contentText = contentText.substring(0, beginNum) + changeStr;
				}
			} else {
				changeStr = getChangeStr(fill, contentText.length() - beginNum, changeStr);
				contentText = contentText.substring(0, beginNum) + changeStr;
			}
		}

		return contentText;
	}

	public String getChangeStr(String filling, int num, String changeText) {
		String reStr = "";
		if (!"none".equals(filling)) {
			for (int i = 0; i < num; i++) {
				reStr += changeText;
			}
		} else {
			reStr = changeText;
		}
		return reStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChangeStr() {
		return changeStr;
	}

	public void setChangeStr(String changeStr) {
		this.changeStr = changeStr;
	}

	public String getFilling() {
		return filling;
	}

	public void setFilling(String filling) {
		this.filling = filling;
	}

	public String getChangeBegin() {
		return changeBegin;
	}

	public void setChangeBegin(String changeBegin) {
		this.changeBegin = changeBegin;
	}

	public String getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(String changeNum) {
		this.changeNum = changeNum;
	}

}
