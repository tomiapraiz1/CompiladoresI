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

	    private static String tipoResultante(String op1, String op2, String operador) {
	        int fil = getNumeroTipo(op1);
	        int col = getNumeroTipo(op2);

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
	        if (tipo.equals(INTEGER_TYPE)) return INTEGER;
	        else if (tipo.equals(FLOAT_TYPE)) return FLOAT;
	        else return FUNC;
	    }
}
