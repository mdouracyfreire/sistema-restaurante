package cardapio;

public class Lanche implements ItemCardapio {
    private String nome;
    private double preco;

    private boolean queijo = true;
    private boolean alface = true;
    private boolean tomate = true;
    private boolean cebola = true;

    public Lanche(String nome, double preco) {
        setNome(nome);
        setPreco(preco);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do lanche inválido!");
        }

        this.nome = nome.trim();
    }

    @Override
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (!Double.isFinite(preco) || preco < 0) {
            throw new IllegalArgumentException("Preço inválido!");
        }

        this.preco = preco;
    }

    public boolean isQueijo() {
        return queijo;
    }

    public void setQueijo(boolean queijo) {
        this.queijo = queijo;
    }

    public boolean isAlface() {
        return alface;
    }

    public void setAlface(boolean alface) {
        this.alface = alface;
    }

    public boolean isTomate() {
        return tomate;
    }

    public void setTomate(boolean tomate) {
        this.tomate = tomate;
    }

    public boolean isCebola() {
        return cebola;
    }

    public void setCebola(boolean cebola) {
        this.cebola = cebola;
    }

    @Override
    public String getNomeBase() {
        return nome;
    }

    @Override
    public String getDescricao() {
        String descricao = nome;

        if (!queijo) descricao += ", sem queijo";
        if (!alface) descricao += ", sem alface";
        if (!tomate) descricao += ", sem tomate";
        if (!cebola) descricao += ", sem cebola";

        return descricao;
    }
}
