import cv2
import numpy as np
from matplotlib import pyplot as plt
import pylab
from mpl_toolkits.mplot3d import Axes3D
from itertools import izip_longest

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
				#print(res[i,x])
				#print [x-(len(rho)/2),t]
				a.append([x-(len(rho)/2),t])				
	ml = np.array(a)

	return ml, res

def drawHoughLines(ml,img):
	for rho,theta in ml:
		a = np.cos(theta)
		b = np.sin(theta)
		x0 = a*rho
		y0 = b*rho
		x1 = int(x0 + 1000*(-b))
		y1 = int(y0 + 1000*(a))
		x2 = int(x0 - 1000*(-b))
		y2 = int(y0 - 1000*(a))
		cv2.line(img,(x1,y1),(x2,y2),(255,0,0),1)
	
	cv2.imwrite('outputHT/hough-std/houghlines-rec.png',img)
	
	img = cv2.imread('outputHT/hough-std/houghlines-rec.png',1)
	cv2.imshow('image',img)
	
	k = cv2.waitKey(0)
	if k == 27:
		cv2.destroyAllWindows()

def getLineParams(houghL):
	'''
	Obter os parametros A,B,C da reta na forma Ax+By+C=0,
	usando rho = x*cos(theta) + y*sin(theta)
	
	Input:
	houghL -> parametros rho, theta obtidos com a Transformada de Hough
	
	Output:
	LP -> Vetor com os parametros A,B,C da reta.
	'''
	LP = []
	rho = houghL[0]
	theta = houghL[1]
	
	A = np.cos(theta)
	B = np.sin(theta)
	C = -rho
	
	LP = np.array([A,B,C])
	
	return LP

def getPlanes(A1,P1,P2):
	L1 = []
	L2 = []
	
	# Para cada linha do par, calcular os parametros do plano 3D definido pela projeccao da mesma.
	for i in range(len(A1)):
		if(i%2==0):
			L2.append(A1[i].dot(P2))
		else:
			L1.append(A1[i].dot(P1))
		
	
	return np.array([L1,L2])

def deleteMatch(L,l):
	newL = []
	for i in L:
		if(np.array_equal(i,l)==False):
			newL.append(i)
	return np.array(newL)

def match(A1,A2,P1,P2):
	'''
	Matching de linhas com recurso a uma funcao de custo (C).
	
	Input:
		A1 -> parametros das linhas na imagem 1 (obtidos com hough transform)
		A2 -> parametros das linhas na imagem 2 (obtidos com hough transform)
		P1 -> matriz da calibracao da camara 1
		P2 -> Matriz da calibracao da camara 2
	
	Output:
		LABC -> parametros da linha 3D
	
	'''
	
	LABC = []
	
	C = []
	L = []
	Ang = []
	
	# evitar casos em que ha mais matches que o possivel
	if(len(A1) > len(A2)):
		Ln1 = A2
		Ln2 = A1
	else:
		Ln1 = A1
		Ln2 = A2
	angcalc = 0
	# match
	for a in Ln1:
		for b in Ln2:
			# Calcular angulo entre as linhas
			ang = np.arctan((b[0]*a[1] - a[0]*b[1]) / (a[0]*a[1] + b[0]*b[1]))
			Ang.append(ang)
			# Calcular o custo do match da linha a atual com cada uma das linhas possiveis (apenas se ang >= pi/4)
			if(ang < (np.pi/4)):
				C.append(np.cos(4*(ang))+1)
				angcalc = angcalc + 1
		
		# Obter o custo
		m = np.argmax(C)
		
		# Guardar informacao para match (manter a ordem)
		if(len(A1) > len(A2)):
			L.append(Ln2[m])	
			L.append(a)
		else:
			L.append(a)
			L.append(Ln2[m])
			
		# Remover linha matched da lista de possiveis matches
		Ln2 = deleteMatch(Ln2,Ln2[m])
		
		LA = getPlanes(np.array(L),P1,P2)
		
		# Criar matriz 2x4 com parametros necessarios para obter a linha reconstruida
		LABC.append(np.array(LA).reshape(2,4))
		C = []
		L = []
	print(angcalc)
	return LABC

