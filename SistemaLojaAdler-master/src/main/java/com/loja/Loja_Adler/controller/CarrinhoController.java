package com.loja.Loja_Adler.controller;

import com.loja.Loja_Adler.model.Cliente;
import com.loja.Loja_Adler.model.Compra;
import com.loja.Loja_Adler.model.ItensCompra;
import com.loja.Loja_Adler.model.Produto;
import com.loja.Loja_Adler.repository.ClienteRepository;
import com.loja.Loja_Adler.repository.CompraRepository;
import com.loja.Loja_Adler.repository.ItensCompraRepository;
import com.loja.Loja_Adler.repository.ProdutoRepository;
import com.loja.Loja_Adler.service.CarrinhoService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CarrinhoController {

    private List<ItensCompra> listaItensCompra = new ArrayList<ItensCompra>();
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

    private final CarrinhoService carrinhoService;


    private void calcularTotal() {
        compra.setValorTotal(0.);
        for (ItensCompra it : listaItensCompra) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        cliente = carrinhoService.buscarUsuarioLogado(cliente);
        System.out.println(cliente);
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", listaItensCompra);
        return mv;
    }


    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra() {
        cliente = carrinhoService.buscarUsuarioLogado(cliente);
        ModelAndView mv = new ModelAndView("/cliente/finalizar");
        listaItensCompra = carrinhoService.calcularTotal(listaItensCompra);
        mv.addObject("compra", compra);
        mv.addObject("listaItens", listaItensCompra);
        mv.addObject("cliente", cliente);
        return mv;
    }


    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaPagamento) {
        ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        repositorioCompra.saveAndFlush(compra);

        for (ItensCompra c : listaItensCompra) {
            c.setCompra(compra);
            repositorioItensCompra.saveAndFlush(c);
        }
        listaItensCompra = new ArrayList<>();
        compra = new Compra();
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        for (ItensCompra it : listaItensCompra) {
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

        for (ItensCompra it : listaItensCompra) {
            if (it.getProduto().getId().equals(id)) {
                listaItensCompra.remove(it);
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
        for (ItensCompra it : listaItensCompra) {
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

            listaItensCompra.add(item);
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