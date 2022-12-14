package AnalizadorLexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class AnalizadorLexico {
	
	private static int [][] state_matrix;
	private static AccionSemantica[][] as_matrix;
	private static int rows, columns;
	private static int line = 1;
	
	public static Reader r;
	public static StringBuilder token_actual = new StringBuilder();
	public static int estado_actual = 0;
	public static final int longitud_id = 25;
	
	public static final int TAB = '\t';
	public static final int BLANCO = ' ';
	public static final int NL = '\n';
	public static final int PL = '\r'; //vuelve al principio de la linea
	public static final int MINUSCULA = 'a';
	public static final int MAYUSCULA = 'A';
	public static final int DIGITO = '0';
	public static final int IDENTIFICADOR = 257;
	public static final int CONSTANTE = 258;
	public static final int CADENA = 279;
	
	public static final Integer maxInt = 32768;
	public static final Integer minInt = -32768;
	public static final double maxF = 2147483648.0d;
	
	
	public static void setAnalizadorLexico(String pathS, String pathA, int rows, int columns) {
		readASMatrix(pathA, rows, columns);
		readStateMatrix(pathS, rows, columns);
	}

	public static int getLine() {
		return line;
	}
	
	public static void setLine(int linea) {
		line = linea;
	}
	
	private static void readStateMatrix(String path, int _rows, int _columns) {
        
		state_matrix = new int[rows][columns];
        rows = _rows;
        columns = _columns;

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
	
	private static AccionSemantica crearAccion(String accion) {
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
            case "AS10":
            	return new AS10();
            default:
                return null;
        }
    }
	
	private static void readASMatrix(String path, int _rows, int _columns) {
        
		as_matrix = new AccionSemantica[_rows][_columns];
        rows = _rows;
        columns = _columns;

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
	
	public static boolean endOfFile(Reader r) throws IOException {
        r.mark(1);
        int value = r.read();
        r.reset();

        return value < 0;
    }
	
	public static char getNextCharWithoutAdvancing(Reader r) throws IOException {
        r.mark(1);
        char next_character = (char) r.read();
        r.reset();

        return next_character;
    }
	
	private static int obtenerTipoCaracter(char caracter) {
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
                caracter_actual = 4;
                break;
            case TAB:
                caracter_actual = 23;
                break;
            case NL:
            case PL:
                caracter_actual = 24;
                break;
            case MINUSCULA:
                caracter_actual = 1;
                break;
            case MAYUSCULA:
                caracter_actual = 0;
                break;
            case '_':
                caracter_actual = 3;
                break;
            case DIGITO:
                caracter_actual = 2;
                break;
            case '.':
                caracter_actual = 8;
                break;
            case 'F':
                caracter_actual = 7;
                break;
            case '+':
                caracter_actual = 15;
                break;
            case '-':
                caracter_actual = 16;
                break;
            case '/':
                caracter_actual = 18;
                break;
            case '(':
                caracter_actual = 21;
                break;
            case ')':
                caracter_actual = 22;
                break;
            case ',':
                caracter_actual = 6;
                break;
            case ';':
                caracter_actual = 5;
                break;
            case ':':
                caracter_actual = 13;
                break;
            case '=':
                caracter_actual = 11;
                break;
            case '>':
                caracter_actual = 10;
                break;
            case '<':
                caracter_actual = 9;
                break;
            case '*':
                caracter_actual = 17;
                break;
            case '{':
                caracter_actual = 19;
                break;
            case '}':
            	caracter_actual = 20;
            	break;
            case '!':
            	caracter_actual = 12;
            	break;
            case 39: //representa ' en ascii
            	caracter_actual = 14;
            	break;
            default:
                caracter_actual = 25;
                break;
        }

        AccionSemantica accion = as_matrix[estado_actual][caracter_actual];
        int identificador_token = accion.ejecutar(lector, token_actual);
        estado_actual = state_matrix[estado_actual][caracter_actual];

        return identificador_token;
    }
	
	
	

}