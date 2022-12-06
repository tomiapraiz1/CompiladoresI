.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
include \masm32\include\masm32.inc 
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib 

.data

DIVISIONPORCERO db "Error division por cero", 0
OVERFLOW db "Error overflow de suma", 0
INVOCACION db "Error la funcion no puede volver a ser invocada", 0
@auxFloat1 real10 2.0
@auxFloat2 dd 3.2
e dd ?
.code
START:
invoke StdOut, addr @auxFloat1
FLD @auxFloat1
FLD @auxFloat2
FADD
FSTP e
invoke ExitProcess, 0 
END START
