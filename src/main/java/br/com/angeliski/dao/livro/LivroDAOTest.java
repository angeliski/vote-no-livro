package br.com.angeliski.dao.livro;

import java.util.ArrayList;
import java.util.Queue;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.angeliski.entidades.Livro;
import br.com.angeliski.entidades.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class LivroDAOTest {

	@Mock
	private EntityManager entityManager;
	@Mock
	private TypedQuery<Livro> queryLivros;
	private LivroDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = new LivroDAO(entityManager);

		Mockito.when(entityManager.createQuery("select obj from Livro obj", Livro.class)).thenReturn(queryLivros);
		Mockito.when(queryLivros.getResultList()).thenReturn(new ArrayList<Livro>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRecuperaListaDeLivrosParaVotacao() {
		Queue<Livro> livros = dao.recuperaListaDeLivrosParaVotacao();

		Assert.assertEquals("N�mero de livros diferente do esperado", 0, livros.size());
	}

	@Test
	public void testRegistraVotos() {
		Usuario usuario = new Usuario();
		try {
			dao.registraVotos(usuario);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testRecuperarLivros() {
		Usuario usuario = new Usuario();
		usuario.setLivros(new ArrayList<Livro>());
		try {
			dao.recuperarLivros(usuario);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testAdicionarLivrosPadrao() {
		try {
			dao.adicionarLivrosPadrao();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
