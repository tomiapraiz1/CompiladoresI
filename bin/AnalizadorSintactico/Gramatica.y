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
			| estruct_when
;

declaracion_variables:	tipo lista_variables ';' 
;

lista_variables:	ID	{setTipo($1.sval); setUso($1.sval, "variable"); $1.sval = TablaSimbolos.modificarNombre($1.sval);}
			| lista_variables ',' ID {setTipo($3.sval); setUso($3.sval, "variable"); $3.sval = TablaSimbolos.modificarNombre($3.sval);}
;

tipo:		I16	{$$.sval = $1.sval; tipoAux = $1.sval;}
		| F32	{$$.sval = $1.sval; tipoAux = $1.sval;}
;

declaracion_funcion:	header_funcion complete_header_funcion cuerpo_funcion {setTipo(funcionAux, $1.sval); Ambito.removeAmbito();}
;

header_funcion:		FUN ID '(' {ambitoAux = $2.sval; setTipo($2.sval); setUso($2.sval, "funcion"); $2.sval = TablaSimbolos.modificarNombre($2.sval); idAux = $2.sval; $$.sval = $2.sval; Ambito.concatenarAmbito(ambitoAux);}
					| FUN '(' {erroresSintacticos.add("Se esperaba un identificador de la funcion");}
					| FUN ID {erroresSintacticos.add("Falta un (");}
					| FUN CTE '(' {erroresSintacticos.add("No se puede declarar una funcion con una constante como nombre");}
;

complete_header_funcion:	lista_parametros ')' ':' tipo {funcionAux = $4.sval;}
							| ')' ':' tipo	{TablaSimbolos.modificarParametros(idAux, 0); funcionAux = $3.sval;}
							|  ':' tipo	{erroresSintacticos.add("Falta un )");}
							| ')' tipo	{erroresSintacticos.add("Falta un :");}
							| ')' ':' 	{erroresSintacticos.add("Se esperaba un tipo de retorno");}
;

