import java.io.Reader;

public class AS7 extends AccionSemantica{
	
	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		System.out.println(token.charAt(0));
		return token.charAt(0);
	}

}
