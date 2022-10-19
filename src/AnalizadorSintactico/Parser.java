package AnalizadorSintactico;

import AnalizadorLexico.AnalizadorLexico;

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
public static ParserVal yylval;//the 'lval' (result) I got from yylex()
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
    0,    1,    1,    2,    2,    2,    2,    3,    3,    4,
    4,    6,    6,    6,    8,   10,   10,   10,   10,   10,
   10,   13,   13,   13,   13,   13,   13,   11,   11,   11,
   11,   11,   12,    5,    5,    5,    5,    5,    5,    5,
    5,    5,   16,   15,   15,   15,   22,   22,   22,   23,
   23,   23,   23,   24,   24,   24,   24,   17,   25,   26,
   26,   27,   28,   28,   28,   28,   28,   28,   19,   29,
   29,   20,   21,   21,   30,   30,   18,   18,    9,   31,
   31,    7,   32,   32,   33,   14,   14,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    2,    2,    2,    1,    1,
    1,    1,    1,    1,    4,    7,    6,    5,    6,    5,
    6,    5,    2,    1,    3,    1,    3,    6,    5,    5,
    5,    4,    1,    1,    1,    1,    1,    2,    2,    4,
    3,    3,    4,    3,    3,    1,    3,    3,    1,    1,
    1,    2,    4,    3,    3,    1,    1,    3,    3,    3,
    6,    3,    1,    1,    1,    1,    1,    1,    7,    2,
    1,    1,    9,    8,    4,    3,    5,    4,    3,    3,
    1,    3,    3,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   86,   87,    0,    0,    0,    1,    0,    9,   10,
   11,   12,   13,   14,    0,    0,   34,   35,   36,   37,
    0,    0,    0,    0,    0,   33,    0,    0,   38,   71,
    0,    0,   39,    0,   81,    0,    5,    0,    0,    7,
    8,    0,   85,    0,   84,    0,    0,   51,    0,    0,
    0,   42,    0,   49,    0,    0,    0,   58,    0,    0,
    0,    0,    0,    0,    0,   70,   72,    0,    0,    0,
   79,    4,    0,    0,    0,    0,    0,   82,   41,    0,
    0,    0,   52,   43,    0,    0,    0,    0,   64,   66,
   68,   63,   65,   67,    0,   59,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,   80,   78,    0,
    0,    0,   15,   83,   57,   56,    0,    0,    0,    0,
    0,   47,   48,    0,    0,   60,   77,   25,   20,    0,
    0,   27,    0,    0,    0,    0,    0,    0,    0,   53,
    0,    0,   76,    0,    0,   21,    0,   17,    0,    0,
   32,    0,    0,    0,    0,   54,   55,   75,    0,    0,
   22,   16,   69,   29,   31,    0,   30,    0,   61,   28,
    0,    0,   74,   73,
};
final static short yydgoto[] = {                          3,
    4,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   86,   38,   72,   26,   65,   27,   28,   29,   30,   31,
   62,   63,   64,  127,   34,   68,   66,  105,   41,   92,
   46,   54,   55,
};
final static short yysindex[] = {                      -244,
    0,    0,    0,  -40, -254,   -7,   27,  -39,    3,  115,
  -37,    0,    0, -191,  -24, -209,    0,   -8,    0,    0,
    0,    0,    0,    0,  -51, -184,    0,    0,    0,    0,
   17,  -43,    6, -179, -197,    0,   36,   45,    0,    0,
  107, -173,    0, -185,    0,  -21,    0,    8,   46,    0,
    0,   24,    0,  -20,    0, -177,   58,    0,  115,    6,
  -25,    0,  -17,    0,   84,   62,  115,    0,   64,   55,
   57,   69,  -41,   38,   76,    0,    0,   66,    6, -191,
    0,    0,   71,   88,   40,    9, -184,    0,    0, -230,
 -135, -132,    0,    0,    6,    6,    6,    6,    0,    0,
    0,    0,    0,    0,    6,    0,  -55,   79, -118, -236,
   90,  106, -236,   93,  111,    6,    0,    0,    0,  -26,
  113,  -36,    0,    0,    0,    0,  -15,  -54,  114,  -17,
  -17,    0,    0,   26,  115,    0,    0,    0,    0, -236,
 -236,    0, -236,   98,  116,   99,   12,  -10,   13,    0,
 -214,  100,    0,    6, -101,    0,  -97,    0, -236,  108,
    0,  109,  110,   20,  112,    0,    0,    0,  129,  117,
    0,    0,    0,    0,    0,  118,    0,  -89,    0,    0,
  -53,  119,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  121,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  173,    0,    0,
    0,    0,    0,    0,    0,    0,   47,    0,    0,    0,
    0,    0,   52,    0,    0,    0,    0,    0,    0,  133,
    0,    0,  134,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  139,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   59,
   81,    0,    0,  141,    0,    0,    0,    0,    0,    0,
    0,    0,   60,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -5,  -11,    1,    0,    0,    0,    0,    0,
    0,    0,  120,   22,   44,   -2,    0,    0,  130,  143,
    0,  -46,  -52,    0,    0,    0, -102,    0,    0,    0,
    0,    0,  101,
};
final static int YYTABLESIZE=385;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   37,   60,  113,  136,  153,  183,   51,   93,   60,   48,
   40,   45,    1,  145,  146,   16,   32,   95,   60,   96,
   42,   43,   80,   87,   97,  150,  125,  126,  151,   98,
  163,   16,   33,   94,   60,    2,   51,   81,   88,   12,
   13,   76,  166,  167,  132,  133,   85,   16,  130,  131,
   60,  169,  162,  165,   95,   95,   96,   96,   73,   91,
  176,   39,   95,   16,   96,   44,   35,  107,   95,   49,
   96,   52,   53,   51,   56,   61,   71,  118,  114,  122,
   67,   69,   15,   77,   74,   79,   83,   50,   50,   50,
   10,   50,   46,   50,   46,   73,   46,   90,  109,   44,
   47,   44,  106,   44,  108,   50,   50,   50,   50,  111,
   46,   46,   46,   46,  110,  116,   50,   44,   44,   44,
   44,   45,   61,   45,  117,   45,   95,  120,   96,  119,
  128,  139,   82,  123,  142,  155,  129,  137,  138,   45,
   45,   45,   45,  102,  104,  103,   16,  140,  134,  141,
  143,  144,  148,  154,   16,  159,  160,  161,  168,  171,
  170,  156,  157,  147,  158,  149,  173,  174,  175,  178,
  177,  181,    6,   24,   26,  179,  180,  184,   72,   23,
  172,   62,   18,   19,   78,   89,    0,  124,    0,    0,
    0,  164,    0,  115,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  152,  182,  135,    0,    0,    0,    0,
    0,    0,    0,   57,   58,  112,    5,   36,    6,    0,
   57,   58,    7,    8,   59,    9,    0,   10,    0,   11,
   57,   58,    5,    0,    6,   12,   13,   14,    7,    8,
    0,    9,   49,   10,    0,   11,   57,   58,    5,    0,
    6,   12,   13,   14,    7,    8,    0,    9,    0,   10,
    0,   11,   57,   58,    5,    0,    6,   12,   13,   14,
    7,    8,    0,    9,    0,   10,    0,   11,    0,    0,
    5,    0,    6,   12,   13,   14,    7,    8,   84,    9,
    0,   10,   70,   11,   70,    0,    5,    0,    6,   12,
   13,   14,    7,    8,  121,    9,    0,   10,    0,   11,
    0,   12,   13,   12,   13,   12,   13,   14,   50,   50,
   50,    0,    0,   46,   46,   46,    0,    0,    0,    0,
   44,   44,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   45,   45,   45,   99,  100,  101,    0,    0,
    0,    0,    0,    5,    0,    6,    0,    0,    0,    7,
    0,    5,    9,    6,   10,   75,   11,    7,    0,    0,
    9,    0,   10,    0,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   45,   44,   59,   59,   59,   18,   60,   45,   15,
   10,   14,  257,  116,   41,   40,  271,   43,   45,   45,
   58,   59,   44,   44,   42,   41,  257,  258,   44,   47,
   41,   40,   40,   59,   45,  280,   48,   59,   59,  276,
  277,   41,  257,  258,   97,   98,   52,   40,   95,   96,
   45,  154,   41,   41,   43,   43,   45,   45,   37,   59,
   41,   59,   43,   40,   45,  257,   40,   67,   43,  279,
   45,  123,  257,   85,   58,   32,   41,   80,   41,   40,
  260,  279,  123,  257,   40,  271,   41,   41,   42,   43,
  268,   45,   41,   47,   43,   74,   45,   40,   44,   41,
  125,   43,   41,   45,   41,   59,   60,   61,   62,   41,
   59,   60,   61,   62,   58,   40,  125,   59,   60,   61,
   62,   41,   79,   43,   59,   45,   43,   40,   45,   59,
  266,  110,  125,  125,  113,  135,  269,   59,  257,   59,
   60,   61,   62,   60,   61,   62,   40,   58,  105,   44,
   58,   41,   40,   40,   40,   58,   41,   59,   59,  257,
  262,  140,  141,  120,  143,  122,   59,   59,   59,   41,
   59,  261,    0,   41,   41,   59,   59,   59,   58,   41,
  159,   41,  123,  123,   42,   56,   -1,   87,   -1,   -1,
   -1,  148,   -1,   74,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  258,  258,  261,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  257,  257,  257,  259,   -1,
  257,  258,  263,  264,  268,  266,   -1,  268,   -1,  270,
  257,  258,  257,   -1,  259,  276,  277,  278,  263,  264,
   -1,  266,  279,  268,   -1,  270,  257,  258,  257,   -1,
  259,  276,  277,  278,  263,  264,   -1,  266,   -1,  268,
   -1,  270,  257,  258,  257,   -1,  259,  276,  277,  278,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,   -1,
  257,   -1,  259,  276,  277,  278,  263,  264,  265,  266,
   -1,  268,  257,  270,  257,   -1,  257,   -1,  259,  276,
  277,  278,  263,  264,  265,  266,   -1,  268,   -1,  270,
   -1,  276,  277,  276,  277,  276,  277,  278,  272,  273,
  274,   -1,   -1,  272,  273,  274,   -1,   -1,   -1,   -1,
  272,  273,  274,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  272,  273,  274,  272,  273,  274,   -1,   -1,
   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
   -1,  257,  266,  259,  268,  269,  270,  263,   -1,   -1,
  266,   -1,  268,   -1,  270,
};
}
final static short YYFINAL=3;
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
"MAYORIGUAL","DISTINTO","COMENTARIO","I16","F32","CONST","CADENA","\"\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombre_prog cuerpo_prog",
"nombre_prog : ID",
"nombre_prog : \"\"",
"cuerpo_prog : '{' bloque '}'",
"cuerpo_prog : '{' '}'",
"cuerpo_prog : '{' bloque",
"cuerpo_prog : bloque '}'",
"bloque : bloque sentencia",
"bloque : sentencia",
"sentencia : ejecucion",
"sentencia : declaracion",
"declaracion : declaracion_var",
"declaracion : declaracion_func",
"declaracion : declaracion_const",
"declaracion_func : header_func '{' cuerpo_func '}'",
"header_func : FUN nombre_func '(' lista_parametros_funcion ')' ':' tipo",
"header_func : FUN nombre_func '(' ')' ':' tipo",
"header_func : FUN nombre_func '(' ')' ':'",
"header_func : FUN nombre_func '(' lista_parametros_funcion ')' ':'",
"header_func : FUN '(' ')' ':' tipo",
"header_func : FUN '(' lista_parametros_funcion ')' ':' tipo",
"lista_parametros_funcion : tipo ID ',' tipo ID",
"lista_parametros_funcion : tipo ID",
"lista_parametros_funcion : ID",
"lista_parametros_funcion : ID ',' ID",
"lista_parametros_funcion : tipo",
"lista_parametros_funcion : tipo ',' tipo",
"cuerpo_func : bloque RETURN '(' expresion_aritmetica ')' ';'",
"cuerpo_func : RETURN '(' expresion_aritmetica ')' ';'",
"cuerpo_func : bloque '(' expresion_aritmetica ')' ';'",
"cuerpo_func : bloque RETURN '(' ')' ';'",
"cuerpo_func : RETURN '(' ')' ';'",
"nombre_func : ID",
"ejecucion : asignacion",
"ejecucion : seleccion",
"ejecucion : impresion",
"ejecucion : estruct_do_until",
"ejecucion : BREAK ';'",
"ejecucion : CONTINUE ';'",
"ejecucion : CONTINUE ':' etiqueta ';'",
"ejecucion : etiqueta ':' estruct_do_until",
"ejecucion : ID ASIG sentencia_ctr_expr",
"asignacion : ID ASIG expresion_aritmetica ';'",
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
"seleccion : IF condicion_seleccion then_seleccion",
"condicion_seleccion : '(' condicion ')'",
"then_seleccion : THEN ejecucion ';'",
"then_seleccion : THEN ejecucion ELSE ejecucion END_IF ';'",
"condicion : expresion_aritmetica operador expresion_aritmetica",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : '='",
"operador : DISTINTO",
"estruct_do_until : DO bloque_do_until UNTIL '(' condicion ')' ';'",
"bloque_do_until : bloque_do_until ejecucion",
"bloque_do_until : ejecucion",
"etiqueta : ID",
"sentencia_ctr_expr : DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE CTE ';'",
"sentencia_ctr_expr : DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE ';'",
"bloque_do_until_expr : ejecucion BREAK CTE ';'",
"bloque_do_until_expr : ejecucion BREAK ';'",
"impresion : OUT '(' CADENA ')' ';'",
"impresion : '(' CADENA ')' ';'",
"declaracion_const : CONST list_const ';'",
"list_const : list_const ',' asignacion",
"list_const : asignacion",
"declaracion_var : tipo lista_de_variables ';'",
"lista_de_variables : lista_de_variables ',' variable",
"lista_de_variables : variable",
"variable : ID",
"tipo : I16",
"tipo : F32",
};

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

