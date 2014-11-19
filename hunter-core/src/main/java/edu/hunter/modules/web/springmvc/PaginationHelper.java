/**
 * @(#)PaginateHelper.java 2013年11月7日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import edu.hunter.modules.persistence.jpa.DynamicSpecifications;
import edu.hunter.modules.persistence.jpa.SearchFilter;
import edu.hunter.modules.persistence.jpa.SimpleOffsetPageable;
import edu.hunter.modules.web.Servlets;

/**
 * @author Woo Cupid
 * @date 2013年11月7日
 * @version $Revision$
 */
public class PaginationHelper {

	private static final Logger logger = LoggerFactory.getLogger(PaginationHelper.class);

	public static final String ATTR_PAGINATION = "_pagination_";
	public static final String PAGINATION_ATTRNAME_PAGE = "page";
	public static final String PAGINATION_ATTRNAME_PAGESIZE = "pagesize";
	public static final String PAGINATION_ATTRNAME_SORT = "sortby";

	public static final String PAGINATION_ATTRNAME_OFFSET = "offset";
	public static final String PAGINATION_ATTRNAME_SIZE = "size";

	@Autowired
	private HttpServletRequest context;

	/**
	 * @param sortby
	 * @return
	 */
	private Sort buildSortBy(String sortby) {
		Sort sort = null;
		if (StringUtils.isNotBlank(sortby)) {
			String[] split = StringUtils.split(sortby, ",");
			for (String s : split) {
				boolean isDesc = StringUtils.startsWith(s, "-");
				String field = isDesc ? StringUtils.removeStart(s, "-") : s;
				if (sort == null) {
					sort = new Sort(isDesc ? Direction.DESC : Direction.ASC, field);
				} else {
					sort = sort.and(new Sort(isDesc ? Direction.DESC : Direction.ASC, field));
				}
			}
		}

		return sort;
	}

	public PageRequest getPagination(String sortby) {

		String page = StringUtils.defaultString(context.getParameter(PAGINATION_ATTRNAME_PAGE), "1");
		String pageSize = StringUtils.defaultString(context.getParameter(PAGINATION_ATTRNAME_PAGESIZE), "10");

		PageRequest pageRequest = null;
		Sort sort = buildSortBy(sortby);
		if (sort != null) {
			pageRequest = new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(pageSize), sort);
		} else {
			pageRequest = new PageRequest(Integer.parseInt(page) - 1, Integer.parseInt(pageSize));
		}

		return pageRequest;
	}

	public PageRequest getPagination() {
		String sortby = context.getParameter(PAGINATION_ATTRNAME_SORT);
		return getPagination(sortby);
	}

	public Pageable getOffset(String sortby) {

		String offset = StringUtils.defaultString(context.getParameter(PAGINATION_ATTRNAME_OFFSET), "0");
		String size = StringUtils.defaultString(context.getParameter(PAGINATION_ATTRNAME_SIZE), "10");

		Pageable pr = null;
		Sort sort = buildSortBy(sortby);
		if (sort != null) {
			pr = new SimpleOffsetPageable(Integer.parseInt(offset), Integer.parseInt(size), sort);
		} else {
			pr = new SimpleOffsetPageable(Integer.parseInt(offset), Integer.parseInt(size));
		}

		return pr;
	}

	public Pageable getOffset() {
		String sortby = context.getParameter(PAGINATION_ATTRNAME_SORT);
		return getOffset(sortby);
	}

	public <T> Specification<T> getSpec(Class<T> entity) {
		Map<String, Object> params = Servlets.getParametersStartingWith(context, "search_");
		Map<String, SearchFilter> filters = SearchFilter.parse(params);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entity);
		return spec;
	}

	public <T> Specification<T> getSpecWithActivedStatus(Class<T> entity) {
		Map<String, Object> params = Servlets.getParametersStartingWith(context, "search_");
		params.put("EQ_status", 1);
		Map<String, SearchFilter> filters = SearchFilter.parse(params);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entity);
		return spec;
	}

}
