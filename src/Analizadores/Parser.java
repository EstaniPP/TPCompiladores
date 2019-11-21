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






//#line 1 "compi/gramatica.y"
	
	package Analizadores;
	import java.util.ArrayList;
	import java.io.PrintWriter;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileWriter;
	import java.io.IOException;
	import javax.swing.JFileChooser;
	import java.util.HashMap;
	import javax.swing.JOptionPane;
//#line 29 "Parser.java"




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
public final static short ELSE=260;
public final static short END_IF=261;
public final static short PRINT=262;
public final static short INT=263;
public final static short BEGIN=264;
public final static short END=265;
public final static short FLOAT=266;
public final static short FOR=267;
public final static short CLASS=268;
public final static short EXTENDS=269;
public final static short CADENA=270;
public final static short ERROR=271;
public final static short VOID=272;
public final static short MAYOR_IGUAL=273;
public final static short MENOR_IGUAL=274;
public final static short IGUAL=275;
public final static short DISTINTO=276;
public final static short ASIGN=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    2,    0,    4,    1,    6,    1,    5,    5,    9,    5,
   10,    5,   11,    7,   14,    7,    7,    7,    7,    7,
    7,    7,   12,   15,   12,   12,   12,   17,   16,   16,
   16,   16,   16,   16,    8,   18,   18,   18,   19,   19,
   13,   13,    3,    3,   20,   20,   20,   20,   20,   20,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   27,   30,   28,   32,   33,   24,
   24,   24,   24,   25,   25,   25,   22,   22,   21,   21,
   21,   36,   26,   37,   26,   38,   26,   39,   26,   40,
   26,   41,   26,   29,   29,   35,   35,   35,   42,   42,
   42,   31,   31,   31,   34,   34,   43,   43,
};
final static short yylen[] = {                            2,
    0,    2,    0,    4,    0,    5,    1,    1,    0,    3,
    0,    3,    0,    6,    0,    8,    7,    5,    5,    7,
    7,    7,    1,    0,    3,    1,    2,    0,    8,    7,
    8,    7,    7,    7,    3,    1,    1,    1,    1,    3,
    1,    3,    1,    2,    1,    1,    1,    1,    1,    2,
    7,    9,    7,    6,    6,    9,    8,    8,    7,    8,
    8,    8,   10,   10,    1,    0,    2,    0,    0,   11,
    6,    5,    5,    6,    5,    6,    5,    3,    4,    4,
    4,    0,    4,    0,    4,    0,    4,    0,    4,    0,
    4,    0,    4,    1,    3,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         1,
    0,    0,   38,   36,    3,   37,    0,    2,    0,    0,
    0,    0,    0,    0,    0,    5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,   46,   47,
   48,   49,    0,    0,    0,    0,    0,    0,    0,   12,
   10,    0,   35,    0,   50,    0,    0,    0,    0,    0,
    0,    0,    4,   44,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   40,    0,
  107,  104,    0,  101,  102,    0,    0,  103,    0,    0,
    0,    0,    0,    0,    0,   78,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   18,   27,    0,    0,   19,
    0,    0,    0,    0,    0,    6,    0,  108,   81,    0,
    0,    0,    0,    0,    0,    0,    0,   94,    0,   65,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,   80,   79,    0,    0,    0,
   25,   42,    0,    0,    0,    0,    0,   14,  106,    0,
    0,   99,  100,    0,   75,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   77,   72,    0,   73,    0,    0,    0,    0,    0,
   17,   22,   21,   20,    0,   76,   74,   95,    0,   55,
    0,    0,    0,   54,    0,    0,    0,   66,    0,    0,
    0,    0,    0,    0,    0,   71,    0,    0,    0,    0,
    0,    0,    0,   16,    0,    0,   53,    0,   66,    0,
   66,    0,   59,    0,    0,   51,   69,    0,    0,    0,
    0,    0,    0,   58,    0,   57,    0,   61,    0,   62,
    0,   60,   67,    0,   30,   32,   33,    0,   34,    0,
   56,    0,    0,   52,    0,   31,   29,   63,   64,    0,
   70,
};
final static short yydgoto[] = {                          1,
    8,    2,   26,   13,    9,   39,   10,   57,   18,   17,
   38,   58,   61,  104,   95,   59,  213,   12,   20,  118,
   28,   29,   30,   31,   32,   84,  119,  224,  120,  225,
   74,  176,  244,   33,   85,  125,  126,  127,  128,  129,
  130,   77,   78,
};
final static short yysindex[] = {                         0,
    0, -126,    0,    0,    0,    0, -165,    0, -241,    0,
    0, -191, -107, -179, -195,    0, -112, -112,   25,    3,
  -52,   32,  -29,  -26,  -12, -160, -107,    0,    0,    0,
    0,    0, -170, -190, -146, -177, -147, -140, -107,    0,
    0, -191,    0,  -20,    0, -138,   91,    2,   77, -129,
  112, -122,    0,    0,   30,  -99,    0, -106, -190,  117,
  -93,  117,  -87,  -83,  -80,  -57, -190,  -79,    0,  154,
    0,    0,  -56,    0,    0,   11,   51,    0,  161,   -7,
 -134,    5,  -20,  162,   34,    0,  163, -134,  -38,  -20,
  146,   12,  166,    9, -190,    0,    0, -146, -190,    0,
 -190, -190, -190,  -55,  -53,    0,  -49,    0,    0,  -20,
  -20,  -20,  -20,  172,  155,  174, -107,    0,  -91,    0,
 -134,  -88,  175,   18,  -51,  -54,  188,  157,  -19,  -13,
  164,  198, -134,  207,    0,    0,    0,  227,  229,  -25,
    0,    0,    6,   13,   14,   16, -190,    0,    0,   51,
   51,    0,    0,  217,    0,  224,   19, -134,  233,  -72,
 -134,  244, -134, -134, -117,  -20,  -20,  -20,  -20,  -20,
  -20,    0,    0,  246,    0,  247,   43,   45,  -36,   54,
    0,    0,    0,    0,   48,    0,    0,    0,   50,    0,
 -134,  257,   56,    0,  -70,  -68,  259,    0,  261,   34,
   34,   34,   34,   34,   34,    0,  -20, -107, -107, -107,
   55, -107,   58,    0,  265,   64,    0,  267,    0,  268,
    0,  269,    0,  -50, -134,    0,    0,   65,   66,   67,
 -107,   68, -107,    0,  270,    0,   73,    0,   74,    0,
  277,    0,    0,  296,    0,    0,    0,   75,    0,   76,
    0,  279,  280,    0, -134,    0,    0,    0,    0,  283,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -89,
  -81,    0,    0,    0,   80,    0,    0,    0,  286,    0,
    0,   69,    0,    0,    0,    0,   82,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -151,    0,   83, -206,
    0, -192,    0,    0,    0,   85,    0,    0,    0,  -41,
    0,    0,    0,    0,    0,    0,  -33,    0,    0,   78,
    0,    0,    0,    0,   21,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -28,
  -21,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   87,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  311,
  312,  313,  315,  316,  317,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,   90,    0,  177,    0,    0,   46,    0,    0,
    0,    1,   -1,    0,    0,    0,    0,    0,  318,   81,
  307,    0,    0,    0,    0,  278,  -37, -120,  -45,    0,
  -60,    0,    0,  260,   -4,    0,    0,    0,    0,    0,
    0,   86,    0,
};
final static int YYTABLESIZE=467;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
  105,  105,  133,  105,  211,  105,   45,   98,  242,   98,
   48,   98,   96,   50,   96,  180,   96,  105,  105,   97,
  105,   97,   16,   97,   73,   98,   98,   52,   98,  135,
   96,   96,  116,   96,   64,   66,   63,   97,   97,   76,
   97,   83,  132,  134,  122,  121,   73,   11,  140,   41,
   92,  152,  153,  110,  110,  111,  111,   41,  164,   97,
   36,   43,   11,   11,   38,   19,    3,  105,   42,  109,
  137,   41,    4,   37,   73,    6,  110,   46,  111,   62,
   88,   56,   86,  160,   34,    4,  165,  174,    6,   35,
   14,   15,  112,   27,   56,  141,  142,  113,  237,  143,
  239,  144,  145,  146,   53,   24,   55,   27,   65,   60,
   60,   24,  189,   23,   24,  193,   54,   79,   80,   27,
   24,   21,   22,   67,   23,  195,  196,   24,   68,  117,
    3,   81,   25,   89,   70,   86,    4,    5,  197,    6,
   87,    7,  198,  199,    3,  216,  227,  185,   21,   22,
    4,   23,   88,    6,   24,    7,   93,   94,   96,   25,
   98,  200,  201,  202,  203,  204,  205,   11,  158,  159,
   99,  161,  162,   11,    7,    9,   11,  100,   11,  243,
  101,    9,    8,  102,    9,  106,    9,  191,  192,  219,
  220,  221,  222,   40,   41,  150,  151,   27,  103,  107,
  114,  108,  124,  131,  136,  138,  157,  149,  147,  260,
  241,  148,  154,  155,  156,  163,  169,   21,   22,  167,
   23,  166,  172,   24,   44,  117,   47,  210,   25,   49,
  179,  105,  105,  105,  105,  105,   70,   71,   44,   98,
   98,   98,   98,   51,   96,   96,   96,   96,  115,  168,
   72,   97,   97,   97,   97,  170,  173,   82,   70,   71,
   21,   22,  171,   23,  139,  175,   24,  177,  117,  178,
  181,   25,   72,   21,   22,  186,   23,  182,  183,   24,
  184,  117,  187,  188,   25,   91,   70,   71,   27,   27,
   27,  190,   27,   82,   84,   90,   92,  228,  229,  230,
   72,  232,  194,   75,  206,  207,  208,   75,  209,  212,
  215,   27,  214,   27,   75,  217,  218,  223,  231,  226,
  248,  233,  250,  234,  235,  236,  238,  240,  251,  245,
  246,  247,  249,  252,  253,  254,  255,  258,  259,  256,
  257,  261,   75,   13,   39,  105,   43,   26,   15,   75,
   28,   83,   85,   87,  106,   89,   91,   93,   90,   69,
  123,    0,    0,    0,    0,    0,    0,    0,    0,   75,
   75,   75,   75,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   75,   75,   75,   75,   75,
   75,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   59,   41,   59,   43,
   40,   45,   41,   40,   43,   41,   45,   59,   60,   41,
   62,   43,  264,   45,   45,   59,   60,   40,   62,   90,
   59,   60,   40,   62,   36,   37,   36,   59,   60,   44,
   62,   40,   88,   89,   82,   41,   45,    2,   40,  256,
   55,  112,  113,   43,   43,   45,   45,  264,   41,   59,
  256,   59,   17,   18,  257,  257,  257,   67,   44,   59,
   59,  264,  263,  269,   45,  266,   43,   46,   45,  257,
   60,  272,   62,  121,  264,  263,  124,  133,  266,  269,
  256,  257,   42,   13,  272,   95,   98,   47,  219,   99,
  221,  101,  102,  103,  265,  257,  277,   27,  256,  257,
  257,  263,  158,  265,  266,  161,   27,  256,  257,   39,
  272,  256,  257,  264,  259,  163,  164,  262,   39,  264,
  257,   41,  267,  256,  257,   59,  263,  264,  256,  266,
  270,  268,  260,  261,  257,  191,  207,  147,  256,  257,
  263,  259,   41,  266,  262,  268,  256,  257,  265,  267,
   44,  166,  167,  168,  169,  170,  171,  257,  260,  261,
  264,  260,  261,  263,  264,  257,  266,  265,  268,  225,
  264,  263,  264,  264,  266,  265,  268,  260,  261,  260,
  261,  260,  261,   17,   18,  110,  111,  117,  256,   46,
   40,  258,   41,   41,   59,   40,  117,  257,  264,  255,
  261,  265,   41,   59,   41,   41,   60,  256,  257,  274,
  259,  273,   59,  262,  277,  264,  256,  264,  267,  256,
  256,  273,  274,  275,  276,  277,  257,  258,  277,  273,
  274,  275,  276,  256,  273,  274,  275,  276,  256,   62,
  271,  273,  274,  275,  276,  275,   59,  256,  257,  258,
  256,  257,  276,  259,  256,   59,  262,   41,  264,   41,
  265,  267,  271,  256,  257,   59,  259,  265,  265,  262,
  265,  264,   59,  265,  267,  256,  257,  258,  208,  209,
  210,   59,  212,  273,  274,  275,  276,  208,  209,  210,
  271,  212,   59,   44,   59,   59,  264,   48,  264,  256,
  261,  231,  265,  233,   55,   59,  261,   59,  264,   59,
  231,  264,  233,   59,  261,   59,   59,   59,   59,  265,
  265,  265,  265,  261,  261,   59,   41,   59,   59,  265,
  265,   59,   83,  264,   59,  277,  265,  265,  264,   90,
  264,   41,   41,   41,  277,   41,   41,   41,   52,   42,
   83,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  110,
  111,  112,  113,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  166,  167,  168,  169,  170,
  171,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  207,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"ID","CTE","IF","ELSE","END_IF","PRINT","INT",
