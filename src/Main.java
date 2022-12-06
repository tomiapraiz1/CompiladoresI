import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import GeneracionCodigo.*;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				String pathA = "src/AnalizadorLexico/MatrizAcciones.txt";
				String pathS = "src/AnalizadorLexico/MatrizEstados.txt";
				AnalizadorLexico.setAnalizadorLexico(pathS, pathA, 16, 26);
				System.out.println("Ingrese la direccion del archivo de prueba:");
				Scanner s = new Scanner(System.in);
				String prueba = s.nextLine();
				AnalizadorLexico.r = new BufferedReader(new FileReader(prueba));
				Parser p = new Parser();
				p.run();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (AnalizadorLexico.r != null)
						AnalizadorLexico.r.close();						
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			TablaSimbolos.imprimirTabla();
			System.out.println("\nErrores:");
			Parser.printErrores();
			System.out.println("\nTercetos:");
			TercetoManager.imprimirTercetosSinAmbito();
			//GeneradorAssembler.procesarArchivo();
			//GeneradorAssembler.crearArchivoASM();
	
	}

}



