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
@auxf1 dd 1.1
@auxf0 dd 2.2
a dd ?
z dd ?
b dd ?
@aux0 dd ?
.code
START:
FNINIT
FLD @auxf0
FSTP a
FLD b
FLD @auxf1
FADD
FSTP @aux0
FLD @aux0
FSTP a
END START
