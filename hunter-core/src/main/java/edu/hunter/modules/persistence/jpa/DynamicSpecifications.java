package edu.hunter.modules.persistence.jpa;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;

import edu.hunter.modules.common.util.AppUtils;
import edu.hunter.modules.conversion.ConversionService;
import edu.hunter.modules.utils.Collections3;

public class DynamicSpecifications {

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
		return new Specification<T>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (Collections3.isNotEmpty(filters)) {

					List<Predicate> predicates = Lists.newArrayList();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName,
						// 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, ".");

						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						if (!(filter.value instanceof String[])
								&& ConversionService.canConvert(filter.value.getClass(), expression.getJavaType())) {
							filter.value = ConversionService.convert(filter.value, expression.getJavaType());
						}

						// logic operator
						switch (filter.operator) {
						case NE:
							predicates.add(builder.notEqual(expression, filter.value));
							break;
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							if (AppUtils.isNotEmpty(filter.value) && filter.value instanceof String) {
								predicates.add(builder.like(expression, "%" + ((String) filter.value).trim() + "%"));
							} else {
								predicates.add(builder.like(expression, "%" + filter.value + "%"));
							}
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case IN:
							if (filter.value instanceof String[]) {
								predicates.add(builder.in(expression).value(Arrays.asList(((String[]) filter.value))));
							} else {
								// if only one element, convert to equal
								// expression
								predicates.add(builder.equal(expression, filter.value));
							}
							break;
						default:
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
}
