import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

public class AnalizadorLexico {
	
	private static int [][] state_matrix;
	private static String[][] as_matrix;
	private int rows, columns;
	private int line = 1;
	
	public static Reader r;
	public static int estado = 0;
	public StringBuilder token;
	
	public static final char TAB = '\t';
	public static final char BLANCO = ' ';
	public static final char NL = '\n';
	public static final char PL = '\r'; //vuelve al principio de la linea
	
	
	public AnalizadorLexico(String pathS, String pathA, int rows, int columns) {
		this.readASMatrix(pathA, rows, columns);
		this.readStateMatrix(pathS, rows, columns);
	}

	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	private void readStateMatrix(String path, int rows, int columns) {
        
		state_matrix = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            for (int i = 0; i < columns; ++i) {
                for (int j = 0; j < rows; ++j) {
                    state_matrix[j][i] = Integer.parseInt(scanner.nextLine());
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo " + path);  
            e.printStackTrace();
        }

    }
	
	private void readASMatrix(String path, int rows, int columns) {
        
		as_matrix = new String[rows][columns];
        this.rows = rows;
        this.columns = columns;

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            for (int i = 0; i < columns; ++i) {
                for (int j = 0; j < rows; ++j) {
                    as_matrix[j][i] = scanner.nextLine();
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo " + path);  
            e.printStackTrace();
        }

    }
	
	public void mostrarStateMatrix() {
		for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                System.out.print(state_matrix[i][j] + " ");
            }
            System.out.println();
        }
	}
	
	public void mostrarASMatrix() {
		for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                System.out.print(as_matrix[i][j] + " ");
            }
            System.out.println();
        }
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String pathS = "E:\\Facultad\\4to\\Compiladores I\\TPE-Compiladores\\CompiladoresI\\MatrizEstados.txt";
		String pathA = "E:\\Facultad\\4to\\Compiladores I\\TPE-Compiladores\\CompiladoresI\\MatrizAcciones.txt";
		AnalizadorLexico l = new AnalizadorLexico(pathS, pathA, 15, 26);
		
		AS0 a = new AS0(l);
		
		BufferedReader archivo = new BufferedReader(new FileReader("E:\\Facultad\\4to\\Compiladores I\\TPE-Compiladores\\CompiladoresI\\pruebas.txt"));
        StringBuilder n = new StringBuilder();
        
        for (int i=0; i<15;i++) {
        	a.ejecutar(archivo, n);
        }
        
        System.out.println(l.getLine());
        
		//l.mostrarStateMatrix();
		//l.mostrarASMatrix();
		
	}

}
