package br.com.angeliski.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class Livro implements Comparable<Livro> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String nome;

	private String url;

	@Override
	public int compareTo(Livro livro) {
		int retorno = 0;
		if (livro != null && nome != null) {
			retorno = String.CASE_INSENSITIVE_ORDER.compare(nome, livro.nome);
		} else if (nome != null) {
			retorno = 1;
		}
		return retorno;
	}

}
