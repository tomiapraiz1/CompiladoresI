package GeneracionCodigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import AnalizadorLexico.*;

public class GeneradorAssembler {
	
    final static String jmp = "JMP";
    final static String jle = "JLE";
    final static String jge = "JGE";
    final static String jg = "JG";
    final static String jl = "JL";
    final static String je = "JE";
    final static String jne = "JNE";
    
    //private StringBuilder lineaAsembler;
    private static ArrayList<StringBuilder> codigo =new ArrayList<StringBuilder>();
    private static ArrayList<StringBuilder> datos =new ArrayList<StringBuilder>();
    private static HashMap<String, String> variablesAuxiliares = new HashMap<String, String>();
    private static Integer auxDisponible = 0;
    private static Integer etiquetas=0;
    private static Integer extras=0;
    private static Integer numero=0;
    
    private static String DIVISIONPORCERO = "";
    private static String auxDivision = "@auxDivision";
    
    private static void generarCabecera() {
        StringBuilder s = new StringBuilder();
       
    	s.append(".386\n")
            .append(".model flat, stdcall\n")
            .append("option casemap :none\n")
            .append("include \\masm32\\include\\windows.inc\n")
            .append("include \\masm32\\include\\kernel32.inc\n")
            .append("include \\masm32\\include\\user32.inc\n")
            .append("includelib \\masm32\\lib\\kernel32.lib\n")
            .append("includelib \\masm32\\lib\\user32.lib\n")
            .append(".data\n");
    	
    	codigo.add(s);
    	generarCodigoDatos();
        StringBuilder x = new StringBuilder();
    	x.append(".code\n").append("START:\n");
    	codigo.add(x);
    }
    
    public static void procesarArchivo(){
        
    	generarCabecera();
    	
    	for (Terceto t : TercetoManager.getLista()) {
    		String op1 = t.getOperador1();
    		String op2 = t.getOperador2();
    		String operador = t.getOperando();
    		switch(operador) {
    			case "+":
    				generarOperadorSuma(op1, op2);
    				break;
    			case "-":
    				generarOperadorResta(op1, op2);
    				break;
    			case "*":
    				generarOperadorMultiplicar(op1, op2);
    				break;
    			case "/":
    				generarOperadorDivision(op1, op2);
    				break;
    			case "=:":
    				generarAsignacion(op1, op2);
    				break;
    			case ">":
    			case ">=":
    			case "<":
    			case "<=":
    			case "=!":
    				generarOperador(operador, op1, op2);
    				break;
    			
    		}
    	}
    	
    	
    }
    
