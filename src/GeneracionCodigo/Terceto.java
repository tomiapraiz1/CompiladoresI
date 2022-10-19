package GeneracionCodigo;

public class Terceto {
	private String operando;
	private String operador1;
	private String operador2;
	
	Terceto(String _operando, String _operador1, String _operador2){
		this.operando = _operando;
		this.operador1 = _operador1;
		this.operador2 = _operador2;
	}

	public String getOperando() {
		return operando;
	}

	public void setOperando(String operando) {
		this.operando = operando;
	}

	public String getOperador1() {
		return operador1;
	}

	public void setOperador1(String operador1) {
		this.operador1 = operador1;
	}

	public String getOperador2() {
		return operador2;
	}

	public void setOperador2(String operador2) {
		this.operador2 = operador2;
	}

}
