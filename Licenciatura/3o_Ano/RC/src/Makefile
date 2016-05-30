objects = rc-compras.o rc-net-lib.o

compras: $(objects)
	cc -o compras $(objects)

rc-compras.o: rc-compras.c rc-net-lib.h 

clean: 
	rm compras  
