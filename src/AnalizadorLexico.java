import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalizadorLexico {
	
	private static int [][] state_matrix;
	private static String[][] as_matrix;
	private int rows, columns;
	
	public void readStateMatrix(String path, int rows, int columns) {
        
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
	
	public void mostrarMatriz() {
		for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                System.out.print(state_matrix[i][j] + " ");
            }
            System.out.println();
        }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AnalizadorLexico l = new AnalizadorLexico();
		l.readStateMatrix("E:\\Facultad\\4to\\Compiladores I\\MatrizEstados.txt", 14, 24);
		l.mostrarMatriz();
	}

}
