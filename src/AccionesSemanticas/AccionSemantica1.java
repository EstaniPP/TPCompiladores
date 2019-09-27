package AccionesSemanticas;
import java.util.HashMap;

import Analizadores.AnalizadorLexico;

public class AccionSemantica1 extends AccionSemantica{

	public AccionSemantica1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		
		al.disminuirContador();
		HashMap<String, Object> atributos =al.getAtributos();
		if(atributos!=null){
			if((boolean)atributos.get("Reservada")) {
				return null;
			}
		}else {
			StringBuilder str = al.getLexema();
			if(str.length() > 25) {
				str.delete(25, str.length());
				al.agregarError("Warning : el identificador se trunco a 25 caracteres.");
			}
			al.agregarLexema();
		}
		return "ID";
	}
}