package AnalizadorLexico;
import java.io.Reader;

public class AS4 extends AccionSemantica {

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		// TODO Auto-generated method stub
		try {
			char c = (char) r.read();
			return c;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return error;
	}
}
