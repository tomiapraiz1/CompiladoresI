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
   13,   13,   13,   13,   13,   13,   13,   15,   15,   15,
   15,   14,   14,   16,   18,   17,   17,   17,   17,   17,
   19,   19,   19,   20,   20,   20,   21,   21,   21,   21,
   22,   22,   22,   22,   10,   10,   23,   23,   24,   24,
    6,    6,   25,   25,   25,   25,   25,   25,   25,   25,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   27,   27,   27,   27,
   31,   31,   31,   33,   33,   33,   33,   33,   33,   32,
   32,   32,   34,   35,   36,   36,   28,   28,   28,   28,
   29,   29,   29,   29,   37,   38,   39,   39,   30,   30,
   30,   30,   40,   41,   42,   42,   42,
};
final static short yylen[] = {                            2,
    4,    3,    3,    1,    1,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    3,    1,    3,    1,    1,    2,
    7,    6,    5,    5,    5,    5,    5,    5,    2,    1,
    3,    4,    3,    1,    1,    5,    4,    4,    4,    4,
    3,    3,    1,    3,    3,    1,    1,    1,    2,    4,
    3,    3,    1,    1,    3,    2,    3,    1,    3,    2,
    1,    1,    2,    1,    2,    1,    2,    1,    2,    1,
    2,    1,    3,    2,    2,    1,    4,    3,    3,    4,
    3,    3,    4,    3,    3,    3,    4,    3,    3,    3,
    5,    4,    4,    1,    1,    1,    1,    1,    1,    4,
    2,    3,    3,    3,    2,    1,    4,    3,    3,    3,
    5,    4,    4,    4,    1,    1,    2,    1,    6,    5,
    5,    5,    1,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    6,    7,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   18,   19,    0,    4,    0,    0,    9,   10,   11,
   12,   13,   14,    0,    0,    0,   61,   62,    0,    0,
    0,    0,   48,    0,    0,    0,    0,    0,    0,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   71,  115,  106,    0,    0,   75,    0,    0,    0,   58,
    0,    5,    2,    8,   16,    0,   34,   20,    0,   63,
   65,   67,   69,    0,  123,    0,    0,   86,    0,    0,
   82,    0,   49,    0,    0,    0,    0,   85,    0,    0,
   95,   97,   99,   94,   96,   98,    0,    0,   89,    0,
   88,  109,    0,  110,    0,    0,    0,   73,  116,  105,
    0,    0,    0,    0,   79,    0,   55,    0,    1,   15,
    0,    0,   54,   53,    0,  124,    0,    0,    0,   83,
   80,    0,    0,   44,   45,    0,    0,    0,    0,   87,
  107,    0,    0,    0,    0,    0,    0,    0,    0,  112,
    0,  113,  114,   77,   57,   17,    0,   35,    0,   33,
    0,   50,    0,    0,    0,    0,  103,    0,  100,   93,
    0,   92,    0,    0,   25,   24,    0,    0,   23,   27,
  117,  111,    0,    0,   32,   51,   52,  127,    0,  120,
  121,    0,  122,    0,   91,   31,   22,    0,    0,    0,
    0,    0,  125,  119,  104,    0,   21,   40,   38,    0,
   37,   28,   36,
};
final static short yydgoto[] = {                          3,
    4,   16,   17,   63,   18,   19,   20,   21,   22,   23,
   24,   66,   25,   68,  146,   69,  159,  160,   38,   39,
   40,  125,   59,   26,   27,   28,   29,   30,   31,   41,
   44,   45,   98,   90,  169,   54,   55,  111,  150,   77,
  127,  190,
};
final static short yysindex[] = {                      -179,
    0,    0,    0,  -93,  -43,  -29,  -40,   -1,  -53,  -71,
   58,    0,    0, -248,    0,  669,  576,    0,    0,    0,
    0,    0,    0, -230,  -52,   -6,    0,    0,   35,   68,
   76,  107,    0,  264,  -31,  -56, -107,   44,  -16,    0,
  109,   34,  -24, -160,  -86,  139,  -37,   80,  146,  132,
    0,    0,    0,  621,  621,    0,  -54,  -35,   11,    0,
  576,    0,    0,    0,    0,   15,    0,    0,  669,    0,
    0,    0,    0, -133,    0,  622,  622,    0,   44,  134,
    0,  142,    0,   -5,   -5,   -5,   -5,    0,  -57,  -63,
    0,    0,    0,    0,    0,    0,  -27,   -5,    0,  -55,
    0,    0,  167,    0,  -33,  152,  159,    0,    0,    0,
  -49,  598,  -49,  162,    0,   -5,    0, -248,    0,    0,
  -25,  560,    0,    0,   10,    0,  -49,  606,  -49,    0,
    0,  -16,  -16,    0,    0,  639,  128,   91,   60,    0,
    0,  194,    7, -131,   -3,  214, -131, -131,  217,    0,
  -49,    0,    0,    0,    0,    0,  -28,    0,  136,    0,
 -103,    0, -186, -186,  -49, -186,    0,  -57,    0,    0,
   98,    0,    2, -131,    0,    0,  224,  218,    0,    0,
    0,    0,  108,  124,    0,    0,    0,    0,   32,    0,
    0, -186,    0,  647,    0,    0,    0, -131, -131,  222,
   97,  239,    0,    0,    0,   46,    0,    0,    0,  246,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  302,    0,
  318,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  340,    0,    0,  364,  389,
  418,    1,    0,    0,    0,    0,    0,  115,   23,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  449,
    0,    0,    0,    0,    0,    0,    0,    0,  544,    0,
  308,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  137,  475,
    0,  497,    0,    0,    0,    0,    0,    0,    0,  160,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  204,
    0,    0,  235,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  519,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   47,   69,    0,    0,    0,  182,    0,    0,    0,
    0,  268,    0,    0,    0,    0,    0,    0,   93,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  191,    0,    0,  281,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  272,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  202,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    4,  269,   -4,   22,    0,    0,    0,    0,
  777,    0,    0,    0,    0,    0,    0,  177,    6,   77,
   92,    0,    0,    5,    0,    0,    0,    0,  317,  309,
  206,  323,  271,    0,    0,  365,    0,  -26,  -32,    0,
  -70, -142,
};
final static int YYTABLESIZE=976;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   47,   37,   81,  104,  115,   51,  129,  143,   58,   37,
   43,  183,   64,   37,   36,   84,   37,   85,   60,   61,
   37,  191,   43,  193,  144,   86,   65,   78,  113,   15,
   87,   53,   94,   96,   95,   94,   96,   95,   49,   37,
   79,   47,   47,   47,   47,   47,   41,   47,   97,  204,
  162,   52,   70,  161,  118,   53,   64,  165,  121,   47,
   47,   47,   47,   43,  174,   43,   43,   43,   42,  117,
   67,  188,  122,  120,  189,  110,   53,    1,    2,  152,
  153,   43,   43,   43,   43,  151,   84,   41,   85,   41,
   41,   41,  118,   71,  163,  164,  166,  110,   53,   42,
  172,   99,   84,  139,   85,   41,   41,   41,   41,   42,
   53,   42,   42,   42,   60,   57,   56,   64,  182,  105,
  106,   79,  155,  123,  124,   47,   72,   42,   42,   42,
   42,  170,  192,  110,   73,   37,   59,  210,  195,   84,
   84,   85,   85,  171,   12,   13,   74,   43,  200,  110,
   83,  118,   37,  186,  187,  209,   89,  110,   60,  101,
  132,  133,  184,    5,  202,    6,   84,   88,   85,    7,
    8,   41,    9,   60,   10,  101,   11,  134,  135,  102,
   59,  102,   12,   13,   14,    5,  107,    6,  201,   53,
  108,    7,  130,   42,    9,   59,   10,  137,   11,    5,
  131,    6,  114,   90,   50,    7,  140,  141,    9,  147,
   10,   10,   11,   32,   33,  110,  148,  118,  101,  149,
  154,   32,   33,  142,   34,   32,   33,   35,   32,   33,
   42,  156,   32,   33,  108,  116,   34,  173,   46,   60,
  102,  103,   12,   13,   91,   92,   93,   91,   92,   93,
  168,   32,   33,  177,  178,   48,   43,   47,  196,   47,
  158,   59,   90,   47,   47,   47,   47,  198,   47,   47,
   47,  126,   47,   47,   47,  199,   47,   47,   47,   43,
  208,   43,   12,   13,  101,   43,   43,   43,   43,  203,
   43,   43,   43,  108,   43,   43,   43,  211,   43,   43,
   43,   72,  212,   41,  213,   41,  102,    3,   30,   41,
   41,   41,   41,   26,   41,   41,   41,   76,   41,   41,
   41,   29,   41,   41,   41,   42,   39,   42,   90,  119,
  126,   42,   42,   42,   42,  185,   42,   42,   42,   64,
   42,   42,   42,   80,   42,   42,   42,   32,   33,  118,
  118,  118,   82,  118,  181,  118,  118,  118,  118,  108,
  118,  118,  118,   66,   32,   33,  100,  138,  118,  118,
  118,   60,    0,   60,    0,    0,    0,   60,   60,   60,
   60,    0,   60,   60,   60,    0,   75,    0,   68,    0,
   60,   60,   60,   59,    0,   59,  126,    0,   76,   59,
   59,   59,   59,    0,   59,   59,   59,    0,    0,    0,
    0,    0,   59,   59,   59,    0,  101,   70,  101,  112,
    0,  101,  101,  101,  101,  101,   72,  101,  101,  101,
    0,    0,    0,    0,    0,  101,  101,  101,  102,    0,
  102,  128,   76,  102,  102,  102,  102,  102,   74,  102,
  102,  102,    0,  136,    0,    0,    0,  102,  102,  102,
   90,    0,   90,    0,   64,    0,   90,   90,   90,   90,
    0,   90,   90,   90,   84,    0,    0,    0,    0,   90,
   90,   90,    0,    0,    0,    0,    0,    0,   66,    0,
    0,  108,    0,  108,    0,    0,   81,  108,  108,  108,
  108,    0,  108,  108,  108,    0,    0,    0,    0,    0,
  108,  108,  108,   68,    0,    0,    0,    0,   78,    0,
    5,    0,    6,    0,    0,    0,    7,    0,  126,    9,
  126,   10,  194,   11,  126,  126,  126,  126,    0,  126,
  126,  126,   70,   56,    0,    0,    0,  126,  126,  126,
    0,    0,    0,    0,    0,    0,    0,    0,   72,    0,
   72,    0,    0,    0,   72,   72,   72,   72,    0,   72,
   72,   72,    0,   74,   76,    0,   76,   72,   72,   72,
   76,   76,   76,   76,    0,   76,   76,   76,    0,    0,
    0,    0,    0,   76,   76,   76,   64,    0,   64,   84,
    0,    0,   64,   64,   64,   64,    0,   64,   64,   64,
    0,    0,    0,    0,    0,   64,   64,   64,    0,    0,
   66,   81,   66,    0,    0,    0,   66,   66,   66,   66,
    0,   66,   66,   66,    0,    0,    0,    0,    0,   66,
   66,   66,    0,   78,    0,   68,    0,   68,    0,    0,
    0,   68,   68,   68,   68,    0,   68,   68,   68,    0,
    0,    0,    0,    0,   68,   68,   68,    0,   56,    0,
    0,    0,    0,    0,   70,    0,   70,    0,    0,    0,
   70,   70,   70,   70,  158,   70,   70,   70,    0,    0,
    0,    0,    0,   70,   70,   70,    0,    0,    0,    0,
   62,    0,    0,    0,    0,   74,    0,   74,    0,    0,
    0,   74,   74,   74,   74,    0,   74,   74,   74,    0,
    0,    0,  109,    0,   74,   74,   74,    0,    0,    0,
  126,   84,    0,   84,    0,    0,    0,   84,   84,   84,
   84,    0,   84,   84,   84,  109,  126,    0,    0,    0,
   84,   84,   84,   81,    0,   81,    0,    0,    0,   81,
   81,   81,   81,  167,   81,   81,   81,    0,    0,    0,
    0,  205,   81,   81,   81,   78,    0,   78,    0,    0,
    0,   78,   78,   78,   78,    0,   78,   78,   78,    0,
    0,    0,    0,    0,   78,   78,   78,    0,    0,    0,
   56,    0,   56,    0,    0,    0,   56,   56,   56,   56,
    0,   56,    0,   56,    0,    0,    5,    0,    6,   56,
   56,   56,    7,    8,  157,    9,    0,   10,    0,   11,
    0,    0,    5,    0,    6,   12,   13,   14,    7,    8,
    0,    9,    0,   10,    0,   11,    0,    0,    0,    0,
    0,   12,   13,   14,    5,    0,    6,    0,    0,    0,
    7,    0,    5,    9,    6,   10,  149,   11,    7,    0,
    0,    9,    0,   10,  149,   11,    0,    5,    5,    6,
    6,  145,    0,    7,    7,    0,    9,    9,   10,   10,
   11,   11,    0,    0,    0,    5,    0,    6,    0,    0,
    0,    7,    0,    5,    9,    6,   10,    0,   11,    7,
    0,    0,    9,    0,   10,    0,   11,    0,    0,  175,
  176,    0,    0,  179,  180,    5,    0,    6,    0,    0,
    0,    7,    8,    0,    9,    0,   10,    0,   11,    0,
    0,    0,    0,    0,   12,   13,   14,    0,    0,    0,
  197,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  206,  207,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   59,   41,   59,   59,   77,   41,  257,   45,
   40,   40,   17,   45,   58,   43,   45,   45,   14,   16,
   45,  164,    0,  166,   58,   42,  257,   59,   55,  123,
   47,   10,   60,   61,   62,   60,   61,   62,   40,   45,
   35,   41,   42,   43,   44,   45,    0,   47,   43,  192,
   41,  123,   59,   44,   44,   34,   61,  128,   44,   59,
   60,   61,   62,   41,   58,   43,   44,   45,    0,   59,
  123,  258,   69,   59,  261,   54,   55,  257,  258,  112,
  113,   59,   60,   61,   62,  112,   43,   41,   45,   43,
   44,   45,    0,   59,  127,  128,  129,   76,   77,  260,
   41,  262,   43,   98,   45,   59,   60,   61,   62,   41,
   89,   43,   44,   45,    0,   58,   59,  122,  151,   40,
   41,  116,  118,  257,  258,  125,   59,   59,   60,   61,
   62,   41,  165,  112,   59,   45,    0,   41,   41,   43,
   43,   45,   45,  138,  276,  277,   40,  125,   41,  128,
  258,   59,   45,  257,  258,   59,  123,  136,   44,    0,
   84,   85,  157,  257,   41,  259,   43,   59,   45,  263,
  264,  125,  266,   59,  268,  262,  270,   86,   87,   41,
   44,    0,  276,  277,  278,  257,   41,  259,  183,  168,
   59,  263,   59,  125,  266,   59,  268,  261,  270,  257,
   59,  259,  257,    0,  258,  263,  262,   41,  266,   58,
  268,  268,  270,  257,  258,  194,   58,  125,   59,  269,
   59,  257,  258,  257,  268,  257,  258,  271,  257,  258,
  260,  257,  257,  258,    0,  271,  268,   44,  279,  125,
   59,  279,  276,  277,  272,  273,  274,  272,  273,  274,
  123,  257,  258,  257,   41,  257,   40,  257,  257,  259,
  125,  125,   59,  263,  264,  265,  266,   44,  268,  269,
  270,    0,  272,  273,  274,   58,  276,  277,  278,  257,
   59,  259,  276,  277,  125,  263,  264,  265,  266,  258,
  268,  269,  270,   59,  272,  273,  274,   59,  276,  277,
  278,    0,  257,  257,   59,  259,  125,    0,   41,  263,
  264,  265,  266,  123,  268,  269,  270,    0,  272,  273,
  274,   41,  276,  277,  278,  257,  125,  259,  125,   61,
   59,  263,  264,  265,  266,  159,  268,  269,  270,    0,
  272,  273,  274,   35,  276,  277,  278,  257,  258,  257,
  258,  259,   36,  261,  149,  263,  264,  265,  266,  125,
  268,  269,  270,    0,  257,  258,   44,   97,  276,  277,
  278,  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,
  266,   -1,  268,  269,  270,   -1,  123,   -1,    0,   -1,
  276,  277,  278,  257,   -1,  259,  125,   -1,   34,  263,
  264,  265,  266,   -1,  268,  269,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,   -1,  257,    0,  259,   55,
   -1,  262,  263,  264,  265,  266,  125,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,   77,  125,  262,  263,  264,  265,  266,    0,  268,
  269,  270,   -1,   89,   -1,   -1,   -1,  276,  277,  278,
  257,   -1,  259,   -1,  125,   -1,  263,  264,  265,  266,
   -1,  268,  269,  270,    0,   -1,   -1,   -1,   -1,  276,
  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,
   -1,  257,   -1,  259,   -1,   -1,    0,  263,  264,  265,
  266,   -1,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  125,   -1,   -1,   -1,   -1,    0,   -1,
  257,   -1,  259,   -1,   -1,   -1,  263,   -1,  257,  266,
  259,  268,  168,  270,  263,  264,  265,  266,   -1,  268,
  269,  270,  125,    0,   -1,   -1,   -1,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  259,   -1,   -1,   -1,  263,  264,  265,  266,   -1,  268,
  269,  270,   -1,  125,  257,   -1,  259,  276,  277,  278,
  263,  264,  265,  266,   -1,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  257,   -1,  259,  125,
   -1,   -1,  263,  264,  265,  266,   -1,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,   -1,   -1,
  257,  125,  259,   -1,   -1,   -1,  263,  264,  265,  266,
   -1,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,   -1,  125,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,   -1,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,   -1,  125,   -1,
   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,   -1,
  263,  264,  265,  266,  125,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,   -1,   -1,   -1,   -1,
  125,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,   -1,  268,  269,  270,   -1,
   -1,   -1,  125,   -1,  276,  277,  278,   -1,   -1,   -1,
  125,  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,
  266,   -1,  268,  269,  270,  125,  125,   -1,   -1,   -1,
  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,  263,
  264,  265,  266,  125,  268,  269,  270,   -1,   -1,   -1,
   -1,  125,  276,  277,  278,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  265,  266,   -1,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,   -1,   -1,   -1,
  257,   -1,  259,   -1,   -1,   -1,  263,  264,  265,  266,
   -1,  268,   -1,  270,   -1,   -1,  257,   -1,  259,  276,
  277,  278,  263,  264,  265,  266,   -1,  268,   -1,  270,
   -1,   -1,  257,   -1,  259,  276,  277,  278,  263,  264,
   -1,  266,   -1,  268,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  257,   -1,  259,   -1,   -1,   -1,
  263,   -1,  257,  266,  259,  268,  269,  270,  263,   -1,
   -1,  266,   -1,  268,  269,  270,   -1,  257,  257,  259,
  259,  105,   -1,  263,  263,   -1,  266,  266,  268,  268,
  270,  270,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,   -1,  257,  266,  259,  268,   -1,  270,  263,
   -1,   -1,  266,   -1,  268,   -1,  270,   -1,   -1,  143,
  144,   -1,   -1,  147,  148,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,   -1,  266,   -1,  268,   -1,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,   -1,   -1,   -1,
  174,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  198,  199,
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
"declaracion_funcion : header_funcion cuerpo_funcion",
"header_funcion : FUN ID '(' lista_parametros ')' ':' tipo",
"header_funcion : FUN ID '(' ')' ':' tipo",
"header_funcion : FUN ID ')' ':' tipo",
"header_funcion : FUN ID '(' ':' tipo",
"header_funcion : FUN ID '(' ')' tipo",
"header_funcion : FUN ID '(' ')' ':'",
"header_funcion : FUN '(' ')' ':' tipo",
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

