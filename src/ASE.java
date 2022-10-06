import java.io.IOException;
import java.io.Reader;

public class ASE extends AccionSemantica {

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			char c = (char) r.read();
			System.out.println("Error lexico en la linea: " + AnalizadorLexico.getLine());
			if(c == AnalizadorLexico.NL)
				AnalizadorLexico.setLine(AnalizadorLexico.getLine()+1);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return error;
	}

}
