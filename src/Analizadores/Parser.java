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
//#line 28 "Parser.java"




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
    0,    1,    1,    3,    3,    6,    3,    7,    3,    8,
    4,   11,    4,    4,    4,    4,    4,    4,    4,    9,
    9,    9,    9,   13,   12,   12,   12,   12,   12,   12,
    5,   14,   14,   14,   15,   15,   10,   10,    2,    2,
   16,   16,   16,   16,   16,   16,   19,   24,   19,   19,
   19,   19,   19,   19,   19,   26,   27,   20,   20,   20,
   20,   21,   21,   21,   18,   18,   17,   17,   17,   30,
   22,   31,   22,   32,   22,   33,   22,   34,   22,   35,
   22,   23,   23,   29,   29,   29,   36,   36,   36,   25,
   25,   25,   28,   28,   37,   37,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    0,    3,    0,    3,    0,
    6,    0,    8,    7,    5,    5,    7,    7,    7,    1,
    2,    1,    2,    0,    8,    7,    8,    7,    7,    7,
    3,    1,    1,    1,    1,    3,    1,    3,    1,    2,
    1,    1,    1,    1,    1,    2,    6,    0,    9,    6,
    5,    5,    8,    7,    7,    0,    0,   11,    6,    5,
    5,    6,    5,    6,    5,    3,    4,    4,    4,    0,
    4,    0,    4,    0,    4,    0,    4,    0,    4,    0,
    4,    1,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
   34,   32,    0,   33,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   41,   42,
   43,   44,   45,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   46,    0,    0,    0,    0,    0,    0,    0,
    2,   40,    0,    0,    0,    0,    0,    0,    0,    9,
    7,    0,   31,    0,   95,   92,    0,   89,   90,    0,
    0,   91,    0,    0,    0,    0,    0,    0,   66,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,   36,    0,
   96,   69,    0,    0,    0,    0,    0,    0,    0,    0,
   82,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   56,   68,   67,    0,    0,
   21,   15,   23,    0,    0,   16,    0,    0,    0,    0,
    0,   94,    0,    0,   87,   88,    0,   63,    0,    0,
    0,   52,    0,    0,   51,    0,    0,    0,    0,    0,
    0,    0,   65,   60,    0,   61,    0,    0,    0,    0,
   38,    0,    0,    0,    0,    0,   11,   64,   62,   83,
    0,    0,   50,    0,   48,   47,    0,    0,    0,    0,
    0,    0,   59,    0,    0,    0,    0,    0,   14,   19,
   18,   17,    0,   55,    0,   54,    0,   57,    0,    0,
    0,    0,    0,    0,   13,   53,    0,    0,    0,    0,
    0,    0,    0,    0,   49,    0,   26,   28,   29,    0,
   30,    0,    0,   27,   25,   58,
};
final static short yydgoto[] = {                          6,
    7,   17,    8,    9,   77,   29,   28,   48,   78,   81,
  130,   79,  204,   11,   31,  101,   19,   20,   21,   22,
   23,   67,  102,  197,   58,  157,  208,   24,   60,  106,
  107,  108,  109,  110,  111,   61,   62,
};
final static short yysindex[] = {                       -96,
    0,    0,  -93,    0, -207,    0,    0, -255,    0,    0,
 -198,  -52,   22,  -29,  -26,  -12, -183,  -93,    0,    0,
    0,    0,    0, -187, -176, -202,  -93,  -62,  -62,   52,
   33,  -20,    0, -145,   58,   13,   47, -161,   73, -137,
    0,    0,   16, -197, -133, -172, -129, -124, -134,    0,
    0, -198,    0,  108,    0,    0, -122,    0,    0,   12,
   63,    0,  122,   -7, -141,    1,  124,   29,    0,  130,
 -141,  -38,  -20,  114,   20, -127, -197,  -88, -197,  135,
  -81,  135,  -80,  -73,  -61,  -54, -197,    0,    0,  -49,
    0,    0,  -20,  -20,  -20,  -20,  164,  148,  168,  -93,
    0, -128, -141, -113, -141,  -63,  -60,  149,  152,  -59,
  -56,  154,  156, -141,  158,    0,    0,    0,  182,    3,
    0,    0,    0, -133, -197,    0, -197, -197, -197,  -14,
  -42,    0,   63,   63,    0,    0,  197,    0,  202,   -3,
 -141,    0, -111, -141,    0, -109,  -20,  -20,  -20,  -20,
  -20,  -20,    0,    0,  205,    0,  207,  226,  241,  -25,
    0,   21,   23,   30,   31, -197,    0,    0,    0,    0,
   24, -141,    0,   36,    0,    0,   29,   29,   29,   29,
   29,   29,    0,  -20,   34,   35,  -36,   44,    0,    0,
    0,    0,   38,    0,   40,    0, -141,    0,  -93,  -93,
  -93,   41,  -93,   42,    0,    0,   46,  267,   45,   49,
   50,  -93,   51,  -93,    0, -141,    0,    0,    0,   53,
    0,   54,  250,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -82,  -70,
    0,    0,   43,    0,    0,    0,    0,   56,    0,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,  252,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
  -33,    0,    0,   55,    0,    0,    0,    2,    0,    0,
    0,    0,    0,    0,    0,    0,   57,    0,   60, -186,
    0, -184,    0,    0,    0,   62,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -28,  -21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   73,  271,  272,  276,
  283,  286,    0,    0,    0,    0,    0,   64,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   90,  160,    0,   48,    0,    0,    0,   10,  -11,
    0,    0,    0,    0,  277,   80,  290,    0,    0,    0,
    0,    0,  -19,    0,  -50,    0,    0,    8,   -6,    0,
    0,    0,    0,    0,    0,  106,    0,
};
final static int YYTABLESIZE=332;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         93,
   93,   93,  114,   93,  202,   93,   33,   86,   27,   86,
   36,   86,   84,   38,   84,  188,   84,   93,   93,   85,
   93,   85,  116,   85,   57,   86,   86,   40,   86,   68,
   84,   84,   99,   84,   84,   86,   75,   85,   85,   59,
   85,  103,  160,   59,  135,  136,  104,   10,   25,   26,
   59,  113,  115,   46,   93,   83,   94,   57,   30,    1,
   57,   76,   93,   74,   94,    2,   47,   34,    4,   37,
   92,   93,   34,   94,   76,   10,   10,   37,  118,   37,
   59,   41,   18,  143,   82,  146,  121,   44,  123,   43,
    2,   53,   45,    4,  155,   52,  131,   18,   65,   76,
   59,   59,   59,   59,   95,   69,   18,   42,   70,   96,
   63,   64,  161,   71,   12,   13,   49,   14,   72,   54,
   15,  171,  100,   80,  174,   16,   85,   80,  119,  120,
   88,  141,  142,  198,  162,   91,  163,  164,  165,   87,
  177,  178,  179,  180,  181,  182,  144,  145,  172,  173,
  175,  176,  195,   90,   59,   59,   59,   59,   59,   59,
    1,   97,   12,   13,  105,   14,    2,    3,   15,    4,
  112,    5,  117,   16,    8,  193,  122,  207,  124,   18,
    8,    4,  125,    8,  126,    8,    6,   50,   51,  140,
  127,   59,    6,    5,    1,    6,  223,    6,  133,  134,
    2,  129,  128,    4,  137,    5,  138,  132,  139,  147,
  149,  150,  153,  148,  154,  151,  156,   12,   13,  152,
   14,  158,  167,   15,   32,  100,   35,  201,   16,   37,
  187,   93,   93,   93,   93,   93,   54,   55,   32,   86,
   86,   86,   86,   39,   84,   84,   84,   84,   98,  166,
   56,   85,   85,   85,   85,  168,   12,   13,  159,   14,
  169,  170,   15,  183,  100,  184,  185,   16,   66,   54,
   55,   74,   54,   55,   70,   72,   78,   80,   18,   18,
   18,  186,   18,   56,  194,  189,   56,  190,  209,  210,
  211,   18,  213,   18,  191,  192,  196,  199,  200,  203,
  206,  220,  205,  222,  212,  214,  215,  216,  226,  217,
   35,   73,   75,  218,  219,  221,   77,  224,  225,   93,
   39,   20,   10,   79,   22,   12,   81,   24,   89,   73,
    0,   94,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   59,   41,  264,   43,
   40,   45,   41,   40,   43,   41,   45,   59,   60,   41,
   62,   43,   73,   45,   45,   59,   60,   40,   62,   36,
   59,   60,   40,   62,   46,   47,   43,   59,   60,   32,
   62,   41,   40,   36,   95,   96,   66,    0,  256,  257,
   43,   71,   72,  256,   43,   46,   45,   45,  257,  257,
   45,   60,   43,   62,   45,  263,  269,   46,  266,  256,
   59,   43,  257,   45,  272,   28,   29,  264,   59,  264,
   73,  265,    3,  103,  257,  105,   77,  264,   79,  277,
  263,   59,  269,  266,  114,   44,   87,   18,   41,  272,
   93,   94,   95,   96,   42,   59,   27,   18,  270,   47,
  256,  257,  124,   41,  256,  257,   27,  259,  256,  257,
  262,  141,  264,  257,  144,  267,  256,  257,  256,  257,
  265,  260,  261,  184,  125,  258,  127,  128,  129,  264,
  147,  148,  149,  150,  151,  152,  260,  261,  260,  261,
  260,  261,  172,   46,  147,  148,  149,  150,  151,  152,
  257,   40,  256,  257,   41,  259,  263,  264,  262,  266,
   41,  268,   59,  267,  257,  166,  265,  197,   44,  100,
  263,  264,  264,  266,  265,  268,  257,   28,   29,  100,
  264,  184,  263,  264,  257,  266,  216,  268,   93,   94,
  263,  256,  264,  266,   41,  268,   59,  257,   41,  273,
   62,   60,   59,  274,   59,  275,   59,  256,  257,  276,
  259,   40,  265,  262,  277,  264,  256,  264,  267,  256,
  256,  273,  274,  275,  276,  277,  257,  258,  277,  273,
  274,  275,  276,  256,  273,  274,  275,  276,  256,  264,
  271,  273,  274,  275,  276,   59,  256,  257,  256,  259,
   59,  265,  262,   59,  264,   59,   41,  267,  256,  257,
  258,  256,  257,  258,  273,  274,  275,  276,  199,  200,
  201,   41,  203,  271,  261,  265,  271,  265,  199,  200,
  201,  212,  203,  214,  265,  265,  261,  264,  264,  256,
  261,  212,  265,  214,  264,  264,  261,   41,   59,  265,
   59,   41,   41,  265,  265,  265,   41,  265,  265,  277,
  265,  265,  264,   41,  265,  264,   41,  264,   52,   40,
   -1,  277,
};
}
final static short YYFINAL=6;
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
"programa : conjunto_sentencias",
"conjunto_sentencias : BEGIN sentencias_ejecutables END",
"conjunto_sentencias : declaraciones BEGIN sentencias_ejecutables END",
"declaraciones : declaracion_clase",
"declaraciones : declaracion_sentencia",
"$$1 :",
"declaraciones : declaracion_sentencia $$1 declaraciones",
"$$2 :",
"declaraciones : declaracion_clase $$2 declaraciones",
"$$3 :",
"declaracion_clase : CLASS ID $$3 BEGIN declaracion_sentencia_clase END",
"$$4 :",
"declaracion_clase : CLASS ID EXTENDS lista_clases $$4 BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS error EXTENDS lista_clases BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS error BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID error declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS lista_clases error declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS error BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID error lista_clases BEGIN declaracion_sentencia_clase END",
"declaracion_sentencia_clase : declaracion_sentencia",
"declaracion_sentencia_clase : declaracion_sentencia declaracion_sentencia_clase",
"declaracion_sentencia_clase : declaracion_metodo",
"declaracion_sentencia_clase : declaracion_metodo declaracion_sentencia_clase",
"$$5 :",
"declaracion_metodo : VOID ID '(' ')' $$5 BEGIN sentencias_ejecutables END",
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
"seleccion : IF '(' condicion ')' bloque END_IF",
"$$6 :",
"seleccion : IF '(' condicion ')' bloque ELSE $$6 bloque END_IF",
"seleccion : IF '(' error ')' bloque END_IF",
"seleccion : IF '(' error bloque END_IF",
"seleccion : IF error ')' bloque END_IF",
"seleccion : IF '(' error ')' bloque ELSE bloque END_IF",
"seleccion : IF '(' error bloque ELSE bloque END_IF",
"seleccion : IF error ')' bloque ELSE bloque END_IF",
"$$7 :",
"$$8 :",
"iteracion : FOR '(' asignacion factor $$7 ';' factor $$8 ')' bloque ';'",
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
"$$9 :",
"condicion : expresion $$9 MAYOR_IGUAL expresion",
"$$10 :",
"condicion : expresion $$10 MENOR_IGUAL expresion",
"$$11 :",
"condicion : expresion $$11 '>' expresion",
"$$12 :",
"condicion : expresion $$12 '<' expresion",
"$$13 :",
"condicion : expresion $$13 IGUAL expresion",
"$$14 :",
"condicion : expresion $$14 DISTINTO expresion",
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

