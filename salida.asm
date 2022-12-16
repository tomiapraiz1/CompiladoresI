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
@auxf0 dd 1.0
_c dd ?
_a dd ?
_b dd ?
@aux1 dd ?
@aux0 dd ?
.code
START:
FNINIT
FLD @auxf0
FSTP _b
FLD 1.0
FLD 1.0
FSUB
FSTP @aux0
FLD @aux0
FSTP _c
FLD _c
FTST
JNE Label@aux1
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux1:
FLD _b
FLD _c
FDIV
FSTP @aux1
FLD @aux1
FSTP _a
END START
