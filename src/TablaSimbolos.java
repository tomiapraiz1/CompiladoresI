import java.util.HashMap;
import java.util.Map.Entry;

public class TablaSimbolos {
	
	private static HashMap<Integer, HashMap<String,String>> tabla = new HashMap<Integer, HashMap<String, String>>();
	
	private static int id = 1;
	
	public static void agregarSimbolo(String simbolo) {
		HashMap<String,String> atributo = new HashMap<String, String>();
		atributo.put("Lexema", simbolo);
		tabla.put(id, atributo);
		id++;
	}
	
	public static int obtenerSimbolo(String lexema) {
		for (Entry<Integer, HashMap<String,String>> e : tabla.entrySet()) {
			String lexema_act = e.getValue().get("Lexema");
			if (lexema_act.equals(lexema))
				return e.getKey();
		}
		return -1;
	}
	
	public static void eliminarSimbolo(int key) {
		if (tabla.containsKey(key))
			tabla.remove(key);
	}
	
	public static void agregarAtributo(int key, String atributo, String valor) {
        if (tabla.containsKey(key)) {
            HashMap<String, String> atributos = tabla.get(key);
            atributos.put(atributo, valor);
        }  
    }
	
	public static void imprimirTabla() {
        System.out.println("\nTabla de Simbolos:");

        for (Entry<Integer, HashMap<String, String>> e : tabla.entrySet()) {
            HashMap<String, String> atributos = e.getValue();
            System.out.print(e.getKey() + ": ");

            for (Entry<String, String> atributo: atributos.entrySet()) {
                System.out.print("(" + atributo.getKey() + ": " + atributo.getValue() + ") ");
            }

            System.out.println();
        }
    }
	
	/*public static void main(String[] args) {
		
		TablaSimbolos t = new TablaSimbolos();
		t.agregarSimbolo("Boca");
		t.agregarAtributo(1, "Hinchada", "La mitad + 1");
		t.agregarSimbolo("Riber");
		t.agregarAtributo(2, "Hinchada", "Frio");
		t.imprimirTabla();
	}*/
}
