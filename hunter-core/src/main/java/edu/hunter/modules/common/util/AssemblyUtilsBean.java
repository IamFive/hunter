/**   
 * @Title：AssemblyUtilsBean.java 
 * @Package：edu.hunter.modules.common.util 
 * @Description：
 * @author：ShiSongBin
 * @date：2013-11-14 上午10:47:55 
 * @version：V1.0   
 */

package edu.hunter.modules.common.util;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MappedPropertyDescriptor;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.expression.Resolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName：AssemblyUtilsBean
 * @Description：
 * @author：ShiSongBin
 * @date：2013-11-14 上午10:47:55
 * 
 */

public class AssemblyUtilsBean {

	private static final Logger logger = LoggerFactory.getLogger(AssemblyUtilsBean.class);

	private static final ContextClassLoaderLocal BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal() {
		// Creates the default instance used when the context classloader is
		// unavailable
		@Override
		protected Object initialValue() {
			return new AssemblyUtilsBean();
		}
	};

	public static AssemblyUtilsBean getInstance() {
		return (AssemblyUtilsBean) BEANS_BY_CLASSLOADER.get();
	}

	public static void setInstance(AssemblyUtilsBean newInstance) {
		BEANS_BY_CLASSLOADER.set(newInstance);
	}

	private final ConvertUtilsBean convertUtilsBean;

	private final PropertyUtilsBean propertyUtilsBean;

	private static final Method INIT_CAUSE_METHOD = getInitCauseMethod();

	public AssemblyUtilsBean() {
		this(new ConvertUtilsBean(), new PropertyUtilsBean());
	}

	public AssemblyUtilsBean(ConvertUtilsBean convertUtilsBean) {
		this(convertUtilsBean, new PropertyUtilsBean());
	}

	public AssemblyUtilsBean(ConvertUtilsBean convertUtilsBean, PropertyUtilsBean propertyUtilsBean) {

		this.convertUtilsBean = convertUtilsBean;
		this.propertyUtilsBean = propertyUtilsBean;
	}

	public Object cloneBean(Object bean) throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {

		if (logger.isDebugEnabled()) {
			logger.debug("Cloning bean: " + bean.getClass().getName());
		}
		Object newBean = null;
		if (bean instanceof DynaBean) {
			newBean = ((DynaBean) bean).getDynaClass().newInstance();
		} else {
			newBean = bean.getClass().newInstance();
		}
		getPropertyUtils().copyProperties(newBean, bean);
		return (newBean);

	}

