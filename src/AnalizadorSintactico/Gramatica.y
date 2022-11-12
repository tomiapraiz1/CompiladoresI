%{
package AnalizadorSintactico;
import java.io.IOException;
import java.io.Reader;
import AnalizadorLexico.*;
import GeneracionCodigo.*;
import java.util.ArrayList;
%}

%token ID CTE IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN DO UNTIL CONTINUE ASIG MENORIGUAL MAYORIGUAL DISTINTO COMENTARIO I16 F32 CONST CADENA

%start program

%%

program:	nombre_programa inicio_programa bloque fin_programa
			| nombre_programa bloque fin_programa {erroresSintacticos.add("Se esperaba un {");}
			| nombre_programa inicio_programa bloque {erroresSintacticos.add("Se esperaba un }");}
;

inicio_programa:	'{' {Ambito.concatenarAmbito("main");}
;

fin_programa:	'}' {Ambito.removeAmbito();}
;

nombre_programa:	ID
					| CTE {erroresSintacticos.add("El nombre del programa no puede ser una constante");}
;

bloque:		bloque sentencia
		| sentencia
;

sentencia:	sentencia_ejecutable
		| sentencia_declarativa
;

sentencia_declarativa:	declaracion_variables
			| declaracion_funcion
			| declaracion_constantes
;

declaracion_variables:	tipo lista_variables ';' 
;

lista_variables:	ID	{setTipo($1.sval); setUso($1.sval, "Variable"); $1.sval = TablaSimbolos.modificarNombre($1.sval);}
			| lista_variables ',' ID {setTipo($3.sval); setUso($3.sval, "Variable"); $3.sval = TablaSimbolos.modificarNombre($3.sval);}
;

tipo:		I16	{$$.sval = $1.sval; tipoAux = $1.sval;}
		| F32	{$$.sval = $1.sval; tipoAux = $1.sval;}
;

declaracion_funcion:	header_funcion cuerpo_funcion
;

header_funcion:		FUN ID '(' lista_parametros ')' ':' tipo {setTipo($2.sval); setUso($2.sval, "Funcion"); $2.sval = TablaSimbolos.modificarNombre($2.sval);}
			| FUN ID '(' ')' ':' tipo	{setTipo($2.sval); setUso($2.sval, "Funcion"); $2.sval = TablaSimbolos.modificarNombre($2.sval);}
			| FUN ID ')' ':' tipo	{erroresSintacticos.add("Falta un (");}
			| FUN ID '(' ':' tipo	{erroresSintacticos.add("Falta un )");}
			| FUN ID '(' ')' tipo	{erroresSintacticos.add("Falta un :");}
			| FUN ID '(' ')' ':' 	{erroresSintacticos.add("Se esperaba un tipo de retorno");}
			| FUN '(' ')' ':' tipo	{erroresSintacticos.add("Se esperaba un identificador de la funcion");}
;

