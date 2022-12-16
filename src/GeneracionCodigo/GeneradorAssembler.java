/*-------Instrucciones Assembler para pasar de un registro de 16 bits a uno de 32 sin signo----------
	MOV BX, D2
	MOV ECX, 0
	MOV CX, BX
	MOV EBX, ECX
	
	resultado EBX
	
	
	-------Instrucciones Assembler para pasar de entero a float----------
	FILD D2 -> entero de 16 bits
	FILD D4 -> entero de 32 bits 
	
	resultado: ST
*/

package GeneracionCodigo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import AnalizadorLexico.*;
import AnalizadorSintactico.Parser;

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
    private static ArrayList<StringBuilder> codigoAux = new ArrayList<StringBuilder>();
    private static ArrayList<StringBuilder> datos =new ArrayList<StringBuilder>();
    private static ArrayList<StringBuilder> funciones =new ArrayList<StringBuilder>();
    private static HashMap<String, String> variablesAuxiliares = new HashMap<String, String>();
    private static HashMap<String, String> floatAuxiliares = new HashMap<String, String>();
    private static Integer auxDisponible = 0;
    private static Integer floatDisponible = 0;
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
    }
    
    public static void procesarArchivo(){
    	
    	Stack<Integer> s = new Stack<Integer>();
    	String parametroF1 = "", parametroF2 = "";
        
    	generarCabecera();
    	
    	for (Terceto t : TercetoManager.getLista()) {
    		String op1 = t.getOperador1();
    		String op2 = t.getOperador2();
    		String operando = t.getOperando();
    		String tipoTerceto = t.getTipoTerceto();
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
    		
    		if (operando.equals(auxiliar)) {
    			codigoAux.add(new StringBuilder(auxiliar).append(":\n"));
    			numero++;
    			continue;
    		}
    					
    		switch(operando) {
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
    			case "=":
    				generarOperador(operando, auxOp1, auxOp2,tipoTerceto);
    				break;
    			case "BI":
    				codigoAux.add(new StringBuilder("JMP Label").append(devolverNumeroTerceto(op1)+"\n"));
    				break;
    			case "BF":
    				codigoAux.add(new StringBuilder("MOV AX, ").append(auxOp1.getLexema()+"\n"));
    				codigoAux.add(new StringBuilder("OR AX, 0\n"));
	    			codigoAux.add(new StringBuilder("JNE Label").append(devolverNumeroTerceto(op2)+"\n"));
    				break;
    			case "tof32":
    				codigoAux.add(new StringBuilder("FILD ").append(auxOp1.getLexema()+"\n"));
    				String aux = obtenerAuxiliar("f32");
    				codigoAux.add(new StringBuilder("FSTP ").append(aux+"\n"));
    				break;
    			case "Funcion":
    				s.add(codigoAux.size());
    				String parametro1 = Parser.getTipoParametro(auxOp1.getTipoP1());
    				String parametro2 = Parser.getTipoParametro(auxOp1.getTipoP2());
    				
    				if (!parametro1.equals(""))
    					parametro1 = "WORD";
    				if (!parametro2.equals(""))
    					parametro2 = "WORD";
    				
    				if (!parametro1.equals("") && !parametro2.equals(""))
    					codigoAux.add(new StringBuilder(auxOp1.getLexema()+" proc " + devolverNombre(Ambito.sinAmbito(auxOp1.getTipoP1())) + ":"+ parametro1 + ", " + devolverNombre(Ambito.sinAmbito(auxOp1.getTipoP2()))+ ":"+ parametro2 +"\n"));
    				else if (!auxOp1.getTipoP1().equals("") && auxOp1.getTipoP2().equals(""))
    					codigoAux.add(new StringBuilder(auxOp1.getLexema()+" proc " + devolverNombre(Ambito.sinAmbito(auxOp1.getTipoP1()))+ ":"+ parametro1+"\n"));
    				else if (auxOp1.getTipoP1().equals("") && !auxOp1.getTipoP2().equals(""))
    					codigoAux.add(new StringBuilder(auxOp1.getLexema()+" proc " + devolverNombre(Ambito.sinAmbito(auxOp1.getTipoP2())) + ":"+ parametro2 +"\n"));
    				else
    					codigoAux.add(new StringBuilder(auxOp1.getLexema()+" proc\n"));
    				break;
    			case "Return":
    				codigoAux.add(new StringBuilder("MOV AX, " + devolverNombre(auxOp1.getLexema())+"\nret\n"));
    				break;
    			case "End_funcion":
    				int indice = s.pop();
    				for (int i = indice; i < codigoAux.size(); i++) {
    					funciones.add(codigoAux.get(i));
    				}
    				
    				int size = codigoAux.size()-1;
    				
    				for (int i = size; i >= indice; i--) {
    					codigoAux.remove(i);
    				}
    				
    				funciones.add(new StringBuilder(auxOp1.getLexema()+ " endp\n"));
    				break;
    			case "Parametros":
    				if (auxOp1 != null)
    					parametroF1 = devolverNombre(auxOp1.getLexema());
    				if (auxOp2 != null)
    					parametroF2 = devolverNombre(auxOp2.getLexema());
    				break;
    			case "CALL":
    				if (!parametroF1.equals("") && !parametroF2.equals(""))
    					codigoAux.add(new StringBuilder("invoke "+auxOp1.getLexema()+", " + parametroF1 + " ," + parametroF2 +"\n"));
    				else if (!parametroF1.equals("") && parametroF2.equals(""))
    					codigoAux.add(new StringBuilder("invoke "+auxOp1.getLexema()+", " + parametroF1+"\n"));
    				else if (parametroF1.equals("") && !parametroF2.equals(""))
    					codigoAux.add(new StringBuilder("invoke "+auxOp1.getLexema()+", " + parametroF2 +"\n"));
    				else
    					codigoAux.add(new StringBuilder("invoke "+auxOp1.getLexema()+"\n"));
    				
    				String aux1 = obtenerAuxiliar("i16");
    				codigoAux.add(new StringBuilder("MOV "+ aux1 + ", AX\n"));
    				break;
    			default:
    				break;
    		}
    		
    		numero++;
    	}
    	
    	generarCodigoDatos();
        StringBuilder x = new StringBuilder();
    	x.append(".code\n");
    	for (StringBuilder string : funciones) {
    		x.append(string);
    	}
    	x.append("START:\nFNINIT\n");
    	codigo.add(x);
    	
    	codigoAux.add(new StringBuilder("END START\n"));
    	
    	for(int i=0;i<codigoAux.size();i++) {
    		codigo.add(codigoAux.get(i));
    	}
    	
    	
    }
  
    
    public static void generarOperador(String operador, Atributo  auxOp1, Atributo auxOp2, String tipoTerceto) {
    	switch(operador) {
		    case "+":
		    	if (tipoTerceto.equals("i16")) {
		    		Integer op1 = Integer.parseInt(auxOp1.getValor());
			    	Integer op2 = Integer.parseInt(auxOp2.getValor());

			    	Integer suma = op1 + op2;
			    	
			    	if (suma > AnalizadorLexico.maxInt) {
			    		System.out.println("Error en ejecucion, la suma se pasa del maximo admitido");
			    	}
		    		codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("ADD AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));					
					String auxiliar = obtenerAuxiliar("i16", suma);
					codigoAux.add(new StringBuilder("JNC Label").append(auxiliar+"\n"));
					codigoAux.add(new StringBuilder("invoke MessageBox, NULL, addr OVERFLOW, addr OVERFLOW, MB_OK\n"));
					codigoAux.add(new StringBuilder("invoke ExitProcess, 0\n"));
					codigoAux.add(new StringBuilder("Label"+auxiliar+":\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					
					Float opf1 = Float.parseFloat(auxOp1.getValor());
			    	Float opf2 = Float.parseFloat(auxOp2.getValor());

			    	Float sumaf = opf1 + opf2;
			    	
			    	System.out.println(sumaf);
			    	
			    	if (sumaf > AnalizadorLexico.maxF) {
			    		System.out.println("Error en ejecucion, la suma se pasa del maximo admitido");
			    	}
					
					if(auxOp2.getIdToken() == 258){
						String auxiliar = obtenerFloat(auxOp2.getLexema()); 
						codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
						codigoAux.add(new StringBuilder("FLD ").append(auxiliar+"\nFADD\n"));
						auxiliar = obtenerAuxiliar("f32", sumaf);
						codigoAux.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
					}else if(auxOp1.getIdToken() == 258){
						String auxiliar = obtenerFloat(auxOp1.getLexema()); 
						codigoAux.add(new StringBuilder("FLD ").append(auxiliar+"\n"));
						codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())+"\nFADD\n"));
						auxiliar = obtenerAuxiliar("f32", sumaf);
						codigoAux.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
					} else {
						codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
						codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())+"\nFADD\n"));
						String auxiliar = obtenerAuxiliar("f32", sumaf);
						codigoAux.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
					}
				}
		    	break;
			case "-":
				if (tipoTerceto.equals("i16")) {
					Integer op1 = Integer.parseInt(auxOp1.getValor());
			    	Integer op2 = Integer.parseInt(auxOp2.getValor());

			    	Integer resta = op1 - op2;
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("SUB AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					String auxiliar = obtenerAuxiliar("i16", resta);
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					Float opf1 = Float.parseFloat(auxOp1.getValor());
			    	Float opf2 = Float.parseFloat(auxOp2.getValor());

			    	Float restaf = opf1 - opf2;
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())+"\nFSUB\n"));
					String auxiliar = obtenerAuxiliar("f32", restaf);
					codigoAux.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
				break;
			case "*":
				if (tipoTerceto.equals("i16")) {
					Integer op1 = Integer.parseInt(auxOp1.getValor());
			    	Integer op2 = Integer.parseInt(auxOp2.getValor());

			    	Integer mul = op1 * op2;
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("MOV DX, ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("MUL ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					String auxiliar = obtenerAuxiliar("i16", mul);
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					Float opf1 = Float.parseFloat(auxOp1.getValor());
			    	Float opf2 = Float.parseFloat(auxOp2.getValor());

			    	Float mulf = opf1 * opf2;
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())+"\nFMUL\n"));
					String auxiliar = obtenerAuxiliar("f32", mulf);
					codigoAux.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
				break;
			case "/":
				if (tipoTerceto.equals("i16")) {
					Integer op1 = Integer.parseInt(auxOp1.getValor());
			    	Integer op2 = Integer.parseInt(auxOp2.getValor());
			    	
			    	Integer div = op1;

					if(op2 == 0) {
						System.out.println("Error en tiempo de ejecucion, el operando de la division es 0");
					}else {
						div = op1 / op2;
					}
					String auxiliar = obtenerAuxiliar("i16", div);					
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP AX, 00h\n"));
					codigoAux.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK\n"));
					codigoAux.add(new StringBuilder("invoke ExitProcess, 0\n"));
					codigoAux.add(new StringBuilder("Label"+auxiliar+":\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("MOV DX, ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("DIV ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", AX\n"));
				} else {
					Float opf1 = Float.parseFloat(auxOp1.getValor());
			    	Float opf2 = Float.parseFloat(auxOp2.getValor());
			    	
			    	Float divf = opf1;

			    	if(opf2 == 0.0) {
						System.out.println("Error en tiempo de ejecucion, el operando de la division es 0 ");
					}else {
						divf = opf1 / opf2;
					}

					String auxiliar = obtenerAuxiliar("f32", divf);					
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FTST\n")); //Intruccion que compara ST con 0
					codigoAux.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK\n"));
					codigoAux.add(new StringBuilder("invoke ExitProcess, 0\n"));
					codigoAux.add(new StringBuilder("Label"+auxiliar+":\n"));
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FDIV\n"));
					codigoAux.add(new StringBuilder("FSTP ").append(auxiliar+"\n"));
				}
				break;
			case "=:": 
				auxOp1.setValor(auxOp2.getValor());
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())).append("\n"));
					codigoAux.add(new StringBuilder("MOV ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));			
				} else {
					if(auxOp2.getLexema().startsWith("@aux")) {
						codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())).append("\n"));
						codigoAux.add(new StringBuilder("FSTP ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					}else {
						if(auxOp2.getIdToken() == 258){
							String auxiliar = obtenerFloat(auxOp2.getLexema()); 
							codigoAux.add(new StringBuilder("FLD ").append(auxiliar+"\n"));
							codigoAux.add(new StringBuilder("FSTP ").append(devolverNombre(auxOp1.getLexema())+"\n"));
						} else {
							codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp2.getLexema())).append("\n"));
							codigoAux.add(new StringBuilder("FSTP ").append(devolverNombre(auxOp1.getLexema())+"\n"));
						}
					}
				}
				break;
			case ">":
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFh\n"));
					codigoAux.add(new StringBuilder("JA ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FCOM ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFFFFFh\n"));
					codigoAux.add(new StringBuilder("JA ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 00000000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case ">=":
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFh\n"));
					codigoAux.add(new StringBuilder("JAE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FCOM ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFFFFFh\n"));
					codigoAux.add(new StringBuilder("JAE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 00000000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "<":
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFh\n"));
					codigoAux.add(new StringBuilder("JB ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FCOM ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFFFFFh\n"));
					codigoAux.add(new StringBuilder("JB ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 00000000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "<=":
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFh\n"));
					codigoAux.add(new StringBuilder("JBE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FCOM ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFFFFFh\n"));
					codigoAux.add(new StringBuilder("JBE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 00000000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "=!":
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFh\n"));
					codigoAux.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FCOM ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFFFFFh\n"));
					codigoAux.add(new StringBuilder("JNE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 00000000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			case "=":
				if (tipoTerceto.equals("i16")) {
					codigoAux.add(new StringBuilder("MOV AX, ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("CMP ").append(devolverNombre(auxOp1.getLexema())+", AX\n"));
					String auxiliar = obtenerAuxiliar("i16");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFh\n"));
					codigoAux.add(new StringBuilder("JE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				} else {
					codigoAux.add(new StringBuilder("FLD ").append(devolverNombre(auxOp1.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FCOM ").append(devolverNombre(auxOp2.getLexema())+"\n"));
					codigoAux.add(new StringBuilder("FSTSW ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("MOV AX, ").append(auxDivision+"\n"));
					codigoAux.add(new StringBuilder("SAHF\n"));
					String auxiliar = obtenerAuxiliar("f32");
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 0FFFFFFFFh\n"));
					codigoAux.add(new StringBuilder("JE ").append("Label"+auxiliar+"\n"));
					codigoAux.add(new StringBuilder("MOV ").append(auxiliar+", 00000000h\n"));
					codigoAux.add(new StringBuilder("Label").append(auxiliar+":\n"));
				}
				break;
			default:
				break;
    	}
	}
    
    public static String obtenerAuxiliar(String tipo, Integer valor) {
		String aux = "@aux" + auxDisponible;
		auxDisponible++;
		TablaSimbolos.agregarSimbolo(aux, aux, tipo, Integer.toString(valor));
		variablesAuxiliares.put("["+numero+"]", aux);
		return aux;
	}

	public static String obtenerAuxiliar(String tipo, Float valor) {
		String aux = "@aux" + auxDisponible;
		auxDisponible++;
		TablaSimbolos.agregarSimbolo(aux, aux, tipo, Float.toString(valor));
		variablesAuxiliares.put("["+numero+"]", aux);
		return aux;
	}
    
    public static String obtenerFloat(String valor) {
		String aux = "@auxf" + floatDisponible;
		floatDisponible++;
		floatAuxiliares.put(valor, aux);
		return aux;
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
	
	private static String devolverNombre(String simbolo) {
		if (simbolo.startsWith("@") || esConstante(simbolo)) {
			return simbolo;
		} else
			return "_"+simbolo;
	}

	private static boolean esConstante(String simbolo) {
		for (int i=0; i<10; i++) {
			if (simbolo.contains(Integer.toString(i))) {
				return true;
			}
		}
		return false;
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
            if(a.getLexema().startsWith("@aux")){
            	String tipo = a.getTipo();
            	String lexema_actual = a.getLexema();
            	StringBuilder b = new StringBuilder();
            	switch(tipo) {
            	case TablaTipos.FLOAT_TYPE:
                case TablaTipos.FUNC_TYPE:      
                    codigo.add(b.append(lexema_actual).append(" dd ?\n"));
                    break;
                case TablaTipos.INTEGER_TYPE:
                    codigo.add(b.append(lexema_actual).append(" dw ?\n"));
                    break;
            	}
            	continue;
            }
            
            if(a.getIdToken() == 258 && a.getTipo().equals("f32")){
            	String lexema_actual = a.getLexema();
            	String auxiliar = floatAuxiliares.get(lexema_actual);
            	codigo.add(new StringBuilder(auxiliar).append(" dd " + lexema_actual+ "\n"));
            	continue;
            }
            
            if (!a.getUso().equals("") && (a.getIdToken() == 258 || a.getUso().equals("funcion") )) continue;

            String tipo_actual = e.getValue().getTipo();
            String lexema_actual = e.getValue().getLexema();
            StringBuilder b = new StringBuilder();
            
            if (tipo_actual.equals("")) continue;

            switch (tipo_actual) {
                case TablaTipos.STR_TYPE:
                    String valor_actual = e.getKey();
                    codigo.add(b.append("_"+lexema_actual).append(" db \"").append(valor_actual).append("\", 0\n"));
                    break;
                case TablaTipos.FLOAT_TYPE:
                case TablaTipos.FUNC_TYPE:      
                    codigo.add(b.append("_"+lexema_actual).append(" dd ?\n"));
                    break;
                case TablaTipos.INTEGER_TYPE:
                    codigo.add(b.append("_"+lexema_actual).append(" dw ?\n"));
                    break;
            }
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