//#line 185 "compi/gramatica.y"
	AnalizadorLexico aLexico=new AnalizadorLexico();
	ArrayList<String> errores = new ArrayList<String>();
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
		if(aLexico.getErrores().size() == 0 && errores.size() == 0){
			noTerminalTercetos.put(noTerminal,value);
		}
	}
	public String crearTerceto(String operador,String operando1,String operando2,String tipo) {
		if(aLexico.getErrores().size() == 0 && errores.size() == 0){
			tercetos.add(new Terceto(operador,operando1,operando2,tipo));
			return new String("["+Integer.toString(tercetos.size()-1)+"]");
		}else{
			return null;
		}
	}

	public boolean checkeoTipos(String operando1,String operando2){
		if(getTipo(operando1) != getTipo(operando2)){
				yyerror("Incopatibilidad de tipos");
				return false;
		}
		return true;
	}

	public void apilarSalto(String numTerceto){
			pilaSaltos.add(Integer.parseInt(numTerceto.substring(1,numTerceto.length()-1)));
	}


	public void apilarExpresionFor(String operando1, String operando2){
		if(getTipo(operando2)=="int" && getTipo(operando1)== "int"){
			pilaExpresionFor.add(new Terceto(":=",operando1,null,"int"));
			pilaExpresionFor.add(new Terceto("+",operando1,operando2,"int"));
		}else{
			yyerror("El tipo de los operandos del for deben ser de tipo int");
		}
	}

	public void desapilarExpresionFor(){
		tercetos.add(pilaExpresionFor.remove(pilaExpresionFor.size()-1));
		tercetos.add(pilaExpresionFor.remove(pilaExpresionFor.size()-1));
		tercetos.get(tercetos.size()-1).operando2 = new String("[" + (tercetos.size()-2)+ "]");
	}

	public void desapilarSalto(Integer posicion){
			Integer pos = pilaSaltos.get(pilaSaltos.size()-1);
			tercetos.get(pos).operando2 = new String("[" + posicion + "]"); 
			pilaSaltos.remove(pilaSaltos.size()-1);
	}

	public String crearTercetoTipo(String operador,String operando1,String operando2,String tipo){
		if(checkeoTipos(operando1,operando2)){
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

	public ArrayList<String> getErrores() {
		ArrayList<String> erroresTotales = new ArrayList<String>();
		erroresTotales.add("Errores lexicos:");
		erroresTotales.addAll(aLexico.getErrores());		
		erroresTotales.add("Errores sintacticos:");
		erroresTotales.addAll(errores);
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
			aLexico.agregarAtributoLexema(s,"Tipo",tipo);
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
			aLexico.agregarAtributoLexema(s,"Uso",uso);
		}
		variablesPorUso.clear();
	}

	public void inicializarAtributos(String clase){
		aLexico.agregarAtributoLexema(clase,"VariablesClase",new ArrayList<String>());
		aLexico.agregarAtributoLexema(clase,"MetodosClase",new ArrayList<String>());
		ambitoActual = clase;
	}
	public void agregarAtributoHeredados(String clase){
		for(String padre: listaHerencia){
				((ArrayList<String>)aLexico.getTablaSimbolos().get(clase).get("VariablesClase")).addAll((ArrayList<String>)(aLexico.getTablaSimbolos().get(padre).get("VariablesClase")));
				((ArrayList<String>)aLexico.getTablaSimbolos().get(clase).get("MetodosClase")).addAll((ArrayList<String>)(aLexico.getTablaSimbolos().get(padre).get("MetodosClase")));
		}
		listaHerencia.clear();
	}
	
	public void verificarDeclaracionVariable(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if((!tablaSimbolos.get(id).containsKey("Uso") || !tablaSimbolos.get(id).get("Uso").equals("variable")) && !((ArrayList<String>)tablaSimbolos.get(ambitoActual).get("VariablesClase")).contains(id) && !variablesPorUso.contains(id)){
			errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - Error - Uso de variable no declarada.");
		}
	}

	public void verificarDeclaracionClase(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!tablaSimbolos.get(id).containsKey("Uso") || !tablaSimbolos.get(id).get("Uso").toString().equals("nombreClase")){
			errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - Error - Tipo de variable no declarado.");
		}
	}

	public void verificarDeclaracionAtributo(String idClase, String atributo){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!((ArrayList<String>)tablaSimbolos.get(tablaSimbolos.get(idClase).get("Tipo")).get("VariablesClase")).contains(atributo)){
			errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - Error - El objeto no tiene ese atributo.");
		}
	}

	public void verificarDeclaracionMetodo(String idClase, String metodo){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!((ArrayList<String>)tablaSimbolos.get(tablaSimbolos.get(idClase).get("Tipo")).get("MetodosClase")).contains(metodo)){
			errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - Error - El objeto no tiene ese metodo.");
		}
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
				
		    	escribir.write("Tokens reconocidos:");
				escribir.write(sb.toString());
				escribir.write(sb.toString());
				for(String s: tokens){
					escribir.write(s.toString()+ " - ");
				}

				escribir.write(sb.toString());
				escribir.write(sb.toString());
		    	escribir.write("Estructuras reconocidas:");
				escribir.write(sb.toString());
				escribir.write(sb.toString());
				for(String s: salida){
					escribir.write(s.toString());
					escribir.write(sb.toString());
				}

				escribir.write(sb.toString());
				for(String s: this.getErrores()){
					escribir.write(s.toString());
					escribir.write(sb.toString());
				}

				escribir.write(aLexico.getDatosTablaSimbolos());
				escribir.write(sb.toString());
		        escribir.close();
	
		    } catch (FileNotFoundException ex) {
		    } catch (IOException ex) {
		    }
	    }

	}

	public static void main(String args[])
	{
		Parser par = new Parser(false);
		System.out.println(par.yyparse());
		par.saveFile();
		System.out.println(par.tercetos.toString());
		int i =0;
		for(Terceto s : par.tercetos) {
			System.out.println(i++ + s.toString());
		}
	}
