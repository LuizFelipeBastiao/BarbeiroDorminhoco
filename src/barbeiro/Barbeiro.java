package barbeiro;

public class Barbeiro extends Thread{
	private String nome;
	private boolean dormindo = false;
	private boolean cortandoCabelo = false;
	private Barbearia barbearia;
	
	public Barbeiro(String nome, Barbearia barbearia) {
		this.nome = nome;
		this.barbearia = barbearia;
	}
	
	public void run() {
		try {
			synchronized(barbearia) {
				while(!barbearia.temClientes()) {
					System.out.println("Sem clientes, barbeiro esta dormindo...");
					tirarSoneca();
					barbearia.wait();
				}
			}
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
	}
	
	public synchronized void atender(Cliente cliente) throws InterruptedException{
		System.out.println("Atendendo o cliente "+ cliente.getNome());
		cortandoCabelo = true;
		for(int i =1 ; i<=3;i++) {
			System.out.println("Cortando o cabelo do cliente "+ cliente.getNome()+"...(tic tic)");
			Thread.sleep(500);
		}
		desocupar();
	}
	
	public String getNome() {
		return nome;
	}
	
	public boolean estaDormindo() {
		return dormindo;
	}
	
	public synchronized boolean estaOcupado() throws InterruptedException{
		
		return cortandoCabelo;
	}
	
	public synchronized void desocupar() throws InterruptedException{
		cortandoCabelo = false;
		notifyAll();
	}
	
	public void acordar() {
		dormindo = false;
	}
	
	public void tirarSoneca() {
		dormindo = true;
	}
}
