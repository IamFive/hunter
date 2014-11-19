/**   
 * @Title: PageTaglib
 * @Package com.lto.scfo.core.tag
 * @date 2011-3-27 下午08:26:36
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

import java.io.File;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * @ClassName: PageTaglib
 * @Description: TODO(分页标签实现类)
 * @author jfwu
 * @date 2011-3-27 下午08:26:36
 * @author klin 修改 将outValue String类型改成StringBuffer
 * 
 */
public class PageTaglib extends TagSupport {

	private static final long serialVersionUID = -669384989909267476L;

	private Object totalRecords;

	private Object totalPages;

	private Object currentPage;

	private Object urlAction;

	private String align;

	private String style;

	private String pageType;

	private String isDialogLocaltion;

	@Override
	public int doEndTag() throws JspException {
		// 分页标签使用
		int int_totalRecords = Integer.parseInt(totalRecords + "");
		int int_totalPages = Integer.parseInt(totalPages + "");
		int int_currentPage = Integer.parseInt(currentPage + "");
		JspWriter out = pageContext.getOut();
		if (int_totalPages > 1) {
			if (align == null || "".equals(align)) {
				align = "right";
			}
			if (style == null) {
				style = "";
			}
			style += "; disabled=true;";
			if (totalRecords == null || "0".equals(int_totalRecords + "")) {
				style += "; disabled=true;";
			}
			if (pageType == null || !"selectpage".equals(pageType)) {
				pageType = "textpage";
			}
			StringBuffer outValue = new StringBuffer("<div align='");
			outValue.append(align).append("' style='").append(style)
					.append("' >");
			outValue.append("当前是第").append(int_currentPage).append("页 / 共")
					.append(int_totalPages).append("页 （共 ")
					.append(int_totalRecords).append(" 项）  ");

			try {
				if (totalRecords == null || "0".equals(totalRecords + "")
						|| int_totalPages == 1) {
					outValue.append("首页 上一页 下一页 末页");
				} else if (int_currentPage == 1) {
					outValue.append("首页 上一页 ");
					outValue.append(
							" <a href='' onclick='javascript:gotoPageInfo(\"")
							.append(((int_currentPage >= int_totalPages) ? (int_totalPages + "")
									: ((int_currentPage + 1) + "")))
							.append("\");return false;'>下一页</a>");
					outValue.append(
							" <a href='' onclick='javascript:gotoPageInfo(\"")
							.append(int_totalPages)
							.append("\");return false;'>末页</a>");
				} else if (int_currentPage == int_totalPages) {
					outValue.append(" <a href='' onclick='javascript:gotoPageInfo(\"1\");return false;' style=''>首页</a>");
					outValue.append(
							" <a href='' onclick='javascript:gotoPageInfo(\"")
							.append(((int_currentPage > 1) ? (int_currentPage - 1)
									+ ""
									: "1"))
							.append("\");return false;'>上一页</a>");
					outValue.append(" 下一页 末页");
				} else {
					outValue.append(" <a href='' onclick='javascript:gotoPageInfo(\"1\");return false;' style=''>首页</a>");
					outValue.append(
							" <a href='' onclick='javascript:gotoPageInfo(\"")
							.append(((int_currentPage > 1) ? (int_currentPage - 1)
									+ ""
									: "1"))
							.append("\");return false;'>上一页</a>");
					outValue.append(
							" <a href='' onclick='javascript:gotoPageInfo(\"")
							.append(((int_currentPage >= int_totalPages) ? (int_totalPages + "")
									: ((int_currentPage + 1) + "")))
							.append("\");return false;'>下一页</a>");
					outValue.append(
							" <a href='' onclick='javascript:gotoPageInfo(\"")
							.append(int_totalPages)
							.append("\");return false;'>末页</a>");
				}

				if ("textpage".equals(pageType)) {
					outValue.append(" <input type='text' id='toPageIndex' name='toPageIndex' style='width:20px;height:12px;' onkeyup='clearNoNum(this)' />");
					outValue.append(" <a href='' onclick='javascript:gotoPageInfo(\"jamppage\");return false;'>跳转</a>");
					// 以下是js验证
					outValue.append("<script type='text/javascript'>var outInputValue = '';function clearNoNum(obj){");
					outValue.append("obj.value = obj.value.replace(/[^")
							.append(File.separator).append("d]/g,'');");
					outValue.append(
							"if (obj.value == '0'){obj.value = ''}else{if (obj.value > '")
							.append(int_totalPages)
							.append("'){obj.value = outInputValue;}else{outInputValue = obj.value;}}}</script>");

				} else if ("selectpage".equals(pageType)) {
					outValue.append(" <select name='select2' style='width :70px; height :20px; FONT-SIZE: 9pt;' onchange='javascript:gotoPageInfo(this.value);'>");
					for (int i = 1; i <= int_totalPages; i++) {
						outValue.append("<option value = '")
								.append(i)
								.append("' ")
								.append((((i + "").equals(int_currentPage + "")) ? "selected='selected'"
										: "")).append("> 第").append(i)
								.append("页 </option>");
					}
					outValue.append("</select>");
				}
				outValue.append("</div>");
				// 以下JS是处理跳转的页面js
				outValue.append("<script type='text/javascript'>function gotoPageInfo(opval){if (opval == 'jamppage'){");
				outValue.append("opval = document.getElementById('toPageIndex').value;");
				outValue.append("if (opval > ").append(int_totalPages)
						.append(" && ").append(int_totalPages).append(" > 0){");
				outValue.append("alert('需要跳转的页面超出页面范围 1 至 ")
						.append(int_totalPages).append(" 页！');");
				outValue.append("document.getElementById('toPageIndex').value = '';");
				outValue.append("return;").append("}}");
				outValue.append("if('").append(int_totalPages)
						.append("' < 2 || '").append(int_currentPage)
						.append("' == opval){");
				outValue.append("return;").append("}else{");
				if ("true".equalsIgnoreCase(isDialogLocaltion)) {
					outValue.append("document.getElementById('ques').action='");
					int pos = urlAction.toString().indexOf("?");
					if (pos > 0) {
						outValue.append(urlAction)
								.append("&pageIndex='+opval;");
					} else {
						outValue.append(urlAction)
								.append("?pageIndex='+opval;");
					}
					outValue.append("document.getElementById('ques').submit();");
				} else {
					outValue.append("window.location=('");
					int pos = urlAction.toString().indexOf("?");
					if (pos > 0) {
						outValue.append(urlAction).append(
								"&pageIndex='+opval);");
					} else {
						outValue.append(urlAction).append(
								"?pageIndex='+opval);");
					}
				}

				outValue.append("}}</script>");

				out.write(outValue.toString());

			} catch (Exception e) {

				throw new JspException("JspException");

			}
		} else {
			try {
				out.write("");
			} catch (IOException e) {
			}
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (totalRecords == null) {
			totalRecords = "0";
		}
		if (totalPages == null) {
			totalPages = "0";
		}
		if (currentPage == null) {
			currentPage = "0";
		}

		return EVAL_PAGE;
	}

	public Object getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Object currentPage) throws JspException {
		if (currentPage == null) {
			currentPage = "1";
		} else {
			this.currentPage = ExpressionEvaluatorManager.evaluate(
					"currentPage", currentPage.toString(), Object.class, this,
					pageContext);
		}

	}

	public Object getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Object totalPages) throws JspException {
		if (totalPages == null) {
			totalPages = "1";
		} else {
			this.totalPages = ExpressionEvaluatorManager.evaluate("totalPages",
					totalPages.toString(), Object.class, this, pageContext);
		}

	}

	public Object getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Object totalRecords) throws JspException {
		if (totalRecords == null) {
			totalRecords = "0";
		} else {
			this.totalRecords = ExpressionEvaluatorManager.evaluate(
					"totalRecords", totalRecords.toString(), Object.class,
					this, pageContext);
		}

	}

	public Object getUrlAction() {
		return urlAction;
	}

	public void setUrlAction(Object urlAction) throws JspException {
		this.urlAction = ExpressionEvaluatorManager.evaluate("urlAction",
				urlAction.toString(), Object.class, this, pageContext);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getIsDialogLocaltion() {
		return isDialogLocaltion;
	}

	public void setIsDialogLocaltion(String isDialogLocaltion) {
		this.isDialogLocaltion = isDialogLocaltion;
	}

}
