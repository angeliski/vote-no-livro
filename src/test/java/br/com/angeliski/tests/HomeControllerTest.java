package br.com.angeliski.tests;

import java.util.Queue;

import javax.enterprise.context.Conversation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.angeliski.dao.livro.LivroDAO;
import br.com.angeliski.entidades.Livro;
import br.com.angeliski.repository.livro.LivroRepository;
import br.com.angeliski.view.home.HomeController;
import br.com.caelum.vraptor.util.test.MockResult;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private HomeController homeController;

	@Mock
	private Conversation conversation;

	private MockResult result;

	private LivroRepository livroRepository;

	@Before
	public void before() {
		result = new MockResult();
		livroRepository = new LivroRepository(new LivroDAO());
		homeController = new HomeController(result, conversation, livroRepository);
	}

	@Test
	public void inicioTest() {
		homeController.inicio();
		// verifica se o cid foi adicionado para manter a bean ativo
		Assert.assertTrue("A conversação não foi adicionada", result.included().containsKey("cid"));
		// verifica se os livros foram adicionados corretamente
		Queue<Livro> livros = (Queue<Livro>) result.included("livros");
		Assert.assertNotNull("Os livros não foram adicionados", livros);
		// só devem ter dois livros
		Assert.assertEquals("O numero de livros esta diferente do esperado", 2, livros.size());

	}

}
