package AccionesSemanticas;
import Analizadores.AnalizadorLexico;

public class AccionSemantica11 extends AccionSemantica{
	public static int cont = 0;
	public AccionSemantica11() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarLexema();
		al.agregarAtributoLexema(al.getLexema().toString(), "Tipo", "cadena");
		al.agregarAtributoLexema(al.getLexema().toString(), "NombreString", "str"+(cont++));
		return "CADENA";
	}
}