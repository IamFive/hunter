/**   
 * @Title: TreeTaglib
 * @Package com.lto.scfo.core.tag
 * @date 2011-3-25 下午08:26:36
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.hunter.modules.common.util.TreeUtil;

/**
 * @ClassName: TreeTaglib
 * @Description: TODO(弹出树tab标签实现类，注意在页面要先引入ztree所需的css和js)
 * @author jfwu
 * @date 2011-08-28 下午08:26:36
 * 
 */
public class TreeTaglib extends TagSupport {
	private String rootName;
	private String id; // tree的id
	private String treeUtilList; // 数据列表
	private String checkable; // 是否显示多选框
	private String beforeClick; //
	private String beforeCollapse;
	private String beforeDbclick;
	private String beforeExpand;
	private String click;
	private String dblclick;
	private String collapse;
	private String expand;
	private String rightClick;
	private String folderUrl;
	private String folderOpenUrl;
	private String async;
	private String asyncUrl;
	private String asyncSuccess;
	private String asyncError;
	private String showIcon;
	private String showLine;
	private String isSimpleData;
	private String isOpen;

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getIsSimpleData() {
		return isSimpleData;
	}

	public void setIsSimpleData(String isSimpleData) {
		this.isSimpleData = isSimpleData;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getShowIcon() {
		return showIcon;
	}

	public void setShowIcon(String showIcon) {
		this.showIcon = showIcon;
	}

	public String getShowLine() {
		return showLine;
	}

	public void setShowLine(String showLine) {
		this.showLine = showLine;
	}

	public String getAsync() {
		return async;
	}

	public void setAsync(String async) {
		this.async = async;
	}

	public String getAsyncUrl() {
		return asyncUrl;
	}

	public void setAsyncUrl(String asyncUrl) {
		this.asyncUrl = asyncUrl;
	}

	public String getAsyncSuccess() {
		return asyncSuccess;
	}

	public void setAsyncSuccess(String asyncSuccess) {
		this.asyncSuccess = asyncSuccess;
	}

	public String getAsyncError() {
		return asyncError;
	}

	public void setAsyncError(String asyncError) {
		this.asyncError = asyncError;
	}

	public String getCheckable() {
		return checkable;
	}

	public void setCheckable(String checkable) {
		this.checkable = checkable;
	}

	public String getTreeUtilList() {
		return treeUtilList;
	}

	public void setTreeUtilList(String treeUtilList) {
		this.treeUtilList = treeUtilList;
	}

	public String getBeforeClick() {
		return beforeClick;
	}

	public void setBeforeClick(String beforeClick) {
		this.beforeClick = beforeClick;
	}

	public String getBeforeCollapse() {
		return beforeCollapse;
	}

	public void setBeforeCollapse(String beforeCollapse) {
		this.beforeCollapse = beforeCollapse;
	}

	public String getBeforeDbclick() {
		return beforeDbclick;
	}

	public void setBeforeDbclick(String beforeDbclick) {
		this.beforeDbclick = beforeDbclick;
	}

	public String getBeforeExpand() {
		return beforeExpand;
	}

	public void setBeforeExpand(String beforeExpand) {
		this.beforeExpand = beforeExpand;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getDblclick() {
		return dblclick;
	}

	public void setDblclick(String dblclick) {
		this.dblclick = dblclick;
	}

	public String getCollapse() {
		return collapse;
	}

	public void setCollapse(String collapse) {
		this.collapse = collapse;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public String getRightClick() {
		return rightClick;
	}

	public void setRightClick(String rightClick) {
		this.rightClick = rightClick;
	}

	public String getFolderUrl() {
		return folderUrl;
	}

	public void setFolderUrl(String folderUrl) {
		this.folderUrl = folderUrl;
	}

	public String getFolderOpenUrl() {
		return folderOpenUrl;
	}

	public void setFolderOpenUrl(String folderOpenUrl) {
		this.folderOpenUrl = folderOpenUrl;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();
		StringBuffer ls_out = new StringBuffer();
		Object o = null;
		List<TreeUtil> list = null;
		o = request.getAttribute(treeUtilList);
		if (o instanceof List) {
			list = (List<TreeUtil>) o;
			if (list.size() > 0) {
				ls_out.append("<div><ul id=\"" + id
						+ "\" class=\"tree\"></ul> </div>");
				ls_out.append("<script type=\"text/javascript\">");
				ls_out.append("var setting =" + getSetting() + ";");
				ls_out.append("var node;");
				ls_out.append("var nodes =\"[");
				for (TreeUtil treeUtil : list) {
					String id = treeUtil.getNodeId();
					String title = treeUtil.getNodeTitle();
					String url = treeUtil.getNodeUrl();
					String parentId = treeUtil.getParentId();
					String isSelected = treeUtil.getIsSelected();
					if (isSelected != null && isSelected.equals("1")) {
						if (isOpen != null && "true".equals(isOpen)) {
							ls_out.append("{'id':'" + id + "','pId':'"
									+ parentId + "','name':'" + title
									+ "','link':'" + url
									+ "',checked:true,open:true},");
						} else {
							ls_out.append("{'id':'" + id + "','pId':'"
									+ parentId + "','name':'" + title
									+ "','link':'" + url + "',checked:true},");
						}
					} else {
						if (isOpen != null && "true".equals(isOpen)) {
							ls_out.append("{'id':'" + id + "','pId':'"
									+ parentId + "','name':'" + title
									+ "','link':'" + url + "',open:true},");
						} else {
							ls_out.append("{'id':'" + id + "','pId':'"
									+ parentId + "','name':'" + title
									+ "','link':'" + url + "'},");
						}

					}
				}
				if (rootName != null && !"".equals(rootName)) {
					ls_out.append("{'id':'0','pId':'-1','name':'" + rootName
							+ "'},");
				}
				ls_out.append("\";");
				ls_out.append("nodes = nodes.substr(0,nodes.length-1);");
				ls_out.append("nodes=nodes+\"]\";");
				ls_out.append("nodes=eval(nodes);");
				ls_out.append("var " + id + "=$('#" + id
						+ "').zTree(setting,nodes);");
			}

			ls_out.append("</script>");
		}
		if (!"".equals(ls_out)) {
			try {
				out.write(ls_out.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	/**
	 * 
	 * @Title: getSetting
	 * @Description: TODO(获取ztree的setting)
	 * @return
	 */
	private String getSetting() {
		StringBuffer setting = new StringBuffer("{");
		StringBuffer callBack = new StringBuffer(" callback : {");

		if (beforeClick != null) {
			callBack.append("beforeClick:" + beforeClick + ",");
		}
		if (beforeCollapse != null) {
			callBack.append("beforeCollapse:" + beforeCollapse + ",");
		}
		if (beforeDbclick != null) {
			callBack.append("beforeDbclick:" + beforeDbclick + ",");
		}
		if (beforeExpand != null) {
			callBack.append("beforeExpand:" + beforeExpand + ",");
		}
		if (click != null) {
			callBack.append("click:" + click + ",");
		}
		if (dblclick != null) {
			callBack.append("dblclick:" + dblclick + ",");
		}
		if (collapse != null) {
			callBack.append("collapse:" + collapse + ",");
		}
		if (expand != null) {
			callBack.append("expand:" + expand + ",");
		}
		if (rightClick != null) {
			callBack.append("rightClick:" + rightClick + ",");
		}
		if (asyncSuccess != null) {
			callBack.append("asyncSuccess:" + asyncSuccess + ",");
		}
		if (asyncError != null) {
			callBack.append("asyncError:" + asyncError + ",");
		}
		int length = callBack.length();
		if (",".equals(String.valueOf(callBack.charAt(length - 1)))) {
			callBack = callBack.deleteCharAt(length - 1);
		}
		callBack.append("}");
		if (this.isSimpleData != null && "true".equals(this.isSimpleData)) {
			setting.append("isSimpleData:" + this.isSimpleData + ",");
			setting.append("treeNodeKey:\"id\",");
			setting.append("treeNodeParentKey:\"pId\",");
		}
		if (this.async != null) {
			setting.append("async:" + this.async + ",");
		}
		if (this.asyncUrl != null) {
			setting.append("asyncUrl:" + this.asyncUrl + ",");
		}
		if (this.showIcon != null) {
			setting.append("showIcon:" + this.showIcon + ",");
		}
		if (this.showLine != null) {
			setting.append("showLine:" + this.showLine + ",");
		}
		if (checkable != null) {
			setting.append("checkable:true,");
		}
		if (setting.length() == 1) {
			setting.append("showLine: true,");
		}
		return setting.append(callBack.toString()).append("}").toString();

	}

}