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
	public static void crearCodigo(ArrayList<Terceto> tercetos, HashMap<String,HashMap<String,Object>> tablaSimbolos) {
		for(Terceto t: tercetos) {
			if(t.operador.equals("+")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("MOV AX, _"+t.operando1);		
						}else {
							codigo.add("MOV AX, "+t.operando1);		
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("ADD AX, _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("ADD AX, _"+t.operando2);		
						}else {
							codigo.add("ADD AX, "+t.operando2);		
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV _"+t.variableAuxiliar+", AX");
				}else {
					if(t.operando1.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando1);		
						}else {
							String nuevoOp = t.operando1.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("FADD _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("FADD _"+t.operando2);		
						}else {
							String nuevoOp = t.operando2.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FADD _"+nuevoOp);				
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("CALL CHECKOVERFLOWFLOAT");
					codigo.add("FSTP _"+t.variableAuxiliar);
				}
			}else if(t.operador.equals("-")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("MOV AX, _"+t.operando1);		
						}else {
							codigo.add("MOV AX, "+t.operando1);		
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("SUB AX, _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("SUB AX, _"+t.operando2);		
						}else {
							codigo.add("SUB AX, "+t.operando2);		
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV _"+t.variableAuxiliar+", AX");
				}else {
					if(t.operando1.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando1);		
						}else {
							String nuevoOp = t.operando1.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("FSUB _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("FSUB _"+t.operando2);		
						}else {
							String nuevoOp = t.operando2.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FSUB _"+nuevoOp);				
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("CALL CHECKOVERFLOWFLOAT");
					codigo.add("FSTP _"+t.variableAuxiliar);
				}
			}else if(t.operador.equals("*")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("MOV AX, _"+t.operando1);		
						}else {
							codigo.add("MOV AX, "+t.operando1);		
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("IMUL AX, _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("IMUL AX, _"+t.operando2);		
						}else {
							codigo.add("IMUL AX, "+t.operando2);		
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV _"+t.variableAuxiliar+", AX");
				}else {
					if(t.operando1.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando1);		
						}else {
							String nuevoOp = t.operando1.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando2);		
						}else {
							String nuevoOp = t.operando2.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("FMUL");
					codigo.add("CALL CHECKOVERFLOWFLOAT");
					codigo.add("FSTP _"+t.variableAuxiliar);
				}
			}else if(t.operador.equals("/")) {
				if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("MOV AX, _"+t.operando1);		
						}else {
							codigo.add("MOV AX, "+t.operando1);		
						}
					}
					codigo.add("CWD");
					if(t.operando2.contains("[")){
						codigo.add("MOV BX, _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("MOV BX, _"+t.operando2);		
						}else {
							codigo.add("MOV BX, "+t.operando2);		
						}
					}
					codigo.add("CMP BX, 0");
					codigo.add("JE DIVISIONCERO");
					codigo.add("IDIV BX");
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("JO OVERFLOW_INT");
					codigo.add("MOV _"+t.variableAuxiliar+", AX");
				}else {
					if(t.operando1.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando1);		
						}else {
							String nuevoOp = t.operando1.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando2);		
						}else {
							String nuevoOp = t.operando2.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					al.getTablaSimbolos().put(t.variableAuxiliar,new HashMap<String,Object>());
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Tipo",t.tipo);
					al.getTablaSimbolos().get(t.variableAuxiliar).put("Uso","variable");
					codigo.add("FLD ceroFloat");
					codigo.add("FCOMP");
					codigo.add("FSTSW AX");
					codigo.add("SAHF");
					codigo.add("JE DIVISIONCERO");
					codigo.add("FDIV");
					codigo.add("CALL CHECKOVERFLOWFLOAT");
					codigo.add("FSTP _"+t.variableAuxiliar);
					//HASTA ACA ANDA
				}
			}else if(t.operador.equals(":=")) {
				if(t.tipo.equals("int")) {
					if(t.operando2.contains("[")){
						codigo.add("MOV AX, _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("MOV AX, _"+t.operando2);		
						}else {
							codigo.add("MOV AX, "+t.operando2);		
						}
					}
					codigo.add("MOV _"+t.operando1 +", AX");
				}else {
					if(t.operando2.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando2);		
						}else {
							String nuevoOp = t.operando2.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);		
						}
					}
					codigo.add("FSTP _"+t.operando1);
				}
			}else if(t.operador.equals("METODO")) {
				codigo.add(t.operando1+":");
			}else if(t.operador.equals("TERMINA_METODO")) {
				codigo.add("RET");
			}else if(t.operador.equals("CALL")) {
				codigo.add("CALL "+t.operando1);
			}else if(t.operador.equals("CMP")) {
					if(t.tipo.equals("int")) {
					if(t.operando1.contains("[")){
						codigo.add("MOV AX, _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("MOV AX, _"+t.operando1);		
						}else {
							codigo.add("MOV AX, "+t.operando1);		
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("CMP AX, _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("CMP AX, _"+t.operando2);		
						}else {
							codigo.add("CMP AX, "+t.operando2);		
						}
					}	
					tercetos.get(contadorVariableAuxiliar-1).tipo="int";
				}else{	
					codigo.add("FINIT");
					if(t.operando1.contains("[")){
						codigo.add("FLD _"+tercetos.get(Integer.parseInt(t.operando1.substring(1,t.operando1.length()-1))).variableAuxiliar);
					}else {
						if(t.operando1.charAt(0)>='a' && t.operando1.charAt(0)<='z') {
							codigo.add("FLD _"+t.operando1);		
						}else {
							String nuevoOp = t.operando1.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FLD _"+nuevoOp);				
						}
					}
					if(t.operando2.contains("[")){
						codigo.add("FCOM _"+tercetos.get(Integer.parseInt(t.operando2.substring(1,t.operando2.length()-1))).variableAuxiliar);
					}else {
						if(t.operando2.charAt(0)>='a' && t.operando2.charAt(0)<='z') {
							codigo.add("FCOM _"+t.operando2);		
						}else {
							String nuevoOp = t.operando2.replaceFirst("[.]","_").replaceFirst("-","@");
							codigo.add("FCOM _"+nuevoOp);				
						}
					}
					codigo.add("FSTSW AX");
					codigo.add("SAHF");
					tercetos.get(contadorVariableAuxiliar-1).tipo="float";
				}
			}else if(t.operador.equals("JG")) {
				if(tercetos.get(contadorVariableAuxiliar-1).tipo.equals("float")) {
					codigo.add("JA Label"+t.operando2.substring(1,t.operando2.length()-1));
				}else {
					codigo.add("JG Label"+t.operando2.substring(1,t.operando2.length()-1));
				}
			}else if(t.operador.equals("JGE")) {
				if(tercetos.get(contadorVariableAuxiliar-1).tipo.equals("float")) {
					codigo.add("JAE Label"+t.operando2.substring(1,t.operando2.length()-1));
				}else {
					codigo.add("JGE Label"+t.operando2.substring(1,t.operando2.length()-1));
				}
			}else if(t.operador.equals("JL")) {
				if(tercetos.get(contadorVariableAuxiliar-1).tipo.equals("float")) {
					codigo.add("JB Label"+t.operando2.substring(1,t.operando2.length()-1));
				}else {
					codigo.add("JL Label"+t.operando2.substring(1,t.operando2.length()-1));
				}
			}else if(t.operador.equals("JLE")) {
				if(tercetos.get(contadorVariableAuxiliar-1).tipo.equals("float")) {
					codigo.add("JBE Label"+t.operando2.substring(1,t.operando2.length()-1));
				}else {
					codigo.add("JLE Label"+t.operando2.substring(1,t.operando2.length()-1));
				}
			}else if(t.operador.equals("JE")) {
				codigo.add("JE Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("JNE")) {
				codigo.add("JNE Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("BI")) {
				codigo.add("JMP Label"+t.operando2.substring(1,t.operando2.length()-1));
			}else if(t.operador.equals("LABEL")){
				codigo.add(t.operando1+":");
			}else if(t.operador.equals("END_PROGRAMA")) {
				codigo.add("ENDSTART:");
				codigo.add("END START");
			}else if(t.operador.equals("OVERFLOW_FLOAT")) {
				codigo.add("OVERFLOW_FLOAT:");
				codigo.add("FINIT");
				codigo.add("invoke StdOut, addr @OVERFLOWFLOAT");
				codigo.add("JMP ENDSTART");
			}else if(t.operador.equals("OVERFLOW_INT")) {
				codigo.add("OVERFLOW_INT:");
				codigo.add("invoke StdOut, addr @OVERFLOWINT");
				codigo.add("JMP ENDSTART");
			}else if(t.operador.equals("DIVISIONCERO")) {
				codigo.add("DIVISIONCERO:");
				codigo.add("invoke StdOut, addr @DIVISIONCERO");
				codigo.add("JMP ENDSTART");
			}else if(t.operador.equals("PRINT")) {
				//codigo.add("print addr _"+tablaSimbolos.get(t.operando1).get("NombreString")+", 13, 10");
				//codigo.add("invoke StdOut, addr _"+tablaSimbolos.get(t.operando1).get("NombreString"));
				codigo.add("invoke MessageBox, NULL,addr _"+tablaSimbolos.get(t.operando1).get("NombreString")+",addr _"+tablaSimbolos.get(t.operando1).get("NombreString")+", MB_OK");
			}else if(t.operador.equals("CHECKOVERFLOWFLOAT")) {
				codigo.add("CHECKOVERFLOWFLOAT:");
				codigo.add("FST aux");
				codigo.add("FINIT");
				codigo.add("FLD aux");
				codigo.add("FCOM maximoPositivo");
				codigo.add("FSTSW AX");
				codigo.add("SAHF");
				codigo.add("JA OVERFLOW_FLOAT");
				codigo.add("FINIT");
				codigo.add("FLD aux");
				codigo.add("FCOM minimoPositivo");
				codigo.add("FSTSW AX");
				codigo.add("SAHF");
				codigo.add("JA OKFLOAT");
				codigo.add("FINIT");
				codigo.add("FLD aux");
				codigo.add("FCOM maximoNegativo");
				codigo.add("FSTSW AX");
				codigo.add("SAHF");
				codigo.add("JB OVERFLOW_FLOAT");
				codigo.add("FINIT");
				codigo.add("FLD aux");
				codigo.add("FCOM minimoNegativo");
				codigo.add("FSTSW AX");
				codigo.add("SAHF");
				codigo.add("JB OKFLOAT");
				codigo.add("FINIT");
				codigo.add("FLD aux");
				codigo.add("FCOM ceroFloat");
				codigo.add("FSTSW AX");
				codigo.add("SAHF");
				codigo.add("JE OKFLOAT");
				codigo.add("JMP OVERFLOW_FLOAT");
				codigo.add("OKFLOAT:");
				codigo.add("RET");
			}
		}
	}
	
	@Override
	public String toString() {
		return new String("("+operador+","+operando1+","+operando2+","+tipo+","+variableAuxiliar+")");
	}
	
}
