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
x dd ?
e dd ?
d dd ?
a dd ?
t dd ?
z dd ?
b dd ?
f dd ?
@aux0 dd ?
@aux1 dd ?
@aux2 dd ?
@aux3 dd ?
@aux4 dd ?
@aux5 dd ?
@aux6 dd ?
@aux7 dd ?
@aux8 dd ?
@aux9 dd ?
.code
START:
FNINIT
FILD 1
FSTP @aux0
FLD @aux0
FSTP a
FILD 1
FSTP @aux1
FLD @aux1
FSTP b
FILD 1
FSTP @aux2
FLD @aux2
FSTP z
FILD 1
FSTP @aux3
FLD @aux3
FSTP d
FILD 1
FSTP @aux4
FLD @aux4
FSTP e
FILD 1
FSTP @aux5
FLD @aux5
FSTP f
FLD b
FLD z
FMUL
FSTP @aux6
FLD a
FLD @aux6
FADD
FSTP @aux7
FLD e
FTST
JNE Label@aux8
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux8:
FLD d
FLD e
FDIV
FSTP @aux8
FLD @aux7
FLD @aux8
FADD
FSTP @aux9
FLD @aux9
FLD f
FADD
FSTP @aux10
FILD 20
FSTP @aux11
FLD @aux10
FLD @aux11
FADD
FSTP @aux12
FLD @aux12
FSTP x
END START
