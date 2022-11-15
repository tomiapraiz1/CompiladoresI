package GeneracionCodigo;

import java.util.ArrayList;
import java.util.HashMap;

import AnalizadorLexico.*;

public class GeneradorAssembler {
	
	final static String mov = "MOV";
    final static String mul = "MUL";
    final static String add = "ADD";
    final static String sub = "SUB";
    final static String div = "DIV";
    final static String jmp = "JMP";
    final static String jle = "JLE";
    final static String jge = "JGE";
    final static String jg = "JG";
    final static String jl = "JL";
    final static String je = "JE";
    final static String jne = "JNE";
    final static String push = "PUSH";
    final static String pop = "POP";
    final static String cmp = "CMP";
    final static String zf = "ZF";
    final static String sf = "SF";
    final static String of = "OF";
    final static String fadd = "FADD";
    final static String fsub = "FSUB";
    final static String fmul = "FMUL";
    final static String fdiv = "FDIV";
    final static String fld = "FLD";
    final static String fstp = "FSTP";
    
    //private StringBuilder lineaAsembler;
    private static ArrayList<StringBuilder> lineaCODE=new ArrayList<StringBuilder>();
    private static ArrayList<StringBuilder> lineaDATA=new ArrayList<StringBuilder>();
    private static Integer etiquetas=0;
    private static Integer extras=0;
    
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
            .append(".data\n").append(".code\n").append("START:\n");

    	lineaCODE.add(s);

    }
    
    public static void procesarArchivo(){
        
    	generarCabecera();
    	//TercetoManager.considerarEtiquetas();
        int x = 1;
        int xAnterior = 1;
        String operadorAnterior="";
        
        HashMap<String,String> variablesPrevias = new HashMap<String,String>();
        
        for (int i = 0; i < TercetoManager.getIndexTerceto(); i++) {
            
            Terceto tercetoProcesar = TercetoManager.getTerceto(i);
            System.out.println(tercetoProcesar);
            String operador = tercetoProcesar.getOperando();
            String sArg = tercetoProcesar.getOperador1();
            String tArg = tercetoProcesar.getOperador2();
            String variableAux= "@aux";
            
            if (operador.equals("Label"+i)) {
                lineaCODE.add(new StringBuilder("Label"+ i +":"));
                etiquetas++;
            }
            
            switch (operador) {
                case "+":
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (!sArg.startsWith("@")) {
	                    if (TablaSimbolos.obtenerSimbolo(sArg).getTipo().equals("i16")) {
	                        operador=add;
	                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
	                    } else {
	                        operador=fadd;
	                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
	                    }
                    } else {
                    	operador=add;
                    	procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "*": 
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (!sArg.startsWith("@")) {	
	                    if (TablaSimbolos.obtenerSimbolo(sArg).getTipo().equals("i16")) {
	                        operador=mul;
	                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
	                    } else {
	                        operador=fmul;
	                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
	                    }
                    } else {
                    	operador=mul;
                    	procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "/": 
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (!sArg.startsWith("@")) {
	                    if (TablaSimbolos.obtenerSimbolo(sArg).getTipo().equals("i16")) {
	                        operador=div;
	                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
	                    } else {
	                        operador=fdiv;
	                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
	                    }
                    } else {
                    	operador=div;
                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "-": 
                    if (sArg.startsWith("[")) {
                        if (Integer.parseInt(sArg.substring(1,sArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            sArg=variablesPrevias.get(sArg);
                        }
                    }
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                    }
                    if (!sArg.startsWith("@")) {
	                    if (TablaSimbolos.obtenerSimbolo(sArg).getTipo().equals("i16")) {
	                        operador=sub;
	                        procesarTerceto(sArg, tArg, operador, (variableAux+x));
	                    } else {
	                        operador=fsub;
	                        procesarTercetoDouble(sArg, tArg, operador, (variableAux+x));
	                    }
                    } else {
                    	operador=sub;
                    	procesarTerceto(sArg, tArg, operador, (variableAux+x));
                    }
                    variablesPrevias.put("["+i+"]",(variableAux+x));
                    x++;
                    break;
                case "=:":
                    operador=mov;
                    if (tArg.startsWith("[")) {
                        if (Integer.parseInt(tArg.substring(1,tArg.length()-1)) > i+etiquetas+extras) {
                            System.out.println("Error en compilacion de assembler se esta intentando acceder al valor de una instruccion futura");
                        }else {
                            tArg=variablesPrevias.get(tArg);
                        }
                        lineaCODE.add(new StringBuilder(mov +" AL, " + tArg));
                        lineaCODE.add(new StringBuilder(mov +" " + sArg + ", AL"));
                        extras+=1;
                    }else {
                        lineaCODE.add(new StringBuilder(mov +" " + sArg +", "+ tArg));
                    }
                    
                    break;
                case "BI":
                    if (sArg.equals("TerRetFuncion:_")) {
                        lineaCODE.add(new StringBuilder(jmp +" TerRetFuncion:_"));
                    }else {
                        lineaCODE.add(new StringBuilder(jmp +" Label"+ sArg.substring(1, sArg.length()-1) +":"));
                    }
                    break;
                case "BF":
                    operador=nemesisOp(operadorAnterior);
                    lineaCODE.add(new StringBuilder(operador +" Label"+ tArg.substring(1, tArg.length()-1) +":"));
                    break;
                case "<":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case ">":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case "=":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case ">=":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case "<=":
                    if (sArg.startsWith("[")) {
                        sArg=variablesPrevias.get(sArg);
                    }
                    if (tArg.startsWith("[")) {
                        tArg=variablesPrevias.get(tArg);
                    }
                    lineaCODE.add(new StringBuilder(cmp +" "+sArg+", "+tArg));
                    break;
                case "out":
                    lineaCODE.add(new StringBuilder("OUT "+sArg));
                    break;
                default:
                    continue;
            }
            operadorAnterior=operador;
            xAnterior=x;
        }
        
        lineaCODE.add(new StringBuilder("\nEND START"));
        
    }
    
    
    //Dado que esta la usamos para bifurcaciones por falso devolvemos el opuesto
    public static String nemesisOp(String op) {
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
    
    public static void procesarTerceto(String sOp, String tOp, String funcion, String variable) {
    	 StringBuilder contenido = new StringBuilder(mov + " AL, " +sOp);
         lineaCODE.add(contenido);
         contenido = new StringBuilder(funcion + " AL, " +tOp);
         lineaCODE.add(contenido);
         contenido = new StringBuilder(mov +" " + variable + ", AL");
         lineaCODE.add(contenido);
         extras+=2;
    }
    
    public static void procesarTercetoDouble(String sOp, String tOp, String funcion, String variable) {
        StringBuilder contenido = new StringBuilder(fld+" "+sOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(fld+" "+tOp);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(funcion);
        lineaCODE.add(contenido);
        contenido = new StringBuilder(fstp+" "+variable);
        lineaCODE.add(contenido);
        extras+=3;
    }
    
    public static void imprimir() {
        System.out.println("\n\n\n/////////Comienzo Assembler");
        for (int i = 0; i<lineaDATA.size();i++) {
            System.out.println(lineaDATA.get(i));
        }
        for (int i = 0; i<lineaCODE.size();i++) {
            System.out.println(lineaCODE.get(i));
        }
    }

}
