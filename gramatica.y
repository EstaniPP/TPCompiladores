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

declaracion_clase: CLASS ID BEGIN declaracion_sentencia_clase END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase.");}
		    	  |CLASS ID EXTENDS lista_variables BEGIN declaracion_sentencia_clase  END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple.");}
					
		;

declaracion_sentencia_clase: declaracion_sentencia
				| declaracion_sentencia declaracion_sentencia_clase
				| declaracion_metodo 
				| declaracion_metodo declaracion_sentencia_clase
				;

declaracion_metodo: VOID ID '(' ')' BEGIN sentencias_ejecutables END {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo.");}
		         ;

declaracion_sentencia: tipo lista_variables ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa");}
		;

tipo: INT
      | FLOAT
      | ID
      ;

lista_variables: ID
		| ID ',' lista_variables
		;

sentencias_ejecutables: sentencia_ej
	   | sentencia_ej sentencias_ejecutables
	   ;

sentencia_ej: asignacion
	        | impresion
	        | seleccion
	        | iteracion
	        | llamadometodo
	        ;

seleccion: IF '(' condicion ')' bloque END_IF {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if");}
	   | IF '(' condicion ')' bloque ELSE bloque END_IF {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else");}
	   
	   ;

iteracion:  FOR '(' asignacion condicion ';' expresion ')' bloque ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for");}
		;

llamadometodo:  ID '.' ID '(' ')' ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto.");}
		;

impresion: PRINT '(' CADENA ')' ';' {System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print");}
		;

asignacion: identificador ASIGN expresion ';'{System.out.println("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable.");}
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

identificador: ID
			  |ID '.' ID
				;

cte : CTE { if(!aLexico.verificarRango($1.sval)){
	    	yyerror("Error : constante entera fuera de rango.");}}
    | '-' CTE {aLexico.actualizarTablaSimbolos($2.sval);}
	;
%%
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