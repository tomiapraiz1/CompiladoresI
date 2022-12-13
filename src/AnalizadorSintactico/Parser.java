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
   17,   17,   17,   17,   16,   18,   19,   20,   20,   20,
   20,   20,   21,   21,   21,   22,   22,   22,   23,   23,
   23,   23,   23,   24,   24,   24,   24,   24,   24,   10,
   10,   25,   25,   26,   26,   27,   27,    6,    6,   28,
   28,   28,   28,   28,   28,   28,   28,   28,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   34,   33,   30,   36,   37,   39,
   39,   39,   40,   40,   40,   40,   40,   40,   38,   38,
   38,   41,   42,   43,   43,   11,   44,   45,   31,   31,
   31,   31,   32,   32,   32,   32,   46,   47,   48,   48,
   49,   49,   35,   35,   35,   35,   50,   51,   52,   52,
   52,
};
final static short yylen[] = {                            2,
    4,    3,    3,    1,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    3,    1,    1,
    3,    3,    2,    2,    3,    4,    3,    2,    2,    2,
    5,    2,    1,    3,    3,    1,    1,    5,    4,    4,
    4,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    4,    3,    3,    3,    3,    3,    1,    1,    3,
    2,    3,    1,    3,    2,    3,    2,    1,    1,    2,
    1,    2,    1,    2,    1,    2,    1,    1,    2,    1,
    3,    2,    2,    1,    4,    3,    3,    3,    2,    2,
    3,    2,    3,    2,    2,    2,    4,    1,    1,    5,
    4,    4,    1,    1,    1,    1,    1,    1,    4,    2,
    3,    3,    3,    2,    1,    6,    1,    1,    4,    3,
    3,    3,    5,    4,    4,    4,    1,    1,    2,    1,
    2,    1,    6,    5,    5,    5,    1,    1,    2,    1,
    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,   98,    0,    0,    0,    0,
  117,    0,    0,   19,   20,    0,    4,    0,    0,    9,
   10,   11,   12,   13,   14,   15,    0,    0,   78,    0,
   68,   69,    0,    0,    0,    0,    0,    0,    0,    0,
   50,    0,    0,   96,    0,    0,    0,   48,    0,    0,
    0,    0,    0,   23,    0,    0,    0,   79,  127,  115,
    0,    0,   83,    0,    0,    0,   63,    0,    5,    2,
    8,   17,    0,    0,    0,    0,    0,    0,    0,   70,
   72,   74,   76,   90,    0,   94,    0,    0,    0,   99,
  118,    0,    0,  137,    0,    0,    0,   51,    0,    0,
    0,    0,   93,  121,    0,  122,   22,   25,    0,    0,
    0,   81,  128,  114,    0,    0,    0,    0,   87,   65,
    0,   60,    0,    1,   16,    0,    0,    0,   29,   28,
    0,   36,   21,    0,    0,   88,   91,  104,  106,  108,
  103,  105,  107,    0,    0,    0,    0,    0,    0,    0,
   53,    0,  138,    0,    0,    0,    0,    0,   46,   47,
  119,   42,   40,    0,   39,    0,  124,    0,  125,  126,
   85,   64,   62,   18,   34,   27,    0,    0,    0,    0,
    0,    0,    0,   97,    0,    0,    0,   52,    0,    0,
    0,    0,    0,   38,  129,  123,    0,   37,   35,   26,
  102,    0,  101,    0,    0,    0,   54,   55,   57,   56,
  131,  141,    0,  134,  135,    0,  136,   31,  100,  112,
    0,  109,  116,  139,  133,    0,  113,
};
final static short yydgoto[] = {                          3,
    4,   18,   19,   70,   20,   21,   22,   23,   24,   25,
   26,   27,   73,   28,   78,  133,   79,  134,  199,   29,
   46,   47,   48,  152,   66,   67,   30,   31,   32,   33,
   34,   35,   36,   37,   49,   38,   89,  147,   90,  145,
  183,  222,   61,   39,   92,   62,  115,  167,  190,   96,
  154,  214,
};
final static short yysindex[] = {                      -155,
    0,    0,    0,  466,  -45,    0,  -38,   -3,   -9,  -52,
    0,  567,   64,    0,    0, -240,    0,  668,  500,    0,
    0,    0,    0,    0,    0,    0, -222,  -32,    0,   -8,
    0,    0,   12,   19,   35,  -47,  -25,   56,   56,   59,
    0,  579,  -39,    0, -199,   36,   73,    0,   50,   75,
  -37,   78,  100,    0,  -27,   76,   79,    0,    0,    0,
  624,  624,    0,  -43, -233,   13,    0,  500,    0,    0,
    0,    0,   14,   88,  -48, -132, -111,   26,  118,    0,
    0,    0,    0,    0,   95,    0,  102,  -21,  -93,    0,
    0,  -86,   67,    0,  636,  636,   36,    0,  -39,  -39,
  -39,  -39,    0,    0,  138,    0,    0,    0,  124,   52,
  125,    0,    0,    0,  -92,  589,  -92,  126,    0,    0,
  -72,    0, -240,    0,    0,  -70,  -69, -132,    0,    0,
  145,    0,    0,  668,  133,    0,    0,    0,    0,    0,
    0,    0,    0,  -40,  -39,   70,  -65,   77,  148,  152,
    0,  157,    0,  -68,  603,  -68,   73,   73,    0,    0,
    0,    0,    0,  140,    0,   56,    0,  -92,    0,    0,
    0,    0,    0,    0,    0,    0, -132,  522, -132,  106,
   92,  -97,  -58,    0,  668, -105, -101,    0,   56, -229,
 -229,  -68, -229,    0,    0,    0,  -53,    0,    0,    0,
    0,   98,    0,  646,   84,  545,    0,    0,    0,    0,
    0,    0,  -50,    0,    0, -229,    0,    0,    0,    0,
  -97,    0,    0,    0,    0,  658,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  202,
    0,    0,  224,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  240,
    0,    0,  262,  284,  306,    0,    0,    0,    0,    1,
    0,    0,  -12,    0,    0,   91,   23,    0,    0,    0,
    0,  -30,    0,    0,    0,    0,  329,    0,    0,    0,
    0,    0,    0,    0,    0,  441,    0,  205,    0,    0,
    0,    0,    0,  168,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  351,    0,  373,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  113,    0,    0,    0,
    0,    0,    0,    0,  136,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  397,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   87,    0,    0,
  170,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  174,  181,
    0,    0,    0,    0,    0,    0,   45,   69,    0,    0,
    0,    0,    0,  419,    0,  158,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -5,    0,    0,    0,    0,    0, -191,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   32,    0,    0,    0,    0,    0,
    0,    0,  180,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -10,  182,   57,  659,    0,    0,    0,    0,
    0,   -1,    0,    0,    0,    0,    0,    0,    0,    0,
   10,   82,   62,    0,    0,  149,    0,    0,    0,    0,
    0,  245,    0,    0,  239,    0,    0,    0,  -24,  141,
    0,    0,   38,    0,    0,    0,  -29,  -67, -100,    0,
  -66, -139,
};
final static int YYTABLESIZE=946;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
   49,   51,   99,  106,  100,   45,   58,   68,   75,  128,
   24,   84,   44,  109,   91,  119,   65,   45,   56,  141,
  143,  142,   45,   45,  120,   76,   77,   24,  212,  156,
   55,  213,  117,   86,   72,   45,   54,  121,  141,  143,
  142,   49,   49,   49,   43,   49,   95,   49,  169,  170,
   80,  215,   97,  217,  191,  193,  123,  126,   98,   49,
   49,   49,   49,   45,  110,   45,  132,   45,   44,  132,
   81,  122,  125,  129,  130,   71,  225,   82,   99,   95,
  100,   45,   45,   45,   45,   43,  168,   43,  192,   43,
   67,  216,  164,   83,   99,   88,  100,  144,   93,  116,
  196,    1,    2,   43,   43,   43,   43,  151,  103,   44,
  163,   44,   66,   44,  101,  104,  111,  107,   99,  102,
  100,   64,   63,  178,   71,   49,  176,   44,   44,   44,
   44,  127,  203,  155,   99,  120,  100,  112,  219,  108,
   99,  195,  100,   14,   15,  131,  201,   45,  132,   67,
   45,  207,  208,  136,  181,  209,  210,  130,  135,    5,
  137,    6,  159,  160,  211,    7,  146,    9,   10,   43,
   12,   66,   13,  148,  206,  197,  166,  200,  161,  140,
  157,  158,  162,  165,  171,  172,  174,  175,  177,  202,
  179,  186,  182,   44,  120,  187,  184,  188,  194,  185,
  189,   80,  205,  218,    3,   57,  221,  224,   33,   30,
   32,   40,   41,  118,   59,   67,  130,   40,   41,  204,
   12,   58,   42,   84,   74,   43,   24,   14,   15,   40,
   41,  138,  139,  140,   71,   40,   41,   66,  140,   71,
   50,  105,   42,   14,   15,   24,   24,   40,   41,  124,
  138,  139,  140,   52,   53,   95,  110,   49,  226,   49,
  120,   73,   71,   49,   49,   49,   49,   49,   49,   49,
   49,  173,   49,   49,   49,   87,   49,   49,   49,   45,
   85,   45,  130,   75,  180,   45,   45,   45,   45,   45,
   45,   45,   45,  111,   45,   45,   45,    0,   45,   45,
   45,   43,    0,   43,  140,   77,    0,   43,   43,   43,
   43,   43,   43,   43,   43,    0,   43,   43,   43,    0,
   43,   43,   43,  149,  150,   44,   80,   44,   82,    0,
    0,   44,   44,   44,   44,   44,   44,   44,   44,    0,
   44,   44,   44,    0,   44,   44,   44,   67,   84,   67,
   89,    0,    0,   67,   67,   67,   67,   67,   67,   67,
   67,    0,   40,   41,   71,    0,   67,   67,   67,   66,
    0,   66,   92,    0,    0,   66,   66,   66,   66,   66,
   66,   66,   66,    0,    0,    0,   73,    0,   66,   66,
   66,    0,  120,    0,  120,    0,   86,    0,  120,  120,
  120,  120,  120,  120,  120,  120,    0,    0,   75,    0,
    0,  120,  120,  120,  130,    0,  130,    0,   41,    0,
  130,  130,  130,  130,  130,  130,  130,  130,    0,    0,
   77,    0,    0,  130,  130,  130,  140,    0,  140,    0,
   61,    0,  140,  140,  140,  140,  140,  140,  140,  140,
    0,    0,    0,   82,    0,  140,  140,  140,   80,    0,
   80,    0,    0,    0,   80,   80,   80,   80,   80,   80,
   80,   80,    0,    0,    0,   89,    0,   80,   80,   80,
   84,    0,   84,    0,    0,    0,   84,   84,   84,   84,
   84,   84,   84,   84,    0,    0,   71,   92,   71,   84,
   84,   84,   71,   71,   71,   71,   71,   71,   71,   71,
    0,    0,    0,    0,    0,   71,   71,   71,   73,    0,
   73,   86,    0,    0,   73,   73,   73,   73,   73,   73,
   73,   73,    0,    0,    0,    0,    0,   73,   73,   73,
   75,    0,   75,   41,    0,    0,   75,   75,   75,   75,
   75,   75,   75,   75,    0,    0,    0,    0,    0,   75,
   75,   75,   77,    0,   77,   61,    0,    0,   77,   77,
   77,   77,   77,   77,   77,   77,    0,    0,    0,    0,
    0,   77,   77,   77,    0,   82,    0,   82,   17,    0,
    0,   82,   82,   82,   82,   82,   82,   82,   82,    0,
    0,    0,    0,    0,   82,   82,   82,   89,    0,   89,
    0,    0,    0,   89,   89,   89,   89,   89,   89,   89,
   89,    0,    0,    0,   69,    0,   89,   89,   89,   92,
    0,   92,    0,    0,    0,   92,   92,   92,   92,   92,
   92,   92,   92,    0,    0,    0,  198,    0,   92,   92,
   92,    0,    0,   86,    0,   86,    0,    0,    0,   86,
   86,   86,   86,   86,   86,   86,   86,    0,    0,  223,
   60,    0,   86,   86,   86,   41,    0,   41,    0,    0,
    0,   41,   41,   41,   41,   41,   41,   41,   41,   59,
    0,    0,    0,    0,   41,   41,   41,   61,    0,   61,
   60,   94,    0,   61,   61,   61,   61,   61,   61,    0,
   61,    0,    0,  113,    0,    0,   61,   61,   61,  114,
   60,    0,    5,    0,    6,    0,    0,  153,    7,    8,
    9,   10,   11,   12,    0,   13,    0,    0,    0,    0,
    0,   14,   15,   16,    0,    0,    0,    0,  113,    0,
    0,    0,    0,  114,   60,    0,    5,    0,    6,    0,
  153,    0,    7,    8,    9,   10,   11,   12,    0,   13,
  220,    0,    0,    0,  114,   14,   15,   16,    5,    0,
    6,    0,  227,    0,    7,    8,    9,   10,   11,   12,
    0,   13,    0,    0,    0,    0,    0,   14,   15,   16,
    0,    5,    0,    6,    0,    0,    0,    7,    8,    9,
   10,   11,   12,  114,   13,    0,    0,    0,    0,    0,
   14,   15,   16,    5,    0,    6,    0,    0,    0,    7,
    0,    9,   10,    0,   12,    5,   13,    6,    0,    0,
   60,    7,    0,    9,   10,    5,   12,    6,   13,    0,
    0,    7,    0,    9,   10,    0,   12,  166,   13,    5,
    0,    6,  114,    0,    0,    7,    0,    9,   10,    0,
   12,  189,   13,    0,    0,    0,    0,    0,    0,   60,
    5,    0,    6,    0,  114,    0,    7,    0,    9,   10,
    0,   12,    5,   13,    6,    0,    0,    0,    7,    0,
    9,   10,    5,   12,    6,   13,    0,    0,    7,    0,
    9,   10,    0,   12,    5,   13,    6,    0,    0,    0,
    7,    0,    9,   10,    5,   12,    6,   13,    0,    0,
    7,    8,    9,   10,   11,   12,    0,   13,    0,    0,
    0,    0,    0,   14,   15,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   43,   41,   45,   45,   59,   18,   41,   58,
   41,   59,   58,   41,   39,   59,  257,   45,    9,   60,
   61,   62,    0,   45,  258,   58,   28,   58,  258,   96,
   40,  261,   62,   59,  257,   45,   40,  271,   60,   61,
   62,   41,   42,   43,    0,   45,   59,   47,  116,  117,
   59,  191,   43,  193,  155,  156,   44,   44,  258,   59,
   60,   61,   62,   41,   55,   43,  258,   45,    0,  261,
   59,   59,   59,   75,   76,   19,  216,   59,   43,   42,
   45,   59,   60,   61,   62,   41,  116,   43,  155,   45,
    0,  192,   41,   59,   43,   40,   45,   88,   40,   62,
  168,  257,  258,   59,   60,   61,   62,   41,   59,   41,
   59,   43,    0,   45,   42,   41,   41,   40,   43,   47,
   45,   58,   59,  134,   68,  125,  128,   59,   60,   61,
   62,   44,   41,   96,   43,    0,   45,   59,   41,   40,
   43,  166,   45,  276,  277,  257,   41,  125,  123,   59,
   45,  257,  258,   59,  145,  257,  258,    0,   41,  257,
   59,  259,  101,  102,  189,  263,  260,  265,  266,  125,
  268,   59,  270,  260,  185,  177,  269,  179,   41,    0,
   99,  100,   59,   59,   59,  258,  257,  257,   44,  180,
   58,   44,  123,  125,   59,   44,  262,   41,   59,  123,
  269,    0,  261,  257,    0,  258,  123,  258,   41,  123,
   41,  257,  258,  257,   41,  125,   59,  257,  258,  182,
  268,   41,  268,    0,  257,  271,  257,  276,  277,  257,
  258,  272,  273,  274,  178,  257,  258,  125,   59,    0,
  279,  279,  268,  276,  277,  276,  277,  257,  258,   68,
  272,  273,  274,  257,  258,  268,  262,  257,  221,  259,
  125,    0,  206,  263,  264,  265,  266,  267,  268,  269,
  270,  123,  272,  273,  274,   37,  276,  277,  278,  257,
   36,  259,  125,    0,  144,  263,  264,  265,  266,  267,
  268,  269,  270,  262,  272,  273,  274,   -1,  276,  277,
  278,  257,   -1,  259,  125,    0,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,   -1,  272,  273,  274,   -1,
  276,  277,  278,  257,  258,  257,  125,  259,    0,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
  272,  273,  274,   -1,  276,  277,  278,  257,  125,  259,
    0,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,   -1,  257,  258,  125,   -1,  276,  277,  278,  257,
   -1,  259,    0,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,  125,   -1,  276,  277,
  278,   -1,  257,   -1,  259,   -1,    0,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,  125,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,    0,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,   -1,   -1,
  125,   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,
    0,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
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
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,  125,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,   -1,  257,   -1,  259,  123,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,  125,   -1,  276,  277,  278,  257,
   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,  125,   -1,  276,  277,
  278,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,   -1,  125,
   12,   -1,  276,  277,  278,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,  123,
   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,
   42,  123,   -1,  263,  264,  265,  266,  267,  268,   -1,
  270,   -1,   -1,  125,   -1,   -1,  276,  277,  278,   61,
   62,   -1,  257,   -1,  259,   -1,   -1,  125,  263,  264,
  265,  266,  267,  268,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,   -1,   -1,   -1,   -1,  125,   -1,
   -1,   -1,   -1,   95,   96,   -1,  257,   -1,  259,   -1,
  125,   -1,  263,  264,  265,  266,  267,  268,   -1,  270,
  125,   -1,   -1,   -1,  116,  276,  277,  278,  257,   -1,
  259,   -1,  125,   -1,  263,  264,  265,  266,  267,  268,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,
  266,  267,  268,  155,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,
   -1,  265,  266,   -1,  268,  257,  270,  259,   -1,   -1,
  182,  263,   -1,  265,  266,  257,  268,  259,  270,   -1,
   -1,  263,   -1,  265,  266,   -1,  268,  269,  270,  257,
   -1,  259,  204,   -1,   -1,  263,   -1,  265,  266,   -1,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,  221,
  257,   -1,  259,   -1,  226,   -1,  263,   -1,  265,  266,
   -1,  268,  257,  270,  259,   -1,   -1,   -1,  263,   -1,
  265,  266,  257,  268,  259,  270,   -1,   -1,  263,   -1,
  265,  266,   -1,  268,  257,  270,  259,   -1,   -1,   -1,
  263,   -1,  265,  266,  257,  268,  259,  270,   -1,   -1,
  263,  264,  265,  266,  267,  268,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,
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
"ejecutable : retorno_funcion",
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

