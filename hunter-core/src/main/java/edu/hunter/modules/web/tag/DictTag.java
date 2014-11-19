package edu.hunter.modules.web.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.hunter.modules.freemarker.FreeMarkerHelper;
import edu.hunter.modules.utils.SpringContextHolder;

public class DictTag extends SelectTag {

	private static final Logger logger = LoggerFactory.getLogger(DictTag.class);

	private static final long serialVersionUID = 1L;
	protected String key;
	protected String start;
	protected String end;

	@Override
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
		DictCache dictCache = SpringContextHolder.getBean(DictCache.class);
		HashMap<String, String> values = dictCache.get(key);
		if (null==values||values.size()<=0) {
			logger.warn("dict with key [{}] is not configurated", key);
			values = Maps.newHashMap();
		}

		Map<String, String> items = Maps.filterKeys(values, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				boolean available = true;
				if (StringUtils.isNotBlank(start)) {
					available = available && (start.compareTo(input) < 0);
				}

				if (StringUtils.isNotBlank(end)) {
					available = available && (end.compareTo(input) > 0);
				}

				return available;
			}
		});

		this.setList(convertToList(items));
		JspWriter writer = this.pageContext.getOut();
		Map<String, Object> map = Maps.newHashMap();
		map.put("Component", this);
		FreeMarkerHelper.process("select.ftl", map, writer);
		return EVAL_PAGE;
	}

	/**
	 * @param items
	 * @return
	 */
	private List<Map<String, String>> convertToList(Map<String, String> items) {
		List<Map<String, String>> converted = Lists.newArrayList();
		for (Entry<String, String> entry : items.entrySet()) {
			converted
					.add(ImmutableMap.<String, String> of("paramValue", entry.getKey(), "paramText", entry.getValue()));
		}
		return converted;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
