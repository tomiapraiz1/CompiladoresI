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
	private static AccionSemantica[][] as_matrix;
	private int rows, columns;
	private static int line = 1;
	
	public static Reader r;
	public static StringBuilder token_actual;
	public static int estado_actual = 0;
	public static final int longitud_id = 25;
	
	public static final char TAB = '\t';
	public static final char BLANCO = ' ';
	public static final char NL = '\n';
	public static final char PL = '\r'; //vuelve al principio de la linea
	public static final char MINUSCULA = 'a';
	public static final char MAYUSCULA = 'A';
	public static final char DIGITO = '0';
	public static final int IDENTIFICADOR = 257;
	public static final int CONSTANTE = 258;
	public static final double maxInt = 32767;
	public static final double minInt = -32768;
	
	
	public AnalizadorLexico(String pathS, String pathA, int rows, int columns) {
		this.readASMatrix(pathA, rows, columns);
		this.readStateMatrix(pathS, rows, columns);
	}

	public static int getLine() {
		return line;
	}
	
	public static void setLine(int linea) {
		line = linea;
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
	
	private AccionSemantica crearAccion(String accion) {
        switch (accion) {
            case "AS0":
                return new AS0();
            case "AS1":
                return new AS1();
            case "AS2":
                return new AS2();
            case "ASE":
                return new ASE();
            case "AS3":
                return new AS3();
            case "AS4":
                return new AS4();
            case "AS5":
                return new AS5();
            case "AS6":
                return new AS6();
            case "AS7":
                return new AS7();
            case "AS8":
                return new AS8();
            case "AS9":
                return new AS9();
            default:
                return null;
        }
    }
	
	private void readASMatrix(String path, int rows, int columns) {
        
		as_matrix = new AccionSemantica[rows][columns];
        this.rows = rows;
        this.columns = columns;

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            for (int i = 0; i < columns; ++i) {
                for (int j = 0; j < rows; ++j) {
                    as_matrix[j][i] = crearAccion(scanner.nextLine());
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
	
	private static char obtenerTipoCaracter(char caracter) {
        if (Character.isDigit(caracter)) {
            return DIGITO;
        } else if (Character.isLowerCase(caracter)) {
            return MINUSCULA;
        } else if (caracter != 'F' && Character.isUpperCase(caracter)) {
            return MAYUSCULA;
        } else {
            return caracter;
        }
    }
	
	public static int cambiarEstado(Reader lector, char caracter) {
        int caracter_actual;
        switch (obtenerTipoCaracter(caracter)) {
            case BLANCO:
                caracter_actual = 0;
                break;
            case TAB:
                caracter_actual = 1;
                break;
            case NL:
            case PL:
                caracter_actual = 2;
                break;
            case MINUSCULA:
                caracter_actual = 3;
                break;
            case MAYUSCULA:
                caracter_actual = 4;
                break;
            case '_':
                caracter_actual = 5;
                break;
            case DIGITO:
                caracter_actual = 6;
                break;
            case '.':
                caracter_actual = 7;
                break;
            case 'E':
                caracter_actual = 8;
                break;
            case '+':
                caracter_actual = 9;
                break;
            case '-':
                caracter_actual = 10;
                break;
            case '/':
                caracter_actual = 11;
                break;
            case '(':
                caracter_actual = 12;
                break;
            case ')':
                caracter_actual = 13;
                break;
            case ',':
                caracter_actual = 14;
                break;
            case ';':
                caracter_actual = 15;
                break;
            case ':':
                caracter_actual = 16;
                break;
            case '=':
                caracter_actual = 17;
                break;
            case '>':
                caracter_actual = 18;
                break;
            case '<':
                caracter_actual = 19;
                break;
            case '*':
                caracter_actual = 20;
                break;
            case '{':
                caracter_actual = 21;
                break;
            case '}':
            	caracter_actual = 22;
            	break;
            default:
                caracter_actual = 23;
                break;
        }

        AccionSemantica accion = as_matrix[estado_actual][caracter_actual];
        int identificador_token = accion.ejecutar(lector, token_actual);
        estado_actual = state_matrix[estado_actual][caracter_actual];

        return identificador_token;
    }


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*String pathS = "D:\\Tomi\\repo-compi\\CompiladoresI\\MatrizEstados.txt";
		String pathA = "D:\\Tomi\\repo-compi\\CompiladoresI\\MatrizAcciones.txt";
		AnalizadorLexico l = new AnalizadorLexico(pathS, pathA, 15, 26);
		
		ASE a = new ASE();
		
		BufferedReader archivo = new BufferedReader(new FileReader("D:\\Tomi\\repo-compi\\CompiladoresI\\pruebas.txt"));
        StringBuilder n = new StringBuilder();
        
        for (int i=0; i<7;i++) {
        	a.ejecutar(archivo, n);
        }
        
		//l.mostrarStateMatrix();
		l.mostrarASMatrix();
		*/
		System.out.println(AnalizadorLexico.maxInt);
	}

}