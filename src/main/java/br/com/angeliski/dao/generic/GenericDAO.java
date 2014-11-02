package br.com.angeliski.dao.generic;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.angeliski.entidades.Livro;

public abstract class GenericDAO<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4200811056270615330L;
	@Inject
	private EntityManager entityManager;

	/**
	 * @deprecated CDI eyes only
	 */
	protected GenericDAO() {
		this(null);
	}

	@Inject
	public GenericDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void salva(T objeto) {
		entityManager.persist(objeto);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PostConstruct
	public void init() {
		prepararBancoDeDados();
	}

	private void prepararBancoDeDados() {
		Long count = entityManager.createQuery("select count(obj.id) from Livro obj", Long.class).getSingleResult();
		if (!count.equals(5L)) {
			entityManager.createQuery("delete from Livro where 1=1").executeUpdate();
			adicionarLivrosPadrao();
		}
	}

	public void adicionarLivrosPadrao() {
		Livro livro = new Livro();
		livro.setNome("Dragões de Éter");
		livro.setUrl("resources/image/livro1.jpg");
		entityManager.persist(livro);

		livro = new Livro();
		livro.setNome("Harry Potter - As Reliquias da Morte");
		livro.setUrl("resources/image/livro2.jpg");
		entityManager.persist(livro);

		livro = new Livro();
		livro.setNome("O Mundo de Sofia");
		livro.setUrl("resources/image/livro3.jpg");
		entityManager.persist(livro);

		livro = new Livro();
		livro.setNome("Dom Casmurro");
		livro.setUrl("resources/image/livro4.jpg");
		entityManager.persist(livro);

		livro = new Livro();
		livro.setNome("O Guia do Mochileiro das Galáxias");
		livro.setUrl("resources/image/livro5.jpg");
		entityManager.persist(livro);

		entityManager.flush();
	}
}
