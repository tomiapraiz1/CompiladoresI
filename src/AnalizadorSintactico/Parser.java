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
//#line 22 "Parser.java"




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
public final static short lista_sentencia_ejecutables=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    4,    1,    3,    3,    5,    5,    7,    7,
    7,    8,   12,   12,   11,   11,    9,   13,   13,   15,
   15,   14,   16,   17,   17,   17,   18,   18,   18,   19,
   19,   19,   19,   20,   20,   20,   20,   10,   21,   21,
   22,    6,    6,   23,   23,   23,   23,   24,   24,   24,
   24,   24,   24,   25,   29,   31,   31,   31,   31,   31,
   31,   30,   30,   33,   32,   32,   26,   27,   34,   28,
   35,
};
final static short yylen[] = {                            2,
    4,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    3,    1,    3,    1,    1,    2,    7,    6,    5,
    2,    4,    5,    3,    3,    1,    3,    3,    1,    1,
    1,    2,    4,    3,    3,    1,    1,    3,    3,    1,
    3,    1,    1,    2,    2,    2,    2,    2,    3,    2,
    4,    4,    4,    4,    5,    1,    1,    1,    1,    1,
    1,    5,    4,    4,    2,    1,    4,    5,    2,    6,
    2,
};
final static short yydefred[] = {                         0,
    4,    0,    0,    2,    0,    0,    0,    0,    0,    0,
    0,    0,   15,   16,    0,    0,    6,    7,    8,    9,
   10,   11,    0,    0,    0,   42,   43,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   48,    0,   50,
    0,    0,    0,   40,    3,    1,    5,   13,    0,    0,
   17,   44,   45,   46,   47,    0,   31,    0,    0,    0,
    0,   29,    0,    0,    0,    0,    0,    0,    0,   49,
   66,    0,    0,    0,   38,    0,   12,    0,    0,    0,
    0,   32,    0,    0,    0,    0,   53,   52,   57,   59,
   61,   56,   58,   60,    0,    0,   54,   67,    0,    0,
    0,    0,   65,   51,   39,   14,    0,    0,   37,   36,
    0,    0,    0,    0,   27,   28,    0,    0,    0,    0,
    0,    0,   68,    0,   22,    0,   33,    0,   55,    0,
   19,    0,    0,   69,    0,   34,   35,    0,    0,   62,
    0,   18,    0,    0,   70,    0,   20,   23,   71,    0,
   64,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   46,   17,   18,   19,   20,   21,   22,
   23,   49,   24,   51,  101,  108,   60,   61,   62,  111,
   43,   25,   26,   27,   28,   29,   30,   63,   34,   67,
   95,   72,  140,  123,  145,
};
final static short yysindex[] = {                      -211,
    0,    0,  -73,    0, -144,  -24,   30,   32, -175,  -43,
  -39,   20,    0,    0, -167, -107,    0,    0,    0,    0,
    0,    0, -165,  -29,   37,    0,    0,   55,   59,   62,
  -28, -145,  -33, -157, -154,   89,   72,    0, -121,    0,
 -120, -131,    3,    0,    0,    0,    0,    0,    4, -144,
    0,    0,    0,    0,    0,  101,    0,   21,  -33,   38,
   18,    0,   84,   87,   -5,   25, -111,  112,  -41,    0,
    0,  -84,   95,  -33,    0, -167,    0, -102, -166, -169,
 -122,    0,  -33,  -33,  -33,  -33,    0,    0,    0,    0,
    0,    0,    0,    0,  -33, -121,    0,    0,  102,  -95,
  123, -104,    0,    0,    0,    0,  126,   42,    0,    0,
   36,   43,   18,   18,    0,    0,   23,  -83, -168,  128,
  119,   30,    0,  -33,    0, -141,    0, -104,    0,  -80,
    0, -168, -168,    0,   26,    0,    0,  -72,   65,    0,
  -79,    0,  132,  -65,    0, -121,    0,    0,    0,  -67,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -40,    0,    0,    0,   15,
  -35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -30,   -8,    0,    0,    0,    0,    0,  153,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  145,    0,   27,  -11,    0,    0,    0,    0,
  -46,    0,    0,    0,    0,    0,    6,   44,  -10,    0,
    0,   29,    0,    0,    0,    0,  165,    0,   78,    0,
    0,  -51,    0,   74,    0,
};
final static int YYTABLESIZE=269;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         99,
   30,   30,   30,   30,   30,   26,   30,   26,   26,   26,
   24,   59,   24,   24,   24,   38,   59,   45,   30,   30,
   30,   30,  100,   26,   26,   26,   26,   71,   24,   24,
   24,   24,   25,   32,   25,   25,   25,   83,   65,   84,
  102,  130,   47,   44,  118,    1,   76,   78,   82,    4,
   25,   25,   25,   25,   92,   94,   93,  151,   41,   85,
  103,   75,   77,  129,   86,   83,  143,   84,   83,   33,
   84,   35,  131,   41,  115,  116,  127,   41,   40,  126,
   83,   36,   84,   39,   71,  141,  142,  109,  110,   42,
    6,   48,    7,   50,  150,   52,    8,    9,  107,   10,
  117,   11,   66,   12,  105,   47,  103,   13,   14,   13,
   14,   15,    6,   53,    7,  136,  137,   54,    8,    9,
   55,   10,   11,   11,   68,   12,  113,  114,   69,  135,
   70,   13,   14,   15,   71,    6,   73,    7,  103,   74,
   80,    8,   87,   81,   10,   88,   11,   96,   12,    6,
   97,    7,   98,  104,  106,    8,    9,  112,   10,  119,
   11,  120,   12,  121,  122,  124,  125,  128,   13,   14,
   15,  132,    6,    6,    7,    7,  133,  147,    8,    8,
  139,   10,   10,   11,   11,   12,   12,  146,  144,    6,
  148,    7,  149,   21,   79,    8,   64,   63,   10,  134,
   11,  138,   12,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   37,    0,    0,    0,    0,    0,
    0,    0,    0,   56,   57,    0,    0,    0,   56,   57,
    0,   30,   30,   30,   13,   14,   26,   26,   26,   58,
    0,   24,   24,   24,    0,    0,   31,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   25,   25,   25,   89,   90,   91,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   41,   45,   43,   44,   45,   59,   45,  125,   59,   60,
   61,   62,   69,   59,   60,   61,   62,   39,   59,   60,
   61,   62,   41,   58,   43,   44,   45,   43,   33,   45,
  125,  125,   16,   15,   96,  257,   44,   44,   59,  123,
   59,   60,   61,   62,   60,   61,   62,  125,   44,   42,
   72,   59,   59,   41,   47,   43,   41,   45,   43,   40,
   45,   40,  119,   59,   85,   86,   41,   58,   59,   44,
   43,  257,   45,  123,   96,  132,  133,  257,  258,  257,
  257,  257,  259,  123,  146,   59,  263,  264,  265,  266,
   95,  268,  260,  270,   76,   79,  118,  276,  277,  276,
  277,  278,  257,   59,  259,  257,  258,   59,  263,  264,
   59,  266,  268,  268,  279,  270,   83,   84,   40,  124,
   59,  276,  277,  278,  146,  257,  257,  259,  150,  271,
   40,  263,   59,  123,  266,   59,  268,  123,  270,  257,
  262,  259,   41,   59,  257,  263,  264,  280,  266,   58,
  268,  257,  270,   41,  269,   40,  125,  125,  276,  277,
  278,   44,  257,  257,  259,  259,   58,  257,  263,  263,
  261,  266,  266,  268,  268,  270,  270,  123,  261,  257,
   59,  259,  258,   41,   50,  263,   32,  262,  266,  122,
  268,  128,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  258,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,   -1,   -1,   -1,  257,  258,
   -1,  272,  273,  274,  276,  277,  272,  273,  274,  268,
   -1,  272,  273,  274,   -1,   -1,  271,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,  274,  272,  273,  274,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=280;
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
"lista_sentencia_ejecutables",
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
"cuerpo_funcion : '{' bloque retorno_funcion '}'",
"retorno_funcion : RETURN '(' expresion_aritmetica ')' ';'",
"expresion_aritmetica : expresion_aritmetica '+' termino",
"expresion_aritmetica : expresion_aritmetica '-' termino",
"expresion_aritmetica : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : '-' factor",
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
"cuerpo_if : THEN '{' lista_sentencias_ejecutables '}' cuerpo_else",
"cuerpo_if : THEN '{' lista_sentencias_ejecutables '}'",
"cuerpo_else : ELSE '{' lista_sentencias_ejecutables '}'",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"impresion : OUT '(' CADENA ')'",
"estruct_do_until : DO '{' lista_sentencias_ejecutables '}' until_condicion",
"until_condicion : UNTIL condicion",
"sentencia_ctr_expr : DO '{' lista_sentencia_ejecutables '}' until_condicion else_until",
"else_until : ELSE CTE",
};

//#line 158 "gramatica.y"

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
//#line 403 "Parser.java"
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
