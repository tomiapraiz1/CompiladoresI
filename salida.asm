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
_z dw ?
_b dw ?
_a dw ?
_i dw ?
@aux3 dw ?
@aux2 dw ?
@aux1 dw ?
@aux0 dw ?
.code
START:
FNINIT
MOV AX, 3
MOV _z, AX
MOV AX, 2
MOV _a, AX
MOV AX, 10
MOV _b, AX
MOV AX, 2
MOV DX, 2
MUL _b
MOV @aux0, AX
MOV AX, _a
ADD AX, @aux0
JNC Label@aux1
invoke MessageBox, NULL, addr OVERFLOW, addr OVERFLOW, MB_OK
invoke ExitProcess, 0
Label@aux1:
MOV @aux1, AX
MOV AX, @aux1
MOV _z, AX
MOV AX, _b
ADD AX, _a
JNC Label@aux2
invoke MessageBox, NULL, addr OVERFLOW, addr OVERFLOW, MB_OK
invoke ExitProcess, 0
Label@aux2:
MOV @aux2, AX
MOV AX, _z
ADD AX, 10
JNC Label@aux3
invoke MessageBox, NULL, addr OVERFLOW, addr OVERFLOW, MB_OK
invoke ExitProcess, 0
Label@aux3:
MOV @aux3, AX
MOV AX, @aux3
CMP @aux2, AX
MOV @aux4, 0FFFFh
JA Label@aux4
MOV @aux4, 0000h
Label@aux4:
MOV AX, @aux4
OR AX, 0
JNE Label12
MOV AX, _i
ADD AX, 1
JNC Label@aux5
invoke MessageBox, NULL, addr OVERFLOW, addr OVERFLOW, MB_OK
invoke ExitProcess, 0
Label@aux5:
MOV @aux5, AX
MOV AX, @aux5
MOV _i, AX
Label12:
END START
