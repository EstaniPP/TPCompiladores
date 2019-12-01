echo off
:inicio
cls
set path=%PATH%;d:\masm32\bin
echo salida.asm:
echo para salir:"exit"
cls
ml /Cp /coff "salida.asm" /link /subsystem:console
pause
goto inicio
:salir
exit