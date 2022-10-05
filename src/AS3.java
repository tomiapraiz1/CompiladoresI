import java.io.Reader;

public class AS3 extends AccionSemantica{
	
	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		// TODO Auto-generated method stub
		String simbolo = token.toString();
		try {
			Integer valor = Integer.parseInt(simbolo);
			if (valor > AnalizadorLexico.maxInt) {
				System.out.println("Warning linea " + AnalizadorLexico.getLine() + " : el valor del simbolo sobrepasa el valor maximo."
						+ " El mismo fue truncado al maximo.");
				simbolo = Double.toString(AnalizadorLexico.maxInt);
			} 
		} catch(Exception e){
			e.printStackTrace();
		}
		
		TablaSimbolos.agregarSimbolo(simbolo, AnalizadorLexico.CONSTANTE, "Entero", AnalizadorLexico.getLine());
		
		return AnalizadorLexico.CONSTANTE;
	}

}
