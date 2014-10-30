package br.com.angeliski.repository.livro;

import java.util.Queue;

import javax.inject.Inject;

import br.com.angeliski.dao.generic.GenericDAO;
import br.com.angeliski.dao.livro.LivroDAO;
import br.com.angeliski.entidades.Livro;
import br.com.angeliski.repository.generic.GenericRepository;

public class LivroRepository extends GenericRepository<Livro> {

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

}
