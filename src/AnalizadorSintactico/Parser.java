//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package AnalizadorSintactico;
import java.io.IOException;
import java.io.Reader;
import AnalizadorLexico.*;
import GeneracionCodigo.*;
//#line 23 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short IF=259;
public final static short THEN=260;
public final static short ELSE=261;
public final static short END_IF=262;
public final static short OUT=263;
public final static short FUN=264;
public final static short RETURN=265;
public final static short BREAK=266;
public final static short WHEN=267;
public final static short DO=268;
public final static short UNTIL=269;
public final static short CONTINUE=270;
public final static short ASIG=271;
public final static short MENORIGUAL=272;
public final static short MAYORIGUAL=273;
public final static short DISTINTO=274;
public final static short COMENTARIO=275;
public final static short I16=276;
public final static short F32=277;
public final static short CONST=278;
public final static short CADENA=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    4,    1,    3,    3,    5,    5,    7,    7,
    7,    8,   12,   12,   11,   11,    9,   13,   13,   15,
   15,   14,   16,   18,   17,   19,   19,   19,   20,   20,
   20,   21,   21,   21,   21,   22,   22,   22,   22,   10,
   23,   23,   24,    6,    6,   25,   25,   25,   25,   26,
   26,   26,   26,   26,   26,   27,   31,   33,   33,   33,
   33,   33,   33,   32,   32,   34,   35,   36,   36,   28,
   29,   37,   38,   39,   30,   40,   41,   42,
};
final static short yylen[] = {                            2,
    4,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    3,    1,    3,    1,    1,    2,    7,    6,    5,
    2,    4,    1,    1,    5,    3,    3,    1,    3,    3,
    1,    1,    1,    2,    4,    3,    3,    1,    1,    3,
    3,    1,    3,    1,    1,    2,    2,    2,    2,    2,
    3,    2,    4,    4,    4,    4,    5,    1,    1,    1,
    1,    1,    1,    4,    2,    3,    3,    2,    1,    4,
    5,    1,    1,    2,    6,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    4,    0,    0,    2,    0,    0,    0,    0,    0,    0,
    0,    0,   15,   16,    0,    0,    6,    7,    8,    9,
   10,   11,    0,    0,    0,   44,   45,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   50,   72,    0,
   52,    0,    0,    0,   42,    3,    1,    5,   13,    0,
   23,   17,    0,   46,   47,   48,   49,    0,   33,    0,
    0,    0,    0,   31,    0,    0,    0,    0,    0,    0,
    0,   51,   69,    0,    0,    0,   40,    0,   12,    0,
    0,    0,   76,    0,   34,    0,    0,    0,    0,   55,
   54,   59,   61,   63,   58,   60,   62,    0,    0,    0,
   56,   70,    0,    0,    0,   73,   68,    0,   53,   41,
   14,    0,    0,   39,   38,    0,    0,    0,    0,   29,
   30,    0,    0,    0,    0,    0,    0,    0,   71,    0,
   24,   22,    0,   35,   77,    0,   57,   66,    0,   64,
   19,    0,    0,   74,    0,   36,   37,    0,    0,    0,
   18,    0,    0,   75,   67,   20,   25,   78,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   47,   17,   18,   19,   20,   21,   22,
   23,   50,   24,   52,  105,   53,  113,  132,   62,   63,
   64,  116,   44,   25,   26,   27,   28,   29,   30,   65,
   34,   69,   98,  100,  140,   74,   40,  108,  129,   84,
  136,  154,
};
final static short yysindex[] = {                      -171,
    0,    0,  -34,    0, -145,  -24,   57,   59, -154,  -43,
  -14,   17,    0,    0, -147, -107,    0,    0,    0,    0,
    0,    0, -144,   -6,   63,    0,    0,   65,   67,   69,
  -28, -133,  -33, -122, -140,  101,   83,    0,    0, -123,
    0, -113, -125,    2,    0,    0,    0,    0,    0,    4,
    0,    0, -145,    0,    0,    0,    0,  108,    0,   26,
 -105,   22,   27,    0,   92,   95,   -5,   32, -104,  119,
  -41,    0,    0,  -84,  103,  -33,    0, -147,    0,  -93,
 -186, -164,    0, -123,    0,  -33,  -33,  -33,  -33,    0,
    0,    0,    0,    0,    0,    0,    0,  -33, -123,  -96,
    0,    0,  109,  -91,  127,    0,    0,  -97,    0,    0,
    0,  137,   53,    0,    0,    6,  -83,   27,   27,    0,
    0,   25,  -67,   58, -181,  144,  131,   57,    0,  -33,
    0,    0, -156,    0,    0,  -97,    0,    0, -123,    0,
    0, -181, -181,    0,   40,    0,    0,  -63,  -66,  -62,
    0,  135,  -53,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -40,    0,    0,
    0,    5,  -35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -30,   -8,    0,
    0,    0,    0,    0,    0,  166,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  155,    0,   23,  -12,    0,    0,    0,    0,
  -27,    0,    0,    0,    0,    0,    0,    0,  -10,   21,
   41,    0,    0,   28,    0,    0,    0,    0,  177,    0,
   82,    0,    0,    0,    0,  -39,    0,    0,   75,    0,
    0,    0,
};
final static int YYTABLESIZE=269;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        103,
   32,   32,   32,   32,   32,   28,   32,   28,   28,   28,
   26,   61,   26,   26,   26,   38,   61,   46,   32,   32,
   32,   32,   67,   28,   28,   28,   28,   73,   26,   26,
   26,   26,   27,   32,   27,   27,   27,   86,   48,   87,
  106,  135,   45,  104,  117,   78,  134,   80,   43,  133,
   27,   27,   27,   27,   95,   97,   96,  138,  155,  123,
   77,  107,   79,   43,   86,  137,   87,   86,   88,   87,
    6,   73,    7,   89,   42,   41,    8,    9,  112,   10,
  152,   11,   86,   12,   87,    1,   73,  122,    4,   13,
   14,   15,  114,  115,   13,   14,   33,  141,   35,  149,
  146,  147,   36,   48,  107,  110,  118,  119,   39,   43,
  107,    6,   49,    7,  150,  151,   51,    8,    9,  145,
   10,   54,   11,   55,   12,   56,   73,   57,  120,  121,
   13,   14,   15,    6,   11,    7,  107,   68,   70,    8,
   71,   72,   10,   75,   11,   76,   12,   82,   83,    6,
   90,    7,   85,   91,   99,    8,    9,  101,   10,  102,
   11,  109,   12,  111,  124,  126,  125,  127,   13,   14,
   15,  128,    6,    6,    7,    7,  130,  131,    8,    8,
  139,   10,   10,   11,   11,   12,   12,  142,  143,    6,
    6,    7,    7,  157,  156,    8,    8,  153,   10,   10,
   11,   11,   12,   12,  158,   65,   21,   81,   66,  144,
  148,    0,    0,    0,   37,    0,    0,    0,    0,    0,
    0,    0,    0,   58,   59,    0,    0,    0,   58,   59,
    0,   32,   32,   32,   13,   14,   28,   28,   28,   60,
    0,   26,   26,   26,    0,    0,   31,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   27,   27,   27,   92,   93,   94,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   41,   45,   43,   44,   45,   59,   45,  125,   59,   60,
   61,   62,   33,   59,   60,   61,   62,   40,   59,   60,
   61,   62,   41,   58,   43,   44,   45,   43,   16,   45,
  125,  125,   15,   71,   84,   44,   41,   44,   44,   44,
   59,   60,   61,   62,   60,   61,   62,  125,  125,   99,
   59,   74,   59,   59,   43,   41,   45,   43,   42,   45,
  257,   84,  259,   47,   58,   59,  263,  264,  265,  266,
   41,  268,   43,  270,   45,  257,   99,   98,  123,  276,
  277,  278,  257,  258,  276,  277,   40,  125,   40,  139,
  257,  258,  257,   81,  117,   78,   86,   87,  123,  257,
  123,  257,  257,  259,  142,  143,  123,  263,  264,  130,
  266,   59,  268,   59,  270,   59,  139,   59,   88,   89,
  276,  277,  278,  257,  268,  259,  149,  260,  279,  263,
   40,   59,  266,  257,  268,  271,  270,   40,  123,  257,
   59,  259,  258,   59,  123,  263,  264,  262,  266,   41,
  268,   59,  270,  257,  261,  257,   58,   41,  276,  277,
  278,  269,  257,  257,  259,  259,   40,  125,  263,  263,
  123,  266,  266,  268,  268,  270,  270,   44,   58,  257,
  257,  259,  259,   59,  257,  263,  263,  261,  266,  266,
  268,  268,  270,  270,  258,  262,   41,   53,   32,  128,
  136,   -1,   -1,   -1,  258,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,   -1,   -1,   -1,  257,  258,
   -1,  272,  273,  274,  276,  277,  272,  273,  274,  268,
   -1,  272,  273,  274,   -1,   -1,  271,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,  274,  272,  273,  274,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","IF","THEN","ELSE","END_IF","OUT",
