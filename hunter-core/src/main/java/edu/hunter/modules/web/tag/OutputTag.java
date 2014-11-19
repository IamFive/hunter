package edu.hunter.modules.web.tag;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hunter.modules.utils.SpringContextHolder;

public class OutputTag extends TagSupport {

	private static final long serialVersionUID = 2275383657293444605L;

	private static final Logger logger = LoggerFactory.getLogger(OutputTag.class);

	private String paramText;
	private String paramValue;
	private String entity;
	private String value;

	@Override
	public int doEndTag() throws JspException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(paramText).append(" FROM ").append(entity).append(" WHERE ").append(paramValue)
				.append(" = ").append(value);

		EntityManagerFactory bean = SpringContextHolder.getBean(EntityManagerFactory.class);
		EntityManager em = null;
		try {

			em = bean.createEntityManager();
//			Object result = em.createQuery(sql.toString()).getSingleResult();
			Object result = "";
			List list = em.createQuery(sql.toString()).getResultList();
			
			if(list!=null && list.size()>0){
				result = list.get(0);
			}
			
			JspWriter writor = this.pageContext.getOut();
			String out = "";
			if (result != null) {
				out = result.toString();
			}

			try {
				writor.write(out);
			} catch (IOException e) {
				logger.warn("Error occur in diaply tag.", e);
			}

			return EVAL_PAGE;
		} finally {
			if (em != null) {
				em.close();
			}
		}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
