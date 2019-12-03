.386
.model flat, stdcall
include \masm32\include\masm32rt.inc
.data
__var34 DD ?
_6_9 DD 6.9
__var33 DD ?
_@1252_97059 DD -1252.97059
__var36 DD ?
__var35 DD ?
_@95_5 DD -95.5
_idHijo DD ?
_objC@idHijo2 DW ?
_id DW ?
_45_0 DD 45.0
__var9 DD ?
_objC@suma2 DD ?
_suma_parcial DD ?
_idHijo2 DW ?
_suma_total DD ?
_str0 DB "-Asigne ID ",0
_10_5 DD 10.5
_1_0 DD 1.0
_str1 DB "Suma parcial 2 es mayor",0
_12_0 DD 12.0
_5_0 DD 5.0
_3_4 DD 3.4
_str3 DB "Mal",0
_suma DD ?
__var16 DD ?
_contador2 DW ?
_str2 DB "Bien",0
_objB@id DW ?
_contador DW ?
_objB@suma DD ?
__var27 DW ?
_objA@id DW ?
_suma2 DD ?
_suma_parcial2 DD ?
_objB@idHijo DD ?
minimoPositivo DQ 1.17549435E-38
minimoNegativo DQ -1.17549435E-38
maximoPositivo DQ 3.40282347E38
maximoNegativo DQ -3.40282347E38
ceroFloat DQ 0.0
aux DQ ?
@OVERFLOWFLOAT DB "Error overflow float",0
@OVERFLOWINT DB "Error overflow int",0
@DIVISIONCERO DB "Error division por 0",0
.code
CHECKOVERFLOWFLOAT:
FST aux
FINIT
FLD aux
FCOM maximoPositivo
FSTSW AX
SAHF
JA OVERFLOW_FLOAT
FINIT
FLD aux
FCOM minimoPositivo
FSTSW AX
SAHF
JA OKFLOAT
FINIT
FLD aux
FCOM maximoNegativo
FSTSW AX
SAHF
JB OVERFLOW_FLOAT
FINIT
FLD aux
FCOM minimoNegativo
FSTSW AX
SAHF
JB OKFLOAT
FINIT
FLD aux
FCOM ceroFloat
FSTSW AX
SAHF
JE OKFLOAT
JMP OVERFLOW_FLOAT
OKFLOAT:
RET
OVERFLOW_FLOAT:
FINIT
invoke StdOut, addr @OVERFLOWFLOAT
JMP ENDSTART
OVERFLOW_INT:
invoke StdOut, addr @OVERFLOWINT
JMP ENDSTART
DIVISIONCERO:
invoke StdOut, addr @DIVISIONCERO
JMP ENDSTART
asignarId:
MOV AX, 0
MOV _id, AX
invoke MessageBox, NULL, _str0, _str0, MB_OK
RET
sumar_DividirId:
FLD _suma_parcial
FADD _idHijo
CALL CHECKOVERFLOWFLOAT
FSTP __var9
FLD __var9
FSTP _suma
MOV AX, 0
MOV _id, AX
RET
START:
FLD _6_9
FSTP _objB@idHijo
FLD _5_0
FSTP _suma_parcial
FLD _10_5
FLD _suma_parcial
FMUL
CALL CHECKOVERFLOWFLOAT
FSTP __var16
FLD __var16
FSTP _suma_parcial2
FINIT
FLD _suma_parcial2
FCOM _suma_parcial
FSTSW AX
SAHF
JBE Label21
invoke MessageBox, NULL, _str1, _str1, MB_OK
Label21:
MOV AX, 0
MOV _contador, AX
Label23:
MOV AX, _contador
CMP AX, 10
JGE Label32
MOV AX, _objA@id
MOV _id, AX
CALL asignarId
MOV AX, _id
MOV _objA@id, AX
MOV AX, _contador
ADD AX, 1
JO OVERFLOW_INT
MOV __var27, AX
MOV AX, __var27
MOV _contador, AX
JMP Label23
Label32:
FLD _@95_5
FLD _3_4
FLD ceroFloat
FCOMP
FSTSW AX
SAHF
JE DIVISIONCERO
FDIV
CALL CHECKOVERFLOWFLOAT
FSTP __var33
FLD __var33
FLD _45_0
FMUL
CALL CHECKOVERFLOWFLOAT
FSTP __var34
FLD _12_0
FADD __var34
CALL CHECKOVERFLOWFLOAT
FSTP __var35
FLD __var35
FSUB _1_0
CALL CHECKOVERFLOWFLOAT
FSTP __var36
FLD __var36
FSTP _suma_parcial
FINIT
FLD _suma_parcial
FCOM _@1252_97059
FSTSW AX
SAHF
JNE Label42
invoke MessageBox, NULL, _str2, _str2, MB_OK
JMP Label44
Label42:
invoke MessageBox, NULL, _str3, _str3, MB_OK
Label44:
ENDSTART:
END START
