
public class Atributo {
	
	private int idToken;
    private String lexema; 
    private String tipo;
    private int line;
    
    public Atributo(String lexema, int id) {
    	this.lexema = lexema;
    	this.idToken = id;
    }
    
    public Atributo(String lexema, int idToken, String tipo, int line){
        this.lexema = lexema;
    	this.idToken = idToken;
        this.tipo = tipo;
        this.line = line;
    }

    public int getIdToken() {
        return this.idToken;
    }

    public String getLexema() {
        return this.lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }    

    public void setIdToken(int idToken) {
        this.idToken = idToken;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return "Atributo [idToken=" + idToken + ", lexema=" + lexema + ", tipo=" + tipo + ", line=" + line + "]";
	}
    
    

}
