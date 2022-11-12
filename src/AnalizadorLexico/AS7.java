package AnalizadorLexico;
import java.io.Reader;

public class AS7 extends AccionSemantica{
	
	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		return token.charAt(0);
	}

}
