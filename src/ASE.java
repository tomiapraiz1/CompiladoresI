import java.io.IOException;
import java.io.Reader;

public class ASE extends AccionSemantica {

	public ASE(AnalizadorLexico l) {
		super(l);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			char c = (char) r.read();
			System.out.println("Error lexico en la linea: " + l.getLine());
			if(c == AnalizadorLexico.NL)
				l.setLine(l.getLine()+1);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return error;
	}

}
