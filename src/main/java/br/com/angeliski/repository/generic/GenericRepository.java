package br.com.angeliski.repository.generic;

import br.com.angeliski.dao.generic.GenericDAO;

public abstract class GenericRepository<T> {

	public void salva(T objeto) {
		getDao().salva(objeto);
	}

	public abstract GenericDAO<T> getDao();
}
