package GeneracionCodigo;

import java.util.ArrayList;
import java.util.Stack;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Atributo;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorSintactico.Parser;

public class TercetoManager {
	
	private static ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
	private static Stack<String> stackTercetos = new Stack<String>();
	private static Stack<String> stackTercetosContinue = new Stack<String>();
	private static Stack<String> stackTercetosBreak = new Stack<String>();
	private static Stack<String> stackFunciones = new Stack<String>();
	private static Stack<String> stackAsignacion = new Stack<String>();
	
	public static ArrayList<Terceto> getLista(){
		return tercetos;
	}
	
	public static void crear_terceto(String operando, String operador1, String operador2) {
		Terceto aux = new Terceto(operando,operador1,operador2, "");
		if (!operando.equals("CALL") && !operando.equals("Funcion") && !operando.equals("End_funcion") && !operando.equals("Return") && !operando.equals("BF") && !operando.equals("BI") && !operando.startsWith("Label"))
			aux.setTipoTerceto(TablaTipos.getTipoAbarcativo(operador1, operador2, operando));
		tercetos.add(aux);
	}
	
	public static void crear_terceto(String operando, String operador1, String operador2, String tipo) {
		Terceto aux = new Terceto(operando,operador1,operador2,tipo);
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
	
	public static Terceto getTerceto(String index) {
		return tercetos.get(Integer.parseInt(index.substring(1,index.length()-1)));
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
        getTerceto(indice_cond).setOperador2('['+Integer.toString(tercetos.size()+1)+']'); //completamos el terceto incompleto
        pushTerceto('['+Integer.toString(tercetos.size())+']'); // apilamos terceto para BI incompleto
        crear_terceto("BI","_","_"); //creamos el terceto para la BI incompleto
        crear_terceto("Label"+tercetos.size(), "_", "_"); //creamos el terceto para el salto
    }
	
	 public static void add_seleccion(){
		 	if (!stackTercetos.isEmpty()) {
		        int indice = popTerceto(); // obtengo el indice del terceto BI
		        getTerceto(indice).setOperador1('['+Integer.toString(tercetos.size())+']'); // completamos el terceto de la BI, con el terceto siguiente al then
		 	}
	        crear_terceto("Label" + tercetos.size(), "_", "_"); //creamos el terceto para el salto
	 }	 

	public static void add_inicio_do_until(){
		pushTerceto('['+Integer.toString(tercetos.size())+']');
		stackTercetosContinue.push('['+Integer.toString(tercetos.size())+']');
		crear_terceto("Label"+tercetos.size(), "_", "_");
	}

	public static void add_iter_do_until(){
		crear_terceto("BF", '['+Integer.toString(tercetos.size() - 1)+']','['+Integer.toString(tercetos.size() + 2)+']');
        int indice_cond = popTerceto();
		crear_terceto("BI","["+Integer.toString(indice_cond)+"]","_"); 
		crear_terceto("Label"+tercetos.size(), "_", "_");
		if(!stackTercetosContinue.isEmpty()){
			int aux = popTercetoContinue();
		}
		while(!stackTercetosBreak.isEmpty()) {
			indice_cond = popTercetoBreak();
			getTerceto(indice_cond).setOperador1('['+Integer.toString(tercetos.size() - 1)+']'); 
		}
	}
	
	public static void add_cond_when(){
		int anterior = tercetos.size()-1;
        crear_terceto("BF", "["+anterior+"]", "_");
		pushTerceto('['+Integer.toString(tercetos.size() - 1)+']');
	}
	
	public static void add_iter_when(){
		int indice_cond = popTerceto();
        getTerceto(indice_cond).setOperador2('['+Integer.toString(tercetos.size())+']'); 
		crear_terceto("Label"+tercetos.size(), "_", "_");
	}
	
	public static int popTercetoContinue() { //da el numero del terceto
		String aux = stackTercetosContinue.pop();
		
		aux = aux.substring(1, aux.length()-1);
		
		return Integer.parseInt(aux);		
	}
	
	public static int popTercetoBreak() { //da el numero del terceto
			String aux = stackTercetosBreak.pop();
			aux = aux.substring(1, aux.length()-1);
			return Integer.parseInt(aux);		
	}
	
	public static void continueDoUntil() {
		if(!stackTercetosContinue.isEmpty()){
			int indice = popTercetoContinue();
			crear_terceto("BI","[" + Integer.toString(indice) + "]","_");
			stackTercetosContinue.push("["+indice+"]");
		}
	}
	
	public static void continueDoUntilEtiqueta(String etiqueta) {
		String aux = etiqueta+Ambito.getAmbitoActual();
		if (TablaSimbolos.contieneSimbolo(aux)) {
			Atributo t = TablaSimbolos.obtenerSimbolo(aux);
			if (t.getUso().equals("etiqueta")) {
				crear_terceto("BI", t.getTercetoSalto(), "_");
			}
		}
	}
	
	public static int popTercetoAsignacion() { //da el numero del terceto
		String aux = stackAsignacion.pop();
		aux = aux.substring(1, aux.length()-1);
		return Integer.parseInt(aux);		
	}
	
	public static void pushTercetoAsignacion(String indexTerceto) {
		
		stackAsignacion.push(indexTerceto);
		
	}
	
	public static void add_inicio_id_asig() {
		crear_terceto("Label"+tercetos.size(), "_", "_");
		pushTercetoAsignacion("["+ (tercetos.size()-1)+ "]");
	}
	
	public static void add_break_cte(String id, String cte, String tipo){
		crear_terceto("=:", id, cte, tipo);
		crear_terceto("BI", "_", "_");
		pushTercetoAsignacion("["+(tercetos.size()-1)+"]");
	}
	
	public static void add_else_cte(String id, String cte, String tipo){
		crear_terceto("=:", id, cte, tipo);
	}
	
	public static void add_condicion_id_asig() {
		crear_terceto("BF", "["+ (tercetos.size()-1)+ "]","["+ (tercetos.size()+3)+ "]");
		int indice = popTercetoAsignacion();
		int indiceAux = popTercetoAsignacion();
		crear_terceto("BI", "["+ (indiceAux)+ "]","_");
		pushTercetoAsignacion("["+indice+"]");
	}
	public static void add_fin_id_asig() {
		crear_terceto("Label"+tercetos.size(), "_", "_");
		int indice = popTercetoAsignacion();
		getTerceto(indice).setOperador1('['+Integer.toString(tercetos.size()-1)+']');
	}
	
	public static void breakDoUntil() {
		stackTercetosBreak.push('['+Integer.toString(tercetos.size())+']');
		crear_terceto("BI","_","_"); 
	}
	
	public static void pushTercetoFuncion(String funcion) {
		stackFunciones.push(funcion);
	}
	
	public static int popTercetoFuncion() {
		if (!stackFunciones.isEmpty()) {
			String aux = stackFunciones.pop();
			aux = aux.substring(1, aux.length()-1);
			return Integer.parseInt(aux);
		}
		return -1;
	}
	
	public static void add_funcion(String funcion) {
		crear_terceto("Funcion",funcion,"_");
	}
	
	public static void add_end_funcion(String id) {
		crear_terceto("End_funcion", id, "_");
	}
	
	//Chequear este metodo con el archivo pruebaTercetoDoUntil porque tira error
	public static void add_return_funcion(String id, String retorno) {
		
		if(retorno == null) return;
		
		String tipo = id;
		String tipoOp1;
		if(retorno.startsWith("[")) {
        	int indexTerceto = Integer.parseInt(retorno.substring(1, retorno.length() - 1));
        	tipoOp1 = TercetoManager.getTerceto(indexTerceto).getTipoTerceto();
        }else
        	tipoOp1 = TablaTipos.getTipo(retorno);
		
		if (!tipo.equals(tipoOp1)) {
			if (tipo.equals("f32")) {
				crear_terceto("tof32", retorno, "_", "f32");
				crear_terceto("Return", "["+(getIndexTerceto()-1)+"]", "_", tipo);
			} else if (tipo.equals("i16")) {
				Parser.erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : no se puede convertir entre i16 y f32");
			}
		} else {
			crear_terceto("Return", retorno, "_", tipo);

		}
	}


	public static void llamado_funcion(String funcion) {
		if(TablaSimbolos.contieneSimbolo(funcion)) {
			String tipo = TablaSimbolos.obtenerSimbolo(funcion).getTipo();
			crear_terceto("CALL", funcion, "["+(getIndexTerceto()-1)+"]", tipo);
		}
	}
	
	public static void imprimirTercetos() {
		for (Terceto t : tercetos) {
			System.out.println((Integer.toString(tercetos.indexOf(t))) + ". " + t);
		}
	}
	

	public static void imprimirTercetosSinAmbito() {
		for (Terceto t : tercetos) {
			System.out.println((Integer.toString(tercetos.indexOf(t))) + ". " + t.imprimirTerceto());
		}
	}
}