//#line 275 "Gramatica.y"

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
	if (TablaSimbolos.contieneSimbolo(p)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(p);
		return aux.getTipo();
	}

	return "";
}

public void verificarTipos(String arg1,String arg2, String operador){
	TablaTipos.setTipoAbarcativo(arg1, arg2, operador);
}


void chequearTipoParametros(String funcion, String p1, String p2){
	if (TablaSimbolos.contieneSimbolo(funcion)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(funcion);
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
{ambitoAux = val_peek(1).sval;; setTipo(val_peek(1).sval); setUso(val_peek(1).sval, "funcion"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); idAux = val_peek(1).sval; yyval.sval = val_peek(1).sval; TercetoManager.add_funcion(val_peek(1).sval); Ambito.concatenarAmbito(ambitoAux);}
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
{TablaSimbolos.modificarParametros(idAux, 2); TablaSimbolos.modificarTipoParametros(idAux, val_peek(4).sval, val_peek(1).sval); setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "ParametroFuncion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
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
case 38:
//#line 87 "Gramatica.y"
{TercetoManager.add_return_funcion(funcionAux, val_peek(2).sval);}
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
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "+");
								if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
								else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 44:
//#line 97 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "-");
								if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
								else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 46:
//#line 103 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "*");
					if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
					else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 47:
//#line 106 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "/");
					if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
					else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 49:
//#line 112 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 114 "Gramatica.y"
{/*ChequearRangoNegativo($2.sval)*/;yyval.sval = "-" + val_peek(0).sval;}
break;
case 52:
//#line 115 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(3).sval); comprobarAmbito(val_peek(3).sval); val_peek(2).sval = val_peek(3).sval; val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); if (val_peek(3).sval == null) chequearParametros(val_peek(2).sval, val_peek(1).ival); else chequearParametros(val_peek(3).sval, val_peek(1).ival); chequearTipoParametros(val_peek(3).sval, parametro1, parametro2); TercetoManager.llamado_funcion(val_peek(3).sval); yyval.sval = "["+(TercetoManager.getIndexTerceto()-1)+"]";}
break;
case 53:
//#line 116 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); comprobarAmbito(val_peek(2).sval); val_peek(1).sval = val_peek(2).sval; val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); if (val_peek(2).sval == null) chequearParametros(val_peek(1).sval, 0); else chequearParametros(val_peek(2).sval, 0); TercetoManager.llamado_funcion(val_peek(2).sval); yyval.sval = "["+(TercetoManager.getIndexTerceto()-1)+"]";}
break;
case 54:
//#line 119 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); TablaSimbolos.eliminarSimbolo(val_peek(0).sval);yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval + Ambito.getAmbitoActual()); parametro2 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual()); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 55:
//#line 120 "Gramatica.y"
{yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval + Ambito.getAmbitoActual()); parametro2 = getTipoParametro(val_peek(0).sval); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 56:
//#line 121 "Gramatica.y"
{yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval); parametro2 = getTipoParametro(val_peek(0).sval); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 57:
//#line 122 "Gramatica.y"
{yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval); parametro2 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual()); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 58:
//#line 123 "Gramatica.y"
{yyval.ival = 1; parametro1 = getTipoParametro(val_peek(0).sval); TercetoManager.crear_terceto("Parametros", val_peek(0).sval, "", "");}
break;
case 59:
//#line 124 "Gramatica.y"
{yyval.ival = 1; parametro1 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual()); TercetoManager.crear_terceto("Parametros", val_peek(0).sval, "", "");}
break;
case 61:
//#line 128 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 64:
//#line 135 "Gramatica.y"
{Atributo aux = TablaSimbolos.obtenerSimbolo(val_peek(0).sval); setTipo(aux.getTipo(),val_peek(2).sval); setUso(val_peek(2).sval, "constante"); val_peek(2).sval = TablaSimbolos.modificarNombre(val_peek(2).sval);  TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 65:
//#line 136 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 66:
//#line 139 "Gramatica.y"
{if (val_peek(0).sval == null) break; comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); esConstante(val_peek(2).sval); int indice = TercetoManager.getIndexTerceto();
					verificarTipos(val_peek(2).sval,val_peek(0).sval, "=:"); if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
					else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 67:
//#line 142 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 71:
//#line 150 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 73:
//#line 152 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 75:
//#line 154 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 77:
//#line 156 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 79:
//#line 160 "Gramatica.y"
{TercetoManager.breakDoUntil();}
break;
case 80:
//#line 161 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 81:
//#line 162 "Gramatica.y"
{TercetoManager.add_break_cte(idAux, val_peek(1).sval, TablaSimbolos.obtenerSimbolo(val_peek(1).sval).getTipo());}
break;
case 82:
//#line 163 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 83:
//#line 164 "Gramatica.y"
{TercetoManager.continueDoUntil();}
break;
case 84:
//#line 165 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 85:
//#line 166 "Gramatica.y"
{contieneEtiqueta(val_peek(1).sval+Ambito.getAmbitoActual()); TercetoManager.continueDoUntilEtiqueta(val_peek(1).sval);}
break;
case 86:
//#line 167 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 87:
//#line 168 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 88:
//#line 169 "Gramatica.y"
{}
break;
case 89:
//#line 170 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 90:
//#line 171 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 91:
//#line 172 "Gramatica.y"
{TercetoManager.add_fin_id_asig();}
break;
case 92:
//#line 173 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 93:
//#line 174 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 94:
//#line 175 "Gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 95:
//#line 178 "Gramatica.y"
{comprobarAmbito(val_peek(1).sval); val_peek(1).sval = Ambito.getAmbito(val_peek(1).sval); idAux = val_peek(1).sval; TercetoManager.add_inicio_id_asig();}
break;
case 96:
//#line 181 "Gramatica.y"
{TablaSimbolos.agregarSimbolo(val_peek(1).sval, 257, "", AnalizadorLexico.getLine()); setUso(val_peek(1).sval,"etiqueta"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); TablaSimbolos.setTercetoSalto(val_peek(1).sval,"[" + TercetoManager.getIndexTerceto() + "]");}
break;
case 97:
//#line 184 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 99:
//#line 190 "Gramatica.y"
{TercetoManager.add_seleccion_cond();}
break;
case 100:
//#line 193 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(3).sval, val_peek(1).sval, val_peek(2).sval); if (indice == TercetoManager.getIndexTerceto()) TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 101:
//#line 194 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 102:
//#line 195 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 103:
//#line 198 "Gramatica.y"
{yyval.sval = "<";}
break;
case 104:
//#line 199 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 105:
//#line 200 "Gramatica.y"
{yyval.sval = ">";}
break;
case 106:
//#line 201 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 107:
//#line 202 "Gramatica.y"
{yyval.sval = "=";}
break;
case 108:
//#line 203 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 111:
//#line 208 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 112:
//#line 211 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 116:
//#line 221 "Gramatica.y"
{TercetoManager.add_iter_when();}
break;
case 118:
//#line 227 "Gramatica.y"
{TercetoManager.add_cond_when();}
break;
case 119:
//#line 230 "Gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 120:
//#line 231 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 121:
//#line 232 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 122:
//#line 233 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 123:
//#line 236 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 124:
//#line 237 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 125:
//#line 238 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 126:
//#line 239 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 127:
//#line 242 "Gramatica.y"
{TercetoManager.add_inicio_do_until();}
break;
case 129:
//#line 248 "Gramatica.y"
{}
break;
case 130:
//#line 249 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 131:
//#line 252 "Gramatica.y"
{TercetoManager.add_condicion_id_asig();}
break;
case 132:
//#line 253 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 134:
//#line 257 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 135:
//#line 258 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 136:
//#line 259 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 139:
//#line 269 "Gramatica.y"
{TercetoManager.add_else_cte(idAux, val_peek(0).sval, TablaSimbolos.obtenerSimbolo(val_peek(0).sval).getTipo());}
break;
case 140:
//#line 270 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 141:
//#line 271 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1320 "Parser.java"
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
