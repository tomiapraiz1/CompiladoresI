package AnalizadorLexico;
import java.io.IOException;
import java.io.Reader;

import AnalizadorSintactico.Parser;

public class ASE extends AccionSemantica {

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			char c = (char) r.read();
			Parser.erroresLexicos.add("Error lexico en la linea: " + AnalizadorLexico.getLine() + ": el caracter " + c + " no se reconoce en el contexto");
			if(c == AnalizadorLexico.NL)
				AnalizadorLexico.setLine(AnalizadorLexico.getLine()+1);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return error;
	}

}
