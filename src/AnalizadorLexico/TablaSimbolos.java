package AnalizadorLexico;
import java.util.HashMap;
import java.util.Map.Entry;

import GeneracionCodigo.Ambito;

public class TablaSimbolos {
	
	private static HashMap<String, Atributo> tabla = new HashMap<String, Atributo>();
	
	public static void agregarSimbolo(String lexema, int id, String tipo, int line) {
		Atributo a = new Atributo(lexema, id, tipo, line);
		tabla.put(lexema, a);
	}
	
	public static Atributo obtenerSimbolo(String lexema) {
	
		if (tabla.containsKey(lexema))
			return tabla.get(lexema);
		return null;
	}
	
	public static boolean contieneSimbolo(String key) {
		return tabla.containsKey(key);
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
	
	public static String modificarNombre(String key) {
		String nuevo = key + Ambito.getAmbitoActual();
		if (tabla.containsKey(nuevo)) {
			System.out.println("Error");
			return "Null";
		}
		Atributo t = tabla.remove(key);
		t.setAmbito(Ambito.getAmbitoActual());
		tabla.put(nuevo, t);
		return nuevo;
	}
	
	public static void imprimirTabla() {
        System.out.println("\nTabla de Simbolos:");

        for (Entry<String, Atributo> e : tabla.entrySet()) {
            System.out.print(e.getKey() + ": " + e.getValue());
            System.out.println();
        }
    }
}
