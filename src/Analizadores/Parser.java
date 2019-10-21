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
    9,    9,    9,   12,   12,   12,   12,   12,   12,    5,
   13,   13,   13,   14,   14,   10,   10,    2,    2,   15,
   15,   15,   15,   15,   15,   18,   18,   18,   18,   18,
   18,   18,   18,   19,   19,   19,   19,   20,   20,   20,
   17,   17,   16,   16,   16,   21,   21,   21,   21,   21,
   21,   22,   22,   23,   23,   23,   25,   25,   25,   26,
   26,   26,   24,   24,   27,   27,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    0,    3,    0,    3,    0,
    6,    0,    8,    7,    5,    5,    7,    7,    7,    1,
    2,    1,    2,    7,    7,    8,    7,    7,    7,    3,
    1,    1,    1,    1,    3,    1,    3,    1,    2,    1,
    1,    1,    1,    1,    2,    6,    8,    6,    5,    5,
    8,    7,    7,    9,    6,    5,    5,    6,    5,    6,
    5,    3,    4,    4,    4,    3,    3,    3,    3,    3,
    3,    1,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
   33,   31,    0,   32,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   40,   41,
   42,   43,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   45,    0,    0,    0,    0,    0,    0,    0,
    2,   39,    0,    0,    0,    0,    0,    0,    0,    9,
    7,    0,   30,    0,   85,   82,    0,    0,   80,    0,
   79,   81,    0,    0,    0,    0,    0,    0,   62,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,   35,    0,
   86,   65,    0,    0,    0,    0,    0,    0,    0,    0,
   72,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   64,   63,    0,    0,
   21,   15,   23,    0,    0,   16,    0,    0,    0,    0,
    0,   84,    0,    0,   77,   78,    0,   59,    0,    0,
    0,   50,    0,    0,   49,    0,    0,    0,    0,    0,
    0,    0,   61,   56,    0,   57,    0,    0,    0,    0,
   37,    0,    0,    0,    0,    0,   11,   60,   58,   73,
    0,    0,   48,    0,    0,   46,   55,    0,    0,    0,
    0,    0,   14,   19,   18,   17,    0,   53,    0,   52,
    0,    0,    0,    0,    0,    0,    0,    0,   13,   51,
   47,    0,    0,    0,    0,    0,    0,    0,   54,   25,
   27,   28,    0,   29,   24,   26,
};
final static short yydgoto[] = {                          6,
    7,   17,    8,    9,   77,   29,   28,   48,   78,   81,
  130,   79,   11,   31,  101,   19,   20,   21,   22,   23,
   67,  102,   58,   24,   60,   61,   62,
};
final static short yysindex[] = {                      -171,
    0,    0, -177,    0, -214,    0,    0, -255,    0,    0,
 -203,  -52,   20,  -29,  -26,   -7, -169, -177,    0,    0,
    0,    0,    0, -146, -204, -216, -177, -150, -150,   56,
   91,  -20,    0, -133,  117,   19,  101, -108,  124, -130,
    0,    0,   22, -109,  -89, -102, -127,  -93,  -87,    0,
    0, -203,    0,  129,    0,    0,  -77,   14,    0,   62,
    0,    0,  153,    6,  -90,    7,  154,  -15,    0,  164,
  -90,  -38,  -20,  149,   18, -112, -109,  -56, -109,  169,
  -48,  169,  -45,  -47,  -42,  -12, -109,    0,    0,   -1,
    0,    0,  -20,  -20,  -20,  -20,  209,  198,  226, -177,
    0, -114,  -90,  -74,  -90,  -20,  -20,  -20,  -20,  -20,
  -20,  211,  213,  -90,  214,   29,    0,    0,  228,    9,
    0,    0,    0,  -89, -109,    0, -109, -109, -109,   25,
   27,    0,   62,   62,    0,    0,  225,    0,  229,   30,
  -90,    0,  -63,  -90,    0,  -61,   38,   38,   38,   38,
   38,   38,    0,    0,  232,    0,  -20,  246,  255,  -25,
    0,   33,   34,   35,   36, -109,    0,    0,    0,    0,
   41,  -90,    0,   42,  -90,    0,    0,   67,   40,   43,
  -36, -180,    0,    0,    0,    0,   44,    0,   45,    0,
   47,  -90, -177, -177, -177,   46, -177, -177,    0,    0,
    0,  238,   48,   49,   50, -177,   51,   52,    0,    0,
    0,    0,   53,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -84,  -72,
    0,    0,   28,    0,    0,    0,    0,   54,    0,    0,
    0,    0,    0,    0,    0,   57,    0,    0,    0,  252,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,  -33,
    0,    0,    0,   55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,    0,   59, -162,
    0, -186,    0,    0,    0,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -28,  -21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  271,  279,  281,  124,
  285,  286,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   17,  173,    0,   23,    0,    0,    0,   24,  -10,
    0,    0,    0,  276,   88,  289,    0,    0,    0,    0,
    0,  -16,   32,   26,  110,  111,    0,
};
final static int YYTABLESIZE=332;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
   83,   83,  114,   83,  196,   83,   33,   76,   27,   76,
   36,   76,   74,   38,   74,  182,   74,   83,   83,   75,
   83,   75,   10,   75,   57,   76,   76,   93,   76,   94,
   74,   74,   40,   74,   42,   84,   86,   75,   75,   46,
   75,   25,   26,   49,  111,   99,  110,  103,  160,  104,
   10,   10,   47,   30,  113,  115,   93,   59,   94,   44,
   93,   59,   94,   57,   45,   34,   57,   68,   59,   83,
   33,   93,   92,   94,   75,  197,  118,   36,   12,   13,
   93,   14,   94,  198,   15,    1,  143,  157,  146,   16,
   18,    2,    3,   36,    4,   41,    5,  155,   59,   52,
  121,   36,  123,   95,  116,   18,    1,  192,   96,   93,
  131,   94,    2,  161,   18,    4,  140,    5,   59,   59,
   59,   59,   63,   64,  171,   72,   54,  174,   85,   80,
   43,   59,   59,   59,   59,   59,   59,  147,  148,  149,
  150,  151,  152,  119,  120,  141,  142,    1,  162,   53,
  163,  164,  165,    2,   82,  189,    4,   65,  191,   69,
    2,   70,   76,    4,   71,   12,   13,   80,   14,   76,
   87,   15,    8,  100,   90,  202,   16,   88,    8,    4,
   91,    8,   59,    8,    6,  144,  145,   18,  178,  187,
    6,    5,   97,    6,  105,    6,  172,  173,  175,  176,
   50,   51,  133,  134,  112,  135,  136,  117,  122,  203,
  204,  205,  124,  207,  208,  125,  127,   12,   13,  126,
   14,  128,  213,   15,   32,  100,   35,  195,   16,   37,
  181,   83,   83,   83,   83,   83,   54,   55,   32,   76,
   76,   76,   76,  129,   74,   74,   74,   74,   39,  137,
   56,   75,   75,   75,   75,  132,  138,  106,  107,  108,
  109,   98,   12,   13,  159,   14,  139,  158,   15,  153,
  100,  154,  156,   16,   66,   54,   55,   74,   54,   55,
   18,   18,   18,  168,   18,   18,  179,  169,  166,   56,
  177,  167,   56,   18,  170,  180,  209,  183,  184,  185,
  186,  188,  190,  193,   83,  200,  194,  201,  199,  206,
   34,   66,  210,  211,  212,  214,  215,  216,   38,   67,
   10,   70,   20,   22,   12,   68,   69,   89,   73,    0,
    0,   84,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   59,   41,  264,   43,
   40,   45,   41,   40,   43,   41,   45,   59,   60,   41,
   62,   43,    0,   45,   45,   59,   60,   43,   62,   45,
   59,   60,   40,   62,   18,   46,   47,   59,   60,  256,
   62,  256,  257,   27,   60,   40,   62,   41,   40,   66,
   28,   29,  269,  257,   71,   72,   43,   32,   45,  264,
   43,   36,   45,   45,  269,   46,   45,   36,   43,   46,
  257,   43,   59,   45,   43,  256,   59,  264,  256,  257,
   43,  259,   45,  264,  262,  257,  103,   59,  105,  267,
    3,  263,  264,  256,  266,  265,  268,  114,   73,   44,
   77,  264,   79,   42,   73,   18,  257,   41,   47,   43,
   87,   45,  263,  124,   27,  266,  100,  268,   93,   94,
   95,   96,  256,  257,  141,  256,  257,  144,  256,  257,
  277,  106,  107,  108,  109,  110,  111,  106,  107,  108,
  109,  110,  111,  256,  257,  260,  261,  257,  125,   59,
  127,  128,  129,  263,  257,  172,  266,   41,  175,   59,
  263,  270,  272,  266,   41,  256,  257,  257,  259,  272,
  264,  262,  257,  264,   46,  192,  267,  265,  263,  264,
  258,  266,  157,  268,  257,  260,  261,  100,  157,  166,
  263,  264,   40,  266,   41,  268,  260,  261,  260,  261,
   28,   29,   93,   94,   41,   95,   96,   59,  265,  193,
  194,  195,   44,  197,  198,  264,  264,  256,  257,  265,
  259,  264,  206,  262,  277,  264,  256,  264,  267,  256,
  256,  273,  274,  275,  276,  277,  257,  258,  277,  273,
  274,  275,  276,  256,  273,  274,  275,  276,  256,   41,
  271,  273,  274,  275,  276,  257,   59,  273,  274,  275,
  276,  256,  256,  257,  256,  259,   41,   40,  262,   59,
  264,   59,   59,  267,  256,  257,  258,  256,  257,  258,
  193,  194,  195,   59,  197,  198,   41,   59,  264,  271,
   59,  265,  271,  206,  265,   41,   59,  265,  265,  265,
  265,  261,  261,  264,  277,  261,  264,  261,  265,  264,
   59,   41,  265,  265,  265,  265,  265,  265,  265,   41,
  264,   41,  265,  265,  264,   41,   41,   52,   40,   -1,
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
"declaracion_metodo : VOID ID '(' ')' BEGIN sentencias_ejecutables END",
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
"seleccion : IF '(' condicion ')' bloque ELSE bloque END_IF",
"seleccion : IF '(' error ')' bloque END_IF",
"seleccion : IF '(' error bloque END_IF",
"seleccion : IF error ')' bloque END_IF",
"seleccion : IF '(' error ')' bloque ELSE bloque END_IF",
"seleccion : IF '(' error bloque ELSE bloque END_IF",
"seleccion : IF error ')' bloque ELSE bloque END_IF",
"iteracion : FOR '(' asignacion expresion ';' expresion ')' bloque ';'",
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
"condicion : expresion MAYOR_IGUAL expresion",
"condicion : expresion MENOR_IGUAL expresion",
"condicion : expresion '>' expresion",
"condicion : expresion '<' expresion",
"condicion : expresion IGUAL expresion",
"condicion : expresion DISTINTO expresion",
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

