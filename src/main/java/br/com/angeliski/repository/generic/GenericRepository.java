package br.com.angeliski.repository.generic;

import java.io.Serializable;

import br.com.angeliski.dao.generic.GenericDAO;

public abstract class GenericRepository<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120203069031507950L;

	public void salva(T objeto) {
		getDao().salva(objeto);
	}

	public abstract GenericDAO<T> getDao();
}
