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
    5,    7,    7,    7,    8,   12,   12,   11,   11,    9,
   13,   13,   13,   13,   14,   14,   14,   14,   14,   16,
   16,   16,   16,   15,   15,   17,   19,   18,   18,   18,
   18,   18,   20,   20,   20,   21,   21,   21,   22,   22,
   22,   22,   22,   23,   23,   23,   23,   10,   10,   24,
   24,   25,   25,    6,    6,   26,   26,   26,   26,   26,
   26,   26,   26,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   27,   27,   27,   27,   28,
   28,   28,   28,   32,   32,   32,   34,   34,   34,   34,
   34,   34,   33,   33,   33,   35,   36,   37,   37,   29,
   29,   29,   29,   30,   30,   30,   30,   38,   39,   40,
   40,   31,   31,   31,   31,   41,   42,   43,   43,   43,
};
final static short yylen[] = {                            2,
    4,    3,    3,    1,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    1,    3,    1,    1,    3,
    3,    2,    2,    3,    4,    3,    2,    2,    2,    5,
    2,    1,    3,    4,    3,    1,    1,    5,    4,    4,
    4,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    4,    3,    3,    3,    1,    1,    3,    2,    3,
    1,    3,    2,    1,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    2,    1,    3,    2,    2,    1,    4,
    3,    3,    4,    3,    3,    4,    3,    3,    3,    4,
    3,    3,    3,    5,    4,    4,    1,    1,    1,    1,
    1,    1,    4,    2,    3,    3,    3,    2,    1,    4,
    3,    3,    3,    5,    4,    4,    4,    1,    1,    2,
    1,    6,    5,    5,    5,    1,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   18,   19,    0,    4,    0,    0,    9,   10,   11,
   12,   13,   14,    0,    0,    0,   64,   65,    0,    0,
    0,    0,   50,    0,    0,    0,    0,    0,    0,   48,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   22,
    0,   74,  118,  109,    0,    0,   78,    0,    0,    0,
   61,    0,    5,    2,    8,   16,    0,    0,    0,    0,
    0,    0,    0,   66,   68,   70,   72,    0,  126,    0,
    0,   89,    0,    0,   85,    0,   51,    0,    0,    0,
    0,   88,    0,    0,   98,  100,  102,   97,   99,  101,
    0,    0,   92,    0,   91,  112,    0,  113,   21,   24,
   76,  119,  108,    0,    0,    0,    0,   82,    0,   58,
    0,    1,   15,    0,    0,    0,   28,   27,    0,   36,
   20,    0,    0,   57,   56,   53,    0,  127,    0,    0,
    0,   86,   83,    0,    0,   46,   47,    0,    0,    0,
    0,   90,  110,    0,  115,    0,  116,  117,   80,   60,
   17,   33,   26,    0,    0,    0,    0,   52,    0,    0,
    0,    0,  106,    0,  103,   96,    0,   95,  120,  114,
    0,    0,   37,    0,   35,   25,   54,   55,  130,    0,
  123,  124,    0,  125,    0,   94,   30,    0,    0,   34,
  128,  122,  107,    0,    0,    0,   42,   40,    0,   39,
   38,
};
final static short yydgoto[] = {                          3,
    4,   16,   17,   64,   18,   19,   20,   21,   22,   23,
   24,   67,   25,   72,  131,   73,  132,  184,  185,   38,
   39,   40,  137,   60,   26,   27,   28,   29,   30,   31,
   41,   44,   45,  102,   94,  175,   55,   56,  114,  155,
   81,  139,  191,
};
final static short yysindex[] = {                      -156,
    0,    0,    0,  566,  -43,  -29,  -40,  -24,  -53,  -73,
   88,    0,    0, -236,    0,  663,  598,    0,    0,    0,
    0,    0,    0, -231,  -33,  -26,    0,    0,    6,   36,
   57,   79,    0,  264,  108,  -52, -136,    8,   28,    0,
   65,    4,  -21, -184, -128,  103,  -37,  114,  116,    0,
  119,    0,    0,    0,  -95,  -95,    0,  -54,   51,  -30,
    0,  598,    0,    0,    0,    0,  -10,  135,  -55, -173,
  -77,   60,  144,    0,    0,    0,    0,   -3,    0,  465,
  465,    0,    8,  128,    0,  129,    0,   26,   26,   26,
   26,    0,  220,  -72,    0,    0,    0,    0,    0,    0,
  -25,   26,    0,  -71,    0,    0,  158,    0,    0,    0,
    0,    0,    0,  -69,  621,  -69,  142,    0,   26,    0,
 -236,    0,    0,  -51,  -50, -173,    0,    0,  164,    0,
    0,  663,  151,    0,    0,    0,   45,    0,  -69,  639,
  -69,    0,    0,   28,   28,    0,    0,  647,   87,   91,
   97,    0,    0,  162,    0,  -69,    0,    0,    0,    0,
    0,    0,    0, -173,  582, -173, -108,    0, -164, -164,
  -69, -164,    0,  220,    0,    0,  100,    0,    0,    0,
  -46,  -28,    0,   92,    0,    0,    0,    0,    0,  -38,
    0,    0, -164,    0,  655,    0,    0,  110,  120,    0,
    0,    0,    0,  153,   80,  168,    0,    0,  173,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  302,    0,
  318,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  340,    0,    0,  364,  389,
  418,    1,    0,    0,    0,    0,    0,  115,   23,    0,
    0,    0,    0,    0,    0,    0,    0,  -31,    0,    0,
  449,    0,    0,    0,    0,    0,    0,    0,    0,  544,
    0,  223,    0,    0,    0,    0,    0,  197,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  137,  475,    0,  497,    0,    0,    0,    0,
    0,    0,    0,  160,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  204,    0,    0,  235,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  519,    0,    0,    0,
    0,    0,    0,    0,    0,  127,    0,    0,  215,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47,   69,    0,    0,    0,  182,    0,
    0,    0,    0,   93,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  272,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  132,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    3,  199,   -8,   18,    0,    0,    0,    0,
  485,    0,    0,    0,    0,    0,    0,    0,   75,  474,
   81,   86,    0,    0,   -1,    0,    0,    0,    0,  232,
  241,  136,  237,  202,    0,    0,   24,    0,  -34,  -84,
    0,  -22,  -93,
};
final static int YYTABLESIZE=941;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   49,   37,  126,  108,  118,   52,   85,   69,   65,   23,
   43,  198,   61,  121,   36,   50,   37,   88,   62,   89,
   59,  116,   45,   37,   70,   66,   23,   54,  120,  112,
  157,  158,   74,  124,   98,  100,   99,  136,   98,  100,
   99,   49,   49,   49,   49,   49,   43,   49,  123,   53,
   88,   54,   89,   65,  169,  170,  172,   80,  141,   49,
   49,   49,   49,   45,   75,   45,   45,   45,   44,   90,
   37,  180,  113,   54,   91,   42,  192,  103,  194,  115,
  156,   45,   45,   45,   45,  168,  193,   43,  167,   43,
   43,   43,  121,  189,   76,   37,  190,  113,   54,  202,
    1,    2,   12,   13,  140,   43,   43,   43,   43,   44,
   54,   44,   44,   44,   63,   77,  148,  171,   78,  160,
  209,   87,   88,   92,   89,   49,   93,   44,   44,   44,
   44,  176,  113,  105,  165,   37,   62,  178,  208,   88,
  196,   89,   88,  106,   89,   58,   57,   45,  187,  188,
  204,  121,   37,  109,   37,  110,   65,  113,   63,  104,
  206,    5,   88,    6,   89,  113,   82,    7,  144,  145,
    9,   43,   10,   63,   11,  146,  147,  111,  125,  129,
   62,  105,  130,    5,  133,    6,  142,  143,  149,    7,
  152,   54,    9,   44,   10,   62,   11,  195,  153,  154,
  159,   43,  117,   93,   51,  161,  162,  164,  166,  174,
  197,  207,  113,   32,   33,   10,  183,  121,  104,  201,
   12,   13,    3,   68,   34,   23,  210,   35,   32,   33,
   42,  211,   48,   49,  111,   32,   33,   32,   46,   63,
  105,  107,   12,   13,   23,   23,   95,   96,   97,   29,
   95,   96,   97,  134,  135,   31,   41,   49,  200,   49,
  122,   62,   93,   49,   49,   49,   49,   86,   49,   49,
   49,  129,   49,   49,   49,   84,   49,   49,   49,   45,
  104,   45,   32,   33,  104,   45,   45,   45,   45,  179,
   45,   45,   45,  111,   45,   45,   45,    0,   45,   45,
   45,   75,  150,   43,    0,   43,  105,   32,   33,   43,
   43,   43,   43,    0,   43,   43,   43,   79,   43,   43,
   43,  119,   43,   43,   43,   44,    0,   44,   93,    0,
  129,   44,   44,   44,   44,    0,   44,   44,   44,   67,
   44,   44,   44,    0,   44,   44,   44,   32,   33,  121,
  121,  121,    0,  121,    0,  121,  121,  121,  121,  111,
  121,  121,  121,   69,   32,   33,   32,   33,  121,  121,
  121,   63,    0,   63,    0,   34,    0,   63,   63,   63,
   63,    0,   63,   63,   63,    0,   79,    0,   71,    0,
   63,   63,   63,   62,    0,   62,  129,    0,    0,   62,
   62,   62,   62,    0,   62,   62,   62,    0,    0,    0,
    0,    0,   62,   62,   62,    0,  104,   73,  104,    0,
    0,  104,  104,  104,  104,  104,   75,  104,  104,  104,
    0,    0,    0,    0,    0,  104,  104,  104,  105,    0,
  105,    0,   79,  105,  105,  105,  105,  105,   77,  105,
  105,  105,    0,    0,    0,    0,    0,  105,  105,  105,
   93,    0,   93,    0,   67,    0,   93,   93,   93,   93,
    0,   93,   93,   93,   87,    0,    5,    0,    6,   93,
   93,   93,    7,    0,    0,    9,    0,   10,   69,   11,
    0,  111,    0,  111,    0,    0,   84,  111,  111,  111,
  111,    0,  111,  111,  111,    0,    0,    0,   83,   71,
  111,  111,  111,   71,    0,    0,  101,    0,   81,    0,
    5,    0,    6,    0,    0,    0,    7,    0,  129,    9,
  129,   10,    0,   11,  129,  129,  129,  129,    0,  129,
  129,  129,   73,   59,    0,    0,    0,  129,  129,  129,
    0,    0,    0,  127,  128,    0,    0,    0,   75,    0,
   75,    0,    0,    0,   75,   75,   75,   75,    0,   75,
   75,   75,    0,   77,   79,  151,   79,   75,   75,   75,
   79,   79,   79,   79,    0,   79,   79,   79,    0,  138,
    0,    0,   83,   79,   79,   79,   67,    0,   67,   87,
    0,    0,   67,   67,   67,   67,    0,   67,   67,   67,
  163,    0,    0,    0,    0,   67,   67,   67,    0,    0,
   69,   84,   69,  177,    0,    0,   69,   69,   69,   69,
    0,   69,   69,   69,    0,    0,    0,    0,    0,   69,
   69,   69,    0,   81,    0,   71,    0,   71,  181,    0,
  186,   71,   71,   71,   71,  199,   71,   71,   71,    0,
    0,    0,    0,    0,   71,   71,   71,    0,   59,    0,
    0,  205,    0,    0,   73,    0,   73,    0,    0,    0,
   73,   73,   73,   73,    0,   73,   73,   73,   15,    0,
    0,    0,    0,   73,   73,   73,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   77,  183,   77,    0,    0,
    0,   77,   77,   77,   77,    0,   77,   77,   77,    0,
    0,    5,   63,    6,   77,   77,   77,    7,    0,    0,
    9,   87,   10,   87,   11,    0,    0,   87,   87,   87,
   87,    0,   87,   87,   87,  112,    0,    0,    0,    0,
   87,   87,   87,   84,    0,   84,    0,    0,    0,   84,
   84,   84,   84,  138,   84,   84,   84,    0,    0,    0,
    0,  173,   84,   84,   84,   81,    0,   81,    0,  203,
    0,   81,   81,   81,   81,    0,   81,   81,   81,    0,
    0,    0,    0,    0,   81,   81,   81,    0,    0,    0,
   59,    0,   59,    0,    0,    0,   59,   59,   59,   59,
    0,   59,    0,   59,    0,    0,    0,    0,    0,   59,
   59,   59,    5,    0,    6,    0,    0,    0,    7,    8,
    0,    9,    0,   10,    0,   11,    0,    0,    5,    0,
    6,   12,   13,   14,    7,    8,  182,    9,    0,   10,
    0,   11,    0,    0,    5,    0,    6,   12,   13,   14,
    7,    8,    0,    9,    0,   10,    0,   11,    0,    0,
    0,    0,    0,   12,   13,   14,    0,    5,    0,    6,
    0,    0,    0,    7,    0,    0,    9,    0,   10,  154,
   11,    0,    0,    0,    0,    5,    0,    6,    0,    0,
    0,    7,    0,    5,    9,    6,   10,  154,   11,    7,
    0,    5,    9,    6,   10,    0,   11,    7,    0,    5,
    9,    6,   10,    0,   11,    7,    8,    0,    9,    0,
   10,    0,   11,    0,    0,    0,    0,    0,   12,   13,
   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   58,   41,   59,   59,   59,   41,   17,   41,
   40,   40,   14,   44,   58,   40,   45,   43,   16,   45,
  257,   56,    0,   45,   58,  257,   58,   10,   59,  125,
  115,  116,   59,   44,   60,   61,   62,   41,   60,   61,
   62,   41,   42,   43,   44,   45,    0,   47,   59,  123,
   43,   34,   45,   62,  139,  140,  141,   34,   81,   59,
   60,   61,   62,   41,   59,   43,   44,   45,    0,   42,
   45,  156,   55,   56,   47,  260,  170,  262,  172,   56,
  115,   59,   60,   61,   62,   41,  171,   41,   44,   43,
   44,   45,    0,  258,   59,   45,  261,   80,   81,  193,
  257,  258,  276,  277,   81,   59,   60,   61,   62,   41,
   93,   43,   44,   45,    0,   59,   93,  140,   40,  121,
   41,  258,   43,   59,   45,  125,  123,   59,   60,   61,
   62,   41,  115,  262,  132,   45,    0,   41,   59,   43,
   41,   45,   43,   41,   45,   58,   59,  125,  257,  258,
   41,   59,   45,   40,   45,   40,  165,  140,   44,    0,
   41,  257,   43,  259,   45,  148,   59,  263,   88,   89,
  266,  125,  268,   59,  270,   90,   91,   59,   44,  257,
   44,    0,  123,  257,   41,  259,   59,   59,  261,  263,
  262,  174,  266,  125,  268,   59,  270,  174,   41,  269,
   59,   40,  257,    0,  258,  257,  257,   44,   58,  123,
  257,   59,  195,  257,  258,  268,  125,  125,   59,  258,
  276,  277,    0,  257,  268,  257,   59,  271,  257,  258,
  260,   59,  257,  258,    0,  257,  258,   41,  279,  125,
   59,  279,  276,  277,  276,  277,  272,  273,  274,  123,
  272,  273,  274,  257,  258,   41,  125,  257,  184,  259,
   62,  125,   59,  263,  264,  265,  266,   36,  268,  269,
  270,    0,  272,  273,  274,   35,  276,  277,  278,  257,
   44,  259,  257,  258,  125,  263,  264,  265,  266,  154,
  268,  269,  270,   59,  272,  273,  274,   -1,  276,  277,
  278,    0,  101,  257,   -1,  259,  125,  257,  258,  263,
  264,  265,  266,   -1,  268,  269,  270,    0,  272,  273,
  274,  271,  276,  277,  278,  257,   -1,  259,  125,   -1,
   59,  263,  264,  265,  266,   -1,  268,  269,  270,    0,
  272,  273,  274,   -1,  276,  277,  278,  257,  258,  257,
  258,  259,   -1,  261,   -1,  263,  264,  265,  266,  125,
  268,  269,  270,    0,  257,  258,  257,  258,  276,  277,
  278,  257,   -1,  259,   -1,  268,   -1,  263,  264,  265,
  266,   -1,  268,  269,  270,   -1,  123,   -1,    0,   -1,
  276,  277,  278,  257,   -1,  259,  125,   -1,   -1,  263,
  264,  265,  266,   -1,  268,  269,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,   -1,  257,    0,  259,   -1,
   -1,  262,  263,  264,  265,  266,  125,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,   -1,  125,  262,  263,  264,  265,  266,    0,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,   -1,  125,   -1,  263,  264,  265,  266,
   -1,  268,  269,  270,    0,   -1,  257,   -1,  259,  276,
  277,  278,  263,   -1,   -1,  266,   -1,  268,  125,  270,
   -1,  257,   -1,  259,   -1,   -1,    0,  263,  264,  265,
  266,   -1,  268,  269,  270,   -1,   -1,   -1,   35,   25,
  276,  277,  278,  125,   -1,   -1,   43,   -1,    0,   -1,
  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  257,  266,
  259,  268,   -1,  270,  263,  264,  265,  266,   -1,  268,
  269,  270,  125,    0,   -1,   -1,   -1,  276,  277,  278,
   -1,   -1,   -1,   69,   70,   -1,   -1,   -1,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,  265,  266,   -1,  268,
  269,  270,   -1,  125,  257,  102,  259,  276,  277,  278,
  263,  264,  265,  266,   -1,  268,  269,  270,   -1,  125,
   -1,   -1,  119,  276,  277,  278,  257,   -1,  259,  125,
   -1,   -1,  263,  264,  265,  266,   -1,  268,  269,  270,
  126,   -1,   -1,   -1,   -1,  276,  277,  278,   -1,   -1,
  257,  125,  259,  150,   -1,   -1,  263,  264,  265,  266,
   -1,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,   -1,  125,   -1,  257,   -1,  259,  164,   -1,
  166,  263,  264,  265,  266,  182,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,   -1,  125,   -1,
   -1,  198,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,  264,  265,  266,   -1,  268,  269,  270,  123,   -1,
   -1,   -1,   -1,  276,  277,  278,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  125,  259,   -1,   -1,
   -1,  263,  264,  265,  266,   -1,  268,  269,  270,   -1,
   -1,  257,  125,  259,  276,  277,  278,  263,   -1,   -1,
  266,  257,  268,  259,  270,   -1,   -1,  263,  264,  265,
  266,   -1,  268,  269,  270,  125,   -1,   -1,   -1,   -1,
  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  125,  268,  269,  270,   -1,   -1,   -1,
   -1,  125,  276,  277,  278,  257,   -1,  259,   -1,  125,
   -1,  263,  264,  265,  266,   -1,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,   -1,   -1,   -1,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,
   -1,  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,   -1,  257,   -1,
  259,  276,  277,  278,  263,  264,  265,  266,   -1,  268,
   -1,  270,   -1,   -1,  257,   -1,  259,  276,  277,  278,
  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,  269,
  270,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,   -1,  257,  266,  259,  268,  269,  270,  263,
   -1,  257,  266,  259,  268,   -1,  270,  263,   -1,  257,
  266,  259,  268,   -1,  270,  263,  264,   -1,  266,   -1,
  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,
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
"lista_inv_func : lista_inv_func ',' ID",
"lista_inv_func : lista_inv_func ',' CTE",
"lista_inv_func : CTE",
"lista_inv_func : ID",
"declaracion_constantes : CONST list_constantes ';'",
"declaracion_constantes : CONST list_constantes",
"list_constantes : list_constantes ',' asignacion",
"list_constantes : asignacion",
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
"seleccion : IF condicion cuerpo_if END_IF",
"seleccion : IF cuerpo_if END_IF",
"seleccion : IF condicion END_IF",
"seleccion : IF condicion cuerpo_if",
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

