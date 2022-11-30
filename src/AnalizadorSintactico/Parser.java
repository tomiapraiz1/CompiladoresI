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
   17,   17,   17,   17,   16,   16,   16,   18,   20,   19,
   19,   19,   19,   19,   21,   21,   21,   22,   22,   22,
   23,   23,   23,   23,   23,   24,   24,   24,   24,   24,
   24,   10,   10,   25,   25,   26,   26,   27,   27,    6,
    6,   28,   28,   28,   28,   28,   28,   28,   28,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   34,   33,   30,   36,   37,
   39,   39,   39,   40,   40,   40,   40,   40,   40,   38,
   38,   38,   41,   42,   43,   43,   11,   44,   45,   31,
   31,   31,   31,   32,   32,   32,   32,   46,   47,   48,
   48,   49,   49,   35,   35,   35,   35,   50,   51,   52,
   52,   52,
};
final static short yylen[] = {                            2,
    4,    3,    3,    1,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    3,    1,    1,
    3,    3,    2,    2,    3,    4,    3,    2,    2,    2,
    5,    2,    1,    3,    4,    3,    3,    1,    1,    5,
    4,    4,    4,    4,    3,    3,    1,    3,    3,    1,
    1,    1,    2,    4,    3,    3,    3,    3,    3,    1,
    1,    3,    2,    3,    1,    3,    2,    3,    2,    1,
    1,    2,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    3,    2,    2,    1,    4,    3,    3,    3,    2,
    2,    3,    2,    3,    2,    2,    2,    4,    1,    1,
    5,    4,    4,    1,    1,    1,    1,    1,    1,    4,
    2,    3,    3,    3,    2,    1,    6,    1,    1,    4,
    3,    3,    3,    5,    4,    4,    4,    1,    1,    2,
    1,    2,    1,    6,    5,    5,    5,    1,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,   99,    0,    0,    0,  118,
    0,    0,   19,   20,    0,    4,    0,    0,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,   70,   71,
    0,    0,    0,    0,    0,    0,    0,    0,   52,    0,
    0,   97,    0,    0,    0,   50,    0,    0,    0,    0,
    0,   23,    0,   80,  128,  116,    0,    0,   84,    0,
    0,    0,   65,    0,    5,    2,    8,   17,    0,    0,
    0,    0,    0,    0,    0,   72,   74,   76,   78,   91,
    0,   95,    0,    0,    0,  100,  119,    0,    0,  138,
    0,    0,    0,   53,    0,    0,    0,    0,   94,  122,
    0,  123,   22,   25,   82,  129,  115,    0,    0,    0,
    0,   88,   67,    0,   62,    0,    1,   16,    0,    0,
    0,   29,   28,    0,   38,   21,    0,    0,   89,   92,
  105,  107,  109,  104,  106,  108,    0,    0,    0,    0,
    0,    0,    0,   55,    0,  139,    0,    0,    0,    0,
    0,   48,   49,  120,    0,  125,    0,  126,  127,   86,
   66,   64,   18,   34,   27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   98,    0,    0,    0,   54,    0,
    0,    0,    0,    0,  130,  124,    0,    0,    0,   39,
    0,   37,   36,   26,  103,    0,  102,    0,    0,    0,
   56,   57,   59,   58,  132,  142,    0,  135,  136,    0,
  137,   31,    0,    0,    0,   35,  101,  113,    0,  110,
  117,  140,  134,   44,   42,    0,   41,    0,   40,  114,
};
final static short yydgoto[] = {                          3,
    4,   17,   18,   66,   19,   20,   21,   22,   23,   24,
   25,   26,   69,   27,   74,  126,   75,  127,  169,  192,
   44,   45,   46,  145,   62,   63,   28,   29,   30,   31,
   32,   33,   34,   35,   47,   36,   85,  140,   86,  138,
  174,  220,   57,   37,   88,   58,  108,  156,  181,   92,
  147,  208,
};
final static short yysindex[] = {                      -136,
    0,    0,    0,  -67,  -45,    0,  -38,   -3,  -52,    0,
 -104,   65,    0,    0, -240,    0,  610,  465,    0,    0,
    0,    0,    0,    0,    0, -222,  -32,  -10,    0,    0,
   11,   17,   36,  -47,  -25,   71,   71,   76,    0,  510,
  -39,    0, -149,   58,   25,    0,   59,  101,  -37,  104,
  109,    0,   87,    0,    0,    0,  552,  552,    0,  -43,
 -184,   -6,    0,  465,    0,    0,    0,    0,   21,  108,
  -48,  -98, -100,   38,  122,    0,    0,    0,    0,    0,
  110,    0,  112,  -21,  -95,    0,    0,  -93,   67,    0,
  566,  566,   58,    0,  -39,  -39,  -39,  -39,    0,    0,
  132,    0,    0,    0,    0,    0,    0,  -84,  520,  -84,
  117,    0,    0,  -70,    0, -240,    0,    0,  -68,  -66,
  -98,    0,    0,  154,    0,    0,  594,  146,    0,    0,
    0,    0,    0,    0,    0,    0,  -40,  -39,   82,  -54,
   97,  171,  178,    0,  209,    0,    3,  534,    3,   25,
   25,    0,    0,    0,   71,    0,  -84,    0,    0,    0,
    0,    0,    0,    0,    0,  -98,   -9,  442,  134,  -98,
  -27,   53, -125,   -4,    0,  610, -118,  -76,    0,   71,
 -180, -180,    3, -180,    0,    0,    6,  106,   74,    0,
  134,    0,    0,    0,    0,   92,    0,  567,  153,  487,
    0,    0,    0,    0,    0,    0,   27,    0,    0, -180,
    0,    0,  235,   34,  239,    0,    0,    0, -125,    0,
    0,    0,    0,    0,    0,  244,    0,  586,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  202,    0,
    0,  224,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  240,    0,    0,
  262,  284,  306,    0,    0,    0,    0,    1,    0,    0,
  -12,    0,    0,   91,   23,    0,    0,    0,    0,  -30,
    0,    0,  329,    0,    0,    0,    0,    0,    0,    0,
    0,  419,    0,  307,    0,    0,    0,    0,    0,  275,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  351,    0,  373,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  113,    0,    0,    0,    0,    0,    0,    0,
  136,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  397,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  197,    0,    0,  289,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  290,  299,    0,    0,    0,    0,    0,    0,   45,
   69,    0,    0,    0,  158,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,    0,    0,    0,    0,    0, -161,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  100,    0,
    0,    0,    0,    0,    0,    0,  180,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  219,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -2,  302,    7,  612,    0,    0,    0,    0,
    0,  532,    0,    0,    0,    0,    0,    0,  185, -137,
  -11,   88,   89,    0,    0,  255,    0,    0,    0,    0,
    0,  340,    0,    0,  349,    0,    0,    0,   13,  238,
    0,    0,   62,    0,    0,    0,  -50,  -58,  -91,    0,
  -59, -155,
};
final static int YYTABLESIZE=888;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   51,   49,   95,  102,   96,   43,   54,  110,   71,  121,
   24,   80,   42,  195,   64,  112,   61,   43,   55,  134,
  136,  135,   47,   43,   67,   72,  209,   24,  211,   93,
  188,  193,  149,   82,   68,   43,   52,  116,  134,  136,
  135,   51,   51,   51,   45,   51,   96,   51,   76,   87,
  158,  159,  115,  216,  223,   16,  182,  184,  157,   51,
   51,   51,   51,   47,  119,   47,   97,   47,   46,   77,
   67,   98,  137,  113,  226,   78,   95,  206,   96,  118,
  207,   47,   47,   47,   47,   45,  114,   45,  183,   45,
   69,  210,  225,  197,   79,   95,  133,   96,  186,  133,
   95,   91,   96,   45,   45,   45,   45,  144,   94,   46,
   84,   46,   68,   46,  215,   89,   95,   99,   96,  109,
    1,    2,   60,   59,  168,   51,  172,   46,   46,   46,
   46,    5,  217,    6,   95,  121,   96,    7,  201,  202,
    9,  100,   11,  103,   12,  105,  213,   47,  104,   69,
   43,  120,    5,  148,    6,  189,  124,  131,    7,  196,
  125,    9,  128,   11,  139,   12,  141,  185,  129,   45,
  130,   68,  154,  200,   67,  160,  214,   13,   14,  141,
  203,  204,  150,  151,  155,  152,  153,  161,  163,    5,
  164,    6,  205,   46,  121,    7,    8,  166,    9,   10,
   11,   81,   12,  170,  173,   53,   67,  175,   13,   14,
   15,   38,   39,  111,  177,   69,  131,   38,   39,  176,
   11,  178,   40,   85,   70,   41,   24,   13,   14,   38,
   39,  131,  132,  133,  198,   38,   39,   68,  141,   73,
   48,  101,   40,   13,   14,   24,   24,   38,   39,  179,
  131,  132,  133,   50,   51,   96,  199,   51,  190,   51,
  121,   75,  212,   51,   51,   51,   51,   51,   51,   51,
   51,  180,   51,   51,   51,  219,   51,   51,   51,   47,
  228,   47,  131,   77,  222,   47,   47,   47,   47,   47,
   47,   47,   47,  224,   47,   47,   47,  227,   47,   47,
   47,   45,  229,   45,  141,   79,    3,   45,   45,   45,
   45,   45,   45,   45,   45,   33,   45,   45,   45,   30,
   45,   45,   45,  142,  143,   46,   81,   46,   83,   32,
   61,   46,   46,   46,   46,   46,   46,   46,   46,   60,
   46,   46,   46,   43,   46,   46,   46,   69,   85,   69,
   90,  111,  191,   69,   69,   69,   69,   69,   69,   69,
   69,  112,   38,   39,   73,  117,   69,   69,   69,   68,
  162,   68,   93,   81,  171,   68,   68,   68,   68,   68,
   68,   68,   68,   83,    0,    0,   75,    0,   68,   68,
   68,    0,  121,    0,  121,    0,   87,    0,  121,  121,
  121,  121,  121,  121,  121,  121,    0,    0,   77,    0,
    0,  121,  121,  121,  131,    0,  131,    0,   63,    0,
  131,  131,  131,  131,  131,  131,  131,  131,    0,    0,
   79,    0,    0,  131,  131,  131,  141,    0,  141,    0,
    0,    0,  141,  141,  141,  141,  141,  141,  141,  141,
    0,    0,    0,   83,    0,  141,  141,  141,   81,    0,
   81,    0,    0,    0,   81,   81,   81,   81,   81,   81,
   81,   81,    0,    0,    0,   90,    0,   81,   81,   81,
   85,    0,   85,    0,    0,    0,   85,   85,   85,   85,
   85,   85,   85,   85,    0,    0,   73,   93,   73,   85,
   85,   85,   73,   73,   73,   73,   73,   73,   73,   73,
    0,    0,    0,    0,    0,   73,   73,   73,   75,    0,
   75,   87,    0,    0,   75,   75,   75,   75,   75,   75,
   75,   75,    0,    0,    0,    0,    0,   75,   75,   75,
   77,    0,   77,   63,    0,    0,   77,   77,   77,   77,
   77,   77,   77,   77,    0,    0,    0,    0,   73,   77,
   77,   77,   79,    0,   79,    0,  190,    0,   79,   79,
   79,   79,   79,   79,   79,   79,    0,    0,    0,    0,
    0,   79,   79,   79,    0,   83,    0,   83,    0,   65,
    0,   83,   83,   83,   83,   83,   83,   83,   83,    0,
    0,    0,  122,  123,   83,   83,   83,   90,    0,   90,
    0,  221,    0,   90,   90,   90,   90,   90,   90,   90,
   90,    0,   56,    0,    0,    0,   90,   90,   90,   93,
    0,   93,   90,    0,    0,   93,   93,   93,   93,   93,
   93,   93,   93,    0,  106,    0,    0,    0,   93,   93,
   93,   56,  165,   87,    0,   87,    0,    0,  146,   87,
   87,   87,   87,   87,   87,   87,   87,    0,  107,   56,
    0,    0,   87,   87,   87,   63,  106,   63,    0,    0,
    0,   63,   63,   63,   63,   63,   63,    0,   63,    0,
  146,  218,    0,    0,   63,   63,   63,  187,    5,    0,
    6,  194,  107,   56,    7,    8,  167,    9,   10,   11,
  230,   12,    0,    0,    0,    0,    0,   13,   14,   15,
  107,    5,    0,    6,    0,    0,    0,    7,    8,    0,
    9,   10,   11,    0,   12,    0,    0,    0,    0,    0,
   13,   14,   15,    5,    0,    6,    0,    0,    0,    7,
    8,    0,    9,   10,   11,    0,   12,    0,    0,  107,
    0,    0,   13,   14,   15,    0,    5,    0,    6,    0,
    0,    0,    7,    0,    0,    9,    5,   11,    6,   12,
    0,    0,    7,    0,   56,    9,    0,   11,  155,   12,
    5,    0,    6,    0,    0,    0,    7,    0,    0,    9,
    0,   11,  180,   12,    0,    0,    0,    0,    5,  107,
    6,    0,    0,    0,    7,    0,    0,    9,    0,   11,
    0,   12,    5,    5,    6,    6,    0,    0,    7,    7,
   56,    9,    9,   11,   11,   12,   12,    0,    0,  107,
    0,    0,    5,    0,    6,    0,    0,    0,    7,    0,
    5,    9,    6,   11,    0,   12,    7,    8,  167,    9,
   10,   11,    0,   12,    0,    0,    5,    0,    6,   13,
   14,   15,    7,    8,    0,    9,   10,   11,    0,   12,
    0,    0,    0,    0,    0,   13,   14,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   43,   41,   45,   45,   59,   58,   41,   58,
   41,   59,   58,   41,   17,   59,  257,   45,  123,   60,
   61,   62,    0,   45,   18,   58,  182,   58,  184,   41,
   40,  169,   92,   59,  257,   45,   40,   44,   60,   61,
   62,   41,   42,   43,    0,   45,   59,   47,   59,   37,
  109,  110,   59,  191,  210,  123,  148,  149,  109,   59,
   60,   61,   62,   41,   44,   43,   42,   45,    0,   59,
   64,   47,   84,  258,   41,   59,   43,  258,   45,   59,
  261,   59,   60,   61,   62,   41,  271,   43,  148,   45,
    0,  183,   59,   41,   59,   43,  258,   45,  157,  261,
   43,   40,   45,   59,   60,   61,   62,   41,  258,   41,
   40,   43,    0,   45,   41,   40,   43,   59,   45,   58,
  257,  258,   58,   59,  127,  125,  138,   59,   60,   61,
   62,  257,   41,  259,   43,    0,   45,  263,  257,  258,
  266,   41,  268,   40,  270,   59,   41,  125,   40,   59,
   45,   44,  257,   92,  259,  167,  257,    0,  263,  171,
  123,  266,   41,  268,  260,  270,  260,  155,   59,  125,
   59,   59,   41,  176,  168,   59,  188,  276,  277,    0,
  257,  258,   95,   96,  269,   97,   98,  258,  257,  257,
  257,  259,  180,  125,   59,  263,  264,   44,  266,  267,
  268,    0,  270,   58,  123,  258,  200,  262,  276,  277,
  278,  257,  258,  257,   44,  125,   59,  257,  258,  123,
  268,   44,  268,    0,  257,  271,  257,  276,  277,  257,
  258,  272,  273,  274,  173,  257,  258,  125,   59,    0,
  279,  279,  268,  276,  277,  276,  277,  257,  258,   41,
  272,  273,  274,  257,  258,  268,  261,  257,  125,  259,
  125,    0,  257,  263,  264,  265,  266,  267,  268,  269,
  270,  269,  272,  273,  274,  123,  276,  277,  278,  257,
  219,  259,  125,    0,  258,  263,  264,  265,  266,  267,
  268,  269,  270,   59,  272,  273,  274,   59,  276,  277,
  278,  257,   59,  259,  125,    0,    0,  263,  264,  265,
  266,  267,  268,  269,  270,   41,  272,  273,  274,  123,
  276,  277,  278,  257,  258,  257,  125,  259,    0,   41,
   41,  263,  264,  265,  266,  267,  268,  269,  270,   41,
  272,  273,  274,  125,  276,  277,  278,  257,  125,  259,
    0,  262,  168,  263,  264,  265,  266,  267,  268,  269,
  270,  262,  257,  258,  125,   64,  276,  277,  278,  257,
  116,  259,    0,   34,  137,  263,  264,  265,  266,  267,
  268,  269,  270,   35,   -1,   -1,  125,   -1,  276,  277,
  278,   -1,  257,   -1,  259,   -1,    0,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,  125,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,    0,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,   -1,   -1,
  125,   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
   -1,   -1,   -1,  125,   -1,  276,  277,  278,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,  125,   -1,  276,  277,  278,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,  257,  125,  259,  276,
  277,  278,  263,  264,  265,  266,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,  125,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,  125,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   27,  276,
  277,  278,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,   -1,  257,   -1,  259,   -1,  125,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
   -1,   -1,   71,   72,  276,  277,  278,  257,   -1,  259,
   -1,  125,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,   -1,   11,   -1,   -1,   -1,  276,  277,  278,  257,
   -1,  259,  123,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,   -1,  125,   -1,   -1,   -1,  276,  277,
  278,   40,  121,  257,   -1,  259,   -1,   -1,  125,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,   57,   58,
   -1,   -1,  276,  277,  278,  257,  125,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,   -1,  270,   -1,
  125,  125,   -1,   -1,  276,  277,  278,  166,  257,   -1,
  259,  170,   91,   92,  263,  264,  265,  266,  267,  268,
  125,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  109,  257,   -1,  259,   -1,   -1,   -1,  263,  264,   -1,
  266,  267,  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,   -1,  266,  267,  268,   -1,  270,   -1,   -1,  148,
   -1,   -1,  276,  277,  278,   -1,  257,   -1,  259,   -1,
   -1,   -1,  263,   -1,   -1,  266,  257,  268,  259,  270,
   -1,   -1,  263,   -1,  173,  266,   -1,  268,  269,  270,
  257,   -1,  259,   -1,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,  269,  270,   -1,   -1,   -1,   -1,  257,  198,
  259,   -1,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,
   -1,  270,  257,  257,  259,  259,   -1,   -1,  263,  263,
  219,  266,  266,  268,  268,  270,  270,   -1,   -1,  228,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,   -1,
  257,  266,  259,  268,   -1,  270,  263,  264,  265,  266,
  267,  268,   -1,  270,   -1,   -1,  257,   -1,  259,  276,
  277,  278,  263,  264,   -1,  266,  267,  268,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
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
"cuerpo_funcion : inicio_funcion retorno_funcion fin_funcion",
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
"con_etiqueta : inicio_id_estruct estruct_do_until ';'",
"con_etiqueta : inicio_id_estruct estruct_do_until",
"con_etiqueta : inicio_id_estruct ';'",
"con_etiqueta : inicio_id_asignacion sentencia_ctr_expr ';'",
"con_etiqueta : inicio_id_asignacion sentencia_ctr_expr",
"con_etiqueta : ID sentencia_ctr_expr ';'",
"con_etiqueta : inicio_id_asignacion ';'",
"inicio_id_asignacion : ID ASIG",
"inicio_id_estruct : ID ':'",
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
"until_condicion_asig : UNTIL condicion",
"until_condicion_asig : UNTIL",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion_asig else_until",
"sentencia_ctr_expr : DO lista_sentencias_ejecutables fin_sentencia_ctr_expr until_condicion_asig else_until",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr lista_sentencias_ejecutables until_condicion_asig else_until",
"sentencia_ctr_expr : DO inicio_sentencia_ctr_expr fin_sentencia_ctr_expr until_condicion_asig else_until",
"inicio_sentencia_ctr_expr : '{'",
"fin_sentencia_ctr_expr : '}'",
"else_until : ELSE CTE",
"else_until : ELSE",
"else_until : CTE",
};