    public static void generarOperadorDivision(String op1, String op2) {
		// TODO Auto-generated method stub
    	Atributo auxOp1;
    	Atributo auxOp2;
    	    	
    	if (esTerceto(op1)) {
			String aux = variablesAuxiliares.get(op1);
			auxOp1 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp1 = TablaSimbolos.obtenerSimbolo(op1);
		if (esTerceto(op2)) {
			String aux = variablesAuxiliares.get(op2);
			auxOp2 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp2 = TablaSimbolos.obtenerSimbolo(op2);
				
		String auxiliar;
		
		if (auxOp1.getTipo().equals("i16")) {
			auxiliar = obtenerAuxiliar("i16");
			codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", 00h\n"));
			codigo.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
			codigo.add(new StringBuilder("invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK\n"));
			codigo.add(new StringBuilder("invoke ExitProcess, 0\n"));
			codigo.add(new StringBuilder("Label"+auxiliar+":\n"));
			codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("DIV ").append(auxOp2.getLexema()+"\n"));
			codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
		} else {
			auxiliar = obtenerAuxiliar("f32");
			codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\n"));
			codigo.add(new StringBuilder("FCOM 00h\n"));
			codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
			codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\nSAHF\n"));
			codigo.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
			codigo.add(new StringBuilder("invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK\n"));
			codigo.add(new StringBuilder("invoke ExitProcess, 0\n"));
			codigo.add(new StringBuilder("Label"+auxiliar+":\n"));
			codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\nFDIV\n"));
			codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
		}
	}

	public static void generarOperadorMultiplicar(String op1, String op2) {
		// TODO Auto-generated method stub
		Atributo auxOp1;
    	Atributo auxOp2;
    	    	
    	if (esTerceto(op1)) {
			String aux = variablesAuxiliares.get(op1);
			auxOp1 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp1 = TablaSimbolos.obtenerSimbolo(op1);
		if (esTerceto(op2)) {
			String aux = variablesAuxiliares.get(op2);
			auxOp2 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp2 = TablaSimbolos.obtenerSimbolo(op2);
		
		String auxiliar;
				
		if (auxOp1.getTipo().equals("i16")) {
			codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("MUL AX, ").append(auxOp2.getLexema()+"\n"));
			auxiliar = obtenerAuxiliar("i16");
			codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
		} else {
			codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\nFMUL\n"));
			auxiliar = obtenerAuxiliar("f32");
			codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
		}
	}

	public static void generarOperadorResta(String op1, String op2) {
		// TODO Auto-generated method stub
		Atributo auxOp1;
    	Atributo auxOp2;
    	    	
    	if (esTerceto(op1)) {
			String aux = variablesAuxiliares.get(op1);
			auxOp1 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp1 = TablaSimbolos.obtenerSimbolo(op1);
		if (esTerceto(op2)) {
			String aux = variablesAuxiliares.get(op2);
			auxOp2 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp2 = TablaSimbolos.obtenerSimbolo(op2);
		
		String auxiliar;
				
		if (auxOp1.getTipo().equals("i16")) {
			codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("SUB AX, ").append(auxOp2.getLexema()+"\n"));
			auxiliar = obtenerAuxiliar("i16");
			codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
		} else {
			codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\nFSUB\n"));
			auxiliar = obtenerAuxiliar("f32");
			codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
		}
	}

	public static void generarAsignacion(String op1, String op2) {
    	Atributo auxOp1;
    	Atributo auxOp2;
    	    	
    	if (esTerceto(op1)) {
			String aux = variablesAuxiliares.get(op1);
			auxOp1 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp1 = TablaSimbolos.obtenerSimbolo(op1);
		if (esTerceto(op2)) {
			String aux = variablesAuxiliares.get(op2);
			auxOp2 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp2 = TablaSimbolos.obtenerSimbolo(op2);
				
		if (auxOp1.getTipo().equals("i16")) {
			codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()).append("\n"));
			codigo.add(new StringBuilder("MOV ").append(auxOp1.getLexema()+", AX\n"));			
		} else {
			codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()).append("\n"));
			codigo.add(new StringBuilder("FSTP ").append(auxOp1.getLexema()+"\n"));
		}
	}

	public static void generarOperadorSuma(String op1, String op2) {
    	Atributo auxOp1;
    	Atributo auxOp2;
    	
    	if (esTerceto(op1)) {
			String aux = variablesAuxiliares.get(op1);
			auxOp1 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp1 = TablaSimbolos.obtenerSimbolo(op1);
		if (esTerceto(op2)) {
			String aux = variablesAuxiliares.get(op2);
			auxOp2 = TablaSimbolos.obtenerSimbolo(aux);
		} else
			auxOp2 = TablaSimbolos.obtenerSimbolo(op2);
		
		String auxiliar;
		
		if (auxOp1.getTipo().equals("i16")) {
			codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
			codigo.add(new StringBuilder("ADD AX, ").append(auxOp2.getLexema()+"\n"));
			auxiliar = obtenerAuxiliar("i16");
			codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
		} else {
			codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\n"));
			codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\nFADD\n"));
			auxiliar = obtenerAuxiliar("f32");
			codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
		}	
	}

	public static String obtenerAuxiliar(String tipo) {
		String aux = "@aux" + auxDisponible;
		auxDisponible++;
		TablaSimbolos.agregarSimbolo(aux, aux, tipo);
		variablesAuxiliares.put("["+numero+"]", aux);
		numero++;
		return aux;
	}

	public static void generarOperador(String operador, String op1, String op2) {
		if (esTerceto(op1)) {
			
		}
		if (esTerceto(op2)) {
			
		}
		
		
	}

	private static boolean esTerceto(String simbolo) {
		return simbolo.startsWith("[");
	}

	//Dado que esta la usamos para bifurcaciones por falso devolvemos el opuesto
    public static String negarSalto(String op) {
        switch (op) {
            case ">": return jle;
            case "<": return jge;
            case "=": return jne;
            case "<=": return jg;
            case ">=": return jl;
            case "!=": return je;
            default: return jmp;
        }
    }
    
    private static void generarCodigoDatos() {
        for (Entry<String, Atributo> e : TablaSimbolos.getTabla().entrySet()) {
            //tomamos el atributo 'uso' del simbolo actual, desde la tabla de simbolos
            Atributo a = TablaSimbolos.obtenerSimbolo(e.getKey());
            
            if (!a.getUso().equals("") && (a.getUso().equals("constante") || a.getUso().equals("funcion") )) continue;

            String tipo_actual = e.getValue().getTipo();
            String lexema_actual = e.getKey();
            StringBuilder b = new StringBuilder();
            
            if (tipo_actual.equals("")) continue;

            switch (tipo_actual) {
                case TablaTipos.STR_TYPE:
                    String valor_actual = e.getKey();
                    codigo.add(b.append(Ambito.sinAmbito(lexema_actual).substring(1)).append(" db \"").append(valor_actual).append("\", 0\n"));
                    break;
                case TablaTipos.FLOAT_TYPE:
                case TablaTipos.FUNC_TYPE:      
                    codigo.add(b.append(Ambito.sinAmbito(lexema_actual)).append(" dd ?\n"));
                    break;
                case TablaTipos.INTEGER_TYPE:
                    codigo.add(b.append(Ambito.sinAmbito(lexema_actual)).append(" dw ?\n"));
                    break;
            }
        }
    }
    
    public static void imprimir() {
        System.out.println("\nAssembler:\n");
        for (int i = 0; i<datos.size();i++) {
            System.out.println(datos.get(i));
        }
        for (int i = 0; i<codigo.size();i++) {
            System.out.println(codigo.get(i));
        }
    }

}
