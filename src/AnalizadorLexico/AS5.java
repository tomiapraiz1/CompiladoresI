package AnalizadorLexico;
import java.io.Reader;

import AnalizadorSintactico.Parser;

public class AS5 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		// TODO Auto-generated method stub
		if (token.indexOf("F") != -1)
			token.setCharAt(token.indexOf("F"), 'E');
		token.append("d");
		String simbolo = token.toString();
		try {
			Double valor = Double.parseDouble(simbolo);
			if (valor > AnalizadorLexico.maxF) {
				Parser.erroresLexicos.add("Warning linea " + AnalizadorLexico.getLine() + " : el valor del simbolo sobrepasa el valor maximo."
						+ " El mismo fue truncado al maximo.");
				simbolo = Double.toString(AnalizadorLexico.maxF);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.CONSTANTE, "flotante", AnalizadorLexico.getLine());
		
		return AnalizadorLexico.CONSTANTE;
	}
}
