package barbeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Scanner sc = new Scanner(System.in);

		int nCadeiras = lerInteiroPositivo(sc, "Quantas cadeiras na barbearia? ");
		int nClientes = lerInteiroPositivo(sc, "Quantos clientes vao chegar? ");

		Barbearia barbearia = new Barbearia(nCadeiras);

		Barbeiro barbeiro = new Barbeiro("Joao", barbearia);
		barbeiro.start();

		List<Cliente> clientes = new ArrayList<>();
		Random r = new Random();

		for (int i = 0; i < nClientes; i++) {
			Cliente c = new Cliente("C" + (i + 1), barbearia);
			clientes.add(c);
			c.start();
			// chegada irregular dos clientes (1 a 5 segundos)
			Thread.sleep((1 + r.nextInt(5)) * 1000L);
		}

		// espera todos os clientes terminarem (atendidos ou desistencias)
		for (Cliente c : clientes) {
			c.join();
		}

		// fecha a barbearia para o barbeiro ir embora
		barbearia.fechar();
		barbeiro.join();

		System.out.println("Fim do expediente.");
		sc.close();
	}

	/**
	 * Le um inteiro > 0 do teclado, repetindo o prompt em caso de entrada invalida.
	 */
	private static int lerInteiroPositivo(Scanner sc, String prompt) {
		while (true) {
			System.out.print(prompt);
			String linha = sc.nextLine().trim();
			try {
				int valor = Integer.parseInt(linha);
				if (valor > 0) {
					return valor;
				}
				System.out.println("Digite um numero inteiro maior que zero.");
			} catch (NumberFormatException e) {
				System.out.println("Entrada invalida. Digite um numero inteiro.");
			}
		}
	}
}
