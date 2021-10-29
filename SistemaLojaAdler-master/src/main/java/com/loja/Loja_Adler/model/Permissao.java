package com.loja.Loja_Adler.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "permissoes")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataCadastro = new Date();

	@ManyToOne
	private Funcionario funcionario;

	@ManyToOne
	private Papel papel;


	public Permissao() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Permissao permissao = (Permissao) o;
		return Objects.equals(id, permissao.id) && Objects.equals(dataCadastro, permissao.dataCadastro) && Objects.equals(funcionario, permissao.funcionario) && Objects.equals(papel, permissao.papel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, dataCadastro, funcionario, papel);
	}

	@Override
	public String toString() {
		return "Permissao{" +
				"id=" + id +
				", dataCadastro=" + dataCadastro +
				", funcionario=" + funcionario +
				", papel=" + papel +
				'}';
	}
}