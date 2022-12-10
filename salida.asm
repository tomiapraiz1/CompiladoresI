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
y dd ?
w dd ?
z dd ?
@aux1 dd ?
@aux0 dd ?
.code
START:
FNINIT
FLD y
FTST
JNE Label@aux0
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux0:
FLD x
FLD y
FDIV
FSTP @aux0
FLD @aux0
FLD z
FADD
FSTP @aux1
FLD @aux1
FSTP w
END START
