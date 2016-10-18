import numpy as np

res_forw = []
res_back = []

# def update_forw(markov,prior):
#     return np.dot(markov,prior)

def forward(obs,markov,seq,prior):
    
    p = np.array(prior)
    observ = np.array(obs)
    
    print("Forward:\n")
    for y in seq:
        p = np.array(observ[y] * np.array(np.dot(markov,p)))
        p = p/sum(p)
        print(p)
        res_forw.append(p)
    print("\n")

def backward(obs,markov,seq,ver):

    pr = np.array(ver)
    observ = np.array(obs)
    print("Backward:\n")
    sx = seq[::-1]
    for z in sx:
        print(pr)
        res_back.append(pr)
        pr = np.dot(markov,observ[z] * pr)
        #pr = pr/sum(pr)

    print("\n")
    #print(res_back)
    
def forward_backward(obs,markov,seq,prior,ver):
    forward(obs,markov,seq,prior)
    backward(obs,markov,seq,ver)
   
    rb = [] 
    for z in res_back[::-1]:
        rb.append(z)

    res = []

    print("Smoothing:\n")
    for x in range(len(seq)):
        k = np.array(res_forw[x])
        y = np.array(rb[x])
        p = np.array(np.multiply(k,y))
        p = p/sum(p)
        print(p)
        res.append(p)

    #print(res)

def main():
    '''
forward:
forward([[0.8,0.2],[0.2,0.8]],[[0.9,0.1],[0.1,0.9]],[0,0,1,1,1],[0.5,0.5])

backward:
backward([[0.8,0.2],[0.2,0.8]],[[0.9,0.1],[0.1,0.9]],[0,0,1,1,1],[1,1])

forward (robot):
forward([[0.8,0.3,0.3,0.8],[0.2,0.7,0.7,0.2]],[[0.3,0.2,0,0.5],[0.5,0.3,0.2,0],[0,0.5,0.3,0.2],[0.2,0,0.5,0.3]],[0,1,1,1,1],[0.25,0.25,0.25,0.25])

'''
    # Exemplos dos apontamentos
    #forward([[0.8,0.2],[0.2,0.8]],[[0.9,0.1],[0.1,0.9]],[0,0,1,1,1],[1,1])
    #backward([[0.8,0.2],[0.2,0.8]],[[0.9,0.1],[0.1,0.9]],[0,0,1,1,1],[1,1])
    #forward_backward([[0.8,0.2],[0.2,0.8]],[[0.9,0.1],[0.1,0.9]],[0,0,1,1,1],[0.5,0.5],[1,1])

    # Robot problem
    #forward([[0.8,0.3,0.3,0.8],[0.2,0.7,0.7,0.2]],[[0.3,0.2,0,0.5],[0.5,0.3,0.2,0],[0,0.5,0.3,0.2],[0.2,0,0.5,0.3]],[0,0,1,1,1],[0.25,0.25,0.25,0.25])
    #forward_backward([[0.8,0.3,0.3,0.8],[0.2,0.7,0.7,0.2]],[[0.3,0.2,0,0.5],[0.5,0.3,0.2,0],[0,0.5,0.3,0.2],[0.2,0,0.5,0.3]],[0,0,1,1,1],[0.25,0.25,0.25,0.25],[1,1,1,1])
    
    #forward_backward([[1,0],[0.5,0.5]], [[0.9,0.1],[0.1,0.9]],[0,1,1],[1,0], [1,1])
    forward_backward([[0.8,0.2],[0.2,0.8]],[[0.9,0.1],[0.1,0.9]],[0,1,1,1],[0.5,0.5],[1,1])
    #ex sebenta
    #forward([[1,0],[0.5,0.5]],[[0.9,0.1],[0.1,0.9]],[0,1,1],[1,0])
    
    
    pass

if __name__ == "__main__":
    main()
