package br.com.angeliski.dao.livro;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.angeliski.dao.generic.GenericDAO;
import br.com.angeliski.entidades.Livro;
import br.com.angeliski.entidades.Usuario;

public class LivroDAO extends GenericDAO<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2570317306470260845L;

	/**
	 * @deprecated CDI eyes only
	 */
	protected LivroDAO() {
		this(null);
	}

	@Inject
	public LivroDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public Queue<Livro> recuperaListaDeLivrosParaVotacao() {

		TypedQuery<Livro> query = getEntityManager().createQuery("select obj from Livro obj", Livro.class);

		Queue<Livro> queue = new PriorityQueue<Livro>(query.getResultList());

		return queue;
	}

	public void registraVotos(Usuario usuario) {
		getEntityManager().persist(usuario);
	}

	public List<Livro> recuperarLivros(Usuario usuario) {
		List<Livro> livros = new ArrayList<Livro>();

		for (Livro livro : usuario.getLivros()) {
			livros.add(getEntityManager().getReference(Livro.class, livro.getId()));
		}

		return livros;
	}

}
