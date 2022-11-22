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
a dw ?
i dw ?
b dw ?
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
MOV AX, 227
MOV b, AX
MOV AX, 0
MOV a, AX
Label2:
MOV AX, i
ADD AX, 1
MOV @aux0, AX
MOV AX, @aux0
MOV i, AX
Label5:
MOV AX, a
ADD AX, 2
MOV @aux1, AX
MOV AX, @aux1
MOV a, AX
MOV AX, b
ADD AX, 1
MOV @aux2, AX
MOV AX, @aux2
MOV b, AX
JMP Label2
MOV AX, b
CMP a, AX
MOV @aux3, FFh
JB Label@aux3
MOV @aux3, 00h
Label@aux3:
MOV AX, @aux3
OR AX, $0
JNE Label14
JMP Label5
Label14:
MOV AX, 100
CMP i, AX
MOV @aux4, FFh
JB Label@aux4
MOV @aux4, 00h
Label@aux4:
MOV AX, @aux4
OR AX, $0
JNE Label18
JMP Label2
Label18:
END START