"BEGIN","END","FLOAT","FOR","CLASS","EXTENDS","CADENA","ERROR","VOID",
"MAYOR_IGUAL","MENOR_IGUAL","IGUAL","DISTINTO","ASIGN",
};
final static String yyrule[] = {
"$accept : programa",
"$$1 :",
"programa : $$1 conjunto_sentencias",
"$$2 :",
"conjunto_sentencias : BEGIN $$2 sentencias_ejecutables END",
"$$3 :",
"conjunto_sentencias : declaraciones BEGIN $$3 sentencias_ejecutables END",
"declaraciones : declaracion_clase",
"declaraciones : declaracion_sentencia",
"$$4 :",
"declaraciones : declaracion_sentencia $$4 declaraciones",
"$$5 :",
"declaraciones : declaracion_clase $$5 declaraciones",
"$$6 :",
"declaracion_clase : CLASS ID $$6 BEGIN declaracion_sentencia_clase END",
"$$7 :",
"declaracion_clase : CLASS ID EXTENDS lista_clases $$7 BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS error EXTENDS lista_clases BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS error BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID error declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS lista_clases error declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS error BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID error lista_clases BEGIN declaracion_sentencia_clase END",
"declaracion_sentencia_clase : declaracion_sentencia",
"$$8 :",
"declaracion_sentencia_clase : declaracion_sentencia $$8 declaracion_sentencia_clase",
"declaracion_sentencia_clase : declaracion_metodo",
"declaracion_sentencia_clase : declaracion_metodo declaracion_sentencia_clase",
"$$9 :",
"declaracion_metodo : VOID ID '(' ')' $$9 BEGIN sentencias_ejecutables END",
"declaracion_metodo : VOID error '(' ')' BEGIN sentencias_ejecutables END",
"declaracion_metodo : VOID ID '(' error ')' BEGIN sentencias_ejecutables END",
"declaracion_metodo : VOID ID error ')' BEGIN sentencias_ejecutables END",
"declaracion_metodo : VOID ID '(' error BEGIN sentencias_ejecutables END",
"declaracion_metodo : VOID ID '(' ')' error sentencias_ejecutables END",
"declaracion_sentencia : tipo lista_variables ';'",
"tipo : INT",
"tipo : FLOAT",
"tipo : ID",
"lista_variables : ID",
"lista_variables : ID ',' lista_variables",
"lista_clases : ID",
"lista_clases : ID ',' lista_clases",
"sentencias_ejecutables : sentencia_ej",
"sentencias_ejecutables : sentencia_ej sentencias_ejecutables",
"sentencia_ej : asignacion",
"sentencia_ej : impresion",
"sentencia_ej : seleccion",
"sentencia_ej : iteracion",
"sentencia_ej : llamadometodo",
"sentencia_ej : error ';'",
"seleccion : IF '(' condicion ')' bloqueThen END_IF ';'",
"seleccion : IF '(' condicion ')' bloqueThen ELSE bloqueElse END_IF ';'",
"seleccion : IF '(' error ')' bloqueThen END_IF ';'",
"seleccion : IF '(' error bloqueThen END_IF ';'",
"seleccion : IF error ')' bloqueThen END_IF ';'",
"seleccion : IF '(' error ')' bloqueThen ELSE bloque END_IF ';'",
"seleccion : IF '(' error bloqueThen ELSE bloque END_IF ';'",
"seleccion : IF error ')' bloqueThen ELSE bloque END_IF ';'",
"seleccion : IF '(' condicion ')' bloqueThen error ';'",
"seleccion : IF '(' condicion ')' bloqueThen ELSE bloqueElse ';'",
"seleccion : IF '(' '(' condicion ')' bloqueThen END_IF ';'",
"seleccion : IF '(' condicion ')' ')' bloqueThen END_IF ';'",
"seleccion : IF '(' '(' condicion ')' bloqueThen ELSE bloqueElse END_IF ';'",
"seleccion : IF '(' condicion ')' ')' bloqueThen ELSE bloqueElse END_IF ';'",
"bloqueThen : bloque",
"$$10 :",
"bloqueElse : $$10 bloque",
"$$11 :",
"$$12 :",
"iteracion : FOR '(' asignacion factor $$11 ';' factor $$12 ')' bloque ';'",
"iteracion : FOR '(' error ')' bloque ';'",
"iteracion : FOR error ')' bloque ';'",
"iteracion : FOR '(' error bloque ';'",
"llamadometodo : ID '.' ID '(' ')' ';'",
"llamadometodo : ID '.' ID error ';'",
"llamadometodo : ID '.' error '(' ')' ';'",
"impresion : PRINT '(' CADENA ')' ';'",
"impresion : PRINT error ';'",
"asignacion : identificador ASIGN expresion ';'",
"asignacion : identificador ASIGN error ';'",
"asignacion : error ASIGN expresion ';'",
"$$13 :",
"condicion : expresion $$13 MAYOR_IGUAL expresion",
"$$14 :",
"condicion : expresion $$14 MENOR_IGUAL expresion",
"$$15 :",
"condicion : expresion $$15 '>' expresion",
"$$16 :",
"condicion : expresion $$16 '<' expresion",
"$$17 :",
"condicion : expresion $$17 IGUAL expresion",
"$$18 :",
"condicion : expresion $$18 DISTINTO expresion",
"bloque : sentencia_ej",
"bloque : BEGIN sentencias_ejecutables END",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : identificador",
"factor : cte",
"factor : ERROR",
"identificador : ID",
"identificador : ID '.' ID",
"cte : CTE",
"cte : '-' CTE",
};

