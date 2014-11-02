package br.com.angeliski.view.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.angeliski.entidades.Livro;
import br.com.angeliski.entidades.Usuario;
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

	private List<Livro> livrosVotados;

	private Result result;

	private Conversation conversation;

	private LivroRepository livroRepository;

	private Usuario user;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
		logger.info("livro votado: " + livro);

		if (livrosVotados == null) {
			livrosVotados = new ArrayList<Livro>();
		}

		livrosVotados.add(livro);
		if (livros.size() > 0) {
			logger.info("livro enviado para votacao" + livro);
			result.use(Results.json()).withoutRoot().from(livros.poll()).serialize();
		} else {
			logger.info("não existem mais livros para serem votados");
			// sinaliza que nao existem mais livros para votar
			// linha adicionada para sinalizar que nao existe mais conteudo
			// verificar se é bug no vraptor4 ou mal uso do nothing
			result.use(Results.http()).setStatusCode(HttpServletResponse.SC_NO_CONTENT);
			result.nothing();
		}
	}

	@Get("usuario")
	public void concluirVotacao() {
		adicionarCID();
	}

	@Post("usuario")
	public void concluirVotacao(Usuario usuario) {
		logger.info("usuario que votou" + usuario);
		usuario.setLivros(livrosVotados);
		adicionarCID();

		user = usuario;

		result.redirectTo(this).ranking();
	}

	@Get("ranking")
	public void ranking() {
		result.include("usuario", user);
		result.include("livros", livroRepository.recuperaListaDeLivrosParaVotacao());
	}

}
