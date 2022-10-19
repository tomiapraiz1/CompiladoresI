%token ID CTE IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN DO UNTIL CONTINUE ASIG MENORIGUAL MAYORIGUAL DISTINTO COMENTARIO I16 F32 CONST CADENA
%start programa

%%
programa:		nombre_prog cuerpo_prog
			;

nombre_prog:		ID
			| '' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": se esperaba un nombre");}
	   		;

cuerpo_prog:		'{' bloque '}'
           		| '{' '}'
			| '{' bloque {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta un }");}
			| bloque '}' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta un {");}
	   		;

bloque:			bloque sentencia
      			| sentencia
      			;

sentencia:		ejecucion
	 		| declaracion
         		;

declaracion:		declaracion_var
           		| declaracion_func
	   		| declaracion_const
	   		;
declaracion_const:		CONST list_const
		 		;
list_const:		list_const ',' asignacion
          		| asignacion
	  		;

declaracion_var:		tipo lista_de_variables ';'
	       			;
lista_de_variables:		lista_de_variables ',' variable
		  		| variable
		  		;
variable:		ID
			;
tipo: 		I16
    		| F32
    		;

declaracion_func:		header_func '{' cuerpo_func '}'
				;
header_func:		FUN nombre_func '(' lista_parametros_funcion ')' ':' tipo
			| FUN nombre_func '(' ')' ':' tipo
			| FUN nombre_func '(' ')' ':' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo de retorno");}
			| FUN nombre_func '(' lista_parametros_funcion ')' ':' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo de retorno");}
			| FUN '(' ')' ':' tipo {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre de la funcion");}
			| FUN '(' lista_parametros_funcion ')' ':' tipo {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre de la funcion");}
	   		;
lista_parametros_funcion:		tipo ID ',' tipo ID
                			| tipo ID
					| ID {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo del parametro");}
					| ID ',' ID {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo del parametro");}
					| tipo {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre del parametro");}
					| tipo ',' tipo {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre del parametro");}
					;
cuerpo_func:		sentencia RETURN '(' expresion_aritmetica ')' ';'
			| RETURN '(' expresion_aritmetica ')' ';'
			| sentencia '(' expresion_aritmetica ')' ';' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
			| sentencia RETURN '(' ')' ';' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
			| RETURN '(' ')' ';' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
	   		;
nombre_func:		ID
	   		;

ejecucion:		asignacion
         		| seleccion
	 		| impresion
	 		| estruct_do_until
 	 		| BREAK ';'
	 		| CONTINUE ';'
	 		| CONTINUE ':' etiqueta ';'
	 		| etiqueta ':' estruct_do_until 
			| ID ASIG sentencia_ctr_expr
	 		;

asignacion:		ID ASIG expresion_aritmetica ';'
	  		;
expresion_aritmetica:		expresion_aritmetica '+' termino
		    		| expresion_aritmetica '-' termino
	            		| termino
		    		;
termino:		termino '*' factor
       			| termino '/' factor
       			| factor
       			;
factor:		ID
      		| CTE
      		| '-' factor
      		| ID '(' lista_inv_func ')'
      		;
lista_inv_func:		lista_inv_func ',' ID
	      		| lista_inv_func ',' CTE
	      		| CTE
	      		| ID
	      		;
seleccion:		IF '(' condicion ')' then_seleccion
	 		;

then_seleccion:		THEN ejecucion ';'
			| THEN ejecucion ELSE ejecucion END_IF ';'
			;

condicion:		expresion_aritmetica operador expresion_aritmetica
	 		;
operador:		'<'
			| MENORIGUAL
			| '>'
			| MAYORIGUAL
			| '='
			| DISTINTO
			;

impresion:		OUT '(' CADENA ')' ';'
			| '(' CADENA ')' ';' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la palabra reservada OUT");}
	 		;


estruct_do_until:		DO bloque_do_until UNTIL '(' condicion ')' ';'
				;
bloque_do_until:		bloque_do_until ejecucion
				| ejecucion
	       			;
etiqueta:		ID
			;

sentencia_ctr_expr:		DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE CTE ';'
				| DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE ';' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la constante si no se cumple");}
		  		;
bloque_do_until_expr:		ejecucion BREAK CTE ';'
				| ejecucion BREAK ';' {System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la constante si no se cumple");}
		    		;
%%