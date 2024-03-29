%{	
	package Analizadores;
	import java.util.ArrayList;
	import java.io.PrintWriter;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileWriter;
	import java.io.IOException;
	import javax.swing.JFileChooser;
	import java.util.HashMap;
	import javax.swing.JOptionPane;
%}

%token ID CTE IF ELSE END_IF PRINT INT BEGIN END FLOAT FOR CLASS EXTENDS CADENA ERROR VOID MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO ASIGN ERROR


%%
programa : {crearTerceto("CHECKOVERFLOWFLOAT",null,null,null); crearTerceto("OVERFLOW_FLOAT",null,null,null); crearTerceto("OVERFLOW_INT",null,null,null); crearTerceto("DIVISIONCERO",null,null,null);} conjunto_sentencias {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");
			crearTerceto("END_PROGRAMA",null,null,null);}
    	;

conjunto_sentencias: BEGIN {crearTerceto("LABEL","START",null,null);}  sentencias_ejecutables END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones. ");}
			| declaraciones BEGIN {crearTerceto("LABEL","START",null,null);} sentencias_ejecutables END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones. ");}
		   ;

declaraciones: declaracion_clase {agregarUsoVariables("nombreAtributo");}
			  | declaracion_sentencia {agregarUsoVariables("variable");}
			  | declaracion_sentencia {agregarUsoVariables("variable");} declaraciones 
			  | declaracion_clase {agregarUsoVariables("nombreAtributo");} declaraciones 
			 ;

