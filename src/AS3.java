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
			} else if (valor < AnalizadorLexico.minInt) {
				System.out.println("Warning linea " + AnalizadorLexico.getLine() + " : el valor del simbolo sobrepasa el valor minimo."
						+ " El mismo fue truncado al minimo.");
				simbolo = Double.toString(AnalizadorLexico.minInt);
			}
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		if (TablaSimbolos.obtenerSimbolo(simbolo) == -1) {
			TablaSimbolos.agregarSimbolo(simbolo);
			int id_t = TablaSimbolos.obtenerSimbolo(simbolo);
			TablaSimbolos.agregarAtributo(id_t, "Tipo", "Entero");
		}
		return AnalizadorLexico.CONSTANTE;
	}

}