//#line 670 "Parser.java"
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
//#line 17 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");}
break;
case 2:
//#line 20 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones. ");}
break;
case 3:
//#line 21 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones. ");}
break;
case 4:
//#line 24 "compi/gramatica.y"
{agregarUsoVariables("nombreAtributo");}
break;
case 5:
//#line 25 "compi/gramatica.y"
{agregarUsoVariables("variable");}
break;
case 6:
//#line 26 "compi/gramatica.y"
{agregarUsoVariables("variable");}
break;
case 8:
//#line 27 "compi/gramatica.y"
{agregarUsoVariables("nombreAtributo");}
break;
case 10:
//#line 30 "compi/gramatica.y"
{inicializarAtributos(val_peek(0).sval);}
break;
case 11:
//#line 30 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase. ");
																   aLexico.agregarAtributoLexema(val_peek(4).sval,"Uso","nombreClase");
																   agregarAtributoAClase(val_peek(4).sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
break;
case 12:
//#line 35 "compi/gramatica.y"
{inicializarAtributos(val_peek(2).sval); agregarAtributoHeredados(val_peek(2).sval);}
break;
case 13:
//#line 35 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple. ");
				  												   aLexico.agregarAtributoLexema(val_peek(6).sval,"Uso","nombreClase");
																   agregarAtributoAClase(val_peek(6).sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
break;
case 14:
//#line 40 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 15:
//#line 41 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 16:
//#line 42 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 17:
//#line 43 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 18:
//#line 44 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase extendidas");}
break;
case 19:
//#line 45 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta palabra reservada extends");}
break;
case 24:
//#line 56 "compi/gramatica.y"
{crearTerceto("metodo",val_peek(2).sval,null,null);}
break;
case 25:
//#line 56 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo. ");
																	 aLexico.agregarAtributoLexema(val_peek(6).sval,"Uso","nombreMetodo");
																	 metodosPorClase.add(val_peek(6).sval);
																	 crearTerceto("termina_metodo",null,null,null);
																	 }
break;
case 26:
//#line 61 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta nombre de metodo");}
break;
case 27:
//#line 62 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: contiene parametros");}
break;
case 28:
//#line 63 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis (");}
break;
case 29:
//#line 64 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis )");}
break;
case 30:
//#line 65 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta begin");}
break;
case 31:
//#line 68 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa ");
												agregarTipoVariables(tipo);

												}
