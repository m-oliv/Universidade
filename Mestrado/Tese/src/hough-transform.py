import cv2
import numpy as np
import matplotlib.pyplot as plt

def houghTransf(img,thrs,r_ro=1, r_theta=1):
	'''
	Algoritmo Hough Transform Standard.
	
	Input:
		img -> a imagem onde se vao detetar linhas
		r_ro -> a resolucao do rho (valor predefinido e 1)
		r_theta -> a resolucao do theta (valor predefinido e 1)
		thrs -> o threshold a partir do qual se considera que foi detetada uma linha.
	Output:
		ml -> parametros rho, theta das retas detetadas
		res -> acumulador obtido com o voting process
	'''

	# Dimensoes da imagem
	linhas, colunas =img.shape

	# Dimensoes do acumulador
	theta = np.linspace(0.0,179.0,np.ceil(179.0/r_theta)+1)
	max_rho = np.sqrt((linhas**2)+(colunas**2))
	rho = np.linspace(0.0,max_rho,2*np.ceil(max_rho/r_ro))

	# Criar acumulador
	res = np.zeros((len(theta),len(rho)))
	
	# Aplicar o voting process da Transformada de Hough.
	for i in range(linhas):
		for j in range(colunas):
			if(img[i,j]<>0):
				for k in theta:
					# Calcular rho, em que:
					# rho = x * sen(theta) + y * cos(theta)
					v_rho = round(i*np.sin(k*(np.pi/180)) + j*np.cos(k*(np.pi/180))) + len(rho)/2
					# Incrementar a posicao correspondente no acumulador
					res[k,v_rho] += 1

	# Obter maximos locais e converter graus para radianos
	a = []
	for i in range(len(res)):
		for x in range(len(res[i])):
			if(res[i,x] > thrs):
				t = i * (np.pi/180)
				print [x-(len(rho)/2),round(t,4)]
				a.append([x-(len(rho)/2),t])				
	ml = np.array(a)

	return ml, res


########################################################

# Obter imagem de teste
#img = cv2.imread('img_teste/controladas/several-lines.jpg')
bg_sub = True

if(bg_sub == True):
	img_prev = cv2.imread('img_teste/reais/cub1.png',1)
	img_next = cv2.imread('img_teste/reais/reconstruct-c1/back2-t1.png',1)
	delta = cv2.absdiff(img_prev,img_next)
	delta2 = cv2.bitwise_not(delta)
	cv2.imwrite('outputHT/nobg/delta1.png',delta2)
	no_bg = cv2.imread('outputHT/nobg/delta1.png',1)
	img = cv2.imread('outputHT/nobg/delta1.png')
else:
	img = cv2.imread('img_teste/reais/reconstruct-c2/c2t1.png',1)
# Aplicar grayscale e Canny
gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
cv2.imwrite('outputHT/grayscale/gray-cube1.png',gray)
edges = cv2.Canny(gray,50,150,apertureSize = 3)
cv2.imwrite('outputHT/edges/edges-cube1.png',edges)
# Threshold a ser usado na transformada
thrsh = 70

# Aplicar Transformada de Hough (com resolucoes predefinidas)
m,r = houghTransf(edges,thrsh)
print("Num. de Linhas Detetadas:")
print(len(m))
# Obter dois pontos de cada reta e desenhar a mesma na imagem
for rho,theta in m:
	a = np.cos(theta)
	b = np.sin(theta)
	x0 = a*rho
	y0 = b*rho
	x1 = int(x0 + 1000*(-b))
	y1 = int(y0 + 1000*(a))
	x2 = int(x0 - 1000*(-b))
	y2 = int(y0 - 1000*(a))

	cv2.line(img,(x1,y1),(x2,y2),(0,0,255),1)

cv2.imwrite('outputHT/hough-std/cube1-T1.png',img)

np.save('projMatrices/HT1.npy', m)

# Mostrar imagem com retas detetadas
img = cv2.imread('outputHT/hough-std/cube1-T1.png',1)
cv2.imshow('image',img)

k = cv2.waitKey(0)
if k == 27:         # wait for ESC key to exit
    cv2.destroyAllWindows()
