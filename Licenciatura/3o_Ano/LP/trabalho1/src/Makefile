CC = gcc
CFLAGS = -Wall

LEX = flex

YACC = bison
YFLAGS = -d

all: lambda.tab.o lex.yy.o
	$(CC) $(CFLAGS) lambda.tab.o lex.yy.o -o lambda $(LDLIBS) 

lambda.tab.c lambda.tab.h: lambda.y
	$(YACC) $(YFLAGS) lambda.y

lambda.tab.o: lambda.tab.c
	$(CC) $(CFLAGS) -c lambda.tab.c -o lambda.tab.o

lex.yy.c: lambda.l
	$(LEX) $(LFLAGS) lambda.l

lex.yy.o: lex.yy.c lambda.tab.h
	$(CC) $(CFLAGS) -Wno-unused-function -c lex.yy.c -o lex.yy.o

clean_s:
	@$(RM) lambda.tab.? lex.yy.?

realclean: clean_s
	@$(RM) *~

clean: realclean
	@$(RM) lambda

run:
	./lambda
