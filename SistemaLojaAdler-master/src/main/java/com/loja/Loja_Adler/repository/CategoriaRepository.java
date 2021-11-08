package com.loja.Loja_Adler.repository;


import com.loja.Loja_Adler.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    @Query("from Categoria c where c.nome like %?1% ")
    List<Categoria> findByNome(String nome);

}
