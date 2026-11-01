package pedido;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

    public double calcularTotalDoDia() {
        double total = 0;

        for (Pedido pedido : pedidos.values()) {
            total += pedido.calcularTotal();
        }

        return total;
    }
}
