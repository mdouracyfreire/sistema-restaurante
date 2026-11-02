package app;

import financeiro.Transacao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestauranteFacadeTest {
    @Test
    void pedidoPagoNaoPodeSerCancelado() {
        RestauranteFacade sistema = new RestauranteFacade();
        int numero = sistema.criarPedido("Maria").getNumero();

        sistema.adicionarItem(numero, sistema.criarLanche(3));
        sistema.pagarPedido(numero, 5.0);

        assertThrows(IllegalStateException.class, () -> sistema.cancelarPedido(numero));
    }

    @Test
    void pedidoPagoNaoPodeSerModificado() {
        RestauranteFacade sistema = new RestauranteFacade();
        int numero = sistema.criarPedido("Bento").getNumero();

        sistema.adicionarItem(numero, sistema.criarLanche(1));
        sistema.pagarPedido(numero, 0);

        assertThrows(IllegalStateException.class, () -> sistema.adicionarItem(numero, sistema.criarLanche(2)));
    }

    @Test
    void pagamentoDeveRegistrarValorEGorjeta() {
        RestauranteFacade sistema = new RestauranteFacade();
        int numero = sistema.criarPedido("Milla").getNumero();

        sistema.adicionarItem(numero, sistema.criarLanche(3));

        Transacao pagamento = sistema.pagarPedido(numero, 4.0);

        assertEquals(22.0, pagamento.getValor(), 0.001);
        assertEquals(4.0, pagamento.getGorjeta(), 0.001);
        assertEquals(26.0, pagamento.getTotal(), 0.001);
        assertTrue(sistema.pedidoFoiPago(numero));
    }
}
