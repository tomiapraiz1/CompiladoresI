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
import java.util.Stack;
//#line 25 "Parser.java"




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
    5,    7,    7,    7,    7,    8,    8,   13,   13,   12,
   12,    9,   14,   14,   14,   14,   15,   15,   15,   15,
   15,   17,   17,   17,   17,   16,   18,   19,   20,   20,
   20,   20,   20,   21,   21,   21,   22,   22,   22,   23,
   23,   23,   23,   23,   24,   24,   24,   24,   24,   24,
   10,   10,   25,   25,   26,   26,   27,   27,    6,    6,
   28,   28,   28,   28,   28,   28,   28,   28,   28,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   34,   33,   30,   30,   36,
   37,   39,   39,   39,   40,   40,   40,   40,   40,   40,
   38,   38,   38,   38,   41,   42,   43,   43,   11,   44,
   45,   31,   31,   31,   31,   32,   32,   32,   32,   46,
   47,   48,   48,   49,   49,   35,   35,   35,   35,   50,
   51,   52,   52,   52,
};
final static short yylen[] = {                            2,
    4,    3,    3,    1,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    3,    2,    1,    3,    1,
    1,    3,    3,    2,    2,    3,    4,    3,    2,    2,
    2,    5,    2,    1,    3,    3,    1,    1,    5,    4,
    4,    4,    4,    3,    3,    1,    3,    3,    1,    1,
    1,    2,    4,    3,    3,    3,    3,    3,    1,    1,
    3,    2,    3,    1,    3,    2,    3,    2,    1,    1,
    2,    1,    2,    1,    2,    1,    2,    1,    1,    2,
    1,    3,    2,    2,    1,    4,    3,    3,    3,    2,
    2,    3,    2,    3,    2,    2,    2,    4,    3,    1,
    1,    5,    4,    4,    1,    1,    1,    1,    1,    1,
    4,    2,    3,    2,    3,    3,    2,    1,    6,    1,
    1,    4,    3,    3,    3,    5,    4,    4,    4,    1,
    1,    2,    1,    2,    1,    6,    5,    5,    5,    1,
    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,  100,    0,    0,    0,    0,
  120,    0,    0,   20,   21,    0,    4,    0,    0,    9,
   10,   11,   12,   13,   14,   15,    0,    0,   79,    0,
   69,   70,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,    0,   97,    0,    0,    0,   49,    0,    0,
    0,    0,    0,   24,    0,    0,    0,   80,  130,  118,
    0,    0,   84,    0,    0,    0,   64,    0,    5,    2,
    8,   18,    0,    0,    0,    0,    0,    0,    0,   71,
   73,   75,   77,   91,    0,   95,    0,    0,    0,  101,
  121,    0,    0,  140,    0,    0,    0,   52,    0,    0,
    0,    0,   94,  124,    0,  125,   23,   26,    0,    0,
    0,   82,  131,  117,    0,    0,    0,    0,   88,   66,
    0,   61,    0,    1,   16,    0,    0,    0,   30,   29,
    0,   37,   22,    0,    0,   89,   92,  106,  108,  110,
  105,  107,  109,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   54,    0,  141,    0,    0,    0,    0,    0,
   47,   48,  122,   43,   41,    0,   40,    0,  127,    0,
  128,  129,   86,   65,   63,   19,   35,   28,    0,    0,
    0,    0,    0,    0,    0,   98,  114,    0,    0,    0,
   53,    0,    0,    0,    0,    0,   39,  132,  126,    0,
   38,   36,   27,  104,    0,  103,    0,  115,    0,   55,
   56,   58,   57,  134,  144,    0,  137,  138,    0,  139,
   32,  102,    0,  111,  119,  142,  136,    0,  116,
};
final static short yydgoto[] = {                          3,
    4,   18,   19,   70,   20,   21,   22,   23,   24,   25,
   26,   27,   73,   28,   78,  133,   79,  134,  202,   29,
   46,   47,   48,  154,   66,   67,   30,   31,   32,   33,
   34,   35,   36,   37,   49,   38,   89,  148,   90,  145,
  149,  224,   61,   39,   92,   62,  115,  169,  193,   96,
  156,  217,
};
final static short yysindex[] = {                      -227,
    0,    0,    0,  559,  465,    0,  -38,   -8,  -28,  -52,
    0, -104,   50,    0,    0, -198,    0,  740,  584,    0,
    0,    0,    0,    0,    0,    0, -187,  -33,    0,   17,
    0,    0,   22,   40,   44,  -43,  -23,   77,   77,   87,
    0,  645,   73,    0, -125,   49,   31,    0,   78,  100,
  -37,  114,  120,    0,  -35,   57,  104,    0,    0,    0,
  687,  687,    0,  -50, -221,   12,    0,  584,    0,    0,
    0,    0,   36,  102,  -58, -161,  -92,   48,  127,    0,
    0,    0,    0,    0,  110,    0,  117,  -21, -108,    0,
    0,  -78,   -3,    0,  705,  705,   49,    0,   73,   73,
   73,   73,    0,    0,  137,    0,    0,    0,  126,   34,
  128,    0,    0,    0,  -80,  655,  -80,  131,    0,    0,
  -66,    0, -198,    0,    0,  -64,  -60, -161,    0,    0,
  154,    0,    0,  740,  141,    0,    0,    0,    0,    0,
    0,    0,    0,  -40,   73,   82,  -82,  -61,  -51,   86,
  159,  167,    0,  171,    0,  -56,  669,  -56,   31,   31,
    0,    0,    0,    0,    0,  156,    0,   77,    0,  -80,
    0,    0,    0,    0,    0,    0,    0,    0, -161,  600,
 -161,  106,   79,  -41,  715,    0,    0,  740, -118, -113,
    0,   77, -207, -207,  -56, -207,    0,    0,    0,  -36,
    0,    0,    0,    0,   80,    0,  108,    0,  622,    0,
    0,    0,    0,    0,    0,  -18,    0,    0, -207,    0,
    0,    0,  -82,    0,    0,    0,    0,  730,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  272,
    0,    0,  294,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  316,
    0,    0,  340,  362,  384,    0,    0,    0,    0,    1,
    0,    0,  -12,    0,    0,  136,   23,    0,    0,    0,
    0,  -30,    0,    0,    0,    0,  407,    0,    0,    0,
    0,    0,    0,    0,    0,  519,    0,  235,    0,    0,
    0,    0,  543,  216,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  429,    0,  451,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  158,    0,    0,    0,
    0,    0,    0,    0,  180,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  475,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  139,    0,    0,
  218,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  204,    0,    0,
  240,  243,    0,    0,    0,    0,    0,    0,   45,   69,
    0,    0,    0,    0,    0,  497,    0,  226,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   91,    0,    0,    0,    0,    0,    0,
    0, -126,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  113,    0,    0,    0,
    0,    0,    0,    0,    0,  248,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    8,  208,   -1,  737,    0,    0,    0,    0,
    0,   21,    0,    0,    0,    0,    0,    0,    0,    0,
   46,   74,   55,    0,    0,  175,    0,    0,    0,    0,
    0,  267,    0,    0,  269,    0,    0,    0,  -25,  176,
  178,    0,   -9,    0,    0,    0,    3,  -59, -123,    0,
  -83, -167,
};
final static int YYTABLESIZE=1018;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        128,
   50,   51,   99,  106,  100,  109,   58,   75,  119,   45,
   25,   55,  158,   91,  147,   84,   45,   71,   59,  141,
  143,  142,   46,   45,   76,   68,  218,   25,  220,    1,
    2,   54,   95,  194,  196,   86,  120,  153,  141,  143,
  142,   50,   50,   50,   44,   50,   96,   50,   77,  121,
  215,  227,  116,  216,   56,  123,  171,  172,   65,   50,
   50,   50,   50,   46,  117,   46,   71,   46,   45,   72,
  122,  219,  101,  195,  166,   80,   99,  102,  100,  126,
   81,   46,   46,   46,   46,   44,  157,   44,   97,   44,
  112,   99,  165,  100,  125,  129,  130,  111,   82,   99,
  110,  100,   83,   44,   44,   44,   44,   64,   63,   45,
  199,   45,  113,   45,   14,   15,   88,   45,  170,  206,
  222,   99,   99,  100,  100,   50,   93,   45,   45,   45,
   45,  135,   98,  144,  135,   68,  103,  185,  210,  211,
  104,  180,  198,  212,  213,  127,  204,   46,  178,  112,
   45,  146,    5,  107,    6,  161,  162,   67,    7,  108,
    9,   10,  112,   12,  131,   13,  214,  135,  136,   44,
  132,  113,  159,  160,    5,  137,    6,  163,   71,  123,
    7,  150,    9,   10,  164,   12,  167,   13,  168,  173,
  183,  174,  176,   45,   68,  209,  177,  179,  181,  200,
  186,  203,  189,   99,  147,   57,  118,   71,  188,  187,
  190,  191,  192,  228,  197,  112,   67,   14,   15,  207,
  221,   40,   41,   74,   12,  133,   25,  205,   40,   41,
  223,  138,  139,  140,    3,   40,   41,  113,  123,  226,
   50,  105,   14,   15,   42,   25,   25,  143,   52,   53,
  138,  139,  140,  151,  152,   96,   34,   50,   33,   50,
   68,   31,   99,   50,   50,   50,   50,   50,   50,   50,
   50,   81,   50,   50,   50,  124,   50,   50,   50,   46,
   60,   46,   67,   59,  133,   46,   46,   46,   46,   46,
   46,   46,   46,   85,   46,   46,   46,  175,   46,   46,
   46,   44,   85,   44,  123,   87,  143,   44,   44,   44,
   44,   44,   44,   44,   44,   72,   44,   44,   44,  182,
   44,   44,   44,  184,    0,   45,    0,   45,   99,   40,
   41,   45,   45,   45,   45,   45,   45,   45,   45,   74,
   45,   45,   45,    0,   45,   45,   45,  112,    0,  112,
  133,    0,  112,  112,  112,  112,  112,  112,  112,  112,
  112,   76,   40,   41,    0,    0,  112,  112,  112,  113,
    0,  113,  143,    0,  113,  113,  113,  113,  113,  113,
  113,  113,  113,   78,    0,    0,    0,    0,  113,  113,
  113,    0,   68,    0,   68,    0,   81,    0,   68,   68,
   68,   68,   68,   68,   68,   68,   83,    0,    0,    0,
    0,   68,   68,   68,   67,    0,   67,    0,   85,    0,
   67,   67,   67,   67,   67,   67,   67,   67,   90,    0,
    0,    0,    0,   67,   67,   67,  123,    0,  123,    0,
   72,    0,  123,  123,  123,  123,  123,  123,  123,  123,
   93,    0,    0,    0,    0,  123,  123,  123,    0,    0,
   99,    0,   99,    0,   74,    0,   99,   99,   99,   99,
   99,   99,   99,   99,   87,    0,    0,    0,    0,   99,
   99,   99,  133,    0,  133,    0,   76,    0,  133,  133,
  133,  133,  133,  133,  133,  133,   42,    0,    0,    0,
    0,  133,  133,  133,  143,    0,  143,    0,   78,   45,
  143,  143,  143,  143,  143,  143,  143,  143,   62,    0,
    0,    0,   44,  143,  143,  143,    0,    0,   81,    0,
   81,   83,    0,    0,   81,   81,   81,   81,   81,   81,
   81,   81,   17,    0,    0,    0,    0,   81,   81,   81,
   85,    0,   85,   90,    0,    0,   85,   85,   85,   85,
   85,   85,   85,   85,    0,    0,    0,    0,    0,   85,
   85,   85,   72,    0,   72,   93,    0,    0,   72,   72,
   72,   72,   72,   72,   72,   72,    0,    0,    0,    0,
    0,   72,   72,   72,    0,    0,   74,    0,   74,   87,
    0,    0,   74,   74,   74,   74,   74,   74,   74,   74,
    0,    0,    0,    0,    0,   74,   74,   74,   76,    0,
   76,   42,    0,    0,   76,   76,   76,   76,   76,   76,
   76,   76,    0,    0,    0,    0,    0,   76,   76,   76,
   78,    0,   78,   62,    0,    0,   78,   78,   78,   78,
   78,   78,   78,   78,    0,    0,    0,    0,    0,   78,
   78,   78,    0,   83,    0,   83,    0,   17,    0,   83,
   83,   83,   83,   83,   83,   83,   83,    0,    0,    0,
    0,   17,   83,   83,   83,   90,    0,   90,    0,    0,
    0,   90,   90,   90,   90,   90,   90,   90,   90,    0,
    0,    0,    0,    0,   90,   90,   90,   93,   69,   93,
    0,    0,    0,   93,   93,   93,   93,   93,   93,   93,
   93,   40,   41,    0,  201,    0,   93,   93,   93,    0,
    0,   87,   42,   87,    0,   43,    0,   87,   87,   87,
   87,   87,   87,   87,   87,    0,  225,    0,   60,    0,
   87,   87,   87,   42,    0,   42,    0,    0,    0,   42,
   42,   42,   42,   42,   42,   42,   42,   94,    0,    0,
    0,    0,   42,   42,   42,   62,    0,   62,   60,  113,
    0,   62,   62,   62,   62,   62,   62,    0,   62,    0,
    0,    0,    0,  155,   62,   62,   62,  114,   60,   17,
    0,   17,    0,    0,    0,   17,   17,   17,   17,   17,
   17,  113,   17,    0,    0,    5,    0,    6,   17,   17,
   17,    7,    8,    9,   10,   11,   12,    0,   13,  155,
    0,  114,   60,    0,   14,   15,   16,    0,    0,  208,
    5,    0,    6,    0,    0,    0,    7,    8,    9,   10,
   11,   12,  114,   13,  229,    0,    5,    0,    6,   14,
   15,   16,    7,    8,    9,   10,   11,   12,    0,   13,
    0,    0,    0,    0,    0,   14,   15,   16,    5,    0,
    6,    0,    0,   60,    7,    8,    9,   10,   11,   12,
    0,   13,    0,  114,    0,    0,    0,   14,   15,   16,
    0,    5,    0,    6,    0,    0,    0,    7,    0,    9,
   10,    5,   12,    6,   13,    0,    0,    7,    0,    9,
   10,  114,   12,  168,   13,    5,    0,    6,    0,    0,
    0,    7,    0,    9,   10,    0,   12,  192,   13,    0,
    0,    0,    0,    5,    0,    6,    0,    0,    0,    7,
    0,    9,   10,    0,   12,    0,   13,    0,    0,   60,
    0,    5,    0,    6,  114,    0,    0,    7,    0,    9,
   10,    5,   12,    6,   13,    0,    0,    7,    0,    9,
   10,    0,   12,    0,   13,    0,    5,    0,    6,    0,
    0,    0,    7,    0,    9,   10,    5,   12,    6,   13,
    0,    0,    7,    8,    9,   10,   11,   12,    0,   13,
    0,    0,    0,    0,    0,   14,   15,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         58,
    0,   40,   43,   41,   45,   41,   59,   41,   59,   45,
   41,   40,   96,   39,  123,   59,   45,   19,  123,   60,
   61,   62,    0,   45,   58,   18,  194,   58,  196,  257,
  258,   40,   42,  157,  158,   59,  258,   41,   60,   61,
   62,   41,   42,   43,    0,   45,   59,   47,   28,  271,
  258,  219,   62,  261,    9,   44,  116,  117,  257,   59,
   60,   61,   62,   41,   62,   43,   68,   45,    0,  257,
   59,  195,   42,  157,   41,   59,   43,   47,   45,   44,
   59,   59,   60,   61,   62,   41,   96,   43,   43,   45,
    0,   43,   59,   45,   59,   75,   76,   41,   59,   43,
   55,   45,   59,   59,   60,   61,   62,   58,   59,   41,
  170,   43,    0,   45,  276,  277,   40,   45,  116,   41,
   41,   43,   43,   45,   45,  125,   40,   59,   60,   61,
   62,  258,  258,   88,  261,    0,   59,  147,  257,  258,
   41,  134,  168,  257,  258,   44,   41,  125,  128,   59,
   45,  260,  257,   40,  259,  101,  102,    0,  263,   40,
  265,  266,   59,  268,  257,  270,  192,   41,   59,  125,
  123,   59,   99,  100,  257,   59,  259,   41,  180,    0,
  263,  260,  265,  266,   59,  268,   59,  270,  269,   59,
  145,  258,  257,  125,   59,  188,  257,   44,   58,  179,
  262,  181,   44,    0,  123,  258,  257,  209,  123,  261,
   44,   41,  269,  223,   59,  125,   59,  276,  277,  261,
  257,  257,  258,  257,  268,    0,  257,  182,  257,  258,
  123,  272,  273,  274,    0,  257,  258,  125,   59,  258,
  279,  279,  276,  277,  268,  276,  277,    0,  257,  258,
  272,  273,  274,  257,  258,  268,   41,  257,   41,  259,
  125,  123,   59,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  272,  273,  274,   68,  276,  277,  278,  257,
   41,  259,  125,   41,   59,  263,  264,  265,  266,  267,
  268,  269,  270,    0,  272,  273,  274,  123,  276,  277,
  278,  257,   36,  259,  125,   37,   59,  263,  264,  265,
  266,  267,  268,  269,  270,    0,  272,  273,  274,  144,
  276,  277,  278,  146,   -1,  257,   -1,  259,  125,  257,
  258,  263,  264,  265,  266,  267,  268,  269,  270,    0,
  272,  273,  274,   -1,  276,  277,  278,  257,   -1,  259,
  125,   -1,  262,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  257,  258,   -1,   -1,  276,  277,  278,  257,
   -1,  259,  125,   -1,  262,  263,  264,  265,  266,  267,
  268,  269,  270,    0,   -1,   -1,   -1,   -1,  276,  277,
  278,   -1,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,    0,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,    0,   -1,
   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,
  125,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
    0,   -1,   -1,   -1,   -1,  276,  277,  278,   -1,   -1,
  257,   -1,  259,   -1,  125,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,    0,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,    0,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,   45,
  263,  264,  265,  266,  267,  268,  269,  270,    0,   -1,
   -1,   -1,   58,  276,  277,  278,   -1,   -1,  257,   -1,
  259,  125,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,    0,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,  125,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,  125,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,   -1,   -1,  257,   -1,  259,  125,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,  125,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,  125,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,   -1,  257,   -1,  259,   -1,  125,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,
   -1,  123,  276,  277,  278,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  257,  125,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  257,  258,   -1,  125,   -1,  276,  277,  278,   -1,
   -1,  257,  268,  259,   -1,  271,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,   -1,  125,   -1,   12,   -1,
  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,  123,   -1,   -1,
   -1,   -1,  276,  277,  278,  257,   -1,  259,   42,  125,
   -1,  263,  264,  265,  266,  267,  268,   -1,  270,   -1,
   -1,   -1,   -1,  125,  276,  277,  278,   61,   62,  257,
   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,  267,
  268,  125,  270,   -1,   -1,  257,   -1,  259,  276,  277,
  278,  263,  264,  265,  266,  267,  268,   -1,  270,  125,
   -1,   95,   96,   -1,  276,  277,  278,   -1,   -1,  125,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,
  267,  268,  116,  270,  125,   -1,  257,   -1,  259,  276,
  277,  278,  263,  264,  265,  266,  267,  268,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,   -1,   -1,  147,  263,  264,  265,  266,  267,  268,
   -1,  270,   -1,  157,   -1,   -1,   -1,  276,  277,  278,
   -1,  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  265,
  266,  257,  268,  259,  270,   -1,   -1,  263,   -1,  265,
  266,  185,  268,  269,  270,  257,   -1,  259,   -1,   -1,
   -1,  263,   -1,  265,  266,   -1,  268,  269,  270,   -1,
   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
   -1,  265,  266,   -1,  268,   -1,  270,   -1,   -1,  223,
   -1,  257,   -1,  259,  228,   -1,   -1,  263,   -1,  265,
  266,  257,  268,  259,  270,   -1,   -1,  263,   -1,  265,
  266,   -1,  268,   -1,  270,   -1,  257,   -1,  259,   -1,
   -1,   -1,  263,   -1,  265,  266,  257,  268,  259,  270,
   -1,   -1,  263,  264,  265,  266,  267,  268,   -1,  270,
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
"declaracion_variables : tipo lista_variables",
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
"seleccion : inicio_if condicion_if cuerpo_if",
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
"cuerpo_if : cuerpo_then ELSE",
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

