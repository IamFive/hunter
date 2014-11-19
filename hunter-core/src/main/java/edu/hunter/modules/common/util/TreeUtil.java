package edu.hunter.modules.common.util;

public class TreeUtil {

	private String nodeId;

	private String parentId;

	private String nodeUrl;

	private String nodeTitle;

	private String isSelected; // 是否选择 0 否 1 是

	private String menu;

	private String pmenu;

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getPmenu() {
		return pmenu;
	}

	public void setPmenu(String pmenu) {
		this.pmenu = pmenu;
	}

	public TreeUtil() {
	}

	public TreeUtil(String nodeId, String parentId, String nodeUrl, String nodeTitle) {
		super();
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.nodeUrl = nodeUrl;
		this.nodeTitle = nodeTitle;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

	public String getNodeTitle() {
		return nodeTitle;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

}