"FUN","RETURN","BREAK","WHEN","DO","UNTIL","CONTINUE","ASIG","MENORIGUAL",
"MAYORIGUAL","DISTINTO","COMENTARIO","I16","F32","CONST","CADENA",
};
final static String yyrule[] = {
"$accept : program",
"program : nombre_programa inicio_programa bloque fin_programa",
"inicio_programa : '{'",
"fin_programa : '}'",
"nombre_programa : ID",
"bloque : bloque sentencia",
"bloque : sentencia",
"sentencia : sentencia_ejecutable",
"sentencia : sentencia_declarativa",
"sentencia_declarativa : declaracion_variables",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_constantes",
"declaracion_variables : tipo lista_variables ';'",
"lista_variables : ID",
"lista_variables : lista_variables ',' ID",
"tipo : I16",
"tipo : F32",
"declaracion_funcion : header_funcion cuerpo_funcion",
"header_funcion : FUN ID '(' lista_parametros ')' ':' tipo",
"header_funcion : FUN ID '(' ')' ':' tipo",
"lista_parametros : tipo ID ',' tipo ID",
"lista_parametros : tipo ID",
"cuerpo_funcion : inicio_funcion bloque retorno_funcion fin_funcion",
"inicio_funcion : '{'",
"fin_funcion : '}'",
"retorno_funcion : RETURN '(' expresion_aritmetica ')' ';'",
"expresion_aritmetica : expresion_aritmetica '+' termino",
"expresion_aritmetica : expresion_aritmetica '-' termino",
"expresion_aritmetica : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : '-' CTE",
"factor : ID '(' lista_inv_func ')'",
"lista_inv_func : lista_inv_func ',' ID",
"lista_inv_func : lista_inv_func ',' CTE",
"lista_inv_func : CTE",
"lista_inv_func : ID",
"declaracion_constantes : CONST list_constantes ';'",
"list_constantes : list_constantes ',' asignacion",
"list_constantes : asignacion",
"asignacion : ID ASIG expresion_aritmetica",
"sentencia_ejecutable : ejecutable",
"sentencia_ejecutable : con_etiqueta",
"ejecutable : asignacion ';'",
"ejecutable : seleccion ';'",
"ejecutable : impresion ';'",
"ejecutable : estruct_do_until ';'",
"con_etiqueta : BREAK ';'",
"con_etiqueta : BREAK CTE ';'",
"con_etiqueta : CONTINUE ';'",
"con_etiqueta : CONTINUE ':' ID ';'",
"con_etiqueta : ID ':' estruct_do_until ';'",
"con_etiqueta : ID ASIG sentencia_ctr_expr ';'",
"seleccion : IF condicion cuerpo_if END_IF",
"condicion : '(' expresion_aritmetica operador expresion_aritmetica ')'",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : '='",
"operador : DISTINTO",
"cuerpo_if : THEN cuerpo_then ELSE cuerpo_else",
"cuerpo_if : THEN cuerpo_then",
"cuerpo_then : '{' lista_sentencias_ejecutables '}'",
"cuerpo_else : '{' lista_sentencias_ejecutables '}'",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"impresion : OUT '(' CADENA ')'",
"estruct_do_until : DO inicion_estruct_do_until lista_sentencias_ejecutables fin_estruct_do_until until_condicion",
"inicion_estruct_do_until : '{'",
"fin_estruct_do_until : '}'",
"until_condicion : UNTIL condicion",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion else_until",
"inicio_sentencia_ctr_expr : '{'",
"fin_sentencia_ctr_expr : '}'",
"else_until : ELSE CTE",
};

