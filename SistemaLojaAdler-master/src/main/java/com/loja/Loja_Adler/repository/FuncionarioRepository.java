package com.loja.Loja_Adler.repository;

import com.loja.Loja_Adler.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByEmail(String email);

    Funcionario findByEmailAndCodigoRecuperacao(String email, String codigoRecuperacao);


}
