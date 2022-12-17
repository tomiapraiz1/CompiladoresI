package AnalizadorLexico;

public class Atributo {
	
	private int idToken;
    private String lexema; 
    private String tipo;
    private int line;
    private String uso;
    private String ambito = "";
    private int cantidadParametros = -1;
    private String tipoP1 = "";
    private String tipoP2 = "";
    private String tercetoSalto = "";
    private String valor = "0";
    
    public Atributo(String lexema, int id) {
    	this.lexema = lexema;
    	this.idToken = id;
    }
    
    public Atributo(String lexema, String tipo, String valor) {
    	this.lexema = lexema;
    	this.tipo = tipo;
    	this.valor = valor;
    }
    
    public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
    
    public String getTipoP1() {
		return tipoP1;
	}

	public void setTipoP1(String tipoP1) {
		this.tipoP1 = tipoP1;
	}

	public String getTipoP2() {
		return tipoP2;
	}

	public void setTipoP2(String tipoP2) {
		this.tipoP2 = tipoP2;
	}

	public String getTercetoSalto() {
		return tercetoSalto;
	}

	public void setTercetoSalto(String tercetoSalto) {
		this.tercetoSalto = tercetoSalto;
	}

	public Atributo(String lexema, String tipo) {
    	this.lexema = lexema;
    	this.tipo = tipo;
    }
    
    public Atributo(String lexema, int idToken, String tipo, int line){
        this.lexema = lexema;
    	this.idToken = idToken;
        this.tipo = tipo;
        this.line = line;
        this.uso = "";
        this.ambito = "";
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

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public int getCantidadParametros() {
		return cantidadParametros;
	}

	public void setCantidadParametros(int cantidadParametros) {
		this.cantidadParametros = cantidadParametros;
	}

	@Override
	public String toString() {
		if (cantidadParametros != -1)
			return "Atributo [idToken=" + idToken + ", lexema=" + lexema + ", tipo=" + tipo + ", line=" + line + ", uso="
				+ uso + ", ambito=" + ambito + ", cantidadParametros=" + cantidadParametros + ", tercetoSalto="
						+ tercetoSalto +  ", tipoP1=" + tipoP1 + ", tipoP2=" + tipoP2 +"]";
		else
			return "Atributo [idToken=" + idToken + ", lexema=" + lexema + ", tipo=" + tipo + ", line=" + line + ", uso="
			+ uso + ", ambito=" + ambito + ", tercetoSalto=" + tercetoSalto + ", valor=" + valor +"]";
	}

	

	

}
