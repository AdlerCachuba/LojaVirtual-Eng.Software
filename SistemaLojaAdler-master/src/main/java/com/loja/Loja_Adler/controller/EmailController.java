package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.service.EnviarEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class EmailController {

    @Autowired
    private EnviarEmailService enviarEmailService;

    @GetMapping("/enviar-email")
    public String enviarEmail() {
        return enviarEmailService.enviar(
                "adlermateuself@gmail.com",
                "Recuperação de Senha",
                "Sua senha é 123");
    }
}
