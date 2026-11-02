package pedido;

import cardapio.ItemCardapio;

import java.util.*;

public class GerenciadorPedidos {
    private final HashMap<Integer, Pedido> pedidos = new HashMap<>();
    private int contadorPedidos = 1;

    public Pedido criaPedido(String nomeCliente) {
        Pedido pedido = new Pedido(contadorPedidos, nomeCliente);
        pedidos.put(pedido.getNumero(), pedido);
        contadorPedidos++;

        return pedido;
    }

    public Pedido buscarPedido(int numero) {
        return pedidos.get(numero);
    }

    public Collection<Pedido> getPedidos() {
        return List.copyOf(pedidos.values());
    }

    public boolean cancelarPedido(int numero) {
        return pedidos.remove(numero) != null;
    }

    public double calcularTotalDoDia() {
        double total = 0;

        for (Pedido pedido : pedidos.values()) {
            total += pedido.calcularTotal();
        }

        return total;
    }

    public Set<String> obterCombinacoes() {
        HashSet<String> combinacoes = new HashSet<>();

        for (Pedido pedido : pedidos.values()) {
            for (ItemCardapio item : pedido.getItens()) {
                combinacoes.add(item.getDescricao());
            }
        }

        return combinacoes;
    }

    public Set<String> obterLanchesBase() {
        HashSet<String> nomesBase = new HashSet<>();

        for (Pedido pedido : pedidos.values()) {
            for (ItemCardapio item : pedido.getItens()) {
                nomesBase.add(item.getNomeBase());
            }
        }

        return nomesBase;
    }
}
