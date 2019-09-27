package AccionesSemanticas;
import java.math.BigDecimal;

import Analizadores.AnalizadorLexico;

public class AccionSemantica3 extends AccionSemantica{

	public AccionSemantica3() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.disminuirContador();
		BigDecimal bd = new BigDecimal(al.getLexema().toString());
		if(new BigDecimal("32768").compareTo(bd)>=0) {
			if(al.getAtributos()==null) {
				al.agregarLexema();
			}
			return "CTE";
		}
		al.agregarError("Error : constante entera fuera de rango.");
		return "ERROR";
	}
}
