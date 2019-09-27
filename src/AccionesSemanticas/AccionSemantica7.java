package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica7 extends AccionSemantica{

	public AccionSemantica7() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.disminuirContador();
		return null;
	}
}
