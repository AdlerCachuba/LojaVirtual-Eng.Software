package com.loja.Loja_Adler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loja.Loja_Adler.model.EntradaItens;
import com.loja.Loja_Adler.model.EntradaProduto;
import com.loja.Loja_Adler.model.Produto;
import com.loja.Loja_Adler.repository.EntradaItensRepository;
import com.loja.Loja_Adler.repository.EntradaProdutoRepository;
import com.loja.Loja_Adler.repository.FuncionarioRepository;
import com.loja.Loja_Adler.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


	
@Controller
public class EntradaProdutoController {
	
	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();
	
	@Autowired
	private EntradaProdutoRepository entradaProdutoRepository;
	
	@Autowired
	private EntradaItensRepository entradaItensRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepositorio;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/administrativo/entrada/cadastrar")
	public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens) {
		ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
		mv.addObject("entrada", entrada);
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepository.findAll());
		return mv;  
	}
	
//	@GetMapping("/administrativo/entrada/listar")
//	public ModelAndView listar() {
//		ModelAndView mv = new ModelAndView("administrativo/entrada/lista");
//		mv.addObject("listaEstados", entradaProdutoRepository.findAll());
//		return mv;
//	}
//	
//	@GetMapping("/administrativo/entrada/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") Long id) {
//		Optional<Estado> estado = entradaProdutoRepositorio.findById(id);
//		return cadastrar(estado.get());
//	}
	
//	@GetMapping("/administrativo/entrada/remover/{id}")
//	public ModelAndView remover(@PathVariable("id") Long id) {
//		Optional<Estado> estado = entradaProdutoRepositorio.findById(id);
//		entradaProdutoRepositorio.delete(estado.get());
//		return listar();
//	}

	
	@PostMapping("/administrativo/entrada/salvar")
	public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens) {
		if(acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		}else if(acao.equals("salvar")) {
			entradaProdutoRepository.saveAndFlush(entrada);
			for(EntradaItens it:listaEntrada) {
				it.setEntrada(entrada);
				entradaItensRepository.saveAndFlush(it);
				Optional<Produto> prod = produtoRepository.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				produtoRepository.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>();
				
			}
			return cadastrar(new EntradaProduto(), new EntradaItens());
		}
		
		System.out.print(this.listaEntrada.size());
		
		return cadastrar(entrada, new EntradaItens());
	}
}
