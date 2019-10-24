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
%}

%token ID CTE IF ELSE END_IF PRINT INT BEGIN END FLOAT FOR CLASS EXTENDS CADENA ERROR VOID MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO ASIGN ERROR


%%
programa : conjunto_sentencias {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa aceptado.");}
    	;

conjunto_sentencias: BEGIN  sentencias_ejecutables END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables sin declaraciones. ");}
			| declaraciones BEGIN sentencias_ejecutables END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Programa con sentencias ejecutables y declaraciones. ");}
		   ;

declaraciones: declaracion_clase {agregarUsoVariables("nombreAtributo");}
			  | declaracion_sentencia {agregarUsoVariables("variable");}
			  | declaracion_sentencia {agregarUsoVariables("variable");} declaraciones 
			  | declaracion_clase {agregarUsoVariables("nombreAtributo");} declaraciones 
			 ;

declaracion_clase: CLASS ID {inicializarAtributos($2.sval);}BEGIN declaracion_sentencia_clase END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase. ");
																   aLexico.agregarAtributoLexema($2.sval,"Uso","nombreClase");
																   agregarAtributoAClase($2.sval);
																   metodosPorClase.clear();
																   ambitoActual = null;}
		    	  |CLASS ID EXTENDS lista_clases  {inicializarAtributos($2.sval); agregarAtributoHeredados($2.sval);} BEGIN declaracion_sentencia_clase  END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de clase con herencia multiple. ");
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

declaracion_sentencia_clase: declaracion_sentencia
				| declaracion_sentencia declaracion_sentencia_clase
				| declaracion_metodo 
				| declaracion_metodo declaracion_sentencia_clase
				;

