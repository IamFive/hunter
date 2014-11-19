/**
 * @(#)SearchFilterTest.java 2013年11月8日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.test.search;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Maps;

import edu.hunter.modules.persistence.jpa.DynamicSpecifications;
import edu.hunter.modules.persistence.jpa.SearchFilter;
import edu.hunter.modules.test.BaseTester;

/**
 * @author Woo Cupid
 * @date 2013年11月8日
 * @version $Revision$
 */
public class SearchFilterT extends BaseTester<SearchFilterT> {

	@Test
	public void testAsList() {
		List<String> asList = Arrays.asList(new String[] { "a", "b" });
		assertEquals(asList.size(), 2);
	}

	@Test
	public void testParse() {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("a", new String[] { "a1", "a2" });
		searchParams.put("EQ_b", "b");
		Map<String, SearchFilter> parsed = SearchFilter.parse(searchParams);

		logger.info("{}", parsed);
	}

	@Test
	public void testGetSpec() {

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("prop", new String[] { "a1", "a2" });
		searchParams.put("EQ_propTwo", "b");
		Map<String, SearchFilter> parsed = SearchFilter.parse(searchParams);

		Specification<TestEntity> bySearchFilter = DynamicSpecifications.bySearchFilter(parsed.values(), TestEntity.class);
		logger.info("{}", bySearchFilter);
	}

	@Entity
	@Table()
	public class TestEntity {

		public String prop;
		public String propTwo;

		/**
		 * @return the prop
		 */
		public String getProp() {
			return prop;
		}

		/**
		 * @param prop
		 *            the prop to set
		 */
		public void setProp(String prop) {
			this.prop = prop;
		}

		/**
		 * @return the propTwo
		 */
		public String getPropTwo() {
			return propTwo;
		}

		/**
		 * @param propTwo
		 *            the propTwo to set
		 */
		public void setPropTwo(String propTwo) {
			this.propTwo = propTwo;
		}

	}

}
