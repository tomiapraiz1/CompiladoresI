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






//#line 2 "gramatica.y"
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
   29,   29,   29,   29,   30,   30,   30,   30,   34,   34,
   34,   36,   36,   36,   36,   36,   36,   35,   35,   35,
   37,   38,   39,   39,   11,   31,   31,   31,   31,   32,
   32,   32,   32,   40,   41,   42,   42,   33,   33,   33,
   33,   43,   44,   45,   45,   45,
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
    4,    3,    3,    3,    4,    3,    3,    3,    5,    4,
    4,    1,    1,    1,    1,    1,    1,    4,    2,    3,
    3,    3,    2,    1,    6,    4,    3,    3,    3,    5,
    4,    4,    4,    1,    1,    2,    1,    6,    5,    5,
    5,    1,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   19,   20,    0,    4,    0,    0,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,   69,   70,
    0,    0,    0,    0,   51,    0,    0,    0,    0,    0,
    0,   49,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,    0,   79,    0,  124,  114,    0,    0,   83,
    0,    0,    0,   64,    0,    5,    2,    8,   17,    0,
    0,    0,    0,    0,    0,    0,   71,   73,   75,   77,
    0,  132,    0,    0,   94,    0,    0,   90,    0,   52,
    0,    0,    0,    0,   93,    0,    0,  103,  105,  107,
  102,  104,  106,    0,    0,   97,    0,   96,  118,    0,
  119,   22,   25,   81,    0,  125,  113,    0,    0,    0,
    0,   87,   66,    0,   61,    0,    1,   16,    0,    0,
    0,   29,   28,    0,   37,   21,    0,    0,    0,    0,
   54,    0,  133,    0,    0,    0,   91,   88,    0,    0,
   47,   48,    0,    0,    0,    0,   95,  116,    0,    0,
  121,    0,  122,  123,   85,   65,   63,   18,   34,   27,
    0,    0,    0,    0,    0,   53,    0,    0,    0,    0,
  111,    0,  108,  101,    0,  100,    0,  126,  120,    0,
    0,   38,    0,   36,   26,   55,   56,   58,   57,  136,
    0,  129,  130,    0,  131,    0,   99,  115,   31,    0,
    0,   35,  134,  128,  112,    0,    0,    0,   43,   41,
    0,   40,   39,
};
final static short yydgoto[] = {                          3,
    4,   17,   18,   67,   19,   20,   21,   22,   23,   24,
   25,   26,   70,   27,   75,  136,   76,  137,  193,  194,
   40,   41,   42,  142,   63,   64,   28,   29,   30,   31,
   32,   33,   43,   46,   47,  105,   97,  183,   58,   59,
  118,  161,   84,  144,  202,
};
final static short yysindex[] = {                      -204,
    0,    0,    0,  513,  465,  -31,  -38,   -2,  -52,   -6,
  -92,   42,    0,    0, -199,    0,  683,  554,    0,    0,
    0,    0,    0,    0,    0, -190,  -33,   18,    0,    0,
   30,   35,   43,   76,    0,  601,  -45,  -43, -149,   14,
    5,    0,   68,   36,  -21, -165, -116,  123,  -37,  126,
  135,    0,  118,    0,  -78,    0,    0,  600,  600,    0,
  -49, -241,    6,    0,  554,    0,    0,    0,    0,   12,
  140,  -46, -144,  -70,   65,  148,    0,    0,    0,    0,
   67,    0,  657,  657,    0,   14,  131,    0,  134,    0,
  -39,  -39,  -39,  -39,    0,  196,  -65,    0,    0,    0,
    0,    0,    0,  -40,  -39,    0,  -63,    0,    0,  159,
    0,    0,    0,    0,   82,    0,    0,  -67,  621,  -67,
  144,    0,    0,  -51,    0, -199,    0,    0,  -48,  -47,
 -144,    0,    0,  167,    0,    0,  683,  156,  171,  176,
    0,  181,    0,  -67,  641,  -67,    0,    0,    5,    5,
    0,    0,  667,  105,  106,   80,    0,    0,  683,   -6,
    0,  -67,    0,    0,    0,    0,    0,    0,    0,    0,
 -144,  538, -144, -103, -101,    0, -225, -225,  -67, -225,
    0,  196,    0,    0,   99,    0,  576,    0,    0,  -22,
   -8,    0,  115,    0,    0,    0,    0,    0,    0,    0,
  -13,    0,    0, -225,    0,  675,    0,    0,    0,  108,
  100,    0,    0,    0,    0,  195,   79,  198,    0,    0,
  200,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  272,    0,
    0,  294,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  316,    0,    0,
  340,  362,  384,    1,    0,    0,    0,    0,    0,  158,
   23,    0,    0,    0,    0,    0,    0,    0,    0,  -30,
    0,    0,  407,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  497,    0,  262,    0,    0,    0,    0,    0,
  235,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  180,  429,    0,  451,    0,
    0,    0,    0,    0,    0,    0,  113,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  204,    0,    0,  226,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  475,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  161,    0,    0,  240,    0,    0,    0,    0,  257,  265,
    0,    0,    0,    0,    0,    0,    0,    0,   45,   69,
    0,    0,    0,  136,    0,    0,    0,    0,    0,   91,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  248,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  178,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   32,  255,   11,   15,    0,    0,    0,    0,
    0,    8,    0,    0,    0,    0,    0,    0,    0,  137,
  -18,   70,   92,    0,    0,  201,    0,    0,    0,    0,
    0,  293,  307,    3,  325,  249,    0,    0,   19,    0,
  -44,  -27,    0,  -66, -108,
};
final static int YYTABLESIZE=961;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   50,   49,   91,  111,   92,   39,   54,   72,   45,  122,
   24,  131,   55,   85,  120,   88,  123,  146,   86,  101,
  103,  102,   46,   39,   73,   57,  104,   24,   68,  124,
   56,  210,  200,   45,   74,  201,   39,   52,  101,  103,
  102,   50,   50,   50,   44,   50,   93,   50,   65,  126,
   57,   94,    1,    2,   83,  129,   91,   62,   92,   50,
   50,   50,   50,   46,  125,   46,   69,   46,   45,  203,
  128,  205,  117,   57,  162,   68,   77,  119,  179,  132,
  133,   46,   46,   46,   46,   44,  156,   44,   78,   44,
  127,  163,  164,   79,   44,  214,  106,  117,   57,   61,
   60,   80,  145,   44,   44,   44,   44,  141,   90,   45,
   57,   45,  109,   45,  153,   81,  177,  178,  180,  221,
  186,   91,   91,   92,   92,   50,   95,   45,   45,   45,
   45,   13,   14,  117,  189,  110,  185,  220,  170,  207,
  218,   91,   91,   92,   92,  108,  184,   46,  216,  127,
   39,  204,   39,  196,  197,  198,  199,   68,   96,  117,
  149,  150,  188,  109,    5,  112,    6,  117,  172,   44,
    7,  109,  211,    9,  113,   11,  114,   12,  190,   67,
  195,  115,   68,  130,  151,  152,  134,  135,  138,  147,
  187,  217,  148,   45,  110,  154,   57,   68,  157,  158,
  206,  160,  165,   98,  159,   53,  166,  121,  168,  169,
  171,   34,   35,  173,  174,  127,   68,   34,   35,  175,
  117,  176,   36,   71,   11,  117,   24,  182,   44,   13,
   14,   98,   99,  100,  209,   34,   35,  109,   67,  192,
   48,  110,   13,   14,  213,   24,   24,  135,   34,   35,
   98,   99,  100,  219,   50,   51,  222,   50,  223,   50,
  110,    3,   98,   50,   50,   50,   50,   50,   50,   50,
   50,   80,   50,   50,   50,   33,   50,   50,   50,   46,
   32,   46,   68,   30,  117,   46,   46,   46,   46,   46,
   46,   46,   46,   84,   46,   46,   46,   60,   46,   46,
   46,   44,   42,   44,   67,   59,  135,   44,   44,   44,
   44,   44,   44,   44,   44,   72,   44,   44,   44,  127,
   44,   44,   44,  139,  140,   45,  167,   45,   98,  212,
   89,   45,   45,   45,   45,   45,   45,   45,   45,   74,
   45,   45,   45,   87,   45,   45,   45,  127,  127,  127,
  117,  127,  155,  127,  127,  127,  127,  127,  127,  127,
  127,   76,   34,   35,   34,   35,  127,  127,  127,  109,
  107,  109,  135,    0,  109,  109,  109,  109,  109,  109,
  109,  109,  109,   78,    0,    0,    0,    0,  109,  109,
  109,    0,  110,    0,  110,    0,   80,  110,  110,  110,
  110,  110,  110,  110,  110,  110,   82,    0,    0,    0,
    0,  110,  110,  110,   68,    0,   68,    0,   84,    0,
   68,   68,   68,   68,   68,   68,   68,   68,   92,    0,
    0,    0,    0,   68,   68,   68,   67,    0,   67,    0,
   72,    0,   67,   67,   67,   67,   67,   67,   67,   67,
   89,    0,    5,    0,    6,   67,   67,   67,    7,    0,
   98,    9,   98,   11,   74,   12,   98,   98,   98,   98,
   98,   98,   98,   98,   86,    0,    0,    0,    0,   98,
   98,   98,  117,    0,  117,    0,   76,    0,  117,  117,
  117,  117,  117,  117,  117,  117,   62,    0,    0,    0,
    0,  117,  117,  117,  135,    0,  135,    0,   78,   39,
  135,  135,  135,  135,  135,  135,  135,  135,    0,    0,
    0,    0,   38,  135,  135,  135,    0,    0,   80,    0,
   80,   82,    0,    0,   80,   80,   80,   80,   80,   80,
   80,   80,    0,    0,    0,    0,    0,   80,   80,   80,
   84,    0,   84,   92,    0,    0,   84,   84,   84,   84,
   84,   84,   84,   84,    0,    0,    0,    0,    0,   84,
   84,   84,   72,    0,   72,   89,    0,    0,   72,   72,
   72,   72,   72,   72,   72,   72,    0,    0,    0,    0,
    0,   72,   72,   72,    0,    0,   74,    0,   74,   86,
    0,    0,   74,   74,   74,   74,   74,   74,   74,   74,
    0,    0,    0,    0,    0,   74,   74,   74,   76,    0,
   76,   62,    0,    0,   76,   76,   76,   76,   76,   76,
   76,   76,    0,    0,    0,   16,    0,   76,   76,   76,
   78,    0,   78,    0,    0,    0,   78,   78,   78,   78,
   78,   78,   78,   78,    0,    0,    0,    0,    0,   78,
   78,   78,  192,   82,    0,   82,    0,    0,    0,   82,
   82,   82,   82,   82,   82,   82,   82,    0,   66,    0,
    0,    0,   82,   82,   82,   92,    0,   92,    0,    0,
    0,   92,   92,   92,   92,   92,   92,   92,   92,    0,
  208,    0,    0,    0,   92,   92,   92,   89,    0,   89,
    0,    0,    0,   89,   89,   89,   89,   89,   89,   89,
   89,   34,   35,   82,  116,    0,   89,   89,   89,    0,
    0,   86,   36,   86,    0,   37,    0,   86,   86,   86,
   86,   86,   86,   86,   86,  116,    0,    0,    0,    0,
   86,   86,   86,   62,    0,   62,    0,    0,    0,   62,
   62,   62,   62,   62,   62,  143,   62,    0,    0,    5,
    0,    6,   62,   62,   62,    7,    8,    0,    9,   10,
   11,  143,   12,    0,    0,    0,    0,    0,   13,   14,
   15,  181,    0,    0,    5,    0,    6,    0,    0,  215,
    7,    8,  191,    9,   10,   11,    0,   12,    0,    0,
    5,    0,    6,   13,   14,   15,    7,    8,    0,    9,
   10,   11,    0,   12,    0,    0,    0,    0,    0,   13,
   14,   15,    5,    0,    6,    0,    0,    0,    7,    8,
    0,    9,   10,   11,    0,   12,    0,    0,    0,    0,
    0,   13,   14,   15,    0,    0,    5,    5,    6,    6,
    0,    0,    7,    7,    0,    9,    9,   11,   11,   12,
   12,    0,    0,    0,    0,    0,    0,    5,    0,    6,
    0,    0,    0,    7,    0,    0,    9,    0,   11,  160,
   12,    0,    0,    0,    0,    0,    0,    5,    0,    6,
    0,    0,    0,    7,    0,    0,    9,    0,   11,  160,
   12,    0,    0,    5,    0,    6,    0,    0,    0,    7,
    0,    0,    9,    5,   11,    6,   12,    0,    0,    7,
    0,    5,    9,    6,   11,    0,   12,    7,    0,    5,
    9,    6,   11,    0,   12,    7,    8,    0,    9,   10,
   11,    0,   12,    0,    0,    0,    0,    0,   13,   14,
   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   43,   41,   45,   45,   59,   41,   40,   59,
   41,   58,   10,   59,   59,   59,  258,   84,   37,   60,
   61,   62,    0,   45,   58,   11,   45,   58,   18,  271,
  123,   40,  258,   40,   27,  261,   45,   40,   60,   61,
   62,   41,   42,   43,    0,   45,   42,   47,   17,   44,
   36,   47,  257,  258,   36,   44,   43,  257,   45,   59,
   60,   61,   62,   41,   59,   43,  257,   45,    0,  178,
   59,  180,   58,   59,  119,   65,   59,   59,  145,   72,
   73,   59,   60,   61,   62,   41,  105,   43,   59,   45,
    0,  119,  120,   59,  260,  204,  262,   83,   84,   58,
   59,   59,   84,   59,   60,   61,   62,   41,  258,   41,
   96,   43,    0,   45,   96,   40,  144,  145,  146,   41,
   41,   43,   43,   45,   45,  125,   59,   59,   60,   61,
   62,  276,  277,  119,  162,    0,  155,   59,  131,   41,
   41,   43,   43,   45,   45,  262,   41,  125,   41,   59,
   45,  179,   45,  257,  258,  257,  258,    0,  123,  145,
   91,   92,  160,   41,  257,   40,  259,  153,  137,  125,
  263,   59,  191,  266,   40,  268,   59,  270,  171,    0,
  173,  260,  172,   44,   93,   94,  257,  123,   41,   59,
  159,  210,   59,  125,   59,  261,  182,  187,  262,   41,
  182,  269,   59,    0,  123,  258,  258,  257,  257,  257,
   44,  257,  258,   58,   44,  125,   59,  257,  258,   44,
  206,   41,  268,  257,  268,    0,  257,  123,  260,  276,
  277,  272,  273,  274,  257,  257,  258,  125,   59,  125,
  279,  279,  276,  277,  258,  276,  277,    0,  257,  258,
  272,  273,  274,   59,  257,  258,   59,  257,   59,  259,
  125,    0,   59,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  272,  273,  274,   41,  276,  277,  278,  257,
   41,  259,  125,  123,   59,  263,  264,  265,  266,  267,
  268,  269,  270,    0,  272,  273,  274,   41,  276,  277,
  278,  257,  125,  259,  125,   41,   59,  263,  264,  265,
  266,  267,  268,  269,  270,    0,  272,  273,  274,   65,
  276,  277,  278,  257,  258,  257,  126,  259,  125,  193,
   38,  263,  264,  265,  266,  267,  268,  269,  270,    0,
  272,  273,  274,   37,  276,  277,  278,  257,  258,  259,
  125,  261,  104,  263,  264,  265,  266,  267,  268,  269,
  270,    0,  257,  258,  257,  258,  276,  277,  278,  257,
   46,  259,  125,   -1,  262,  263,  264,  265,  266,  267,
  268,  269,  270,    0,   -1,   -1,   -1,   -1,  276,  277,
  278,   -1,  257,   -1,  259,   -1,  125,  262,  263,  264,
  265,  266,  267,  268,  269,  270,    0,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,   -1,
  263,  264,  265,  266,  267,  268,  269,  270,    0,   -1,
   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,
  125,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
    0,   -1,  257,   -1,  259,  276,  277,  278,  263,   -1,
  257,  266,  259,  268,  125,  270,  263,  264,  265,  266,
  267,  268,  269,  270,    0,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,   -1,  125,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,    0,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,  125,   45,
  263,  264,  265,  266,  267,  268,  269,  270,   -1,   -1,
   -1,   -1,   58,  276,  277,  278,   -1,   -1,  257,   -1,
  259,  125,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,  125,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,  125,   -1,   -1,  263,  264,
  265,  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,   -1,   -1,  257,   -1,  259,  125,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,  125,   -1,   -1,  263,  264,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,  123,   -1,  276,  277,  278,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  125,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,  269,  270,   -1,  125,   -1,
   -1,   -1,  276,  277,  278,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  270,   -1,
  125,   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,
   -1,   -1,   -1,  263,  264,  265,  266,  267,  268,  269,
  270,  257,  258,  123,  125,   -1,  276,  277,  278,   -1,
   -1,  257,  268,  259,   -1,  271,   -1,  263,  264,  265,
  266,  267,  268,  269,  270,  125,   -1,   -1,   -1,   -1,
  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,  125,  270,   -1,   -1,  257,
   -1,  259,  276,  277,  278,  263,  264,   -1,  266,  267,
  268,  125,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  125,   -1,   -1,  257,   -1,  259,   -1,   -1,  125,
  263,  264,  265,  266,  267,  268,   -1,  270,   -1,   -1,
  257,   -1,  259,  276,  277,  278,  263,  264,   -1,  266,
  267,  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,  268,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,   -1,   -1,  257,  257,  259,  259,
   -1,   -1,  263,  263,   -1,  266,  266,  268,  268,  270,
  270,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,  269,
  270,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  263,   -1,   -1,  266,   -1,  268,  269,
  270,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,  263,
   -1,   -1,  266,  257,  268,  259,  270,   -1,   -1,  263,
   -1,  257,  266,  259,  268,   -1,  270,  263,   -1,  257,
  266,  259,  268,   -1,  270,  263,  264,   -1,  266,  267,
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
"estruct_when : WHEN condicion THEN '{' bloque '}'",
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

