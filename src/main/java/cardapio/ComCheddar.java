package cardapio;

public class ComCheddar implements ItemCardapio{
    private final ItemCardapio item;

    public ComCheddar(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo!");
        }

        this.item = item;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + cheddar";
    }

    @Override
    public String getNomeBase() {
        return item.getNomeBase();
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 4.0;
    }
}
