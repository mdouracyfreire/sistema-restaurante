package app;

import cardapio.ItemCardapio;
import cardapio.Lanche;
import cardapio.LancheFactory;
import cardapio.adicionais.ComBaconExtra;
import cardapio.adicionais.ComCheddar;
import financeiro.Caixa;
import financeiro.Transacao;
import pedido.GerenciadorPedidos;
import pedido.Pedido;

import java.util.Collection;
import java.util.Set;

public class RestauranteFacade {
    private final GerenciadorPedidos gerenciador = new GerenciadorPedidos();
    private final Caixa caixa = new Caixa();

    public Pedido criarPedido(String nomeCliente) {
        return gerenciador.criaPedido(nomeCliente);
    }

    public Pedido buscarPedido(int numero) {
        return gerenciador.buscarPedido(numero);
    }

    public Lanche criarLanche(int opcao) {
        return switch (opcao) {
            case 1 -> LancheFactory.xBurguer();
            case 2 -> LancheFactory.xSalada();
            case 3 -> LancheFactory.xBacon();
            default -> throw new IllegalArgumentException(
                    "Opção de lanche inválida!"
            );
        };
    }

    public ItemCardapio aplicarAdicionais(
            ItemCardapio item,
            boolean cheddar,
            boolean baconExtra) {

        if (item == null) {
            throw new IllegalArgumentException("Item inválido!");
        }

        if (cheddar) {
            item = new ComCheddar(item);
        }

        if (baconExtra) {
            item = new ComBaconExtra(item);
        }

        return item;
    }

    public void adicionarItem(int numeroPedido, ItemCardapio item) {
        Pedido pedido = exigirPedidoEditavel(numeroPedido);
        pedido.adicionarItem(item);
    }

    public void removerItem(int numeroPedido, int indice) {
        Pedido pedido = exigirPedidoEditavel(numeroPedido);
        pedido.removerItem(indice);
    }

    public boolean cancelarPedido(int numeroPedido) {
        exigirPedidoEditavel(numeroPedido);
        return gerenciador.cancelarPedido(numeroPedido);
    }

    public Transacao pagarPedido(int numeroPedido, double gorjeta) {
        Pedido pedido = exigirPedidoEditavel(numeroPedido);

        if (pedido.getItens().isEmpty()) {
            throw new IllegalStateException(
                    "Não é possível pagar um pedido vazio!"
            );
        }

        Transacao transacao = new Transacao(
                numeroPedido,
                pedido.calcularTotal(),
                gorjeta
        );

        caixa.registrarTransacao(transacao);
        pedido.marcarComoPago();

        return transacao;
    }

    public boolean pedidoFoiPago(int numeroPedido) {
        return caixa.pedidoFoiPago(numeroPedido);
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
            if (!pedido.isPago()) {
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

    private Pedido exigirPedidoEditavel(int numeroPedido) {
        Pedido pedido = gerenciador.buscarPedido(numeroPedido);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado!");
        }

        if (pedido.isPago() || caixa.pedidoFoiPago(numeroPedido)) {
            throw new IllegalStateException(
                    "Não é possível modificar um pedido já pago!"
            );
        }

        return pedido;
    }
}
