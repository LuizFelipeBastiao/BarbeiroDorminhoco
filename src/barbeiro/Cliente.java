package barbeiro;

public class Cliente extends Thread{
	
	private String nome;
	
	public Cliente(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

}
