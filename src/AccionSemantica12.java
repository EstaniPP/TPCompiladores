import java.util.HashMap;

public class AccionSemantica12 extends AccionSemantica{

	public AccionSemantica12() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		HashMap<String, Object> atributos =al.getAtributos();
		if(atributos!=null){
			if((boolean)atributos.get("Reservada")) {
				return null;
			}
		}else {
			StringBuilder str = al.getLexema();
			if(str.length() > 25) {
				str.delete(25, str.length());
			}
			al.agregarLexema();
		}
		al.aumentarContadorFila();
		return "ID";
	}
}