package AnalizadorLexico;

import java.io.Reader;

public class AS10 extends AccionSemantica {

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			r.read();
			if (token.lastIndexOf("\n") != -1) {
				int sl = token.lastIndexOf("\n");
				token.delete(sl,sl+2);
			}
			String simbolo = token.toString();
			TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.CADENA, "Cadena", AnalizadorLexico.getLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AnalizadorLexico.CADENA;
	}

}
