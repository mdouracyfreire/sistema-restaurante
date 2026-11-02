package app;

import cardapio.ItemCardapio;
import financeiro.Transacao;
import pedido.Pedido;
import ui.ConsoleUI;
import ui.MenuConsole;

public class Main {
    public static void main(String[] args) {
        RestauranteFacade sistema = new RestauranteFacade();
        MenuConsole menu = new MenuConsole();
        boolean executando = true;

        while (executando) {
            ConsoleUI.limparTela();
            menu.exibirMenu();
            int opcao = menu.lerOpcaoEntre(0, 5);

            switch (opcao) {
                case 1 -> {
                    try {
                        String cliente = menu.lerNomeCliente();
                        Pedido pedido = sistema.criarPedido(cliente);
                        boolean continuar;

                        do {
                            ItemCardapio item = menu.montarItem(sistema);

                            sistema.adicionarItem(pedido.getNumero(), item);
                            menu.exibirResumoItem(item);
                            continuar = menu.desejaAdicionarMais();
                        } while (continuar);

                        ConsoleUI.limparTela();
                        ConsoleUI.sucesso("Pedido registrado!");
                        menu.exibirPedido(pedido);

                    } catch (IllegalArgumentException | IllegalStateException e) {
                        ConsoleUI.erro(e.getMessage());
                    }

                    menu.aguardarEnter();
                }

                case 2 -> {
                    ConsoleUI.limparTela();
                    ConsoleUI.cabecalho("Gerenciar pedido");

                    int numero = menu.lerNumeroPedido();
                    Pedido pedido = sistema.buscarPedido(numero);

                    if (pedido == null) {
                        ConsoleUI.erro("Pedido não encontrado!");
                        menu.aguardarEnter();
                        break;
                    }

                    if (sistema.pedidoFoiPago(numero)) {
                        ConsoleUI.aviso("Pedidos pagos não podem ser modificados!");
                        menu.aguardarEnter();
                        break;
                    }

                    boolean modificando = true;

                    while (modificando) {
                        ConsoleUI.limparTela();
                        menu.exibirPedido(pedido);
                        menu.exibirMenuModificacao();
                        int escolha = menu.lerOpcaoEntre(0, 3);

                        try {
                            switch (escolha) {
                                case 1 -> {
                                    ItemCardapio item = menu.montarItem(sistema);

                                    sistema.adicionarItem(numero, item);
                                    menu.exibirResumoItem(item);
                                    menu.aguardarEnter();
                                }

                                case 2 -> {
                                    if (pedido.getItens().isEmpty()) {
                                        ConsoleUI.aviso("Não há itens para remover.");
                                        menu.aguardarEnter();
                                        break;
                                    }

                                    ConsoleUI.limparTela();
                                    ConsoleUI.cabecalho("Remover item");
                                    menu.exibirItensNumerados(pedido);

                                    int indice = menu.lerNumeroItem() - 1;

                                    sistema.removerItem(numero, indice);
                                    ConsoleUI.sucesso("Item removido com sucesso!");
                                    menu.aguardarEnter();
                                }

                                case 3 -> {
                                    ConsoleUI.limparTela();
                                    ConsoleUI.cabecalho("Cancelar pedido");

                                    if (menu.confirmar("Deseja cancelar este pedido?")) {
                                        sistema.cancelarPedido(numero);
                                        ConsoleUI.sucesso("Pedido cancelado!");

                                        modificando = false;
                                    } else {
                                        ConsoleUI.info("Cancelamento não realizado.");
                                    }

                                    menu.aguardarEnter();
                                }

                                case 0 -> modificando = false;
                            }

                        } catch (IllegalArgumentException | IllegalStateException e) {
                            ConsoleUI.erro(e.getMessage());
                            menu.aguardarEnter();
                        }
                    }
                }

                case 3 -> {
                    ConsoleUI.limparTela();
                    ConsoleUI.cabecalho("Pagamento");

                    int numero = menu.lerNumeroPedido();
                    Pedido pedido = sistema.buscarPedido(numero);

                    if (pedido == null) {
                        ConsoleUI.erro("Pedido não encontrado!");
                        menu.aguardarEnter();
                        break;
                    }

                    if (sistema.pedidoFoiPago(numero)) {
                        ConsoleUI.aviso("Este pedido já foi pago!");
                        menu.aguardarEnter();
                        break;
                    }

                    ConsoleUI.limparTela();
                    menu.exibirPedido(pedido);

                    if (!menu.confirmar("Deseja registrar o pagamento?")) {
                        ConsoleUI.info("Pagamento não realizado.");
                        menu.aguardarEnter();
                        break;
                    }

                    double gorjeta = menu.lerGorjeta();

                    try {
                        Transacao transacao = sistema.pagarPedido(numero, gorjeta);
                        menu.exibirPagamento(transacao);

                    } catch (IllegalArgumentException | IllegalStateException e) {
                        ConsoleUI.erro(e.getMessage());
                    }

                    menu.aguardarEnter();
                }

                case 4 -> {
                    menu.exibirPedidos(sistema.listarPedidos(), sistema);
                    menu.aguardarEnter();
                }

                case 5 -> {
                    menu.exibirFechamentoDoDia(
                            sistema.contarPagamentos(),
                            sistema.calcularTotalVendas(),
                            sistema.calcularTotalGorjetas(),
                            sistema.calcularTotalRecebido(),
                            sistema.calcularTotalPendente()
                    );

                    menu.exibirCombinacoes(sistema.obterCombinacoes());
                    menu.exibirLanchesBase(sistema.obterLanchesBase());
                    menu.aguardarEnter();

                    executando = false;
                }

                case 0 -> {
                    ConsoleUI.limparTela();
                    ConsoleUI.cabecalho("Encerrando sistema...");
                    ConsoleUI.info("Obrigado por utilizar o Restaurante!");

                    executando = false;
                }
            }
        }
    }
}
