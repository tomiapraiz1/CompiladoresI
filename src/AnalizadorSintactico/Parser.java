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






//#line 2 "Gramatica.y"
package AnalizadorSintactico;
import java.io.IOException;
import java.io.Reader;
import AnalizadorLexico.*;
import GeneracionCodigo.*;
import java.util.ArrayList;
//#line 24 "Parser.java"




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
    0,    0,    0,    2,    4,    1,    1,    3,    3,    5,
    5,    7,    7,    7,    7,    8,   13,   13,   12,   12,
    9,   14,   14,   14,   14,   15,   15,   15,   15,   15,
   17,   17,   17,   17,   16,   16,   18,   20,   19,   19,
   19,   19,   19,   21,   21,   21,   22,   22,   22,   23,
   23,   23,   23,   23,   24,   24,   24,   24,   24,   24,
   10,   10,   25,   25,   26,   26,   27,   27,    6,    6,
   28,   28,   28,   28,   28,   28,   28,   28,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   30,   34,   35,   37,   37,   37,
   38,   38,   38,   38,   38,   38,   36,   36,   36,   39,
   40,   41,   41,   11,   42,   43,   31,   31,   31,   31,
   32,   32,   32,   32,   44,   45,   46,   46,   33,   33,
   33,   33,   47,   48,   49,   49,   49,
};
final static short yylen[] = {                            2,
    4,    3,    3,    1,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    3,    1,    1,
    3,    3,    2,    2,    3,    4,    3,    2,    2,    2,
    5,    2,    1,    3,    4,    3,    1,    1,    5,    4,
    4,    4,    4,    3,    3,    1,    3,    3,    1,    1,
    1,    2,    4,    3,    3,    3,    3,    3,    1,    1,
    3,    2,    3,    1,    3,    2,    3,    2,    1,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    3,    2,    2,    1,    4,    3,    3,    4,    3,    3,
    4,    3,    3,    3,    4,    1,    1,    5,    4,    4,
    1,    1,    1,    1,    1,    1,    4,    2,    3,    3,
    3,    2,    1,    6,    1,    1,    4,    3,    3,    3,
    5,    4,    4,    4,    1,    1,    2,    1,    6,    5,
    5,    5,    1,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,   96,    0,    0,    0,  115,
    0,    0,   19,   20,    0,    4,    0,    0,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,   69,   70,
    0,    0,    0,    0,    0,    0,   51,    0,    0,    0,
    0,    0,    0,   49,    0,    0,    0,    0,    0,   23,
    0,   79,  125,  113,    0,    0,   83,    0,    0,    0,
   64,    0,    5,    2,    8,   17,    0,    0,    0,    0,
    0,    0,    0,   71,   73,   75,   77,    0,    0,   97,
  116,    0,    0,  133,    0,    0,   94,    0,    0,   90,
    0,   52,    0,    0,    0,    0,   93,  119,    0,  120,
   22,   25,   81,  126,  112,    0,    0,    0,    0,   87,
   66,    0,   61,    0,    1,   16,    0,    0,    0,   29,
   28,    0,   37,   21,    0,    0,  102,  104,  106,  101,
  103,  105,    0,    0,    0,    0,    0,    0,    0,   54,
    0,  134,    0,    0,    0,   91,   88,    0,    0,   47,
   48,  117,    0,  122,    0,  123,  124,   85,   65,   63,
   18,   34,   27,    0,    0,    0,    0,    0,    0,    0,
   95,    0,    0,    0,   53,    0,    0,    0,    0,  127,
  121,    0,    0,   38,    0,   36,   26,  100,    0,   99,
    0,    0,    0,   55,   56,   58,   57,  137,    0,  130,
  131,    0,  132,   31,    0,    0,   35,   98,  110,    0,
  107,  114,  135,  129,    0,    0,    0,    0,   43,   41,
    0,   40,  111,   39,
};
final static short yydgoto[] = {                          3,
    4,   17,   18,   64,   19,   20,   21,   22,   23,   24,
   25,   26,   67,   27,   72,  124,   73,  125,  185,  186,
   42,   43,   44,  141,   60,   61,   28,   29,   30,   31,
   32,   33,   45,   34,   79,  136,   80,  134,  170,  211,
   55,   35,   82,   56,  106,  154,   86,  143,  200,
};
final static short yysindex[] = {                      -221,
    0,    0,    0,  332,  117,    0,  -38,   -8,  -52,    0,
  522,   21,    0,    0, -200,    0,  609,  476,    0,    0,
    0,    0,    0,    0,    0, -190,  -33,   12,    0,    0,
   18,   33,   66,   92,   92,   98,    0,  523,  -45,  -47,
 -124,   31,   11,    0,   81,  102,  -37,  105,  112,    0,
   87,    0,    0,    0,  348,  348,    0,  -50, -206,  -10,
    0,  476,    0,    0,    0,    0,   -9,  110,  -48, -182,
 -102,   34,  115,    0,    0,    0,    0,  -21, -101,    0,
    0,  -99,   -3,    0,  575,  575,    0,   31,  104,    0,
  107,    0,  -39,  -39,  -39,  -39,    0,    0,  124,    0,
    0,    0,    0,    0,    0,  -98,  538,  -98,  109,    0,
    0,  -82,    0, -200,    0,    0,  -80,  -79, -182,    0,
    0,  139,    0,    0,  609,  126,    0,    0,    0,    0,
    0,    0,  -40,  -39,   68,  -66,   70,  153,  159,    0,
  163,    0,  -98,  553,  -98,    0,    0,   11,   11,    0,
    0,    0,   92,    0,  -98,    0,    0,    0,    0,    0,
    0,    0,    0, -182,  -78, -182,  106,   75, -170,  -53,
    0,  609, -155, -149,    0, -202, -202,  -98, -202,    0,
    0,  -46,  -27,    0,   84,    0,    0,    0,   76,    0,
  576,   97,  492,    0,    0,    0,    0,    0,  -44,    0,
    0, -202,    0,    0,  108,   94,    0,    0,    0, -170,
    0,    0,    0,    0,  151,   56,  156,  597,    0,    0,
  166,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  202,    0,
    0,  226,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  248,    0,    0,
  272,  294,  316,    0,    0,    1,    0,    0,    0,    0,
    0,  113,   23,    0,    0,    0,    0,  -30,    0,    0,
  362,    0,    0,    0,    0,    0,    0,    0,    0,  451,
    0,  222,    0,    0,    0,    0,    0,  194,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  136,  384,    0,
  407,    0,    0,    0,    0,    0,    0,    0,  158,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  429,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  122,    0,
    0,  199,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  215,  216,    0,
    0,    0,    0,    0,    0,    0,    0,   45,   69,    0,
    0,    0,   91,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   14,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   19,    0,    0,    0,    0,    0,    0,  180,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  134,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    2,  200,    8,  692,    0,    0,    0,    0,
    0,    3,    0,    0,    0,    0,    0,    0,    0,   78,
  -23,   30,   46,    0,    0,  170,    0,    0,    0,    0,
    0,  245,  259,    0,    0,    0,  -20,  173,    0,    0,
   -5,    0,    0,    0,  -29,  355,    0,  -69, -148,
};
final static int YYTABLESIZE=910;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   50,   47,   93,  100,   94,   41,   52,   69,  110,  119,
   24,   90,  205,   87,   81,   88,  145,   41,   62,  130,
  132,  131,   46,   41,   70,   65,  108,   24,  201,   71,
  203,   50,   85,  114,  117,    1,    2,  140,  130,  132,
  131,   50,   50,   50,   44,   50,  184,   50,  113,  116,
  107,  111,   95,  214,  133,  198,   59,   96,  199,   50,
   50,   50,   50,   46,  112,   46,   66,   46,   45,   65,
   74,  120,  121,   93,  178,   94,   75,  155,   58,   57,
  144,   46,   46,   46,   46,   44,    5,   44,    6,   44,
  128,   76,    7,   13,   14,    9,  221,   11,   93,   12,
   94,  194,  195,   44,   44,   44,   44,  196,  197,   45,
  168,   45,   68,   45,  220,  190,  208,   93,   93,   94,
   94,  163,  148,  149,   77,   50,  165,   45,   45,   45,
   45,   78,  180,   92,  217,   67,   93,   83,   94,   97,
  150,  151,   98,  189,  101,  103,  188,   46,  215,  128,
   41,  102,   41,  118,  122,  126,  123,  118,  135,  206,
  137,   41,  146,  191,  152,  147,  182,  158,  187,   44,
  153,   68,   65,  193,   40,  159,  161,  162,    5,  136,
    6,  216,  164,  166,    7,    8,  183,    9,   10,   11,
  169,   12,  172,   45,   67,  171,  173,   13,   14,   15,
   65,   80,  174,  175,  218,   51,  109,  192,  184,  219,
  204,   36,   37,  213,  222,  128,  118,   36,   37,  210,
   11,    3,   38,   68,  224,   84,   24,   13,   14,   36,
   37,  127,  128,  129,   33,   36,   37,   68,  136,   32,
   46,   99,   13,   14,   30,   24,   24,   72,   48,   49,
  127,  128,  129,  138,  139,   60,   59,   50,   42,   50,
   67,  115,  207,   50,   50,   50,   50,   50,   50,   50,
   50,   74,   50,   50,   50,  108,   50,   50,   50,   46,
  109,   46,  118,  160,   91,   46,   46,   46,   46,   46,
   46,   46,   46,   76,   46,   46,   46,   89,   46,   46,
   46,   44,    0,   44,  136,  167,    0,   44,   44,   44,
   44,   44,   44,   44,   44,   78,   44,   44,   44,    0,
   44,   44,   44,    0,    0,   45,   80,   45,    0,    0,
    0,   45,   45,   45,   45,   45,   45,   45,   45,    0,
   45,   45,   45,    0,   45,   45,   45,  128,  128,  128,
   84,  128,    0,  128,  128,  128,  128,  128,  128,  128,
  128,   82,   36,   37,   36,   37,  128,  128,  128,   68,
    0,   68,   72,   36,   37,   68,   68,   68,   68,   68,
   68,   68,   68,   92,   38,    0,    0,   39,   68,   68,
   68,    0,   67,    0,   67,    0,   74,    0,   67,   67,
   67,   67,   67,   67,   67,   67,   89,    0,    0,    0,
    0,   67,   67,   67,  118,    0,  118,    0,   76,    0,
  118,  118,  118,  118,  118,  118,  118,  118,   86,    0,
    0,    0,    0,  118,  118,  118,  136,    0,  136,    0,
   78,    0,  136,  136,  136,  136,  136,  136,  136,  136,
   62,    0,    0,    0,   16,  136,  136,  136,   80,    0,
   80,  156,  157,    0,   80,   80,   80,   80,   80,   80,
   80,   80,  104,    0,    0,    0,    0,   80,   80,   80,
    0,    0,   84,    0,   84,    0,   82,    0,   84,   84,
   84,   84,   84,   84,   84,   84,    0,  176,  177,  179,
    0,   84,   84,   84,   72,    0,   72,    0,   92,  181,
   72,   72,   72,   72,   72,   72,   72,   72,    0,    0,
    0,    0,    0,   72,   72,   72,    0,    0,   74,    0,
   74,   89,  202,    0,   74,   74,   74,   74,   74,   74,
   74,   74,    0,    0,    0,    0,    0,   74,   74,   74,
   76,    0,   76,   86,    0,    0,   76,   76,   76,   76,
   76,   76,   76,   76,    0,    0,    0,    0,    0,   76,
   76,   76,   78,    0,   78,   62,    0,    0,   78,   78,
   78,   78,   78,   78,   78,   78,    0,    0,    5,    0,
    6,   78,   78,   78,    7,    8,    0,    9,   10,   11,
   63,   12,    0,    0,    5,    0,    6,   13,   14,   15,
    7,    0,    0,    9,    0,   11,  212,   12,   82,    0,
   82,    0,    0,    0,   82,   82,   82,   82,   82,   82,
   82,   82,    0,    0,    0,    0,    0,   82,   82,   82,
   92,    0,   92,    0,   53,   84,   92,   92,   92,   92,
   92,   92,   92,   92,    0,    0,    0,    0,    0,   92,
   92,   92,  104,   89,    0,   89,    0,    0,    0,   89,
   89,   89,   89,   89,   89,   89,   89,  142,    0,    0,
    0,    0,   89,   89,   89,   86,    0,   86,    0,    0,
    0,   86,   86,   86,   86,   86,   86,   86,   86,  142,
  209,    0,   54,    0,   86,   86,   86,   62,    0,   62,
    0,    0,    0,   62,   62,   62,   62,   62,   62,    0,
   62,  223,    0,    0,    0,    0,   62,   62,   62,   54,
    0,    0,    5,    0,    6,    0,    0,    0,    7,    8,
    0,    9,   10,   11,    0,   12,  105,   54,    5,    0,
    6,   13,   14,   15,    7,    8,    0,    9,   10,   11,
    0,   12,    0,    0,    0,    0,    0,   13,   14,   15,
    0,    0,    0,    0,    0,    0,  105,   54,    5,    5,
    6,    6,    0,    0,    7,    7,    0,    9,    9,   11,
   11,   12,   12,    0,    5,    0,    6,    0,  105,    0,
    7,    0,    0,    9,    0,   11,  153,   12,    0,    5,
    0,    6,    0,    0,    0,    7,    0,    0,    9,    0,
   11,  153,   12,    0,    0,    0,    0,    0,    0,    0,
    0,    5,    5,    6,    6,  105,    0,    7,    7,    0,
    9,    9,   11,   11,   12,   12,    0,    0,    0,    0,
    0,    0,    0,    5,    0,    6,    0,    0,    0,    7,
   54,    0,    9,    0,   11,    5,   12,    6,    0,    0,
    0,    7,    8,    0,    9,   10,   11,    0,   12,    0,
    0,    0,  105,    0,   13,   14,   15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   54,    0,    0,    0,    0,    0,    0,    0,  105,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   43,   41,   45,   45,   59,   41,   59,   58,
   41,   59,   40,   59,   35,   39,   86,   45,   17,   60,
   61,   62,    0,   45,   58,   18,   56,   58,  177,   27,
  179,   40,   38,   44,   44,  257,  258,   41,   60,   61,
   62,   41,   42,   43,    0,   45,  125,   47,   59,   59,
   56,  258,   42,  202,   78,  258,  257,   47,  261,   59,
   60,   61,   62,   41,  271,   43,  257,   45,    0,   62,
   59,   69,   70,   43,  144,   45,   59,  107,   58,   59,
   86,   59,   60,   61,   62,   41,  257,   43,  259,   45,
    0,   59,  263,  276,  277,  266,   41,  268,   43,  270,
   45,  257,  258,   59,   60,   61,   62,  257,  258,   41,
  134,   43,    0,   45,   59,   41,   41,   43,   43,   45,
   45,  119,   93,   94,   59,  125,  125,   59,   60,   61,
   62,   40,  153,  258,   41,    0,   43,   40,   45,   59,
   95,   96,   41,  167,   40,   59,   41,  125,   41,   59,
   45,   40,   45,   44,  257,   41,  123,    0,  260,  183,
  260,   45,   59,  169,   41,   59,  164,   59,  166,  125,
  269,   59,  165,  172,   58,  258,  257,  257,  257,    0,
  259,  205,   44,   58,  263,  264,  265,  266,  267,  268,
  123,  270,  123,  125,   59,  262,   44,  276,  277,  278,
  193,    0,   44,   41,  210,  258,  257,  261,  125,   59,
  257,  257,  258,  258,   59,  125,   59,  257,  258,  123,
  268,    0,  268,  257,   59,    0,  257,  276,  277,  257,
  258,  272,  273,  274,   41,  257,  258,  125,   59,   41,
  279,  279,  276,  277,  123,  276,  277,    0,  257,  258,
  272,  273,  274,  257,  258,   41,   41,  257,  125,  259,
  125,   62,  185,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  272,  273,  274,  262,  276,  277,  278,  257,
  262,  259,  125,  114,   40,  263,  264,  265,  266,  267,
  268,  269,  270,    0,  272,  273,  274,   39,  276,  277,
  278,  257,   -1,  259,  125,  133,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,    0,  272,  273,  274,   -1,
  276,  277,  278,   -1,   -1,  257,  125,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
  272,  273,  274,   -1,  276,  277,  278,  257,  258,  259,
  125,  261,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  257,  258,  257,  258,  276,  277,  278,  257,
   -1,  259,  125,  257,  258,  263,  264,  265,  266,  267,
  268,  269,  270,    0,  268,   -1,   -1,  271,  276,  277,
  278,   -1,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,    0,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,    0,   -1,
   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,
  125,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
    0,   -1,   -1,   -1,  123,  276,  277,  278,  257,   -1,
  259,  107,  108,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,  125,   -1,   -1,   -1,   -1,  276,  277,  278,
   -1,   -1,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,  143,  144,  145,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,  155,
  263,  264,  265,  266,  267,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,   -1,   -1,  257,   -1,
  259,  125,  178,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,  125,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,  125,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,  257,   -1,
  259,  276,  277,  278,  263,  264,   -1,  266,  267,  268,
  125,  270,   -1,   -1,  257,   -1,  259,  276,  277,  278,
  263,   -1,   -1,  266,   -1,  268,  125,  270,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,   -1,  123,  123,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  125,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  125,   -1,   -1,
   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,  125,
  125,   -1,   11,   -1,  276,  277,  278,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,   -1,
  270,  125,   -1,   -1,   -1,   -1,  276,  277,  278,   38,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,   -1,  270,   55,   56,  257,   -1,
  259,  276,  277,  278,  263,  264,   -1,  266,  267,  268,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   85,   86,  257,  257,
  259,  259,   -1,   -1,  263,  263,   -1,  266,  266,  268,
  268,  270,  270,   -1,  257,   -1,  259,   -1,  107,   -1,
  263,   -1,   -1,  266,   -1,  268,  269,  270,   -1,  257,
   -1,  259,   -1,   -1,   -1,  263,   -1,   -1,  266,   -1,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  257,  259,  259,  144,   -1,  263,  263,   -1,
  266,  266,  268,  268,  270,  270,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
  169,   -1,  266,   -1,  268,  257,  270,  259,   -1,   -1,
   -1,  263,  264,   -1,  266,  267,  268,   -1,  270,   -1,
   -1,   -1,  191,   -1,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  210,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  218,
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
"MAYORIGUAL","DISTINTO","COMENTARIO","I16","F32","CONST","CADENA",
};
final static String yyrule[] = {
"$accept : program",
"program : nombre_programa inicio_programa bloque fin_programa",
"program : nombre_programa bloque fin_programa",
"program : nombre_programa inicio_programa bloque",
"inicio_programa : '{'",
"fin_programa : '}'",
"nombre_programa : ID",
"nombre_programa : CTE",
"bloque : bloque sentencia",
"bloque : sentencia",
"sentencia : sentencia_ejecutable",
"sentencia : sentencia_declarativa",
"sentencia_declarativa : declaracion_variables",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_constantes",
"sentencia_declarativa : estruct_when",
"declaracion_variables : tipo lista_variables ';'",
"lista_variables : ID",
"lista_variables : lista_variables ',' ID",
"tipo : I16",
"tipo : F32",
"declaracion_funcion : header_funcion complete_header_funcion cuerpo_funcion",
"header_funcion : FUN ID '('",
"header_funcion : FUN '('",
"header_funcion : FUN ID",
"header_funcion : FUN CTE '('",
"complete_header_funcion : lista_parametros ')' ':' tipo",
"complete_header_funcion : ')' ':' tipo",
"complete_header_funcion : ':' tipo",
"complete_header_funcion : ')' tipo",
"complete_header_funcion : ')' ':'",
"lista_parametros : tipo ID ',' tipo ID",
"lista_parametros : tipo ID",
"lista_parametros : ID",
"lista_parametros : ID ',' ID",
"cuerpo_funcion : inicio_funcion bloque retorno_funcion fin_funcion",
"cuerpo_funcion : inicio_funcion bloque fin_funcion",
"inicio_funcion : '{'",
"fin_funcion : '}'",
"retorno_funcion : RETURN '(' expresion_aritmetica ')' ';'",
"retorno_funcion : RETURN expresion_aritmetica ')' ';'",
"retorno_funcion : RETURN '(' expresion_aritmetica ';'",
"retorno_funcion : RETURN '(' expresion_aritmetica ')'",
"retorno_funcion : RETURN '(' ')' ';'",
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
"factor : ID '(' ')'",
"lista_inv_func : ID ',' ID",
"lista_inv_func : ID ',' CTE",
"lista_inv_func : CTE ',' CTE",
"lista_inv_func : CTE ',' ID",
"lista_inv_func : CTE",
"lista_inv_func : ID",
"declaracion_constantes : CONST list_constantes ';'",
"declaracion_constantes : CONST list_constantes",
"list_constantes : list_constantes ',' asignacion_constante",
"list_constantes : asignacion_constante",
"asignacion_constante : ID ASIG CTE",
"asignacion_constante : ID CTE",
"asignacion : ID ASIG expresion_aritmetica",
"asignacion : ID expresion_aritmetica",
"sentencia_ejecutable : ejecutable",
"sentencia_ejecutable : con_etiqueta",
"ejecutable : asignacion ';'",
"ejecutable : asignacion",
"ejecutable : seleccion ';'",
"ejecutable : seleccion",
"ejecutable : impresion ';'",
"ejecutable : impresion",
"ejecutable : estruct_do_until ';'",
"ejecutable : estruct_do_until",
"con_etiqueta : BREAK ';'",
"con_etiqueta : BREAK",
"con_etiqueta : BREAK CTE ';'",
"con_etiqueta : BREAK CTE",
"con_etiqueta : CONTINUE ';'",
"con_etiqueta : CONTINUE",
"con_etiqueta : CONTINUE ':' ID ';'",
"con_etiqueta : CONTINUE ':' ID",
"con_etiqueta : CONTINUE ':' ';'",
"con_etiqueta : ID ':' estruct_do_until ';'",
"con_etiqueta : ID ':' estruct_do_until",
"con_etiqueta : ID ':' ';'",
"con_etiqueta : ID ASIG sentencia_ctr_expr ';'",
"con_etiqueta : ID ASIG sentencia_ctr_expr",
"con_etiqueta : ID sentencia_ctr_expr ';'",
"con_etiqueta : ID ASIG ';'",
"seleccion : inicio_if condicion_if cuerpo_if END_IF",
"inicio_if : IF",
"condicion_if : condicion",
"condicion : '(' expresion_aritmetica operador expresion_aritmetica ')'",
"condicion : '(' operador expresion_aritmetica ')'",
"condicion : '(' expresion_aritmetica operador ')'",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : '='",
"operador : DISTINTO",
"cuerpo_if : THEN cuerpo_then ELSE cuerpo_else",
"cuerpo_if : THEN cuerpo_then",
"cuerpo_if : THEN cuerpo_then ELSE",
"cuerpo_then : '{' lista_sentencias_ejecutables '}'",
"cuerpo_else : '{' lista_sentencias_ejecutables '}'",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"estruct_when : inicio_when condicion_when THEN '{' bloque '}'",
"inicio_when : WHEN",
"condicion_when : condicion",
"impresion : OUT '(' CADENA ')'",
"impresion : OUT '(' CADENA",
"impresion : OUT CADENA ')'",
"impresion : OUT '(' ')'",
"estruct_do_until : DO inicion_estruct_do_until lista_sentencias_ejecutables fin_estruct_do_until until_condicion",
"estruct_do_until : DO lista_sentencias_ejecutables fin_estruct_do_until until_condicion",
"estruct_do_until : DO inicion_estruct_do_until lista_sentencias_ejecutables until_condicion",
"estruct_do_until : DO inicion_estruct_do_until fin_estruct_do_until until_condicion",
"inicion_estruct_do_until : '{'",
"fin_estruct_do_until : '}'",
"until_condicion : UNTIL condicion",
"until_condicion : UNTIL",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion else_until",
"sentencia_ctr_expr : DO lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion else_until",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables until_condicion else_until",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr fin_sentencia_ctr_expr until_condicion else_until",
"inicio_sentencia_ctr_expr : '{'",
"fin_sentencia_ctr_expr : '}'",
"else_until : ELSE CTE",
"else_until : ELSE",
"else_until : CTE",
};

