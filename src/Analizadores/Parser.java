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
   12,    9,    9,    9,   14,   13,   13,   13,   13,   13,
   13,    5,   15,   15,   15,   16,   16,   10,   10,    2,
    2,   17,   17,   17,   17,   17,   17,   20,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   24,   27,   25,   29,   30,   21,   21,   21,   21,
   22,   22,   22,   19,   19,   18,   18,   18,   33,   23,
   34,   23,   35,   23,   36,   23,   37,   23,   38,   23,
   26,   26,   32,   32,   32,   39,   39,   39,   28,   28,
   28,   31,   31,   40,   40,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    0,    3,    0,    3,    0,
    6,    0,    8,    7,    5,    5,    7,    7,    7,    1,
    0,    3,    1,    2,    0,    8,    7,    8,    7,    7,
    7,    3,    1,    1,    1,    1,    3,    1,    3,    1,
    2,    1,    1,    1,    1,    1,    2,    7,    9,    7,
    6,    6,    9,    8,    8,    7,    8,    8,    8,   10,
   10,    1,    0,    2,    0,    0,   11,    6,    5,    5,
    6,    5,    6,    5,    3,    4,    4,    4,    0,    4,
    0,    4,    0,    4,    0,    4,    0,    4,    0,    4,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
   35,   33,    0,   34,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   42,   43,
   44,   45,   46,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   47,    0,    0,    0,    0,    0,    0,    0,
    2,   41,    0,    0,    0,    0,    0,    0,    0,    9,
    7,    0,   32,    0,  104,  101,    0,   98,   99,    0,
    0,  100,    0,    0,    0,    0,    0,    0,    0,   75,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    3,   37,
    0,  105,   78,    0,    0,    0,    0,    0,    0,    0,
    0,   91,    0,   62,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,   77,
   76,    0,    0,    0,   15,   24,    0,    0,   16,    0,
    0,    0,    0,    0,  103,    0,    0,   96,   97,    0,
   72,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   74,   69,    0,
   70,    0,    0,    0,    0,   22,   39,    0,    0,    0,
    0,    0,   11,   73,   71,   92,    0,   52,    0,    0,
    0,   51,    0,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,   68,    0,    0,    0,    0,    0,   14,
   19,   18,   17,    0,    0,    0,   50,    0,   63,    0,
   63,    0,   56,    0,    0,   48,   66,    0,    0,    0,
    0,    0,    0,   13,   55,    0,   54,    0,   58,    0,
   59,    0,   57,   64,    0,    0,    0,    0,    0,    0,
    0,   53,    0,    0,   49,    0,   27,   29,   30,    0,
   31,    0,   60,   61,    0,   28,   26,   67,
};
final static short yydgoto[] = {                          6,
    7,   17,    8,    9,   78,   29,   28,   48,   79,   82,
  133,  124,   80,  223,   11,   31,  102,   19,   20,   21,
   22,   23,   68,  103,  214,  104,  215,   58,  162,  235,
   24,   69,  109,  110,  111,  112,  113,  114,   61,   62,
};
final static short yysindex[] = {                      -143,
    0,    0, -106,    0, -152,    0,    0, -254,    0,    0,
 -237,  -55,   10,  -26,  -24,  -19, -201, -106,    0,    0,
    0,    0,    0, -206, -227, -229, -106,  -79,  -79,   30,
   18,    4,    0, -144,   51,  -23,   49, -181,   99, -130,
    0,    0,  -44, -172, -114, -118, -126, -104, -119,    0,
    0, -237,    0,  135,    0,    0,  -62,    0,    0,   37,
   48,    0,  148,  -10, -115,  -36,    4,  156,   79,    0,
  157, -115,  -12,    4,  140,   38,  -94,    0,  -65, -172,
  158,  -63,  158,  -61,  -58,  -57,  -47, -172,    0,    0,
  -46,    0,    0,    4,    4,    4,    4,  162,  151,  183,
 -106,    0,  -90,    0, -115,  -81,  188,   25,  -17,  -25,
  191,  194,  -18,  -16,  199,  200, -115,  204,    0,    0,
    0,  224,   -5, -172,    0,    0, -114, -172,    0, -172,
 -172, -172,    6,   15,    0,   48,   48,    0,    0,  226,
    0,  227,   23, -115,  231,  -78, -115,  232, -115, -115,
 -188,    4,    4,    4,    4,    4,    4,    0,    0,  237,
    0,  239,  242,  262,  -38,    0,    0,   40,   41,   42,
   44, -172,    0,    0,    0,    0,   52,    0, -115,  251,
   56,    0,  -75,  -70,  252,    0,  253,   79,   79,   79,
   79,   79,   79,    0,    4,   54,   55,  -39,   64,    0,
    0,    0,    0,   57,  264,   60,    0,  265,    0,  266,
    0,  267,    0,  -53, -115,    0,    0, -106, -106, -106,
   63, -106,   65,    0,    0,  269,    0,   69,    0,   70,
    0,  273,    0,    0,  292,   71,   72,   73, -106,   76,
 -106,    0,  275,  276,    0, -115,    0,    0,    0,   77,
    0,   78,    0,    0,  281,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -99,  -91,
    0,    0,   67,    0,    0,    0,    0,   80,    0,    0,
    0,    0,    0,    0,    0,   83,    0,    0,    0,  289,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -34,    0,    0,    0,    0,    0,    0,
   -7,    0,    0,   74,    0,    0,    0,    0,   26,    0,
    0,    0,    0,    0,    0,    0,    0, -156,    0,   84,
 -205,    0, -203,    0,    0,    0,   86,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -2,    3,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  311,  312,  313,
  314,  315,  316,    0,    0,    0,    0,    0,   94,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   75,  164,    0,   50,    0,    0,    0,  -13,  -28,
    0,    0,    0,    0,    0,  307,   -3,  320,    0,    0,
    0,    0,  294,  -21,  -54,  -41,    0,  -27,    0,    0,
  272,  -20,    0,    0,    0,    0,    0,    0,  100,    0,
};
final static int YYTABLESIZE=467;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   57,  221,  199,   33,  105,  233,  102,  102,  102,   27,
  102,   60,  102,   36,   18,   38,   67,   85,   87,   30,
   40,   57,   76,   18,  102,  102,   46,  102,  117,  100,
  116,  118,   84,   95,  165,   95,   44,   95,   93,   47,
   93,   45,   93,   94,  106,   94,  119,   94,   57,   10,
   38,   95,   95,   35,   95,   34,   93,   93,   38,   93,
   38,   94,   94,   41,   94,  150,  126,  185,  138,  139,
   43,  186,  187,   52,  134,  160,   53,   10,   10,   94,
   94,   95,   95,  146,    1,   85,  151,   83,   71,   96,
    2,   65,   42,    4,   97,   93,  121,   18,  167,   77,
   21,   49,  177,   25,   26,  181,   21,   70,   20,   21,
  166,   63,   64,    1,  168,   21,  169,  170,  171,    2,
    3,   94,    4,   95,    5,   73,   54,  183,  184,   86,
   81,  188,  189,  190,  191,  192,  193,  206,   83,   72,
   12,   13,   81,   14,    2,   89,   15,    4,  101,   12,
   13,   16,   14,   77,  228,   15,  230,    8,  204,   88,
   16,  122,  123,    8,    4,    6,    8,  217,    8,  144,
  145,    6,    5,  234,    6,  143,    6,    1,  147,  148,
   91,  179,  180,    2,  209,  210,    4,   98,    5,  211,
  212,   50,   51,  136,  137,   92,  108,  115,  120,  125,
  128,  127,  140,  129,  255,  130,  131,  232,  132,  141,
  135,   75,   54,   55,   18,   18,   18,  198,   18,   12,
   13,   32,   14,  142,  220,   15,   56,  101,  149,   35,
   16,   37,   66,   54,   55,   18,   39,   18,  102,  102,
  102,  102,  102,   12,   13,   99,   14,   56,  153,   15,
  164,  101,  154,  155,   16,  152,  156,  158,  159,  157,
   54,   55,  161,  163,   32,   95,   95,   95,   95,  172,
   93,   93,   93,   93,   56,   94,   94,   94,   94,  173,
   12,   13,  196,   14,  174,  175,   15,  176,  101,  178,
  182,   16,  236,  237,  238,  194,  240,  195,   79,   81,
   87,   89,  197,   59,  200,  201,  202,   59,  203,  207,
  213,  216,  205,  250,   59,  252,  208,  218,  219,  222,
  226,  224,  225,  227,  229,  231,  239,  242,  241,  243,
  244,  245,  246,  253,  254,  247,  248,  249,   59,  258,
  251,  256,  257,  102,   40,   59,   10,   36,   23,   12,
  103,   80,   82,   84,   86,   88,   90,   25,   90,   74,
  107,    0,    0,    0,    0,   59,   59,   59,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   59,   59,   59,   59,   59,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   59,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
   45,   41,   41,   59,   41,   59,   41,   42,   43,  264,
   45,   32,   47,   40,   18,   40,   40,   46,   47,  257,
   40,   45,   43,   27,   59,   60,  256,   62,   41,   40,
   72,   73,   46,   41,   40,   43,  264,   45,   41,  269,
   43,  269,   45,   41,   66,   43,   74,   45,   45,    0,
  256,   59,   60,  257,   62,   46,   59,   60,  264,   62,
  264,   59,   60,  265,   62,   41,   80,  256,   96,   97,
  277,  260,  261,   44,   88,  117,   59,   28,   29,   43,
   43,   45,   45,  105,  257,   60,  108,   62,  270,   42,
  263,   41,   18,  266,   47,   59,   59,  101,  127,  272,
  257,   27,  144,  256,  257,  147,  263,   59,  265,  266,
  124,  256,  257,  257,  128,  272,  130,  131,  132,  263,
  264,   43,  266,   45,  268,  256,  257,  149,  150,  256,
  257,  152,  153,  154,  155,  156,  157,  179,  257,   41,
  256,  257,  257,  259,  263,  265,  262,  266,  264,  256,
  257,  267,  259,  272,  209,  262,  211,  257,  172,  264,
  267,  256,  257,  263,  264,  257,  266,  195,  268,  260,
  261,  263,  264,  215,  266,  101,  268,  257,  260,  261,
   46,  260,  261,  263,  260,  261,  266,   40,  268,  260,
  261,   28,   29,   94,   95,  258,   41,   41,   59,  265,
  264,   44,   41,  265,  246,  264,  264,  261,  256,   59,
  257,  256,  257,  258,  218,  219,  220,  256,  222,  256,
  257,  277,  259,   41,  264,  262,  271,  264,   41,  256,
  267,  256,  256,  257,  258,  239,  256,  241,  273,  274,
  275,  276,  277,  256,  257,  256,  259,  271,  274,  262,
  256,  264,   62,   60,  267,  273,  275,   59,   59,  276,
  257,  258,   59,   40,  277,  273,  274,  275,  276,  264,
  273,  274,  275,  276,  271,  273,  274,  275,  276,  265,
  256,  257,   41,  259,   59,   59,  262,  265,  264,   59,
   59,  267,  218,  219,  220,   59,  222,   59,  273,  274,
  275,  276,   41,   32,  265,  265,  265,   36,  265,   59,
   59,   59,  261,  239,   43,  241,  261,  264,  264,  256,
  261,  265,   59,   59,   59,   59,  264,   59,  264,  261,
  261,   59,   41,   59,   59,  265,  265,  265,   67,   59,
  265,  265,  265,  277,  265,   74,  264,   59,  265,  264,
  277,   41,   41,   41,   41,   41,   41,  264,   52,   40,
   67,   -1,   -1,   -1,   -1,   94,   95,   96,   97,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  152,  153,  154,  155,  156,  157,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  195,
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
"$$5 :",
"declaracion_sentencia_clase : declaracion_sentencia $$5 declaracion_sentencia_clase",
"declaracion_sentencia_clase : declaracion_metodo",
"declaracion_sentencia_clase : declaracion_metodo declaracion_sentencia_clase",
"$$6 :",
"declaracion_metodo : VOID ID '(' ')' $$6 BEGIN sentencias_ejecutables END",
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
"$$7 :",
"bloqueElse : $$7 bloque",
"$$8 :",
"$$9 :",
"iteracion : FOR '(' asignacion factor $$8 ';' factor $$9 ')' bloque ';'",
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
"$$10 :",
"condicion : expresion $$10 MAYOR_IGUAL expresion",
"$$11 :",
"condicion : expresion $$11 MENOR_IGUAL expresion",
"$$12 :",
"condicion : expresion $$12 '>' expresion",
"$$13 :",
"condicion : expresion $$13 '<' expresion",
"$$14 :",
"condicion : expresion $$14 IGUAL expresion",
"$$15 :",
"condicion : expresion $$15 DISTINTO expresion",
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

