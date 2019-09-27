package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public abstract class AccionSemantica {

	public abstract String aplicar(char c, AnalizadorLexico al);
	
}
