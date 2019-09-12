import java.util.HashMap;

public class AccionSemantica1 extends AccionSemantica{

	public AccionSemantica1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		
		al.disminuirContador();
		HashMap<String, Object> atributos =al.getAtributos();
		if(atributos!=null){
			if((boolean)atributos.get("Reservada")) {
				
			}
		}
	}
}