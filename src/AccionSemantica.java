import java.io.Reader;

public abstract class AccionSemantica {
	
	protected AnalizadorLexico l;
	protected int t_activo = -1;
	protected int error = -2;
	
	public AccionSemantica(AnalizadorLexico l) {
		this.l = l;
	}

	public abstract int ejecutar(Reader r, StringBuilder token);

}
