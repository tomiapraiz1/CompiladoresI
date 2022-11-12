package GeneracionCodigo;

import java.util.ArrayList;
import java.util.Stack;

public class TercetoManager {
	
	private static ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
	private static Stack<String> stackTercetos = new Stack<String>();
	
	public static void crear_terceto(String operando, String operador1, String operador2) {
		Terceto aux = new Terceto(operando,operador1,operador2);
		tercetos.add(aux);
	}
	
	public static int getIndexTerceto() {
		return tercetos.size();
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
	
	public static int popTerceto() { //da el numero del terceto
		String aux = stackTercetos.pop();
		
		aux = aux.substring(1, aux.length()-1);
		
		return Integer.parseInt(aux);		
	}
	
	public static void add_seleccion_cond(){
        stackTercetos.push('['+Integer.toString(tercetos.size())+']'); //agregamos el indice del terceto incompleto que mas adelante se completara.
        int anterior = tercetos.size()-1;
        crear_terceto("BF", "["+anterior+"]", "_");
    }
	
	public static void add_seleccion_then(){
        int indice_cond = popTerceto(); //obtengo el indice del terceto incompleto de la condicion
        getTerceto(indice_cond).setOperador2('['+Integer.toString(tercetos.size()+1)+']'); //completamos el terceto incompleto que se agrego por el void de arriba
        pushTerceto('['+Integer.toString(tercetos.size())+']'); // apilamos terceto para BI incompleto
        crear_terceto("BI","_","_"); //generamos el terceto para la BI incompleto
    }
	
	 public static void add_seleccion(){
	        int indice = popTerceto(); // obtengo el indice del terceto BI
	        getTerceto(indice).setOperador1('['+Integer.toString(tercetos.size())+']'); // le seteamos al terceto de la BI, el terceto siguiente el cual es al que tiene que saltar una vez ejecutada la rama del then
	 }

	public static void imprimirTercetos() {
		for (Terceto t : tercetos) {
			System.out.println((Integer.toString(tercetos.indexOf(t))) + ". " + t);
		}
	}

	public static void add_inicio_do_until(){
		pushTerceto('['+Integer.toString(tercetos.size())+']');
	}

	public static void add_iter_do_until(){
		int indice_cond = popTerceto();
        getTerceto(indice_cond).setOperador2('['+Integer.toString(tercetos.size()+1)+']'); 
        indice_cond = popTerceto();
		crear_terceto("BI",Integer.toString(indice_cond),"_"); 
	}
	
	
}
