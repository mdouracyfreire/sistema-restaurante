package app;

import cardapio.ItemCardapio;
import cardapio.Lanche;
import financeiro.Transacao;
import pedido.Pedido;

import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n==== RESTAURANTE ====");
        System.out.println("1. Registrar novo pedido");
        System.out.println("2. Modificar pedido");
        System.out.println("3. Pagar pedido");
        System.out.println("4. Listar pedidos do dia");
        System.out.println("5. Fechar restaurante");
        System.out.println("0. Sair");
    }

    public int lerOpcao() {
        while (true) {
            System.out.print("Digite aqui: ");

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                exibirMensagem("Entrada inválida, digite um número!");
            }
        }
    }

    public String lerNomeCliente() {
        System.out.print("Nome do cliente: ");
        return scanner.nextLine();
    }

    public int lerOpcaoLanche() {
        System.out.println("\n==== CARDÁPIO ====");
        System.out.println("1. X-Burguer: R$ 16,00");
        System.out.println("2. X-Salada: R$ 18,00");
        System.out.println("3. X-Bacon: R$ 22,00");

        return lerOpcaoEntre(1, 3);
    }

    public boolean confirmar(String pergunta) {
        while (true) {
            System.out.print(pergunta + " (s/n): ");
            String resposta = scanner.nextLine().trim();

            if (resposta.equalsIgnoreCase("s")) {
                return true;
            }

            if (resposta.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Digite 's' ou 'n'!");
        }
    }

    public void personalizarLanche(Lanche lanche){
        if (confirmar("Retirar queijo?")) {
            lanche.setQueijo(false);
        }

        if (confirmar("Retirar alface?")) {
            lanche.setAlface(false);
        }

        if (confirmar("Retirar tomate?")) {
            lanche.setTomate(false);
        }

        if (confirmar("Retirar cebola?")) {
            lanche.setCebola(false);
        }
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public ItemCardapio montarItem(RestauranteFacade sistema) {
        int opcao = lerOpcaoLanche();
        Lanche lanche = sistema.criarLanche(opcao);
        personalizarLanche(lanche);

        boolean cheddar = confirmar("Adicionar cheddar por R$ 4,00?");
        boolean baconExtra = confirmar("Adicionar bacon extra por R$ 6,00?");

        return sistema.aplicarAdicionais(lanche, cheddar, baconExtra);
    }

    public boolean desejaAdicionarMais() {
        return confirmar("Adicionar outro lanche?");
    }

    public void exibirPedido(Pedido pedido) {
        System.out.println("\nPedido #" + pedido.getNumero());
        System.out.println("Cliente: " + pedido.getNomeCliente());

        if (pedido.getItens().isEmpty()) {
            exibirMensagem("Nenhum item no pedido!");
        }

        for (ItemCardapio item : pedido.getItens()) {
            System.out.printf("- %s: R$ %.2f%n", item.getDescricao(), item.getPreco());
        }

        System.out.printf("Total: R$ %.2f%n", pedido.calcularTotal());
    }

    public void exibirPedidos(Collection<Pedido> pedidos, RestauranteFacade sistema) {
        if (pedidos.isEmpty()) {
            exibirMensagem("Nenhum pedido registrado!");
            return;
        }

        for (Pedido pedido : pedidos) {
            exibirPedido(pedido);

            String status = sistema.pedidoFoiPago(pedido.getNumero()) ? "PAGO" : "ABERTO";
            System.out.println("Status: " + status);
        }
    }

    public void exibirFechamentoDoDia(int pagamentos, double vendas, double gorjetas, double recebido, double pendente) {
        System.out.println("\n==== FECHAMENTO DO DIA ====");
        System.out.println("Pedidos pagos: " + pagamentos);
        System.out.printf("Vendas recebidas: R$ %.2f%n", vendas);
        System.out.printf("Gorjetas: R$ %.2f%n", gorjetas);
        System.out.printf("Total recebido: R$ %.2f%n", recebido);
        System.out.printf("Pedidos em aberto: R$ %.2f%n", pendente);
    }

    public int lerNumeroPedido() {
        System.out.println("Qual o número do pedido?");
        return lerOpcao();
    }

    public int lerNumeroItem() {
        System.out.println("Qual o número do item? ");
        return lerOpcao();
    }

    public void exibirItensNumerados(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            exibirMensagem("O pedido não possui itens!");
            return;
        }

        int numero = 1;
        for (ItemCardapio item : pedido.getItens()) {
            System.out.printf("%d - %s: R$ %.2f%n", numero++, item.getDescricao(), item.getPreco());
        }
    }

    public void exibirMenuModificacao() {
        System.out.println("\n==== MODIFICAR PEDIDO ====");
        System.out.println("1. Adicionar item");
        System.out.println("2. Remover item");
        System.out.println("3. Cancelar pedido");
        System.out.println("0. Voltar");
    }

    public void exibirCombinacoes(Set<String> combinacoes) {
        System.out.println("\n==== COMBINAÇÕES DIFERENTES ====");
        System.out.println("Quantidade: " + combinacoes.size());

        for (String descricao : combinacoes) {
            System.out.println("- " + descricao);
        }
    }

    public int lerOpcaoEntre(int minimo, int maximo) {
        while (true) {
            int opcao = lerOpcao();

            if (opcao >= minimo && opcao <= maximo) {
                return opcao;
            }

            exibirMensagem("Opção inválida, escolha um número de " + minimo + " a " + maximo + "!");
        }
    }

    public double lerGorjeta() {
        while (true) {
            System.out.println("Valor da gorjeta (0 para nenhuma): R$ ");
            String entrada = scanner.nextLine().trim().replace(",", ".");

            try {
                double gorjeta = Double.parseDouble(entrada);

                if (Double.isFinite(gorjeta) && gorjeta >= 0) {
                    return gorjeta;
                }

                exibirMensagem("A gorjeta deve ser um valor válido e não negativo!");
            } catch (NumberFormatException e) {
                exibirMensagem("Digite um valor numérico válido!");
            }
        }
    }

    public void exibirPagamento(Transacao transacao) {
        System.out.println("\n==== PAGAMENTO REGISTRADO ====");
        System.out.println("Pedido #" + transacao.getNumeroPedido());
        System.out.printf("Lanches: R$ %.2f%n", transacao.getValor());
        System.out.printf("Gorjeta: R$ %.2f%n", transacao.getGorjeta());
        System.out.printf("Total pago: R$ %.2f%n", transacao.getTotal());
    }

    public void exibirLanchesBase(Set<String> lanchesBase) {
        System.out.println("\n==== LANCHES BASE DIFERENTES ====");
        System.out.println("Quantidade: " + lanchesBase.size());

        for (String nome : lanchesBase) {
            System.out.println("- " + nome);
        }
    }
}
