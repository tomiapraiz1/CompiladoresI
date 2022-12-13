package AnalizadorLexico;
import java.util.HashMap;
import java.util.Map.Entry;

import AnalizadorSintactico.Parser;
import GeneracionCodigo.Ambito;

public class TablaSimbolos {
	
	private static HashMap<String, Atributo> tabla = new HashMap<String, Atributo>();
	
	public static HashMap<String, Atributo> getTabla(){
		return tabla;
	}
	
	public static void setTercetoSalto(String key, String tercetoSalto) {
		if(tabla.containsKey(key)) {
			Atributo aux = tabla.get(key);
			aux.setTercetoSalto(tercetoSalto);
		}
	}
	
	public static void modificarTipoParametros(String key, String tipo1, String tipo2) {
		if (tabla.containsKey(key)) {
			Atributo aux = tabla.get(key);
			aux.setTipoP1(tipo1);
			aux.setTipoP2(tipo2);
		}
	}
	
	public static void agregarSimbolo(String lexema, int id, String tipo, int line) {
		Atributo a = new Atributo(lexema, id, tipo, line);
		tabla.put(lexema, a);
	}
	
	public static void agregarSimbolo(String identificador, String lexema,  String tipo) {
		Atributo a = new Atributo(lexema, tipo);
		tabla.put(identificador, a);
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
	
	public static void modificarParametros(String key, int cantidad) {
		if (tabla.containsKey(key)) {
			tabla.get(key).setCantidadParametros(cantidad);
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
			if (obtenerSimbolo(key).getUso().equals("etiqueta"))
				Parser.erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : Etiqueta '" + key + "' redeclarada");
			else if (obtenerSimbolo(key).getUso().equals("variable"))
				Parser.erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : Variable '" + key + "' redeclarada");
			else
				Parser.erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : Funcion '" + key + "' redeclarada");
			tabla.remove(key);
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
