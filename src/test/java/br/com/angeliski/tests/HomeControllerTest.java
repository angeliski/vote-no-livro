package br.com.angeliski.tests;

import java.util.List;
import java.util.PriorityQueue;
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
import br.com.angeliski.mocks.MockSerializationResultCustom;
import br.com.angeliski.repository.livro.LivroRepository;
import br.com.angeliski.view.home.HomeController;

import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private HomeController homeController;

	@Mock
	private Conversation conversation;

	private MockSerializationResultCustom result;

	private LivroRepository livroRepository;

	@Before
	public void before() {
		result = new MockSerializationResultCustom();
		livroRepository = new LivroRepository(new LivroDAO() {
			@Override
			public Queue<Livro> recuperaListaDeLivrosParaVotacao() {
				Queue<Livro> queue = new PriorityQueue<Livro>();
				Livro livro = new Livro();
				livro.setId(1L);
				livro.setNome("Livro 1");
				livro.setUrl("resources/image/livro1.jpg");

				queue.add(livro);
				livro = new Livro();
				livro.setId(2L);
				livro.setNome("Livro 2");
				livro.setUrl("resources/image/livro2.jpg");
				queue.add(livro);

				livro = new Livro();
				livro.setId(2L);
				livro.setNome("Livro 3");
				livro.setUrl("resources/image/livro3.jpg");
				queue.add(livro);
				return queue;
			}
		});
		homeController = new HomeController(result, conversation, livroRepository);
	}

	@Test
	public void inicioTest() {
		homeController.inicio();
		// verifica se o cid foi adicionado para manter a bean ativo
		Assert.assertTrue("A conversa��o n�o foi adicionada", result.included().containsKey("cid"));
		// verifica se os livros foram adicionados corretamente
		List<Livro> livros = (List<Livro>) result.included("livros");
		Assert.assertNotNull("Os livros n�o foram adicionados", livros);
		// s� devem ter dois livros
		Assert.assertEquals("O numero de livros esta diferente do esperado", 2, livros.size());
	}

	@Test
	public void votaTest() {
		// simulando acesso do usuario
		homeController.inicio();

		// criando o livro que vai ser votado
		Livro livroVotado = new Livro(1L, "Livro 1");

		// votando no livro
		homeController.voto(livroVotado);

		Livro livro = recuperaLivroDoJson();

		// verificando se foi adicionado um novo livro para vota��o
		Assert.assertNotNull("n�o foi adicionado nenhum livro para vota��o", livro);

		// verifica se o livro que vai ser retornado para a nova vota��o n�o
		// � o mesmo que foi votado
		Assert.assertNotEquals("o livro retornando acabou de ser votado", livro.getId(), livroVotado.getId());
	}

	private Livro recuperaLivroDoJson() {
		// recuperando valores do json
		String json = null;
		try {
			json = result.serializedResult();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		Gson gson = new Gson();
		Livro livro = gson.fromJson(json, Livro.class);
		return livro;
	}

	@Test
	public void votaRedirecionaAoFimTest() {
		// simulando acesso do usuario
		homeController.inicio();

		// criando o livro que vai ser votado
		Livro livroVotado = new Livro(1L, "Livro 1");

		// votando no livro
		homeController.voto(livroVotado);

		Livro livro = recuperaLivroDoJson();
		// limpando response para a nova requisicao
		result.resetResponse();

		// votando em outro livro
		homeController.voto(livro);

		livro = recuperaLivroDoJson();

		// verificando se foi adicionado um novo livro para vota��o
		Assert.assertNull("foi adicionada um livro onde n�o deveria existir mais", livro);

		// verifica se o controller redireciona quando n�o houver mais livros
		// para votar
		Assert.assertTrue("O redirecionamento n�o foi feito", result.used());

	}

}
