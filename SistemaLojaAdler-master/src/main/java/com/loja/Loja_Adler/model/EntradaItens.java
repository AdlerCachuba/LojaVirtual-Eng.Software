package com.loja.Loja_Adler.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "entrada_itens")
public class EntradaItens implements Serializable {

    public EntradaItens() {
        super();
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double quantidade = 0.0;

    private Double valorProduto = 0.0;

    private Double valorVenda = 0.0;

    @ManyToOne
    private EntradaProduto entradaProduto;

    @ManyToOne
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public EntradaProduto getEntradaProduto() {
        return entradaProduto;
    }

    public void setEntradaProduto(EntradaProduto entradaProduto) {
        this.entradaProduto = entradaProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntradaItens that = (EntradaItens) o;
        return Objects.equals(id, that.id) && Objects.equals(quantidade, that.quantidade) && Objects.equals(valorProduto, that.valorProduto) && Objects.equals(valorVenda, that.valorVenda) && Objects.equals(entradaProduto, that.entradaProduto) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidade, valorProduto, valorVenda, entradaProduto, produto);
    }

    @Override
    public String toString() {
        return "EntradaItens{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", valorProduto=" + valorProduto +
                ", valorVenda=" + valorVenda +
                ", entradaProduto=" + entradaProduto +
                ", produto=" + produto +
                '}';
    }
}