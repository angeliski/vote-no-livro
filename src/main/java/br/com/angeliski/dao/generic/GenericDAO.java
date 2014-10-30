package br.com.angeliski.dao.generic;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class GenericDAO<T> {
	@Inject
	private EntityManager entityManager;

	public void salva(T objeto) {
		entityManager.persist(objeto);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
