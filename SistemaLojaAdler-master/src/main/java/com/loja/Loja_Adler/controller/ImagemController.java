package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.constants.ConstantsImagens;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImagemController {

    ConstantsImagens constantsImagens;

    @GetMapping("/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File(constantsImagens.CAMINHO_PASTA_IMAGENS + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            try {
                return Files.readAllBytes(imagemArquivo.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
