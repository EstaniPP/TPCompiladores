%{	
	package Analizadores;
	import java.util.ArrayList;
	import java.util.HashMap;
%}

%token ID CTE IF ELSE END_IF PRINT INT BEGIN END FLOAT FOR CLASS EXTENDS CADENA ERROR VOID MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO ASIGN


%%
programa : conjunto_sentencias {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");}
    	;

conjunto_sentencias: BEGIN  sentencias_ejecutables END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones.");}
			| declaraciones BEGIN sentencias_ejecutables END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones.");}
		   ;

declaraciones: declaracion_clase
			  | declaracion_sentencia
			  | declaracion_sentencia declaraciones
			  | declaracion_clase declaraciones 
			 ;

declaracion_clase: CLASS ID BEGIN declaracion_sentencia_clase END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase.");
																	aLexico.agregarAtributoLexema($1.sval,"Tipo","clase");}
		    		|CLASS ID EXTENDS lista_variables BEGIN declaracion_sentencia_clase  END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple.");
			 																			aLexico.agregarAtributoLexema($1.sval,"Tipo","clase");}
					
		;

declaracion_sentencia_clase: declaracion_sentencia
				| declaracion_sentencia declaracion_sentencia_clase
				| declaracion_metodo 
				| declaracion_metodo declaracion_sentencia_clase
				;

declaracion_metodo: VOID ID '(' ')' BEGIN sentencias_ejecutables END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo.");}
		         ;

declaracion_sentencia: tipo lista_variables ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa");
						 agregarTipoVariables($1.sval);
						 variables.clear();}
		;

tipo: INT
      | FLOAT
      | ID {verificarDeclaracionClase($1.sval);}
      ;

lista_variables: ID {variables.add($1.sval);
		     aLexico.agregarAtributoLexema($1.sval,"Declarada",new Boolean(true));}
		| ID ',' lista_variables {variables.add($1.sval);
					  aLexico.agregarAtributoLexema($1.sval,"Declarada",new Boolean(true));}
		;

sentencias_ejecutables: sentencia_ej
	   | sentencia_ej sentencias_ejecutables
	   ;

sentencia_ej: asignacion ';'
	        | PRINT '(' CADENA ')' ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print");}
	        | seleccion
	        |  FOR '(' asignacion ';' condicion ';' expresion ')' bloque ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for");}
	        | ID '.' ID '(' ')' ';' {System.out.println("LLamado a metodo de objeto.");
					 verificarDeclaracionVariable($1.sval);}
	        ;
seleccion: IF '(' condicion ')' bloque END_IF {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if");}
	   | IF '(' condicion ')' bloque ELSE bloque END_IF {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else");}
	   ;

condicion: expresion MAYOR_IGUAL expresion
	     | expresion MENOR_IGUAL expresion
     	     | expresion '>' expresion
	     | expresion '<' expresion
	     | expresion IGUAL expresion
	     | expresion DISTINTO expresion
	     ;

bloque: sentencia_ej
	| BEGIN sentencia_ej sentencias_ejecutables END
	;

asignacion: identificador ASIGN expresion {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable.");
				verificarDeclaracionVariable($1.sval);}
	     ;

expresion: expresion '+' termino
	    | expresion '-' termino
	    | termino
	    ;
termino: termino '*' factor
	| termino '/' factor
	| factor
	;

factor: identificador
		| cte 
		| error
        ;

identificador: ID {verificarDeclaracionVariable($1.sval);}
			  |ID '.' ID {verificarDeclaracionVariable($1.sval);
				    verificarDeclaracionVariable($3.sval);} ;

cte : CTE { if(!aLexico.verificarRango($1.sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
    | '-' CTE {aLexico.actualizarTablaSimbolos($2.sval);}
	;
%%
	ArrayList<String> variables = new ArrayList<String>();
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