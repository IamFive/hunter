/**
 * @(#)StringToDateConverter.java 2013年12月5日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.conversion.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

/**
 * @author Woo Cupid
 * @date 2013年12月5日
 * @version $Revision$
 */
public class CopyOfStringToDateConverter implements ConditionalGenericConverter {

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(String.class, Date.class));
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (sourceType.getType().equals(String.class) && targetType.getType().isAssignableFrom(Date.class)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.core.convert.converter.GenericConverter#convert(java.lang.Object,
	 * org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)
	 */
	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		return doConvertToDate(source, Locale.CHINA);
	}

	private DateFormat[] getDateFormats(Locale locale) {
		DateFormat ls = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat ss = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat dt1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale);
		DateFormat dt2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale);
		DateFormat dt3 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

		DateFormat d1 = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		DateFormat d2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		DateFormat d3 = DateFormat.getDateInstance(DateFormat.LONG, locale);

		DateFormat rfc3399 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return new DateFormat[] { ls, ss, dt1, dt2, dt3, rfc3399, d1, d2, d3 };
	}

	private Date doConvertToDate(Object value, Locale locale) {
		Date result = null;
		if (value instanceof String) {
			DateFormat[] dfs = getDateFormats(locale);
			for (DateFormat df1 : dfs) {
				try {
					result = df1.parse(value.toString());
					if (result != null) {
						break;
					}
				} catch (ParseException ignore) {
				}
			}
		} else if (value instanceof Object[]) {
			// let's try to convert the first element only
			Object[] array = (Object[]) value;
			if (array.length >= 1) {
				Object v = array[0];
				result = doConvertToDate(v, locale);
			}
		} else if (Date.class.isAssignableFrom(value.getClass())) {
			result = (Date) value;
		}

		return result;
	}

}
