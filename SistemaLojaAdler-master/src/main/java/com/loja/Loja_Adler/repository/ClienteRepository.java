package com.loja.Loja_Adler.repository;


import com.loja.Loja_Adler.model.Cliente;
import com.loja.Loja_Adler.model.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    @Query("from Cliente where email=?1")
    public List<Cliente> buscarClienteEmail(String email);
}
