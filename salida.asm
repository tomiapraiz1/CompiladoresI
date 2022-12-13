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
d dw ?
g dw ?
b dw ?
h dw ?
a dw ?
c dw ?
e dw ?
f dw ?
@aux2 dw ?
@aux1 dw ?
@aux0 dw ?
.code
START:
FNINIT
MOV AX, c
SUB AX, d
MOV @aux0, AX
MOV AX, e
MOV DX, e
MUL f
MOV @aux1, AX
MOV AX, h
CMP AX, 00h
JNE Label@aux2
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux2:
MOV AX, g
MOV DX, g
DIV h
MOV @aux2, AX
END START