//#line 204 "compi/gramatica.y"
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
	

				if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){		
					PrintWriter writer1 = new PrintWriter(archivo+"\\program.txt");
					writer1.print("");
					FileWriter escribir1 = new FileWriter(archivo+"\\program.txt", true);
			
					for(String s : Terceto.codigo) {
						escribir1.write(s);
						escribir1.write(sb.toString());
					}
					escribir1.close();
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
		Terceto.crearCodigo(par.tercetos);
		par.saveFile();
		int i =0;
		for(Terceto s : par.tercetos) {
			System.out.println(i++ + s.toString());
		}		
	}
//#line 822 "Parser.java"
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
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");
								crearTerceto("OVERFLOW_FLOAT",null,null,null);
								crearTerceto("OVERFLOW_INT",null,null,null);
								crearTerceto("DIVISIONCERO",null,null,null);
								crearTerceto("END_PROGRAMA",null,null,null);}
break;
case 2:
//#line 24 "compi/gramatica.y"
{crearTerceto("LABEL","INICIO",null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones. ");}
break;
case 3:
//#line 25 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones. ");}
break;
case 4:
//#line 28 "compi/gramatica.y"
{agregarUsoVariables("nombreAtributo");}
break;
case 5:
//#line 29 "compi/gramatica.y"
{agregarUsoVariables("variable");}
break;
case 6:
//#line 30 "compi/gramatica.y"
{agregarUsoVariables("variable");}
break;
case 8:
//#line 31 "compi/gramatica.y"
{agregarUsoVariables("nombreAtributo");}
break;
case 10:
//#line 34 "compi/gramatica.y"
{inicializarAtributos(val_peek(0).sval);}
break;
case 11:
//#line 34 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase. ");
																   verificarRedeclaracionClase(val_peek(4).sval);
																   aLexico.agregarAtributoLexema(val_peek(4).sval,"Uso","nombreClase");
																   agregarAtributoAClase(val_peek(4).sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
break;
case 12:
//#line 40 "compi/gramatica.y"
{inicializarAtributos(val_peek(2).sval); agregarAtributoHeredados(val_peek(2).sval);}
break;
case 13:
//#line 40 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple. ");
				  												   verificarRedeclaracionClase(val_peek(6).sval);
																   aLexico.agregarAtributoLexema(val_peek(6).sval,"Uso","nombreClase");
																   agregarAtributoAClase(val_peek(6).sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
break;
case 14:
//#line 46 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 15:
//#line 47 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase");}
break;
case 16:
//#line 48 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 17:
//#line 49 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta begin de clase");}
break;
case 18:
//#line 50 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta nombre de clase extendidas");}
break;
case 19:
//#line 51 "compi/gramatica.y"
{yyerror("Error en la definicion de la clase: falta palabra reservada extends");}
break;
case 20:
//#line 56 "compi/gramatica.y"
{if(!tipo.equals("int") && !tipo.equals("float")) yyerrorSemantico("Los atributos de la clase deben ser de tipos primitivos.");}
break;
case 21:
//#line 57 "compi/gramatica.y"
{if(!tipo.equals("int") && !tipo.equals("float")) yyerrorSemantico("Los atributos de la clase deben ser de tipos primitivos.");}
break;
case 25:
//#line 62 "compi/gramatica.y"
{crearTerceto("METODO",val_peek(2).sval,null,null);}
break;
case 26:
//#line 62 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo. ");
																	 aLexico.agregarAtributoLexema(val_peek(6).sval,"Uso","nombreMetodo");
																	 metodosPorClase.add(val_peek(6).sval);
																	 crearTerceto("TERMINA_METODO",null,null,null);
																	 }
break;
case 27:
//#line 67 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta nombre de metodo");}
break;
case 28:
//#line 68 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: contiene parametros");}
break;
case 29:
//#line 69 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis (");}
break;
case 30:
//#line 70 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta parentesis )");}
break;
case 31:
//#line 71 "compi/gramatica.y"
{yyerror("Error en la definicion del metodo: falta begin");}
break;
case 32:
//#line 74 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa ");
												agregarTipoVariables(tipo);

												}
break;
case 33:
//#line 80 "compi/gramatica.y"
{tipo = "int";}
break;
case 34:
//#line 81 "compi/gramatica.y"
{tipo = "float";}
break;
case 35:
//#line 82 "compi/gramatica.y"
{tipo = val_peek(0).sval;
	  		verificarDeclaracionClase(val_peek(0).sval);}
break;
case 36:
//#line 86 "compi/gramatica.y"
{variables.add(val_peek(0).sval);
					 variablesPorUso.add(val_peek(0).sval);}
break;
case 37:
//#line 88 "compi/gramatica.y"
{variables.add(val_peek(2).sval);
								  variablesPorUso.add(val_peek(2).sval);}
break;
case 38:
//#line 92 "compi/gramatica.y"
{listaHerencia.add(val_peek(0).sval);}
break;
case 39:
//#line 93 "compi/gramatica.y"
{listaHerencia.add(val_peek(2).sval);}
break;
case 47:
//#line 105 "compi/gramatica.y"
{yyerror("Error de sentencia");}
break;
case 48:
//#line 108 "compi/gramatica.y"
{desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if ");}
break;
case 49:
//#line 109 "compi/gramatica.y"
{desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else ");}
break;
case 50:
//#line 110 "compi/gramatica.y"
{yyerror("Error en la condicion del if");}
break;
case 51:
//#line 111 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta )");}
break;
case 52:
//#line 112 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta (");}
break;
case 53:
//#line 113 "compi/gramatica.y"
{yyerror("Error en la condicion del if");}
break;
case 54:
//#line 114 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta )");}
break;
case 55:
//#line 115 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta (");}
break;
case 56:
//#line 116 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta end_if");}
break;
case 57:
//#line 117 "compi/gramatica.y"
{yyerror("Error en la definicion del if: falta end_if");}
break;
case 58:
//#line 118 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado izquierdo");}
break;
case 59:
//#line 119 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado derecho");}
break;
case 60:
//#line 120 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado izquierdo");}
break;
case 61:
//#line 121 "compi/gramatica.y"
{yyerror("Error en la definicion del if: hay un parentesis de mas del lado derecho");}
break;
case 63:
//#line 126 "compi/gramatica.y"
{desapilarSalto(tercetos.size() + 1); apilarSalto(crearTerceto("BI",null,null,null)); crearTerceto("LABEL","Label"+tercetos.size(),null,null);}
break;
case 65:
//#line 127 "compi/gramatica.y"
{crearTerceto("LABEL","Label"+tercetos.size(),null,null); insertarNoTerminal("condicion",crearTercetoTipo("CMP",tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"),"boolean")); apilarSalto(crearTerceto("JGE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 66:
//#line 128 "compi/gramatica.y"
{apilarExpresionFor(tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"));}
break;
case 67:
//#line 129 "compi/gramatica.y"
{desapilarExpresionFor(); crearTerceto("BI",null,new String("[" + (pilaSaltos.get(pilaSaltos.size()-1)-2) + "]"),null); desapilarSalto(tercetos.size()); crearTerceto("Label","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for ");}
break;
case 68:
//#line 130 "compi/gramatica.y"
{yyerror("Error en la definicion del for");}
break;
case 69:
//#line 131 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta (");}
break;
case 70:
//#line 132 "compi/gramatica.y"
{yyerror("Error en la definicion del for falta )");}
break;
case 71:
//#line 135 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto. ");
									   if(verificarDeclaracionVariable(val_peek(5).sval)){
									   	verificarDeclaracionMetodo(val_peek(5).sval,val_peek(3).sval);
									   	crearTercetoCopiaValor(val_peek(5).sval);
									   	crearTerceto("CALL",val_peek(3).sval,val_peek(5).sval,null);
									   	crearTercetoCopiaResultado(val_peek(5).sval);
									   }
									   }
break;
case 72:
//#line 143 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo");}
break;
case 73:
//#line 144 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo - metodo vacio");}
break;
case 74:
//#line 147 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print ");
									crearTerceto("PRINT",val_peek(2).sval,null,null);}
break;
case 75:
//#line 149 "compi/gramatica.y"
{yyerror("Error en la impresion");}
break;
case 76:
//#line 152 "compi/gramatica.y"
{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable. ");
											  insertarNoTerminal("asignacion",crearTercetoTipo(":=",val_peek(3).sval,noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("expresion"))));}
break;
case 77:
//#line 154 "compi/gramatica.y"
{yyerror("Error en la asignacion lado derecho");}
break;
case 78:
//#line 155 "compi/gramatica.y"
{yyerror("Error en la asignacion lado izquierdo");}
break;
case 79:
//#line 158 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 80:
//#line 158 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
					  apilarSalto(crearTerceto("JL",noTerminalTercetos.get("condicion"),null,null));}
break;
case 81:
//#line 160 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 82:
//#line 160 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("JG",noTerminalTercetos.get("condicion"),null,null));}
break;
case 83:
//#line 162 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 84:
//#line 162 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		  			  apilarSalto(crearTerceto("JLE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 85:
//#line 164 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 86:
//#line 164 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("JGE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 87:
//#line 166 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 88:
//#line 166 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("JNE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 89:
//#line 168 "compi/gramatica.y"
{insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));}
break;
case 90:
//#line 168 "compi/gramatica.y"
{insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),"boolean"));
		 			  apilarSalto(crearTerceto("JE",noTerminalTercetos.get("condicion"),null,null));}
