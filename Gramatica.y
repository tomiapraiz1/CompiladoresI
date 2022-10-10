%token ID CTE IF THEN ELSE END_IF OUT FUN RETURN BREAK WHEN DO UNTIL CONTINUE ASIG MENORIGUAL MAYORIGUAL DISTINTO COMENTARIO I16 F32 CONST
%left '+' '-'
%left '*' '/'
%start programa

%%
programa:		nombre_prog cuerpo_prog
			;

nombre_prog:		ID
	   		;

cuerpo_prog:		'{' bloque '}'
           		| '{' '}'
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
declaracion_const:		CONST list_const';'
		 		;
list_const:		list_const ',' ID ASIG CTE
          		| ID ASIG CTE
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
	   		;
lista_parametros_funcion:		tipo ID ',' tipo ID
                			| tipo ID
					;
cuerpo_func:		sentencia RETURN '(' expresion_aritmetica ')' ';'
	   		;
nombre_func:		ID
	   		;

ejecucion:		asignacion
         		| seleccion
	 		| impresion
	 		| estruct_do_until
 	 		| BREAK ';'
	 		| CONTINUE ';'
	 		| etiqueta CONTINUE ';'
	 		| etiqueta estruct_do_until
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
         		| IF '(' condicion ')' then_seleccion else_seleccion
	 		;

then_seleccion:		THEN ejecucion ';'
			;

else_seleccion:		ELSE ejecucion END_IF ';'
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

impresion:		OUT '(' ID ')' ';'
	 		;


estruct_do_until:		DO bloque_do_until UNTIL '(' condicion ')' ';'
				;
bloque_do_until:		bloque_do_until ejecucion
				| ejecucion
	       			;
etiqueta:		ID
			;

sentencia_ctr_expr:		DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE CTE ';'
		  		;
bloque_do_until_expr:		ejecucion BREAK CTE ';'
		    		;
%%