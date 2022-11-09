package AnalizadorLexico;
import java.util.HashMap;
import java.util.Map.Entry;

public class TablaSimbolos {
	
	private static HashMap<String, Atributo> tabla = new HashMap<String, Atributo>();
	
	public static void agregarSimbolo(String lexema, int id, String tipo, int line) {
		Atributo a = new Atributo(lexema, id, tipo, line);
		tabla.put(lexema, a);
	}
	
	public static int obtenerSimbolo(String lexema) {
		if (tabla.containsKey(lexema))
			return tabla.get(lexema).getIdToken();
		return -1;
	}
	
	public static void eliminarSimbolo(String key) {
		if (tabla.containsKey(key))
			tabla.remove(key);
	}
	
	public static void modificarTipo(String key, String tipo) {
        if (tabla.containsKey(key)) {
            tabla.get(key).setTipo(tipo);
        }  
    }
	
	public static void modificarUso(String key, String uso) {
        if (tabla.containsKey(key)) {
            tabla.get(key).setUso(uso);
        }  
    }
	
	public static void imprimirTabla() {
        System.out.println("\nTabla de Simbolos:");

        for (Entry<String, Atributo> e : tabla.entrySet()) {
            System.out.print(e.getKey() + ": " + e.getValue());
            System.out.println();
        }
    }
}
