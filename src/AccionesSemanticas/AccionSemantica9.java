package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica9 extends AccionSemantica{

	public AccionSemantica9() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.inicializarLexema();
		return null;
	}
}
