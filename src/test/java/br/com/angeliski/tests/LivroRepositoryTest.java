package br.com.angeliski.tests;

import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.angeliski.dao.livro.LivroDAO;
import br.com.angeliski.entidades.Livro;
import br.com.angeliski.entidades.Usuario;
import br.com.angeliski.repository.livro.LivroRepository;

@RunWith(MockitoJUnitRunner.class)
public class LivroRepositoryTest {

	@Mock
	private LivroDAO dao;

	private LivroRepository repository;

	@Before
	public void setUp() throws Exception {
		Queue<Livro> livros = new PriorityQueue<Livro>();
		livros.add(new Livro());
		livros.add(new Livro());

		Mockito.when(dao.recuperaListaDeLivrosParaVotacao()).thenReturn(livros);

		repository = new LivroRepository(dao);
	}

	@After
	public void tearDown() throws Exception {
		repository = null;
	}

	@Test
	public void testRecuperaListaDeLivrosParaVotacao() {
		Queue<Livro> livros = repository.recuperaListaDeLivrosParaVotacao();

		Assert.assertNotNull("A lista de livros não foi carregada.", livros);
		Assert.assertEquals("Não foi retornando o numero de livros esperado", 2, livros.size());
	}

	@Test
	public void testRegistraVotos() {
		Usuario usuario = new Usuario(1L, "Test", "test@test.com");
		try {
			repository.registraVotos(usuario);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSalva() {
		Livro livro = new Livro();
		try {
			repository.salva(livro);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
