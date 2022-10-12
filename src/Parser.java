import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
static ParserVal yylval;//the 'lval' (result) I got from yylex()
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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    2,    3,    3,    4,
    4,    6,    6,    6,    9,   10,   10,    7,   13,   13,
   14,   12,   12,    8,   15,   15,   15,   15,   15,   15,
   18,   18,   18,   18,   18,   18,   16,   16,   16,   16,
   16,   17,    5,    5,    5,    5,    5,    5,    5,    5,
    5,   11,   19,   19,   19,   25,   25,   25,   26,   26,
   26,   26,   27,   27,   27,   27,   20,   29,   29,   28,
   30,   30,   30,   30,   30,   30,   21,   21,   22,   31,
   31,   23,   24,   24,   32,   32,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    2,    2,    2,    1,    1,
    1,    1,    1,    1,    2,    3,    1,    3,    3,    1,
    1,    1,    1,    4,    7,    6,    5,    6,    5,    6,
    5,    2,    1,    3,    1,    3,    6,    5,    5,    5,
    4,    1,    1,    1,    1,    1,    2,    2,    4,    3,
    3,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    4,    3,    3,    1,    1,    5,    3,    6,    3,
    1,    1,    1,    1,    1,    1,    5,    4,    7,    2,
    1,    1,    9,    8,    4,    3,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   22,   23,    0,    0,    0,    1,    0,    9,   10,
   11,   12,   13,   14,   43,    0,    0,   44,   45,   46,
    0,    0,    0,    0,   42,    0,    0,   47,   81,    0,
   48,    0,    0,    0,   17,    5,    0,    0,    7,    8,
   21,    0,   20,    0,    0,    0,   60,    0,    0,    0,
   51,    0,   58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   80,   82,    0,    0,    0,    4,    0,    0,
   18,    0,    0,    0,   50,    0,    0,    0,   61,    0,
    0,   52,    0,    0,   72,   74,   76,   71,   73,   75,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   49,   16,   78,   19,    0,    0,    0,   24,   66,
   65,    0,    0,    0,    0,    0,   56,   57,    0,    0,
   67,   77,   34,   29,    0,   36,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   62,    0,   86,    0,    0,
    0,   30,   26,    0,    0,   41,    0,    0,    0,    0,
   63,   64,   85,    0,    0,   68,   31,   25,   79,   38,
   40,    0,   39,    0,    0,   37,    0,    0,    0,   84,
   69,   83,
};
final static short yydgoto[] = {                          3,
    4,   17,   18,   19,   20,   21,   22,   23,   24,   44,
   25,   26,   52,   53,   27,   84,   37,   70,   64,   28,
   29,   30,   31,   61,   62,   63,  122,   65,  131,  101,
   40,   88,
};
final static short yysindex[] = {                      -243,
    0,    0,    0,  -40, -250,   34,   39,  -37,  -20,   88,
   23,    0,    0, -224,  -24, -203,    0,   17,    0,    0,
    0,    0,    0,    0,    0, -169,  -29,    0,    0,    0,
   38,  -36,   47, -160,    0,  -28,   59,    0,    0,   80,
    0, -149, -158,   70,    0,    0,   33,   84,    0,    0,
    0,  -21,    0,   49, -139,   91,    0,   88,   47,   18,
    0,  -25,    0,  -11,   92,   94,   95,   82,  -39,   97,
  -26,  101,    0,    0,   85,   47, -224,    0,   86, -169,
    0,  103,  -38,   22,    0, -238, -120, -121,    0,   47,
   47,    0,   47,   47,    0,    0,    0,    0,    0,    0,
   47, -111,   93, -107, -218,  107, -218,   96,   98,  114,
   47,    0,    0,    0,    0,   15,  119,   47,    0,    0,
    0,   27,  -53,  120,  -25,  -25,    0,    0,   35,   88,
    0,    0,    0,    0, -218,    0, -218, -218,  105,  123,
  102,  -14,   31,   21, -173,    0,  106,    0,   47,  -55,
  -91,    0,    0, -218,  108,    0,  109,  110,   24,  111,
    0,    0,    0,  130,   88,    0,    0,    0,    0,    0,
    0,  113,    0,  -88,  -87,    0,  -51,  115,  117,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  121,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,  177,    0,    0,    0,
    0,    0,    0,    0,    0,  -17,    0,    0,    0,    0,
    0,   50,    0,    0,    0,    0,  137,    0,  139,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  140,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   57,   62,    0,    0,  141,    0,
    0,    0,    0,    0,    0,    0,    0,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   61,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  170,   -7,   -3,    0,    0,    0,    0,    0,
   -2,   -1,    0,  112,    0,    0,    0,  116,   14,    0,
    0,  131,  146,    0,   -4,  -41,    0, -101,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=358;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   15,  118,   36,  166,  107,  148,   39,  180,   59,  140,
   50,   45,   68,    1,  109,   16,   93,   89,  120,  121,
   32,   94,   80,   59,   59,   59,  157,   59,   90,   59,
   91,   90,   43,   91,   69,    2,   73,   81,   38,   50,
   15,   59,   59,   59,   59,   60,   83,  164,   98,  100,
   99,  127,  128,   48,   87,  141,   16,   12,   13,   59,
   90,  160,   91,   90,  172,   91,   90,  146,   91,   69,
  145,  158,   16,   33,  113,   59,   92,   90,   34,   91,
   42,   41,   15,  161,  162,  125,  126,   51,   16,   60,
   55,   59,   55,   54,   55,   55,   66,   53,   71,   53,
   46,   53,   54,  134,   54,  136,   54,   74,   55,   55,
   55,   55,   76,   77,  129,   53,   53,   53,   53,   16,
   54,   54,   54,   54,   79,   15,  150,   16,   10,  142,
   86,  144,  102,  151,  103,  152,  153,  108,  104,  105,
  111,   49,  116,  112,  114,  123,  119,  124,  130,  133,
  135,  132,  168,  137,  139,  138,  159,   78,  143,  149,
  156,  175,  154,  155,  163,  167,  169,  170,  171,  173,
  174,  176,  177,  181,  178,  182,    6,   33,   82,   35,
   32,   70,   27,   28,   47,   85,  110,   75,    0,    0,
    0,  115,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  147,  165,  179,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    5,  106,    6,   35,
   56,   57,    7,    8,    0,    9,  117,   10,   67,   11,
   67,   58,    5,    0,    6,   12,   13,   14,    7,    8,
    0,    9,    0,   10,    0,   11,    0,   12,   13,   12,
   13,   12,   13,   14,   59,   59,   59,   15,    0,   15,
   95,   96,   97,   15,   15,   15,   15,    0,   15,    0,
   15,   56,   57,    5,    0,    6,   15,   15,   15,    7,
    8,    0,    9,    0,   10,    0,   11,   56,   57,    5,
    0,    6,   12,   13,   14,    7,    8,    0,    9,    0,
   10,    0,   11,   56,   57,    5,    0,    6,   12,   13,
   14,    7,    8,   82,    9,    0,   10,    0,   11,    0,
    0,   55,   55,   55,   12,   13,   14,    0,   53,   53,
   53,    0,    0,   54,   54,   54,    5,    0,    6,    0,
    0,    0,    7,    0,    5,    9,    6,   10,   72,   11,
    7,    0,    0,    9,    0,   10,    0,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   40,   59,   44,   59,   10,   59,   45,  111,
   18,   14,   41,  257,   41,   40,   42,   59,  257,  258,
  271,   47,   44,   41,   42,   43,   41,   45,   43,   47,
   45,   43,  257,   45,   36,  279,   40,   59,   59,   47,
   40,   59,   60,   61,   62,   32,   54,  149,   60,   61,
   62,   93,   94,  257,   58,   41,   40,  276,  277,   45,
   43,   41,   45,   43,   41,   45,   43,   41,   45,   71,
   44,   41,   40,   40,   77,   45,   59,   43,   40,   45,
   58,   59,  123,  257,  258,   90,   91,  257,   40,   76,
   41,   45,   43,  123,   45,   58,  257,   41,   40,   43,
  125,   45,   41,  105,   43,  107,   45,  257,   59,   60,
   61,   62,  271,   44,  101,   59,   60,   61,   62,   40,
   59,   60,   61,   62,   41,  125,  130,   40,  268,  116,
   40,  118,   41,  135,   41,  137,  138,   41,   44,   58,
   40,  125,   40,   59,   59,  266,  125,  269,  260,  257,
   44,   59,  154,   58,   41,   58,  143,  125,   40,   40,
   59,  165,   58,   41,   59,  257,   59,   59,   59,   59,
   41,   59,  261,   59,  262,   59,    0,   41,   58,   41,
   41,   41,  123,  123,   15,   55,   71,   42,   -1,   -1,
   -1,   80,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  258,  261,  258,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  257,  259,  257,
  257,  258,  263,  264,   -1,  266,  265,  268,  257,  270,
  257,  268,  257,   -1,  259,  276,  277,  278,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,  276,  277,  276,
  277,  276,  277,  278,  272,  273,  274,  257,   -1,  259,
  272,  273,  274,  263,  264,  265,  266,   -1,  268,   -1,
  270,  257,  258,  257,   -1,  259,  276,  277,  278,  263,
  264,   -1,  266,   -1,  268,   -1,  270,  257,  258,  257,
   -1,  259,  276,  277,  278,  263,  264,   -1,  266,   -1,
  268,   -1,  270,  257,  258,  257,   -1,  259,  276,  277,
  278,  263,  264,  265,  266,   -1,  268,   -1,  270,   -1,
   -1,  272,  273,  274,  276,  277,  278,   -1,  272,  273,
  274,   -1,   -1,  272,  273,  274,  257,   -1,  259,   -1,
   -1,   -1,  263,   -1,  257,  266,  259,  268,  269,  270,
  263,   -1,   -1,  266,   -1,  268,   -1,  270,
};
}
final static short YYFINAL=3;
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
"MAYORIGUAL","DISTINTO","COMENTARIO","I16","F32","CONST","\"\"",
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
"declaracion_const : CONST list_const",
"list_const : list_const ',' asignacion",
"list_const : asignacion",
"declaracion_var : tipo lista_de_variables ';'",
"lista_de_variables : lista_de_variables ',' variable",
"lista_de_variables : variable",
"variable : ID",
"tipo : I16",
"tipo : F32",
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
"cuerpo_func : sentencia RETURN '(' expresion_aritmetica ')' ';'",
"cuerpo_func : RETURN '(' expresion_aritmetica ')' ';'",
"cuerpo_func : sentencia '(' expresion_aritmetica ')' ';'",
"cuerpo_func : sentencia RETURN '(' ')' ';'",
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
"seleccion : IF '(' condicion ')' then_seleccion",
"then_seleccion : THEN ejecucion ';'",
"then_seleccion : THEN ejecucion ELSE ejecucion END_IF ';'",
"condicion : expresion_aritmetica operador expresion_aritmetica",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : '='",
"operador : DISTINTO",
"impresion : OUT '(' ID ')' ';'",
"impresion : '(' ID ')' ';'",
"estruct_do_until : DO bloque_do_until UNTIL '(' condicion ')' ';'",
"bloque_do_until : bloque_do_until ejecucion",
"bloque_do_until : ejecucion",
"etiqueta : ID",
"sentencia_ctr_expr : DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE CTE ';'",
"sentencia_ctr_expr : DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE ';'",
"bloque_do_until_expr : ejecucion BREAK CTE ';'",
"bloque_do_until_expr : ejecucion BREAK ';'",
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

