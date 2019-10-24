package Analizadores;

public class Terceto {
	public String operador;
	public String operando1;
	public String operando2;
	public String tipo;
	
	public Terceto(String operador,String operando1,String operando2, String tipo) {
		this.operador = operador;
		this.operando1 = operando1;
		this.operando2 = operando2;
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return new String("("+operador+","+operando1+","+operando2+","+tipo+")");
	}
	
}
