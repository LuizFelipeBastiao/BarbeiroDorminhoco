package barbeiro;

/**
 * Thread do cliente. So conversa com o monitor (Barbearia):
 *  - se houver cadeira, senta e espera o atendimento;
 *  - se nao, vai embora imediatamente.
 */
public class Cliente extends Thread {

	private final String nome;
	private final Barbearia barbearia;

	public Cliente(String nome, Barbearia barbearia) {
		this.nome = nome;
		this.barbearia = barbearia;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public void run() {
		try {
			barbearia.entrar(this);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
