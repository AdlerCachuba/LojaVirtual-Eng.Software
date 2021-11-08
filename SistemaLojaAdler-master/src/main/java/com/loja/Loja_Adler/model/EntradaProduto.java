package com.loja.Loja_Adler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "entrada_produto")
public class EntradaProduto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Funcionario funcionario;
	private Date dataEntrada = new Date();
	private String observacao;
	private String fornecedor;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public EntradaProduto() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EntradaProduto that = (EntradaProduto) o;
		return Objects.equals(id, that.id) && Objects.equals(funcionario, that.funcionario) && Objects.equals(dataEntrada, that.dataEntrada) && Objects.equals(observacao, that.observacao) && Objects.equals(fornecedor, that.fornecedor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, funcionario, dataEntrada, observacao, fornecedor);
	}

	@Override
	public String toString() {
		return "EntradaProduto{" +
				"id=" + id +
				", funcionario=" + funcionario +
				", dataEntrada=" + dataEntrada +
				", observacao='" + observacao + '\'' +
				", fornecedor='" + fornecedor + '\'' +
				'}';
	}
}