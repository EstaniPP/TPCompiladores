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
	import java.util.HashMap;
//#line 22 "Parser.java"




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
    0,    1,    1,    3,    3,    3,    3,    4,    4,    4,
    4,    4,    4,    4,    4,    6,    6,    6,    6,    8,
    8,    8,    8,    8,    8,    5,    9,    9,    9,    7,
    7,    2,    2,   10,   10,   10,   10,   10,   10,   13,
   13,   13,   13,   13,   14,   14,   14,   14,   15,   15,
   15,   12,   12,   11,   11,   11,   16,   16,   16,   16,
   16,   16,   17,   17,   18,   18,   18,   20,   20,   20,
   21,   21,   21,   19,   19,   22,   22,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    2,    2,    5,    7,    7,
    5,    5,    7,    7,    7,    1,    2,    1,    2,    7,
    7,    8,    7,    7,    7,    3,    1,    1,    1,    1,
    3,    1,    2,    1,    1,    1,    1,    1,    2,    6,
    8,    6,    5,    5,    9,    6,    5,    5,    6,    5,
    6,    5,    3,    4,    4,    4,    3,    3,    3,    3,
    3,    3,    1,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
   29,   27,    0,   28,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   34,   35,
   36,   37,   38,    0,    0,    0,    0,    7,    6,    0,
    0,    0,   39,    0,    0,    0,    0,    0,    0,    0,
    2,   33,    0,    0,    0,    0,    0,    0,    0,    0,
   26,    0,   76,   73,    0,    0,   71,    0,   70,   72,
    0,    0,    0,    0,    0,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    3,   31,    0,   77,   56,    0,
    0,    0,    0,    0,    0,    0,    0,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   55,   54,    0,    0,   17,   11,   19,
    0,   12,    0,    8,    0,    0,    0,   75,    0,    0,
   68,   69,    0,   50,    0,    0,   44,    0,   43,    0,
    0,    0,    0,    0,    0,    0,   52,   47,    0,   48,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
   49,   64,   42,    0,   40,   46,    0,    0,    0,    0,
    0,   10,   15,   14,   13,    9,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,   45,   21,   23,   24,    0,   25,   20,   22,
};
final static short yydgoto[] = {                          6,
    7,   17,    8,    9,   75,   76,   31,   77,   11,   18,
   19,   20,   21,   22,   23,   65,   99,   66,   24,   58,
   59,   60,
};
final static short yysindex[] = {                      -162,
    0,    0, -121,    0,  -71,    0,    0, -241, -109, -109,
 -198,  -52,   26,  -29,  -26,   -7, -180, -121,    0,    0,
    0,    0,    0, -183, -111, -151, -121,    0,    0,   59,
   57,  -20,    0,  -69,   78,   19,   68, -131,  101,  -67,
    0,    0,   22, -175, -198, -123, -175,  -65, -103, -198,
    0,  110,    0,    0,  -90,   33,    0,  136,    0,    0,
  130,    6, -112,    7,  138,  -15,    0,  140, -112,  -38,
  -20,  151,   34,  -63, -175,  -70, -175,  -51,   59,  -66,
  -49,  -48,  -44, -201,    0,    0,  -35,    0,    0,  -20,
  -20,  -20,  -20,  175,  164,  203, -121,    0,  -11, -112,
   -5, -112,  -20,  -20,  -20,  -20,  -20,  -20,  198,  208,
 -112,  209,  211,    0,    0,  232,    9,    0,    0,    0,
 -175,    0, -175,    0, -175, -175, -175,    0,  136,  136,
    0,    0,  214,    0,  222,   20,    0,   23,    0,  -58,
   38,   38,   38,   38,   38,   38,    0,    0,  223,    0,
  -20,  242,  245,  -25,   24,   27,   29,   30,   31,    0,
    0,    0,    0, -112,    0,    0,  139,   35,   36,  -36,
 -144,    0,    0,    0,    0,    0,   37, -112, -121, -121,
 -121,   39, -121, -121,    0,  228,   32,   41,   42, -121,
   43,   44,    0,    0,    0,    0,   45,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   47,   48,
    0,    0,   14,    0,    0,    0,    0,   49,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -50,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -41,    0,    0,    0,    0,    0,  -33,    0,    0,
    0,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   50,    0,   51,    0,  -93,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -28,  -21,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -1,   10,   11,   12,   15,   21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   17,  195,    0,   90,   40,  127,    0,    0,  -27,
  248,    0,    0,    0,    0,  230,   -4,   25,   18,  118,
  119,    0,
};
final static int YYTABLESIZE=316;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         74,
   74,   74,  111,   74,  182,   74,   33,   67,   30,   67,
   36,   67,   65,   38,   65,  171,   65,   74,   74,   66,
   74,   66,   27,   66,   55,   67,   67,   90,   67,   91,
   65,   65,   40,   65,   42,   98,   98,   66,   66,   57,
   66,   98,   98,   49,  108,   96,  107,  100,  154,   57,
   58,   61,   62,   57,  126,   59,   56,   57,   30,  101,
   57,   60,  127,   55,  110,  112,   55,   73,   58,   61,
   62,   34,   98,   59,   98,   90,   90,   91,   91,   60,
   90,    1,   91,   98,   41,   80,   82,    2,   57,   10,
    4,   89,  115,   43,    1,  138,   74,  140,   10,   10,
    2,    3,   50,    4,   46,    5,  149,   57,   57,   57,
   57,  183,   47,  136,  118,   51,  120,   48,   63,  184,
   57,   57,   57,   57,   57,   57,   67,  141,  142,  143,
  144,  145,  146,   79,   12,   13,   98,   14,   68,    2,
   15,   69,    4,   12,   13,   16,   14,    1,   74,   15,
   98,   97,   44,    2,   16,   87,    4,   45,    5,  177,
  155,   85,  156,   29,  157,  158,  159,   88,   57,   94,
   30,   78,   81,  186,   84,  167,   86,   92,  102,  178,
  109,   90,   93,   91,   25,   26,   61,   62,   70,   52,
   83,   30,  116,  117,  119,  187,  188,  189,  122,  191,
  192,  164,  165,   28,   29,   30,  197,  129,  130,  114,
  131,  132,  121,   30,  123,  133,  124,   12,   13,  125,
   14,  128,  134,   15,   32,   97,   35,  181,   16,   37,
  170,   74,   74,   74,   74,   74,   52,   53,   32,   67,
   67,   67,   67,  135,   65,   65,   65,   65,   39,  137,
   54,   66,   66,   66,   66,  139,  147,  103,  104,  105,
  106,   95,   12,   13,  153,   14,  148,  150,   15,  151,
   97,  152,  160,   16,   64,   52,   53,   72,   52,   53,
  161,  166,  168,  163,  162,  169,  193,   71,  172,   54,
   74,  173,   54,  174,  175,  176,  194,  185,  179,  180,
  113,    0,  190,    0,   75,  195,  196,  198,  199,  200,
    4,    5,    0,   32,   16,   18,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   59,   41,   59,   43,
   40,   45,   41,   40,   43,   41,   45,   59,   60,   41,
   62,   43,  264,   45,   45,   59,   60,   43,   62,   45,
   59,   60,   40,   62,   18,   63,   64,   59,   60,   41,
   62,   69,   70,   27,   60,   40,   62,   41,   40,   32,
   41,   41,   41,   36,  256,   41,   32,   59,  257,   64,
   43,   41,  264,   45,   69,   70,   45,   43,   59,   59,
   59,   46,  100,   59,  102,   43,   43,   45,   45,   59,
   43,  257,   45,  111,  265,   46,   47,  263,   71,    0,
  266,   59,   59,  277,  257,  100,  272,  102,    9,   10,
  263,  264,   44,  266,  256,  268,  111,   90,   91,   92,
   93,  256,  264,   97,   75,   59,   77,  269,   41,  264,
  103,  104,  105,  106,  107,  108,   59,  103,  104,  105,
  106,  107,  108,  257,  256,  257,  164,  259,  270,  263,
  262,   41,  266,  256,  257,  267,  259,  257,  272,  262,
  178,  264,  264,  263,  267,   46,  266,  269,  268,  164,
  121,  265,  123,  257,  125,  126,  127,  258,  151,   40,
  264,   45,   46,  178,   48,  151,   50,   42,   41,   41,
   41,   43,   47,   45,  256,  257,  256,  257,  256,  257,
  256,  257,  256,  257,  265,  179,  180,  181,  265,  183,
  184,  260,  261,    9,   10,  256,  190,   90,   91,   59,
   92,   93,  264,  264,  264,   41,  265,  256,  257,  264,
  259,  257,   59,  262,  277,  264,  256,  264,  267,  256,
  256,  273,  274,  275,  276,  277,  257,  258,  277,  273,
  274,  275,  276,   41,  273,  274,  275,  276,  256,  261,
  271,  273,  274,  275,  276,  261,   59,  273,  274,  275,
  276,  256,  256,  257,  256,  259,   59,   59,  262,   59,
  264,   40,   59,  267,  256,  257,  258,  256,  257,  258,
   59,   59,   41,  261,  265,   41,   59,   40,  265,  271,
  277,  265,  271,  265,  265,  265,  265,  261,  264,  264,
   71,   -1,  264,   -1,  277,  265,  265,  265,  265,  265,
  264,  264,   -1,  265,  265,  265,
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
"declaraciones : declaracion_sentencia declaraciones",
"declaraciones : declaracion_clase declaraciones",
"declaracion_clase : CLASS ID BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS lista_variables BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS error EXTENDS lista_variables BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS error BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID error declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS lista_variables error declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID EXTENDS error BEGIN declaracion_sentencia_clase END",
"declaracion_clase : CLASS ID error lista_variables BEGIN declaracion_sentencia_clase END",
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
"iteracion : FOR '(' asignacion condicion ';' expresion ')' bloque ';'",
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

