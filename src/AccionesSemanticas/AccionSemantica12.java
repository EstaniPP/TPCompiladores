package AccionesSemanticas;
import java.util.HashMap;

import Analizadores.AnalizadorLexico;

public class AccionSemantica12 extends AccionSemantica{

	public AccionSemantica12() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.disminuirContador();
		al.agregarError("Error : Token invalido \""+al.getLexema()+"\".");
		return "ERROR";
	}
}