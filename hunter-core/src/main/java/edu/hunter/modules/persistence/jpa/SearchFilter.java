package edu.hunter.modules.persistence.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import edu.hunter.modules.utils.Collections3;

public class SearchFilter {

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, IN, NE,SPACE;
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();

			if ((value == null)) {
				// value wont be null here indeed
				continue;
			}

			// should we force it to be string?
			boolean isPrimitive = value.getClass().isPrimitive()
					|| (ClassUtils.wrapperToPrimitive(value.getClass()) != null);
			boolean isValidString = (value instanceof String) && StringUtils.isNotBlank((String) value);
			boolean isValidList = (value instanceof String[]) && (((String[]) value).length > 0);

			if (isValidString || isValidList || isPrimitive) {
				String[] names = StringUtils.split(key, "_");
				if (names.length < 2) {
					continue;
					// throw new IllegalArgumentException(key
					// + " is not a valid search filter name for string value, need to add operator.");
				}

				String filedName = null;
				if (names.length == 2) {
					filedName = names[1];
				} else {
					List<String> list = Arrays.asList(names).subList(1, names.length);
					filedName = Collections3.convertToString(list, ".");
				}

				Operator operator = Operator.valueOf(names[0].toUpperCase());
				SearchFilter filter = new SearchFilter(filedName, operator, value);
				filters.put(key, filter);
			}
		}

		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchFilter [fieldName=" + fieldName + ", value=" + value + ", operator=" + operator + "]";
	}

}
