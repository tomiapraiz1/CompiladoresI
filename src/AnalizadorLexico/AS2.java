package AnalizadorLexico;
import java.io.Reader;

public class AS2 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		// TODO Auto-generated method stub
		int id;
		String simbolo = token.toString();
		int id_pr = TablaPalabrasReservadas.identificadorPalabra(simbolo);
		if (id_pr != TablaPalabrasReservadas.palabra_noreservada) {
			id = id_pr;
			System.out.println("Palabra reservada " + simbolo);
		} else {
			if (token.length() > AnalizadorLexico.longitud_id) { //se pasa de longitud
				simbolo = token.substring(0, AnalizadorLexico.longitud_id);
				System.out.println("Warning linea " + AnalizadorLexico.getLine() + ": el identificador " + token + 
						" fue truncado porque supera la longitud maxima de caracteres");
			}
			TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.IDENTIFICADOR, "", AnalizadorLexico.getLine());
			id = AnalizadorLexico.IDENTIFICADOR;
			System.out.println("Identificador " + simbolo);
		}
		return id;
	}

}