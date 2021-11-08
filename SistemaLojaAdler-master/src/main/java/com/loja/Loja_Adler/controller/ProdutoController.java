package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.constants.ConstantsImagens;
import com.loja.Loja_Adler.model.Imagem;
import com.loja.Loja_Adler.model.Produto;
import com.loja.Loja_Adler.repository.CategoriaRepository;
import com.loja.Loja_Adler.repository.ImagemRepository;
import com.loja.Loja_Adler.repository.MarcaRepository;
import com.loja.Loja_Adler.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/administrativo/produtos")
public class ProdutoController {

    ConstantsImagens constantsImagens;

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("listaMarcas", marcaRepository.findAll());
        mv.addObject("listaCategorias", categoriaRepository.findAll());
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        List<Imagem> listaDeImagens = imagemRepository.findAll();
        List<Produto> listaDeProdutos = produtoRepository.findAll();
        for (Imagem imagem : listaDeImagens) {
            for(Produto prod: listaDeProdutos){
                if(imagem.getProduto().equals(prod)){
                    prod.setNomeImagem(imagem.getNome());
                }
            }
        }
        mv.addObject("listaProdutos", listaDeProdutos);
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return cadastrar(produto.get());
    }

    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        produtoRepository.delete(produto.get());
        return listar();
    }

    @ResponseBody
    @GetMapping("/mostrarImagem/{imagem}")
    public byte[] retornarImagem(@PathVariable("imagem") String imagem) {
        if (imagem != null || imagem.trim().length() > 0) {
            File imagemArquivo = new File(ConstantsImagens.CAMINHO_PASTA_IMAGENS + imagem);
            try {
                return Files.readAllBytes(imagemArquivo.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @PostMapping("/salvar")
    public ModelAndView salvar(@Validated Produto produto, BindingResult result, @RequestParam("file") List<MultipartFile> arquivo) {

        if (result.hasErrors()) {
            return cadastrar(produto);
        }
        produtoRepository.saveAndFlush(produto);
        try {

            if (!arquivo.isEmpty()) {
                Imagem imagem = new Imagem();
                //Salvou Produto
                produtoRepository.saveAndFlush(produto);
                for (MultipartFile file : arquivo) {
                    byte[] bytes = file.getBytes();
                    // Caminho onde a imagem vai ser salva
                    Path caminho = Paths.get(ConstantsImagens.CAMINHO_PASTA_IMAGENS + String.valueOf(produto.getId()) + file.getOriginalFilename());
                    Files.write(caminho, bytes);
                    imagem.setNome(String.valueOf(produto.getId() + file.getOriginalFilename()));
                    imagem.setProduto(produto);
                    imagemRepository.saveAndFlush(imagem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return cadastrar(new Produto());
    }
}