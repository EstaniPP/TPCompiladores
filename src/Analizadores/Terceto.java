package Analizadores;

import java.util.ArrayList;
import java.util.HashMap;

public class Terceto {
	public String operador;
	public String operando1;
	public String operando2;
	public String tipo;
	public String variableAuxiliar;
	public static ArrayList<String> codigo = new ArrayList<String>();
	public static AnalizadorLexico al;
	public static int contadorVariableAuxiliar = 0;
	
	public Terceto(String operador,String operando1,String operando2, String tipo) {
		this.operador = operador;
		this.operando1 = operando1;
		this.operando2 = operando2;
		this.tipo = tipo;
		this.variableAuxiliar = "_var" + contadorVariableAuxiliar++;
	}
	public static void crearCodigo(ArrayList<Terceto> tercetos) {
		for(Terceto t: tercetos) {
			if(t.operador.equals("+")) {
				
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _var"+t.operando1.substring(1,t.operando1.length()-1));
					}else {
						codigo.add("MOV AX, "+t.operando1);	
					}
					if(t.operando2.contains("[")){
						codigo.add("ADD AX, _var"+t.operando2.substring(1,t.operando2.length()-1));
					}else {
						codigo.add("ADD AX, "+t.operando2);
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV "+t.variableAuxiliar+", AX");
				}else {
					
				}
			}else if(t.operador.equals("-")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _var"+t.operando1.substring(1,t.operando1.length()-1));
					}else {
						codigo.add("MOV AX, "+t.operando1);	
					}
					if(t.operando2.contains("[")){
						codigo.add("SUB AX, _var"+t.operando2.substring(1,t.operando2.length()-1));
					}else {
						codigo.add("SUB AX, "+t.operando2);
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV "+t.variableAuxiliar+", AX");
				}else {
					
				}
			}else if(t.operador.equals("*")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _var"+t.operando1.substring(1,t.operando1.length()-1));
					}else {
						codigo.add("MOV AX, "+t.operando1);	
					}
					if(t.operando2.contains("[")){
						codigo.add("IMUL AX, _var"+t.operando2.substring(1,t.operando2.length()-1));
					}else {
						codigo.add("IMUL AX, "+t.operando2);
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV "+t.variableAuxiliar+", AX");
				}else {
					
				}
			}else if(t.operador.equals("/")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _var"+t.operando1.substring(1,t.operando1.length()-1));
					}else {
						codigo.add("MOV AX, "+t.operando1);	
					}
					codigo.add("CWD");
					if(t.operando2.contains("[")){
						codigo.add("MOV BX, _var"+t.operando2.substring(1,t.operando2.length()-1));
					}else {
						codigo.add("MOV BX, "+t.operando2);
					}
					codigo.add("IDIV BX");
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV "+t.variableAuxiliar+", AX");
				}else {
					
				}
			}else if(t.operador.equals(":=")) {
				if(t.tipo.equals("int")) {
					if(t.operando2.contains("[")){
						codigo.add("MOV AX, _var"+t.operando2.substring(1,t.operando2.length()-1));
					}else {
						codigo.add("MOV AX, "+t.operando2);
					}
					codigo.add("MOV "+t.operando1 +", AX");
				}else {
					
				}
			}else if(t.operador.equals("METODO")) {
				codigo.add(t.operando1+":");
			}else if(t.operador.equals("TERMINA_METODO")) {
				codigo.add("RET");
				codigo.add("END");
			}else if(t.operador.equals("CALL")) {
				codigo.add("CALL "+t.operando1);
			}else if(t.operador.equals("CMP")) {
				if(t.operando1.contains("[")){
					codigo.add("MOV AX, _var"+t.operando1.substring(1,t.operando1.length()-1));
				}else {
					codigo.add("MOV AX, "+t.operando1);	
				}
				if(t.operando2.contains("[")){
					codigo.add("CMP BX, _var"+t.operando2.substring(1,t.operando2.length()-1));
				}else {
					codigo.add("CMP BX, "+t.operando2);
				}
			}else if(t.operador.equals("JG")) {
				codigo.add("JG Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("JGE")) {
				codigo.add("JGE Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("JL")) {
				codigo.add("JL Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("JLE")) {
				codigo.add("JLE Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("JE")) {
				codigo.add("JE Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("JNE")) {
				codigo.add("JNE Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("BI")) {
				codigo.add("JMP Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("LABEL")){
				codigo.add(t.operando1+":");
			}else if(t.operador.equals("END_PROGRAMA")) {
				codigo.add("END INICIO");
			}else if(t.operador.equals("OVERFLOW_FLOAT")) {
			
			}else if(t.operador.equals("OVERFLOW_INT")) {
			
			}else if(t.operador.equals("DIVISIONCERO")) {
			
			}else if(t.operador.equals("PRINT")) {
			
			}
		}
	}
	
	@Override
	public String toString() {
		return new String("("+operador+","+operando1+","+operando2+","+tipo+")");
	}
	
}
