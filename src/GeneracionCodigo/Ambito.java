package GeneracionCodigo;

public class Ambito {
	
	private static StringBuilder ambito = new StringBuilder();
	private static int tam = 0;
	
	public static char getAmbitoActual() {
		
		if (!ambito.isEmpty()) {
			return ambito.charAt(tam - 1);
		}
		
		return '1';
	}
	
	public static void concatenarAmbito(String amb) {
		ambito.append(amb);
		tam += 2;
	}
	
	public static boolean esVacio() {
		return tam == 0;
	}
	
	public static void removeAmbito() {
		 ambito.deleteCharAt(tam - 1);
		 ambito.deleteCharAt(tam - 2);
		 tam -= 2;
	}
	
	public static void imprimirAmbitos() {
		System.out.println(ambito);
	}
}

