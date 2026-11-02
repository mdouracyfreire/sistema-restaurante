package pedido;

import cardapio.LancheFactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorPedidosTest {
    @Test
    void fechamentoDeveSomarTodosOsPedidos() {
        GerenciadorPedidos gerenciador = new GerenciadorPedidos();
        Pedido primeiro = gerenciador.criaPedido("Pedro");
        Pedido segundo = gerenciador.criaPedido("Lola");

        primeiro.adicionarItem(LancheFactory.xBurguer());
        segundo.adicionarItem(LancheFactory.xBacon());

        assertEquals(38.0, gerenciador.calcularTotalDoDia(), 0.001);
    }

    @Test
    void cancelamentoDeveRetirarPedidoDoTotal() {
        GerenciadorPedidos gerenciador = new GerenciadorPedidos();
        Pedido pedido = gerenciador.criaPedido("Alice");

        pedido.adicionarItem(LancheFactory.xSalada());
        gerenciador.cancelarPedido(pedido.getNumero());

        assertNull(gerenciador.buscarPedido(pedido.getNumero()));
        assertEquals(0.0, gerenciador.calcularTotalDoDia(), 0.001);
    }

    @Test
    void combinacoesRepetidasDevemContarUmaVez() {
        GerenciadorPedidos gerenciador = new GerenciadorPedidos();
        Pedido primeiro = gerenciador.criaPedido("Caio");
        Pedido segundo = gerenciador.criaPedido("Liss");

        primeiro.adicionarItem(LancheFactory.xBacon());
        segundo.adicionarItem(LancheFactory.xBacon());

        assertEquals(1, gerenciador.obterCombinacoes().size());
    }
}
