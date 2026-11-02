package pedido;

import cardapio.ItemCardapio;
import cardapio.ItemRegistrado;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final int numero;
    private String nomeCliente;
    private final ArrayList<ItemCardapio> itens = new ArrayList<>();
    private boolean pago = false;

    public Pedido(int numero, String nomeCliente) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número do pedido inválido!");
        }

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
        verificarEditavel();

        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório!");
        }

        this.nomeCliente = nomeCliente.trim();
    }

    public boolean isPago() {
        return pago;
    }

    public void marcarComoPago() {
        if (pago) {
            throw new IllegalStateException("Este pedido já foi pago!");
        }

        if (itens.isEmpty()) {
            throw new IllegalStateException("Não é possível pagar um pedido vazio!");
        }

        pago = true;
    }

    public void adicionarItem(ItemCardapio item) {
        verificarEditavel();

        if (item == null) {
            throw new IllegalArgumentException("Um item precisa ser adicionado!");
        }

        itens.add(new ItemRegistrado(item));
    }


    public List<ItemCardapio> getItens() {
        return List.copyOf(itens);
    }

    public void removerItem(int indice) {
        verificarEditavel();

        if (indice < 0 || indice >= itens.size()) {
            throw new IllegalArgumentException("Item não encontrado!");
        }

        itens.remove(indice);
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemCardapio item : itens) {
            total += item.getPreco();
        }

        return total;
    }

    private void verificarEditavel() {
        if (pago) {
            throw new IllegalStateException(
                    "Não é possível modificar um pedido já pago!"
            );
        }
    }
}
