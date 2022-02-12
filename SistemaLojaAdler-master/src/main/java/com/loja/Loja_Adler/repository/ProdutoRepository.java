package com.loja.Loja_Adler.repository;

import com.loja.Loja_Adler.model.Categoria;
import com.loja.Loja_Adler.model.Marca;
import com.loja.Loja_Adler.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    @Query("from Produto p where p.descricao like %?1% ")
    List<Produto> findByDescricao(String descricao);

    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findByMarca(Marca marca);


}