//#line 236 "Gramatica.y"

public String tipoAux = "";
public String ambitoAux = "";
public String funcionAux = "";
public static ArrayList<String> erroresSintacticos = new ArrayList<String>();
public static ArrayList<String> erroresLexicos = new ArrayList<String>();
public static ArrayList<String> erroresSemanticos = new ArrayList<String>();

public void verificarTipos(String arg1,String arg2, String operador){
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

void comprobarID(String identificador){
	if (TablaSimbolos.contieneSimbolo(identificador)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(identificador);
		if (aux.getUso().equals("funcion")){
			erroresSemanticos.add("La funcion '" + Ambito.sinAmbito(identificador) + "' ya fue declarada");
		} else if (aux.getUso().equals("variable")){
			erroresSemanticos.add("Ya existe una variable con el nombre '" + Ambito.sinAmbito(identificador) + "'");
		}
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
		erroresSemanticos.add("La variable " + simbolo + " no esta al alcance");
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
//#line 691 "Parser.java"
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
case 16:
//#line 47 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "Variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval); comprobarID(val_peek(0).sval);}
break;
case 17:
//#line 48 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "Variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval); comprobarID(val_peek(2).sval);}
break;
case 18:
//#line 51 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 19:
//#line 52 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 20:
//#line 55 "Gramatica.y"
{setTipo(funcionAux, val_peek(2).sval); Ambito.removeAmbito();}
break;
case 21:
//#line 58 "Gramatica.y"
{ambitoAux = val_peek(1).sval; setTipo(val_peek(1).sval); setUso(val_peek(1).sval, "funcion"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); comprobarID(val_peek(1).sval); yyval.sval = val_peek(1).sval; Ambito.concatenarAmbito(ambitoAux);}
break;
case 22:
//#line 59 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un identificador de la funcion");}
break;
case 23:
//#line 60 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 24:
//#line 61 "Gramatica.y"
{erroresSintacticos.add("No se puede declarar una funcion con una constante como nombre");}
break;
case 25:
//#line 64 "Gramatica.y"
{funcionAux = val_peek(0).sval;}
break;
case 26:
//#line 65 "Gramatica.y"
{funcionAux = val_peek(0).sval;}
break;
case 27:
//#line 66 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 28:
//#line 67 "Gramatica.y"
{erroresSintacticos.add("Falta un :");}
break;
case 29:
//#line 68 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo de retorno");}
break;
case 30:
//#line 71 "Gramatica.y"
{setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "ParametroFuncion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "Nombre_Parametro_Funcion"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 31:
//#line 72 "Gramatica.y"
{setTipo(val_peek(1).sval,val_peek(0).sval);setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 32:
//#line 73 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
break;
case 33:
//#line 74 "Gramatica.y"
{erroresSintacticos.add("Los identificadores deben tener un tipo");}
break;
case 35:
//#line 78 "Gramatica.y"
{erroresSintacticos.add("La funcion debe retornar un valor");}
break;
case 38:
//#line 87 "Gramatica.y"
{}
break;
case 39:
//#line 88 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 40:
//#line 89 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 41:
//#line 90 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 42:
//#line 91 "Gramatica.y"
{erroresSintacticos.add("Falta un valor que devolver");}
break;
case 43:
//#line 94 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval,"+"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 44:
//#line 95 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "-"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 46:
//#line 99 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "*"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 47:
//#line 100 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "/"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 49:
//#line 104 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 106 "Gramatica.y"
{yyval.sval = "-" + val_peek(0).sval;}
break;
case 52:
//#line 107 "Gramatica.y"
{val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); yyval.sval = val_peek(3).sval;}
break;
case 53:
//#line 108 "Gramatica.y"
{comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); yyval.sval = val_peek(2).sval;}
break;
case 59:
//#line 118 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 62:
//#line 125 "Gramatica.y"
{comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); verificarTipos(val_peek(2).sval,val_peek(0).sval, "=:"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 63:
//#line 126 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 67:
//#line 134 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 69:
//#line 136 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 71:
//#line 138 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 73:
//#line 140 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 74:
//#line 143 "Gramatica.y"
{TercetoManager.breakDoUntil();}
break;
case 75:
//#line 144 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 77:
//#line 146 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 78:
//#line 147 "Gramatica.y"
{TercetoManager.continueDoUntil();}
break;
case 79:
//#line 148 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 81:
//#line 150 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 82:
//#line 151 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 84:
//#line 153 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 85:
//#line 154 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 87:
//#line 156 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 88:
//#line 157 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 89:
//#line 158 "Gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 90:
//#line 161 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 91:
//#line 162 "Gramatica.y"
{erroresSintacticos.add("Falta la condicion del if");}
break;
case 92:
//#line 163 "Gramatica.y"
{erroresSintacticos.add("Falta el cuerpo del if");}
break;
case 93:
//#line 164 "Gramatica.y"
{erroresSintacticos.add("Falta un end_if");}
break;
case 94:
//#line 167 "Gramatica.y"
{verificarTipos(val_peek(3).sval, val_peek(1).sval, val_peek(2).sval); TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval); TercetoManager.add_seleccion_cond();}
break;
case 95:
//#line 168 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 96:
//#line 169 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 97:
//#line 172 "Gramatica.y"
{yyval.sval = "<";}
break;
case 98:
//#line 173 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 99:
//#line 174 "Gramatica.y"
{yyval.sval = ">";}
break;
case 100:
//#line 175 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 101:
//#line 176 "Gramatica.y"
{yyval.sval = "=";}
break;
case 102:
//#line 177 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 105:
//#line 182 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 106:
//#line 185 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 110:
//#line 195 "Gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 111:
//#line 196 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 112:
//#line 197 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 113:
//#line 198 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 114:
//#line 201 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 115:
//#line 202 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 116:
//#line 203 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 117:
//#line 204 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 118:
//#line 207 "Gramatica.y"
{Ambito.concatenarAmbito("doUntil"); TercetoManager.add_inicio_do_until();}
break;
case 119:
//#line 210 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 121:
//#line 214 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 123:
//#line 218 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 124:
//#line 219 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 125:
//#line 220 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 126:
//#line 224 "Gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 127:
//#line 227 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 129:
//#line 231 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 130:
//#line 232 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1196 "Parser.java"
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