declaracion_clase: CLASS ID {inicializarAtributos($2.sval);}BEGIN declaracion_sentencia_clase END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase. ");
																   verificarRedeclaracionClase($2.sval);
																   aLexico.agregarAtributoLexema($2.sval,"Uso","nombreClase");
																   agregarAtributoAClase($2.sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
		    	  |CLASS ID EXTENDS lista_clases  {inicializarAtributos($2.sval); agregarAtributoHeredados($2.sval);} BEGIN declaracion_sentencia_clase  END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple. ");
				  												   verificarRedeclaracionClase($2.sval);
																   aLexico.agregarAtributoLexema($2.sval,"Uso","nombreClase");
																   agregarAtributoAClase($2.sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
	  			  |CLASS error EXTENDS lista_clases BEGIN declaracion_sentencia_clase  END {yyerror("Error en la definicion de la clase: falta nombre de clase");}
	  			  |CLASS error BEGIN declaracion_sentencia_clase  END {yyerror("Error en la definicion de la clase: falta nombre de clase");}
	  			  |CLASS ID error declaracion_sentencia_clase  END {yyerror("Error en la definicion de la clase: falta begin de clase");}
	  			  |CLASS ID EXTENDS lista_clases error declaracion_sentencia_clase  END {yyerror("Error en la definicion de la clase: falta begin de clase");}
	  			  |CLASS ID EXTENDS error BEGIN declaracion_sentencia_clase  END {yyerror("Error en la definicion de la clase: falta nombre de clase extendidas");}
	  			  |CLASS ID error lista_clases BEGIN declaracion_sentencia_clase  END {yyerror("Error en la definicion de la clase: falta palabra reservada extends");}
					
					
		;

declaracion_sentencia_clase: declaracion_sentencia {if(!tipo.equals("int") && !tipo.equals("float")) yyerrorSemantico("Los atributos de la clase deben ser de tipos primitivos.");}
				| declaracion_sentencia {if(!tipo.equals("int") && !tipo.equals("float")) yyerrorSemantico("Los atributos de la clase deben ser de tipos primitivos.");} declaracion_sentencia_clase
				| declaracion_metodo 
				| declaracion_metodo declaracion_sentencia_clase
				;

declaracion_metodo: VOID ID '(' ')' {crearTerceto("METODO",$2.sval,null,null);} BEGIN sentencias_ejecutables END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo. ");
																	 aLexico.agregarAtributoLexema($2.sval,"Uso","nombreMetodo");
																	 metodosPorClase.add($2.sval);
																	 crearTerceto("TERMINA_METODO",null,null,null);
																	 }
	  			  | VOID error '(' ')' BEGIN sentencias_ejecutables END {yyerror("Error en la definicion del metodo: falta nombre de metodo");}
	  			  | VOID ID '(' error ')' BEGIN sentencias_ejecutables END {yyerror("Error en la definicion del metodo: contiene parametros");}
	  			  | VOID ID error ')' BEGIN sentencias_ejecutables END {yyerror("Error en la definicion del metodo: falta parentesis (");}
	  			  | VOID ID '(' error BEGIN sentencias_ejecutables END {yyerror("Error en la definicion del metodo: falta parentesis )");}
	  			  | VOID ID '(' ')' error sentencias_ejecutables END {yyerror("Error en la definicion del metodo: falta begin");}
		         ;

declaracion_sentencia: tipo lista_variables ';' {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia declarativa ");
												agregarTipoVariables(tipo);

												}
		;

tipo: INT {tipo = "int";}
      | FLOAT {tipo = "float";}
      | ID {tipo = $1.sval;
	  		verificarDeclaracionClase($1.sval);}
      ;

lista_variables: ID {variables.add($1.sval);
					 variablesPorUso.add($1.sval);}
		| ID ',' lista_variables {variables.add($1.sval);
								  variablesPorUso.add($1.sval);}
		;

lista_clases: ID {listaHerencia.add($1.sval);}
		| ID ',' lista_clases {listaHerencia.add($1.sval);}
		;

sentencias_ejecutables: sentencia_ej
	   | sentencia_ej sentencias_ejecutables
	   ;

sentencia_ej: asignacion
	        | impresion
	        | seleccion
	        | iteracion
	        | llamadometodo
			| error ';' {yyerror("Error de sentencia");}
	        ;

seleccion: IF '(' condicion ')' bloqueThen END_IF ';' {desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if ");}
	   | IF '(' condicion ')' bloqueThen ELSE bloqueElse END_IF ';' {desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else ");}
	   | IF '(' error ')' bloqueThen END_IF ';' {yyerror("Error en la condicion del if");}
	   | IF '(' error bloqueThen END_IF ';' {yyerror("Error en la definicion del if: falta )");}
	   | IF error ')' bloqueThen END_IF ';' {yyerror("Error en la definicion del if: falta (");}
   	   | IF '(' error ')' bloqueThen ELSE bloque END_IF ';' {yyerror("Error en la condicion del if");}
	   | IF '(' error bloqueThen ELSE bloque END_IF ';' {yyerror("Error en la definicion del if: falta )");}
	   | IF error ')' bloqueThen ELSE bloque END_IF ';' {yyerror("Error en la definicion del if: falta (");}
	   | IF '(' condicion ')' bloqueThen error ';' {yyerror("Error en la definicion del if: falta end_if");}
	   | IF '(' condicion ')' bloqueThen ELSE bloqueElse ';' {yyerror("Error en la definicion del if: falta end_if");}
	   |IF '(' '(' condicion ')' bloqueThen END_IF ';' {yyerror("Error en la definicion del if: hay un parentesis de mas del lado izquierdo");}
	   |IF '(' condicion ')'')' bloqueThen END_IF ';' {yyerror("Error en la definicion del if: hay un parentesis de mas del lado derecho");}
	   |IF '(' '(' condicion ')' bloqueThen ELSE bloqueElse END_IF ';' {yyerror("Error en la definicion del if: hay un parentesis de mas del lado izquierdo");}
	   |IF '(' condicion ')'')' bloqueThen ELSE bloqueElse END_IF ';' {yyerror("Error en la definicion del if: hay un parentesis de mas del lado derecho");}
	   ;


bloqueThen:	bloque ;
bloqueElse:	{desapilarSalto(tercetos.size() + 1); apilarSalto(crearTerceto("BI",null,null,null)); crearTerceto("LABEL","Label"+tercetos.size(),null,null);} bloque ;
iteracion:  FOR '(' asignacion factor {crearTerceto("LABEL","Label"+tercetos.size(),null,null); insertarNoTerminal("condicion",crearTercetoTipo("CMP",tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("factor")))); apilarSalto(crearTerceto("JGE",noTerminalTercetos.get("condicion"),null,null));} 
				';' factor {apilarExpresionFor(tercetos.get(Integer.parseInt((noTerminalTercetos.get("asignacion")).substring(1,(noTerminalTercetos.get("asignacion")).length()-1))).operando1,noTerminalTercetos.get("factor"));} ')'
				 bloque ';' {
					 if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){
					 desapilarExpresionFor(); 
					 crearTerceto("BI",null,new String("[" + (pilaSaltos.get(pilaSaltos.size()-1)-2) + "]"),null); 
					 desapilarSalto(tercetos.size()); crearTerceto("LABEL","Label"+tercetos.size(),null,null); 
					 salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for ");}
				 }
		   |FOR '(' error ')' bloque ';'{yyerror("Error en la definicion del for");}
		   |FOR error ')' bloque ';'{yyerror("Error en la definicion del for falta (");}
		   |FOR '(' error bloque ';'{yyerror("Error en la definicion del for falta )");}
		;

llamadometodo:  ID '.' ID '(' ')' ';' {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto. ");
									   if(verificarDeclaracionVariable($1.sval)){
									   	verificarDeclaracionMetodo($1.sval,$3.sval);
									   	crearTercetoCopiaValor($1.sval);
									   	crearTerceto("CALL",$3.sval,$1.sval,null);
									   	crearTercetoCopiaResultado($1.sval);
									   }
									   }
			   |ID '.' ID error ';'{yyerror("Error en la invocacion a metodo");}
			   |ID '.' error '(' ')' ';'{yyerror("Error en la invocacion a metodo - metodo vacio");}
		;

impresion: PRINT '(' CADENA ')' ';' {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print ");
									crearTerceto("PRINT",$3.sval,null,null);}
		  |PRINT error ';'{yyerror("Error en la impresion");}
		;

asignacion: identificador ASIGN expresion ';'{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable. ");
											  insertarNoTerminal("asignacion",crearTercetoTipo(":=",$1.sval,noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("expresion"))));}
			|identificador ASIGN error ';'{yyerror("Error en la asignacion lado derecho");}
			|error ASIGN expresion ';'{yyerror("Error en la asignacion lado izquierdo");}
	     ;

condicion: expresion {insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));} MAYOR_IGUAL expresion {insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
					  apilarSalto(crearTerceto("JL",noTerminalTercetos.get("condicion"),null,null));}
	     | expresion {insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));} MENOR_IGUAL expresion {insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JG",noTerminalTercetos.get("condicion"),null,null));}
     	 | expresion {insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));} '>' expresion {insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		  			  apilarSalto(crearTerceto("JLE",noTerminalTercetos.get("condicion"),null,null));}
	     | expresion {insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));} '<' expresion {insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JGE",noTerminalTercetos.get("condicion"),null,null));}
	     | expresion {insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));} IGUAL expresion {insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JNE",noTerminalTercetos.get("condicion"),null,null));}
	     | expresion {insertarNoTerminal("li_condicion",noTerminalTercetos.get("expresion"));} DISTINTO expresion {insertarNoTerminal("condicion",crearTercetoTipo("CMP",noTerminalTercetos.get("li_condicion"),noTerminalTercetos.get("expresion"),getTipo(noTerminalTercetos.get("li_condicion"))));
		 			  apilarSalto(crearTerceto("JE",noTerminalTercetos.get("condicion"),null,null));}
	     ;

bloque: sentencia_ej
	| BEGIN sentencias_ejecutables END
	;

expresion: expresion '+' termino {insertarNoTerminal("expresion",crearTercetoTipo("+",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
	    | expresion '-' termino {insertarNoTerminal("expresion",crearTercetoTipo("-",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino"),getTipo(noTerminalTercetos.get("termino"))));}
	    | termino {insertarNoTerminal("expresion",noTerminalTercetos.get("termino"));}
	    ;
termino: termino '*' factor {insertarNoTerminal("termino",crearTercetoTipo("*",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
	| termino '/' factor {insertarNoTerminal("termino",crearTercetoTipo("/",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor"),getTipo(noTerminalTercetos.get("termino"))));}
	| factor {insertarNoTerminal("termino",noTerminalTercetos.get("factor"));}
	;

factor: identificador {insertarNoTerminal("factor",$1.sval);}
		| cte {insertarNoTerminal("factor",$1.sval);}
		| ERROR
        ;

identificador: ID {verificarDeclaracionVariable($1.sval);}
			  |ID '.' ID 
				    {if(verificarDeclaracionVariable($1.sval))
					verificarDeclaracionAtributo($1.sval,$3.sval);
					$$ = new ParserVal($1.sval + "@" + $3.sval);} ;
				;

cte : CTE { if(!aLexico.verificarRango($1.sval)){
	    	yyerror("Error : constante entera fuera de rango.");}else{
			}}
    | '-' CTE {aLexico.actualizarTablaSimbolos($2.sval);
				$$ = new ParserVal("-" + $2.sval);}
	;
%%
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

				for(String s: this.getErrores()){
					escribir.write(s.toString());
					escribir.write(sb.toString());
				}

				escribir.write(aLexico.getDatosTablaSimbolos());
				escribir.write(sb.toString());

				
				escribir.write("Tercetos");
				escribir.write(sb.toString());
				
				int i =0;	
				for(Terceto s : tercetos) {
					escribir.write((i++) + "-" + s.toString());
					escribir.write(sb.toString());
				}	
				
		        escribir.close();

				if(aLexico.getErrores().size() == 0 && erroresSem.size() == 0 && errores.size() == 0){		
					PrintWriter writer1 = new PrintWriter(archivo+"\\program.asm");
					writer1.print("");
					FileWriter escribir1 = new FileWriter(archivo+"\\program.asm", true);
			
					escribir1.write(".386");
					escribir1.write(sb.toString());
					escribir1.write(".model flat, stdcall");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\masm32rt.inc");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\windows.inc");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\kernel32.inc");
					escribir1.write(sb.toString());
					escribir1.write("include \\masm32\\include\\user32.inc");
					escribir1.write(sb.toString());
					escribir1.write("includelib \\masm32\\lib\\kernel32.lib");
					escribir1.write(sb.toString());
					escribir1.write("includelib \\masm32\\lib\\user32.lib");
					escribir1.write(sb.toString());
					escribir1.write(".data");
					escribir1.write(sb.toString());

					HashMap<String,HashMap<String,Object>> tablaSimbolos = aLexico.getTablaSimbolos();
					for(String key: tablaSimbolos.keySet()){
						HashMap<String,Object> datosKey = tablaSimbolos.get(key);
						if(datosKey.containsKey("Uso")){
							if(datosKey.get("Uso").equals("variable") || datosKey.get("Uso").equals("nombreAtributo")){
								if(datosKey.get("Tipo").equals("int")){
									if(key.charAt(0)=='-')
									{
										escribir1.write("@"+key.substring(1,key.length())+" DW ?");
										escribir1.write(sb.toString());
									}else{
										escribir1.write("_"+key+" DW ?");
										escribir1.write(sb.toString());
									}
									
								}else if(datosKey.get("Tipo").equals("float")){
									escribir1.write("_"+key.replaceFirst("[.]","_").replaceFirst("-","@")+" DD ?");
									escribir1.write(sb.toString());
								}
							}
						}else if(datosKey.containsKey("Tipo") && datosKey.get("Tipo").equals("cadena")){
							escribir1.write("_"+datosKey.get("NombreString")+" DB \""+key+"\",0");
							escribir1.write(sb.toString());									
						}else if(datosKey.containsKey("Tipo") && datosKey.get("Tipo").equals("float")){
							escribir1.write("_"+key.replaceFirst("[.]","_").replaceFirst("-","@")+" DD "+key);
							escribir1.write(sb.toString());						
						}
					}
					//Variables overflow float
					escribir1.write("minimoPositivo DQ 1.17549435E-38");
					escribir1.write(sb.toString());		
					escribir1.write("minimoNegativo DQ -1.17549435E-38");
					escribir1.write(sb.toString());		
					escribir1.write("maximoPositivo DQ 3.40282347E38");
					escribir1.write(sb.toString());		
					escribir1.write("maximoNegativo DQ -3.40282347E38");
					escribir1.write(sb.toString());		
					escribir1.write("ceroFloat DQ 0.0");
					escribir1.write(sb.toString());		
					escribir1.write("aux DQ ?");
					escribir1.write(sb.toString());									
					escribir1.write("@OVERFLOWFLOAT DB \"Error overflow float\",0");
					escribir1.write(sb.toString());
					escribir1.write("@OVERFLOWINT DB \"Error overflow int\",0");
					escribir1.write(sb.toString());
					escribir1.write("@DIVISIONCERO DB \"Error division por 0\",0");
					escribir1.write(sb.toString());
					escribir1.write(".code");
					escribir1.write(sb.toString());
					
					for(String s : Terceto.codigo) {
						escribir1.write(s);
						escribir1.write(sb.toString());
					}
					escribir1.close();
				}else{
					JOptionPane.showMessageDialog(null, "Hubo errores de compilacion, revise salidaCompilador.txt");
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
		if(par.aLexico.getErrores().size() == 0 && par.erroresSem.size() == 0 && par.errores.size() == 0){
			Terceto.crearCodigo(par.tercetos,par.aLexico.getTablaSimbolos());
		}
		par.saveFile();
	}