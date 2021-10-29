package com.loja.Loja_Adler.repository;

import com.loja.Loja_Adler.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
