package br.com.angeliski.dao.livro;

import java.util.PriorityQueue;
import java.util.Queue;

import br.com.angeliski.dao.generic.GenericDAO;
import br.com.angeliski.entidades.Livro;

public class LivroDAO extends GenericDAO<Livro> {

	public Queue<Livro> recuperaListaDeLivrosParaVotacao() {
		Queue<Livro> queue = new PriorityQueue<Livro>();
		queue.add(new Livro());
		queue.add(new Livro());

		return queue;
	}

}
