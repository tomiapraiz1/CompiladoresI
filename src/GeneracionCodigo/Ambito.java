package GeneracionCodigo;

import java.util.ArrayList;
import AnalizadorLexico.*;

// Main:funcion:do

public class Ambito {
	
	private static ArrayList<String> ambito = new ArrayList<String>();
	
    public static String sinAmbito(String simbolo) {
    	if (simbolo.contains(":"))
    		return simbolo.substring(0, simbolo.indexOf(':'));
    	else
    		return simbolo;
    }
	
	public static String getAmbito(String simbolo) { //verifica el ambito de un simbolo
		int ind = ambito.size()-1;
        while (ind>=0){
            String aux = simbolo + getNombreVariable(ind);
            if (TablaSimbolos.contieneSimbolo(aux)){
                TablaSimbolos.eliminarSimbolo(simbolo);
                return aux;
            }

            ind--;
        }
        return null;
	}
	
	public static String getNombreVariable(int indice){ //devuelve el nombre de una variable con su ambito
        StringBuilder nombre = new StringBuilder();
        int i= 0;
        while (i<=indice){
            nombre.append(":").append(ambito.get(i));
            i++;
        }
        return nombre.toString();
    }
	
	public static String getAmbitoActual(){ //devuelve el nombre del ambito en el que estamos
        StringBuilder nombre = new StringBuilder();
        for (String a : ambito){
            nombre.append(":").append(a);
        }
        return nombre.toString();
    }
	
	public static void concatenarAmbito(String amb) {
		ambito.add(amb);
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

