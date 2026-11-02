package app;

import cardapio.*;
import financeiro.Caixa;
import financeiro.Transacao;
import pedido.GerenciadorPedidos;
import pedido.Pedido;

import java.util.Collection;
import java.util.Set;

public class RestauranteFacade {
    private final GerenciadorPedidos gerenciador = new GerenciadorPedidos();
    private final Caixa caixa = new Caixa();

    public Pedido criarPedido(String cliente) {
        return gerenciador.criaPedido(cliente);
    }

    public Lanche criarLanche(int opcao) {
        return switch (opcao) {
            case 1 -> LancheFactory.xBurguer();
            case 2 -> LancheFactory.xSalada();
            case 3 -> LancheFactory.xBacon();
            default -> throw new IllegalArgumentException("Opção de lanche inválida!");
        };
    }

    public void adicionarItem(int numero, ItemCardapio item) {
        Pedido pedido = exigirPedidoEditavel(numero);
        pedido.adicionarItem(item);
    }

    public ItemCardapio aplicarAdicionais(ItemCardapio item, boolean cheddar, boolean baconExtra) {
        if (cheddar) {
            item = new ComCheddar(item);
        }

        if (baconExtra) {
            item = new ComBaconExtra(item);
        }

        return item;
    }

    public Pedido buscarPedido(int numero) {
        return gerenciador.buscarPedido(numero);
    }

    public void removerItem(int numero, int indice) {
        Pedido pedido = exigirPedidoEditavel(numero);
        pedido.removerItem(indice);
    }

    public boolean cancelarPedido(int numero) {
        Pedido pedido = gerenciador.buscarPedido(numero);

        if (pedido == null) {
            return false;
        }

        if (caixa.pedidoFoiPago(numero)){
            throw new IllegalStateException("Não é possível cancelar um pedido pago!");
        }

        return gerenciador.cancelarPedido(numero);
    }

    private Pedido exigirPedidoEditavel(int numero) {
        Pedido pedido = gerenciador.buscarPedido(numero);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado!");
        }

        if (caixa.pedidoFoiPago(numero)) {
            throw new IllegalStateException("Não é possível modificar um pedido pago!");
        }

        return pedido;
    }

    public Transacao pagarPedido(int numero, double gorjeta) {
        Pedido pedido = exigirPedidoEditavel(numero);

        if (pedido.getItens().isEmpty()) {
            throw new IllegalStateException("Não é possível pagar um pedido vazio!");
        }

        Transacao transacao = new Transacao(numero, pedido.calcularTotal(), gorjeta);
        caixa.registrarTransacao(transacao);

        return transacao;
    }

    public boolean pedidoFoiPago(int numero) {
        return caixa.pedidoFoiPago(numero);
    }

    public Collection<Pedido> listarPedidos() {
        return gerenciador.getPedidos();
    }

    public double calcularTotalDoDia() {
        return gerenciador.calcularTotalDoDia();
    }

    public double calcularTotalVendas() {
        return caixa.calcularTotalVendas();
    }

    public double calcularTotalGorjetas() {
        return caixa.calcularTotalGorjetas();
    }

    public double calcularTotalRecebido() {
        return caixa.calcularTotalRecebido();
    }

    public int contarPagamentos() {
        return caixa.contarPagamentos();
    }

    public double calcularTotalPendente() {
        double total = 0;

        for (Pedido pedido : gerenciador.getPedidos()) {
            if (!caixa.pedidoFoiPago(pedido.getNumero())) {
                total += pedido.calcularTotal();
            }
        }

        return total;
    }

    public Set<String> obterCombinacoes() {
        return gerenciador.obterCombinacoes();
    }

    public Set<String> obterLanchesBase() {
        return gerenciador.obterLanchesBase();
    }
}
