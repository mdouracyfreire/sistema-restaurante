package cardapio.adicionais;

import cardapio.ItemCardapio;

public class ComBaconExtra implements ItemCardapio {
    private final ItemCardapio item;

    public ComBaconExtra(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo!");
        }

        this.item = item;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + bacon extra";
    }

    @Override
    public String getNomeBase() {
        return item.getNomeBase();
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 6.0;
    }
}
