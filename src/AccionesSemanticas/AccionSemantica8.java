package AccionesSemanticas;
import AnalizadorLexico.AnalizadorLexico;

public class AccionSemantica8 extends AccionSemantica{

	public AccionSemantica8() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.aumentarContadorFila();
		return null;
	}
}
