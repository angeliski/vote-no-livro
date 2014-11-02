package br.com.angeliski.dao.generic;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class GenericDAO<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4200811056270615330L;
	@Inject
	private EntityManager entityManager;

	public void salva(T objeto) {
		entityManager.persist(objeto);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
