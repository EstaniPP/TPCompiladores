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
    0,    1,    1,    3,    3,    3,    3,    4,    4,    6,
    6,    6,    6,    8,    5,    9,    9,    9,    7,    7,
    2,    2,   10,   10,   10,   10,   10,   10,   13,   13,
   14,   14,   15,   15,   12,   12,   11,   11,   11,   16,
   16,   16,   16,   16,   16,   17,   17,   18,   18,   18,
   20,   20,   20,   21,   21,   21,   19,   19,   22,   22,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    2,    2,    5,    7,    1,
    2,    1,    2,    7,    3,    1,    1,    1,    1,    3,
    1,    2,    1,    1,    1,    1,    1,    2,    6,    8,
    9,    6,    6,    5,    5,    3,    4,    4,    4,    3,
    3,    3,    3,    3,    3,    1,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
   18,   16,    0,   17,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   23,   24,
   25,   26,   27,    0,    0,    0,    7,    6,    0,    0,
    0,   28,    0,    0,    0,    0,    0,    2,   22,    0,
    0,    0,    0,    0,   15,    0,   59,   56,    0,    0,
   54,    0,   53,   55,    0,    0,    0,   36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    3,   20,
    0,   60,   39,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   38,
   37,    0,   11,    8,   13,    0,   58,    0,    0,   51,
   52,   34,    0,    0,   46,    0,    0,    0,    0,    0,
    0,    0,   35,    0,    0,    0,    0,   33,    0,    0,
   29,   32,    0,    0,    9,    0,    0,    0,    0,   47,
   30,    0,    0,   31,   14,
};
final static short yydgoto[] = {                          6,
    7,   17,    8,    9,   65,   66,   30,   67,   11,   18,
   19,   20,   21,   22,   23,   56,  106,   57,   51,   52,
   53,   54,
};
final static short yysindex[] = {                      -139,
    0,    0, -124,    0, -241,    0,    0, -222, -129, -129,
 -199,  -52,   27,   25,  -26,   45, -172, -124,    0,    0,
    0,    0,    0, -179, -203, -124,    0,    0,   60,   48,
  -34,    0, -142,  -34,   58, -130, -137,    0,    0,  -40,
 -163, -199, -116, -199,    0,  106,    0,    0, -105,   19,
    0,   35,    0,    0,   -7,  113,  -15,    0,  114,  -38,
  -34,   97,   24, -100, -163, -107, -163, -104,    0,    0,
  -98,    0,    0,  -34,  -34,  -34,  -34,  102,  122, -151,
  -34,  -34,  -34,  -34,  -34,  -34,  103, -151,  105,    0,
    0,  125,    0,    0,    0, -163,    0,   35,   35,    0,
    0,    0,  107, -124,    0, -138,   36,   36,   36,   36,
   36,   36,    0,  108,  -34,  127,  -96,    0, -124, -151,
    0,    0,   56,  -94,    0,  -93,  -90, -151, -124,    0,
    0,  115,  -92,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -89,  -88,
    0,    0,  -99,    0,    0,    0,    0,  -86,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,    0,    0,
    0,  -33,    0,    0,  -97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -84,    0,  -83,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -28,  -21,    0,
    0,    0,    0,    0,    0,    0,    9,   11,   12,   13,
   15,   16,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   17,  121,    0,   86,  -16,   70,    0,    0,  -44,
  140,    0,    0,    0,    0,  123,  -65,    6,   22,   73,
   68,    0,
};
final static int YYTABLESIZE=261;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         57,
   57,   57,   88,   57,   49,   57,   32,   50,   19,   50,
   49,   50,   48,   36,   48,   25,   48,   57,   57,   49,
   57,   49,  114,   49,   24,   50,   50,   74,   50,   75,
   48,   48,   79,   48,   39,  105,   50,   49,   49,   24,
   49,   26,   43,  105,   86,   63,   85,   24,   93,   40,
   95,   41,   44,   45,  127,   42,   43,   29,   24,  119,
   41,   74,  132,   75,   34,   42,   74,   40,   75,   41,
   44,   45,   33,   42,   43,  105,   76,   73,   74,  117,
   75,   77,   91,  105,   37,   10,  107,  108,  109,  110,
  111,  112,   38,    1,   10,   10,  128,   40,   74,    2,
   75,   24,    4,   44,   12,   13,   45,   14,   64,   24,
   15,   68,  104,   70,   55,   16,   58,    1,   60,   46,
  123,  120,  121,    2,    3,   24,    4,    1,    5,   27,
   28,   12,   13,    2,   14,  126,    4,   15,    5,   59,
   24,   24,   16,  100,  101,  133,   98,   99,   69,   24,
   24,   71,   72,   80,   87,   90,   92,   94,   97,   96,
  102,  113,  103,  115,  116,  118,  122,  124,  125,  129,
  131,  130,  135,  134,    4,    5,   61,   57,   21,   58,
   10,   12,    0,   89,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   19,    0,   62,   46,   47,    0,    0,
    0,    0,   46,   47,   31,    0,    0,    0,    0,   35,
   48,   57,   57,   57,   57,   57,   48,    0,   31,   50,
   50,   50,   50,    0,   48,   48,   48,   48,   78,    0,
    0,   49,   49,   49,   49,    0,    0,   81,   82,   83,
   84,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   45,   47,   59,   41,   59,   43,
   45,   45,   41,   40,   43,  257,   45,   59,   60,   41,
   62,   43,   88,   45,    3,   59,   60,   43,   62,   45,
   59,   60,   40,   62,   18,   80,   31,   59,   60,   18,
   62,  264,   26,   88,   60,   40,   62,   26,   65,   41,
   67,   41,   41,   41,  120,   41,   41,  257,   37,  104,
  264,   43,  128,   45,   40,  269,   43,   59,   45,   59,
   59,   59,   46,   59,   59,  120,   42,   59,   43,   96,
   45,   47,   59,  128,   40,    0,   81,   82,   83,   84,
   85,   86,  265,  257,    9,   10,   41,  277,   43,  263,
   45,   80,  266,   44,  256,  257,   59,  259,  272,   88,
  262,   42,  264,   44,  257,  267,   59,  257,  256,  257,
  115,  260,  261,  263,  264,  104,  266,  257,  268,    9,
   10,  256,  257,  263,  259,  119,  266,  262,  268,  270,
  119,  120,  267,   76,   77,  129,   74,   75,  265,  128,
  129,   46,  258,   41,   41,   59,  257,  265,  257,  264,
   59,   59,   41,   59,   40,   59,   59,   41,  265,  264,
  261,  265,  265,   59,  264,  264,   37,  277,  265,  277,
  265,  265,   -1,   61,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  264,   -1,  256,  257,  258,   -1,   -1,
   -1,   -1,  257,  258,  277,   -1,   -1,   -1,   -1,  256,
  271,  273,  274,  275,  276,  277,  271,   -1,  277,  273,
  274,  275,  276,   -1,  273,  274,  275,  276,  256,   -1,
   -1,  273,  274,  275,  276,   -1,   -1,  273,  274,  275,
  276,
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
"declaracion_sentencia_clase : declaracion_sentencia",
"declaracion_sentencia_clase : declaracion_sentencia declaracion_sentencia_clase",
"declaracion_sentencia_clase : declaracion_metodo",
"declaracion_sentencia_clase : declaracion_metodo declaracion_sentencia_clase",
"declaracion_metodo : VOID ID '(' ')' BEGIN sentencias_ejecutables END",
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
"iteracion : FOR '(' asignacion condicion ';' expresion ')' bloque ';'",
"iteracion : FOR '(' error ')' bloque ';'",
"llamadometodo : ID '.' ID '(' ')' ';'",
"llamadometodo : ID '.' ID error ';'",
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
"bloque : BEGIN sentencia_ej sentencias_ejecutables END",
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

