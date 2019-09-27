package Analizadores;
import java.util.ArrayList;

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
AnalizadorLexico aLexico=new AnalizadorLexico();
ArrayList<String> errores = new ArrayList<String>();

void yyerror(String error) {
	errores.add(error);
}

public ArrayList<String> getErrores() {
	ArrayList<String> erroresTotales = new ArrayList<String>();
	erroresTotales.addAll(errores);
	erroresTotales.addAll(aLexico.getErrores());
	return erroresTotales;
}

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
    0,    1,    1,    1,    2,    2,    2,    2,    4,    4,
    6,    6,    6,    6,    8,    5,    9,    9,    9,    7,
    7,    3,    3,   10,   10,   10,   10,   10,   12,   12,
   13,   13,   13,   13,   13,   13,   15,   15,   11,   11,
   14,   14,   14,   16,   16,   16,   17,   17,   17,   18,
   18,
};
final static short yylen[] = {                            2,
    1,    1,    3,    4,    1,    1,    2,    2,    5,    7,
    1,    2,    1,    2,    7,    3,    1,    1,    1,    1,
    3,    1,    2,    2,    5,    1,   10,    6,    6,    8,
    3,    3,    3,    3,    3,    3,    1,    4,    3,    5,
    3,    3,    1,    3,    3,    1,    1,    1,    3,    1,
    2,
};
final static short yydefred[] = {                         0,
   19,   17,    0,   18,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   26,    0,
    0,    8,    7,    0,    0,    0,    0,    0,    0,    0,
    3,   23,   24,    0,    0,    0,    0,   16,    0,   50,
    0,    0,    0,   46,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    4,   21,    0,   51,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   12,    9,   14,
    0,   49,    0,    0,   44,   45,    0,    0,    0,   37,
    0,    0,    0,    0,    0,    0,    0,   25,    0,    0,
    0,    0,   28,    0,    0,   29,    0,    0,   10,    0,
    0,    0,    0,   38,   30,    0,    0,    0,   15,   27,
};
final static short yydgoto[] = {                          6,
    7,    8,   16,    9,   53,   54,   25,   55,   11,   17,
   18,   19,   47,   48,   91,   43,   44,   45,
};
final static short yysindex[] = {                      -183,
    0,    0, -157,    0, -243,    0,    0, -242, -171, -171,
 -218,  -43,    8,   18,   33, -200, -157,   45,    0, -197,
 -157,    0,    0,   64,   61,  -36, -134,  -36, -145, -133,
    0,    0,    0, -188, -218, -139, -218,    0,   81,    0,
 -130,   34,   59,    0,    0,  -40,   89,  -17,   90,  -42,
   73, -124, -188, -131, -188, -129,    0,    0, -121,    0,
  -36,  -36,  -36,  -36,  -36,   96, -168,  -36,  -36,  -36,
  -36,  -36,  -36,   80, -117,  -36,  101,    0,    0,    0,
 -188,    0,   59,   59,    0,    0,   34,   83, -157,    0,
 -146,   34,   34,   34,   34,   34,   34,    0, -132,   84,
  105, -118,    0, -157, -168,    0,  -36, -120,    0, -116,
 -113,   66, -157,    0,    0, -168, -115,   92,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  152,    1,    2,
    0,    0,    0,    0,    0,    0, -112,    0,    0,    0,
    0,    0,    0,  -54,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -35,    0,
    0,   95,  -30,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -110,    0, -109,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -25,   -5,    0,    0,   98,    0,    0,    0,
    0,  -18,  -10,   -8,    9,   11,   12,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  107,   25,    0,  103,  -34,   53,    0,    0,  -23,
  128,    0,   85,   -9,  -29,   57,   58,    0,
};
final static int YYTABLESIZE=271;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         66,
    5,    6,   27,   75,   20,   47,   47,   47,   41,   47,
   43,   47,   43,   20,   43,   41,   42,   41,   78,   41,
   80,   21,   31,   47,   47,   61,   47,   62,   43,   43,
   32,   43,   35,   41,   41,   42,   41,   42,   24,   42,
   31,   32,   73,   90,   72,   36,  102,   28,   32,   36,
   35,   33,   34,   42,   42,   87,   42,   29,   92,   93,
   94,   95,   96,   97,   31,  104,   34,   36,    1,   33,
   34,   35,   30,    1,    2,  111,   61,    4,   62,    2,
    3,   90,    4,   52,    5,    1,  118,   56,   12,   58,
   13,    2,   90,   14,    4,   89,    5,  112,   15,   12,
   63,   13,   10,   33,   14,   64,  116,   37,   61,   15,
   62,   10,   10,  105,  106,   22,   23,   83,   84,   38,
   85,   86,   46,   50,   49,   57,   59,   60,  110,   67,
   74,   76,   77,   79,   81,   82,   88,  117,   98,   99,
  101,  103,  107,  113,   65,  108,  109,  115,  114,  119,
  120,    2,   22,   39,   11,   13,   40,   51,    0,    0,
  100,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   20,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,   40,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   26,   26,    0,   65,   47,   47,   47,
   47,    0,   43,   43,   43,   43,    0,   41,   41,   41,
   41,    0,    0,    0,    0,   68,   69,   70,   71,    0,
    0,    0,    0,    0,    5,    6,    0,   42,   42,   42,
   42,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   46,   46,   59,   41,   42,   43,   45,   45,
   41,   47,   43,  257,   45,   41,   26,   43,   53,   45,
   55,  264,   41,   59,   60,   43,   62,   45,   59,   60,
   41,   62,   41,   59,   60,   41,   62,   43,  257,   45,
   59,   17,   60,   67,   62,   21,   81,   40,   59,   41,
   59,   41,   41,   59,   60,   65,   62,   40,   68,   69,
   70,   71,   72,   73,  265,   89,  264,   59,  257,   59,
   59,  269,   40,  257,  263,  105,   43,  266,   45,  263,
  264,  105,  266,  272,  268,  257,  116,   35,  257,   37,
  259,  263,  116,  262,  266,  264,  268,  107,  267,  257,
   42,  259,    0,   59,  262,   47,   41,   44,   43,  267,
   45,    9,   10,  260,  261,    9,   10,   61,   62,   59,
   63,   64,  257,  257,  270,  265,   46,  258,  104,   41,
   41,   59,  257,  265,  264,  257,   41,  113,   59,  257,
   40,   59,   59,  264,  277,   41,  265,  261,  265,  265,
   59,    0,  265,   59,  265,  265,   59,   30,   -1,   -1,
   76,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  264,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  277,   -1,  277,  273,  274,  275,
  276,   -1,  273,  274,  275,  276,   -1,  273,  274,  275,
  276,   -1,   -1,   -1,   -1,  273,  274,  275,  276,   -1,
   -1,   -1,   -1,   -1,  264,  264,   -1,  273,  274,  275,
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
"conjunto_sentencias : declaraciones",
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
"asignacion : ID '.' ID ASIGN expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : cte",
"factor : ID '.' ID",
"cte : CTE",
"cte : '-' CTE",
};

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
        yychar = aLexico.yylex();  //get next token
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
        yychar = aLexico.yylex();        //get next character
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
