import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    4,    4,    6,    6,
    6,    9,   10,   10,    7,   12,   12,   13,   11,   11,
    8,   14,   14,   17,   17,   15,   16,    5,    5,    5,
    5,    5,    5,    5,    5,    5,   19,   18,   18,   18,
   25,   25,   25,   26,   26,   26,   26,   27,   27,   27,
   27,   20,   20,   29,   30,   28,   31,   31,   31,   31,
   31,   31,   21,   22,   32,   32,   23,   24,   33,
};
final static short yylen[] = {                            2,
    2,    1,    3,    2,    2,    1,    1,    1,    1,    1,
    1,    3,    5,    3,    3,    3,    1,    1,    1,    1,
    4,    7,    6,    5,    2,    6,    1,    1,    1,    1,
    1,    2,    2,    3,    2,    3,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    4,    3,    3,    1,
    1,    5,    6,    3,    4,    3,    1,    1,    1,    1,
    1,    1,    5,    7,    2,    1,    1,    9,    4,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    1,    0,    0,    0,    0,    0,
    0,    0,   19,   20,    0,    4,    0,    6,    7,    8,
    9,   10,   11,    0,    0,   28,   29,   30,   31,    0,
    0,    0,    0,   27,    0,   32,   66,    0,   33,    0,
    0,    3,    5,   18,    0,   17,    0,    0,   35,    0,
   45,    0,    0,    0,   36,    0,   43,    0,    0,    0,
    0,    0,   65,    0,   12,    0,   15,    0,    0,    0,
   34,    0,    0,    0,   46,    0,    0,   37,    0,    0,
   58,   60,   62,   57,   59,   61,    0,    0,    0,    0,
    0,    0,    0,   14,    0,   16,    0,   21,   51,   50,
    0,    0,    0,    0,    0,   41,   42,    0,    0,    0,
   63,    0,    0,    0,    0,    0,    0,    0,   47,    0,
    0,    0,    0,   53,   23,    0,    0,    0,   13,    0,
   48,   49,   69,    0,   54,    0,    0,   22,   64,    0,
    0,    0,   24,   26,    0,   55,    0,   68,
};
final static short yydgoto[] = {                          2,
    3,    5,   17,   18,   19,   20,   21,   22,   23,   41,
   24,   45,   46,   25,   70,   35,   92,   58,   26,   27,
   28,   29,   30,   55,   56,   57,  101,   59,  110,  124,
   87,   38,   74,
};
final static short yysindex[] = {                      -234,
    0,    0,  -80, -116,    0, -193,   58,   64, -152,   48,
 -146,   53,    0,    0, -143,    0,  -91,    0,    0,    0,
    0,    0,    0, -142,   -4,    0,    0,    0,    0, -194,
  -28,  -33, -139,    0,   81,    0,    0, -160,    0, -148,
    3,    0,    0,    0,    4,    0, -182,   66,    0,   86,
    0, -146,  -33,    1,    0,   19,    0,   -5,   87,   88,
  -41,   91,    0, -131,    0, -125,    0, -142, -132,    9,
    0, -208, -130, -134,    0,  -33,  -33,    0,  -33,  -33,
    0,    0,    0,    0,    0,    0,  -33, -123,   79,   82,
 -118,  101,  -33,    0, -127,    0,  105,    0,    0,    0,
   -2, -112,  109,   19,   19,    0,    0,   40, -146, -110,
    0, -186,  111,   95,  115, -101,  -33, -165,    0,   99,
  -33,  100, -146,    0,    0, -186, -186,  104,    0,   28,
    0,    0,    0,  123,    0,  -97,  -90,    0,    0,  110,
  -87,  112,    0,    0,  -88,    0,  117,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -181,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -40,
    0,    0,    0,    0,    0,  -35,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -30,   -8,    0,    0,  137,    0,  -55,
    0,    0,  139,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   11,    7,    0,    0,    0,    0,    0,
  -47,    0,  113,    0,    0,    0,    0,  -15,    0,    0,
    0,  152,    0,    0,   24,  -12,    0,  -57,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=269;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         90,
   44,   44,   44,   52,   44,   40,   44,   40,   16,   40,
   38,   53,   38,   91,   38,   54,   53,   37,   44,   44,
   44,   44,    1,   40,   40,   40,   40,   43,   38,   38,
   38,   38,   39,   42,   39,  115,   39,   76,  119,   77,
   75,  118,    4,   76,   63,   77,   66,   68,   99,  100,
   39,   39,   39,   39,   84,   86,   85,   69,   73,   78,
   79,   65,   67,  134,  125,   80,  106,  107,  140,   52,
   76,  108,   77,   11,    6,   48,    7,   31,  137,  138,
    8,    9,   76,   10,   77,   11,   67,   12,   67,   13,
   14,  131,  132,   13,   14,   15,    6,   32,    7,  104,
  105,  130,    8,   33,   34,   10,   36,   11,   62,   12,
    6,   39,    7,   40,   44,  122,    8,   60,   47,   10,
   61,   11,   64,   12,   71,   72,   94,   88,   89,  136,
   93,   95,   97,   98,  103,  102,  109,  111,  113,  112,
    6,  114,    7,  116,  117,  120,    8,    9,  121,   10,
  123,   11,  127,   12,  126,  128,  129,  133,  135,   13,
   14,   15,  139,  141,  142,    6,  143,    7,  144,  147,
  146,    8,    9,  145,   10,  148,   11,   56,   12,   25,
   96,   49,    0,    0,   13,   14,   15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   52,    0,   52,    0,    0,   52,   52,   52,   52,
   52,    0,   52,   52,   52,    0,    0,    0,    0,    0,
   52,   52,   52,   50,   51,    0,    0,    0,   50,   51,
    0,   44,   44,   44,   13,   14,   40,   40,   40,   52,
    0,   38,   38,   38,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,   39,   39,   81,   82,   83,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   41,   42,   43,   59,   45,   41,   47,   43,  125,   45,
   41,   45,   43,   61,   45,   31,   45,   11,   59,   60,
   61,   62,  257,   59,   60,   61,   62,   17,   59,   60,
   61,   62,   41,  125,   43,   93,   45,   43,   41,   45,
   53,   44,  123,   43,   38,   45,   44,   44,  257,  258,
   59,   60,   61,   62,   60,   61,   62,   47,   52,   59,
   42,   59,   59,  121,  112,   47,   79,   80,   41,  125,
   43,   87,   45,  268,  257,  270,  259,  271,  126,  127,
  263,  264,   43,  266,   45,  268,  268,  270,  270,  276,
  277,  257,  258,  276,  277,  278,  257,   40,  259,   76,
   77,  117,  263,   40,  257,  266,   59,  268,  269,  270,
  257,   59,  259,  257,  257,  109,  263,  257,  123,  266,
   40,  268,  271,  270,   59,   40,  258,   41,   41,  123,
   40,  257,  265,  125,  269,  266,  260,   59,  257,   58,
  257,   41,  259,  271,   40,  258,  263,  264,   40,  266,
  261,  268,   58,  270,   44,   41,  258,   59,   59,  276,
  277,  278,   59,   41,  262,  257,  257,  259,   59,  258,
   59,  263,  264,  261,  266,   59,  268,   41,  270,   41,
   68,   30,   -1,   -1,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,   -1,  259,   -1,   -1,  262,  263,  264,  265,
  266,   -1,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  257,  258,   -1,   -1,   -1,  257,  258,
   -1,  272,  273,  274,  276,  277,  272,  273,  274,  268,
   -1,  272,  273,  274,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,  274,  272,  273,  274,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=278;
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
"MAYORIGUAL","DISTINTO","COMENTARIO","I16","F32","CONST",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombre_prog cuerpo_prog",
"nombre_prog : ID",
"cuerpo_prog : '{' bloque '}'",
"cuerpo_prog : '{' '}'",
"bloque : bloque sentencia",
"bloque : sentencia",
"sentencia : ejecucion",
"sentencia : declaracion",
"declaracion : declaracion_var",
"declaracion : declaracion_func",
"declaracion : declaracion_const",
"declaracion_const : CONST list_const ';'",
"list_const : list_const ',' ID ASIG CTE",
"list_const : ID ASIG CTE",
"declaracion_var : tipo lista_de_variables ';'",
"lista_de_variables : lista_de_variables ',' variable",
"lista_de_variables : variable",
"variable : ID",
"tipo : I16",
"tipo : F32",
"declaracion_func : header_func '{' cuerpo_func '}'",
"header_func : FUN nombre_func '(' lista_parametros_funcion ')' ':' tipo",
"header_func : FUN nombre_func '(' ')' ':' tipo",
"lista_parametros_funcion : tipo ID ',' tipo ID",
"lista_parametros_funcion : tipo ID",
"cuerpo_func : sentencia RETURN '(' expresion_aritmetica ')' ';'",
"nombre_func : ID",
"ejecucion : asignacion",
"ejecucion : seleccion",
"ejecucion : impresion",
"ejecucion : estruct_do_until",
"ejecucion : BREAK ';'",
"ejecucion : CONTINUE ';'",
"ejecucion : etiqueta CONTINUE ';'",
"ejecucion : etiqueta estruct_do_until",
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
"seleccion : IF '(' condicion ')' then_seleccion else_seleccion",
"then_seleccion : THEN ejecucion ';'",
"else_seleccion : ELSE ejecucion END_IF ';'",
"condicion : expresion_aritmetica operador expresion_aritmetica",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : '='",
"operador : DISTINTO",
"impresion : OUT '(' ID ')' ';'",
"estruct_do_until : DO bloque_do_until UNTIL '(' condicion ')' ';'",
"bloque_do_until : bloque_do_until ejecucion",
"bloque_do_until : ejecucion",
"etiqueta : ID",
"sentencia_ctr_expr : DO bloque_do_until_expr UNTIL '(' condicion ')' ELSE CTE ';'",
"bloque_do_until_expr : ejecucion BREAK CTE ';'",
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

public static void main(String[] args) throws IOException {
	try {
		String pathA = "src/MatrizAcciones.txt";
		String pathS = "src/MatrizEstados.txt";
		AnalizadorLexico l = new AnalizadorLexico(pathS, pathA, 15, 26);
        AnalizadorLexico.r = new BufferedReader(new FileReader("src/pruebas.txt"));
        Parser parser = new Parser(true);
        parser.run();
        TablaSimbolos.imprimirTabla();
} catch (IOException excepcion) {
        excepcion.printStackTrace();
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
