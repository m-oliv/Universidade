import numpy as np

def utility_mdp(markov,prev_util,horizon,reward):
    #prob = []
    act = []
    r = np.array(reward)
    m = np.array(markov)
    prvu = np.array(prev_util)
    #print(prvu)
    #prob.append(r)
    while(horizon!=0):
        x =(m*prvu).sum(axis=2)
        print(x)
        y = np.argmax(x,0)
        print(y)
        act.insert(0,y)
        prvu = r + np.max(x,0)
        horizon = horizon - 1
    
    #print(act)
def main():

    # Exemplos dos apontamentos
    utility_mdp([[[0.9,0.1],[0.1,0.9]],[[0.5,0.5],[0.5,0.5]]],[1,5],3,[1,5])
    pass

if __name__ == "__main__":
    main()
