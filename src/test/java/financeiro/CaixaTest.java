package financeiro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaixaTest {
    @Test
    void transacoesDoMesmoPedidoNaoPodemDuplicar() {
        Caixa caixa = new Caixa();
        Transacao primeira = new Transacao(1, 22.0, 5.0);
        Transacao duplicada = new Transacao(1, 22.0, 10.0);

        caixa.registrarTransacao(primeira);

        assertThrows(IllegalStateException.class, () -> caixa.registrarTransacao(duplicada));
        assertEquals(1, caixa.contarPagamentos());
    }

    @Test
    void fechamentoDeveSomarVendasEGorjetas() {
        Caixa caixa = new Caixa();

        caixa.registrarTransacao(new Transacao(1, 22.0, 5.0));
        caixa.registrarTransacao(new Transacao(2, 18.0, 3.0));

        assertAll(
                () -> assertEquals(40.0, caixa.calcularTotalVendas(), 0.001),
                () -> assertEquals(8.0, caixa.calcularTotalGorjetas(), 0.001),
                () -> assertEquals(48.0, caixa.calcularTotalRecebido(), 0.001)
                );
    }

    @Test
    void gorjetaNegativaDeveSerRecusada() {
        assertThrows(IllegalArgumentException.class, () -> new Transacao(1, 46.0, -5.0));
    }
}
