package br.com.angeliski.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@ManyToMany
	@JoinTable(name = "usuario_livro", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "livro_id"))
	private List<Usuario> usuarios;

	public Livro() {
	}

	public Livro(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

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

	@Override
	public String toString() {
		return "Livro [id=" + id + ", nome=" + nome + ", url=" + url + "]";
	}
}
