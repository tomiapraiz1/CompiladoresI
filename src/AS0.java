import java.io.IOException;
import java.io.Reader;

public class AS0 extends AccionSemantica{
	
	public AS0(AnalizadorLexico l) {
		super(l);
	}

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			int c = r.read();
			if (c == AnalizadorLexico.NL)
				l.setLine(l.getLine()+1);
		}
		catch(IOException e) {
            e.printStackTrace();
        }
		
		return t_activo;
	}

}
