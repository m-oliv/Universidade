import scipy.optimize as spo
import matplotlib.pyplot as plt 
import numpy as np

def func_rec_self(p1,p2):
    # Jogo em que as placas competem para enviar com probabilidades diferentes (selfish)
    #Recompensa
    #p1 -> prob envio placa 1
    #p2 -> prob envio placa 2

    return (((p1**2)*p2) + (1-p1)*p2 + p1*(1-p1)*(1-p2)) / ((p1**2)+ p1*p2 - 2 *(p1**2)*p2)

def func_rec_nonself(c):
    # Jogo em que as placas enviam com probabilidades iguais (non selfish)
    #Recompensa
    #c -> prob envio
    
    return (2*(c**2) - 3*c + 2)/(2*c - 2*(c**2))

def main():

    a = np.linspace(0.0,1.0) # probabilidade de envio p1
    b = np.linspace(0.5,0.8) # probabilidade de envio p2
    
    #temporarios para armazenar melhores respostas
    valp1 = []
    valp2 = []
    
    print("Quando as placas enviam com probabilidades diferentes.")
    # obter melhores respostas para p2
    for j in a:
        valp1.append(spo.minimize_scalar(func_rec_self, args = (j,),bounds=(0.0, 1.0),method='bounded').x)
        # mostrar valores obtidos na minimizacao
        k = spo.minimize_scalar(func_rec_self, args = (j,),bounds=(0.0, 1.0),method='bounded')
        print(k)
    
    # obter melhores respostas para p1
    for i in b:
        valp2.append(spo.minimize_scalar(func_rec_self, args = (i,),bounds=(0.0, 1.0),method='bounded').x)
        # mostrar valores obtidos na minimizacao
        k = spo.minimize_scalar(func_rec_self, args = (i,),bounds=(0.0, 1.0),method='bounded')
        print(k)
    
    # obter o valor optimo
    non_s=spo.minimize_scalar(func_rec_nonself, bounds=(0.0, 1.0),method='bounded').x
    
    print("\n Quando as placas enviam com a mesma probabilidade.")
    print("Valor optimo: ",non_s)
    
    #desenhar grafico
    plt.plot(a,valp1)
    plt.plot(a,valp2)
    plt.show()
    pass

if __name__ == "__main__":
    main()
