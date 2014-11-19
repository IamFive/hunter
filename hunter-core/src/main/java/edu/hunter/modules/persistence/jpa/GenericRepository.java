/**
 * @(#)aa.java 2013年11月11日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.persistence.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Woo Cupid
 * @date 2013年11月11日
 * @version $Revision$
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	@Lock(LockModeType.READ)
	public Page<T> findAll(String jpql, Pageable pageable);

	@Lock(LockModeType.READ)
	public Page<T> findAllBySql(String sql, Pageable pageable);

	@Lock(LockModeType.READ)
	public T findSingleByProp(String propName, Object propValue);

	@Lock(LockModeType.READ)
	public List<T> findByProp(String propName, Object propValue);

	@Lock(LockModeType.READ)
	public T findSingleByProps(Map<String, Object> props);

	@Lock(LockModeType.READ)
	public List<T> findByProps(Map<String, Object> props);

	@Lock(LockModeType.READ)
	public boolean exists(String propName, Object propValue);

	/**
	 * find entity with status 1 and identify id
	 * 
	 * @param id
	 * @return
	 */
	@Lock(LockModeType.READ)
	public T getAvailable(ID id);
}