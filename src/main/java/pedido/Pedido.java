package pedido;

import cardapio.Lanche;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final int numero;
    private String nomeCliente;
    private final ArrayList<Lanche> itens = new ArrayList<>();

    public Pedido(int numero, String nomeCliente) {
        this.numero = numero;
        setNomeCliente(nomeCliente);
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório!");
        }
        this.nomeCliente = nomeCliente;
    }

    public void adicionarItem(Lanche lanche) {
        if (lanche == null) {
            throw new IllegalArgumentException("Um lanche precisa ser adicionado!");
        }
        itens.add(lanche);
    }

    public List<Lanche> getItens() {
        return List.copyOf(itens);
    }

    public double calcularTotal() {
        double total = 0;

        for (Lanche item : itens) {
            total += item.getPreco();
        }

        return total;
    }
}
