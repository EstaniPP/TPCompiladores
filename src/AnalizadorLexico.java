import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFileChooser;

public class AnalizadorLexico {
	
	private int contadorFila;
	private int contadorColumna;
	private int ultimoEstado;
	private File archivo;
	private StringBuilder lexema;
	
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
		
		//JFILECHOOSER
		
		JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivo = fc.getSelectedFile();
        }
        
        //LEO TODAS LAS LINEAS DEL ARCHIVO
        
		try {
			allLines = (ArrayList<String>) Files.readAllLines(Paths.get(archivo.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ASIGNO CODIGO DE TOKEN
		
		codigosTokens=new HashMap<String, Integer>();
		codigosTokens.put("ID", 0);
		codigosTokens.put("CTE", 1);
		codigosTokens.put("if", 2);
		codigosTokens.put("else", 3);
		codigosTokens.put("end_if", 4);
		codigosTokens.put("print", 5);
		codigosTokens.put("int", 6);
		codigosTokens.put("begin", 7);
		codigosTokens.put("end", 8);
		codigosTokens.put("float", 9);
		codigosTokens.put("for", 10);
		codigosTokens.put("class", 11);
		codigosTokens.put("extends", 12);
		codigosTokens.put("+", 13);
		codigosTokens.put("-", 14);
		codigosTokens.put("*", 15);
		codigosTokens.put("/", 16);
		codigosTokens.put(":=", 17);
		codigosTokens.put(">=", 18);
		codigosTokens.put("<=", 19);
		codigosTokens.put(">", 20);
		codigosTokens.put("<", 21);
		codigosTokens.put("==", 22);
		codigosTokens.put("<>", 23);
		codigosTokens.put("(", 24);
		codigosTokens.put(")", 25);
		codigosTokens.put(",", 26);
		codigosTokens.put(";", 27);
		codigosTokens.put("$", 28);
				
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
		//FILA 18 EN CASO DE QUE NO CONTAINS LA KEY(OTRO).
		
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
			{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,-1,11},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,-1,12,12,12,-1,12}
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
		matrizAccionesSemanticas = new AccionSemantica[][] {
			{as2,as4,null,as2,as2,as2,as2,as2,as2,as4,as2,as2,as9,null,as2,null,as8,as2,null},
			{as3,as3,as3,as1,as1,as1,as1,as1,as1,as1,as3,as1,as1,as1,as1,as1,as12,as1,as1},
			{as6,as5,as6,as6,as6,as6,as6,as6,as6,as5,as6,as6,as6,as6,as6,as6,as8,as6,as6},
			{null,null,null,null,null,null,as3,null,null,null,null,null,null,null,null,null,null,null,as2,null},
			{as7,as7,as7,as7,as7,as7,as3,as7,as3,as7,as7,as7,as7,as7,as7,as7,as8,as2,as7},
			{as7,as7,as7,as7,as7,as7,as3,as7,as7,as7,as7,as7,as7,as7,as7,as7,as8,as2,as7},
			{as6,as5,as6,as6,as6,as6,as6,as6,as6,as6,as5,as6,as6,as6,as6,as6,as8,as2,as6},
			{null,as5,null,as5,null,null,null,null,null,null,null,null,null,null,null,null,null,null,as2,null},
			{null,as5,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,as2,null},
			{as6,as5,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as8,as2,as6},
			{null,as5,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,as2,null},
			{null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,as8,as2,null},
			{as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as11,as5,as5,as10,as2,as5}
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
	
	public int getToken() {//ESTADO -2 SIGNIFICA ERROR
		ultimoEstado=0;
		String token = null;
		while(ultimoEstado>-1) {
			char proximoCaracter=allLines.get(contadorFila).charAt(contadorColumna);
			System.out.println("Fila: "+contadorFila+" Columna:"+ contadorColumna+" Caracter: "+proximoCaracter);
			contadorColumna++;
			int columnaCaracter=mapCaracterColumna.get(proximoCaracter);
			if(matrizAccionesSemanticas[ultimoEstado][columnaCaracter] != null) {
				token=matrizAccionesSemanticas[ultimoEstado][columnaCaracter].aplicar(proximoCaracter, this);
			}
			ultimoEstado=matrizTransicionEstados[ultimoEstado][columnaCaracter];
		}
		if(ultimoEstado == -2) {
			System.out.println("ERROR");
		}else {
			if(token != null && token.equals("ID")) {
				return codigosTokens.get("ID");
			}else if(token != null &&  token.equals("CTE")) {
				return codigosTokens.get("CTE");
			}else {
				return codigosTokens.get(lexema.toString());
			}
		}
		return -1;
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
	
	
}
