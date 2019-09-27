package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica4 extends AccionSemantica{

	public AccionSemantica4() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.inicializarLexema();
		al.agregarCaracter(c);
		return null;
	}
}
