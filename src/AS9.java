import java.io.Reader;

public class AS9 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		 try {
	            char caracter = (char) r.read(); // Lee el siguiente caracter

	            if (caracter == AnalizadorLexico.NL) {
	                AnalizadorLexico.setLine(AnalizadorLexico.getLine() + 1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        int barra = token.lastIndexOf("/");
	        token.delete(barra, token.length());

	        return t_activo;
	}

}
