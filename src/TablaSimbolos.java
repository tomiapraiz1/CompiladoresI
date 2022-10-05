import java.util.HashMap;
import java.util.Map.Entry;

public class TablaSimbolos {
	
	private static HashMap<Integer, HashMap<String,String>> tabla = new HashMap<Integer, HashMap<String, String>>();
	
	private static int id = 1;
	
	public void agregarSimbolo(String simbolo) {
		HashMap<String,String> atributo = new HashMap<String, String>();
		atributo.put("Lexema", simbolo);
		tabla.put(id, atributo);
		id++;
	}
	
	public int obtenerSimbolo(String lexema) {
		for (Entry<Integer, HashMap<String,String>> e : tabla.entrySet()) {
			String lexema_act = e.getValue().get("Lexema");
			if (lexema_act.equals(lexema))
				return e.getKey();
		}
		return -1;
	}
	
	public void eliminarSimbolo(int key) {
		if (tabla.containsKey(key))
			tabla.remove(key);
	}
	
	public void agregarAtributo(int key, String atributo, String valor) {
        if (tabla.containsKey(key)) {
            HashMap<String, String> atributos = tabla.get(key);
            atributos.put(atributo, valor);
        }  
    }
	
	public static void imprimirTabla() {
        System.out.println("\nTablaSimbolos:");

        for (Entry<Integer, HashMap<String, String>> e : tabla.entrySet()) {
            HashMap<String, String> atributos = e.getValue();
            System.out.print(e.getKey() + ": ");

            for (Entry<String, String> atributo: atributos.entrySet()) {
                System.out.print("(" + atributo.getKey() + ": " + atributo.getValue() + ") ");
            }

            System.out.println();
        }
    }
}