//#line 136 "compi/gramatica.y"
	AnalizadorLexico aLexico=new AnalizadorLexico();
	ArrayList<String> errores = new ArrayList<String>();

	int yylex(){
		int token = aLexico.yylex();
		if(token == 257 || token == 258 || token  == 270) yylval = aLexico.getyylval();
		return token;
	}

	void yyerror(String error) {
	errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - "+error);
	}

	public ArrayList<String> getErrores() {
		ArrayList<String> erroresTotales = new ArrayList<String>();
		erroresTotales.add("Errores lexicos:");
		erroresTotales.addAll(aLexico.getErrores());		
		erroresTotales.add("Errores sintactico:");
		erroresTotales.addAll(errores);
		return erroresTotales;
	}

	public static void main(String args[])
	{
		Parser par = new Parser(false);
		System.out.println(par.yyparse());
		System.out.println(par.aLexico.getDatosTablaSimbolos());
		for(String s: par.getErrores()){
			System.out.println(s);
		}
	}
//#line 422 "Parser.java"
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
//#line 11 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");}
break;
case 2:
//#line 14 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones.");}
break;
case 3:
//#line 15 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones.");}
break;
case 8:
//#line 24 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase.");}
break;
case 9:
//#line 25 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple.");}
break;
case 10:
//#line 26 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 11:
//#line 27 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 12:
//#line 28 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 13:
//#line 29 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 14:
//#line 30 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase extendidas");}
break;
case 15:
//#line 31 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta palabra reservada extends");}
break;
case 20:
//#line 42 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo.");}
break;
case 21:
//#line 43 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta nombre de metodo");}
break;
case 22:
//#line 44 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: contiene parametros");}
break;
case 23:
//#line 45 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis (");}
break;
case 24:
//#line 46 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis )");}
break;
case 25:
//#line 47 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta begin");}
break;
case 26:
//#line 50 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa");}
break;
case 39:
//#line 71 "compi/gramatica.y"
{yyerror("ERROR DE SENTENCIA");}
break;
case 40:
//#line 74 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if");}
break;
case 41:
//#line 75 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else");}
break;
case 42:
//#line 76 "compi/gramatica.y"
{yyerror("Error en la definicion del if");}
break;
case 43:
//#line 77 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta )");}
break;
case 44:
//#line 78 "compi/gramatica.y"
{yyerror("Error en la definicion del if falta (");}
break;
case 45:
//#line 81 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for");}
break;
case 46:
//#line 82 "compi/gramatica.y"
{yyerror("Error en la definicion del for");}
break;
case 47:
//#line 83 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta (");}
break;
case 48:
//#line 84 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta )");}
break;
case 49:
//#line 87 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto.");}
break;
case 50:
//#line 88 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo");}
break;
case 51:
//#line 89 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo - metodo vacio");}
break;
case 52:
//#line 92 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print");}
break;
case 53:
//#line 93 "compi/gramatica.y"
{yyerror("Error en la impresion");}
break;
case 54:
//#line 96 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable.");}
break;
case 55:
//#line 97 "compi/gramatica.y"
{yyerror("Error en la asignacion lado derecho");}
break;
case 56:
//#line 98 "compi/gramatica.y"
{yyerror("Error en la asignacion lado izquierdo");}
break;
case 76:
//#line 131 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
break;
case 77:
//#line 133 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);}
break;
//#line 724 "Parser.java"
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
