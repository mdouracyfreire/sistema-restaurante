package financeiro;

import java.util.Objects;

public class Transacao {
    private final int numeroPedido;
    private final double valor;
    private final double gorjeta;

    public Transacao(int numeroPedido, double valor, double gorjeta) {
        if (numeroPedido <= 0) {
            throw new IllegalArgumentException("Número de pedido inválido!");
        }

        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException("Valor inválido!");
        }

        if (!Double.isFinite(gorjeta) || gorjeta < 0) {
            throw new IllegalArgumentException("Gorjeta inválida!");
        }

        this.numeroPedido = numeroPedido;
        this.valor = valor;
        this.gorjeta = gorjeta;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public double getValor() {
        return valor;
    }

    public double getGorjeta() {
        return gorjeta;
    }

    public double getTotal() {
        return valor + gorjeta;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Transacao outra)) {
            return false;
        }

        return numeroPedido == outra.numeroPedido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedido);
    }
}
