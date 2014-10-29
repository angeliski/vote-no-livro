package br.com.angeliski.tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.angeliski.entidades.Livro;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceTest {

	private EntityManager entityManager;

	@Before
	public void before() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
		entityManager = factory.createEntityManager();

		adicionarLivrosAoBanco(5);
	}

	private void adicionarLivrosAoBanco(int quantidade) {
		entityManager.getTransaction().begin();

		for (int i = 0; i < quantidade; i++) {
			Livro livro = new Livro();
			livro.setNome("Livro " + i);
			entityManager.persist(livro);
		}
		entityManager.flush();
		entityManager.getTransaction().commit();

	}

	@Test
	public void entityManagerTest() {

		TypedQuery<Livro> query = entityManager.createQuery("from Livro", Livro.class);
		List<Livro> livros = query.getResultList();

		Assert.assertEquals("Quantidade de livros necessária não cadastrada", 5, livros.size());
	}

}