//#line 231 "Gramatica.y"

public String tipoAux = "";
public static ArrayList<String> erroresSintacticos = new ArrayList<String>();
public static ArrayList<String> erroresLexicos = new ArrayList<String>();

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
		System.out.println("Error");
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
//#line 662 "Parser.java"
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
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "Variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 17:
//#line 48 "Gramatica.y"
{setTipo(val_peek(0).sval); setUso(val_peek(0).sval, "Variable"); val_peek(0).sval = TablaSimbolos.modificarNombre(val_peek(0).sval);}
break;
case 18:
//#line 51 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 19:
//#line 52 "Gramatica.y"
{yyval.sval = val_peek(0).sval; tipoAux = val_peek(0).sval;}
break;
case 21:
//#line 58 "Gramatica.y"
{setTipo(val_peek(5).sval); setUso(val_peek(5).sval, "Funcion"); val_peek(5).sval = TablaSimbolos.modificarNombre(val_peek(5).sval);}
break;
case 22:
//#line 59 "Gramatica.y"
{setTipo(val_peek(4).sval); setUso(val_peek(4).sval, "Funcion"); val_peek(4).sval = TablaSimbolos.modificarNombre(val_peek(4).sval);}
break;
case 23:
//#line 60 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 24:
//#line 61 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 25:
//#line 62 "Gramatica.y"
{erroresSintacticos.add("Falta un :");}
break;
case 26:
//#line 63 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo de retorno");}
break;
case 27:
//#line 64 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un identificador de la funcion");}
break;
case 28:
//#line 67 "Gramatica.y"
{setTipo(val_peek(4).sval,val_peek(3).sval);setUso(val_peek(3).sval, "Nombre_Parametro_Funcion"); setTipo(val_peek(1).sval,val_peek(0).sval); setUso(val_peek(0).sval, "Nombre_Parametro_Funcion"); }
break;
case 29:
//#line 68 "Gramatica.y"
{setUso(val_peek(0).sval, "Nombre_Parametro_Funcion");}
break;
case 30:
//#line 69 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un tipo para el identificador");}
break;
case 31:
//#line 70 "Gramatica.y"
{erroresSintacticos.add("Los identificadores deben tener un tipo");}
break;
case 33:
//#line 74 "Gramatica.y"
{erroresSintacticos.add("La funcion debe retornar un valor");}
break;
case 34:
//#line 77 "Gramatica.y"
{Ambito.concatenarAmbito("func");}
break;
case 35:
//#line 80 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 37:
//#line 84 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 38:
//#line 85 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 39:
//#line 86 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 40:
//#line 87 "Gramatica.y"
{erroresSintacticos.add("Falta un valor que devolver");}
break;
case 41:
//#line 90 "Gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 42:
//#line 91 "Gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 43:
//#line 92 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 44:
//#line 95 "Gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 45:
//#line 96 "Gramatica.y"
{yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 46:
//#line 97 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 47:
//#line 100 "Gramatica.y"
{comprobarAmbito(val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 48:
//#line 101 "Gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 49:
//#line 102 "Gramatica.y"
{yyval.sval = "-" + val_peek(1).sval;}
break;
case 56:
//#line 113 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 59:
//#line 120 "Gramatica.y"
{comprobarAmbito(val_peek(2).sval); yyval.sval = '[' + Integer.toString(TercetoManager.getIndexTerceto()) + ']'; TercetoManager.crear_terceto("=:", val_peek(2).sval, val_peek(0).sval);}
break;
case 60:
//#line 121 "Gramatica.y"
{erroresSintacticos.add("Falta =:");}
break;
case 64:
//#line 129 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 66:
//#line 131 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 68:
//#line 133 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 70:
//#line 135 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 72:
//#line 139 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 74:
//#line 141 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 76:
//#line 143 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 78:
//#line 145 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 79:
//#line 146 "Gramatica.y"
{erroresSintacticos.add("Falta un identificador");}
break;
case 81:
//#line 148 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 82:
//#line 149 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 84:
//#line 151 "Gramatica.y"
{erroresSintacticos.add("Falta un ;");}
break;
case 85:
//#line 152 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo do until");}
break;
case 86:
//#line 153 "Gramatica.y"
{erroresSintacticos.add("Falta un =:");}
break;
case 87:
//#line 156 "Gramatica.y"
{TercetoManager.add_seleccion();}
break;
case 88:
//#line 157 "Gramatica.y"
{erroresSintacticos.add("Falta la condicion del if");}
break;
case 89:
//#line 158 "Gramatica.y"
{erroresSintacticos.add("Falta el cuerpo del if");}
break;
case 90:
//#line 159 "Gramatica.y"
{erroresSintacticos.add("Falta un end_if");}
break;
case 91:
//#line 162 "Gramatica.y"
{TercetoManager.crear_terceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval); TercetoManager.add_seleccion_cond();}
break;
case 92:
//#line 163 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 93:
//#line 164 "Gramatica.y"
{erroresSintacticos.add("Falta un valor con que comparar");}
break;
case 94:
//#line 167 "Gramatica.y"
{yyval.sval = "<";}
break;
case 95:
//#line 168 "Gramatica.y"
{yyval.sval = "<=";}
break;
case 96:
//#line 169 "Gramatica.y"
{yyval.sval = ">";}
break;
case 97:
//#line 170 "Gramatica.y"
{yyval.sval = ">=";}
break;
case 98:
//#line 171 "Gramatica.y"
{yyval.sval = "=";}
break;
case 99:
//#line 172 "Gramatica.y"
{yyval.sval = "=!";}
break;
case 102:
//#line 177 "Gramatica.y"
{erroresSintacticos.add("Falta un cuerpo de else");}
break;
case 103:
//#line 180 "Gramatica.y"
{TercetoManager.add_seleccion_then(); }
break;
case 108:
//#line 191 "Gramatica.y"
{erroresSintacticos.add("Falta un )");}
break;
case 109:
//#line 192 "Gramatica.y"
{erroresSintacticos.add("Falta un (");}
break;
case 110:
//#line 193 "Gramatica.y"
{erroresSintacticos.add("Falta una cadena que imprimir");}
break;
case 111:
//#line 196 "Gramatica.y"
{TercetoManager.add_iter_do_until();}
break;
case 112:
//#line 197 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 113:
//#line 198 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 114:
//#line 199 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 115:
//#line 202 "Gramatica.y"
{Ambito.concatenarAmbito("doUntil"); TercetoManager.add_inicio_do_until();}
break;
case 116:
//#line 205 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 118:
//#line 209 "Gramatica.y"
{erroresSintacticos.add("Falta una condicion");}
break;
case 120:
//#line 213 "Gramatica.y"
{erroresSintacticos.add("Falta un {");}
break;
case 121:
//#line 214 "Gramatica.y"
{erroresSintacticos.add("Falta un }");}
break;
case 122:
//#line 215 "Gramatica.y"
{erroresSintacticos.add("Faltan sentencias de ejecucion");}
break;
case 123:
//#line 219 "Gramatica.y"
{Ambito.concatenarAmbito("doUntilExpr");}
break;
case 124:
//#line 222 "Gramatica.y"
{Ambito.removeAmbito();}
break;
case 126:
//#line 226 "Gramatica.y"
{erroresSintacticos.add("Falta una constante para asignar");}
break;
case 127:
//#line 227 "Gramatica.y"
{erroresSintacticos.add("Se esperaba un else");}
break;
//#line 1151 "Parser.java"
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
