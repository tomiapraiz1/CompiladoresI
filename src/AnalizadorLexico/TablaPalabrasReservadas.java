package AnalizadorLexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class TablaPalabrasReservadas {
	
	private static final HashMap<String, Integer> tabla = readMap("src/AnalizadorLexico/PalabrasReservadas.txt");
	
	public static final int palabra_noreservada = -1;
	
	public static int identificadorPalabra(String palabra) {
		return tabla.getOrDefault(palabra, palabra_noreservada);
	}

	public static HashMap<String, Integer> readMap(String path) {
		HashMap<String, Integer> tabla = new HashMap<>();

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNext()) {
                String palabra_reservada = scanner.next();
                int identificador = scanner.nextInt();
                tabla.put(palabra_reservada, identificador);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo " + path);
            e.printStackTrace();
        }

        return tabla;
	}
	
	public static void imprimirTabla() {
        System.out.println("\nTabla de Palabras Reservadas:");

        for (Entry<String, Integer> entrada: tabla.entrySet()) {
            System.out.print(entrada.getKey() + ": " + entrada.getValue());
            System.out.println();
        }
    }
	
	/*public static void main(String[] args) {
		TablaPalabrasReservadas t = new TablaPalabrasReservadas();
		t.imprimirTabla();
	
	}*/
}
