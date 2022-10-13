package AnalizadorLexico;
import java.io.Reader;

public class AS6 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		
		token.delete(0, token.length()); // Reinicia el token

        try {
            r.read(); // Lee el siguiente caracter
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t_activo;
	}

}