/**   
 * @Title: StarFormat.java
 * @Package com.zfh.core.tag
 * @date 2011-10-16 下午03:17:00
 * @version V1.0   
 */

package edu.hunter.modules.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @ClassName: StarFormat
 * @Description: TODO(将一个字符串的中间显示为"*")
 * @author whwang
 * @date 2011-10-16 下午03:17:00
 * 
 */
@SuppressWarnings("serial")
public class StarFormatTaglibs extends TagSupport {
	/**
	 * 需要格式化的字符数量(最中间的count个) 如果字符串长度小于等于count, 将显示******(全部是星号)
	 */
	private Integer count;

	/**
	 * 需要格式化的字符串
	 */
	private String data;

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		StringBuffer sb = new StringBuffer("");
		if (count == null || count <= 0) {
			count = 12;
		}
		int temp = count;
		int length = data.length();
		if (count >= length) {
			sb = getStar(length);
		} else {
			if (data != null && data.length() > 0) {
				int left, right;
				int mid = length >> 1;
				if ((length & 1) == 0) {
					if (count > 1) {
						left = mid - 2;
						right = mid + 1;
						count -= 2;
					} else {
						left = mid - 1;
						right = mid + 1;
						count -= 1;
					}
				} else {
					left = mid - 1;
					right = mid + 1;
					count -= 1;
				}
				while (true) {
					if (count <= 0) {
						break;
					}
					if (left >= 0) {
						left--;
						count--;
					}
					if (right < length && count > 0) {
						right++;
						count--;
					}
				}
				if (left >= 0) {
					String head = data.substring(0, left + 1);
					sb.append(head);
				}
				sb.append(getStar(temp));
				if (right < length) {
					String tail = data.substring(right, length);
					sb.append(tail);
				}
			}
		}
		if (out != null) {
			try {
				out.write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	private static StringBuffer getStar(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append("*");
		}
		return sb;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	// public static void main(String[] args)
	// {
	// Integer count = 5;
	// int temp = count;
	// String data = "15805924353";
	// StringBuffer sb = new StringBuffer("");
	// if (count == null || count <= 0)
	// {
	// count = 4;
	// }
	// int length = data.length();
	// if (count >= length)
	// {
	// sb = getStar(length);
	// }
	// else
	// {
	// if (data != null && data.length() > 0)
	// {
	// int left, right;
	// int mid = length >> 1;
	// if ((length & 1) == 0)
	// {
	// if (count > 1) {
	// left = mid - 2;
	// right = mid + 1;
	// count -= 2;
	// } else {
	// left = mid - 1;
	// right = mid + 1;
	// count -= 1;
	// }
	// }
	// else
	// {
	// left = mid - 1;
	// right = mid + 1;
	// count -= 1;
	// }
	// while (true)
	// {
	// if (count <= 0)
	// {
	// break;
	// }
	// if (left >= 0)
	// {
	// left--;
	// count--;
	// }
	// if (right < length && count > 0)
	// {
	// right++;
	// count--;
	// }
	// }
	// if (left >= 0)
	// {
	// String head = data.substring(0, left+1);
	// sb.append(head);
	// }
	// sb.append(getStar(temp));
	// if (right < length)
	// {
	// String tail = data.substring(right, length);
	// sb.append(tail);
	// }
	// }
	// }
	// System.out.println(sb.toString());
	// }

}