public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
	try {
		String pathA = "src/MatrizAcciones.txt";
		String pathS = "src/MatrizEstados.txt";
		AnalizadorLexico l = new AnalizadorLexico(pathS, pathA, 15, 26);
		AnalizadorLexico.r = new BufferedReader(new FileReader("src/pruebas.txt"));
		Parser p = new Parser(true);
		p.run();
		TablaSimbolos.imprimirTabla();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
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
//#line 11 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": se esperaba un nombre");}
break;
case 6:
//#line 16 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta un }");}
break;
case 7:
//#line 17 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta un {");}
break;
case 27:
//#line 53 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo de retorno");}
break;
case 28:
//#line 54 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo de retorno");}
break;
case 29:
//#line 55 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre de la funcion");}
break;
case 30:
//#line 56 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre de la funcion");}
break;
case 33:
//#line 60 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo del parametro");}
break;
case 34:
//#line 61 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el tipo del parametro");}
break;
case 35:
//#line 62 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre del parametro");}
break;
case 36:
//#line 63 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta el nombre del parametro");}
break;
case 39:
//#line 67 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
break;
case 40:
//#line 68 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
break;
case 41:
//#line 69 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": la funcion debe retornar un valor");}
break;
case 78:
//#line 123 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la palabra reservada OUT");}
break;
case 84:
//#line 136 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la constante si no se cumple");}
break;
case 86:
//#line 139 "gramatica.y"
{System.out.println("Error sintactico en la linea " + AnalizadorLexico.getLine() + ": falta la constante si no se cumple");}
break;
//#line 619 "Parser.java"
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
