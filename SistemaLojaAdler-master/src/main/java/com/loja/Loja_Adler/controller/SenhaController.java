package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.model.Funcionario;
import com.loja.Loja_Adler.repository.FuncionarioRepository;
import com.loja.Loja_Adler.service.EnviarEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;

@Controller
public class SenhaController {


    @Autowired
    private EnviarEmailService enviarEmailService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/mensagem")
    public ModelAndView mensagem() {
        return new ModelAndView("/administrativo/email/mensagem");
    }

    @GetMapping("/recuperarSenha")
    public ModelAndView recuperarSenha() {
        return new ModelAndView("/administrativo/email/recuperacaoSenha");
    }

    @GetMapping("/alterarSenha")
    public ModelAndView alterarSenha() {
        return new ModelAndView("/administrativo/email/alterarSenha");
    }

    @PostMapping("/solicitarCodigo")
    public String solicitarCodigo(@RequestParam(name="email") String email, Model model){
        Funcionario funcionario = funcionarioRepository.findByEmail(email);
        if(funcionario != null){
            funcionario.setCodigoRecuperacao(RandomStringUtils.randomAlphanumeric(15).toUpperCase());
            funcionario.setDataCodigo(new Date());
            funcionarioRepository.save(funcionario);
            enviarEmailService.enviar(
                    email,
                    "Recuperação de Senha",
                    "O seu código de recuperação é : " +
                            funcionario.getCodigoRecuperacao());
            model.addAttribute("mensagem", "O código de recuperação foi encaminhado para seu email");
            return "administrativo/email/alterarSenha";
        }
        model.addAttribute("administrativo/email/mensagem","E-mail e/ou código não encontrado");
        return "administrativo/email/alterarSenha";
    }

    @PostMapping("/alterarSenha")
    public String alterarSenha(
            @RequestParam(name="email") String email,
            @RequestParam(name="codigoRecuperacao") String codigoRecuperacao,
            @RequestParam(name="senha") String senha, Model model){
        Funcionario funcionario = funcionarioRepository.findByEmailAndCodigoRecuperacao(email,codigoRecuperacao);
        if(funcionario != null){
            Date dif = new Date(new Date().getTime() - funcionario.getDataCodigo().getTime());
            if (dif.getTime() /1000 < 15000){
                funcionario.setCodigoRecuperacao(null);
                funcionario.setDataCodigo(null);
                funcionario.setSenha(new BCryptPasswordEncoder().encode(senha));
                funcionarioRepository.save(funcionario);
                model.addAttribute("mensagem", "Senha alterada com Sucesso !!!");
                return "administrativo/email/mensagem";
            }else{
                model.addAttribute("mensagem", "Código expirado, solicite um novo código!!!");
            }
        }
        model.addAttribute("mensagem", "E-mail e/ou código não encontrado");
        return "administrativo/email/mensagem";
    }


}
