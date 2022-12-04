package GeneracionCodigo;

import java.io.File;
import java.io.FileWriter;
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
    private static Integer numero=0;
    //Errores
    private static String DIVISIONPORCERO = "Error division por cero";
    private static String OVERFLOW = "Error overflow de suma";
    private static String INVOCACION = "Error la funcion no puede volver a ser invocada";
    
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
        .append(".data\n\n")
        .append("DIVISIONPORCERO db \""+DIVISIONPORCERO+"\", 0\n")
    	.append("OVERFLOW db \""+OVERFLOW+"\", 0\n")
    	.append("INVOCACION db \""+INVOCACION+"\", 0\n")
        .append(auxDivision+" dw ?\n");
    	
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
    		
    		String auxiliar = "Label"+numero;
    		
    		if (operador.equals(auxiliar)) {
    			codigo.add(new StringBuilder(auxiliar).append(":\n"));
    		}
    				
    		switch(operador) {
    			case "+":
    			case "-":
    			case "*":
    			case "/":
    			case "=:":
    			case ">":
    			case ">=":
    			case "<":
    			case "<=":
    			case "=!":
    				generarOperador(operador, auxOp1, auxOp2);
    				break;
    			case "BI":
    				codigo.add(new StringBuilder("JMP Label").append(devolverNumeroTerceto(op1)+"\n"));
    				break;
    			case "BF":
    				codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
	    			codigo.add(new StringBuilder("OR AX, $0\n"));
    				codigo.add(new StringBuilder("JNE Label").append(devolverNumeroTerceto(op2)+"\n"));
    				break;
    			default:
    				break;
    		}
    		
    		numero++;
    	}
    	
    	codigo.add(new StringBuilder("END START\n"));
    	
    	
    }
  
    
    public static void generarOperador(String operador, Atributo  auxOp1, Atributo auxOp2) {
    	switch(operador) {
		    case "+":
		    	if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("ADD AX, ").append(auxOp2.getLexema()+"\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\nFADD\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
		    	break;
			case "-":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("SUB AX, ").append(auxOp2.getLexema()+"\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\nFSUB\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
				break;
			case "*":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("MUL ").append(auxOp2.getLexema()+"\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()+"\nFMUL\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
				break;
			case "/":
				if (auxOp1.getTipo().equals("i16")) {
					String auxiliar = obtenerAuxiliar("i16");					
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP AX, 00h\n"));
					codigo.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK\n"));
					codigo.add(new StringBuilder("invoke ExitProcess, 0\n"));
					codigo.add(new StringBuilder("Label"+auxiliar+":\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("DIV ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					String auxiliar = obtenerAuxiliar("f32");					
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", "+auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FLD "+auxiliar+"\n"));
					codigo.add(new StringBuilder("FTST\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\nSAHF\n"));
					codigo.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK\n"));
					codigo.add(new StringBuilder("invoke ExitProcess, 0\n"));
					codigo.add(new StringBuilder("Label"+auxiliar+":\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", "+auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FLD ").append(auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", "+auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FLD ").append(auxiliar+"\nFDIV\n"));
					codigo.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
				break;
			case "=:":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()).append("\n"));
					codigo.add(new StringBuilder("MOV ").append(auxOp1.getLexema()+", AX\n"));			
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp2.getLexema()).append("\n"));
					codigo.add(new StringBuilder("FSTP ").append(auxOp1.getLexema()+"\n"));
				}
				break;
			case ">":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JA ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FCOM ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JA ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case ">=":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JAE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FCOM ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JAE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "<":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JB ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FCOM ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JB ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "<=":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JBE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FCOM ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JBE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "=!":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FCOM ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "=":
				if (auxOp1.getTipo().equals("i16")) {
					codigo.add(new StringBuilder("MOV AX, ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("CMP ").append(auxOp1.getLexema()+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigo.add(new StringBuilder("FLD ").append(auxOp1.getLexema()+"\n"));
					codigo.add(new StringBuilder("FCOM ").append(auxOp2.getLexema()+"\n"));
					codigo.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigo.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", FFh\n"));
					codigo.add(new StringBuilder("JE ").append("Label"+auxiliar+"\n"));
					codigo.add(new StringBuilder("MOV ").append(auxiliar+", 00h\n"));
					codigo.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			default:
				break;
    	}
	}

	public static String obtenerAuxiliar(String tipo) {
		String aux = "@aux" + auxDisponible;
		auxDisponible++;
		TablaSimbolos.agregarSimbolo(aux, aux, tipo);
		variablesAuxiliares.put("["+numero+"]", aux);
		return aux;
	}

	private static boolean esTerceto(String simbolo) {
		return simbolo.startsWith("[");
	}
	
	private static String devolverNumeroTerceto(String terceto) {
		return terceto.substring(1, terceto.length()-1);
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
            String lexema_actual = e.getValue().getLexema();
            StringBuilder b = new StringBuilder();
            
            if (tipo_actual.equals("")) continue;

            switch (tipo_actual) {
                case TablaTipos.STR_TYPE:
                    String valor_actual = e.getKey();
                    codigo.add(b.append(lexema_actual).append(" db \"").append(valor_actual).append("\", 0\n"));
                    break;
                case TablaTipos.FLOAT_TYPE:
                case TablaTipos.FUNC_TYPE:      
                    codigo.add(b.append(lexema_actual).append(" dd ?\n"));
                    break;
                case TablaTipos.INTEGER_TYPE:
                    codigo.add(b.append(lexema_actual).append(" dw ?\n"));
                    break;
            }
        }
        for (int i=0; i<10; i++) {
        	codigo.add(new StringBuilder("@aux").append(i+" dw ?\n"));
        }
    }
    
    public static void crearArchivoASM() {
    	try {
            //Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor  		
    		File archivo = new File("salida.asm");
    		archivo.delete();
    		archivo = new File("salida.asm");
            //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
            FileWriter escribir = new FileWriter(archivo, true);


            //Escribimos en el archivo con el metodo write 
            for (int i = 0; i < datos.size(); i++) {
                escribir.write(datos.get(i).toString());
            }
            for (int i = 0; i < codigo.size(); i++) {
            	escribir.write(codigo.get(i).toString());
            }

            //Cerramos la conexion
            escribir.close();
        } //Si existe un problema al escribir cae aqui
        catch (Exception e) {
            System.out.println("Error al escribir");
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