//#line 169 "compi/gramatica.y"
	AnalizadorLexico aLexico=new AnalizadorLexico();
	ArrayList<String> errores = new ArrayList<String>();
	ArrayList<String> salida = new ArrayList<String>();
	ArrayList<String> tokens = new ArrayList<String>();
	ArrayList<String> variables = new ArrayList<String>();
	ArrayList<String> variablesPorUso = new ArrayList<String>();
	ArrayList<String> metodosPorClase = new ArrayList<String>();
	ArrayList<String> listaHerencia = new ArrayList<String>();
	String tipo, ambitoActual;

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

	public void agregarTipoVariables(String tipo){
		for(String s: variables){
			aLexico.agregarAtributoLexema(s,"Tipo",tipo);
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
	}
//#line 567 "Parser.java"
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
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo. ");
																	 aLexico.agregarAtributoLexema(val_peek(5).sval,"Uso","nombreMetodo");
																	 metodosPorClase.add(val_peek(5).sval);
																	 }
break;
case 25:
//#line 60 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta nombre de metodo");}
break;
case 26:
//#line 61 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: contiene parametros");}
break;
case 27:
//#line 62 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis (");}
break;
case 28:
//#line 63 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis )");}
break;
case 29:
//#line 64 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta begin");}
break;
case 30:
//#line 67 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa ");
												agregarTipoVariables(tipo);
												}
