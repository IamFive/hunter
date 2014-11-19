/**
 * @(#)DictCache.java 2013年12月30日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.tag;

import java.util.*;
import java.util.Map.Entry;

import javax.annotation.*;
import javax.persistence.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

import com.google.common.collect.*;

import edu.hunter.modules.utils.*;

/**
 * A JVM-based cache for dictionary
 * 
 * @author Woo Cupid
 * @date 2013年12月30日
 * @version $Revision$
 */
// @Service(DictCache.BEAN_NAME)
// @Lazy(value = true)
public class DictCache {

	private static DictCache instance = null;

	public static final String BEAN_NAME = "DictCache";
	private static final Logger logger = LoggerFactory.getLogger(DictCache.class);

	@Autowired
	@Value("${pro.dict.tablename}")
	private String tableName;

	@Autowired
	@Value("${pro.dict.field.key}")
	private String keyField;

	@Autowired
	@Value("${pro.dict.field.display}")
	private String displayField;

	@Autowired
	@Value("${pro.dict.field.value}")
	private String valueField;

	// TODO use jdbc or mybatis?
	@PersistenceContext
	private EntityManager em;

	private HashMap<String, HashMap<String, String>> mapper = Maps.newHashMap();

	// private ReadWriteLock lock = new ReentrantReadWriteLock();

	@PostConstruct
	public void init() {
		logger.info("=== Start init Dict Cache center ===");
		String sql = "select %s, %s, %s from %s order by %s asc";
		Query getAll = em.createNativeQuery(String.format(sql, keyField, valueField, displayField, tableName,
				valueField));
		List<Object[]> result = getAll.getResultList();
		for (Object[] object : result) {
			String key = object[0].toString();
			String value = object[1].toString();
			String display = object[2].toString();
			addItem(key, value, display);
			logger.debug("add dict record : {}-{}-{}", key, value, display);
		}
		logger.info("=== Init Dict Cache center done ===");
	}

	public void reload() {
		init();
	}

	/**
	 * @param key
	 * @param value
	 * @param display
	 */
	private void addItem(String key, String value, String display) {
		if (!mapper.containsKey(key)) {
			LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
			mapper.put(key, map);
		}
		mapper.get(key).put(value, display);
	}

	public HashMap<String, String> get(String key) {
		HashMap<String, String> map = mapper.get(key);
		if (map == null) {
			this.reload();
			return mapper.get(key);
		} else {
			return map;
		}
	}

	public String getDisplay(String key, String value) {
		HashMap<String, String> map = mapper.get(key);
		if (map == null) {
			this.reload();
			map = mapper.get(key);
		}
		return map.get(value);
	}

	public String getValue(String key, String display) {
		HashMap<String, String> map = mapper.get(key);
		if (map == null) {
			this.reload();
			map = mapper.get(key);
		}

		for (Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue().equals(display)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * public ArrayList<HashMap<String, String>> getAsList(String key, String display) {
	 * HashMap<String, String> hashMap = mapper.get(key);
	 * ArrayList<HashMap<String, String>> result = Lists.newArrayList();
	 * for (Entry<String, String> entry : hashMap.entrySet()) {
	 * result.add(Maps.newHashMap());
	 * }
	 * return result;
	 * }
	 */

	public static DictCache instance() {
		if (instance == null) {
			instance = SpringContextHolder.getBean(DictCache.BEAN_NAME);
		}
		return instance;
	}
}
