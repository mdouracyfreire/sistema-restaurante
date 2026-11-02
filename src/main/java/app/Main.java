package app;

import cardapio.ItemCardapio;
import financeiro.Transacao;
import pedido.Pedido;

public class Main {
    public static void main(String[] args) {
        RestauranteFacade sistema = new RestauranteFacade();
        MenuConsole menu = new MenuConsole();
        boolean executando = true;

        while (executando) {
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
                            menu.exibirMensagem("Lanche adicionado!");
                            continuar = menu.desejaAdicionarMais();
                        }while (continuar);

                        menu.exibirPedido(pedido);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        menu.exibirMensagem(e.getMessage());
                    }
                }

                case 2 -> {
                    int numero = menu.lerNumeroPedido();
                    Pedido pedido = sistema.buscarPedido(numero);

                    if (pedido == null) {
                        menu.exibirMensagem("Pedido não encontrado!");
                        break;
                    }

                    if (sistema.pedidoFoiPago(numero)) {
                        menu.exibirMensagem("Pedidos pagos não podem ser modificados!");
                        break;
                    }

                    boolean modificando = true;

                    while (modificando) {
                        menu.exibirPedido(pedido);
                        menu.exibirMenuModificacao();

                        int escolha = menu.lerOpcaoEntre(0, 3);

                        try {
                            switch (escolha) {
                                case 1 -> {
                                    ItemCardapio item = menu.montarItem(sistema);
                                    sistema.adicionarItem(numero, item);
                                    menu.exibirMensagem("Item adicionado!");
                                }

                                case 2 -> {
                                    if (pedido.getItens().isEmpty()) {
                                        menu.exibirMensagem("Não há itens para remover!");
                                        break;
                                    }

                                    menu.exibirItensNumerados(pedido);
                                    int indice = menu.lerNumeroItem() - 1;

                                    sistema.removerItem(numero, indice);
                                    menu.exibirMensagem("Item removido!");
                                }

                                case 3 -> {
                                    if (menu.confirmar("Deseja cancelar este pedido?")) {
                                        sistema.cancelarPedido(numero);
                                        menu.exibirMensagem("Pedido cancelado!");
                                        modificando = false;
                                    }
                                }

                                case 0 -> modificando = false;
                            }
                        } catch (IllegalArgumentException | IllegalStateException e) {
                            menu.exibirMensagem(e.getMessage());
                        }
                    }
                }

                case 3 -> {
                    int numero = menu.lerNumeroPedido();
                    Pedido pedido = sistema.buscarPedido(numero);

                    if (pedido == null) {
                        menu.exibirMensagem("Pedido não encontrado!");
                        break;
                    }

                    if (sistema.pedidoFoiPago(numero)) {
                        menu.exibirMensagem("Este pedido já foi pago!");
                        break;
                    }

                    menu.exibirPedido(pedido);
                    if(!menu.confirmar("Deseja registrar o pagamento?")) {
                        break;
                    }

                    double gorjeta = menu.lerGorjeta();
                    try {
                        Transacao transacao = sistema.pagarPedido(numero, gorjeta);
                        menu.exibirPagamento(transacao);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        menu.exibirMensagem(e.getMessage());
                    }
                }

                case 4 -> menu.exibirPedidos(sistema.listarPedidos(), sistema);

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
                    executando = false;
                }

                case 0 -> {
                    menu.exibirMensagem("Sistema encerrado!");
                    executando = false;
                }
            }
        }
    }
}
