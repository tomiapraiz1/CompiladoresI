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
_number dw ?
_end dw ?
_a dw ?
_i dw ?
@aux3 dw ?
@aux2 dw ?
@aux1 dw ?
@aux0 dw ?
.code
START:
FNINIT
Label0:
MOV AX, _number
CMP _i, AX
MOV @aux0, 0FFFFh
JE Label@aux0
MOV @aux0, 0000h
Label@aux0:
MOV AX, @aux0
OR AX, 0
JNE Label6
MOV AX, 1
MOV _a, AX
JMP Label28
JMP Label7
Label6:
Label7:
MOV AX, 2
MOV DX, 2
MUL _end
MOV @aux1, AX
MOV AX, _i
ADD AX, @aux1
JNC Label@aux2
invoke MessageBox, NULL, addr OVERFLOW, addr OVERFLOW, MB_OK
invoke ExitProcess, 0
Label@aux2:
MOV @aux2, AX
MOV AX, @aux2
MOV _i, AX
Label11:
MOV AX, _number
CMP _i, AX
MOV @aux3, 0FFFFh
JE Label@aux3
MOV @aux3, 0000h
Label@aux3:
MOV AX, @aux3
OR AX, 0
JNE Label17
MOV AX, 1
MOV _a, AX
JMP Label23
JMP Label18
Label17:
Label18:
MOV AX, _end
CMP _i, AX
MOV @aux4, 0FFFFh
JA Label@aux4
MOV @aux4, 0000h
Label@aux4:
MOV AX, @aux4
OR AX, 0
JNE Label23
JMP Label11
MOV AX, 2
MOV _a, AX
Label23:
MOV AX, _end
CMP _i, AX
MOV @aux5, 0FFFFh
JA Label@aux5
MOV @aux5, 0000h
Label@aux5:
MOV AX, @aux5
OR AX, 0
JNE Label28
JMP Label0
MOV AX, 3
MOV _a, AX
Label28:
END START
