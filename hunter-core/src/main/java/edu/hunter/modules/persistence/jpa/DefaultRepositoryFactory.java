/**
 * @(#)cc.java 2013年11月11日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.persistence.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * @author Woo Cupid
 * @date 2013年11月11日
 * @version $Revision$
 */
public class DefaultRepositoryFactory extends JpaRepositoryFactory {

	@SuppressWarnings("unused")
	private final EntityManager entityManager;

	public DefaultRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	protected <T, ID extends Serializable> JpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {

		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

		if (isQueryDslExecutor(repositoryInterface)) {
			return new QueryDslJpaRepository(entityInformation, entityManager);
		} else {
			return new GenericRepositoryImpl(entityInformation, entityManager, repositoryInterface); // custom
																										// implementation
		}
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
			return QueryDslJpaRepository.class;
		} else {
			return GenericRepositoryImpl.class;
		}
	}

	/**
	 * Returns whether the given repository interface requires a QueryDsl
	 * specific implementation to be chosen.
	 * 
	 * @param repositoryInterface
	 * @return
	 */
	private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
		return QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
	}
}