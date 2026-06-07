package barbeiro;

public class Cliente extends Thread{
	
	private String nome;
	private Barbeiro barbeiro;
	private Barbearia barbearia;
	
	public Cliente(String nome, Barbeiro barbeiro, Barbearia barbearia) {
		this.nome = nome;
		this.barbeiro = barbeiro;
		this.barbearia= barbearia;
	}
	
	public void run() {
		try {
			synchronized(barbearia) {
				if(barbearia.temCadeiraDisponivel(this)) {
					barbearia.sentarCadeira(this);
				}else {
					System.out.println("Cliente "+nome+" nao achou cadeira e foi embora.");
					return;
				}
					
				
			}
			
			synchronized(barbeiro){
				if(barbeiro.estaDormindo()) {
					barbeiro.acordar();
					System.out.println("Barbeiro acordou!");
				}
			
				while(barbeiro.estaOcupado()) {
					System.out.println("Cliente "+nome+" esta sentado na cadeira e esperando o barbeiro...");
					barbeiro.wait();
				}
					
				barbeiro.atender(this);
				barbeiro.desocupar();
				barbearia.sairCadeira(this);
			}
				
			System.out.println("Cliente "+nome+" foi atendido e foi embora.");
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
	}
			
	public String getNome() {
		return nome;
	}
	
	
	
	

}
