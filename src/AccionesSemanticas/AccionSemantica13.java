public class AccionSemantica13 extends AccionSemantica{

	public AccionSemantica13() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String aplicar(char c, AnalizadorLexico al) {
		al.agregarError("Error : Token invalido \""+al.getLexema()+"\".");
		return "ERROR";
	}
}