//#line 255 "Gramatica.y"

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
//#line 699 "Parser.java"
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
//#line 17 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un {");}
break;
case 3:
//#line 18 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un }");}
break;
case 4:
//#line 21 "Gramatica.y"
{Ambito.concatenarAmbito("main");}
break;
case 5:
//#line 24 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 7:
//#line 28 "Gramatica.y"
{erroresSintacticos.add("El nombre del programa no puede ser una constante");}
break;
case 17:
//#line 48 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 18:
//#line 49 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 19:
//#line 52 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 20:
//#line 53 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 21:
//#line 56 "Gramatica.y"
{setTipo(funcionAux, val_peek(2).sval); Ambito.removeAmbito();}
break;
case 22:
//#line 59 "Gramatica.y"
{ambitoAux = val_peek(1).sval; setTipo(val_peek(1).sval); setUso(val_peek(1).sval, "funcion"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); idAux = val_peek(1).sval; yyval.sval = val_peek(1).sval; Ambito.concatenarAmbito(ambitoAux);}
break;
case 23:
//#line 60 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un identificador de la funcion");}
break;
case 24:
//#line 61 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 25:
//#line 62 "Gramatica.y"
{erroresSintacticos.add("No se puede declarar una funcion con una constante como nombre");}
break;
case 26:
//#line 65 "Gramatica.y"
{funcionAux = val_peek(0).sval;}
break;
case 27:
//#line 66 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 0); funcionAux = val_peek(0).sval;}
break;
case 28:
//#line 67 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 29:
//#line 68 "Gramatica.y"
{erroresSintacticos.add("Falta un :");}
break;
case 30:
//#line 69 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo de retorno");}
break;
case 31:
//#line 72 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 2); setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "ParametroFuncion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "Nombre_Parametro_Funcion"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 32:
//#line 73 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 1); setTipo(val_peek(1).sval,val_peek(0).sval);setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 33:
//#line 74 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
break;
case 34:
//#line 75 "Gramatica.y"
{erroresSintacticos.add("Los identificadores deben tener un tipo");}
break;
case 36:
//#line 79 "Gramatica.y"
{erroresSintacticos.add("La funcion debe retornar un valor");}
break;
case 39:
//#line 88 "Gramatica.y"
{}
break;
case 40:
//#line 89 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 41:
//#line 90 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 42:
//#line 91 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 43:
//#line 92 "Gramatica.y"
{erroresSintacticos.add("Falta un valor que devolver");}
break;
case 44:
//#line 95 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval,"+"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 45:
//#line 96 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "-"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 47:
//#line 100 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "*"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 48:
//#line 101 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "/"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 50:
//#line 105 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 107 "Gramatica.y"
{yyval.sval = "-" + val_peek(0).sval;}
break;
case 53:
//#line 108 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(3).sval); comprobarAmbito(val_peek(3).sval); val_peek(2).sval = val_peek(3).sval; val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); if (val_peek(3).sval == null) chequearParametros(val_peek(2).sval, val_peek(1).ival); else chequearParametros(val_peek(3).sval, val_peek(1).ival); yyval.sval = val_peek(3).sval;}
break;
case 54:
//#line 109 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); comprobarAmbito(val_peek(2).sval); val_peek(1).sval = val_peek(2).sval; val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); if (val_peek(2).sval == null) chequearParametros(val_peek(1).sval, 0); else chequearParametros(val_peek(2).sval, 0); yyval.sval = val_peek(2).sval;}
break;
case 55:
//#line 112 "Gramatica.y"
{yyval.ival = 2;}
break;
case 56:
//#line 113 "Gramatica.y"
{yyval.ival = 2;}
break;
case 57:
//#line 114 "Gramatica.y"
{yyval.ival = 2;}
break;
case 58:
//#line 115 "Gramatica.y"
{yyval.ival = 2;}
break;
case 59:
//#line 116 "Gramatica.y"
{yyval.ival = 1;}
break;
case 60:
//#line 117 "Gramatica.y"
{yyval.ival = 1;}
break;
case 62:
//#line 121 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 65:
//#line 128 "Gramatica.y"
{Atributo aux = TablaSimbolos.obtenerSimbolo(val_peek(0).sval); setTipo(aux.getTipo(),val_peek(2).sval); val_peek(2).sval = TablaSimbolos.modificarNombre(val_peek(2).sval);  TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 66:
//#line 129 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 67:
//#line 132 "Gramatica.y"
{if (val_peek(0).sval == null) break; comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); verificarTipos(val_peek(2).sval,val_peek(0).sval, "=:"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 68:
//#line 133 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 72:
//#line 141 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 74:
//#line 143 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 76:
//#line 145 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 78:
//#line 147 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 79:
//#line 150 "Gramatica.y"
{TercetoManager.breakDoUntil();}
break;
case 80:
//#line 151 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 82:
//#line 153 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 83:
//#line 154 "Gramatica.y"
{TercetoManager.continueDoUntil();}
break;
case 84:
//#line 155 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 86:
//#line 157 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 87:
//#line 158 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 88:
//#line 159 "Gramatica.y"
{TablaSimbolos.agregarSimbolo(val_peek(3).sval, 257, "", AnalizadorLexico.getLine()); setUso(val_peek(3).sval,"etiqueta"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval);}
break;
case 89:
//#line 160 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 90:
//#line 161 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 92:
//#line 163 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 93:
//#line 164 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 94:
//#line 165 "Gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 95:
//#line 168 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 97:
//#line 174 "Gramatica.y"
{TercetoManager.add_seleccion_cond();}
break;
case 98:
//#line 177 "Gramatica.y"
{verificarTipos(val_peek(3).sval, val_peek(1).sval, val_peek(2).sval); TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 99:
//#line 178 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 100:
//#line 179 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 101:
//#line 182 "Gramatica.y"
{yyval.sval = "<";}
break;
case 102:
//#line 183 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 103:
//#line 184 "Gramatica.y"
{yyval.sval = ">";}
break;
case 104:
//#line 185 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 105:
//#line 186 "Gramatica.y"
{yyval.sval = "=";}
break;
case 106:
//#line 187 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 109:
//#line 192 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 110:
//#line 195 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 114:
//#line 205 "Gramatica.y"
{TercetoManager.add_iter_when();}
break;
case 115:
//#line 208 "Gramatica.y"
{TercetoManager.add_inicio_when();}
break;
case 116:
//#line 211 "Gramatica.y"
{TercetoManager.add_cond_when();}
break;
case 117:
//#line 214 "Gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 118:
//#line 215 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 119:
//#line 216 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 120:
//#line 217 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 121:
//#line 220 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 122:
//#line 221 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 123:
//#line 222 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 124:
//#line 223 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 125:
//#line 226 "Gramatica.y"
{Ambito.concatenarAmbito("doUntil"); TercetoManager.add_inicio_do_until();}
break;
case 126:
//#line 229 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 128:
//#line 233 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 130:
//#line 237 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 131:
//#line 238 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 132:
//#line 239 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 133:
//#line 243 "Gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 134:
//#line 246 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 136:
//#line 250 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 137:
//#line 251 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1244 "Parser.java"
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
