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
import br.com.caelum.vraptor.util.test.MockSerializationResult;

import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private HomeController homeController;

	@Mock
	private Conversation conversation;

	private MockSerializationResult result;

	private LivroRepository livroRepository;

	@Before
	public void before() {
		result = new MockSerializationResult();
		livroRepository = new LivroRepository(new LivroDAO());
		homeController = new HomeController(result, conversation, livroRepository);
	}

	@Test
	public void inicioTest() {
		homeController.inicio();
		// verifica se o cid foi adicionado para manter a bean ativo
		Assert.assertTrue("A conversa��o n�o foi adicionada", result.included().containsKey("cid"));
		// verifica se os livros foram adicionados corretamente
		Queue<Livro> livros = (Queue<Livro>) result.included("livros");
		Assert.assertNotNull("Os livros n�o foram adicionados", livros);
		// s� devem ter dois livros
		Assert.assertEquals("O numero de livros esta diferente do esperado", 2, livros.size());
	}

	@Test
	public void votaTest() throws Exception {
		// criando o livro que vai ser votado
		Livro livroVotado = new Livro(1L, "Livro 1");

		// votando no livro
		homeController.voto(livroVotado);

		// recuperando valores do json
		String json = result.serializedResult();
		Gson gson = new Gson();
		Livro livro = gson.fromJson(json, Livro.class);

		// verificando se foi adicionado um novo livro para vota��o
		Assert.assertNotNull("n�o foi adicionado nenhum livro para vota��o", livro);

		// verifica se o livro que vai ser retornado para a nova vota��o n�o
		// � o mesmo que foi votado
		Assert.assertNotEquals("o livro retornando acabou de ser votado", livro.getId(), livroVotado.getId());
	}

}
