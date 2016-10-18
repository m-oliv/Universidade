import numpy as np

def viterbi(markov,obs,prior,seq):
    
    pr = np.array(prior)
    observ = np.array(obs)
    m = np.array(markov)

    print("------------- Viterbi -------------")
    
    for x in seq:
        #print("Seq:",x)
        #print("max:",np.amax(m * pr,x))
        #print("observ:",observ[x])
        pr = observ[x] * np.array(np.max(m * pr,x))
        print(pr)
        
def main():

    # Exemplos dos apontamentos

    viterbi([[0.9,0.1],[0.1,0.9]],[[1,0],[0.5,0.5]],[1,0],[0,1,1])
    pass

if __name__ == "__main__":
    main()