declaracion_metodo: VOID ID '(' ')' BEGIN sentencias_ejecutables END {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Declaracion de metodo. ");
																	 aLexico.agregarAtributoLexema($2.sval,"Uso","nombreMetodo");
																	 metodosPorClase.add($2.sval);
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

seleccion: IF '(' condicion ')' bloque END_IF {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if ");}
	   | IF '(' condicion ')' bloque ELSE bloque END_IF {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia if else ");}
	   | IF '(' error ')' bloque END_IF {yyerror("Error en la definicion del if");}
	   | IF '(' error bloque END_IF {yyerror("Error en la definicion del if falta )");}
	   | IF error ')' bloque END_IF {yyerror("Error en la definicion del if falta (");}
   	   | IF '(' error ')' bloque ELSE bloque END_IF {yyerror("Error en la definicion del if");}
	   | IF '(' error bloque ELSE bloque END_IF {yyerror("Error en la definicion del if falta )");}
	   | IF error ')' bloque ELSE bloque END_IF {yyerror("Error en la definicion del if falta (");}
	   ;

iteracion:  FOR '(' asignacion expresion ';' expresion ')' bloque ';' {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia for ");}
		   |FOR '(' error ')' bloque ';'{yyerror("Error en la definicion del for");}
		   |FOR error ')' bloque ';'{yyerror("Error en la definicion del for falta (");}
		   |FOR '(' error bloque ';'{yyerror("Error en la definicion del for falta )");}
		;

llamadometodo:  ID '.' ID '(' ')' ';' {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - LLamado a metodo de objeto. ");
									   verificarDeclaracionVariable($1.sval);
									   verificarDeclaracionMetodo($1.sval,$3.sval);}
			   |ID '.' ID error ';'{yyerror("Error en la invocacion a metodo");}
			   |ID '.' error '(' ')' ';'{yyerror("Error en la invocacion a metodo - metodo vacio");}
		;

impresion: PRINT '(' CADENA ')' ';' {salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia print ");}
		  |PRINT error ';'{yyerror("Error en la impresion");}
		;

asignacion: identificador ASIGN expresion ';'{salida.add("Linea - "+ (aLexico.getContadorFila()+1)+" - Sentencia asignacion de variable. ");
											  insertarNoTerminal("asignacion",crearTerceto(":=",$1.sval,noTerminalTercetos.get("expresion")));}
			|identificador ASIGN error ';'{yyerror("Error en la asignacion lado derecho");}
			|error ASIGN expresion ';'{yyerror("Error en la asignacion lado izquierdo");}
	     ;

condicion: expresion MAYOR_IGUAL expresion
	     | expresion MENOR_IGUAL expresion
     	 | expresion '>' expresion
	     | expresion '<' expresion
	     | expresion IGUAL expresion
	     | expresion DISTINTO expresion
	     ;

bloque: sentencia_ej
	| BEGIN sentencias_ejecutables END
	;

expresion: expresion '+' termino {insertarNoTerminal("expresion",crearTerceto("+",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino")));}
	    | expresion '-' termino {insertarNoTerminal("expresion",crearTerceto("-",noTerminalTercetos.get("expresion"),noTerminalTercetos.get("termino")));}
	    | termino {insertarNoTerminal("expresion",noTerminalTercetos.get("termino"));}
	    ;
termino: termino '*' factor {insertarNoTerminal("termino",crearTerceto("*",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor")));}
	| termino '/' factor {insertarNoTerminal("termino",crearTerceto("/",noTerminalTercetos.get("termino"),noTerminalTercetos.get("factor")));}
	| factor {insertarNoTerminal("termino",noTerminalTercetos.get("factor"));}
	;

factor: identificador {insertarNoTerminal("factor",$1.sval);}
		| cte {insertarNoTerminal("factor",$1.sval);}
		| ERROR
        ;

identificador: ID {verificarDeclaracionVariable($1.sval);}
			  |ID '.' ID 
				    {verificarDeclaracionVariable($1.sval);
					verificarDeclaracionAtributo($1.sval,$3.sval);
					$$ = new ParserVal($1.sval + "." + $3.sval);} ;
				;

cte : CTE { if(!aLexico.verificarRango($1.sval)){
	    	yyerror("Error : constante entera fuera de rango.");}else{
			}}
    | '-' CTE {aLexico.actualizarTablaSimbolos($2.sval);}
	;
%%
	AnalizadorLexico aLexico=new AnalizadorLexico();
	ArrayList<String> errores = new ArrayList<String>();
	ArrayList<String> salida = new ArrayList<String>();
	ArrayList<String> tokens = new ArrayList<String>();
	ArrayList<String> variables = new ArrayList<String>();
	ArrayList<String> variablesPorUso = new ArrayList<String>();
	ArrayList<String> metodosPorClase = new ArrayList<String>();
	ArrayList<String> listaHerencia = new ArrayList<String>();
	ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
	String tipo, ambitoActual;
	HashMap<String,String> noTerminalTercetos = new HashMap<String,String>();

	public String getTipo(String elemento){
		if(elemento.charAt(0) == '[' && elemento.charAt(elemento.length()-1) == ']'){
			elemento = elemento.substring(1,elemento.length()-1);
			return tercetos.get(Integer.parseInt(elemento)).tipo;
		}else{
			return (String) aLexico.getTablaSimbolos().get(elemento).get("Tipo");
		}
	}
	public void insertarNoTerminal(String noTerminal, String value){
		if(aLexico.getErrores().size() == 0 && errores.size() == 0){
			noTerminalTercetos.put(noTerminal,value);
		}
	}

	public String crearTerceto(String operador,String operando1,String operando2){
		if(getTipo(operando1) != getTipo(operando2)){
			yyerror("Incopatibilidad de tipos");
		}
		if(aLexico.getErrores().size() == 0 && errores.size() == 0){
			tercetos.add(new Terceto(operador,operando1,operando2,getTipo(operando1)));
			return new String("["+Integer.toString(tercetos.size()-1)+"]");
		}else{
			return null;
		}
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

	public ArrayList<String> getErrores() {
		ArrayList<String> erroresTotales = new ArrayList<String>();
		erroresTotales.add("Errores lexicos:");
		erroresTotales.addAll(aLexico.getErrores());		
		erroresTotales.add("Errores sintacticos:");
		erroresTotales.addAll(errores);
		return erroresTotales;
	}
 	public void crearObjetoTablaSimbolos(String tipo){
		ArrayList<String> atributosClase = (ArrayList<String>) aLexico.getTablaSimbolos().get(tipo).get("VariablesClase");
		for(String s : variables){
			for(String atributo : atributosClase){
				aLexico.getTablaSimbolos().put(s+"."+atributo,new HashMap<String,Object>(aLexico.getTablaSimbolos().get(atributo)));
			}
		}
	}
	public void agregarTipoVariables(String tipo){
		for(String s: variables){
			aLexico.agregarAtributoLexema(s,"Tipo",tipo);
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
		System.out.println(par.tercetos.toString());
	}