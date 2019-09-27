package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica10 extends AccionSemantica{

	public AccionSemantica10() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarCaracter(' ');
		al.aumentarContadorFila();
		return null;
	}
}
