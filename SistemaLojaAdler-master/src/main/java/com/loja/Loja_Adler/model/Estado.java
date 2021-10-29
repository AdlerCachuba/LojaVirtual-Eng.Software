package com.loja.Loja_Adler.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "estado")
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	private String sigla;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Estado() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Estado estado = (Estado) o;
		return Objects.equals(id, estado.id) && Objects.equals(nome, estado.nome) && Objects.equals(sigla, estado.sigla);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, sigla);
	}

	@Override
	public String toString() {
		return "Estado{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", sigla='" + sigla + '\'' +
				'}';
	}
}