	public void copyProperties(Object dest, Object orig) {

		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (DynaProperty origDescriptor : origDescriptors) {
				String name = origDescriptor.getName();

				if (getPropertyUtils().isReadable(orig, name) && getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					if (value != null) {
						try {
							copyProperty(dest, name, value);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if ((entry.getValue() != null) && (name != null)) {
					if (getPropertyUtils().isWriteable(dest, name)) {
						try {
							copyProperty(dest, name, entry.getValue());
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(orig);
			for (PropertyDescriptor origDescriptor : origDescriptors) {
				String name = origDescriptor.getName();
				if ("class".equals(name)) {
					continue;
				}
				if (getPropertyUtils().isReadable(orig, name) && getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = null;
						try {
							value = getPropertyUtils().getSimpleProperty(orig, name);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (value != null) {
							try {
								copyProperty(dest, name, value);
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (NoSuchMethodException e) {

					}
				}
			}
		}

	}

	public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException,
			InvocationTargetException {

		if (logger.isTraceEnabled()) {
			StringBuffer sb = new StringBuffer("  copyProperty(");
			sb.append(bean);
			sb.append(", ");
			sb.append(name);
			sb.append(", ");
			if (value == null) {
				sb.append("<NULL>");
			} else if (value instanceof String) {
				sb.append((String) value);
			} else if (value instanceof String[]) {
				String[] values = (String[]) value;
				sb.append('[');
				for (int i = 0; i < values.length; i++) {
					if (i > 0) {
						sb.append(',');
					}
					sb.append(values[i]);
				}
				sb.append(']');
			} else {
				sb.append(value.toString());
			}
			sb.append(')');
			logger.trace(sb.toString());
		}

		Object target = bean;
		Resolver resolver = getPropertyUtils().getResolver();
		while (resolver.hasNested(name)) {
			try {
				target = getPropertyUtils().getProperty(target, resolver.next(name));
				name = resolver.remove(name);
			} catch (NoSuchMethodException e) {
				return;
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace("    Target bean = " + target);
			logger.trace("    Target name = " + name);
		}

		String propName = resolver.getProperty(name);

		Class type = null;
		int index = resolver.getIndex(name);
		String key = resolver.getKey(name);

		if (target instanceof DynaBean) {
			DynaClass dynaClass = ((DynaBean) target).getDynaClass();
			DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
			if (dynaProperty == null) {
				return;
			}
			type = dynaProperty.getType();
		} else {
			PropertyDescriptor descriptor = null;
			try {
				descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
				if (descriptor == null) {
					return;
				}
			} catch (NoSuchMethodException e) {
				return;
			}
			type = descriptor.getPropertyType();
			if (type == null) {
				if (logger.isTraceEnabled()) {
					logger.trace("    target type for property '" + propName + "' is null, so skipping ths setter");
				}
				return;
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace("    target propName=" + propName + ", type=" + type + ", index=" + index + ", key=" + key);
		}

		if (index >= 0) {
			value = convert(value, type.getComponentType());
			try {
				getPropertyUtils().setIndexedProperty(target, propName, index, value);
			} catch (NoSuchMethodException e) {
				throw new InvocationTargetException(e, "Cannot set " + propName);
			}
		} else if (key != null) {
			try {
				getPropertyUtils().setMappedProperty(target, propName, key, value);
			} catch (NoSuchMethodException e) {
				throw new InvocationTargetException(e, "Cannot set " + propName);
			}
		} else {
			value = convert(value, type);
			try {
				getPropertyUtils().setSimpleProperty(target, propName, value);
			} catch (NoSuchMethodException e) {
				throw new InvocationTargetException(e, "Cannot set " + propName);
			}
		}

	}

	public String[] getArrayProperty(Object bean, String name) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		Object value = getPropertyUtils().getProperty(bean, name);
		if (value == null) {
			return (null);
		} else if (value instanceof Collection) {
			ArrayList values = new ArrayList();
			Iterator items = ((Collection) value).iterator();
			while (items.hasNext()) {
				Object item = items.next();
				if (item == null) {
					values.add((String) null);
				} else {
					// convert to string using convert utils
					values.add(getConvertUtils().convert(item));
				}
			}
			return ((String[]) values.toArray(new String[values.size()]));
		} else if (value.getClass().isArray()) {
			int n = Array.getLength(value);
			String[] results = new String[n];
			for (int i = 0; i < n; i++) {
				Object item = Array.get(value, i);
				if (item == null) {
					results[i] = null;
				} else {
					// convert to string using convert utils
					results[i] = getConvertUtils().convert(item);
				}
			}
			return (results);
		} else {
			String[] results = new String[1];
			results[0] = getConvertUtils().convert(value);
			return (results);
		}

	}

	public String getIndexedProperty(Object bean, String name) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		Object value = getPropertyUtils().getIndexedProperty(bean, name);
		return (getConvertUtils().convert(value));

	}

	public String getIndexedProperty(Object bean, String name, int index) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		Object value = getPropertyUtils().getIndexedProperty(bean, name, index);
		return (getConvertUtils().convert(value));

	}

	public String getMappedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Object value = getPropertyUtils().getMappedProperty(bean, name);
		return (getConvertUtils().convert(value));

	}

	public String getMappedProperty(Object bean, String name, String key) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		Object value = getPropertyUtils().getMappedProperty(bean, name, key);
		return (getConvertUtils().convert(value));

	}

	public String getNestedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Object value = getPropertyUtils().getNestedProperty(bean, name);
		return (getConvertUtils().convert(value));

	}

	public String getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		return (getNestedProperty(bean, name));

	}

	public String getSimpleProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Object value = getPropertyUtils().getSimpleProperty(bean, name);
		return (getConvertUtils().convert(value));

	}

	public void populate(Object bean, Map properties) throws IllegalAccessException, InvocationTargetException {

		if ((bean == null) || (properties == null)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("BeanUtils.populate(" + bean + ", " + properties + ")");
		}

		Iterator entries = properties.entrySet().iterator();
		while (entries.hasNext()) {

			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			if (name == null) {
				continue;
			}

			setProperty(bean, name, entry.getValue());

		}

	}

	public void setProperty(Object bean, String name, Object value) throws IllegalAccessException,
			InvocationTargetException {

		if (logger.isTraceEnabled()) {
			StringBuffer sb = new StringBuffer("  setProperty(");
			sb.append(bean);
			sb.append(", ");
			sb.append(name);
			sb.append(", ");
			if (value == null) {
				sb.append("<NULL>");
			} else if (value instanceof String) {
				sb.append((String) value);
			} else if (value instanceof String[]) {
				String[] values = (String[]) value;
				sb.append('[');
				for (int i = 0; i < values.length; i++) {
					if (i > 0) {
						sb.append(',');
					}
					sb.append(values[i]);
				}
				sb.append(']');
			} else {
				sb.append(value.toString());
			}
			sb.append(')');
			logger.trace(sb.toString());
		}

		Object target = bean;
		Resolver resolver = getPropertyUtils().getResolver();
		while (resolver.hasNested(name)) {
			try {
				target = getPropertyUtils().getProperty(target, resolver.next(name));
				name = resolver.remove(name);
			} catch (NoSuchMethodException e) {
				return;
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace("    Target bean = " + target);
			logger.trace("    Target name = " + name);
		}

		String propName = resolver.getProperty(name);

		Class type = null;
		int index = resolver.getIndex(name);
		String key = resolver.getKey(name);

		if (target instanceof DynaBean) {
			DynaClass dynaClass = ((DynaBean) target).getDynaClass();
			DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
			if (dynaProperty == null) {
				return;
			}
			type = dynaProperty.getType();
		} else if (target instanceof Map) {
			type = Object.class;
		} else if ((target != null) && target.getClass().isArray() && (index >= 0)) {
			type = Array.get(target, index).getClass();
		} else {
			PropertyDescriptor descriptor = null;
			try {
				descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
				if (descriptor == null) {
					return;
				}
			} catch (NoSuchMethodException e) {
				return;
			}
			if (descriptor instanceof MappedPropertyDescriptor) {
				if (((MappedPropertyDescriptor) descriptor).getMappedWriteMethod() == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping read-only property");
					}
					return;
				}
				type = ((MappedPropertyDescriptor) descriptor).getMappedPropertyType();
			} else if ((index >= 0) && (descriptor instanceof IndexedPropertyDescriptor)) {
				if (((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping read-only property");
					}
					return;
				}
				type = ((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType();
			} else if (key != null) {
				if (descriptor.getReadMethod() == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping read-only property");
					}
					return;
				}
				type = (value == null) ? Object.class : value.getClass();
			} else {
				if (descriptor.getWriteMethod() == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping read-only property");
					}
					return;
				}
				type = descriptor.getPropertyType();
			}
		}
		Object newValue = null;
		if (type.isArray() && (index < 0)) {
			if (value == null) {
				String[] values = new String[1];
				values[0] = null;
				newValue = getConvertUtils().convert(values, type);
			} else if (value instanceof String) {
				newValue = getConvertUtils().convert(value, type);
			} else if (value instanceof String[]) {
				newValue = getConvertUtils().convert((String[]) value, type);
			} else {
				newValue = convert(value, type);
			}
		} else if (type.isArray()) {
			if ((value instanceof String) || (value == null)) {
				newValue = getConvertUtils().convert((String) value, type.getComponentType());
			} else if (value instanceof String[]) {
				newValue = getConvertUtils().convert(((String[]) value)[0], type.getComponentType());
			} else {
				newValue = convert(value, type.getComponentType());
			}
		} else {
			if (value instanceof String) {
				newValue = getConvertUtils().convert((String) value, type);
			} else if (value instanceof String[]) {
				newValue = getConvertUtils().convert(((String[]) value)[0], type);
			} else {
				newValue = convert(value, type);
			}
		}

		try {
			getPropertyUtils().setProperty(target, name, newValue);
		} catch (NoSuchMethodException e) {
			throw new InvocationTargetException(e, "Cannot set " + propName);
		}

	}

	public ConvertUtilsBean getConvertUtils() {
		return convertUtilsBean;
	}

	public PropertyUtilsBean getPropertyUtils() {
		return propertyUtilsBean;
	}

	public boolean initCause(Throwable throwable, Throwable cause) {
		if ((INIT_CAUSE_METHOD != null) && (cause != null)) {
			try {
				INIT_CAUSE_METHOD.invoke(throwable, new Object[] { cause });
				return true;
			} catch (Throwable e) {
				return false; // can't initialize cause
			}
		}
		return false;
	}

	protected Object convert(Object value, Class type) {
		Converter converter = getConvertUtils().lookup(type);
		if (converter != null) {
			logger.trace("        USING CONVERTER " + converter);
			return converter.convert(type, value);
		} else {
			return value;
		}
	}

	private static Method getInitCauseMethod() {
		try {
			Class[] paramsClasses = new Class[] { Throwable.class };
			return Throwable.class.getMethod("initCause", paramsClasses);
		} catch (NoSuchMethodException e) {
			Logger logger = LoggerFactory.getLogger(BeanUtils.class);
			if (logger.isWarnEnabled()) {
				logger.warn("Throwable does not have initCause() method in JDK 1.3");
			}
			return null;
		} catch (Throwable e) {
			Logger logger = LoggerFactory.getLogger(BeanUtils.class);
			if (logger.isWarnEnabled()) {
				logger.warn("Error getting the Throwable initCause() method", e);
			}
			return null;
		}
	}
}
