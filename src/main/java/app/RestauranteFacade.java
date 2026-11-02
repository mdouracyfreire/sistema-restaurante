package app;

import cardapio.Lanche;
import cardapio.LancheFactory;
import pedido.GerenciadorPedidos;
import pedido.Pedido;

import java.util.Collection;

public class RestauranteFacade {
    private final GerenciadorPedidos gerenciador = new GerenciadorPedidos();

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

    public void adicionarLanche(int numero, int opcao) {
        Pedido pedido = gerenciador.buscarPedido(numero);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado!");
        }

        pedido.adicionarItem(criarLanche(opcao));
    }

    public Collection<Pedido> listarPedidos() {
        return gerenciador.getPedidos();
    }

    public double calcularTotalDoDia() {
        return gerenciador.calcularTotalDoDia();
    }
}
