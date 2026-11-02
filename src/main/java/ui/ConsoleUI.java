package ui;

import java.util.Locale;

public final class ConsoleUI {
    private static final Locale BRASIL = Locale.forLanguageTag("pt-BR");
    private static final int LARGURA = 52;
    private static final boolean USAR_CORES = true;
    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String AMARELO = "\u001B[33m";
    private static final String CIANO = "\u001B[36m";

    private ConsoleUI() {}

    public static void limparTela() {
        System.out.print("\u001B[2J\u001B[H");
        System.out.flush();
    }

    private static String colorir(String texto, String cor) {
        return USAR_CORES ? cor + texto + RESET : texto;
    }

    public static void linha() {
        System.out.println("-".repeat(LARGURA));
    }

    public static void cabecalho(String titulo) {
        System.out.println();
        linha();
        System.out.println(colorir("  " + titulo.toUpperCase(BRASIL), CIANO));
        linha();
    }

    public static void secao(String titulo) {
        System.out.println();
        System.out.println(colorir("  > " + titulo.toUpperCase(BRASIL), AMARELO));
        linha();
    }

    public static void opcao(int numero, String descricao) {
        System.out.printf(BRASIL, "  [%d] %s%n", numero, descricao);
    }

    public static void opcao(int numero, String descricao, double preco) {
        System.out.printf(BRASIL, "  [%d] %-24s %s%n", numero, descricao, moeda(preco));
    }

    public static void campo(String descricao, String valor) {
        System.out.printf(BRASIL, "  %-23s %s%n", descricao, valor);
    }

    public static String moeda(double valor) {
        return String.format(BRASIL, "R$ %.2f", valor);
    }

    public static void sucesso(String mensagem) {
        System.out.println();
        System.out.println(colorir("  [OK] " + mensagem, VERDE));
    }

    public static void erro(String mensagem) {
        System.out.println();
        System.out.println(colorir("  [ERRO] " + mensagem, VERMELHO));
    }

    public static void aviso(String mensagem) {
        System.out.println();
        System.out.println(colorir("  [!] " + mensagem, AMARELO));
    }

    public static void info(String mensagem) {
        System.out.println("  " + mensagem);
    }

    public static void pergunta(String mensagem) {
        System.out.print(colorir("  > " + mensagem, CIANO));
        System.out.flush();
    }
}
