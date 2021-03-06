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

	public int getTotal() {
		return usuarios != null ? usuarios.size() : 0;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Livro [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (nome != null)
			builder.append("nome=").append(nome).append(", ");
		if (url != null)
			builder.append("url=").append(url);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
