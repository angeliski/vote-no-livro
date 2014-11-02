package br.com.angeliski.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String nome;

	private String email;

	private Date ultimaVisita;

	@ManyToMany
	@JoinTable(name = "usuario_livro", joinColumns = @JoinColumn(name = "livro_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private List<Livro> livros;

	public Usuario() {
	}

	public Usuario(Long id, String nome, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	@PrePersist
	public void prePersist() {
		setUltimaVisita(new Date());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (nome != null)
			builder.append("nome=").append(nome).append(", ");
		if (email != null)
			builder.append("email=").append(email).append(", ");
		if (ultimaVisita != null)
			builder.append("ultimaVisita=").append(ultimaVisita);
		builder.append("]");
		return builder.toString();
	}

}
