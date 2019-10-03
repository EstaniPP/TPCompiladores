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






//#line 1 "gramatica.y"
	
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
    2,    2,   10,   10,   10,   10,   10,   12,   12,   13,
   13,   13,   13,   13,   13,   15,   15,   11,   11,   14,
   14,   14,   16,   16,   16,   17,   17,   17,   17,   18,
   18,
};
final static short yylen[] = {                            2,
    1,    3,    4,    1,    1,    2,    2,    5,    7,    1,
    2,    1,    2,    7,    3,    1,    1,    1,    1,    3,
    1,    2,    2,    5,    1,   10,    6,    6,    8,    3,
    3,    3,    3,    3,    3,    1,    4,    3,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    3,    1,    1,
    2,
};
final static short yydefred[] = {                         0,
   18,   16,    0,   17,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    7,    6,    0,    0,    0,    0,    0,    0,    0,
    2,   22,   23,    0,    0,    0,    0,   15,   49,    0,
   50,    0,    0,    0,   45,   47,    0,   39,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    3,   20,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   11,    8,
   13,    0,   48,    0,    0,   43,   44,    0,    0,   36,
    0,    0,    0,    0,    0,    0,    0,   24,    0,    0,
    0,   27,    0,    0,   28,    0,    0,    9,    0,    0,
    0,    0,   37,   29,    0,    0,    0,   14,   26,
};
final static short yydgoto[] = {                          6,
    7,   16,    8,    9,   55,   56,   25,   57,   11,   17,
   18,   19,   49,   50,   91,   44,   45,   46,
};
final static short yysindex[] = {                      -184,
    0,    0, -159,    0, -247,    0,    0, -235, -180, -180,
 -203,  -41,   50,   53,   57, -148, -159,   62,    0, -160,
 -159,    0,    0,   78,   64,  -43, -133,  -43, -145, -130,
    0,    0,    0, -185, -203, -137, -203,    0,    0,   83,
    0, -128,   69,   63,    0,    0,  -40,    0,   90,  -42,
   91,  -23,   74, -123, -185, -129, -185, -127,    0,    0,
 -119,    0,  -43,  -43,  -43,  -43,   98, -130, -168,  -43,
  -43,  -43,  -43,  -43,  -43,   82,  -43,  100,    0,    0,
    0, -185,    0,   63,   63,    0,    0,   84, -159,    0,
 -214,   69,   69,   69,   69,   69,   69,    0,   85,  101,
 -120,    0, -159, -168,    0,  -43, -118,    0, -117, -114,
   70, -159,    0,    0, -168, -116,   93,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0, -113, -111,
    0,    0,    0,    0,    0,    0, -115,    0,    0,    0,
    0,    0,    0,  -55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -34,
    0,    0,   64,  -29,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -110,    0, -109,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -24,   -4,    0,    0,    0,    0,    0,
    0,   -9,   -7,    7,    8,   10,   12,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   23,   66,    0,   92,  -12,   81,    0,    0,  -47,
   -3,    0,   77,  -11,  -30,   43,   54,    0,
};
final static int YYTABLESIZE=272;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         67,
   63,   42,   64,   19,   27,   68,   46,   46,   46,   20,
   46,   42,   46,   42,   43,   42,   40,   75,   40,   74,
   40,   90,   68,   48,   46,   46,   53,   46,   21,   42,
   42,   30,   42,   31,   40,   40,   41,   40,   41,   32,
   41,  103,   79,   36,   81,  104,  105,   34,   35,   30,
   32,   31,   33,   24,   41,   41,   90,   41,   92,   93,
   94,   95,   96,   97,   48,   34,   35,   90,   32,  101,
   33,    1,    1,  110,   22,   23,    1,    2,    2,    3,
    4,    4,    2,    5,  117,    4,   54,    5,   12,   28,
   13,   10,   29,   14,  111,   89,   30,   12,   15,   13,
   10,   10,   14,   34,   65,   84,   85,   15,   35,   66,
  115,   63,   63,   64,   64,   58,   31,   60,   86,   87,
   33,   37,   38,   47,   51,  109,   52,   59,   61,   62,
   69,   76,   77,   78,  116,   80,   82,   83,   88,  100,
   98,  107,  102,  106,  108,  112,  114,  113,  118,   21,
    4,  119,    5,   99,   10,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   19,    0,
    0,    0,   39,   40,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   70,   71,   72,   73,    0,   26,   26,    0,   46,   46,
   46,   46,    0,   42,   42,   42,   42,    0,   40,   40,
   40,   40,    0,   26,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   41,   41,
   41,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   43,   45,   45,   59,   46,   46,   41,   42,   43,  257,
   45,   41,   47,   43,   26,   45,   41,   60,   43,   62,
   45,   69,   46,   27,   59,   60,   30,   62,  264,   59,
   60,   41,   62,   41,   59,   60,   41,   62,   43,   17,
   45,   89,   55,   21,   57,  260,  261,   41,   41,   59,
   41,   59,   41,  257,   59,   60,  104,   62,   70,   71,
   72,   73,   74,   75,   68,   59,   59,  115,   59,   82,
   59,  257,  257,  104,    9,   10,  257,  263,  263,  264,
  266,  266,  263,  268,  115,  266,  272,  268,  257,   40,
  259,    0,   40,  262,  106,  264,   40,  257,  267,  259,
    9,   10,  262,  264,   42,   63,   64,  267,  269,   47,
   41,   43,   43,   45,   45,   35,  265,   37,   65,   66,
   59,   44,   59,  257,  270,  103,  257,  265,   46,  258,
   41,   41,   59,  257,  112,  265,  264,  257,   41,   40,
   59,   41,   59,   59,  265,  264,  261,  265,  265,  265,
  264,   59,  264,   77,  265,  265,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  264,   -1,
   -1,   -1,  256,  257,  258,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,  276,   -1,  277,  277,   -1,  273,  274,
  275,  276,   -1,  273,  274,  275,  276,   -1,  273,  274,
  275,  276,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,  276,
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
"sentencia_ej : asignacion ';'",
"sentencia_ej : PRINT '(' CADENA ')' ';'",
"sentencia_ej : seleccion",
"sentencia_ej : FOR '(' asignacion ';' condicion ';' expresion ')' bloque ';'",
"sentencia_ej : ID '.' ID '(' ')' ';'",
"seleccion : IF '(' condicion ')' bloque END_IF",
"seleccion : IF '(' condicion ')' bloque ELSE bloque END_IF",
"condicion : expresion MAYOR_IGUAL expresion",
"condicion : expresion MENOR_IGUAL expresion",
"condicion : expresion '>' expresion",
"condicion : expresion '<' expresion",
"condicion : expresion IGUAL expresion",
"condicion : expresion DISTINTO expresion",
"bloque : sentencia_ej",
"bloque : BEGIN sentencia_ej sentencias_ejecutables END",
"asignacion : ID ASIGN expresion",
"asignacion : ID '.' asignacion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : cte",
"factor : ID '.' ID",
"factor : error",
"cte : CTE",
"cte : '-' CTE",
};

