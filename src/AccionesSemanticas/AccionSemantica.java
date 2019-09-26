package AccionesSemanticas;
import AnalizadorLexico.AnalizadorLexico;

public abstract class AccionSemantica {

	public abstract String aplicar(char c, AnalizadorLexico al);
	
}