//#line 266 "Gramatica.y"

public String tipoAux = "";
public String ambitoAux = "";
public String funcionAux = "";
public String idAux = "";
public String parametro1 = "";
public String parametro2 = "";
public static ArrayList<String> erroresSintacticos = new ArrayList<String>();
public static ArrayList<String> erroresLexicos = new ArrayList<String>();
public static ArrayList<String> erroresSemanticos = new ArrayList<String>();

/*public void ChequearRangoNegativo(String numNegativo){
	

}*/

public String getTipoParametro(String p){
	System.out.println(p);
	if (TablaSimbolos.contieneSimbolo(p)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(p);
		return aux.getTipo();
	}

	return "";
}

/*public void verificarTipos(String arg1,String arg2, String operador){
	if (TablaSimbolos.contieneSimbolo(arg1) || TablaSimbolos.contieneSimbolo(arg2)){
		Atributo a1 = TablaSimbolos.obtenerSimbolo(arg1);
		Atributo a2 = TablaSimbolos.obtenerSimbolo(arg2);

		if (a1.getTipo().equals(a2.getTipo()))
			return;
		else{
			TablaTipos.setTipoAbarcativo(a1.getTipo(),a2.getTipo(),operador);
		}
	}

}*/
void chequearTipoParametros(String funcion, String p1, String p2){
	if (TablaSimbolos.contieneSimbolo(funcion)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(funcion);
		System.out.println(p1);
		System.out.println(p2);
		if (aux.getUso().equals("funcion")){
			if (!p1.equals(aux.getTipoP1()) || !p2.equals(aux.getTipoP2()))
				erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": Los tipos de los parametros no coinciden");
		}
	}
}

