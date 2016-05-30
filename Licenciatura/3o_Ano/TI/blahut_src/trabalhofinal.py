import math

def blahut(pa,pc,e):
    aux=[]; # array auxiliar para guardar os valores a serem somados
    C = []; # array que vai armazenar os valores das capacidades calculadas
    arrayLimInf =[]; # array temp que vai armazenar os valores para calcular o limite inferior
    som1 = []; # array que guarda os valores do somatorio do dividendo para se poder fazer o somatorio
    som2 = []; # array que guarda os valores do somatorio2
    cap = [];  # array que guarda as capacidades
    som3 = [] # array que guarda a multiplica????o das probabilidades de entrada pelas capacidades(calculo necessario para limite inferior)
    dividendos = []
    matrizFinal = []


    x = len(pc)/len(pa)

    contador = 0
    # Calcular o somatorio do dividendo
    for j in range(0,len(pa)):  # 0 e o numero de colunas da matriz de transi????o(entrada)
        som2 = []
        matrizFinal =[]
        for i in range(0,x): # 0 e numero de linhas da matriz de transi????o(saida)
            x2 = i
            som1 = []
            for k in range(0, len(pa)): # numero de entradas
                temp = pa[k]*pc[x2]  # calculo do somatorio do dividendo

                x2 = x2+x # incrementa o numero corresponde ao passar na matriz para a linha seguinte da matriz

                som1.append(temp)
                dividendos.append(temp)

            e1 = sum(som1)
            if(pc[i+contador] != 0):
                temp2 = pc[i+contador]*math.log(((pc[i+contador])/e1)) #corresponde aos valores do segundo somatorio
            else:
                temp2 = 0
            som2.append(temp2) # insere os valores num array

            matrizFinal.append(e1)
        contador = i+1  # incrementa o contador para a coluna seguinte da matriz

        e2 = sum(som2)

        temp3 = math.exp(e2) # calcula a capacidade
    
        cap.append(temp3) # guarda a capacidade num array
    # Calculo auxiliar para o Limite Inferior
    print pa
    print cap
    for m in range(0,len(pa)):
        temp4 = pa[m]*cap[m]
        som3.append(temp4)

    e3 = sum(som3)
    # Limites
    liminf = math.log(e3,2)
    limsup = math.log(max(cap),2)


    if(limsup - liminf < e):
        c = liminf
        print "Capacidade do Canal =  ", c
        print "Probabilidade de Entrada = ",pa  # ser???
        print "Probabilidade de Saida = ", matrizFinal
    else:
        for i in range(len(pa)):
            pa[i] = pa[i]*(cap[i]/e3) # Calcular o novo valor das probabilidades
        print pa
        blahut(pa,pc,e)

def main():
    inic = input("Insira o numero de simbolos de entrada: ")
    print "Numero de simbolos de entrada = ", inic

    fim = input ("Insira o numero de simbolos de saida: ")
    print "Numero de simbolos de saida = ",fim

    e = input("Insira a margem de precisao: ")
    print "Margem de precisao = ",e

    pc= []; #probabilidades condicionais dadas
    pa = []; #probabilidades dos simbolos iniciais

    pc = input("Insira a matriz das probabilidades de transicao que caracteriza o canal: ")

    print "Matriz de transicao = ",pc
    #inicializa pa
    for i in range(inic):
      tp = (1.0/inic)
      pa.append(tp)
    print "Probabilidades Iniciais = ",pa

    blahut(pa,pc,e)

    pass


if __name__ == "__main__":
    main()