package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica14 extends AccionSemantica{

	public AccionSemantica14() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarError("Error : Caracter invalido \""+c+"\".");
		return "ERROR";
	}
}