void chequearParametros(String simbolo, int cantidad){
	if (TablaSimbolos.contieneSimbolo(simbolo)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(simbolo);
		if (aux.getUso().equals("funcion")){
			if (aux.getCantidadParametros() != cantidad)
				erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": No coinciden la cantidad de parametros de '" + Ambito.sinAmbito(simbolo) + "'");
		} else
			erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": '" + simbolo + "' no es una funcion");
	} else
		erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": La funcion '" + simbolo + "' no esta declarada");
}

void contieneEtiqueta(String etiqueta){
	if (TablaSimbolos.contieneSimbolo(etiqueta)){
		if (!TablaSimbolos.obtenerSimbolo(etiqueta).getUso().equals("etiqueta"))
			erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": '" + Ambito.sinAmbito(etiqueta) + "' no es una etiqueta valida");
	} else
		erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": '" + Ambito.sinAmbito(etiqueta) + "' no esta declarada");

}

void esConstante(String simbolo){
	if (TablaSimbolos.contieneSimbolo(simbolo)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(simbolo);
		if (aux.getUso().equals("constante"))
			erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": No se puede asignar un valor a una constante");
	}
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
		erroresSemanticos.add("Error en la linea"+ AnalizadorLexico.getLine()+": '" + simbolo + "' no esta al alcance");
}

