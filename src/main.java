import java.io.FileNotFoundException;

import Analizadores.AnalizadorLexico;
import jdk.nashorn.internal.parser.Parser;

public class main {

	public static void main(String[] args) throws FileNotFoundException {
		
		AnalizadorLexico a=new AnalizadorLexico();
		int token = 0;
		while(token != 28) {
			token = a.yylex();
			System.out.println("Lexema " + a.getLexema() + " token "+ token);
		}
		for(String s : a.getErrores()) {System.out.println(s);}
	}

}
