package br.com.angeliski.repository.livro;

import java.util.Date;
import java.util.List;
import java.util.Queue;

import javax.inject.Inject;

import br.com.angeliski.dao.generic.GenericDAO;
import br.com.angeliski.dao.livro.LivroDAO;
import br.com.angeliski.entidades.Livro;
import br.com.angeliski.entidades.Usuario;
import br.com.angeliski.repository.generic.GenericRepository;

public class LivroRepository extends GenericRepository<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4102148234710973705L;
	private LivroDAO livroDao;

	/**
	 * @deprecated CDI eyes only
	 */
	protected LivroRepository() {
		this(null);
	}

	@Inject
	public LivroRepository(LivroDAO livroDao) {
		this.livroDao = livroDao;
	}

	@Override
	public GenericDAO<Livro> getDao() {
		return livroDao;
	}

	public Queue<Livro> recuperaListaDeLivrosParaVotacao() {
		Queue<Livro> queue = livroDao.recuperaListaDeLivrosParaVotacao();
		return queue;
	}

	public void registraVotos(Usuario usuario) {
		List<Livro> livros = livroDao.recuperarLivros(usuario);
		usuario.setLivros(livros);
		Usuario user = livroDao.recuperaUsuarioPorEmail(usuario.getEmail());
		// identifica que o usuario esta voltando ao site
		if (user != null) {

			// atribuindo data de modificação ao usuario
			// preUpdate não atende porque o objeto gerenciado não foi
			// atualizado
			user.setUltimaVisita(new Date());
			usuario.setUltimaVisita(new Date());
 
			usuario = user;
			user.getLivros().addAll(livros);
		}

		livroDao.registraVotos(usuario);
	}

}
