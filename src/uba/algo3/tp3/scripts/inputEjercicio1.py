import random

# el peor caso es un completo donde cada nodo tiene lc colores.
# cada una de esas listas usan colores distintos
# luego hay dos nodos que generan conflictos con 1 color cada uno (el mismo)
def generarInputPeorCaso(n, name):
    f = open(name, 'w')
    m = (n*(n-1))/2
    c = 2
    f.write(str(n) + " " + str(m) + " " + str(c) + "\n")
    j = 0
    for i in range(0, n):
        listaColores = [j, j+1]
        j = j+2
        f.write(str(2) + " " + str(listaColores).replace('[', '').replace(']','').replace(', ', ' ') + "\n")
    
    for i in range(0, n):
        ady = [str(i) + " " + str(x) for x in range(i+1, n)]
        #ady = [str(i) + " " + str(i + 1)]        
        for arista in ady:  
            f.write(arista + "\n")

    f.close()



for i in range(100, 850, 100):
    generarInputPeorCaso(i, "entradaPeorEj1N"+ str(i)+"LC2.in")

# LC = 6 N=4..11 EXPONENCIAL
#for i in range(4,13):
#    generarInputPeorCaso(i, 6, "entradaPeorEj2N"+ str(i)+"LC6.in")
#    generarInputMejorCaso(i, 6, "entradaMejorEj2N"+ str(i)+"LC6.in")
#    generarInputSinIntencionalidad(i, 6, "entradaSinIntencionalidadEj2N"+ str(i)+"LC6.in")

# LC = 2...20 N=5 POLINOMIAL
#for i in range(2,21):
#    generarInputPeorCaso(5, i, "entradaPeorEj2N5LC"+str(i)+".in")
#    generarInputMejorCaso(5, i, "entradaMejorEj2N5LC"+str(i)+".in")
#    generarInputSinIntencionalidad(5, i, "entradaSinIntencionalidadEj2N5LC"+str(i)+".in")

#
#