//#line 202 "compi/gramatica.y"
	AnalizadorLexico aLexico=new AnalizadorLexico();
	ArrayList<String> errores = new ArrayList<String>();
	ArrayList<String> erroresSem = new ArrayList<String>();
	ArrayList<String> salida = new ArrayList<String>();
	ArrayList<String> tokens = new ArrayList<String>();
	ArrayList<String> variables = new ArrayList<String>();
	ArrayList<String> variablesPorUso = new ArrayList<String>();
	ArrayList<String> metodosPorClase = new ArrayList<String>();
	ArrayList<String> listaHerencia = new ArrayList<String>();
	ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
	ArrayList<Integer> pilaSaltos = new ArrayList<Integer>();
	ArrayList<Terceto> pilaExpresionFor = new ArrayList<Terceto>();
	
	String tipo, ambitoActual;
	HashMap<String,String> noTerminalTercetos = new HashMap<String,String>();

	public String getTipo(String elemento){
		if(elemento == null) {
			return null;
		}
		if(elemento.charAt(0) == '[' && elemento.charAt(elemento.length()-1) == ']'){
			elemento = elemento.substring(1,elemento.length()-1);
			return tercetos.get(Integer.parseInt(elemento)).tipo;
		}else{
			return (String) aLexico.getTablaSimbolos().get(elemento).get("Tipo");
		}
	}
	public void insertarNoTerminal(String noTerminal, String value){
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
			noTerminalTercetos.put(noTerminal,value);
		}
	}
	public String crearTerceto(String operador,String operando1,String operando2,String tipo) {
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
			tercetos.add(new Terceto(operador,operando1,operando2,tipo));
			return new String("["+Integer.toString(tercetos.size()-1)+"]");
		}else{
			return null;
		}
	}

	public boolean checkeoTipos(String operando1,String operando2){
		if(getTipo(operando1) != getTipo(operando2)){
				yyerrorSemantico("Incopatibilidad de tipos");
				return false;
		}
		return true;
	}

	public void apilarSalto(String numTerceto){
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
			pilaSaltos.add(Integer.parseInt(numTerceto.substring(1,numTerceto.length()-1)));
		}
	}


	public void apilarExpresionFor(String operando1, String operando2){
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
			if(getTipo(operando2)=="int" && getTipo(operando1)== "int"){
				pilaExpresionFor.add(new Terceto(":=",operando1,null,"int"));
				pilaExpresionFor.add(new Terceto("+",operando1,operando2,"int"));
			}else{
				yyerrorSemantico("El tipo de los operandos del for deben ser de tipo int");
			}
		}
	}

	public void desapilarExpresionFor(){
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
			tercetos.add(pilaExpresionFor.remove(pilaExpresionFor.size()-1));
			tercetos.add(pilaExpresionFor.remove(pilaExpresionFor.size()-1));
			tercetos.get(tercetos.size()-1).operando2 = new String("[" + (tercetos.size()-2)+ "]");
		}
	}

	public void desapilarSalto(Integer posicion){
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
			Integer pos = pilaSaltos.get(pilaSaltos.size()-1);
			tercetos.get(pos).operando2 = new String("[" + posicion + "]"); 
			pilaSaltos.remove(pilaSaltos.size()-1);
		}
	}

	public String crearTercetoTipo(String operador,String operando1,String operando2,String tipo){
		if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0 && checkeoTipos(operando1,operando2)){	
			crearTerceto(operador, operando1, operando2, tipo);
			return new String("["+Integer.toString(tercetos.size()-1)+"]");
		}
		return null;
	}
	int yylex(){
		int token = aLexico.yylex();
		if(token == 257 || token == 258 || token  == 270) yylval = aLexico.getyylval();
		tokens.add(new Integer(token).toString());
		return token;
	}

	void yyerror(String error) {
		errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - "+error);
	}

	void yyerrorSemantico(String error) {
		erroresSem.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - "+error);
	}

	public ArrayList<String> getErrores() {
		ArrayList<String> erroresTotales = new ArrayList<String>();
		erroresTotales.add("Errores lexicos:");
		erroresTotales.addAll(aLexico.getErrores());		
		erroresTotales.add("Errores sintacticos:");
		erroresTotales.addAll(errores);
		erroresTotales.add("Errores semanticos:");
		erroresTotales.addAll(erroresSem);
		return erroresTotales;
	}
 	public void crearObjetoTablaSimbolos(String tipo){
		ArrayList<String> atributosClase = (ArrayList<String>) aLexico.getTablaSimbolos().get(tipo).get("VariablesClase");
		for(String s : variables){
			for(String atributo : atributosClase){
				aLexico.getTablaSimbolos().put(s+"@"+atributo,new HashMap<String,Object>(aLexico.getTablaSimbolos().get(atributo)));
			}
		}
	}
	public void agregarTipoVariables(String tipo){
		for(String s: variables){
			HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
			if((tablaSimbolos.get(s)).containsKey("Tipo")){
				yyerrorSemantico("La variable "+s+" ya fue declarada.");
			}else{
				aLexico.agregarAtributoLexema(s,"Tipo",tipo);	
			}
		}
		if(!tipo.equals("int") && !tipo.equals("float")){
			crearObjetoTablaSimbolos(tipo);
		}
		variables.clear();
	}

	public void agregarAtributoAClase(String clase){
				((ArrayList<String>)aLexico.getTablaSimbolos().get(clase).get("VariablesClase")).addAll(variablesPorUso);
				((ArrayList<String>)aLexico.getTablaSimbolos().get(clase).get("MetodosClase")).addAll(metodosPorClase);
	}

	public void agregarUsoVariables(String uso){
		for(String s: variablesPorUso){	
			HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
			if((tablaSimbolos.get(s)).containsKey("Uso")){
			}else{
				aLexico.agregarAtributoLexema(s,"Uso",uso);	
			}
			
		}
		variablesPorUso.clear();
	}


	public void crearTercetoCopiaValor(String nombreObj){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		String clase = (tablaSimbolos.get(nombreObj).get("Tipo")).toString();
		ArrayList<String> atributos = (ArrayList<String>)tablaSimbolos.get(clase).get("VariablesClase");

		for( String atr : atributos){
			crearTerceto(":=",atr,new String(nombreObj+"@"+atr),tablaSimbolos.get(atr).get("Tipo").toString());
		}
	}

	public void crearTercetoCopiaResultado(String nombreObj){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		String clase = (tablaSimbolos.get(nombreObj).get("Tipo")).toString();
		ArrayList<String> atributos = (ArrayList<String>)tablaSimbolos.get(clase).get("VariablesClase");

		for( String atr : atributos){
			crearTerceto(":=",new String(nombreObj+"@"+atr),atr,tablaSimbolos.get(atr).get("Tipo").toString());
		}
	}

	public void inicializarAtributos(String clase){
		aLexico.agregarAtributoLexema(clase,"VariablesClase",new ArrayList<String>());
		aLexico.agregarAtributoLexema(clase,"MetodosClase",new ArrayList<String>());
		ambitoActual = clase;
	}
	public void agregarAtributoHeredados(String clase){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		for(String padre: listaHerencia){
			if(!(tablaSimbolos.get(padre).containsKey("Uso") && tablaSimbolos.get(padre).get("Uso").equals("nombreClase"))){
			yyerrorSemantico("Clase extendida "+padre+" no existente.");
			}else{
				((ArrayList<String>)aLexico.getTablaSimbolos().get(clase).get("VariablesClase")).addAll((ArrayList<String>)(aLexico.getTablaSimbolos().get(padre).get("VariablesClase")));
				((ArrayList<String>)aLexico.getTablaSimbolos().get(clase).get("MetodosClase")).addAll((ArrayList<String>)(aLexico.getTablaSimbolos().get(padre).get("MetodosClase")));
			}
		}
		listaHerencia.clear();
	}
	
	public boolean verificarDeclaracionVariable(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!(tablaSimbolos.get(id).containsKey("Uso"))){
			if(!variablesPorUso.contains(id)) {
				yyerrorSemantico("Uso de variable "+id+" no declarada.");
				return false;
			}
		}else if(!(tablaSimbolos.get(id).get("Uso").equals("variable") ||(ambitoActual!=null && ((ArrayList<String>)tablaSimbolos.get(ambitoActual).get("VariablesClase")).contains(id)) || variablesPorUso.contains(id))){
			yyerrorSemantico("La variable "+id+" no esta al alcance.");
			return false;
		}
		return true;
	}

	public void verificarDeclaracionClase(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!tablaSimbolos.get(id).containsKey("Uso") || !tablaSimbolos.get(id).get("Uso").toString().equals("nombreClase")){
			yyerrorSemantico("Tipo de variable no declarado.");
		}
	}

	public void verificarDeclaracionAtributo(String idClase, String atributo){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!((ArrayList<String>)tablaSimbolos.get(tablaSimbolos.get(idClase).get("Tipo")).get("VariablesClase")).contains(atributo)){
			yyerrorSemantico("El objeto no tiene ese atributo.");
		}
	}

	public void verificarDeclaracionMetodo(String idClase, String metodo){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!((ArrayList<String>)tablaSimbolos.get(tablaSimbolos.get(idClase).get("Tipo")).get("MetodosClase")).contains(metodo)){
			yyerrorSemantico("El objeto no tiene ese metodo.");
		}
	}

	public void verificarRedeclaracionClase(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(tablaSimbolos.get(id).containsKey("Uso")){
			yyerrorSemantico("Nombre de clase "+id +" ya declarado.");
		}
	}

	public void verificarDeclaracionClaseExtendida(String id){

	}

	public void saveFile(){

	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

		    File archivo = chooser.getSelectedFile();
	
		    try {
	
		    	PrintWriter writer = new PrintWriter(archivo+"\\salidaCompilador.txt");
		    	writer.print("");
		    	FileWriter escribir = new FileWriter(archivo+"\\salidaCompilador.txt", true);
		    	StringBuilder sb = new StringBuilder("\r\n");

				for(String s: this.getErrores()){
					escribir.write(s.toString());
					escribir.write(sb.toString());
				}

				escribir.write(aLexico.getDatosTablaSimbolos());
				escribir.write(sb.toString());

				
				escribir.write("Tercetos");
				escribir.write(sb.toString());
				
				int i =0;	
				for(Terceto s : tercetos) {
					escribir.write((i++) + "-" + s.toString());
					escribir.write(sb.toString());
				}	
				
		        escribir.close();

				if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){		
					PrintWriter writer1 = new PrintWriter(archivo+"\\program.asm");
					writer1.print("");
					FileWriter escribir1 = new FileWriter(archivo+"\\program.asm", true);
			
					escribir1.write(".386");
					escribir1.write(sb.toString());
					escribir1.write(".model flat, stdcall");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\masm32rt.inc");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\windows.inc");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\kernel32.inc");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\user32.inc");
					escribir1.write(sb.toString());
					escribir1.write("includelib \\masm32\\lib\\kernel32.lib");
					escribir1.write(sb.toString());
					escribir1.write("includelib \\masm32\\lib\\user32.lib");
					escribir1.write(sb.toString());
					escribir1.write(".data");
					escribir1.write(sb.toString());

					HashMap<String,HashMap<String,Object>> tablaSimbolos = aLexico.getTablaSimbolos();
					for(String key: tablaSimbolos.keySet()){
						HashMap<String,Object> datosKey = tablaSimbolos.get(key);
						if(datosKey.containsKey("Uso")){
							if(datosKey.get("Uso").equals("variable") || datosKey.get("Uso").equals("nombreAtributo")){
								if(datosKey.get("Tipo").equals("int")){
									if(key.charAt(0)=='-')
									{
										escribir1.write("@"+key.substring(1,key.length())+" DW ?");
										escribir1.write(sb.toString());
									}else{
										escribir1.write("_"+key+" DW ?");
										escribir1.write(sb.toString());
									}
									
								}else if(datosKey.get("Tipo").equals("float")){
									escribir1.write("_"+key.replaceFirst("[.]","_").replaceFirst("-","@")+" DD ?");
									escribir1.write(sb.toString());
								}
							}
						}else if(datosKey.containsKey("Tipo") && datosKey.get("Tipo").equals("cadena")){
							escribir1.write("_"+datosKey.get("NombreString")+" DB \""+key+"\",0");
							escribir1.write(sb.toString());									
						}else if(datosKey.containsKey("Tipo") && datosKey.get("Tipo").equals("float")){
							escribir1.write("_"+key.replaceFirst("[.]","_").replaceFirst("-","@")+" DD "+key);
							escribir1.write(sb.toString());						
						}
					}
					//Variables overflow float
					escribir1.write("minimoPositivo DQ 1.17549435E-38");
					escribir1.write(sb.toString());		
					escribir1.write("minimoNegativo DQ -1.17549435E-38");
					escribir1.write(sb.toString());		
					escribir1.write("maximoPositivo DQ 3.40282347E38");
					escribir1.write(sb.toString());		
					escribir1.write("maximoNegativo DQ -3.40282347E38");
					escribir1.write(sb.toString());		
					escribir1.write("ceroFloat DD 0.0");
					escribir1.write(sb.toString());		
					escribir1.write("aux DD ?");
					escribir1.write(sb.toString());									
					escribir1.write("@OVERFLOWFLOAT DB \"Error overflow float\",0");
					escribir1.write(sb.toString());
					escribir1.write("@OVERFLOWINT DB \"Error overflow int\",0");
					escribir1.write(sb.toString());
					escribir1.write("@DIVISIONCERO DB \"Error division por 0\",0");
					escribir1.write(sb.toString());
					escribir1.write(".code");
					escribir1.write(sb.toString());
					
					for(String s : Terceto.codigo) {
						escribir1.write(s);
						escribir1.write(sb.toString());
					}
					escribir1.close();
				}else{
					JOptionPane.showMessageDialog(null, "Hubo errores de compilacion, revise salidaCompilador.txt");
				}
		    } catch (FileNotFoundException ex) {
		    } catch (IOException ex) {
		    }
	    }

	}

	public static void main(String args[])
	{
		Parser par = new Parser(false);
		Terceto.al = par.aLexico;
		par.yyparse();
		if(par.aLexico.getErrores().size() == 0 && par.erroresSem.size() == 0 && par.errores.size() == 0){
			Terceto.crearCodigo(par.tercetos,par.aLexico.getTablaSimbolos());
		}
		par.saveFile();
	}
