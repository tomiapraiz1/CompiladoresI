package GeneracionCodigo;

import java.util.ArrayList;
import java.util.Stack;

public class TercetoManager {
	
	private ArrayList<Terceto> tercetos;
	private Stack<String> stackTercetos;
	public static int size = 0;
	
	TercetoManager(){
		this.tercetos = new ArrayList<Terceto>();
		this.stackTercetos = new Stack<String>();
	}
	
	public void crear_terceto(String operando, String operador1, String operador2) {
		size++;
		Terceto aux = new Terceto(operando,operador1,operador2,size);
		this.tercetos.add(aux);
	}
	
	public int getIndexTerceto(Terceto t) {
		return this.tercetos.indexOf(t);
	}
	
	public Terceto getTerceto(int index) {
		return tercetos.get(index);
	}
	
	public void pushTerceto(String indexTerceto) {
		
		stackTercetos.push(indexTerceto);
		
	}
	
	public int popTerceto() {
		String aux = stackTercetos.pop();
		
		aux = aux.substring(1, aux.length()-1);
		
		return Integer.parseInt(aux);		
	}
}
