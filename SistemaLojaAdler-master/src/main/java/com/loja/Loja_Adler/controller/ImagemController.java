package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.constants.ConstantsImagens;
import com.loja.Loja_Adler.model.Imagem;
import com.loja.Loja_Adler.model.Produto;
import com.loja.Loja_Adler.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class ImagemController {

//    @GetMapping("/mostrarImagem/{imagem}")
//    @ResponseBody
//    public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
//        File imagemArquivo = new File(ConstantsImagens.CAMINHO_PASTA_IMAGENS + imagem);
//        if (imagem != null || imagem.trim().length() > 0) {
//            try {
//                return Files.readAllBytes(imagemArquivo.toPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
@Autowired
ImagemRepository imagemRepository;

@GetMapping("/mostraImagem/{productid}")
@ResponseBody
public byte[] getImage(@PathVariable("productid") Long productid) throws IOException {
    Produto produto = new Produto();
    produto.setId(productid);
    List<Imagem> imagens = imagemRepository.findImagensProdutoByProduto(produto);
    if (imagens.size() > 0) {
        File imageFile = new File(ConstantsImagens.CAMINHO_PASTA_IMAGENS + imagens.get(0).getNome());
        if (imageFile != null || imagens.get(0).getNome().trim().length() > 0) {
            return Files.readAllBytes(imageFile.toPath());
        }
        return null;
    }
    return null;
}


}
