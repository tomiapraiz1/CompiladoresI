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
public final static short IGUAL=279;
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
   58,   60,   62,   61,   57,   59,    0,    0,    0,    0,
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
final static short yysindex[] = {                      -213,
    0,    0,  -75, -118,    0, -217,   22,   38, -172,   42,
 -155,   44,    0,    0, -165,    0, -102,    0,    0,    0,
    0,    0,    0, -145,   -9,    0,    0,    0,    0, -228,
  -42,  -40, -141,    0,   77,    0,    0, -170,    0, -153,
   -1,    0,    0,    0,    9,    0, -194,   60,    0,   80,
    0, -155,  -40,   -8,    0,   10,    0,  -29,   81,   82,
  -28,   84,    0, -133,    0, -131,    0, -145, -138,    3,
    0, -167, -137, -136,    0,  -40,  -40,    0,  -40,  -40,
    0,    0,    0,    0,    0,    0,  -40, -129,   73,   76,
 -122,   95,  -40,    0, -134,    0,   98,    0,    0,    0,
   15, -116,  100,   10,   10,    0,    0,   34, -155, -117,
    0, -171,   99,   89,  108, -107,  -40, -163,    0,   94,
  -40,   97, -155,    0,    0, -171, -171,  104,    0,   30,
    0,    0,    0,  113,    0,  -97,  -90,    0,    0,  110,
  -91,  112,    0,    0,  -86,    0,  114,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -182,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -41,
    0,    0,    0,    0,    0,  -33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -21,  -13,    0,    0,  136,    0,    5,
    0,    0,  137,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    8,   -2,    0,    0,    0,    0,    0,
  -46,    0,  111,    0,    0,    0,    0,  -20,    0,    0,
    0,  150,    0,    0,   33,  -19,    0,  -76,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=283;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   44,   44,   53,   44,   53,   44,   16,   40,   37,   40,
   54,   40,   90,   76,   91,   77,  115,   44,   44,   38,
   44,   38,   42,   38,   43,   40,   40,   39,   40,   39,
   85,   39,   86,   75,   76,   63,   77,   38,   38,   11,
   38,   48,   66,    1,  134,   39,   39,    4,   39,   73,
   78,   79,   68,   31,   69,  119,   80,   65,  118,  106,
  107,   32,    6,   52,    7,  125,  108,   67,    8,    9,
  140,   10,   76,   11,   77,   12,   76,   33,   77,  137,
  138,   13,   14,   15,   34,   67,    6,   67,    7,   99,
  100,   40,    8,  131,  132,   10,  130,   11,   62,   12,
   36,    6,   39,    7,   13,   14,  122,    8,  104,  105,
   10,   44,   11,   47,   12,   60,   61,   64,   71,   72,
  136,   88,   89,   93,   94,   95,   97,   98,  102,   52,
  109,  111,  103,  112,  113,  114,  116,  117,    6,  121,
    7,  120,  126,  123,    8,    9,  127,   10,  128,   11,
  129,   12,  133,  141,    6,  135,    7,   13,   14,   15,
    8,    9,  139,   10,  142,   11,  143,   12,  144,  145,
  146,  147,  148,   13,   14,   15,   56,   25,   96,   49,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   50,   51,   50,   51,    0,    0,
    0,    0,    0,    0,    0,   52,    0,    0,    0,    0,
   44,   44,   44,    0,    0,    0,    0,   44,   40,   40,
   40,    0,   81,   82,   83,   40,    0,   13,   14,   84,
   38,   38,   38,    0,    0,    0,    0,   38,   39,   39,
   39,   52,    0,   52,    0,   39,   52,   52,   52,   52,
   52,    0,   52,   52,   52,    0,    0,    0,    0,    0,
   52,   52,   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   45,   45,   45,   47,  125,   41,   11,   43,
   31,   45,   41,   43,   61,   45,   93,   59,   60,   41,
   62,   43,  125,   45,   17,   59,   60,   41,   62,   43,
   60,   45,   62,   53,   43,   38,   45,   59,   60,  268,
   62,  270,   44,  257,  121,   59,   60,  123,   62,   52,
   59,   42,   44,  271,   47,   41,   47,   59,   44,   79,
   80,   40,  257,   59,  259,  112,   87,   59,  263,  264,
   41,  266,   43,  268,   45,  270,   43,   40,   45,  126,
  127,  276,  277,  278,  257,  268,  257,  270,  259,  257,
  258,  257,  263,  257,  258,  266,  117,  268,  269,  270,
   59,  257,   59,  259,  276,  277,  109,  263,   76,   77,
  266,  257,  268,  123,  270,  257,   40,  271,   59,   40,
  123,   41,   41,   40,  258,  257,  265,  125,  266,  125,
  260,   59,  269,   58,  257,   41,  271,   40,  257,   40,
  259,  258,   44,  261,  263,  264,   58,  266,   41,  268,
  258,  270,   59,   41,  257,   59,  259,  276,  277,  278,
  263,  264,   59,  266,  262,  268,  257,  270,   59,  261,
   59,  258,   59,  276,  277,  278,   41,   41,   68,   30,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  257,  258,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  268,   -1,   -1,   -1,   -1,
  272,  273,  274,   -1,   -1,   -1,   -1,  279,  272,  273,
  274,   -1,  272,  273,  274,  279,   -1,  276,  277,  279,
  272,  273,  274,   -1,   -1,   -1,   -1,  279,  272,  273,
  274,  257,   -1,  259,   -1,  279,  262,  263,  264,  265,
  266,   -1,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
"MAYORIGUAL","DISTINTO","COMENTARIO","I16","F32","CONST","IGUAL",
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
"operador : IGUAL",
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
