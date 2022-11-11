package GeneracionCodigo;

import java.util.ArrayList;

// Main:funcion:do

public class Ambito {
	
	private static ArrayList<String> ambito = new ArrayList<String>();
	
	private static String Abecedario[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	private static int indexOf = 0;
	
	public static String getAmbitoActual() {
		
		if (!ambito.isEmpty()) {
			return ambito.get(ambito.size() - 1);
		}
		
		return "Null";
	}
	
	public static void concatenarAmbito(String amb) {
		if (amb == "main")
			ambito.add(amb);
		else
			ambito.add(amb + Abecedario[indexOf]);
	}
	
	public static boolean esVacio() {
		return ambito.isEmpty();
	}
	
	public static void removeAmbito() {
		ambito.remove(ambito.size() - 1);
	}
	
	public static void imprimirAmbitos() {
		System.out.println(ambito);
	}
}

