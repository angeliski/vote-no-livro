package br.com.angeliski.view.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import br.com.angeliski.entidades.Livro;
import br.com.angeliski.repository.livro.LivroRepository;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("/")
@ConversationScoped
public class HomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3994111876958024410L;

	private Queue<Livro> livros;

	private Result result;

	private Conversation conversation;

	private LivroRepository livroRepository;

	/**
	 * @deprecated CDI eyes only
	 */
	protected HomeController() {
		this(null, null, null);
	}

	@Inject
	public HomeController(Result result, Conversation conversation, LivroRepository livroRepository) {
		this.result = result;
		this.conversation = conversation;
		this.livroRepository = livroRepository;
	}

	@PostConstruct
	private void initConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	private void adicionarCID() {
		result.include("cid", conversation.getId());
	}

	@Get
	@Path("/")
	public void inicio() {
		adicionarCID();
		if (livros == null) {
			livros = livroRepository.recuperaListaDeLivrosParaVotacao();
		}

		List<Livro> livrosParaVotacao = new ArrayList<Livro>();
		livrosParaVotacao.add(livros.poll());
		livrosParaVotacao.add(livros.poll());

		result.include("livros", livrosParaVotacao);
	}

	@Post("voto")
	@Consumes("application/json")
	public void voto(Livro livro) {
		System.out.println("voto no " + livro.getId());

		if (livros.size() > 0) {
			result.use(Results.json()).withoutRoot().from(livros.poll()).serialize();
		} else {
			// sinaliza que nao existem mais livros para votar
			result.use(Results.json()).withoutRoot().from(livros).serialize();
		}
	}

	@Get("usuario")
	public void concluirVotacao() {
		adicionarCID();
	}

}
