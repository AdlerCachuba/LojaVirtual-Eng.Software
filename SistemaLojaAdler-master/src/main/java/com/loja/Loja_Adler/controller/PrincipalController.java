package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.model.Compra;
import com.loja.Loja_Adler.model.EntradaItens;
import com.loja.Loja_Adler.model.Produto;
import com.loja.Loja_Adler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PrincipalController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private EntradaItensRepository entradaItensRepository;

	@GetMapping("/administrativo")
	public ModelAndView acessarPrincipal() {
		ModelAndView mv = new ModelAndView("administrativo/home");
		mv.addObject("totalProdutosEstoque", quantidadeEstoqueProduto());
		mv.addObject("totalFuncionarios", funcionarioRepository.count());
		mv.addObject("totalCompras", compraRepository.count());
		mv.addObject("totalCompraVendidas", totalCompraVendidas());
		mv.addObject("margemDeLucros", margemDeLucros());
		return mv;
	}

	public int quantidadeEstoqueProduto() {
		List<Produto> produtos = produtoRepository.findAll();

		int totalEstoque = 0;
		for (Produto produto : produtos) {
			totalEstoque += produto.getQuantidadeEstoque();
		}
		return totalEstoque;
	}

	public Double totalCompraVendidas() {
		List<Compra> compras = compraRepository.findAll();

		Double totalVendido = 0.0;
		for (Compra compra : compras) {
			totalVendido += compra.getValorTotal();
		}
		return totalVendido;
	}

	public Double receitaDeCustos() {
		List<EntradaItens> entradaItens = entradaItensRepository.findAll();

		Double total = 0.0;
		for (EntradaItens entradaItem : entradaItens) {
			total += entradaItem.getValorProduto();
		}
		return total;
	}

	public Double margemDeLucros() {
		Double totalVendido = totalCompraVendidas();
		Double totalCusto = receitaDeCustos();
		Double totalLucro = (totalVendido - totalCusto)/ totalCusto;
		return totalLucro * 100;
	}

}