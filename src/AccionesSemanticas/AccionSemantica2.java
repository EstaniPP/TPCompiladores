
public class AccionSemantica2 extends AccionSemantica{

	public AccionSemantica2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.inicializarLexema();
		al.agregarCaracter(c);
		return null;
	}
}