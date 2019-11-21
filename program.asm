.386
.model flat, stdcall
include \masm32\include\masm32rt.inc
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
__var35 DD ?
__var17 DD ?
_@200_1 DD -200.1
_str6 DB "bien4",0
_1000000_0 DD 1000000.0
_str2 DB "bien2",0
_str4 DB "bien3",0
_str0 DB "bien1",0
_@10_0 DD -10.0
__var45 DD ?
_a DD ?
__var8 DD ?
_b DD ?
_c DD ?
_d DD ?
__var26 DD ?
_str5 DB "mal3",0
_str3 DB "mal2",0
_str1 DB "mal1",0
_1_0E38 DD 1.0E38
_str7 DB "mal4",0
_1002_5 DD 1002.5
_2_0 DD 2.0
_@5002_5 DD -5002.5
_1000_5 DD 1000.5
_@5_0 DD -5.0
minimoPositivo DQ 1.17549435E-38
minimoNegativo DQ -1.17549435E-38
maximoPositivo DQ 3.40282347E38
maximoNegativo DQ -3.40282347E38
ceroFloat DD 0.0
aux DD ?
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
FCOM maximoNegativo
FINIT
FLD aux
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
invoke StdOut, addr @OVERFLOWFLOAT
JMP ENDSTART
OVERFLOW_INT:
invoke StdOut, addr @OVERFLOWINT
JMP ENDSTART
DIVISIONCERO:
invoke StdOut, addr @DIVISIONCERO
JMP ENDSTART
START:
FLD _@5_0
FSTP _a
FLD _2_0
FSTP _b
FLD _1000_5
FSTP _c
FLD _a
FLD _b
FMUL
CALL CHECKOVERFLOWFLOAT
FSTP __var8
FLD __var8
FSTP _d
FINIT
FLD _d
FCOM _@10_0
FSTSW AX
SAHF
JNE Label14
invoke StdOut, addr _str0
JMP Label16
Label14:
invoke StdOut, addr _str1
Label16:
FLD _a
FLD _c
FMUL
CALL CHECKOVERFLOWFLOAT
FSTP __var17
FLD __var17
FSTP _d
FINIT
FLD _d
FCOM _@5002_5
FSTSW AX
SAHF
JNE Label23
invoke StdOut, addr _str2
JMP Label25
Label23:
invoke StdOut, addr _str3
Label25:
FLD _c
FLD _b
FADD
CALL CHECKOVERFLOWFLOAT
FSTP __var26
FLD __var26
FSTP _d
FINIT
FLD _d
FCOM _1002_5
FSTSW AX
SAHF
JNE Label32
invoke StdOut, addr _str4
JMP Label34
Label32:
invoke StdOut, addr _str5
Label34:
FLD _c
FLD _a
FLD ceroFloat
FCOMP
FSTSW AX
SAHF
JE DIVISIONCERO
FDIV
FSTP __var35
FLD __var35
FSTP _d
FINIT
FLD _d
FCOM _@200_1
FSTSW AX
SAHF
JNE Label41
invoke StdOut, addr _str6
JMP Label43
Label41:
invoke StdOut, addr _str7
Label43:
FLD _1000000_0
FSTP _d
FLD _d
FLD _1_0E38
FMUL
CALL CHECKOVERFLOWFLOAT
FSTP __var45
FLD __var45
FSTP _d
ENDSTART:
END START
