import numpy as np
import matplotlib.pyplot as plt
res_forw = []

def forward(obs,markov,seq,prior):
    
    p = np.array(prior)
    observ = np.array(obs)
    
    print("Forward:\n")
    for y in seq:
        p = np.array(observ[y] * np.array(np.dot(markov,p)))
        # normalizar
        p = p/sum(p)
        # print com precisao de 4 casas decimais
        np.set_printoptions(precision = 4,suppress=True)
        print(p)
        res_forw.append(p)
    print("\n")


def main():
    #probs das observacoes (sem erro)
    obs_ne = [[1,0,0,0,0,0,0,0,0],
              [0,1,0,0,0,0,0,0,0],
              [0,0,1,0,0,0,0,0,0],
              [0,0,0,1,0,0,0,0,0],
              [0,0,0,0,1,0,0,0,0],
              [0,0,0,0,0,1,0,0,0],
              [0,0,0,0,0,0,1,0,0],
              [0,0,0,0,0,0,0,1,0],
              [0,0,0,0,0,0,0,0,1]]
    
    obs = []
    
    # calcular distribuicao do erro
    erro = np.array([0.5, 1.0, 3.0, 20.0,  80.0, 20.0, 3.0, 1.0, 0.5])
    erro = erro/sum(erro)
    
    # calcular as probs das observacoes (incluindo erro)
    for i in range(0,len(obs_ne)):
        obs_f = np.convolve(np.array(obs_ne[i]),erro,mode='same')
        obs_f = obs_f/sum(obs_f)
         
        obs.append(obs_f)
         
#     # probabilidades de transicao
    markov = np.array([
              [0.1,0,0,0,0,0,0,0,0.9],
              [0.9,0.1,0,0,0,0,0,0,0],
              [0,0.9,0.1,0,0,0,0,0,0],
              [0,0,0.9,0.1,0,0,0,0,0],
              [0,0,0,0.9,0.1,0,0,0,0],
              [0,0,0,0,0.9,0.1,0,0,0],
              [0,0,0,0,0,0.9,0.1,0,0],
              [0,0,0,0,0,0,0.9,0.1,0],
              [0,0,0,0,0,0,0,0.9,0.1]
              ])

    # sequencia observada   
    #      a,b,c,d,e           
    seq = [0,1,2,3,4]
    # estado inicial (I)
    x = [1/9]*9;
    prior = np.array(x)
    
    # filtragem
    forward(obs,markov,seq,prior)
    
    pass

if __name__ == "__main__":
    main()
