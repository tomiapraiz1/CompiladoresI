import java.io.Reader;

public abstract class AccionSemantica {
	
	protected int t_activo = -1;
	protected int error = -2;

	public abstract int ejecutar(Reader r, StringBuilder token);

}