//#line 119 "compi/gramatica.y"
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
//#line 373 "Parser.java"
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
case 14:
//#line 35 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo.");}
break;
case 15:
//#line 38 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa");}
break;
case 28:
//#line 59 "compi/gramatica.y"
{yyerror("ERROR DE SENTENCIA");}
break;
case 29:
//#line 62 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if");}
break;
case 30:
//#line 63 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else");}
break;
case 31:
//#line 67 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for");}
break;
case 32:
//#line 68 "compi/gramatica.y"
{yyerror("Error en el for");}
break;
case 33:
//#line 71 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto.");}
break;
case 34:
//#line 72 "compi/gramatica.y"
{yyerror("Error en la invocacion a metodo");}
break;
case 35:
//#line 75 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print");}
break;
case 36:
//#line 76 "compi/gramatica.y"
{yyerror("Error en la impresion");}
break;
case 37:
//#line 79 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable.");}
break;
case 38:
//#line 80 "compi/gramatica.y"
{yyerror("Error en la asignacion lado derecho");}
break;
case 39:
//#line 81 "compi/gramatica.y"
{yyerror("Error en la asignacion lado izquierdo");}
break;
case 59:
//#line 114 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
break;
case 60:
//#line 116 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);}
break;
//#line 607 "Parser.java"
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
