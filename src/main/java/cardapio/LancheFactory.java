package cardapio;

public class LancheFactory {

    private LancheFactory() {}

    public static Lanche xBurguer() {
        return new Lanche("X-Burguer", 16.0);
    }

    public static Lanche xSalada() {
        return new Lanche("X-Salada", 18.0);
    }

    public static Lanche xBacon() {
        return new Lanche("X-Bacon", 22.0);
    }
}
