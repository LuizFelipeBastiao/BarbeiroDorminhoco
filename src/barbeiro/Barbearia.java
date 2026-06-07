package barbeiro;

public class Barbearia {
	public int cadeirasDisponiveis;
	private int cadeiras;
	
	public Barbearia (int cadeiras) {
		this.cadeiras = cadeiras;
		cadeirasDisponiveis = cadeiras;
	}
	
	public synchronized boolean temCadeiraDisponivel(Cliente c) throws InterruptedException{
		System.out.println("Cliente "+c.getNome()+" esta vericando se tem cadeira disponivel.");
		if(cadeirasDisponiveis == 0) {
			return false;
		}
			return true;
	}
	
	public boolean temClientes() throws InterruptedException{
		if(cadeirasDisponiveis == cadeiras) {
			return false;
		}
		notifyAll();
		return true;
	}
	
	public synchronized void sentarCadeira(Cliente c) throws InterruptedException{
		System.out.println("Cliente "+c.getNome()+" sentou na cadeira");
		cadeirasDisponiveis --;
		notifyAll();
	}
	
	public synchronized void sairCadeira(Cliente c) throws InterruptedException{
		System.out.println("Cliente "+c.getNome()+" saiu da cadeira");
		
		cadeirasDisponiveis ++;
		notifyAll();
	}
	
}