//#line 279 "Gramatica.y"

public String idAux = "";
public String tipoAux = "";
public String ambitoAux = "";
public String funcionAux = "";
public Stack<String> pilaAux = new Stack<String>();
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
				erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : Los tipos de los parametros no coinciden");
		}
	}
}

void chequearParametros(String simbolo, int cantidad){
	if (TablaSimbolos.contieneSimbolo(simbolo)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(simbolo);
		if (aux.getUso().equals("funcion")){
			if (aux.getCantidadParametros() != cantidad)
				erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : No coinciden la cantidad de parametros de '" + Ambito.sinAmbito(simbolo) + "'");
		} else
			erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : '" + simbolo + "' no es una funcion");
	} else
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : La funcion '" + simbolo + "' no esta declarada");
}

void contieneEtiqueta(String etiqueta){
	if (TablaSimbolos.contieneSimbolo(etiqueta)){
		if (!TablaSimbolos.obtenerSimbolo(etiqueta).getUso().equals("etiqueta"))
			erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : '" + Ambito.sinAmbito(etiqueta) + "' no es una etiqueta valida");
	} else
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : '" + Ambito.sinAmbito(etiqueta) + "' no esta declarada");

}

void esConstante(String simbolo){
	if (TablaSimbolos.contieneSimbolo(simbolo)){
		Atributo aux = TablaSimbolos.obtenerSimbolo(simbolo);
		if (aux.getUso().equals("constante"))
			erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : No se puede asignar un valor a una constante");
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
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getLine()+" : '" + simbolo + "' no esta al alcance");
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
//#line 768 "Parser.java"
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
//#line 18 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un {");}
break;
case 3:
//#line 19 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un }");}
break;
case 4:
//#line 22 "Gramatica.y"
{Ambito.concatenarAmbito("main");}
break;
case 5:
//#line 25 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 7:
//#line 29 "Gramatica.y"
{erroresSintacticos.add("El nombre del programa no puede ser una constante");}
break;
case 17:
//#line 47 "Gramatica.y"
{erroresSintacticos.add("Falta un ';' en la linea: " + Integer.toString(AnalizadorLexico.getLine() - 1));}
break;
case 18:
//#line 50 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 19:
//#line 51 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 20:
//#line 54 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 21:
//#line 55 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 22:
//#line 58 "Gramatica.y"
{setTipo(funcionAux, val_peek(2).sval); Ambito.removeAmbito();}
break;
case 23:
//#line 61 "Gramatica.y"
{ambitoAux = val_peek(1).sval;; setTipo(val_peek(1).sval); setUso(val_peek(1).sval, "funcion"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); idAux = val_peek(1).sval; yyval.sval = val_peek(1).sval; TercetoManager.add_funcion(val_peek(1).sval); Ambito.concatenarAmbito(ambitoAux);}
break;
case 24:
//#line 62 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un identificador de la funcion");}
break;
case 25:
//#line 63 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 26:
//#line 64 "Gramatica.y"
{erroresSintacticos.add("No se puede declarar una funcion con una constante como nombre");}
break;
case 27:
//#line 67 "Gramatica.y"
{funcionAux = val_peek(0).sval;}
break;
case 28:
//#line 68 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 0); funcionAux = val_peek(0).sval;}
break;
case 29:
//#line 69 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 30:
//#line 70 "Gramatica.y"
{erroresSintacticos.add("Falta un :");}
break;
case 31:
//#line 71 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo de retorno");}
break;
case 32:
//#line 74 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 2); TablaSimbolos.modificarTipoParametros(idAux, val_peek(4).sval, val_peek(1).sval); setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "ParametroFuncion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 33:
//#line 75 "Gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 1); TablaSimbolos.modificarTipoParametros(idAux, val_peek(1).sval, ""); setTipo(val_peek(1).sval,val_peek(0).sval);setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 34:
//#line 76 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
break;
case 35:
//#line 77 "Gramatica.y"
{erroresSintacticos.add("Los identificadores deben tener un tipo");}
break;
case 39:
//#line 89 "Gramatica.y"
{TercetoManager.add_return_funcion(funcionAux, val_peek(2).sval);}
break;
case 40:
//#line 90 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 41:
//#line 91 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 42:
//#line 92 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 43:
//#line 93 "Gramatica.y"
{erroresSintacticos.add("Falta un valor que devolver");}
break;
case 44:
//#line 96 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "+");
								if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
								else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 45:
//#line 99 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "-");
								if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
								else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 47:
//#line 105 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "*");
					if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
					else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 48:
//#line 108 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(2).sval,val_peek(0).sval, "/");
					if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
					else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 50:
//#line 114 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 116 "Gramatica.y"
{/*ChequearRangoNegativo($2.sval)*/;yyval.sval = "-" + val_peek(0).sval;}
break;
case 53:
//#line 117 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(3).sval); comprobarAmbito(val_peek(3).sval); val_peek(2).sval = val_peek(3).sval; val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); if (val_peek(3).sval == null) chequearParametros(val_peek(2).sval, val_peek(1).ival); else chequearParametros(val_peek(3).sval, val_peek(1).ival); chequearTipoParametros(val_peek(3).sval, parametro1, parametro2); TercetoManager.llamado_funcion(val_peek(3).sval); yyval.sval = "["+(TercetoManager.getIndexTerceto()-1)+"]";}
break;
case 54:
//#line 118 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); comprobarAmbito(val_peek(2).sval); val_peek(1).sval = val_peek(2).sval; val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); if (val_peek(2).sval == null) chequearParametros(val_peek(1).sval, 0); else chequearParametros(val_peek(2).sval, 0); TercetoManager.llamado_funcion(val_peek(2).sval); yyval.sval = "["+(TercetoManager.getIndexTerceto()-1)+"]";}
break;
case 55:
//#line 121 "Gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); TablaSimbolos.eliminarSimbolo(val_peek(0).sval);yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval + Ambito.getAmbitoActual()); parametro2 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual()); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 56:
//#line 122 "Gramatica.y"
{yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval + Ambito.getAmbitoActual()); parametro2 = getTipoParametro(val_peek(0).sval); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 57:
//#line 123 "Gramatica.y"
{yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval); parametro2 = getTipoParametro(val_peek(0).sval); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 58:
//#line 124 "Gramatica.y"
{yyval.ival = 2; parametro1 = getTipoParametro(val_peek(2).sval); parametro2 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual()); TercetoManager.crear_terceto("Parametros", val_peek(2).sval, val_peek(0).sval, "");}
break;
case 59:
//#line 125 "Gramatica.y"
{yyval.ival = 1; parametro1 = getTipoParametro(val_peek(0).sval); TercetoManager.crear_terceto("Parametros", val_peek(0).sval, "", "");}
break;
case 60:
//#line 126 "Gramatica.y"
{yyval.ival = 1; parametro1 = getTipoParametro(val_peek(0).sval + Ambito.getAmbitoActual()); TercetoManager.crear_terceto("Parametros", val_peek(0).sval, "", "");}
break;
case 62:
//#line 130 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 65:
//#line 137 "Gramatica.y"
{Atributo aux = TablaSimbolos.obtenerSimbolo(val_peek(0).sval); setTipo(aux.getTipo(),val_peek(2).sval); setUso(val_peek(2).sval, "constante"); val_peek(2).sval = TablaSimbolos.modificarNombre(val_peek(2).sval);  TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 66:
//#line 138 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 67:
//#line 141 "Gramatica.y"
{if (val_peek(0).sval == null) break; comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); esConstante(val_peek(2).sval); int indice = TercetoManager.getIndexTerceto();
					verificarTipos(val_peek(2).sval,val_peek(0).sval, "=:"); if (indice == TercetoManager.getIndexTerceto()){ yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
					else {yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()-1) + ']';}}
