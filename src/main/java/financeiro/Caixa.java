package financeiro;

import java.util.HashSet;
import java.util.Set;

public class Caixa {
    private final HashSet<Transacao> transacoes = new HashSet<>();

    public void registrarTransacao(Transacao transacao) {
        if (transacao == null) {
            throw new IllegalArgumentException("Transação não pode se nula!");
        }

        if (!transacoes.add(transacao)) {
            throw new IllegalStateException("Este pedido já foi pago!");
        }
    }

    public boolean pedidoFoiPago(int numeroPedido) {
        return transacoes.stream()
                .anyMatch(t -> t.getNumeroPedido() == numeroPedido);
    }

    public double calcularTotalVendas() {
        double total = 0;

        for (Transacao transacao : transacoes) {
            total += transacao.getValor();
        }

        return total;
    }

    public double calcularTotalGorjetas() {
        double total = 0;

        for (Transacao transacao : transacoes) {
            total += transacao.getGorjeta();
        }

        return total;
    }

    public double calcularTotalRecebido() {
        return calcularTotalVendas() + calcularTotalGorjetas();
    }

    public int contarPagamentos() {
        return transacoes.size();
    }

    public Set<Transacao> getTransacoes() {
        return Set.copyOf(transacoes);
    }
}
