package cardapio;

public final class ItemRegistrado implements ItemCardapio {
    private final String descricao;
    private final String nomeBase;
    private final double preco;

    public ItemRegistrado(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo!");
        }

        this.descricao = item.getDescricao();
        this.nomeBase = item.getNomeBase();
        this.preco = item.getPreco();
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getNomeBase() {
        return nomeBase;
    }

    @Override
    public double getPreco() {
        return preco;
    }
}
