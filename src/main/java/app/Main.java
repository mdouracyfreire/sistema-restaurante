package app;

import cardapio.ItemCardapio;
import pedido.Pedido;

public class Main {
    public static void main(String[] args) {
        RestauranteFacade sistema = new RestauranteFacade();
        MenuConsole menu = new MenuConsole();
        boolean executando = true;

        while (executando) {
            menu.exibirMenu();
            int opcao = menu.lerOpcao();

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
                    } catch (IllegalArgumentException e) {
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

                    boolean modificando = true;

                    while (modificando) {
                        menu.exibirPedido(pedido);
                        menu.exibirMenuModificacao();

                        int escolha = menu.lerOpcao();

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

                                try {
                                    sistema.removerItem(numero, indice);
                                    menu.exibirMensagem("Item removido!");
                                } catch (IllegalArgumentException e) {
                                    menu.exibirMensagem(e.getMessage());
                                }
                            }

                            case 3 -> {
                                if (menu.confirmar("Deseja cancelar este pedido?")) {
                                    if (sistema.cancelarPedido(numero)) {
                                        menu.exibirMensagem("Pedido cancelado!");
                                        modificando = false;
                                    } else {
                                        menu.exibirMensagem("Não foi possível cancelar o pedido!");
                                    }
                                }
                            }

                            case 0 -> modificando = false;

                            default -> menu.exibirMensagem("Opção inválida!");
                        }
                    }
                }

                case 3 -> menu.exibirPedidos(sistema.listarPedidos());

                case 4 -> {
                    double total = sistema.calcularTotalDoDia();

                    menu.exibirFechamentoDoDia(total);
                    executando = false;
                }

                case 0 -> {
                    menu.exibirMensagem("Sistema encerrado!");
                    executando = false;
                }

                default -> menu.exibirMensagem("Opção inválida!");
            }
        }
    }
}