//#line 180 "gramatica.y"

public String tipoAux = "";

void setTipo(String simbolo){
	TablaSimbolos.modificarTipo(simbolo, tipoAux);
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
//#line 427 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 18 "gramatica.y"
{Ambito.concatenarAmbito("main");}
break;
case 3:
//#line 21 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 13:
//#line 43 "gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "Variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 14:
//#line 44 "gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "Variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 15:
//#line 47 "gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 16:
//#line 48 "gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 18:
//#line 54 "gramatica.y"
{setTipo(val_peek(5).sval); setUso(val_peek(5).sval, "Funcion"); val_peek(5).sval = TablaSimbolos.modificarNombre(val_peek(5).sval);}
break;
case 19:
//#line 55 "gramatica.y"
{setTipo(val_peek(4).sval); setUso(val_peek(4).sval, "Funcion"); val_peek(4).sval = TablaSimbolos.modificarNombre(val_peek(4).sval);}
break;
case 23:
//#line 65 "gramatica.y"
{Ambito.concatenarAmbito("func");}
break;
case 24:
//#line 68 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 26:
//#line 74 "gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 27:
//#line 75 "gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 28:
//#line 76 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 79 "gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 30:
//#line 80 "gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 31:
//#line 81 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 32:
//#line 84 "gramatica.y"
{comprobarAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 85 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 34:
//#line 86 "gramatica.y"
{yyval.sval = "-" + val_peek(1).sval;}
break;
case 43:
//#line 103 "gramatica.y"
{comprobarAmbito(val_peek(2).sval); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 56:
//#line 124 "gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 57:
//#line 127 "gramatica.y"
{TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval); TercetoManager.add_seleccion_cond();}
break;
case 58:
//#line 130 "gramatica.y"
{yyval.sval = "<";}
break;
case 59:
//#line 131 "gramatica.y"
{yyval.sval = "<=";}
break;
case 60:
//#line 132 "gramatica.y"
{yyval.sval = ">";}
break;
case 61:
//#line 133 "gramatica.y"
{yyval.sval = ">=";}
break;
case 62:
//#line 134 "gramatica.y"
{yyval.sval = "=";}
break;
case 63:
//#line 135 "gramatica.y"
{yyval.sval = "=!";}
break;
case 66:
//#line 142 "gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 72:
//#line 158 "gramatica.y"
{Ambito.concatenarAmbito("doUntil");}
break;
case 73:
//#line 161 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 76:
//#line 170 "gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 77:
//#line 173 "gramatica.y"
{Ambito.removeAmbito();}
break;
//#line 708 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
