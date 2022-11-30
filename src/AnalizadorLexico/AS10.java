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
			simbolo = simbolo.replaceAll("(\n|\r)", "");
			simbolo = simbolo.replaceAll("/","");
			simbolo = simbolo.replaceAll("(\t|\r)", "");
			AnalizadorLexico.token_actual.setLength(0);
			for(int i=0;i<simbolo.length();i++)
				AnalizadorLexico.token_actual.append(simbolo.charAt(i));
			TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.CADENA, "cadena", AnalizadorLexico.getLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AnalizadorLexico.CADENA;
	}

}
