package app;

import cardapio.Lanche;
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
            case 1 -> new Lanche("X-Burguer", 16);
            case 2 -> new Lanche("X-Salada", 18);
            case 3 -> new Lanche("X-Bacon", 22);
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
