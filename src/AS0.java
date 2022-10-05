import java.io.IOException;
import java.io.Reader;

public class AS0 extends AccionSemantica{
	

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			int c = r.read();
			if (c == AnalizadorLexico.NL)
				AnalizadorLexico.setLine(AnalizadorLexico.getLine()+1);
		}
		catch(IOException e) {
            e.printStackTrace();
        }
		
		return t_activo;
	}

}