break;
case 31:
//#line 72 "compi/gramatica.y"
{tipo = "Int";}
break;
case 32:
//#line 73 "compi/gramatica.y"
{tipo = "Float";}
break;
case 33:
//#line 74 "compi/gramatica.y"
{tipo = val_peek(0).sval;
	  		verificarDeclaracionClase(val_peek(0).sval);}
break;
case 34:
//#line 78 "compi/gramatica.y"
{variables.add(val_peek(0).sval);
					 variablesPorUso.add(val_peek(0).sval);}
break;
case 35:
//#line 80 "compi/gramatica.y"
{variables.add(val_peek(2).sval);
								  variablesPorUso.add(val_peek(2).sval);}
break;
case 36:
//#line 84 "compi/gramatica.y"
{listaHerencia.add(val_peek(0).sval);}
break;
case 37:
//#line 85 "compi/gramatica.y"
{listaHerencia.add(val_peek(2).sval);}
break;
case 45:
//#line 97 "compi/gramatica.y"
{yyerror("Error de sentencia");}
break;
case 46:
//#line 100 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if ");}
break;
case 47:
//#line 101 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else ");}
break;
case 48:
//#line 102 "compi/gramatica.y"
{yyerror("Error en la definicion del if");}
break;
case 49:
//#line 103 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta )");}
break;
case 50:
//#line 104 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta (");}
break;
case 51:
//#line 105 "compi/gramatica.y"
{yyerror("Error en la definicion del if");}
break;
case 52:
//#line 106 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta )");}
break;
case 53:
//#line 107 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta (");}
break;
case 54:
//#line 110 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for ");}
break;
case 55:
//#line 111 "compi/gramatica.y"
{yyerror("Error en la definicion del for");}
break;
case 56:
//#line 112 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta (");}
break;
case 57:
//#line 113 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta )");}
break;
case 58:
//#line 116 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto. ");
									   verificarDeclaracionVariable(val_peek(5).sval);
									   verificarDeclaracionMetodo(val_peek(5).sval,val_peek(3).sval);}
break;
case 59:
//#line 119 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo");}
break;
case 60:
//#line 120 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo - metodo vacio");}
break;
case 61:
//#line 123 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print ");}
break;
case 62:
//#line 124 "compi/gramatica.y"
{yyerror("Error en la impresion");}
break;
case 63:
//#line 127 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable. ");}
break;
case 64:
//#line 128 "compi/gramatica.y"
{yyerror("Error en la asignacion lado derecho");}
break;
case 65:
//#line 129 "compi/gramatica.y"
{yyerror("Error en la asignacion lado izquierdo");}
break;
case 83:
//#line 158 "compi/gramatica.y"
{verificarDeclaracionVariable(val_peek(0).sval);}
break;
case 84:
//#line 160 "compi/gramatica.y"
{verificarDeclaracionVariable(val_peek(2).sval);
					verificarDeclaracionAtributo(val_peek(2).sval,val_peek(0).sval);}
break;
case 85:
//#line 164 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
break;
case 86:
//#line 166 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);}
break;
//#line 960 "Parser.java"
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
