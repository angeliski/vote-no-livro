package br.com.angeliski.dao.livro;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import br.com.angeliski.dao.generic.GenericDAO;
import br.com.angeliski.entidades.Livro;
import br.com.angeliski.entidades.Usuario;

public class LivroDAO extends GenericDAO<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2570317306470260845L;

	public Queue<Livro> recuperaListaDeLivrosParaVotacao() {
		Queue<Livro> queue = new PriorityQueue<Livro>();
		Livro livro = new Livro();
		livro.setId(1L);
		livro.setNome("Livro 1");
		livro.setUrl("resources/image/livro1.jpg");
		livro.setUsuarios(new ArrayList<Usuario>());
		livro.getUsuarios().add(new Usuario());
		queue.add(livro);

		livro = new Livro();
		livro.setId(2L);
		livro.setNome("Livro 2");
		livro.setUrl("resources/image/livro2.jpg");
		livro.setUsuarios(new ArrayList<Usuario>());
		livro.getUsuarios().add(new Usuario());
		queue.add(livro);

		livro = new Livro();
		livro.setId(2L);
		livro.setNome("Livro 3");
		livro.setUrl("resources/image/livro3.jpg");
		livro.setUsuarios(new ArrayList<Usuario>());
		livro.getUsuarios().add(new Usuario());
		queue.add(livro);

		livro = new Livro();
		livro.setId(2L);
		livro.setNome("Livro 4");
		livro.setUrl("resources/image/livro4.jpg");
		livro.setUsuarios(new ArrayList<Usuario>());
		livro.getUsuarios().add(new Usuario());
		queue.add(livro);

		livro = new Livro();
		livro.setId(2L);
		livro.setNome("Livro 5");
		livro.setUrl("resources/image/livro5.jpg");
		livro.setUsuarios(new ArrayList<Usuario>());

		queue.add(livro);

		return queue;
	}

	public void registraVotos(Usuario usuario) {

	}

}
