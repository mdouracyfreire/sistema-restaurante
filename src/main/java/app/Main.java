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

                case 2 -> menu.exibirPedidos(sistema.listarPedidos());

                case 3 -> {
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
