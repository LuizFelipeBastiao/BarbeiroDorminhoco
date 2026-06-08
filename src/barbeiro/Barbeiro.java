package barbeiro;

/**
 * Thread do barbeiro. Em loop:
 *  1) pede o proximo cliente ao monitor (dorme se nao houver);
 *  2) simula o corte;
 *  3) avisa o monitor que terminou.
 */
public class Barbeiro extends Thread {

	private final String nome;
	private final Barbearia barbearia;

	public Barbeiro(String nome, Barbearia barbearia) {
		this.nome = nome;
		this.barbearia = barbearia;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Cliente c = barbearia.proximoCliente();
				if (c == null) {
					// barbearia fechada
					break;
				}
				cortar(c);
				barbearia.terminarCorte(c);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println("Barbeiro " + nome + " foi para casa.");
	}

	private void cortar(Cliente c) throws InterruptedException {
		System.out.println("Barbeiro " + nome + " comecou a cortar o cabelo de "
			+ c.getNome() + ".");
		for (int i = 1; i <= 3; i++) {
			System.out.println("Cortando cabelo de " + c.getNome() + "... (tic tic)");
			Thread.sleep(1500);
		}
	}
}
