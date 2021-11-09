package com.loja.Loja_Adler.repository;

import com.loja.Loja_Adler.model.Imagem;
import com.loja.Loja_Adler.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    List<Imagem> findImagensProdutoByProduto(Produto produto);
}