package AnalizadorLexico;
import java.io.Reader;
import AnalizadorSintactico.Parser;

public class AS3 extends AccionSemantica{
	
	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		// TODO Auto-generated method stub
		String simbolo = token.toString();
		try {
			Integer valor = Integer.parseInt(simbolo);
			if (valor > AnalizadorLexico.maxInt) {
				Parser.erroresLexicos.add("Warning linea " + AnalizadorLexico.getLine() + " : el valor del simbolo sobrepasa el valor maximo."
						+ " El mismo fue truncado al maximo.");
				simbolo = Integer.toString(AnalizadorLexico.maxInt);
				AnalizadorLexico.token_actual.setLength(0);
				AnalizadorLexico.token_actual.append(simbolo);
			} 
		} catch(Exception e){
			e.printStackTrace();
		}
		
		TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.CONSTANTE, "i16", AnalizadorLexico.getLine());
		TablaSimbolos.modificarUso(simbolo, "constante");
		TablaSimbolos.modificarValor(simbolo,simbolo);

		return AnalizadorLexico.CONSTANTE;
	}

}
