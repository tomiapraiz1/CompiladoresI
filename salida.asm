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
e dd ?
c dw ?
d dd ?
a dw ?
b dw ?
f dd ?
@aux0 dw ?
@aux1 dw ?
@aux2 dw ?
@aux3 dw ?
@aux4 dw ?
@aux5 dw ?
@aux6 dw ?
@aux7 dw ?
@aux8 dw ?
@aux9 dw ?
.code
START:
MOV AX, a
ADD AX, b
MOV @aux0, AX
FLD @aux0
FSTP f
END START
