
public class AccionSemantica5 extends AccionSemantica{

	public AccionSemantica5() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarCaracter(c);
		return null;
	}
}
