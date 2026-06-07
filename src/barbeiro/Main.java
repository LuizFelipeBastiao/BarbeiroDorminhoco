package barbeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static void main(String args[]) throws InterruptedException {
		
		Barbearia bar = new Barbearia(3);
		
		List<Cliente> clientes = new ArrayList<>();
		Barbeiro barbeiro = new Barbeiro("Joao",bar);
		barbeiro.start();
		
		for(int i=0; i<15;i++) {
			Random r = new Random();
			Cliente c = new Cliente("C"+(i+1), barbeiro, bar);
			clientes.add(c);
			c.start();
			Thread.sleep((1 + r.nextInt(5))*1000);
		}
		
		for(Cliente c: clientes) {
			c.join();
		}
	}

}