lista_parametros:	tipo ID ',' tipo ID {TablaSimbolos.modificarParametros(idAux, 2); setTipo($1.sval,$2.sval);setUso($2.sval, "ParametroFuncion"); setTipo($4.sval,$5.sval); setUso($5.sval, "Nombre_Parametro_Funcion"); $2.sval = TablaSimbolos.modificarNombre($2.sval); $5.sval = TablaSimbolos.modificarNombre($5.sval);}
                	| tipo ID {TablaSimbolos.modificarParametros(idAux, 1); setTipo($1.sval,$2.sval);setUso($2.sval, "ParametroFuncion"); $2.sval = TablaSimbolos.modificarNombre($2.sval);}
                	| ID		{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
                	| ID ',' ID {erroresSintacticos.add("Los identificadores deben tener un tipo");}
;

cuerpo_funcion:		inicio_funcion bloque retorno_funcion fin_funcion
					| inicio_funcion bloque fin_funcion {erroresSintacticos.add("La funcion debe retornar un valor");}
;

inicio_funcion: '{' 
;

fin_funcion: '}' 
;

retorno_funcion:	RETURN '(' expresion_aritmetica ')' ';' {}
					| RETURN expresion_aritmetica ')' ';' {erroresSintacticos.add("Falta un (");}
					| RETURN '(' expresion_aritmetica ';' {erroresSintacticos.add("Falta un )");}
					| RETURN '(' expresion_aritmetica ')' {erroresSintacticos.add("Falta un ;");}
					| RETURN '('  ')' ';' {erroresSintacticos.add("Falta un valor que devolver");}
;

expresion_aritmetica:		expresion_aritmetica '+' termino {verificarTipos($1.sval,$3.sval,"+"); $$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", $1.sval, $3.sval);}
		    		| expresion_aritmetica '-' termino {verificarTipos($1.sval,$3.sval, "-"); $$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", $1.sval, $3.sval);}
	            		| termino
;

termino:		termino '*' factor {verificarTipos($1.sval,$3.sval, "*"); $$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", $1.sval, $3.sval);}
       			| termino '/' factor {verificarTipos($1.sval,$3.sval, "/"); $$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", $1.sval, $3.sval);}
       			| factor 
;

factor:		ID {comprobarAmbito($1.sval); $1.sval = Ambito.getAmbito($1.sval); $$.sval = $1.sval;}
      		| CTE 
      		| '-' CTE {$$.sval = "-" + $2.sval;}
      		| ID '(' lista_inv_func ')' {TablaSimbolos.eliminarSimbolo($1.sval); comprobarAmbito($1.sval); $2.sval = $1.sval; $1.sval = Ambito.getAmbito($1.sval); if ($1.sval == null) chequearParametros($2.sval, $3.ival); else chequearParametros($1.sval, $3.ival); $$.sval = $1.sval;}
			| ID '('')'	{TablaSimbolos.eliminarSimbolo($1.sval); comprobarAmbito($1.sval); $2.sval = $1.sval; $1.sval = Ambito.getAmbito($1.sval); if ($1.sval == null) chequearParametros($2.sval, 0); else chequearParametros($1.sval, 0); $$.sval = $1.sval;}
;

lista_inv_func:		ID ',' ID {$$.ival = 2;}
	      		| ID ',' CTE {$$.ival = 2;}
	      		| CTE ',' CTE {$$.ival = 2;}
	      		| CTE ',' ID {$$.ival = 2;}
	      		| CTE {$$.ival = 1;}
	      		| ID {$$.ival = 1;}
;

declaracion_constantes:	CONST list_constantes ';'
						| CONST list_constantes {erroresSintacticos.add("Falta un ;");}
;

list_constantes:	list_constantes ',' asignacion_constante 
			| asignacion_constante
;

asignacion_constante:		ID ASIG CTE {Atributo aux = TablaSimbolos.obtenerSimbolo($3.sval); setTipo(aux.getTipo(),$1.sval); $1.sval = TablaSimbolos.modificarNombre($1.sval);  TercetoManager.crear_terceto("=:", $1.sval, $3.sval);}
							| ID CTE {erroresSintacticos.add("Falta =:");}
;

asignacion:		ID ASIG expresion_aritmetica {if ($3.sval == null) break; comprobarAmbito($1.sval); $1.sval = Ambito.getAmbito($1.sval); verificarTipos($1.sval,$3.sval, "=:"); $$.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", $1.sval, $3.sval);}
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

con_etiqueta:		BREAK ';' {TercetoManager.breakDoUntil();}
			| BREAK {erroresSintacticos.add("Falta un ;");}
			| BREAK CTE ';'
			| BREAK CTE {erroresSintacticos.add("Falta un ;");}
			| CONTINUE ';' {TercetoManager.continueDoUntil();}
			| CONTINUE {erroresSintacticos.add("Falta un ;");}
			| CONTINUE ':' ID ';'
			| CONTINUE ':' ID {erroresSintacticos.add("Falta un ;");}
			| CONTINUE ':' ';' {erroresSintacticos.add("Falta un identificador");}
			| ID ':' estruct_do_until ';' {TablaSimbolos.agregarSimbolo($1.sval, 257, "", AnalizadorLexico.getLine()); setUso($1.sval,"etiqueta"); $1.sval = TablaSimbolos.modificarNombre($1.sval);}
			| ID ':' estruct_do_until {erroresSintacticos.add("Falta un ;");}
			| ID ':'  ';'{erroresSintacticos.add("Falta un cuerpo do until");}
			| ID ASIG sentencia_ctr_expr ';'
			| ID ASIG sentencia_ctr_expr {erroresSintacticos.add("Falta un ;");}
			| ID sentencia_ctr_expr ';' {erroresSintacticos.add("Falta un cuerpo do until");}
			| ID ASIG ';' {erroresSintacticos.add("Falta un =:");}
;

seleccion:		inicio_if condicion_if cuerpo_if END_IF {TercetoManager.add_seleccion();}
;

inicio_if: IF
;

condicion_if: condicion {TercetoManager.add_seleccion_cond();}
;

condicion:		'(' expresion_aritmetica operador expresion_aritmetica ')' {verificarTipos($2.sval, $4.sval, $3.sval); TercetoManager.crear_terceto($3.sval, $2.sval, $4.sval);}
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

estruct_when:	 inicio_when condicion_when THEN '{' bloque '}' {TercetoManager.add_iter_when();}
;

inicio_when: WHEN {TercetoManager.add_inicio_when();}
;

condicion_when: condicion {TercetoManager.add_cond_when();}
;

impresion:		OUT '(' CADENA ')' {TercetoManager.crear_terceto("out", $3.sval, "_");}
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
public String ambitoAux = "";
public String funcionAux = "";
public String idAux = "";
public static ArrayList<String> erroresSintacticos = new ArrayList<String>();
public static ArrayList<String> erroresLexicos = new ArrayList<String>();
public static ArrayList<String> erroresSemanticos = new ArrayList<String>();

public void verificarTipos(String arg1,String arg2, String operador){
	if (arg1 != null && arg2 != null){	
		String aux1 = arg1;
		while (aux1.startsWith("[")){
			aux1=TercetoManager.getTerceto(aux1).getOperador1();
		}
		String aux2=arg2;
		while (aux2.startsWith("[")){
			aux2=TercetoManager.getTerceto(aux2).getOperador1();
		}
		if (TablaSimbolos.obtenerSimbolo(aux1).getTipo().equals(TablaSimbolos.obtenerSimbolo(aux2).getTipo()))
			return;
		else
			TablaTipos.setTipoAbarcativo(aux1, aux2, operador);
	}
}

void chequearParametros(String simbolo, int cantidad){
	if (TablaSimbolos.contieneSimbolo(simbolo)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(simbolo);
		if (aux.getUso().equals("funcion")){
			if (aux.getCantidadParametros() != cantidad)
				erroresSemanticos.add("No coinciden la cantidad de parametros de '" + Ambito.sinAmbito(simbolo) + "'");
		} else
			erroresSemanticos.add("'" + simbolo + "' no es una funcion");
	} else
		erroresSemanticos.add("La funcion '" + simbolo + "' no esta declarada");
}

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
		erroresSemanticos.add("'" + simbolo + "' no esta al alcance");
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
	if (!erroresSemanticos.isEmpty()) {
		System.out.println(erroresSemanticos);
	} else System.out.println("No hay errores semanticos.");
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