/**
 * @(#)HighCharts.java 2012-4-22
 * Copyright 2000-2012 by iampurse@vip.qq.com. All rights reserved.
 */
package edu.hunter.modules.highcharts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import edu.hunter.modules.mapper.JsonMapper;
import edu.hunter.modules.utils.CC;

/**
 * a simple data model implement for high-charts
 * 
 * @author Five
 * @date 2012-4-22
 * @version $Revision$
 */
public class HighCharts {

	private static final int BEST_X_CHAR_LEN = 80;
	private List<Comparable<?>> categories = new ArrayList<Comparable<?>>();
	private List<Comparable<?>> series = new ArrayList<Comparable<?>>();
	private Map<Comparable<?>, Map<Comparable<?>, Number>> dataModel = new HashMap<Comparable<?>, Map<Comparable<?>, Number>>();

	public void setupCategory(List<Comparable<?>> category) {
		categories = category;
	}

	public void setupSeries(List<Comparable<?>> series) {
		this.series = series;
	}

	public void addData(Comparable<?> seriesName, Comparable<?> categoryName, Number value) {
		if (dataModel.get(seriesName) == null) {
			dataModel.put(seriesName, new HashMap<Comparable<?>, Number>());
		}

		dataModel.get(seriesName).put(categoryName, value);
	}

	public void addSeries(Comparable<?> seriesName, Map<? extends Comparable<?>, ? extends Number> categories) {
		if (dataModel.get(seriesName) == null) {
			dataModel.put(seriesName, new HashMap<Comparable<?>, Number>());
		}

		if (categories != null) {
			dataModel.get(seriesName).putAll(categories);
		}
	}
	
	
	public Map buildAsMap(boolean sortSeries, boolean sortCategory) {
		initModel(sortSeries, sortCategory);
		List<Map<String, Object>> optionSeries = new ArrayList<Map<String, Object>>();
		for (Comparable<?> seriesName : series) {

			// build category data list.
			List<Number> categoriesModel = new ArrayList<Number>();
			Map<Comparable<?>, Number> categoriesMapper = dataModel.get(seriesName);
			for (Comparable<?> category : categories) {
				categoriesModel.add(categoriesMapper.get(category) == null ? 0 : categoriesMapper.get(category));
			}

			// add to model
			Map<String, Object> seriesModel = new HashMap<String, Object>();
			seriesModel.put("data", categoriesModel);
			seriesModel.put("name", seriesName);
			optionSeries.add(seriesModel);
		}

		int categoriesLong = estimateCategoriesLong();
		int step = (int) (categoriesLong <= BEST_X_CHAR_LEN ? 1 : Math.ceil(categoriesLong
				/ new Double(BEST_X_CHAR_LEN)));

		return CC.newHashMap("xAxis",
				CC.newHashMap("categories", categories, "labels", CC.newHashMap("step", String.valueOf(step))),
				"series", optionSeries);
	}

	public String build(boolean sortSeries, boolean sortCategory) {
		initModel(sortSeries, sortCategory);
		List<Map<String, Object>> optionSeries = new ArrayList<Map<String, Object>>();
		for (Comparable<?> seriesName : series) {

			// build category data list.
			List<Number> categoriesModel = new ArrayList<Number>();
			Map<Comparable<?>, Number> categoriesMapper = dataModel.get(seriesName);
			for (Comparable<?> category : categories) {
				categoriesModel.add(categoriesMapper.get(category) == null ? 0 : categoriesMapper.get(category));
			}

			// add to model
			Map<String, Object> seriesModel = new HashMap<String, Object>();
			seriesModel.put("data", categoriesModel);
			seriesModel.put("name", seriesName);
			optionSeries.add(seriesModel);
		}

		int categoriesLong = estimateCategoriesLong();
		int step = (int) (categoriesLong <= BEST_X_CHAR_LEN ? 1 : Math.ceil(categoriesLong
				/ new Double(BEST_X_CHAR_LEN)));

		return JsonMapper.nonEmptyMapper().toJson(
				CC.newHashMap("xAxis",
						CC.newHashMap("categories", categories, "labels", CC.newHashMap("step", String.valueOf(step))),
						"series", optionSeries));
	}

	/**
	 * @return
	 */
	private int estimateCategoriesLong() {
		int l = 0;
		for (Comparable<?> c : categories) {
			l += c.toString().length();
		}
		return l;
	}

	/**
	 * if series is empty, we build it from data model by hand.
	 * 
	 * @param sortCategory
	 * @param sortSeries
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initModel(boolean sortSeries, boolean sortCategory) {

		if (CollectionUtils.isEmpty(series)) {
			series.addAll(dataModel.keySet());
		}

		if (CollectionUtils.isEmpty(categories)) {
			HashSet<Comparable<?>> tmp = new HashSet<Comparable<?>>();
			for (Map<Comparable<?>, Number> map : dataModel.values()) {
				tmp.addAll(map.keySet());
			}
			categories.addAll(tmp);
		}

		if (sortSeries) {
			Collections.sort((List) series);
		}

		if (sortSeries) {
			Collections.sort((List) categories);
		}
	}
}
