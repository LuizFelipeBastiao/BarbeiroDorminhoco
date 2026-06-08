package barbeiro;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Monitor da barbearia. Centraliza toda a sincronizacao:
 *  - controla as N cadeiras de espera;
 *  - faz o barbeiro dormir/acordar;
 *  - garante que cada cliente seja atendido por vez.
 */
public class Barbearia {

	private final int cadeiras;
	private final Queue<Cliente> filaEspera = new LinkedList<>();
	private Cliente clienteAtendido = null;
	private boolean barbeiroDormindo = false;
	private boolean cortando = false;
	private boolean fechada = false;

	public Barbearia(int cadeiras) {
		this.cadeiras = cadeiras;
	}

	/**
	 * Chamado pela thread do cliente.
	 * Retorna true se o cliente conseguiu ser atendido, false se foi embora
	 * por nao haver cadeira disponivel.
	 */
	public synchronized boolean entrar(Cliente c) throws InterruptedException {
		System.out.println("Cliente " + c.getNome() + " chegou na barbearia.");

		// Se nao ha cadeira livre, o cliente vai embora imediatamente.
		if (filaEspera.size() >= cadeiras) {
			System.out.println("Cliente " + c.getNome()
				+ " nao achou cadeira livre e foi embora.");
			return false;
		}

		// Senta numa cadeira de espera.
		filaEspera.add(c);
		System.out.println("Cliente " + c.getNome() + " sentou na cadeira ("
			+ filaEspera.size() + "/" + cadeiras + " ocupadas).");

		// Se o barbeiro estava dormindo, o cliente o acorda.
		if (barbeiroDormindo) {
			System.out.println("Cliente " + c.getNome() + " acordou o barbeiro!");
		}
		notifyAll();

		// Espera ate o barbeiro pegar este cliente e terminar o corte.
		while (clienteAtendido != c || cortando) {
			wait();
		}

		// Corte concluido.
		clienteAtendido = null;
		notifyAll();
		System.out.println("Cliente " + c.getNome() + " foi atendido e foi embora.");
		return true;
	}

	/**
	 * Chamado pela thread do barbeiro.
	 * Bloqueia (dorme) ate aparecer um cliente, entao retorna o proximo da fila.
	 * Retorna null se a barbearia foi fechada.
	 */
	public synchronized Cliente proximoCliente() throws InterruptedException {
		while (filaEspera.isEmpty()) {
			if (fechada) {
				return null;
			}
			barbeiroDormindo = true;
			System.out.println("Sem clientes, barbeiro esta tirando uma soneca... zzz");
			wait();
		}
		barbeiroDormindo = false;
		clienteAtendido = filaEspera.poll();
		cortando = true;
		return clienteAtendido;
	}

	/**
	 * Chamado pela thread do barbeiro ao concluir o corte.
	 * Libera o cliente atendido para sair da barbearia.
	 */
	public synchronized void terminarCorte(Cliente c) {
		System.out.println("Barbeiro terminou o corte de " + c.getNome() + ".");
		cortando = false;
		notifyAll();
	}

	/**
	 * Encerra a simulacao: faz o barbeiro sair do wait() e terminar.
	 */
	public synchronized void fechar() {
		fechada = true;
		notifyAll();
	}
}
