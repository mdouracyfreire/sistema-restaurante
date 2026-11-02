package ui;

import app.RestauranteFacade;
import cardapio.ItemCardapio;
import cardapio.Lanche;
import financeiro.Transacao;
import pedido.Pedido;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);

    public void aguardarEnter() {
        System.out.println();
        ConsoleUI.pergunta("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void exibirMenu() {
        ConsoleUI.cabecalho("Restaurante");
        ConsoleUI.info("Sistema de gerenciamento de pedidos");

        ConsoleUI.secao("Menu principal");

        ConsoleUI.opcao(1, "Registrar novo pedido");
        ConsoleUI.opcao(2, "Gerenciar pedido");
        ConsoleUI.opcao(3, "Finalizar pagamento");
        ConsoleUI.opcao(4, "Consultar pedidos do dia");
        ConsoleUI.opcao(5, "Fechamento do dia");
        ConsoleUI.opcao(0, "Sair do sistema");

        ConsoleUI.linha();
    }

    public int lerOpcao() {
        while (true) {
            ConsoleUI.pergunta("Escolha uma opção: ");

            try {
                return Integer.parseInt(
                        scanner.nextLine().trim()
                );
            } catch (NumberFormatException e) {
                ConsoleUI.erro("Digite um número inteiro válido!");
            }
        }
    }

    public int lerOpcaoEntre(int minimo, int maximo) {
        while (true) {
            int opcao = lerOpcao();

            if (opcao >= minimo && opcao <= maximo) {
                return opcao;
            }

            ConsoleUI.aviso("Escolha uma opção entre " + minimo + " e " + maximo + "!");
        }
    }

    public String lerNomeCliente() {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Novo pedido");
        ConsoleUI.pergunta("Nome do cliente: ");

        return scanner.nextLine().trim();
    }

    public int lerOpcaoLanche() {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Nosso cardápio");

        ConsoleUI.opcao(1, "X-Burguer", 16.00);
        ConsoleUI.opcao(2, "X-Salada", 18.00);
        ConsoleUI.opcao(3, "X-Bacon", 22.00);

        ConsoleUI.linha();

        return lerOpcaoEntre(1, 3);
    }

    public boolean confirmar(String pergunta) {
        while (true) {
            ConsoleUI.pergunta(pergunta + " [s/n]: ");

            String resposta = scanner.nextLine().trim();

            if (resposta.equalsIgnoreCase("s")) {
                return true;
            }

            if (resposta.equalsIgnoreCase("n")) {
                return false;
            }

            ConsoleUI.aviso("Resposta inválida. Digite 's' ou 'n'!");
        }
    }

    public void personalizarLanche(Lanche lanche) {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Personalização do lanche");

        ConsoleUI.campo("Lanche:", lanche.getDescricao());

        ConsoleUI.secao("Ingredientes");
        ConsoleUI.info("Responda 's' para retirar o ingrediente.");
        System.out.println();

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

    public ItemCardapio montarItem(RestauranteFacade sistema) {
        int opcao = lerOpcaoLanche();
        Lanche lanche = sistema.criarLanche(opcao);
        personalizarLanche(lanche);

        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Adicionais");

        ConsoleUI.campo("Lanche:", lanche.getDescricao());
        ConsoleUI.secao("Opções extras");

        ConsoleUI.info("Cheddar: + " + ConsoleUI.moeda(4.00));
        ConsoleUI.info("Bacon extra: + " + ConsoleUI.moeda(6.00));
        System.out.println();

        boolean cheddar = confirmar("Adicionar cheddar?");
        boolean baconExtra = confirmar("Adicionar bacon extra?");

        return sistema.aplicarAdicionais(lanche, cheddar, baconExtra);
    }

    public boolean desejaAdicionarMais() {
        System.out.println();
        return confirmar("Adicionar outro lanche?");
    }

    public void exibirMensagem(String mensagem) {
        ConsoleUI.info(mensagem);
    }

    public void exibirResumoItem(ItemCardapio item) {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Item adicionado");

        ConsoleUI.sucesso("Lanche registrado no pedido!");
        ConsoleUI.secao("Resumo do item");

        ConsoleUI.campo("Descrição:", item.getDescricao());
        ConsoleUI.campo("Valor:", ConsoleUI.moeda(item.getPreco()));
    }

    public void exibirPedido(Pedido pedido) {
        ConsoleUI.cabecalho("Pedido #" + pedido.getNumero());

        ConsoleUI.campo("Cliente:", pedido.getNomeCliente());
        ConsoleUI.campo("Situação:", pedido.isPago() ? "PAGO" : "EM ABERTO");
        ConsoleUI.secao("Itens do pedido");

        if (pedido.getItens().isEmpty()) {
            ConsoleUI.aviso("Nenhum item adicionado!");
        } else {
            exibirItensNumerados(pedido);
        }

        ConsoleUI.linha();
        ConsoleUI.campo("TOTAL:", ConsoleUI.moeda(pedido.calcularTotal()));
    }

    public void exibirPedidos(
            Collection<Pedido> pedidos,
            RestauranteFacade sistema
    ) {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Pedidos do dia");

        if (pedidos.isEmpty()) {
            ConsoleUI.aviso("Nenhum pedido registrado!");
            return;
        }

        List<Pedido> ordenados = new ArrayList<>(pedidos);
        ordenados.sort(Comparator.comparingInt(Pedido::getNumero));

        int pagos = 0;
        int abertos = 0;

        for (Pedido pedido : ordenados) {
            exibirPedido(pedido);

            if (sistema.pedidoFoiPago(pedido.getNumero())) {
                pagos++;
            } else {
                abertos++;
            }
        }

        ConsoleUI.secao("Resumo dos pedidos");

        ConsoleUI.campo("Pedidos registrados:", String.valueOf(ordenados.size()));
        ConsoleUI.campo("Pagos:", String.valueOf(pagos));
        ConsoleUI.campo("Em aberto:", String.valueOf(abertos));
    }

    public void exibirItensNumerados(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            ConsoleUI.aviso("O pedido não possui itens!");
            return;
        }

        int numero = 1;

        for (ItemCardapio item : pedido.getItens()) {
            System.out.println();

            ConsoleUI.info("[" + numero++ + "] " + item.getDescricao());
            ConsoleUI.campo("Valor:", ConsoleUI.moeda(item.getPreco()));
        }
    }

    public void exibirMenuModificacao() {
        ConsoleUI.secao("Gerenciar pedido");

        ConsoleUI.opcao(1, "Adicionar item");
        ConsoleUI.opcao(2, "Remover item");
        ConsoleUI.opcao(3, "Cancelar pedido");
        ConsoleUI.opcao(0, "Voltar ao menu");

        ConsoleUI.linha();
    }

    public int lerNumeroPedido() {
        ConsoleUI.pergunta("Número do pedido: ");
        return lerInteiro();
    }

    public int lerNumeroItem() {
        ConsoleUI.pergunta("Número do item: ");
        return lerInteiro();
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(
                        scanner.nextLine().trim()
                );
            } catch (NumberFormatException e) {
                ConsoleUI.aviso("Digite um número inteiro válido!");
                ConsoleUI.pergunta("Tente novamente: ");
            }
        }
    }

    public double lerGorjeta() {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Registrar gorjeta");

        while (true) {
            ConsoleUI.pergunta("Valor da gorjeta (0 para nenhuma): R$ ");
            String entrada = scanner.nextLine().trim().replace(",", ".");

            try {
                double gorjeta = Double.parseDouble(entrada);

                if (Double.isFinite(gorjeta) && gorjeta >= 0) {
                    return gorjeta;
                }

                ConsoleUI.aviso("A gorjeta deve ser maior ou igual a zero!");

            } catch (NumberFormatException e) {
                ConsoleUI.aviso("Digite um valor numérico válido!");
            }
        }
    }

    public void exibirPagamento(Transacao transacao) {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Pagamento confirmado");

        ConsoleUI.sucesso("Pagamento registrado com sucesso!");
        ConsoleUI.campo("Pedido:", "#" + transacao.getNumeroPedido());

        ConsoleUI.secao("Comprovante");
        ConsoleUI.campo("Valor dos lanches:", ConsoleUI.moeda(transacao.getValor()));
        ConsoleUI.campo("Gorjeta:", ConsoleUI.moeda(transacao.getGorjeta()));

        ConsoleUI.linha();
        ConsoleUI.campo("TOTAL PAGO:", ConsoleUI.moeda(transacao.getTotal()));
    }

    public void exibirFechamentoDoDia(int pagamentos, double vendas, double gorjetas, double recebido, double pendente) {
        ConsoleUI.limparTela();
        ConsoleUI.cabecalho("Fechamento do dia");

        ConsoleUI.secao("Resumo financeiro");

        ConsoleUI.campo("Pedidos pagos:", String.valueOf(pagamentos));
        ConsoleUI.campo("Vendas:", ConsoleUI.moeda(vendas));
        ConsoleUI.campo("Gorjetas:", ConsoleUI.moeda(gorjetas));

        ConsoleUI.linha();
        ConsoleUI.campo("TOTAL RECEBIDO:", ConsoleUI.moeda(recebido));
        ConsoleUI.campo("Valores pendentes:", ConsoleUI.moeda(pendente));
    }

    public void exibirCombinacoes(Set<String> combinacoes) {
        ConsoleUI.secao("Combinações vendidas");
        ConsoleUI.campo("Combinações diferentes:", String.valueOf(combinacoes.size()));

        if (combinacoes.isEmpty()) {
            ConsoleUI.info("Nenhuma combinação vendida.");
            return;
        }

        List<String> ordenadas = new ArrayList<>(combinacoes);
        ordenadas.sort(String::compareToIgnoreCase);

        for (String descricao : ordenadas) {
            ConsoleUI.info("- " + descricao);
        }
    }

    public void exibirLanchesBase(Set<String> lanchesBase) {
        ConsoleUI.secao("Lanches base vendidos");
        ConsoleUI.campo("Lanches diferentes:", String.valueOf(lanchesBase.size()));

        if (lanchesBase.isEmpty()) {
            ConsoleUI.info("Nenhum lanche vendido.");
        } else {
            List<String> ordenados = new ArrayList<>(lanchesBase);
            ordenados.sort(String::compareToIgnoreCase);

            for (String nome : ordenados) {
                ConsoleUI.info("- " + nome);
            }
        }

        ConsoleUI.linha();
        ConsoleUI.sucesso("Fechamento concluído!");
    }
}
