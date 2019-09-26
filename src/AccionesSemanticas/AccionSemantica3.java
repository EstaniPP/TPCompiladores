
public class AccionSemantica3 extends AccionSemantica{

	public AccionSemantica3() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarCaracter(c);
		return null;
	}
}
