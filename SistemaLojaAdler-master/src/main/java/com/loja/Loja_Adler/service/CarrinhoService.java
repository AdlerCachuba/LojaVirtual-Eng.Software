package com.loja.Loja_Adler.service;

import com.loja.Loja_Adler.model.Cliente;
import com.loja.Loja_Adler.model.ItensCompra;

import java.util.List;

public interface CarrinhoService {

    List<ItensCompra> calcularTotal(List<ItensCompra> listItensCompras);

    Cliente buscarUsuarioLogado(Cliente cliente);
}