def reconstruct(LP, v1, v2, flag):
	'''
	Obter dois pontos de uma reta (com coord. x com valor fixo).
	
	Input:
		LP -> array com as matrizes 2x4 com os parametros das retas 3D
		v1 -> valor predefinido para x do ponto 1
		v2 -> valor predefinido para x do ponto 2
		flag -> 0 vertical, 1 horizontal, 2 diagonal 
	Output:
		pt -> array com os pontos obtidos.
	'''
	
	pt = []
	it = 0
	espv = 5
	espv2 = 3
	espd = 2
	for LNP in LP:
		b = LNP[:,3] # a ultima linha da matriz A 
		A1 = LNP[:,0] # primeira coluna de A
		A2 = LNP[:,1] # segunda coluna de A
		A3 = LNP[:,2]
		A = LNP[:,1:3] # A excepto primeira e ultima coluna
		Av = LNP[:,0:2]
		
		if(flag == 0):
			# v2 da reconstr -> escrever linhas verticais com espacamento 
			p1 = np.linalg.lstsq(Av,-b-A2*v1)
			if(it%2==0):
				px = p1[0][0] + (it * espv)
			if(it%3==0):
				px = p1[0][0] + (it * espv2)
			ps = np.delete(p1[0],0)
			p1 = np.insert(ps,0,px)
			p1 = np.insert(p1,2,v1)
			
			p2 = np.linalg.lstsq(Av,-b-A3*v2)
			if(it%2==0):
				px = p2[0][0] + (it * espv)
			if(it%3==0):
				px = p2[0][0] + (it * espv2)
			ps = np.delete(p2[0],0)
			p2 = np.insert(ps,0,px)
			p2 = np.insert(p2,2,v2)
			it = it + 1
			
		if(flag == 1):
			p1 = np.linalg.lstsq(A,-b-A1*v1)
			p1 = np.insert(p1[0],0,v1)
			
			p2 = np.linalg.lstsq(A,-b-A2*v2)
			p2 = np.insert(p2[0],0,v2)
		
		if(flag == 2):
			# v2 da reconstr -> escrever linhas com espacamento 
			p1 = np.linalg.lstsq(Av,-b-A2*v1)
			px = (p1[0][0] - (it * espd))/100
			ps = np.delete(p1[0],0)
			p1 = np.insert(ps,0,px)
			p1 = np.insert(p1,2,v1)
			
			p2 = np.linalg.lstsq(Av,-b-A3*v2)
			px = (p2[0][0] - (it * espd))/100
			ps = np.delete(p2[0],0)
			p2 = np.insert(ps,0,px)
			p2 = np.insert(p2,2,v2)
			it = it + 1
					
		pt.append([p1,p2])
	
	return np.array(pt)
	
def generate3DLine(PT,A):
	'''
	Gerar pontos de uma linha 3D.
	
	Input:
		PT -> pontos usados para gerar a linha.
		A -> Escalar (numero de pontos da reta).
		
	Output:
		X,Y,Z -> coordenadas X,Y,Z dos pontos que constituem a linha.
	'''
	PTS = []
	X = []
	Y = []
	Z = []
	
	for p in PT:
		for i in range(0,A):
			P = p[0] + i*(p[1]-p[0])
			X.append(P[0])
			Y.append(P[1])
			Z.append(P[2])
		PTS.append([X,Y,Z])
		X = []
		Y = []
		Z = []
	
	return np.array(PTS)

def plot3DLines(LPTS,LPTSv,LPTSd):
	'''
	Plot das linhas 3D reconstruidas.
	
	Input:
		LPTS -> Array com os pontos de cada reta.

	Output:
		void.
	
	'''
	fig = plt.figure()
	ax = fig.gca(projection='3d')
	ax.set_xticks([])
	ax.set_yticks([])
	ax.set_zticks([])
	
	for p in LPTS:
		ax.plot(p[0],p[1],zs=p[2])
	
	for pv in LPTSv:
		ax.plot(pv[0],pv[1],zs=pv[2])
	
	for pd in LPTSd:
		ax.plot(pd[0],pd[1],zs=pd[2])
	
	
	plt.show()	

def getVecDir(M):
	
	C = []
	
	for m in M:
		C.append(np.cross(m[0][:3],m[1][:3]))
	
	return np.array(C)
		

