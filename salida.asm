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
e dw ?
d dw ?
a dw ?
z dw ?
b dw ?
f dw ?
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
MOV AX, b
CMP AX, 00h
JNE Label@aux0
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux0:
MOV AX, a
DIV b
MOV @aux0, AX
MOV AX, d
MUL e
MOV @aux1, AX
MOV AX, @aux0
ADD AX, @aux1
MOV @aux2, AX
MOV AX, @aux2
SUB AX, f
MOV @aux3, AX
MOV AX, @aux3
ADD AX, 20
MOV @aux4, AX
MOV AX, @aux4
MOV z, AX
END START