//#line 865 "Parser.java"
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
case 1:
//#line 18 "compi/gramatica.y"
{crearTerceto("CHECKOVERFLOWFLOAT",null,null,null); crearTerceto("OVERFLOW_FLOAT",null,null,null); crearTerceto("OVERFLOW_INT",null,null,null); crearTerceto("DIVISIONCERO",null,null,null);}
break;
case 2:
//#line 18 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");
			crearTerceto("END_PROGRAMA",null,null,null);}
break;
case 3:
//#line 22 "compi/gramatica.y"
{crearTerceto("LABEL","START",null,null);}
break;
case 4:
//#line 22 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones. ");}
break;
case 5:
//#line 23 "compi/gramatica.y"
{crearTerceto("LABEL","START",null,null);}
break;
case 6:
//#line 23 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones. ");}
break;
case 7:
//#line 26 "compi/gramatica.y"
{agregarUsoVariables("nombreAtributo");}
break;
case 8:
//#line 27 "compi/gramatica.y"
{agregarUsoVariables("variable");}
break;
case 9:
//#line 28 "compi/gramatica.y"
{agregarUsoVariables("variable");}
break;
case 11:
//#line 29 "compi/gramatica.y"
{agregarUsoVariables("nombreAtributo");}
break;
case 13:
//#line 32 "compi/gramatica.y"
{inicializarAtributos(val_peek(0).sval);}
break;
case 14:
//#line 32 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase. ");
																   verificarRedeclaracionClase(val_peek(4).sval);
																   aLexico.agregarAtributoLexema(val_peek(4).sval,"Uso","nombreClase");
																   agregarAtributoAClase(val_peek(4).sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
break;
case 15:
//#line 38 "compi/gramatica.y"
{inicializarAtributos(val_peek(2).sval); agregarAtributoHeredados(val_peek(2).sval);}
break;
case 16:
//#line 38 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple. ");
				  												   verificarRedeclaracionClase(val_peek(6).sval);
																   aLexico.agregarAtributoLexema(val_peek(6).sval,"Uso","nombreClase");
																   agregarAtributoAClase(val_peek(6).sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
break;
case 17:
//#line 44 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 18:
//#line 45 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 19:
//#line 46 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 20:
//#line 47 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 21:
//#line 48 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase extendidas");}
break;
case 22:
//#line 49 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta palabra reservada extends");}
break;
case 23:
//#line 54 "compi/gramatica.y"
{if(!tipo.equals("int") && !tipo.equals("float")) yyerrorSemantico("Los atributos de la clase deben ser de tipos primitivos.");}
break;
case 24:
//#line 55 "compi/gramatica.y"
{if(!tipo.equals("int") && !tipo.equals("float")) yyerrorSemantico("Los atributos de la clase deben ser de tipos primitivos.");}
break;
case 28:
//#line 60 "compi/gramatica.y"
{crearTerceto("METODO",val_peek(2).sval,null,null);}
break;
case 29:
//#line 60 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo. ");
																	 aLexico.agregarAtributoLexema(val_peek(6).sval,"Uso","nombreMetodo");
																	 metodosPorClase.add(val_peek(6).sval);
																	 crearTerceto("TERMINA_METODO",null,null,null);
																	 }
break;
case 30:
//#line 65 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta nombre de metodo");}
break;
case 31:
//#line 66 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: contiene parametros");}
break;
case 32:
//#line 67 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis (");}
break;
case 33:
//#line 68 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis )");}
break;
case 34:
//#line 69 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta begin");}
break;
case 35:
//#line 72 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa ");
												agregarTipoVariables(tipo);

												}
