package com.loja.Loja_Adler.model;

import com.loja.Loja_Adler.model.Produto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "imagem")
@Entity
public class Imagem implements Serializable{

    public Imagem(){super();}

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @ManyToOne
    private Produto produto;

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
        Imagem imagem = (Imagem) o;
        return Objects.equals(id, imagem.id) && Objects.equals(nome, imagem.nome) && Objects.equals(produto, imagem.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, produto);
    }

    @Override
    public String toString() {
        return "Imagem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", produto=" + produto +
                '}';
    }
}