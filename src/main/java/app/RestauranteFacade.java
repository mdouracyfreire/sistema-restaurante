package app;

import cardapio.*;
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

    public void adicionarItem(int numero, ItemCardapio item) {
        Pedido pedido = gerenciador.buscarPedido(numero);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado!");
        }

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

    public Collection<Pedido> listarPedidos() {
        return gerenciador.getPedidos();
    }

    public double calcularTotalDoDia() {
        return gerenciador.calcularTotalDoDia();
    }
}