break;
case 93:
//#line 176 "compi/gramatica.y"
{insertarNoTerminal("expresion",crearTercetoTipo("+",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 94:
//#line 177 "compi/gramatica.y"
{insertarNoTerminal("expresion",crearTercetoTipo("-",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 95:
//#line 178 "compi/gramatica.y"
{insertarNoTerminal("expresion",noTerminalTercetos.get("termino"));}
break;
case 96:
//#line 180 "compi/gramatica.y"
{insertarNoTerminal("termino",crearTercetoTipo("*",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 97:
//#line 181 "compi/gramatica.y"
{insertarNoTerminal("termino",crearTercetoTipo("/",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
break;
case 98:
//#line 182 "compi/gramatica.y"
{insertarNoTerminal("termino",noTerminalTercetos.get("factor"));}
break;
case 99:
//#line 185 "compi/gramatica.y"
{insertarNoTerminal("factor",val_peek(0).sval);}
break;
case 100:
//#line 186 "compi/gramatica.y"
{insertarNoTerminal("factor",val_peek(0).sval);}
break;
case 102:
//#line 190 "compi/gramatica.y"
{verificarDeclaracionVariable(val_peek(0).sval);}
break;
case 103:
//#line 192 "compi/gramatica.y"
{if(verificarDeclaracionVariable(val_peek(2).sval))
					verificarDeclaracionAtributo(val_peek(2).sval,val_peek(0).sval);
					yyval = new ParserVal(val_peek(2).sval + "@" + val_peek(0).sval);}
break;
case 104:
//#line 197 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}else{
			}}
break;
case 105:
//#line 200 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);
				yyval = new ParserVal("-" + val_peek(0).sval);}
break;
//#line 1367 "Parser.java"
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
