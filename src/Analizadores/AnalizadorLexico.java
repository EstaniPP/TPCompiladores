package Analizadores;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import AccionesSemanticas.*;

public class AnalizadorLexico {
	
	private int contadorFila;
	private int contadorColumna;
	private int ultimoEstado;
	private File archivo;
	private StringBuilder lexema;
	
	private ArrayList<String> errores;;
	private ArrayList<String> allLines;
	private HashMap<String, Integer> codigosTokens;
	private HashMap<Character, Integer> mapCaracterColumna;
	private HashMap<String, HashMap<String, Object>> tablaSimbolos;
	private int[][] matrizTransicionEstados;
	private AccionSemantica[][] matrizAccionesSemanticas;
	
	public AnalizadorLexico() {
		
		//INICIALIZO VARIBLES
		
		contadorFila=0;
		contadorColumna=0;
		ultimoEstado=0;
		errores = new ArrayList<String>();
		
		//JFILECHOOSER
		
		JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivo = fc.getSelectedFile();
        }
        
        //LEO TODAS LAS LINEAS DEL ARCHIVO
        
        allLines = new ArrayList<String>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					archivo));
			String line = reader.readLine();
			while (line != null) {
				StringBuilder linea = (new StringBuilder(line)).append('\n');
				allLines.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ASIGNO CODIGO DE TOKEN
		
		codigosTokens=new HashMap<String, Integer>();

		codigosTokens.put("$", 0);
		codigosTokens.put("(", 40);
		codigosTokens.put(")", 41);
		codigosTokens.put("*", 42);
		codigosTokens.put("+", 43);
		codigosTokens.put(",", 44);
		codigosTokens.put("-", 45);
		codigosTokens.put("/", 47);
		codigosTokens.put(";", 59);
		codigosTokens.put("<", 60);
		codigosTokens.put(">", 62);
		codigosTokens.put("ID", 257);
		codigosTokens.put("CTE", 258);
		codigosTokens.put("if", 259);
		codigosTokens.put("else", 260);
		codigosTokens.put("end_if", 261);
		codigosTokens.put("print", 262);
		codigosTokens.put("int", 263);
		codigosTokens.put("begin", 264);
		codigosTokens.put("end", 265);
		codigosTokens.put("float", 266);
		codigosTokens.put("for", 267);
		codigosTokens.put("class", 268);
		codigosTokens.put("extends", 269);
		codigosTokens.put("CADENA", 270);
		codigosTokens.put("ERROR", 271);
		codigosTokens.put("void", 272);
		codigosTokens.put(">=", 273);
		codigosTokens.put("<=", 274);
		codigosTokens.put("==", 275);
		codigosTokens.put("<>", 276);
		codigosTokens.put(":=", 277);
				
		//MAPEO CADA CHARACTER CON EL NUMERO DE COLUMNA CORRESPONDIENTE
		
		mapCaracterColumna=new HashMap<Character, Integer>();
		char[] letras = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		for(int i=0; i<letras.length; i++) {
			mapCaracterColumna.put(letras[i], 0);
			mapCaracterColumna.put(Character.toUpperCase(letras[i]), 0);
		}
		
		char[] digitos = {'0','1','2','3','4','5','6','7','8','9'};
		for(int i=0; i<digitos.length; i++) {
			mapCaracterColumna.put(digitos[i], 1);
		}
		mapCaracterColumna.put('_', 2);
		mapCaracterColumna.put('+', 3);
		mapCaracterColumna.put('-', 3);
		mapCaracterColumna.put('*', 4);
		mapCaracterColumna.put('/', 4);
		mapCaracterColumna.put(':', 5);
		mapCaracterColumna.put('=', 6);
		mapCaracterColumna.put('<', 7);
		mapCaracterColumna.put('>', 8);
		mapCaracterColumna.put('.', 9);
		mapCaracterColumna.put('E', 10);
		mapCaracterColumna.put('#', 11);
		mapCaracterColumna.put('{', 12);
		mapCaracterColumna.put('}', 13);
		mapCaracterColumna.put('(', 14);
		mapCaracterColumna.put(')', 14);
		mapCaracterColumna.put(',', 14);
		mapCaracterColumna.put(';', 14);
		mapCaracterColumna.put(' ', 15);
		mapCaracterColumna.put('	', 15);//PREGUNTAR TAB
		mapCaracterColumna.put('\n', 16);//PREGUNTAR SALTO DE LINEA
		mapCaracterColumna.put('$', 17);
		
		//INICIALIZO TABLA DE SIMBOLOS CON PALABRAS RESERVADAS
		
		tablaSimbolos=new HashMap<String, HashMap<String, Object>>();
		tablaSimbolos.put("if", new HashMap<String, Object>());
		tablaSimbolos.get("if").put("Reservada", true);
		tablaSimbolos.put("else", new HashMap<String, Object>());
		tablaSimbolos.get("else").put("Reservada", true);
		tablaSimbolos.put("end_if", new HashMap<String, Object>());
		tablaSimbolos.get("end_if").put("Reservada", true);
		tablaSimbolos.put("print", new HashMap<String, Object>());
		tablaSimbolos.get("print").put("Reservada", true);
		tablaSimbolos.put("int", new HashMap<String, Object>());
		tablaSimbolos.get("int").put("Reservada", true);
		tablaSimbolos.put("begin", new HashMap<String, Object>());
		tablaSimbolos.get("begin").put("Reservada", true);
		tablaSimbolos.put("end", new HashMap<String, Object>());
		tablaSimbolos.get("end").put("Reservada", true);
		tablaSimbolos.put("float", new HashMap<String, Object>());
		tablaSimbolos.get("float").put("Reservada", true);
		tablaSimbolos.put("for", new HashMap<String, Object>());
		tablaSimbolos.get("for").put("Reservada", true);
		tablaSimbolos.put("class", new HashMap<String, Object>());
		tablaSimbolos.get("class").put("Reservada", true);
		tablaSimbolos.put("extends", new HashMap<String, Object>());
		tablaSimbolos.get("extends").put("Reservada", true);
		
		
		//INICIALIZO MATRIZ TRANSICION DE ESTADOS
		
		matrizTransicionEstados= new int[][] {//REHACER
			{1,2,-2,-1,-1,3,5,4,5,10,1,11,12,-2,-1,0,0,-1,-2},
			{1,1,1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,2,-1,-1,-1,-1,-1,-1,-1,6,-1,-1,-1,-1,-1,-1,-1,-1,-1},
			{-2,-2,-2,-2,-2,-2,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2},
			{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,6,-1,-1,-1,-1,-1,-1,-1,-1,7,-1,-1,-1,-1,-1,-1,-1,-1},
			{-2,9,-2,8,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2},
			{-2,9,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2},
			{-1,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
			{-2,6,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2},
			{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,11,11},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,-1,12,12,12,-2,12}
			};
			
		//INICIALIZO MATRIZ ACCIONES SEMANTICAS
		
		AccionSemantica as1 = new AccionSemantica1();
		AccionSemantica as2 = new AccionSemantica2();
		AccionSemantica as3 = new AccionSemantica3();
		AccionSemantica as4 = new AccionSemantica4();
		AccionSemantica as5 = new AccionSemantica5();
		AccionSemantica as6 = new AccionSemantica6();
		AccionSemantica as7 = new AccionSemantica7();
		AccionSemantica as8 = new AccionSemantica8();
		AccionSemantica as9 = new AccionSemantica9();
		AccionSemantica as10 = new AccionSemantica10();
		AccionSemantica as11 = new AccionSemantica11();
		AccionSemantica as12 = new AccionSemantica12();
		AccionSemantica as13 = new AccionSemantica13();
		AccionSemantica as14 = new AccionSemantica14();
		
		matrizAccionesSemanticas = new AccionSemantica[][] {
			{as2,as4,as13,as2,as2,as2,as2,as2,as2,as4,as2,null,as9,as13,as2,null,as8,as2,as14},/*Inicial*/
			{as5,as5,as5,as1,as1,as1,as1,as1,as1,as1,as5,as1,as1,as1,as1,as1,as1,as1,as1},/*ID*/
			{as3,as5,as3,as3,as3,as3,as3,as3,as3,as5,as3,as3,as3,as3,as3,as3,as3,as3,as3},/*CTE*/
			{as12,as12,as12,as12,as12,as12,as5,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12},/*:*/
			{as7,as7,as7,as7,as7,as7,as5,as7,as5,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},/*<*/
			{as7,as7,as7,as7,as7,as7,as5,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},/*=*/
			{as6,as5,as6,as6,as6,as6,as6,as6,as6,as6,as5,as6,as6,as6,as6,as6,as6,as6,as6},/*. de float*/
			{as12,as5,as12,as5,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12},/*E de float*/
			{as12,as5,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12},/*+- de E*/
			{as6,as5,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},/*E-+digito*/
			{as12,as5,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12,as12},/*. de float*/
			{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,as8,null,null},/*#*/
			{as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as11,as5,as5,as10,as12,as5}/*cadena multilinea*/
		};
	}
	
	public void disminuirContador() {
		if(contadorColumna!=0)
			contadorColumna--;
		else {
			contadorFila--;
			contadorColumna=allLines.get(contadorFila).length();
		}
	}
	
	public void aumentarContadorFila() {
		contadorFila++;
		contadorColumna = 0;
	}
	
	public int yylex() {//ESTADO -2 SIGNIFICA ERROR
		ultimoEstado=0;
		String token = null;
		while(ultimoEstado>-1) {
			char proximoCaracter;
			if(allLines.get(contadorFila).length() == contadorColumna) {
				proximoCaracter = '\n';
			}else {
				proximoCaracter=allLines.get(contadorFila).charAt(contadorColumna);
			}
			contadorColumna++;
			int columnaCaracter;
			if(mapCaracterColumna.containsKey(proximoCaracter)) {
				columnaCaracter=mapCaracterColumna.get(proximoCaracter);
			}else {
				columnaCaracter=18;
			}
			if(matrizAccionesSemanticas[ultimoEstado][columnaCaracter] != null) {
				token=matrizAccionesSemanticas[ultimoEstado][columnaCaracter].aplicar(proximoCaracter, this);
			}
			ultimoEstado=matrizTransicionEstados[ultimoEstado][columnaCaracter];
		}
		if(token != null && (token.equals("ID") || token.equals("CTE") || token.equals("CADENA"))) {
			//hacer yyval
			return codigosTokens.get(token);
		}else if(token != null && token.equals("ERROR")){
			return codigosTokens.get(token);
		}else {
			return codigosTokens.get(lexema.toString());
		}
	}

	public HashMap<String, Object> getAtributos() {
		return tablaSimbolos.get(lexema.toString());
	}
	
	public void agregarLexema() {
		tablaSimbolos.put(lexema.toString(), new HashMap<String,Object>());
		tablaSimbolos.get(lexema.toString()).put("Reservada", false);
	}
	
	public void inicializarLexema() {
		lexema = new StringBuilder();
	}
	
	public void agregarCaracter(char c) {
		lexema.append(c);
	}
	
	public StringBuilder getLexema() {
		return lexema;
	}
	
	public String getDatosTablaSimbolos() {
		StringBuilder texto =  new StringBuilder();
		for(String s : tablaSimbolos.keySet()) {
			texto.append(s+":\n");
			for(String prop : tablaSimbolos.get(s).keySet()) {
				texto.append("    "+prop+": "+tablaSimbolos.get(s).get(prop).toString()+"\n");
			}
		}
		return texto.toString();
	}

	public void agregarError(String string) {
		errores.add("Numero de linea: "+ (contadorFila+1) +" - "+ string);
	}
	
	public ArrayList<String> getErrores() {
		return errores;
	}
	
	
}