//#line 109 "gramatica.y"
	ArrayList<String> variables = new ArrayList<String>();
	AnalizadorLexico aLexico=new AnalizadorLexico();
	ArrayList<String> errores = new ArrayList<String>();

	int yylex(){
		int token = aLexico.yylex();
		yylval = aLexico.getyylval();
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

	public void agregarTipoVariables(String tipo){
		for(String s: variables){
			aLexico.agregarAtributoLexema(s,"Tipo",tipo);
		}
	}

	public void verificarDeclaracionVariable(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!tablaSimbolos.get(id).containsKey("Declarada")){
			errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - Error - Uso de variable no declarada.");
		}
	}

	public void verificarDeclaracionClase(String id){
		HashMap<String, HashMap<String, Object>> tablaSimbolos = aLexico.getTablaSimbolos();
		if(!tablaSimbolos.get(id).containsKey("Tipo") || !tablaSimbolos.get(id).get("Tipo").toString().equals("clase")){
			errores.add("Numero de linea: "+ (aLexico.getContadorFila()+1) +" - Error - Tipo de variable no declarado.");
		}
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
//#line 379 "Parser.java"
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
//#line 11 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");}
break;
case 2:
//#line 14 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones.");}
break;
case 3:
//#line 15 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones.");}
break;
case 8:
//#line 24 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase.");
																	aLexico.agregarAtributoLexema(val_peek(4).sval,"Tipo","clase");}
break;
case 9:
//#line 26 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple.");
			 																			aLexico.agregarAtributoLexema(val_peek(6).sval,"Tipo","clase");}
break;
case 14:
//#line 36 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo.");}
break;
case 15:
//#line 39 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa");
						 agregarTipoVariables(val_peek(2).sval);
						 variables.clear();}
break;
case 18:
//#line 46 "gramatica.y"
{verificarDeclaracionClase(val_peek(0).sval);}
break;
case 19:
//#line 49 "gramatica.y"
{variables.add(val_peek(0).sval);
		     aLexico.agregarAtributoLexema(val_peek(0).sval,"Declarada",new Boolean(true));}
break;
case 20:
//#line 51 "gramatica.y"
{variables.add(val_peek(2).sval);
					  aLexico.agregarAtributoLexema(val_peek(2).sval,"Declarada",new Boolean(true));}
break;
case 24:
//#line 60 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print");}
break;
case 26:
//#line 62 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for");}
break;
case 27:
//#line 63 "gramatica.y"
{System.out.println("LLamado a metodo de objeto.");
					 verificarDeclaracionVariable(val_peek(5).sval);}
break;
case 28:
//#line 66 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if");}
break;
case 29:
//#line 67 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else");}
break;
case 38:
//#line 82 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable.");
				verificarDeclaracionVariable(val_peek(2).sval);}
break;
case 39:
//#line 84 "gramatica.y"
{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de atributo de objeto");
				  verificarDeclaracionVariable(val_peek(2).sval);}
break;
case 46:
//#line 97 "gramatica.y"
{verificarDeclaracionVariable(val_peek(0).sval);}
break;
case 48:
//#line 99 "gramatica.y"
{verificarDeclaracionVariable(val_peek(2).sval);
				    verificarDeclaracionVariable(val_peek(0).sval);}
break;
case 50:
//#line 104 "gramatica.y"
{ if(!aLexico.verificarRango(val_peek(0).sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
break;
case 51:
//#line 106 "gramatica.y"
{aLexico.actualizarTablaSimbolos(val_peek(0).sval);}
break;
//#line 623 "Parser.java"
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
