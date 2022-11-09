package GeneracionCodigo;

import java.util.ArrayList;
import java.util.Stack;

public class TercetoManager {
	
	private static ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
	private static Stack<String> stackTercetos = new Stack<String>();
	public static int size = 0;
	
	public static String crear_terceto(String operando, String operador1, String operador2) {
		size++;
		Terceto aux = new Terceto(operando,operador1,operador2,size);
		tercetos.add(aux);
		return "[" + size + "]";
	}
	
	public static int getIndexTerceto(Terceto t) {
		return tercetos.indexOf(t);
	}
	
	public static Terceto getTerceto(int index) {
		return tercetos.get(index);
	}
	
	public static void pushTerceto(String indexTerceto) {
		
		stackTercetos.push(indexTerceto);
		
	}
	
	public static int popTerceto() {
		String aux = stackTercetos.pop();
		
		aux = aux.substring(1, aux.length()-1);
		
		return Integer.parseInt(aux);		
	}

	public static void imprimirTercetos() {
		for (Terceto t : tercetos) {
			System.out.println(t);
		}
	}
	
	
}