break;
case 68:
//#line 144 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 72:
//#line 152 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 74:
//#line 154 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 76:
//#line 156 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 78:
//#line 158 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 80:
//#line 162 "Gramatica.y"
{TercetoManager.breakDoUntil();}
break;
case 81:
//#line 163 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 82:
//#line 164 "Gramatica.y"
{TercetoManager.add_break_cte(pilaAux.pop(), val_peek(1).sval, TablaSimbolos.obtenerSimbolo(val_peek(1).sval).getTipo());}
break;
case 83:
//#line 165 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 84:
//#line 166 "Gramatica.y"
{TercetoManager.continueDoUntil();}
break;
case 85:
//#line 167 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 86:
//#line 168 "Gramatica.y"
{contieneEtiqueta(val_peek(1).sval+Ambito.getAmbitoActual()); TercetoManager.continueDoUntilEtiqueta(val_peek(1).sval);}
break;
case 87:
//#line 169 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 88:
//#line 170 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 89:
//#line 171 "Gramatica.y"
{}
break;
case 90:
//#line 172 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 91:
//#line 173 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 92:
//#line 174 "Gramatica.y"
{TercetoManager.add_fin_id_asig();}
break;
case 93:
//#line 175 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 94:
//#line 176 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 95:
//#line 177 "Gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 96:
//#line 180 "Gramatica.y"
{comprobarAmbito(val_peek(1).sval); val_peek(1).sval = Ambito.getAmbito(val_peek(1).sval); pilaAux.push(val_peek(1).sval); pilaAux.push(val_peek(1).sval); TercetoManager.add_inicio_id_asig();}
break;
case 97:
//#line 183 "Gramatica.y"
{TablaSimbolos.agregarSimbolo(val_peek(1).sval, 257, "", AnalizadorLexico.getLine()); setUso(val_peek(1).sval,"etiqueta"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); TablaSimbolos.setTercetoSalto(val_peek(1).sval,"[" + TercetoManager.getIndexTerceto() + "]");}
break;
case 98:
//#line 186 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 99:
//#line 187 "Gramatica.y"
{erroresSintacticos.add("Falta la palabra reservada"+" end_if "+" en la linea: " + AnalizadorLexico.getLine());}
break;
case 101:
//#line 193 "Gramatica.y"
{TercetoManager.add_seleccion_cond();}
break;
case 102:
//#line 196 "Gramatica.y"
{int indice = TercetoManager.getIndexTerceto(); verificarTipos(val_peek(3).sval, val_peek(1).sval, val_peek(2).sval); if (indice == TercetoManager.getIndexTerceto()) TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 103:
//#line 197 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 104:
//#line 198 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 105:
//#line 201 "Gramatica.y"
{yyval.sval = "<";}
break;
case 106:
//#line 202 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 107:
//#line 203 "Gramatica.y"
{yyval.sval = ">";}
break;
case 108:
//#line 204 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 109:
//#line 205 "Gramatica.y"
{yyval.sval = "=";}
break;
case 110:
//#line 206 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 113:
//#line 211 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 114:
//#line 212 "Gramatica.y"
{erroresSintacticos.add("Falta la palabra reservada" + " else " +  "en la linea " + AnalizadorLexico.getLine());}
break;
case 115:
//#line 215 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 119:
//#line 225 "Gramatica.y"
{TercetoManager.add_iter_when();}
break;
case 121:
//#line 231 "Gramatica.y"
{TercetoManager.add_cond_when();}
break;
case 122:
//#line 234 "Gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 123:
//#line 235 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 124:
//#line 236 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 125:
//#line 237 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 126:
//#line 240 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 127:
//#line 241 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 128:
//#line 242 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 129:
//#line 243 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 130:
//#line 246 "Gramatica.y"
{TercetoManager.add_inicio_do_until();}
break;
case 132:
//#line 252 "Gramatica.y"
{}
break;
case 133:
//#line 253 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 134:
//#line 256 "Gramatica.y"
{TercetoManager.add_condicion_id_asig();}
break;
case 135:
//#line 257 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 137:
//#line 261 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 138:
//#line 262 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 139:
//#line 263 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 142:
//#line 273 "Gramatica.y"
{TercetoManager.add_else_cte(pilaAux.pop(), val_peek(0).sval, TablaSimbolos.obtenerSimbolo(val_peek(0).sval).getTipo());}
break;
case 143:
//#line 274 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 144:
//#line 275 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1351 "Parser.java"
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
