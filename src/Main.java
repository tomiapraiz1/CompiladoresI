import java.io.BufferedReader;
import java.io.FileReader;
import AnalizadorLexico.*;
import AnalizadorSintatico.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				String pathA = "src/AnalizadorLexico/MatrizAcciones.txt";
				String pathS = "src/AnalizadorLexico/MatrizEstados.txt";
				AnalizadorLexico l = new AnalizadorLexico(pathS, pathA, 15, 26);
				AnalizadorLexico.r = new BufferedReader(new FileReader("src/test/prueba1.txt"));
				Parser p = new Parser();
				p.run();
				TablaSimbolos.imprimirTabla();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}

}


