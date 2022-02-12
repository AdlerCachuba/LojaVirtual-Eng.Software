package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.model.Cliente;
import com.loja.Loja_Adler.model.Compra;
import com.loja.Loja_Adler.model.ItensCompra;
import com.loja.Loja_Adler.model.Produto;
import com.loja.Loja_Adler.repository.ClienteRepository;
import com.loja.Loja_Adler.repository.CompraRepository;
import com.loja.Loja_Adler.repository.ItensCompraRepository;
import com.loja.Loja_Adler.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarrinhoController {

    private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente;

    @Autowired
    private ProdutoRepository repositorioProduto;

    @Autowired
    private ClienteRepository repositorioCliente;

    @Autowired
    private CompraRepository repositorioCompra;

    @Autowired
    private ItensCompraRepository repositorioItensCompra;

    private void calcularTotal() {
        compra.setValorTotal(0.);
        for (ItensCompra it : itensCompra) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {

        ModelAndView mv = new ModelAndView("administrativo/cliente/carrinho");
        calcularTotal();
        // System.out.println(compra.getValorTotal());
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    private void buscarUsuarioLogado() {
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticado instanceof AnonymousAuthenticationToken)) {
            String email = autenticado.getName();
            cliente = repositorioCliente.buscarClienteEmail(email).get(0);
        }
    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra() {
        buscarUsuarioLogado();
        ModelAndView mv = new ModelAndView("administrativo/cliente/finalizar");
        calcularTotal();
        // System.out.println(compra.getValorTotal());
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaPagamento) {
        ModelAndView mv = new ModelAndView("administrativo/cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        repositorioCompra.saveAndFlush(compra);

        for (ItensCompra c : itensCompra) {
            c.setCompra(compra);
            repositorioItensCompra.saveAndFlush(c);
        }
        itensCompra = new ArrayList<>();
        compra = new Compra();
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        ModelAndView mv = new ModelAndView("administrativo/cliente/carrinho");

        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {
                // System.out.println(it.getValorTotal());
                if (acao.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                } else if (acao == 0) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                }
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProdutoCarrinho(@PathVariable Long id) {

        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {
                itensCompra.remove(it);
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id) {

        Optional<Produto> prod = repositorioProduto.findById(id);
        Produto produto = prod.get();

        int controle = 0;
        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(0.);
                it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setProduto(produto);
            item.setValorUnitario(produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));

            itensCompra.add(item);
        }

        return "redirect:/carrinho";
    }

}

//@Controller
//public class CarrinhoController {
//    private List<ItensCompra> listItensCompras;
//    private Compra compra = new Compra();
//    private Cliente cliente;
//
//    @GetMapping("/cliente/carrinho")
//    public ModelAndView carrinho() {
//        ModelAndView mv = new ModelAndView("administrativo/cliente/carrinho");
//        return mv;
//    }
//
//    @GetMapping("/adicionarCarrinho/{id}")
//    public ModelAndView adicionarCarrinho(@PathVariable Long id) {
//        ModelAndView mv = new ModelAndView("administrativo/cliente/carrinho");
//        return mv;
//    }
//
//    @GetMapping("/alterarQuantidade/{id}/{acao}")
//    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
//        ModelAndView mv = new ModelAndView("/cliente/carrinho");
//
//        for (ItensCompra it : listItensCompras) {
//            if (it.getProduto().getId().equals(id)) {
//                if (acao.equals(1)) {
//                    it.setQuantidade(it.getQuantidade() + 1);
//                    it.setValorTotal(0.0);
//                    it.setValorTotal(it.getValorTotal() + it.getQuantidade() * it.getValorUnitario());
//                } else if (acao == 0 && it.getQuantidade() > 0) {
//                    it.setQuantidade(it.getQuantidade() - 1);
//                    it.setValorTotal(0.0);
//                    it.setValorTotal(it.getValorTotal() + it.getQuantidade() * it.getValorUnitario());
//                }
//                break;
//            }
//        }
//        return "redirect:/carrinho";
//    }
//
//}