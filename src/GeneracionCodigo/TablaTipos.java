package GeneracionCodigo;

import AnalizadorLexico.*;
import AnalizadorSintactico.Parser;

public class TablaTipos {
	    public static final int INTEGER = 0;
	    public static final int FLOAT = 1;
	    public static final int FUNC = 2;

	    public static final String FLOAT_TYPE = "f32";
	    public static final String INTEGER_TYPE = "i16";
	    public static final String FUNC_TYPE = "funcion";
	    public static final String STR_TYPE = "cadena";
	    public static final String ERROR_TYPE = "error";

	    private static final String[][] tiposSumaResta = { { INTEGER_TYPE, FLOAT_TYPE, ERROR_TYPE },
	                                                       { FLOAT_TYPE, FLOAT_TYPE, ERROR_TYPE },
	                                                       { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE} };
	    private static final String[][] tiposMultDiv = { { INTEGER_TYPE, FLOAT_TYPE, ERROR_TYPE },
	                                                     { FLOAT_TYPE, FLOAT_TYPE, ERROR_TYPE },
	                                                     { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE} };
	    private static final String[][] tiposComparadores = { { INTEGER_TYPE, FLOAT_TYPE, ERROR_TYPE }, 
	                                                          { FLOAT_TYPE, FLOAT_TYPE, ERROR_TYPE },
	                                                          { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE } };
	    private static final String[][] tiposAsig = { { INTEGER_TYPE, ERROR_TYPE, INTEGER_TYPE }, 
	                                                  { FLOAT_TYPE, FLOAT_TYPE, FLOAT_TYPE },
	                                                  { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE } };

	    public static void setTipoAbarcativo(String op1, String op2, String operador){

	        String tipoOp1 = getTipo(op1);
	        String tipoOp2 = getTipo(op2);
	        
	        if (tipoOp1.equalsIgnoreCase(tipoOp2))
	        	return;

	        String tipoFinal = tipoResultante(tipoOp1, tipoOp2, operador);
	        if (tipoFinal.equals(ERROR_TYPE)) {
	            Parser.erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": No se puede realizar la operacion " + operador + " entre los tipos " + tipoOp1 + " y " + tipoOp2);
	        } else if (tipoFinal.equals(tipoOp2)) {
        		TercetoManager.crear_terceto("to"+tipoFinal, op1, "_");
        	} else if (tipoFinal.equals(tipoOp1)){
        		TercetoManager.crear_terceto("to"+tipoFinal, op2, "_");
        	}
	    }

	    public static String getTipo(String op) {
	    	return TablaSimbolos.obtenerSimbolo(op).getTipo(); 
	    }

	    public static String tipoResultante(String op1, String op2, String operador) {
	        int fil = -1;
	        int col = -1;
	        
	        
	        
	        if (TablaSimbolos.contieneSimbolo(op1) && TablaSimbolos.contieneSimbolo(op2)) {
	    		fil = getNumeroTipo(TablaSimbolos.obtenerSimbolo(op1).getTipo());
	        	col = getNumeroTipo(TablaSimbolos.obtenerSimbolo(op2).getTipo());
	    	}else if (TablaSimbolos.contieneSimbolo(op1)){
	    		fil = getNumeroTipo(TablaSimbolos.obtenerSimbolo(op1).getTipo());
		        col = getNumeroTipo(TercetoManager.getTerceto(Integer.parseInt(op2.substring(1, op2.length()-1))).getTipoTerceto());
	    	}else if (TablaSimbolos.contieneSimbolo(op2)){
	    		fil = getNumeroTipo(TercetoManager.getTerceto(Integer.parseInt(op1.substring(1, op1.length()-1))).getTipoTerceto());
		        col = getNumeroTipo(TablaSimbolos.obtenerSimbolo(op2).getTipo());
	    	}else {
	    		fil = getNumeroTipo(TercetoManager.getTerceto(Integer.parseInt(op1.substring(1, op1.length()-1))).getTipoTerceto());
		        col = getNumeroTipo(TercetoManager.getTerceto(Integer.parseInt(op2.substring(1, op2.length()-1))).getTipoTerceto());
	    		
	    	}

	        switch (operador) {
	            case ("+"):
	            case ("-"):
	                return tiposSumaResta[fil][col];
	            case ("*"):
	            case ("/"):
	                return tiposMultDiv[fil][col];
	            case ("=:"):
	                return tiposAsig[fil][col];
	            case ("<="):
	            case ("<"):
	            case (">="):
	            case (">"):
	            case ("=!"):
	            case ("="):
	                return tiposComparadores[fil][col];
	            default:
	                return ERROR_TYPE;
	        }
	       

	    }

	    private static int getNumeroTipo(String tipo) {
	        if (tipo.equals(INTEGER_TYPE)) 
	        	return INTEGER;
	        else if (tipo.equals(FLOAT_TYPE)) 
	        	return FLOAT;
	        else return FUNC;
	    }
}
