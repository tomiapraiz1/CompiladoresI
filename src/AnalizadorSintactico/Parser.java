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
   29,   29,   29,   29,   29,   33,   30,   35,   36,   38,
   38,   38,   39,   39,   39,   39,   39,   39,   37,   37,
   37,   40,   41,   42,   42,   11,   43,   44,   31,   31,
   31,   31,   32,   32,   32,   32,   45,   46,   47,   47,
   34,   34,   34,   34,   48,   49,   50,   50,   50,
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
    2,    4,    3,    3,    3,    2,    4,    1,    1,    5,
    4,    4,    1,    1,    1,    1,    1,    1,    4,    2,
    3,    3,    3,    2,    1,    6,    1,    1,    4,    3,
    3,    3,    5,    4,    4,    4,    1,    1,    2,    1,
    6,    5,    5,    5,    1,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,   98,    0,    0,    0,  117,
    0,    0,   19,   20,    0,    4,    0,    0,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,   70,   71,
    0,    0,    0,    0,    0,    0,    0,   52,    0,    0,
   96,    0,    0,    0,   50,    0,    0,    0,    0,    0,
   23,    0,   80,  127,  115,    0,    0,   84,    0,    0,
    0,   65,    0,    5,    2,    8,   17,    0,    0,    0,
    0,    0,    0,    0,   72,   74,   76,   78,   91,    0,
    0,    0,   99,  118,    0,    0,  135,    0,    0,   95,
    0,    0,   53,    0,    0,    0,    0,   94,  121,    0,
  122,   22,   25,   82,  128,  114,    0,    0,    0,    0,
   88,   67,    0,   62,    0,    1,   16,    0,    0,    0,
   29,   28,    0,   38,   21,    0,    0,   89,  104,  106,
  108,  103,  105,  107,    0,    0,    0,    0,    0,    0,
    0,   55,    0,  136,    0,    0,    0,   92,    0,    0,
   48,   49,  119,    0,  124,    0,  125,  126,   86,   66,
   64,   18,   34,   27,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   97,    0,    0,    0,   54,    0,    0,
    0,    0,  129,  123,    0,    0,    0,   39,    0,   37,
   36,   26,  102,    0,  101,    0,    0,    0,   56,   57,
   59,   58,  139,    0,  132,  133,    0,  134,   31,    0,
    0,    0,   35,  100,  112,    0,  109,  116,  137,  131,
   44,   42,    0,   41,    0,   40,  113,
};
final static short yydgoto[] = {                          3,
    4,   17,   18,   65,   19,   20,   21,   22,   23,   24,
   25,   26,   68,   27,   73,  125,   74,  126,  168,  190,
   43,   44,   45,  143,   61,   62,   28,   29,   30,   31,
   32,   33,   34,   46,   35,   82,  138,   83,  136,  173,
  217,   56,   36,   85,   57,  107,  155,   89,  145,  205,
};
final static short yysindex[] = {                      -201,
    0,    0,    0,  332,  117,    0,  -38,   -8,  -52,    0,
  354,   40,    0,    0, -240,    0,  622,  476,    0,    0,
    0,    0,    0,    0,    0, -230,  -33,  -24,    0,    0,
    6,   21,   57,  -47,   83,   83,   85,    0,  500,  -45,
    0, -123,   36,    8,    0,   80,  101,  -37,  105,  114,
    0,   87,    0,    0,    0,  508,  508,    0,  -50, -222,
  -25,    0,  476,    0,    0,    0,    0,   -7,  111,  -48,
 -176, -101,   37,  118,    0,    0,    0,    0,    0,  104,
  -21,  -96,    0,    0,  -95,   -3,    0,  578,  578,    0,
   36,  107,    0,  -39,  -39,  -39,  -39,    0,    0,  126,
    0,    0,    0,    0,    0,    0, -100,  544, -100,  109,
    0,    0,  -87,    0, -240,    0,    0,  -83,  -81, -176,
    0,    0,  138,    0,    0,  606,  125,    0,    0,    0,
    0,    0,    0,    0,  -40,  -39,   61,  -69,   73,  159,
  160,    0,  164,    0, -100,  564, -100,    0,    8,    8,
    0,    0,    0,   83,    0, -100,    0,    0,    0,    0,
    0,    0,    0,    0, -176,  -27,  -78,   86, -176,  106,
   51, -148,  -53,    0,  622, -125, -120,    0, -191, -191,
 -100, -191,    0,    0,  -43,  108,   52,    0,   86,    0,
    0,    0,    0,   76,    0,  586,   92,  520,    0,    0,
    0,    0,    0,  -36,    0,    0, -191,    0,    0,  151,
   30,  161,    0,    0,    0, -148,    0,    0,    0,    0,
    0,    0,  166,    0,  598,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  202,    0,
    0,  226,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  248,    0,    0,
  272,  294,  316,    0,    0,    0,    1,    0,    0,    0,
    0,    0,  113,   23,    0,    0,    0,    0,  -30,    0,
    0,  362,    0,    0,    0,    0,    0,    0,    0,    0,
  451,    0,  240,    0,    0,    0,    0,    0,  204,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  384,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  136,  407,    0,    0,    0,    0,    0,    0,    0,  158,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  429,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  133,
    0,    0,  216,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  218,
  221,    0,    0,    0,    0,    0,    0,    0,   45,   69,
    0,    0,    0,   91,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   14,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   22,    0,    0,    0,
    0,    0,    0,  180,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  156,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   -2,  200,   11,  579,    0,    0,    0,    0,
    0,   32,    0,    0,    0,    0,    0,    0,  131, -135,
   -9,   46,   47,    0,    0,  170,    0,    0,    0,    0,
    0,  269,    0,  266,    0,    0,    0,  -20,  172,    0,
    0,   19,    0,    0,    0,  -31,  672,    0,  -59, -129,
};
final static int YYTABLESIZE=900;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   51,   48,   94,  101,   95,   42,   53,   70,  111,  120,
   24,   79,  186,   90,   63,   84,   60,   42,  115,  132,
  134,  133,   47,   42,   71,  109,   67,   24,   66,  147,
   91,   51,  191,  114,   75,  112,  118,  142,  132,  134,
  133,   51,   51,   51,   45,   51,  188,   51,  113,   96,
  206,  117,  208,  213,   97,    1,    2,   88,   72,   51,
   51,   51,   51,   47,   76,   47,  203,   47,   46,  204,
  223,  135,   94,   66,   95,  108,  156,  220,   94,   77,
   95,   47,   47,   47,   47,   45,  181,   45,  222,   45,
  130,  195,  212,   94,   94,   95,   95,   59,   58,   13,
   14,  121,  122,   45,   45,   45,   45,  146,    5,   46,
    6,   46,   69,   46,    7,   78,  214,    9,   94,   11,
   95,   12,   81,  167,   86,   51,  171,   46,   46,   46,
   46,  199,  200,  183,   93,   68,  201,  202,   98,  149,
  150,   99,  151,  152,  102,  104,  193,   47,  210,  130,
   42,  164,   42,  103,  119,  123,  187,  120,  127,  124,
  194,   42,  128,  137,  139,  148,  153,  159,  154,   45,
  160,   69,  198,  162,   41,  163,  211,   66,    5,  138,
    6,  165,  169,  172,    7,    8,  166,    9,   10,   11,
  196,   12,  174,   46,   68,  175,  185,   13,   14,   15,
  192,   81,  176,  177,  178,   52,  110,  197,   66,  221,
  188,   37,   38,  209,  216,  130,  120,   37,   38,  224,
   11,  219,   39,   69,  226,   85,   24,   13,   14,   37,
   38,  129,  130,  131,  225,   37,   38,   69,  138,    3,
   47,  100,   13,   14,   33,   24,   24,   73,   49,   50,
  129,  130,  131,  140,  141,   30,   32,   51,   61,   51,
   68,   60,  116,   51,   51,   51,   51,   51,   51,   51,
   51,   75,   51,   51,   51,  110,   51,   51,   51,   47,
   43,   47,  120,  111,  161,   47,   47,   47,   47,   47,
   47,   47,   47,   77,   47,   47,   47,  189,   47,   47,
   47,   45,   80,   45,  138,   92,  170,   45,   45,   45,
   45,   45,   45,   45,   45,   79,   45,   45,   45,    0,
   45,   45,   45,    0,    0,   46,   81,   46,    0,    0,
    0,   46,   46,   46,   46,   46,   46,   46,   46,    0,
   46,   46,   46,    0,   46,   46,   46,  130,  130,  130,
   85,  130,    0,  130,  130,  130,  130,  130,  130,  130,
  130,   83,   37,   38,   37,   38,  130,  130,  130,   69,
    0,   69,   73,   37,   38,   69,   69,   69,   69,   69,
   69,   69,   69,   90,   39,    0,    0,   40,   69,   69,
   69,    0,   68,    0,   68,    0,   75,    0,   68,   68,
   68,   68,   68,   68,   68,   68,   93,    0,    0,    0,
    0,   68,   68,   68,  120,    0,  120,    0,   77,    0,
  120,  120,  120,  120,  120,  120,  120,  120,   87,    0,
    0,    0,    0,  120,  120,  120,  138,    0,  138,    0,
   79,    0,  138,  138,  138,  138,  138,  138,  138,  138,
   63,    0,    0,    0,   16,  138,  138,  138,   81,    0,
   81,    0,    0,    0,   81,   81,   81,   81,   81,   81,
   81,   81,    0,    0,    0,    0,   54,   81,   81,   81,
    0,    0,   85,    0,   85,    0,   83,    0,   85,   85,
   85,   85,   85,   85,   85,   85,    0,    0,    0,    0,
    0,   85,   85,   85,   73,    0,   73,    0,   90,    0,
   73,   73,   73,   73,   73,   73,   73,   73,    0,    0,
    0,    0,    0,   73,   73,   73,    0,    0,   75,    0,
   75,   93,    0,    0,   75,   75,   75,   75,   75,   75,
   75,   75,    0,    0,    0,    0,    0,   75,   75,   75,
   77,    0,   77,   87,    0,    0,   77,   77,   77,   77,
   77,   77,   77,   77,    0,    0,    0,    0,    0,   77,
   77,   77,   79,    0,   79,   63,    0,    0,   79,   79,
   79,   79,   79,   79,   79,   79,    0,    0,    5,   55,
    6,   79,   79,   79,    7,    8,    0,    9,   10,   11,
   64,   12,    0,    0,    0,    0,    0,   13,   14,   15,
    5,    0,    6,    0,    0,    0,    7,   55,   83,    9,
   83,   11,   87,   12,   83,   83,   83,   83,   83,   83,
   83,   83,  105,    0,  106,   55,    0,   83,   83,   83,
   90,    0,   90,    0,  218,    0,   90,   90,   90,   90,
   90,   90,   90,   90,    0,    0,    0,    0,    0,   90,
   90,   90,    0,   93,    0,   93,  106,   55,  105,   93,
   93,   93,   93,   93,   93,   93,   93,    0,    0,    0,
    0,    0,   93,   93,   93,   87,  106,   87,  144,    0,
    0,   87,   87,   87,   87,   87,   87,   87,   87,    0,
    0,    0,  144,    0,   87,   87,   87,   63,    0,   63,
  215,    0,    0,   63,   63,   63,   63,   63,   63,    0,
   63,    0,  227,    0,  106,    0,   63,   63,   63,    0,
    0,    0,    5,    0,    6,    0,    0,    0,    7,    8,
    0,    9,   10,   11,    0,   12,    0,    0,    0,    0,
   55,   13,   14,   15,    0,    0,    5,    0,    6,    0,
    0,    0,    7,    0,    5,    9,    6,   11,    0,   12,
    7,    0,    0,    9,  106,   11,    5,   12,    6,  157,
  158,    0,    7,    8,    0,    9,   10,   11,    0,   12,
    0,    0,    0,    0,   55,   13,   14,   15,    0,    0,
    5,    0,    6,  106,    0,    0,    7,    0,    0,    9,
    0,   11,  154,   12,    0,    0,  179,  180,  182,    0,
    5,    0,    6,    0,    0,    0,    7,  184,    0,    9,
    0,   11,  154,   12,    5,    0,    6,    0,    0,    0,
    7,    0,    5,    9,    6,   11,    0,   12,    7,    0,
    0,    9,  207,   11,    5,   12,    6,    0,    0,    0,
    7,    0,    5,    9,    6,   11,    0,   12,    7,    8,
  166,    9,   10,   11,    0,   12,    0,    0,    5,    0,
    6,   13,   14,   15,    7,    8,    0,    9,   10,   11,
    0,   12,    0,    0,    0,    0,    0,   13,   14,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   43,   41,   45,   45,   59,   41,   59,   58,
   41,   59,   40,   59,   17,   36,  257,   45,   44,   60,
   61,   62,    0,   45,   58,   57,  257,   58,   18,   89,
   40,   40,  168,   59,   59,  258,   44,   41,   60,   61,
   62,   41,   42,   43,    0,   45,  125,   47,  271,   42,
  180,   59,  182,  189,   47,  257,  258,   39,   27,   59,
   60,   61,   62,   41,   59,   43,  258,   45,    0,  261,
   41,   81,   43,   63,   45,   57,  108,  207,   43,   59,
   45,   59,   60,   61,   62,   41,  146,   43,   59,   45,
    0,   41,   41,   43,   43,   45,   45,   58,   59,  276,
  277,   70,   71,   59,   60,   61,   62,   89,  257,   41,
  259,   43,    0,   45,  263,   59,   41,  266,   43,  268,
   45,  270,   40,  126,   40,  125,  136,   59,   60,   61,
   62,  257,  258,  154,  258,    0,  257,  258,   59,   94,
   95,   41,   96,   97,   40,   59,   41,  125,   41,   59,
   45,  120,   45,   40,   44,  257,  166,    0,   41,  123,
  170,   45,   59,  260,  260,   59,   41,   59,  269,  125,
  258,   59,  175,  257,   58,  257,  186,  167,  257,    0,
  259,   44,   58,  123,  263,  264,  265,  266,  267,  268,
  172,  270,  262,  125,   59,  123,  165,  276,  277,  278,
  169,    0,   44,   44,   41,  258,  257,  261,  198,   59,
  125,  257,  258,  257,  123,  125,   59,  257,  258,   59,
  268,  258,  268,  257,   59,    0,  257,  276,  277,  257,
  258,  272,  273,  274,  216,  257,  258,  125,   59,    0,
  279,  279,  276,  277,   41,  276,  277,    0,  257,  258,
  272,  273,  274,  257,  258,  123,   41,  257,   41,  259,
  125,   41,   63,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  272,  273,  274,  262,  276,  277,  278,  257,
  125,  259,  125,  262,  115,  263,  264,  265,  266,  267,
  268,  269,  270,    0,  272,  273,  274,  167,  276,  277,
  278,  257,   34,  259,  125,   40,  135,  263,  264,  265,
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
  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,  123,  276,  277,  278,
   -1,   -1,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,   -1,   -1,  257,   -1,
  259,  125,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,  125,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,  125,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,  257,   11,
  259,  276,  277,  278,  263,  264,   -1,  266,  267,  268,
  125,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,   -1,   -1,   -1,  263,   39,  257,  266,
  259,  268,  123,  270,  263,  264,  265,  266,  267,  268,
  269,  270,  125,   -1,   56,   57,   -1,  276,  277,  278,
  257,   -1,  259,   -1,  125,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,   -1,  257,   -1,  259,   88,   89,  125,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  257,  108,  259,  125,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
   -1,   -1,  125,   -1,  276,  277,  278,  257,   -1,  259,
  125,   -1,   -1,  263,  264,  265,  266,  267,  268,   -1,
  270,   -1,  125,   -1,  146,   -1,  276,  277,  278,   -1,
   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,   -1,  270,   -1,   -1,   -1,   -1,
  172,  276,  277,  278,   -1,   -1,  257,   -1,  259,   -1,
   -1,   -1,  263,   -1,  257,  266,  259,  268,   -1,  270,
  263,   -1,   -1,  266,  196,  268,  257,  270,  259,  108,
  109,   -1,  263,  264,   -1,  266,  267,  268,   -1,  270,
   -1,   -1,   -1,   -1,  216,  276,  277,  278,   -1,   -1,
  257,   -1,  259,  225,   -1,   -1,  263,   -1,   -1,  266,
   -1,  268,  269,  270,   -1,   -1,  145,  146,  147,   -1,
  257,   -1,  259,   -1,   -1,   -1,  263,  156,   -1,  266,
   -1,  268,  269,  270,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,  257,  266,  259,  268,   -1,  270,  263,   -1,
   -1,  266,  181,  268,  257,  270,  259,   -1,   -1,   -1,
  263,   -1,  257,  266,  259,  268,   -1,  270,  263,  264,
  265,  266,  267,  268,   -1,  270,   -1,   -1,  257,   -1,
  259,  276,  277,  278,  263,  264,   -1,  266,  267,  268,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
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
"con_etiqueta : ID ASIG sentencia_ctr_expr ';'",
"con_etiqueta : ID ASIG sentencia_ctr_expr",
"con_etiqueta : ID sentencia_ctr_expr ';'",
"con_etiqueta : ID ASIG ';'",
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

