import java.io.Reader;

public class AS5 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		// TODO Auto-generated method stub
		if (token.indexOf("F") != -1)
			token.setCharAt(token.indexOf("F"), 'E');
		token.append("d");
		String simbolo = token.toString();
		try {
			Double valor = Double.parseDouble(simbolo);
			System.out.println(simbolo);
			if (valor > AnalizadorLexico.maxF) {
				System.out.println("Warning linea " + AnalizadorLexico.getLine() + " : el valor del simbolo sobrepasa el valor maximo."
						+ " El mismo fue truncado al maximo.");
				simbolo = Double.toString(AnalizadorLexico.maxF);
				System.out.println(simbolo);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.CONSTANTE, "Float", AnalizadorLexico.getLine());
		
		System.out.println("Constante flotante " + simbolo);
		
		return AnalizadorLexico.CONSTANTE;
	}
}
