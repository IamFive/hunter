/**
 * @(#)bb.java 2013年11月11日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.persistence.jpa;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.CollectionUtils;

/**
 * @author Woo Cupid
 * @date 2013年11月11日
 * @version $Revision$
 */
@NoRepositoryBean
public class GenericRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericRepository<T, ID>,
		Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);

	private final JpaEntityInformation<T, ?> entityInformation;
	private final EntityManager em;

	private Class<?> springDataRepositoryInterface;

	public Class<?> getSpringDataRepositoryInterface() {
		return springDataRepositoryInterface;
	}

	public void setSpringDataRepositoryInterface(Class<?> springDataRepositoryInterface) {
		this.springDataRepositoryInterface = springDataRepositoryInterface;
	}

	/**
	 * Creates a new {@link SimpleJpaRepository} to manage objects of the given
	 * {@link JpaEntityInformation}.
	 * 
	 * @param entityInformation
	 * @param entityManager
	 */
	public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager,
			Class<?> springDataRepositoryInterface) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.em = entityManager;
		this.springDataRepositoryInterface = springDataRepositoryInterface;
	}

	/**
	 * Creates a new {@link SimpleJpaRepository} to manage objects of the given
	 * domain type.
	 * 
	 * @param domainClass
	 * @param em
	 */
	public GenericRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getMetadata(domainClass, em), em, null);
	}

	protected Class<T> getDomainClass() {
		return entityInformation.getJavaType();
	}

	@Override
	public T findSingleByProp(String propName, Object propValue) {
		String entityName = entityInformation.getEntityName();
		String sql = MessageFormat.format("From {0} where {1} = :value", entityName, propName);
		TypedQuery<T> q = em.createQuery(sql, getDomainClass());
		q.setParameter("value", propValue);
		return q.getSingleResult();
	}

	/**
	 * find entities who's propName value is propValue TODO, 支持多属性
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	@Override
	public List<T> findByProp(String propName, Object propValue) {
		String entityName = entityInformation.getEntityName();
		String sql = MessageFormat.format("From {0} where {1} = :value", entityName, propName);
		TypedQuery<T> q = em.createQuery(sql, getDomainClass());
		q.setParameter("value", propValue);
		return q.getResultList();
	}

	/**
	 * check whether property TODO, 支持多属性
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	@Override
	public boolean exists(String propName, Object propValue) {
		return !CollectionUtils.isEmpty(this.findByProp(propName, propValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.hunter.modules.jpa.GenericRepository#findAll(java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<T> findAll(String jql, Pageable pageable) {
		TypedQuery<T> query = em.createQuery(jql, getDomainClass());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		// if (pageable.getSort() != null) {
		// CriteriaBuilder builder = em.getCriteriaBuilder();
		// CriteriaQuery<T> q = builder.createQuery(getDomainClass());
		// Root<T> root = q.from(getDomainClass());
		// query
		// }

		// TODO　QueryUtils.createCountQueryFor has bug indeed
		String countJql = getCountSql(jql);

		TypedQuery<Long> countQuery = em.createQuery(countJql, Long.class);
		Long total = QueryUtils.executeCountQuery(countQuery);

		List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();
		return new PageImpl<T>(content, pageable, total);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.hunter.modules.jpa.GenericRepository#findAllBySql(java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<T> findAllBySql(String sql, Pageable pageable) {
		Query query = em.createNativeQuery(sql, getDomainClass());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		// TODO　QueryUtils.createCountQueryFor has bug indeed
		String countSql = getCountSql(sql);
		Query countQuery = em.createNativeQuery(countSql);
		Long total = Long.parseLong(countQuery.getSingleResult().toString());

		@SuppressWarnings("unchecked")
		List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();
		return new PageImpl<T>(content, pageable, total);
	}

	/**
	 * @param sql
	 * @return
	 */
	private String getCountSql(String sql) {
		String countSql = QueryUtils.createCountQueryFor(sql);
		String countTarget = StringUtils.substringBetween(countSql, "count(", ")");
		countSql = StringUtils.replaceOnce(countSql, countTarget, "*");
		return countSql;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.hunter.modules.persistence.jpa.GenericRepository#findAvailable(java
	 * .lang.Long)
	 */
	@Override
	public T getAvailable(ID id) {
		String entityName = entityInformation.getEntityName();
		String idName = this.entityInformation.getIdAttribute().getName();
		String sql = MessageFormat.format("From {0} where {1} = :id and status = 1", entityName, idName);
		TypedQuery<T> q = em.createQuery(sql, getDomainClass());
		q.setParameter("id", id);
		return q.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.hunter.modules.persistence.jpa.GenericRepository#findSingleByProps
	 * (java.util.Map)
	 */
	@Override
	public T findSingleByProps(Map<String, Object> props) {
		String entityName = entityInformation.getEntityName();
		StringBuffer sql = new StringBuffer("From ").append(entityName);
		if (!props.isEmpty()) {
			sql.append(" where ");
			for (Entry<String, Object> entry : props.entrySet()) {
				sql.append(entry.getKey()).append(" = :").append(entry.getKey()).append(" and ");
			}
			sql.append("1=1");
		}

		TypedQuery<T> q = em.createQuery(sql.toString(), getDomainClass());
		for (Entry<String, Object> entry : props.entrySet()) {
			q.setParameter(entry.getKey(), entry.getValue());
		}
		return q.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.hunter.modules.persistence.jpa.GenericRepository#findByProps(java
	 * .util.Map)
	 */
	@Override
	public List<T> findByProps(Map<String, Object> props) {
		String entityName = entityInformation.getEntityName();
		StringBuffer sql = new StringBuffer("From ").append(entityName);
		if (!props.isEmpty()) {
			sql.append(" where ");
			for (Entry<String, Object> entry : props.entrySet()) {
				sql.append(entry.getKey()).append(" = :").append(StringUtils.remove(entry.getKey(), ".")).append(" and ");
			}
			sql.append("1 = 1");
		}

		TypedQuery<T> q = em.createQuery(sql.toString(), getDomainClass());
		for (Entry<String, Object> entry : props.entrySet()) {
			q.setParameter(StringUtils.remove(entry.getKey(), "."), entry.getValue());
		}
		return q.getResultList();
	}
}