package app;

import cardapio.ItemCardapio;
import cardapio.Lanche;
import pedido.Pedido;

import java.util.Collection;
import java.util.Scanner;

public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n==== RESTAURANTE ====");
        System.out.println("1. Registrar novo pedido");
        System.out.println("2. Modificar pedido");
        System.out.println("3. Listar pedidos do dia");
        System.out.println("4. Fechar restaurante");
        System.out.println("0. Sair");
    }

    public int lerOpcao() {
        while (true) {
            System.out.print("Escolha uma opção: ");

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, digite um número!");
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

        return lerOpcao();
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
        while (true) {
            int opcao = lerOpcaoLanche();

            try {
                Lanche lanche = sistema.criarLanche(opcao);
                personalizarLanche(lanche);

                boolean cheddar = confirmar("Adicionar cheddar por R$ 4,00?");
                boolean baconExtra = confirmar("Adicionar bacon extra por R$ 6,00?");

                return sistema.aplicarAdicionais(lanche, cheddar, baconExtra);
            } catch (IllegalArgumentException e) {
                exibirMensagem(e.getMessage());
            }
        }
    }

    public boolean desejaAdicionarMais() {
        return confirmar("Adicionar outro lanche? ");
    }

    public void exibirPedido(Pedido pedido) {
        System.out.println("\nPedido #" + pedido.getNumero());
        System.out.println("Cliente: " + pedido.getNomeCliente());

        for (ItemCardapio item : pedido.getItens()) {
            System.out.printf("- %s: R$ %.2f%n", item.getDescricao(), item.getPreco());
        }

        System.out.printf("Total: R$ %.2f%n", pedido.calcularTotal());
    }

    public void exibirPedidos(Collection<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            exibirMensagem("Nenhum pedido registrado!");
            return;
        }

        for (Pedido pedido : pedidos) {
            exibirPedido(pedido);
        }
    }

    public void exibirFechamentoDoDia(double total) {
        System.out.println("\n==== FECHAMENTO ====");
        System.out.printf("Total vendido: R$ %.2f%n", total);
    }

    public int lerNumeroPedido() {
        System.out.println("Digite o número do pedido: ");
        return lerOpcao();
    }

    public int lerNumeroItem() {
        System.out.println("Digite o número do item: ");
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
        System.out.println("\n==== MODIFICAR ====");
        System.out.println("1. Adicionar item");
        System.out.println("2. Remover item");
        System.out.println("3. Cancelar pedido");
        System.out.println("0. Voltar");
    }
}
