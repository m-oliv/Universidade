CC = gcc

EXECUTABLES = trabalho

all: $(EXECUTABLES)

trabalho.o: trabalho.h trabalho.c

clean:
	rm -f core *.o

clean_all clean-all: clean
	rm -f *~ $(EXECUTABLES)

.SUFFIXES: .c .o
.c.o:
	$(CC) $(CFLAGS) -c $*.c	

trabalho: trabalho.o
	$(CC) $(CFLAGS) -o $@ trabalho.o

runtrabalho: $(EXECUTABLES)
	./trabalho "3 3 2 5 7" "0 5 1 1 3 1" "2 2 2 3 3 5 2 4" "3 7 2 2"
