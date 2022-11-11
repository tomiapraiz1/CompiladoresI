%{
package AnalizadorSintactico;
import java.io.IOException;
import java.io.Reader;
import AnalizadorLexico.*;
import GeneracionCodigo.*;
%}

%token ID CTE IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN DO UNTIL CONTINUE ASIG MENORIGUAL MAYORIGUAL DISTINTO COMENTARIO I16 F32 CONST CADENA

%start program

%%

program:	nombre_programa inicio_programa bloque fin_programa
;

inicio_programa:	'{' {Ambito.concatenarAmbito("main");}
;

fin_programa:	'}'
;

nombre_programa:	ID
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

lista_variables:	ID	{setTipo($1.sval); setUso($1.sval, "Variable");}
			| lista_variables ',' ID {setTipo($3.sval); setUso($3.sval, "Variable");}
;

tipo:		I16	{$$.sval = $1.sval; tipoAux = $1.sval;}
		| F32	{$$.sval = $1.sval; tipoAux = $1.sval;}
;

declaracion_funcion:	header_funcion cuerpo_funcion
;

header_funcion:		FUN ID '(' lista_parametros ')' ':' tipo
			| FUN ID '(' ')' ':' tipo
;

lista_parametros:	tipo ID ',' tipo ID
                	| tipo ID
;

cuerpo_funcion:		'{' bloque retorno_funcion '}' {Ambito.concatenarAmbito("func");}
;

retorno_funcion:	RETURN '(' expresion_aritmetica ')' ';'
;

expresion_aritmetica:		expresion_aritmetica '+' termino {$$.sval = TercetoManager.crear_terceto("+", $1.sval, $3.sval);}
		    		| expresion_aritmetica '-' termino {$$.sval = TercetoManager.crear_terceto("-", $1.sval, $3.sval);}
	            		| termino{$$.sval = $1.sval;}
;

termino:		termino '*' factor {$$.sval = TercetoManager.crear_terceto("*", $1.sval, $3.sval);}
       			| termino '/' factor {$$.sval = TercetoManager.crear_terceto("/", $1.sval, $3.sval);}
       			| factor {$$.sval = $1.sval;}
;

factor:		ID {$$.sval = $1.sval;}
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
;

list_constantes:	list_constantes ',' asignacion
			| asignacion
;

asignacion:		ID ASIG expresion_aritmetica {$$.sval = TercetoManager.crear_terceto("=:", $1.sval, $3.sval);}
;

sentencia_ejecutable:	ejecutable
			| con_etiqueta
;

ejecutable:		asignacion ';'
			| seleccion ';'
			| impresion ';'
			| estruct_do_until ';'
;

con_etiqueta:		BREAK ';'
			| BREAK CTE ';'
			| CONTINUE ';'
			| CONTINUE ':' ID ';'
			| ID ':' estruct_do_until ';'
			| ID ASIG sentencia_ctr_expr ';'
;

seleccion:		IF condicion cuerpo_if END_IF
;

condicion:		'(' expresion_aritmetica operador expresion_aritmetica ')' {$$.sval = TercetoManager.crear_terceto($3.sval, $2.sval, $4.sval);}
;

operador:		'<' {$$.sval = "<";}
			| MENORIGUAL {$$.sval = "<=";}
			| '>' {$$.sval = ">";}
			| MAYORIGUAL {$$.sval = ">=";}
			| '=' {$$.sval = "=";}
			| DISTINTO {$$.sval = "=!";}
;

cuerpo_if:		THEN inic_then lista_sentencias_ejecutables '}' cuerpo_else 
			| THEN '{' lista_sentencias_ejecutables '}' {Ambito.concatenarAmbito("then");}
;

inic_then: '{' {Ambito.concatenarAmbito("then");}

cuerpo_else:		ELSE '{' lista_sentencias_ejecutables '}' {Ambito.concatenarAmbito("else");}
;

lista_sentencias_ejecutables:	lista_sentencias_ejecutables sentencia_ejecutable
				| sentencia_ejecutable
;

impresion:		OUT '(' CADENA ')'
;

estruct_do_until:	DO '{' lista_sentencias_ejecutables '}' until_condicion {Ambito.concatenarAmbito("doUntil");}
;

until_condicion:	UNTIL condicion
;

sentencia_ctr_expr:	DO '{' lista_sentencia_ejecutables '}' until_condicion else_until {Ambito.concatenarAmbito("doUntilExpr");}
;

else_until:		ELSE CTE
;

%%

public String tipoAux = "";

void setTipo(String simbolo){
	TablaSimbolos.modificarTipo(simbolo, tipoAux);
}

void setUso(String simbolo, String uso){
	TablaSimbolos.modificarUso(simbolo, uso);
}

void yyerror(String mensaje) {
        // funcion utilizada para imprimir errores que produce yacc
        System.out.println("Error yacc: " + mensaje);
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