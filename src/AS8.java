import java.io.Reader;

public class AS8 extends AccionSemantica{

	@Override
	public int ejecutar(Reader r, StringBuilder token) {
		try {
            char siguiente_caracter = (char) r.read(); // Lee el siguiente caracter
            token.append(siguiente_caracter); // Concatena el caracter actual
        } catch (Exception e) {
            e.printStackTrace();
        }

        String simbolo = token.toString();
        
        System.out.println(simbolo);

        return TablaPalabrasReservadas.identificadorPalabra(simbolo);
	}

}