//#line 259 "Gramatica.y"

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

void contieneEtiqueta(String etiqueta){
	if (TablaSimbolos.contieneSimbolo(etiqueta)){
		if (!TablaSimbolos.obtenerSimbolo(etiqueta).getUso().equals("etiqueta"))
			erroresSemanticos.add("'" + Ambito.sinAmbito(etiqueta) + "' no es una etiqueta valida");
	} else
		erroresSemanticos.add("'" + Ambito.sinAmbito(etiqueta) + "' no esta declarada");

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
//#line 708 "Parser.java"
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
{verificarTipos(val_peek(2).sval,val_peek(0).sval,"+"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 46:
//#line 97 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "-"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 48:
//#line 101 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "*"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 49:
//#line 102 "Gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "/"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 51:
//#line 106 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 108 "Gramatica.y"
{yyval.sval = "-" + val_peek(0).sval;}
break;
case 54:
//#line 109 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(3).sval); comprobarAmbito(val_peek(3).sval); val_peek(2).sval = val_peek(3).sval; val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); if (val_peek(3).sval == null) chequearParametros(val_peek(2).sval, val_peek(1).ival); else chequearParametros(val_peek(3).sval, val_peek(1).ival); yyval.sval = val_peek(3).sval; TercetoManager.llamado_funcion();}
break;
case 55:
//#line 110 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); comprobarAmbito(val_peek(2).sval); val_peek(1).sval = val_peek(2).sval; val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); if (val_peek(2).sval == null) chequearParametros(val_peek(1).sval, 0); else chequearParametros(val_peek(2).sval, 0); yyval.sval = val_peek(2).sval; TercetoManager.llamado_funcion();}
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
{yyval.ival = 2;}
break;
case 60:
//#line 117 "Gramatica.y"
{yyval.ival = 1;}
break;
case 61:
//#line 118 "Gramatica.y"
{yyval.ival = 1;}
break;
case 63:
//#line 122 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 66:
//#line 129 "Gramatica.y"
{Atributo aux = TablaSimbolos.obtenerSimbolo(val_peek(0).sval); setTipo(aux.getTipo(),val_peek(2).sval); val_peek(2).sval = TablaSimbolos.modificarNombre(val_peek(2).sval);  TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 67:
//#line 130 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 68:
//#line 133 "Gramatica.y"
{if (val_peek(0).sval == null) break; comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); verificarTipos(val_peek(2).sval,val_peek(0).sval, "=:"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
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
{contieneEtiqueta(val_peek(1).sval+Ambito.getAmbitoActual()); TercetoManager.continueDoUntil();}
break;
case 87:
//#line 158 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 88:
//#line 159 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 90:
//#line 161 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 91:
//#line 162 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
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
{TablaSimbolos.agregarSimbolo(val_peek(1).sval, 257, "", AnalizadorLexico.getLine()); setUso(val_peek(1).sval,"etiqueta"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval);}
break;
case 97:
//#line 172 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 99:
//#line 178 "Gramatica.y"
{TercetoManager.add_seleccion_cond();}
break;
case 100:
//#line 181 "Gramatica.y"
{verificarTipos(val_peek(3).sval, val_peek(1).sval, val_peek(2).sval); TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 101:
//#line 182 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 102:
//#line 183 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 103:
//#line 186 "Gramatica.y"
{yyval.sval = "<";}
break;
case 104:
//#line 187 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 105:
//#line 188 "Gramatica.y"
{yyval.sval = ">";}
break;
case 106:
//#line 189 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 107:
//#line 190 "Gramatica.y"
{yyval.sval = "=";}
break;
case 108:
//#line 191 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 111:
//#line 196 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 112:
//#line 199 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 116:
//#line 209 "Gramatica.y"
{TercetoManager.add_iter_when();}
break;
case 118:
//#line 215 "Gramatica.y"
{TercetoManager.add_cond_when();}
break;
case 119:
//#line 218 "Gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 120:
//#line 219 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 121:
//#line 220 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 122:
//#line 221 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 123:
//#line 224 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 124:
//#line 225 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 125:
//#line 226 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 126:
//#line 227 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 127:
//#line 230 "Gramatica.y"
{TercetoManager.add_inicio_do_until();}
break;
case 130:
//#line 237 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 132:
//#line 241 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 133:
//#line 242 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 134:
//#line 243 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 135:
//#line 247 "Gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 136:
//#line 250 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 138:
//#line 254 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 139:
//#line 255 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1249 "Parser.java"
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