lista_parametros:	tipo ID ',' tipo ID {setTipo($1.sval,$2.sval);setUso($2.sval, "Nombre_Parametro_Funcion"); setTipo($4.sval,$5.sval); setUso($5.sval, "Nombre_Parametro_Funcion"); }
                	| tipo ID {setUso($2.sval, "Nombre_Parametro_Funcion");}
                	| ID		{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
                	| ID ',' ID {erroresSintacticos.add("Los identificadores deben tener un tipo");}
;

cuerpo_funcion:		inicio_funcion bloque retorno_funcion fin_funcion
					| inicio_funcion bloque fin_funcion {erroresSintacticos.add("La funcion debe retornar un valor");}
;

inicio_funcion: '{' {Ambito.concatenarAmbito("func");}
;

fin_funcion: '}' {Ambito.removeAmbito();}
;

retorno_funcion:	RETURN '(' expresion_aritmetica ')' ';'
					| RETURN expresion_aritmetica ')' ';' {erroresSintacticos.add("Falta un (");}
					| RETURN '(' expresion_aritmetica ';' {erroresSintacticos.add("Falta un )");}
					| RETURN '(' expresion_aritmetica ')' {erroresSintacticos.add("Falta un ;");}
					| RETURN '('  ')' ';' {erroresSintacticos.add("Falta un valor que devolver");}
;

expresion_aritmetica:		expresion_aritmetica '+' termino {$$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", $1.sval, $3.sval);}
		    		| expresion_aritmetica '-' termino {$$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", $1.sval, $3.sval);}
	            		| termino{$$.sval = $1.sval;}
;

termino:		termino '*' factor {$$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", $1.sval, $3.sval);}
       			| termino '/' factor {$$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", $1.sval, $3.sval);}
       			| factor {$$.sval = $1.sval;}
;

factor:		ID {comprobarAmbito($1.sval); $$.sval = $1.sval;}
      		| CTE {$$.sval = $1.sval;}
      		| '-' CTE {$$.sval = "-" + $1.sval;}
      		| ID '(' lista_inv_func ')'
;

lista_inv_func:		lista_inv_func ',' ID
	      		| lista_inv_func ',' CTE
	      		| CTE
	      		| ID
;

declaracion_constantes:	CONST list_constantes ';'
						| CONST list_constantes {erroresSintacticos.add("Falta un ;");}
;

list_constantes:	list_constantes ',' asignacion 
			| asignacion 
;

asignacion:		ID ASIG expresion_aritmetica {comprobarAmbito($1.sval); $$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", $1.sval, $3.sval);}
				| ID expresion_aritmetica {erroresSintacticos.add("Falta =:");}
;

sentencia_ejecutable:	ejecutable
			| con_etiqueta
;

ejecutable:		asignacion ';'
			| asignacion {erroresSintacticos.add("Falta un ;");}
			| seleccion ';'
			| seleccion {erroresSintacticos.add("Falta un ;");}
			| impresion ';'
			| impresion {erroresSintacticos.add("Falta un ;");}
			| estruct_do_until ';'
			| estruct_do_until {erroresSintacticos.add("Falta un ;");}
;

con_etiqueta:		BREAK ';'
			| BREAK {erroresSintacticos.add("Falta un ;");}
			| BREAK CTE ';'
			| BREAK CTE {erroresSintacticos.add("Falta un ;");}
			| CONTINUE ';'
			| CONTINUE {erroresSintacticos.add("Falta un ;");}
			| CONTINUE ':' ID ';'
			| CONTINUE ':' ID {erroresSintacticos.add("Falta un ;");}
			| CONTINUE ':' ';' {erroresSintacticos.add("Falta un identificador");}
			| ID ':' estruct_do_until ';'
			| ID ':' estruct_do_until {erroresSintacticos.add("Falta un ;");}
			| ID ':'  ';'{erroresSintacticos.add("Falta un cuerpo do until");}
			| ID ASIG sentencia_ctr_expr ';'
			| ID ASIG sentencia_ctr_expr {erroresSintacticos.add("Falta un ;");}
			| ID sentencia_ctr_expr ';' {erroresSintacticos.add("Falta un cuerpo do until");}
			| ID ASIG ';' {erroresSintacticos.add("Falta un =:");}
;

seleccion:		IF condicion cuerpo_if END_IF {TercetoManager.add_seleccion();}
				| IF cuerpo_if END_IF {erroresSintacticos.add("Falta la condicion del if");}
				| IF condicion END_IF {erroresSintacticos.add("Falta el cuerpo del if");}
				| IF condicion cuerpo_if {erroresSintacticos.add("Falta un end_if");}
;

condicion:		'(' expresion_aritmetica operador expresion_aritmetica ')' {TercetoManager.crear_terceto($3.sval, $2.sval, $4.sval); TercetoManager.add_seleccion_cond();}
				| '(' operador expresion_aritmetica ')' {erroresSintacticos.add("Falta un valor con que comparar");}
				| '(' expresion_aritmetica operador ')' {erroresSintacticos.add("Falta un valor con que comparar");}
;

operador:		'<' {$$.sval = "<";}
			| MENORIGUAL {$$.sval = "<=";}
			| '>' {$$.sval = ">";}
			| MAYORIGUAL {$$.sval = ">=";}
			| '=' {$$.sval = "=";}
			| DISTINTO {$$.sval = "=!";}
;

cuerpo_if:		THEN cuerpo_then ELSE cuerpo_else 
			| THEN cuerpo_then
			| THEN cuerpo_then ELSE {erroresSintacticos.add("Falta un cuerpo de else");}
;

cuerpo_then:	'{' lista_sentencias_ejecutables '}' {TercetoManager.add_seleccion_then(); }
;

cuerpo_else:		 '{' lista_sentencias_ejecutables '}' 
;

lista_sentencias_ejecutables:	lista_sentencias_ejecutables sentencia_ejecutable
				| sentencia_ejecutable
;

impresion:		OUT '(' CADENA ')'
				| OUT '(' CADENA {erroresSintacticos.add("Falta un )");}
				| OUT CADENA ')' {erroresSintacticos.add("Falta un (");}
				| OUT '(' ')' {erroresSintacticos.add("Falta una cadena que imprimir");}
;

estruct_do_until:	DO inicion_estruct_do_until lista_sentencias_ejecutables fin_estruct_do_until until_condicion {TercetoManager.add_iter_do_until();}
					| DO lista_sentencias_ejecutables fin_estruct_do_until until_condicion {erroresSintacticos.add("Falta un {");}
					| DO inicion_estruct_do_until lista_sentencias_ejecutables until_condicion {erroresSintacticos.add("Falta un }");}
					| DO inicion_estruct_do_until fin_estruct_do_until until_condicion {erroresSintacticos.add("Faltan sentencias de ejecucion");}
;

inicion_estruct_do_until: '{' {Ambito.concatenarAmbito("doUntil"); TercetoManager.add_inicio_do_until();}
;

fin_estruct_do_until: '}' {Ambito.removeAmbito();}
;

until_condicion:	UNTIL condicion
					| UNTIL {erroresSintacticos.add("Falta una condicion");}
;

sentencia_ctr_expr:	DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion else_until
					| DO lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion else_until {erroresSintacticos.add("Falta un {");}
					| DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables until_condicion else_until {erroresSintacticos.add("Falta un }");}
					| DO inicio_sentencia_ctr_expr fin_sentencia_ctr_expr until_condicion else_until {erroresSintacticos.add("Faltan sentencias de ejecucion");}

;

inicio_sentencia_ctr_expr: '{' {Ambito.concatenarAmbito("doUntilExpr");}
;

fin_sentencia_ctr_expr: '}' {Ambito.removeAmbito();}
;

else_until:		ELSE CTE
				| ELSE {erroresSintacticos.add("Falta una constante para asignar");}
				| CTE {erroresSintacticos.add("Se esperaba un else");}
;

%%

public String tipoAux = "";
public static ArrayList<String> erroresSintacticos = new ArrayList<String>();
public static ArrayList<String> erroresLexicos = new ArrayList<String>();

void setTipo(String simbolo){
	TablaSimbolos.modificarTipo(simbolo, tipoAux);
}

void setTipo(String tipo, String simbolo){
	TablaSimbolos.modificarTipo(simbolo,tipo);
}

void setUso(String simbolo, String uso){
	TablaSimbolos.modificarUso(simbolo, uso);
}

void comprobarAmbito(String simbolo){
	String aux = Ambito.getAmbito(simbolo);
	if (aux == null)
		System.out.println("Error");
}

void yyerror(String mensaje) {
        // funcion utilizada para imprimir errores que produce yacc
        System.out.println("Error yacc: " + mensaje);
}

public static void printErrores(){
	if (!erroresLexicos.isEmpty()) {
		System.out.println(erroresLexicos);
	} else System.out.println("No hay errores lexicos.");
	if (!erroresSintacticos.isEmpty()) {
		System.out.println(erroresSintacticos);
	} else System.out.println("No hay errores sintacticos.");
}

int yylex() {
	    int identificador_token = 0;
	    Reader lector = AnalizadorLexico.r;
	    AnalizadorLexico.estado_actual = 0;

	    // Leo hasta que el archivo termine
	    while (true) {
	            try {
	                    if (AnalizadorLexico.endOfFile(lector)) {
	                            break;
	                    }

	                    char caracter = AnalizadorLexico.getNextCharWithoutAdvancing(lector);
	                    identificador_token = AnalizadorLexico.cambiarEstado(lector, caracter);

	                    // Si llego a un estado final
	                    if (identificador_token != AccionSemantica.t_activo) {
	                            yylval = new ParserVal(AnalizadorLexico.token_actual.toString());
	                            AnalizadorLexico.token_actual.delete(0, AnalizadorLexico.token_actual.length());
	                            return identificador_token;
	                    }
	            } catch (IOException e) {
	                    e.printStackTrace();
	            }
	    }

	    return identificador_token;
}