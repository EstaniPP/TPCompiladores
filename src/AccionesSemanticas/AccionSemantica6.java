package AccionesSemanticas;
import java.math.BigDecimal;

import AnalizadorLexico.AnalizadorLexico;

public class AccionSemantica6 extends AccionSemantica{

	public AccionSemantica6() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.disminuirContador();
		BigDecimal bd = new BigDecimal(al.getLexema().toString());
		if((new BigDecimal("1.17549435E-38").compareTo(bd)<=0 && new BigDecimal("3.40282347E+38").compareTo(bd)>=0) || new BigDecimal("0.0").compareTo(bd)==0) {
			if(al.getAtributos()==null) {
				al.agregarLexema();
			}
			return "CTE";
		}
		al.agregarError("Error : constante float fuera de rango.");
		return "ERROR";
	}
}
