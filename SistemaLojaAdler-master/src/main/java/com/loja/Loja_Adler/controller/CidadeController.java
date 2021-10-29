package com.loja.Loja_Adler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loja.Loja_Adler.model.Cidade;
import com.loja.Loja_Adler.repository.CidadeRepository;
import com.loja.Loja_Adler.repository.EstadoRepository;

import java.util.Optional;
import javax.validation.Valid;


	
@Controller
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepositorio;
	
	@GetMapping("/administrativo/cidades/cadastrar")
	public ModelAndView cadastrar(Cidade cidade) {
		ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro");
		mv.addObject("cidade", cidade);
		mv.addObject("listaEstados", estadoRepositorio.findAll());
		return mv;  
	}
	
	@GetMapping("/administrativo/cidades/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
		mv.addObject("listaCidades", cidadeRepository.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/cidades/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		return cadastrar(cidade.get());
	}
	
	@GetMapping("/administrativo/cidades/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		cidadeRepository.delete(cidade.get());
		return listar();
	}
	
	@PostMapping("/administrativo/cidades/salvar")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrar(cidade);
		}
		cidadeRepository.saveAndFlush(cidade);
		
		return cadastrar(new Cidade());
	}
}
