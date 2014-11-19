package edu.hunter.modules.web.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import edu.hunter.modules.freemarker.FreeMarkerHelper;
import edu.hunter.modules.utils.SpringContextHolder;

public class SelectTag extends TagSupport {

	protected static final long serialVersionUID = -85148664789201973L;

	protected String name;
	protected String paramText;
	protected String paramValue;
	protected String entity;
	protected String condition;
	protected String style;
	protected String value;
	protected boolean blank;
	protected String blankTxt;

	protected List<Map<String, String>> list;

	@Override
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT new map(").append(paramText).append(" AS paramText").append(",").append(paramValue)
				.append(" AS paramValue) FROM ").append(entity);
		if (StringUtils.isNotBlank(condition)) {
			sql.append(" WHERE ").append(condition);
		}

		EntityManagerFactory bean = SpringContextHolder.getBean(EntityManagerFactory.class);
		EntityManager em = null;
		try {
			em = bean.createEntityManager();
			List result = em.createQuery(sql.toString()).getResultList();

			this.setList(result);
			JspWriter writer = this.pageContext.getOut();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Component", this);
			FreeMarkerHelper.process("select.ftl", map, writer);
			return EVAL_PAGE;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamText() {
		return paramText;
	}

	public void setParamText(String paramText) {
		this.paramText = paramText;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isBlank() {
		return blank;
	}

	public void setBlank(boolean blank) {
		this.blank = blank;
	}

	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	/**
	 * @return the blankTxt
	 */
	public String getBlankTxt() {
		return blankTxt;
	}

	/**
	 * @param blankTxt the blankTxt to set
	 */
	public void setBlankTxt(String blankTxt) {
		this.blankTxt = blankTxt;
	}

}
