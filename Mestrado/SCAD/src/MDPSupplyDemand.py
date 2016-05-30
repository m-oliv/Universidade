import numpy as np
import math
import pylab as mpl

#################################################################

# Problema dos servidores com numero de servidores = 2

#################################################################


act = [] #auxiliar para guardar as accoes optimas
prob = [] #auxiliar para guardar as utilidades
val = [] #auxiliar para guardar a demand ao longo de t

def demand(t):
    # calcular a demand
    return math.sin(t*0.31)*500+1000
    
def reward(x,t):
    #calcular a reward
    
    dem = demand(t) #demand no instante t
    c = 1000        # capacidade do servidor
    e = 500         # custo energia
    b = 1           # custo falhas
    
    if(dem <= x*c):         # reward caso a demand seja inferior a capacidade
        return dem - x*e
    if(dem > x*c):          # reward caso a demand seja superior a capacidade
        return x*(c-e) - b*(dem-x*c)

def utility_mdp(markov,prev_util,stat,t):
    rw = [] #auxiliar para armazenar o calculo das rewards
    
    #calcular a reward para cada estado em t
    for i in range(0,stat):
        rw.append(reward(i,t))
    
    r = np.array(rw)
    m = np.array(markov)
    prvu = np.array(prev_util)
    
    x =(m*prvu).sum(axis=2) #somatorio markov * utilidade seguinte
    a = np.argmax(x,0) #accao optima
    prvu = r + np.max(x,0) #calcular utilidade
    
    act.insert(0,a) #guardar accao optima
    
    return prvu  
        
def main():

    pb = 0.1 #falha no boot
    pr = 0.01 #falha online
    
    #probabilidades da cadeia de markov
    markov = [[[1,0,0],[1,0,0],[1,0,0]],
    [[pb,1-pb,0],[pr,1-pr,0],[pr,1-pr,0]],
    [[pb*pb,(1-pb)*pb*2,(1-pb)*(1-pb)],[pr*pb,(1-pr)*pb+(1-pb)*pr,(1-pb)*(1-pr)],[pr*pr,((1-pr)*pr)*2,(1-pr)*(1-pr)]]]
    
    prvu = [reward(0,23),reward(1,23),reward(2,23)]  #utilidade inicial
    
    stat = 3        #numero de estados
    
    #vectores auxiliares para grafico
    zero = []
    one = []
    two = []
    val = []
    
    for i in range(23,0,-1):
        prvu=utility_mdp(markov,prvu,stat,i)
        prob.append(prvu) #guardar utilidade num vector
    
    for j in range(0,24): #gerar dados grafico da demand ao longo do intervalo de tempo
        val.append(demand(j))
        
    # prints auxiliares    
    for j in range(0,len(act)):
        print(prob[j])
    for k in range(0,len(prob)):
        print(act[k])
    
    # isolar dados de cada tipo de estado para desenhar no grafico
    for p in range(0,len(act)):
        zero.append(act[p][0]*1000)
        one.append(act[p][1]*1000)
        two.append(act[p][2]*1000)
    
    #desenhar linhas no grafico
    
    # com labels e legendas
    mpl.plot(val,label = 'Pedidos')
    mpl.plot(zero, label = 'a0')
    mpl.plot(one, label = 'a1')
    mpl.plot(two, label = 'a2')
    mpl.legend(loc = 'lower right')       
     
#     #sem labels e legendas
#     mpl.plot(val)
#     mpl.plot(zero)
#     mpl.plot(one)
#     mpl.plot(two)
#     
    mpl.show()
    
    pass

if __name__ == "__main__":
    main()