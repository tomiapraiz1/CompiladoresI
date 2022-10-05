import java.io.IOException;
import java.io.Reader;

public class AS1 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
			char c = (char) r.read();
			token.append(c);
			System.out.println((int) c);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return t_activo;
	}

}
