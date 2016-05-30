CC = gcc

EXECUTABLES = trabalho

all: $(EXECUTABLES)

trabalhomain.o: trabalhomain.c

clean:
	rm -f core *.o

clean_all clean-all: clean
	rm -f *~ $(EXECUTABLES)

.SUFFIXES: .c .o
.c.o:
	$(CC) $(CFLAGS) -c $*.c	

trabalho: trabalhomain.o
	$(CC) $(CFLAGS) -o $@ trabalhomain.o

