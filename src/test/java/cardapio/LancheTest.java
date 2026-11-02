package cardapio;

import cardapio.adicionais.ComBaconExtra;
import cardapio.adicionais.ComCheddar;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LancheTest {
    @Test
    void precoNegativoDeveSerRecusado() {
        assertThrows(IllegalArgumentException.class, () -> new Lanche("X-Burguer", -5.0));
    }

    @Test
    void nomeVazioDeveSerRecusado() {
        assertThrows(IllegalArgumentException.class, () -> new Lanche(" ", 16.0));
    }

    @Test
    void xBaconComCheddarCusta26() {
        ItemCardapio lanche = new ComCheddar(LancheFactory.xBacon());

        assertEquals(26.0, lanche.getPreco(), 0.001);
    }

    @Test
    void xBaconComDoisAdicionaisCusta32() {
        ItemCardapio lanche = new ComBaconExtra(new ComCheddar(LancheFactory.xBacon()));

        assertEquals(32.0, lanche.getPreco(), 0.001);
        assertEquals("X-Bacon + cheddar + bacon extra", lanche.getDescricao());
    }
}