//#line 246 "gramatica.y"

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
//#line 710 "Parser.java"
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
//#line 17 "gramatica.y"
{erroresSintacticos.add("Se esperaba un {");}
break;
case 3:
//#line 18 "gramatica.y"
{erroresSintacticos.add("Se esperaba un }");}
break;
case 4:
//#line 21 "gramatica.y"
{Ambito.concatenarAmbito("main");}
break;
case 5:
//#line 24 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 7:
//#line 28 "gramatica.y"
{erroresSintacticos.add("El nombre del programa no puede ser una constante");}
break;
case 17:
//#line 48 "gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 18:
//#line 49 "gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 19:
//#line 52 "gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 20:
//#line 53 "gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 21:
//#line 56 "gramatica.y"
{setTipo(funcionAux, val_peek(2).sval); Ambito.removeAmbito();}
break;
case 22:
//#line 59 "gramatica.y"
{ambitoAux = val_peek(1).sval; setTipo(val_peek(1).sval); setUso(val_peek(1).sval, "funcion"); val_peek(1).sval = TablaSimbolos.modificarNombre(val_peek(1).sval); idAux = val_peek(1).sval; yyval.sval = val_peek(1).sval; Ambito.concatenarAmbito(ambitoAux);}
break;
case 23:
//#line 60 "gramatica.y"
{erroresSintacticos.add("Se esperaba un identificador de la funcion");}
break;
case 24:
//#line 61 "gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 25:
//#line 62 "gramatica.y"
{erroresSintacticos.add("No se puede declarar una funcion con una constante como nombre");}
break;
case 26:
//#line 65 "gramatica.y"
{funcionAux = val_peek(0).sval;}
break;
case 27:
//#line 66 "gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 0); funcionAux = val_peek(0).sval;}
break;
case 28:
//#line 67 "gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 29:
//#line 68 "gramatica.y"
{erroresSintacticos.add("Falta un :");}
break;
case 30:
//#line 69 "gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo de retorno");}
break;
case 31:
//#line 72 "gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 2); setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "ParametroFuncion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "Nombre_Parametro_Funcion"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 32:
//#line 73 "gramatica.y"
{TablaSimbolos.modificarParametros(idAux, 1); setTipo(val_peek(1).sval,val_peek(0).sval);setUso(val_peek(0).sval, "ParametroFuncion"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 33:
//#line 74 "gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
break;
case 34:
//#line 75 "gramatica.y"
{erroresSintacticos.add("Los identificadores deben tener un tipo");}
break;
case 36:
//#line 79 "gramatica.y"
{erroresSintacticos.add("La funcion debe retornar un valor");}
break;
case 39:
//#line 88 "gramatica.y"
{}
break;
case 40:
//#line 89 "gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 41:
//#line 90 "gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 42:
//#line 91 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 43:
//#line 92 "gramatica.y"
{erroresSintacticos.add("Falta un valor que devolver");}
break;
case 44:
//#line 95 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval,"+"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 45:
//#line 96 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "-"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 47:
//#line 100 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "*"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 48:
//#line 101 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval, "/"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 50:
//#line 105 "gramatica.y"
{comprobarAmbito(val_peek(0).sval); val_peek(0).sval = Ambito.getAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 107 "gramatica.y"
{yyval.sval = "-" + val_peek(0).sval;}
break;
case 53:
//#line 108 "gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(3).sval); comprobarAmbito(val_peek(3).sval); val_peek(2).sval = val_peek(3).sval; val_peek(3).sval = Ambito.getAmbito(val_peek(3).sval); if (val_peek(3).sval == null) chequearParametros(val_peek(2).sval, val_peek(1).ival); else chequearParametros(val_peek(3).sval, val_peek(1).ival); yyval.sval = val_peek(3).sval;}
break;
case 54:
//#line 109 "gramatica.y"
{TablaSimbolos.eliminarSimbolo(val_peek(2).sval); comprobarAmbito(val_peek(2).sval); val_peek(1).sval = val_peek(2).sval; val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); if (val_peek(2).sval == null) chequearParametros(val_peek(1).sval, 0); else chequearParametros(val_peek(2).sval, 0); yyval.sval = val_peek(2).sval;}
break;
case 55:
//#line 112 "gramatica.y"
{yyval.ival = 2;}
break;
case 56:
//#line 113 "gramatica.y"
{yyval.ival = 2;}
break;
case 57:
//#line 114 "gramatica.y"
{yyval.ival = 2;}
break;
case 58:
//#line 115 "gramatica.y"
{yyval.ival = 2;}
break;
case 59:
//#line 116 "gramatica.y"
{yyval.ival = 1;}
break;
case 60:
//#line 117 "gramatica.y"
{yyval.ival = 1;}
break;
case 62:
//#line 121 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 65:
//#line 128 "gramatica.y"
{Atributo aux = TablaSimbolos.obtenerSimbolo(val_peek(0).sval); setTipo(aux.getTipo(),val_peek(2).sval); val_peek(2).sval = TablaSimbolos.modificarNombre(val_peek(2).sval);  TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 66:
//#line 129 "gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 67:
//#line 132 "gramatica.y"
{if (val_peek(0).sval == null) break; comprobarAmbito(val_peek(2).sval); val_peek(2).sval = Ambito.getAmbito(val_peek(2).sval); verificarTipos(val_peek(2).sval,val_peek(0).sval, "=:"); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 68:
//#line 133 "gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 72:
//#line 141 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 74:
//#line 143 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 76:
//#line 145 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 78:
//#line 147 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 79:
//#line 150 "gramatica.y"
{TercetoManager.breakDoUntil();}
break;
case 80:
//#line 151 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 82:
//#line 153 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 83:
//#line 154 "gramatica.y"
{TercetoManager.continueDoUntil();}
break;
case 84:
//#line 155 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 86:
//#line 157 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 87:
//#line 158 "gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 88:
//#line 159 "gramatica.y"
{TablaSimbolos.agregarSimbolo(val_peek(3).sval, 257, "", AnalizadorLexico.getLine()); setUso(val_peek(3).sval,"etiqueta"); val_peek(3).sval = TablaSimbolos.modificarNombre(val_peek(3).sval);}
break;
case 89:
//#line 160 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 90:
//#line 161 "gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 92:
//#line 163 "gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 93:
//#line 164 "gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 94:
//#line 165 "gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 95:
//#line 168 "gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 96:
//#line 169 "gramatica.y"
{erroresSintacticos.add("Falta la condicion del if");}
break;
case 97:
//#line 170 "gramatica.y"
{erroresSintacticos.add("Falta el cuerpo del if");}
break;
case 98:
//#line 171 "gramatica.y"
{erroresSintacticos.add("Falta un end_if");}
break;
case 99:
//#line 174 "gramatica.y"
{verificarTipos(val_peek(3).sval, val_peek(1).sval, val_peek(2).sval); TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval); TercetoManager.add_seleccion_cond();}
break;
case 100:
//#line 175 "gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 101:
//#line 176 "gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 102:
//#line 179 "gramatica.y"
{yyval.sval = "<";}
break;
case 103:
//#line 180 "gramatica.y"
{yyval.sval = "<=";}
break;
case 104:
//#line 181 "gramatica.y"
{yyval.sval = ">";}
break;
case 105:
//#line 182 "gramatica.y"
{yyval.sval = ">=";}
break;
case 106:
//#line 183 "gramatica.y"
{yyval.sval = "=";}
break;
case 107:
//#line 184 "gramatica.y"
{yyval.sval = "=!";}
break;
case 110:
//#line 189 "gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 111:
//#line 192 "gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 116:
//#line 205 "gramatica.y"
{TercetoManager.crear_terceto("out", val_peek(1).sval, "_");}
break;
case 117:
//#line 206 "gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 118:
//#line 207 "gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 119:
//#line 208 "gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 120:
//#line 211 "gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 121:
//#line 212 "gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 122:
//#line 213 "gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 123:
//#line 214 "gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 124:
//#line 217 "gramatica.y"
{Ambito.concatenarAmbito("doUntil"); TercetoManager.add_inicio_do_until();}
break;
case 125:
//#line 220 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 127:
//#line 224 "gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 129:
//#line 228 "gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 130:
//#line 229 "gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 131:
//#line 230 "gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 132:
//#line 234 "gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 133:
//#line 237 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 135:
//#line 241 "gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 136:
//#line 242 "gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1251 "Parser.java"
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