break;
case 32:
//#line 74 "compi/gramatica.y"
{tipo = "int";}
break;
case 33:
//#line 75 "compi/gramatica.y"
{tipo = "float";}
break;
case 34:
//#line 76 "compi/gramatica.y"
{tipo = val_peek(0).sval;
	  		verificarDeclaracionClase(val_peek(0).sval);}
break;
case 35:
//#line 80 "compi/gramatica.y"
{variables.add(val_peek(0).sval);
					 variablesPorUso.add(val_peek(0).sval);}
break;
case 36:
//#line 82 "compi/gramatica.y"
{variables.add(val_peek(2).sval);
								  variablesPorUso.add(val_peek(2).sval);}
break;
case 37:
//#line 86 "compi/gramatica.y"
{listaHerencia.add(val_peek(0).sval);}
break;
case 38:
//#line 87 "compi/gramatica.y"
{listaHerencia.add(val_peek(2).sval);}
break;
case 46:
//#line 99 "compi/gramatica.y"
{yyerror("Error de sentencia");}
break;
case 47:
//#line 102 "compi/gramatica.y"
{desapilarSalto(tercetos.size()); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if ");}
break;
case 48:
//#line 103 "compi/gramatica.y"
{desapilarSalto(tercetos.size() + 1); apilarSalto(crearTerceto("BI",null,null,null));}
break;
case 49:
//#line 103 "compi/gramatica.y"
{desapilarSalto(tercetos.size()); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else ");}
break;
case 50:
//#line 104 "compi/gramatica.y"
{yyerror("Error en la definicion del if");}
break;
case 51:
//#line 105 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta )");}
break;
case 52:
//#line 106 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta (");}
break;
case 53:
//#line 107 "compi/gramatica.y"
{yyerror("Error en la definicion del if");}
break;
case 54:
//#line 108 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta )");}
break;
case 55:
//#line 109 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta (");}
break;
case 56:
//#line 112 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("<",tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"),"boolean")); apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 57:
//#line 113 "compi/gramatica.y"
{apilarExpresionFor(tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"));}
break;
case 58:
//#line 114 "compi/gramatica.y"
{desapilarExpresionFor(); crearTerceto("BI",null,new String("[" + (pilaSaltos.get(pilaSaltos.size()-1)-1) + "]"),null); desapilarSalto(tercetos.size()); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for ");}
break;
case 59:
//#line 115 "compi/gramatica.y"
{yyerror("Error en la definicion del for");}
break;
case 60:
//#line 116 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta (");}
break;
case 61:
//#line 117 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta )");}
break;
case 62:
//#line 120 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto. ");
									   verificarDeclaracionVariable(val_peek(5).sval);
									   verificarDeclaracionMetodo(val_peek(5).sval,val_peek(3).sval);
									   crearTerceto("call",val_peek(3).sval,val_peek(5).sval,null);
									   }
break;
case 63:
//#line 125 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo");}
break;
case 64:
//#line 126 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo - metodo vacio");}
break;
case 65:
//#line 129 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print ");}
break;
case 66:
//#line 130 "compi/gramatica.y"
{yyerror("Error en la impresion");}
break;
case 67:
//#line 133 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable. ");
											  insertarNoTerminal("asignacion",crearTercetoTipo(":=",val_peek(3).sval,noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get(val_peek(3).sval))));}
break;
case 68:
//#line 135 "compi/gramatica.y"
{yyerror("Error en la asignacion lado derecho");}
break;
case 69:
//#line 136 "compi/gramatica.y"
{yyerror("Error en la asignacion lado izquierdo");}
break;
case 70:
//#line 139 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 71:
//#line 139 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo(">=",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
					  apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 72:
//#line 141 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 73:
//#line 141 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("<=",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 74:
//#line 143 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 75:
//#line 143 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo(">",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		  			  apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 76:
//#line 145 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 77:
//#line 145 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("<",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 78:
//#line 147 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 79:
//#line 147 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("==",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 80:
//#line 149 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 81:
//#line 149 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("<>",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("BF",noTerminalTercetos.get("condicion"),null,null));}
break;
case 84:
//#line 157 "compi/gramatica.y"
{insertarNoTerminal("expresion",crearTercetoTipo("+",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 85:
//#line 158 "compi/gramatica.y"
{insertarNoTerminal("expresion",crearTercetoTipo("-",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 86:
//#line 159 "compi/gramatica.y"
{insertarNoTerminal("expresion",noTerminalTercetos.get("termino"));}
break;
case 87:
//#line 161 "compi/gramatica.y"
{insertarNoTerminal("termino",crearTercetoTipo("*",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 88:
//#line 162 "compi/gramatica.y"
{insertarNoTerminal("termino",crearTercetoTipo("/",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 89:
//#line 163 "compi/gramatica.y"
{insertarNoTerminal("termino",noTerminalTercetos.get("factor"));}
break;
case 90:
//#line 166 "compi/gramatica.y"
{insertarNoTerminal("factor",val_peek(0).sval);}
break;
case 91:
//#line 167 "compi/gramatica.y"
{insertarNoTerminal("factor",val_peek(0).sval);}
break;
case 93:
//#line 171 "compi/gramatica.y"
{verificarDeclaracionVariable(val_peek(0).sval);}
break;
case 94:
//#line 173 "compi/gramatica.y"
{verificarDeclaracionVariable(val_peek(2).sval);
					verificarDeclaracionAtributo(val_peek(2).sval,val_peek(0).sval);
					yyval = new ParserVal(val_peek(2).sval + "@" + val_peek(0).sval);}
break;
case 95:
//#line 178 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}else{
			}}
break;
case 96:
//#line 181 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);
				yyval = new ParserVal("-" + val_peek(0).sval);}
break;
//#line 1173 "Parser.java"
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