def generate3DLineAlt(PT,D,T):
	PTS = []
	X = []
	Y = []
	Z = []
	
	for p,d in zip(PT,D):
		for i in range(0,T):
			P = p + i*d
			#print(P)
			X.append(P[0])
			Y.append(P[1])
			Z.append(P[2])
		
		PTS.append([X,Y,Z])
		X = []
		Y = []
		Z = []
		
	return np.array(PTS)

def getVHD(L,vl1, vl2, hl1,hl2):
	'''
	Separar as linhas em grupos (verticais, horizontais, diagonais).
	Input:
		L - conjunto com as linhas todas;
		vl1 / hl1 - lim inf para linhas verticais / horiz
		vl2 / hl2 - lim sup para linhas verticais / horiz
	Output:
		oth - conj linhas diagonais
		vert - conj linhas verticais
		horiz - conj linhas horizontais
	'''
	vert = []
	horiz = []
	oth = []
	
	for l in L:
		if((l[1] > vl1 or l[1] == vl1) and (l[1] < vl2 or l[1] == vl2)):
			vert.append(l)
		elif((l[1] > hl1 or l[1] == hl1) and (l[1] < hl2 or l[1] == hl2)):
			horiz.append(l)
		else:
			oth.append(l)
			
	return oth, vert, horiz

def autoConvToABC(HT1,HT2):
	L1 = []
	L2 = []
	
	for l1 in HT1:
		L1.append(getLineParams(l1))
	
	for l2 in HT2:
		L2.append(getLineParams(l2))
	
	return L1, L2
	
####################################################
# Obter parametros das linhas nas projecoes

# load das matrizes de calibracao
P1 = np.load('projMatrices/P1-V1.npy')
P2 = np.load('projMatrices/P2-V1.npy')

HT1 = np.load('projMatrices/HT1.npy')
HT2 = np.load('projMatrices/HT2.npy')

print("Parametros (rho,theta) das Linhas Detetadas Pela T. Hough:")
print(HT1)
print(HT2)

# Separar linhas por classe (horiz, vert, outras (diag))
HT1, HT1v, HT1h = getVHD(HT1,1.396,1.745,0,0.175)
HT2, HT2v, HT2h = getVHD(HT2,1.396,1.745,0,0.175)
print("Num Verticais Image 1")
print(len(HT1v))

print("Num Horizontais Image 1")
print(len(HT1h))

print("Num Diagonais Image 1")
print(len(HT1))

print("Num Verticais Image 2")
print(len(HT2v))

print("Num Horizontais Image 2")
print(len(HT2h))

print("Num Diagonais Image 2")
print(len(HT2))

#converter de rho, theta para A,B,C
V1,V2 = autoConvToABC(HT1v,HT2v)
H1,H2 = autoConvToABC(HT1h,HT2h)
D1,D2 = autoConvToABC(HT1,HT2)

# matching de linhas
Lv = match(V1,V2,P1,P2)
Lh = match(H1,H2,P1,P2)
Ld = match(D1,D2,P1,P2)

print("Match de Linhas Verticais")
print(len(Lv))
LVx = []
for i in Lv:
	a = []
	for j in i:
		b = []
		for k in j:
			b.append('{0:.4f}'.format(k))
		a.append(b)
	LVx.append(a)
print(LVx)

print("Match de Linhas Horizontais")
print(len(Lh))
LHx = []
for i in Lh:
	a = []
	for j in i:
		b = []
		for k in j:
			b.append('{0:.4f}'.format(k))
		a.append(b)
	LHx.append(a)
print(LHx)

print("Match de Linhas Diagonais")
print(len(Ld))
LDx = []
for i in Ld:
	a = []
	for j in i:
		b = []
		for k in j:
			b.append('{0:.4f}'.format(k))
		a.append(b)
	LDx.append(a)
print(LDx)

# reconstrucao
PTSLv = reconstruct(Lv,-2,10,0)
PTSLh = reconstruct(Lh,1,7,1)
PTSLd = reconstruct(Ld,-1.3,0,2)

LPTv = generate3DLine(PTSLv,2)
LPTh = generate3DLine(PTSLh,10)
LPTd = generate3DLine(PTSLd,10)

plot3DLines(LPTh,LPTv,LPTd)
