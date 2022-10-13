package AnalizadorLexico;
import java.io.Reader;

public abstract class AccionSemantica {
	
	public static int t_activo = -1;
	public static int error = -2;

	public abstract int ejecutar(Reader r, StringBuilder token);

}
