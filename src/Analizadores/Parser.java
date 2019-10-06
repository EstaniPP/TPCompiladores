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
    2,    2,   10,   10,   10,   10,   10,   13,   13,   14,
   15,   12,   11,   16,   16,   16,   16,   16,   16,   17,
   17,   18,   18,   18,   20,   20,   20,   21,   21,   21,
   19,   19,   22,   22,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    2,    2,    5,    7,    1,
    2,    1,    2,    7,    3,    1,    1,    1,    1,    3,
    1,    2,    1,    1,    1,    1,    1,    6,    8,    9,
    6,    5,    4,    3,    3,    3,    3,    3,    3,    1,
    4,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
   18,   16,    0,   17,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   23,   24,   25,
   26,   27,    0,    0,    0,    7,    6,    0,    0,    0,
    0,    0,    0,    2,   22,    0,    0,    0,    0,    0,
   15,    0,   50,    0,   53,    0,    0,    0,   48,    0,
   47,   49,    0,    0,    0,    0,    0,    0,    0,    0,
    3,   20,    0,    0,   54,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   33,    0,
   11,    8,   13,    0,    0,   52,    0,   40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,   46,   32,
    0,    0,    0,   31,    0,    0,   28,    0,    0,    9,
    0,    0,    0,    0,   41,   29,    0,    0,   30,   14,
};
final static short yydgoto[] = {                          6,
    7,   16,    8,    9,   57,   58,   29,   59,   11,   17,
   18,   19,   20,   21,   22,   47,   89,   48,   49,   50,
   51,   52,
};
final static short yysindex[] = {                      -174,
    0,    0, -139,    0, -241,    0,    0, -201, -170, -170,
 -190,   39,   60,   68,   79, -163, -139,    0,    0,    0,
    0,    0, -150, -214, -139,    0,    0,   87,   73, -124,
  -42, -136, -122,    0,    0,  -42, -175, -190, -129, -190,
    0,   97,    0,   92,    0, -119,   99,  -23,    0,   24,
    0,    0,  100,  -42,   13, -115, -175, -121, -175, -118,
    0,    0,  102, -112,    0, -158,  -42,  -42,  -42,  -42,
  -42,  -42,  -42,  -42,  -42,  -42,   88,   89,    0,  109,
    0,    0,    0, -175,   91,    0, -139,    0, -149,   30,
   30,   30,   30,   30,   30,   24,   24,    0,    0,    0,
  -42,  110, -113,    0, -139, -158,    0,   62, -111,    0,
 -110, -107, -158, -139,    0,    0,   98, -109,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0, -106, -105,
    0, -117,    0,    0,    0,    0, -104,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -51,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -114,    0,  -41,    0,    0,    0,    0,    0,  -36,
    0,    0,    0,    0,    0,    0, -103,    0, -101,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   -5,
    1,    2,    3,    6,   11,  -31,  -11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   10,  107,    0,   59,  -44,   46,    0,    0,  -49,
  132,    0,    0,    0,    0,  112,  -60,    9,    8,   52,
   54,    0,
};
final static int YYTABLESIZE=265;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   51,   51,   46,   51,   44,   51,   44,   19,   44,   42,
   23,   42,   81,   42,   83,   24,   88,   51,   51,   73,
   51,   74,   44,   44,   23,   44,   35,   42,   42,   43,
   42,   43,   23,   43,   39,   34,   72,  105,   71,  103,
   23,   35,   38,   39,   55,  112,   36,   43,   43,   37,
   43,   37,  117,   34,   38,   73,   88,   74,   10,   35,
   38,   39,   25,   88,   36,   75,   28,   10,   10,   37,
   76,   79,   73,   23,   74,   90,   91,   92,   93,   94,
   95,    1,    1,   60,   30,   62,    1,    2,    2,    3,
    4,    4,    2,    5,   23,    4,   56,    5,   12,   31,
   13,   34,  113,   14,   73,   87,   74,   32,   15,  108,
  106,  107,   23,   23,  111,   26,   27,   12,   33,   13,
   23,   23,   14,  118,   96,   97,   36,   15,   98,   99,
   40,   41,   42,   53,   44,   61,   63,   64,   65,   66,
   77,   80,   85,   82,   86,   84,  100,  101,  102,  104,
  109,  110,  114,  116,  115,  120,  119,    4,    5,   51,
   21,   10,   52,   12,   54,   78,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   19,   43,   44,   45,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   51,   51,   51,   51,   51,   44,   44,   44,   44,
    0,   42,   42,   42,   42,    0,    0,    0,    0,   67,
   68,   69,   70,    0,    0,    0,    0,    0,    0,    0,
    0,   43,   43,   43,   43,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   45,   45,   41,   47,   43,   59,   45,   41,
    3,   43,   57,   45,   59,  257,   66,   59,   60,   43,
   62,   45,   59,   60,   17,   62,   17,   59,   60,   41,
   62,   43,   25,   45,   25,   41,   60,   87,   62,   84,
   33,   41,   41,   41,   36,  106,   41,   59,   60,  264,
   62,   41,  113,   59,  269,   43,  106,   45,    0,   59,
   59,   59,  264,  113,   59,   42,  257,    9,   10,   59,
   47,   59,   43,   66,   45,   67,   68,   69,   70,   71,
   72,  257,  257,   38,   46,   40,  257,  263,  263,  264,
  266,  266,  263,  268,   87,  266,  272,  268,  257,   40,
  259,  265,   41,  262,   43,  264,   45,   40,  267,  101,
  260,  261,  105,  106,  105,    9,   10,  257,   40,  259,
  113,  114,  262,  114,   73,   74,  277,  267,   75,   76,
   44,   59,  257,  270,  257,  265,   40,   46,  258,   41,
   41,  257,   41,  265,  257,  264,   59,   59,   40,   59,
   41,  265,  264,  261,  265,  265,   59,  264,  264,  277,
  265,  265,  277,  265,   33,   54,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  264,  256,  257,  258,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  273,  274,  275,  276,  277,  273,  274,  275,  276,
   -1,  273,  274,  275,  276,   -1,   -1,   -1,   -1,  273,
  274,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  273,  274,  275,  276,
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
"seleccion : IF '(' condicion ')' bloque END_IF",
"seleccion : IF '(' condicion ')' bloque ELSE bloque END_IF",
"iteracion : FOR '(' asignacion condicion ';' expresion ')' bloque ';'",
"llamadometodo : ID '.' ID '(' ')' ';'",
"impresion : PRINT '(' CADENA ')' ';'",
"asignacion : identificador ASIGN expresion ';'",
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
"factor : error",
"identificador : ID",
"identificador : ID '.' ID",
"cte : CTE",
"cte : '-' CTE",
};

//#line 113 "compi/gramatica.y"
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
//#line 361 "Parser.java"
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
//#line 61 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if");}
break;
case 29:
//#line 62 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else");}
break;
case 30:
//#line 66 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for");}
break;
case 31:
//#line 69 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto.");}
break;
case 32:
//#line 72 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print");}
break;
case 33:
//#line 75 "compi/gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable.");}
break;
case 53:
//#line 108 "compi/gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
break;
case 54:
//#line 110 "compi/gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);}
break;
//#line 571 "Parser.java"
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
