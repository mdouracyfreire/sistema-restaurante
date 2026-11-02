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

        primeiro.marcarComoPago();
        segundo.marcarComoPago();

        assertEquals(1, gerenciador.obterCombinacoes().size());
    }

    @Test
    void pedidosNaoPagosNaoDevemContarComoVendas() {
        GerenciadorPedidos gerenciador = new GerenciadorPedidos();
        Pedido primeiro = gerenciador.criaPedido("Ravena");
        Pedido segundo = gerenciador.criaPedido("Bruno");

        primeiro.adicionarItem(LancheFactory.xBurguer());
        segundo.adicionarItem(LancheFactory.xBacon());

        primeiro.marcarComoPago();

        assertEquals(1, gerenciador.obterCombinacoes().size());
        assertEquals(1, gerenciador.obterLanchesBase().size());
        assertTrue(gerenciador.obterCombinacoes().contains("X-Burguer"));
        assertFalse(gerenciador.obterCombinacoes().contains("X-Bacon"));
    }

    @Test
    void lanchesBaseRepetidosDevemContarUmaVez() {
        GerenciadorPedidos gerenciador = new GerenciadorPedidos();

        Pedido primeiro = gerenciador.criaPedido("Julia");
        Pedido segundo = gerenciador.criaPedido("Marcos");

        primeiro.adicionarItem(LancheFactory.xSalada());
        segundo.adicionarItem(LancheFactory.xSalada());

        primeiro.marcarComoPago();
        segundo.marcarComoPago();

        assertEquals(1, gerenciador.obterLanchesBase().size());
        assertTrue(gerenciador.obterLanchesBase().contains("X-Salada"));
    }
}