void yyerror(String mensaje) {
    // funcion utilizada para imprimir errores que produce yacc
    System.out.println("Error yacc: " + mensaje);
}


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
        yychar = AnalizadorLexico.yylex();  //get next token
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
case 3:
//#line 9 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": se esperaba un nombre");}
break;
case 6:
//#line 14 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta un }");}
break;
case 7:
//#line 15 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta un {");}
break;
case 18:
//#line 36 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo de retorno");}
break;
case 19:
//#line 37 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo de retorno");}
break;
case 20:
//#line 38 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre de la funcion");}
break;
case 21:
//#line 39 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre de la funcion");}
break;
case 24:
//#line 43 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo del parametro");}
break;
case 25:
//#line 44 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo del parametro");}
break;
case 26:
//#line 45 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre del parametro");}
break;
case 27:
//#line 46 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre del parametro");}
break;
case 30:
//#line 50 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
break;
case 31:
//#line 51 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
break;
case 32:
//#line 52 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
break;
case 74:
//#line 117 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la constante si no se cumple");}
break;
case 76:
//#line 120 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la constante si no se cumple");}
break;
case 78:
//#line 124 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la palabra reservada OUT");}
break;
//#line 627 "Parser.java"
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
        yychar = AnalizadorLexico.yylex();        //get next character
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
