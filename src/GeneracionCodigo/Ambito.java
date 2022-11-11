package GeneracionCodigo;

import java.util.ArrayList;
import AnalizadorLexico.*;

// Main:funcion:do

public class Ambito {
	
	private static ArrayList<String> ambito = new ArrayList<String>();
	
	//private static String Abecedario[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	//private static int indexOf = 0;
	
	public static String getAmbito(String simbolo) { //verifica el ambito de un simbolo
		int ind = ambito.size()-1;
        while (ind>=0){
            String aux = simbolo + getNombreVariable(ind);
            //System.out.println(aux);
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
		//indexOf--;
	}
	
	public static void imprimirAmbitos() {
		System.out.println(ambito);
	}
}