break;
case 36:
//#line 78 "compi/gramatica.y"
{tipo = "int";}
break;
case 37:
//#line 79 "compi/gramatica.y"
{tipo = "float";}
break;
case 38:
//#line 80 "compi/gramatica.y"
{tipo = val_peek(0).sval;
	  		verificarDeclaracionClase(val_peek(0).sval);}
break;
case 39:
//#line 84 "compi/gramatica.y"
{variables.add(val_peek(0).sval);
					 variablesPorUso.add(val_peek(0).sval);}
break;
case 40:
//#line 86 "compi/gramatica.y"
{variables.add(val_peek(2).sval);
								  variablesPorUso.add(val_peek(2).sval);}
break;
case 41:
//#line 90 "compi/gramatica.y"
{listaHerencia.add(val_peek(0).sval);}
break;
case 42:
//#line 91 "compi/gramatica.y"
{listaHerencia.add(val_peek(2).sval);}
break;
case 50:
//#line 103 "compi/gramatica.y"
{yyerror("Error de sentencia");}
break;
case 51:
//#line 106 "compi/gramatica.y"
{desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if ");}
break;
case 52:
//#line 107 "compi/gramatica.y"
{desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else ");}
break;
case 53:
//#line 108 "compi/gramatica.y"
{yyerror("Error en la condicion del if");}
break;
case 54:
//#line 109 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta )");}
break;
case 55:
//#line 110 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta (");}
break;
case 56:
//#line 111 "compi/gramatica.y"
{yyerror("Error en la condicion del if");}
break;
case 57:
//#line 112 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta )");}
break;
case 58:
//#line 113 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta (");}
break;
case 59:
//#line 114 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta end_if");}
break;
case 60:
//#line 115 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta end_if");}
break;
case 61:
//#line 116 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado izquierdo");}
break;
case 62:
//#line 117 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado derecho");}
break;
case 63:
//#line 118 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado izquierdo");}
break;
case 64:
//#line 119 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado derecho");}
break;
case 66:
//#line 124 "compi/gramatica.y"
{desapilarSalto(tercetos.size() + 1); apilarSalto(crearTerceto("BI",null,null,null)); crearTerceto("LABEL","Label"+tercetos.size(),null,null);}
break;
case 68:
//#line 125 "compi/gramatica.y"
{crearTerceto("LABEL","Label"+tercetos.size(),null,null); insertarNoTerminal("condicion",crearTercetoTipo("CMP",tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("factor")))); apilarSalto(crearTerceto("JGE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 69:
//#line 126 "compi/gramatica.y"
{apilarExpresionFor(tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"));}
break;
case 70:
//#line 127 "compi/gramatica.y"
{desapilarExpresionFor(); crearTerceto("BI",null,new String("[" + (pilaSaltos.get(pilaSaltos.size()-1)-2) + "]"),null); desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for ");}
break;
case 71:
//#line 128 "compi/gramatica.y"
{yyerror("Error en la definicion del for");}
break;
case 72:
//#line 129 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta (");}
break;
case 73:
//#line 130 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta )");}
break;
case 74:
//#line 133 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto. ");
									   if(verificarDeclaracionVariable(val_peek(5).sval)){
									   	verificarDeclaracionMetodo(val_peek(5).sval,val_peek(3).sval);
									   	crearTercetoCopiaValor(val_peek(5).sval);
									   	crearTerceto("CALL",val_peek(3).sval,val_peek(5).sval,null);
									   	crearTercetoCopiaResultado(val_peek(5).sval);
									   }
									   }
break;
case 75:
//#line 141 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo");}
break;
case 76:
//#line 142 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo - metodo vacio");}
break;
case 77:
//#line 145 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print ");
									crearTerceto("PRINT",val_peek(2).sval,null,null);}
break;
case 78:
//#line 147 "compi/gramatica.y"
{yyerror("Error en la impresion");}
break;
case 79:
//#line 150 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable. ");
											  insertarNoTerminal("asignacion",crearTercetoTipo(":=",val_peek(3).sval,noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("expresion"))));}
break;
case 80:
//#line 152 "compi/gramatica.y"
{yyerror("Error en la asignacion lado derecho");}
break;
case 81:
//#line 153 "compi/gramatica.y"
{yyerror("Error en la asignacion lado izquierdo");}
break;
case 82:
//#line 156 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 83:
//#line 156 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
					  apilarSalto(crearTerceto("JL",noTerminalTercetos.get("condicion"),null,null));}
break;
case 84:
//#line 158 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 85:
//#line 158 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JG",noTerminalTercetos.get("condicion"),null,null));}
break;
case 86:
//#line 160 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 87:
//#line 160 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		  			  apilarSalto(crearTerceto("JLE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 88:
//#line 162 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 89:
//#line 162 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JGE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 90:
//#line 164 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 91:
//#line 164 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JNE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 92:
//#line 166 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 93:
//#line 166 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 96:
//#line 174 "compi/gramatica.y"
{insertarNoTerminal("expresion",crearTercetoTipo("+",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 97:
//#line 175 "compi/gramatica.y"
{insertarNoTerminal("expresion",crearTercetoTipo("-",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 98:
//#line 176 "compi/gramatica.y"
{insertarNoTerminal("expresion",noTerminalTercetos.get("termino"));}
break;
case 99:
//#line 178 "compi/gramatica.y"
{insertarNoTerminal("termino",crearTercetoTipo("*",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 100:
//#line 179 "compi/gramatica.y"
{insertarNoTerminal("termino",crearTercetoTipo("/",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 101:
//#line 180 "compi/gramatica.y"
{insertarNoTerminal("termino",noTerminalTercetos.get("factor"));}
break;
case 102:
//#line 183 "compi/gramatica.y"
{insertarNoTerminal("factor",val_peek(0).sval);}
break;
case 103:
//#line 184 "compi/gramatica.y"
{insertarNoTerminal("factor",val_peek(0).sval);}
break;
case 105:
//#line 188 "compi/gramatica.y"
{verificarDeclaracionVariable(val_peek(0).sval);}
break;
case 106:
//#line 190 "compi/gramatica.y"
{if(verificarDeclaracionVariable(val_peek(2).sval))
					verificarDeclaracionAtributo(val_peek(2).sval,val_peek(0).sval);
					yyval = new ParserVal(val_peek(2).sval + "@" + val_peek(0).sval);}
break;
case 107:
//#line 195 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}else{
			}}
break;
case 108:
//#line 198 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);
				yyval = new ParserVal("-" + val_peek(0).sval);}
break;
//#line 1419 "Parser.java"
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