void yyerror(String mensaje) {
        // funcion utilizada para imprimir errores que produce yacc
        System.out.println("Error yacc: " + mensaje);
}

public static void printErrores(){
	if (!erroresLexicos.isEmpty()) {
		System.out.println("ERRORES LEXICOS:");
		System.out.println(erroresLexicos);
	} else System.out.println("No hay errores lexicos.");
	if (!erroresSintacticos.isEmpty()) {
		System.out.println("ERRORES SINTACTICOS:");
		System.out.println(erroresSintacticos);
	} else System.out.println("No hay errores sintacticos.");
	if (!erroresSemanticos.isEmpty()) {
		System.out.println("ERRORES SEMANTICOS:");
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
//#line 749 "Parser.java"
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
{ambitoAux = val_peek(1).sval; setTipo(val_peek(1).sval); setUso(val_peek(1).sval, "funcion"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); idAux = val_peek(1).sval; yyval.sval = val_peek(1).sval; TercetoManager.add_funcion(val_peek(1).sval); Ambito.concatenarAmbito(ambitoAux);}
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
{TablaSimbolos.modificarParametros(idAux, 2); TablaSimbolos.modificarTipoParametros(idAux, val_peek(4).sval, val_peek(1).sval); setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "ParametroFuncion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "Nombre_Parametro_Funcion"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 32:
//#line 73 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 1); TablaSimbolos.modificarTipoParametros(idAux, val_peek(1).sval, ""); setTipo(val_peek(1).sval,val_peek(0).sval);setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 33:
//#line 74 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
break;
case 34:
//#line 75 "Gramatica.y"
{erroresSintacticos.add("Los identificadores deben tener un tipo");}
break;
case 37:
//#line 80 "Gramatica.y"
{erroresSintacticos.add("La funcion debe retornar un valor");}
break;
case 40:
//#line 89 "Gramatica.y"
{TercetoManager.add_return_funcion();}
break;
case 41:
//#line 90 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 42:
//#line 91 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 43:
//#line 92 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 44:
//#line 93 "Gramatica.y"
{erroresSintacticos.add("Falta un valor que devolver");}
break;
case 45:
//#line 96 "Gramatica.y"
{/*verificarTipos($1.sval,$3.sval,"+");*/ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 46:
//#line 97 "Gramatica.y"
{/*verificarTipos($1.sval,$3.sval, "-")*/; yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 48:
//#line 101 "Gramatica.y"
{/*verificarTipos($1.sval,$3.sval, "*");*/ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 49:
//#line 102 "Gramatica.y"
{/*verificarTipos($1.sval,$3.sval, "/");*/ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 51:
//#line 106 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 108 "Gramatica.y"
{System.out.println(val_peek(0).sval);/*ChequearRangoNegativo($2.sval)*/;yyval.sval = "-" + val_peek(0).sval;}
break;
case 54:
//#line 109 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(3).sval); comprobarAmbito(val_peek(3).sval); val_peek(2).sval = val_peek(3).sval; val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); if (val_peek(3).sval == null) chequearParametros(val_peek(2).sval, val_peek(1).ival); else chequearParametros(val_peek(3).sval, val_peek(1).ival); yyval.sval = val_peek(3).sval; chequearTipoParametros(val_peek(3).sval, parametro1, parametro2); TercetoManager.llamado_funcion();}
break;
case 55:
//#line 110 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); comprobarAmbito(val_peek(2).sval); val_peek(1).sval = val_peek(2).sval; val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); if (val_peek(2).sval == null) chequearParametros(val_peek(1).sval, 0); else chequearParametros(val_peek(2).sval, 0); yyval.sval = val_peek(2).sval; TercetoManager.llamado_funcion();}
break;
case 56:
//#line 113 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); TablaSimbolos.eliminarSimbolo(val_peek(0).sval);yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval + Ambito.getAmbitoActual()); parametro2 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual());}
break;
case 57:
//#line 114 "Gramatica.y"
{yyval.ival = 2; parametro1 = val_peek(2).sval; parametro2 = val_peek(0).sval;}
break;
case 58:
//#line 115 "Gramatica.y"
{yyval.ival = 2; parametro1 = val_peek(2).sval; parametro2 = val_peek(0).sval;}
break;
case 59:
//#line 116 "Gramatica.y"
{yyval.ival = 2; parametro1 = val_peek(2).sval; parametro2 = val_peek(0).sval;}
break;
case 60:
//#line 117 "Gramatica.y"
{yyval.ival = 1; parametro1 = val_peek(0).sval;}
break;
case 61:
//#line 118 "Gramatica.y"
{yyval.ival = 1; parametro1 = val_peek(0).sval;}
break;
case 63:
//#line 122 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 66:
//#line 129 "Gramatica.y"
{Atributo aux = TablaSimbolos.obtenerSimbolo(val_peek(0).sval); setTipo(aux.getTipo(),val_peek(2).sval); setUso(val_peek(2).sval, "constante"); val_peek(2).sval = TablaSimbolos.modificarNombre(val_peek(2).sval);  TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 67:
//#line 130 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 68:
//#line 133 "Gramatica.y"
{if (val_peek(0).sval == null) break; comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); esConstante(val_peek(2).sval); /*verificarTipos($1.sval,$3.sval, "=:");*/ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 69:
//#line 134 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 73:
//#line 142 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 75:
//#line 144 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 77:
//#line 146 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 79:
//#line 148 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 80:
//#line 151 "Gramatica.y"
{TercetoManager.breakDoUntil();}
break;
case 81:
//#line 152 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 82:
//#line 153 "Gramatica.y"
{TercetoManager.add_break_cte(idAux, val_peek(1).sval);}
break;
case 83:
//#line 154 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 84:
//#line 155 "Gramatica.y"
{TercetoManager.continueDoUntil();}
break;
case 85:
//#line 156 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 86:
//#line 157 "Gramatica.y"
{contieneEtiqueta(val_peek(1).sval+Ambito.getAmbitoActual()); TercetoManager.continueDoUntilEtiqueta(val_peek(1).sval);}
break;
case 87:
//#line 158 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 88:
//#line 159 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 89:
//#line 160 "Gramatica.y"
{}
break;
case 90:
//#line 161 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 91:
//#line 162 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 92:
//#line 163 "Gramatica.y"
{TercetoManager.add_fin_id_asig();}
break;
case 93:
//#line 164 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 94:
//#line 165 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 95:
//#line 166 "Gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 96:
//#line 169 "Gramatica.y"
{idAux = val_peek(1).sval; TercetoManager.add_inicio_id_asig();}
break;
case 97:
//#line 172 "Gramatica.y"
{TablaSimbolos.agregarSimbolo(val_peek(1).sval, 257, "", AnalizadorLexico.getLine()); setUso(val_peek(1).sval,"etiqueta"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); TablaSimbolos.setTercetoSalto(val_peek(1).sval,"[" + TercetoManager.getIndexTerceto() + "]");}
break;
case 98:
//#line 175 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 100:
//#line 181 "Gramatica.y"
{TercetoManager.add_seleccion_cond();}
break;
case 101:
//#line 184 "Gramatica.y"
{/*verificarTipos($2.sval, $4.sval, $3.sval);*/ TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 102:
//#line 185 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 103:
//#line 186 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 104:
//#line 189 "Gramatica.y"
{yyval.sval = "<";}
break;
case 105:
//#line 190 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 106:
//#line 191 "Gramatica.y"
{yyval.sval = ">";}
break;
case 107:
//#line 192 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 108:
//#line 193 "Gramatica.y"
{yyval.sval = "=";}
break;
case 109:
//#line 194 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 112:
//#line 199 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 113:
//#line 202 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 117:
//#line 212 "Gramatica.y"
{TercetoManager.add_iter_when();}
break;
case 119:
//#line 218 "Gramatica.y"
{TercetoManager.add_cond_when();}
break;
case 120:
//#line 221 "Gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 121:
//#line 222 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 122:
//#line 223 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 123:
//#line 224 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 124:
//#line 227 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 125:
//#line 228 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 126:
//#line 229 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 127:
//#line 230 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 128:
//#line 233 "Gramatica.y"
{TercetoManager.add_inicio_do_until();}
break;
case 130:
//#line 239 "Gramatica.y"
{}
break;
case 131:
//#line 240 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 132:
//#line 243 "Gramatica.y"
{TercetoManager.add_condicion_id_asig();}
break;
case 133:
//#line 244 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 135:
//#line 248 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 136:
//#line 249 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 137:
//#line 250 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 138:
//#line 254 "Gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 139:
//#line 257 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 140:
//#line 260 "Gramatica.y"
{TercetoManager.add_else_cte(idAux, val_peek(0).sval);}
break;
case 141:
//#line 261 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 142:
//#line 262 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1322 "Parser.java"
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
