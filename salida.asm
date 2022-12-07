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
@aux5 dw ?
@aux4 dw ?
d dw ?
t dw ?
z dw ?
b dw ?
x dw ?
e dw ?
a dw ?
@aux3 dw ?
@aux2 dw ?
@aux1 dw ?
f dw ?
@aux0 dw ?
.code
START:
FNINIT
MOV AX, 1
MOV a, AX
MOV AX, 1
MOV b, AX
MOV AX, 1
MOV z, AX
MOV AX, 1
MOV d, AX
MOV AX, 1
MOV e, AX
MOV AX, 1
MOV f, AX
MOV AX, b
MOV DX, b
MUL z
MOV @aux0, AX
MOV AX, a
ADD AX, @aux0
MOV @aux1, AX
MOV AX, e
CMP AX, 00h
JNE Label@aux2
invoke MessageBox, NULL, addr DIVISIONPORCERO, addr DIVISIONPORCERO, MB_OK
invoke ExitProcess, 0
Label@aux2:
MOV AX, d
MOV DX, d
DIV e
MOV @aux2, AX
MOV AX, @aux1
ADD AX, @aux2
MOV @aux3, AX
MOV AX, @aux3
ADD AX, f
MOV @aux4, AX
MOV AX, @aux4
ADD AX, 20
MOV @aux5, AX
MOV AX, @aux5
MOV x, AX
END START
