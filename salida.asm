.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data

DIVISIONPORCERO db "Error division por cero", 0
OVERFLOW db "Error overflow de suma", 0
INVOCACION db "Error la funcion no puede volver a ser invocada", 0
@auxDivision dw ?
@aux4 dd ?
d dd ?
t dd ?
z dd ?
b dd ?
x dd ?
e dd ?
a dd ?
@aux3 dd ?
@aux2 dd ?
@aux1 dd ?
f dd ?
@aux0 dd ?
.code
START:
FNINIT
FLD b
FLD z
FMUL
FSTP @aux0
FLD a
FLD @aux0
FADD
FSTP @aux1
FLD e
FTST
JNE Label@aux2
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux2:
FLD d
FLD e
FDIV
FSTP @aux2
FLD @aux1
FLD @aux2
FADD
FSTP @aux3
FLD @aux3
FLD f
FADD
FSTP @aux4
FLD @aux4
FSTP x
END START
