package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica11 extends AccionSemantica{

	public AccionSemantica11() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarLexema();
		return "CADENA";
	}
}