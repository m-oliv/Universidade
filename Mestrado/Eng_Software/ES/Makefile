
compile:
	CLASSPATH=.:classes:lib/junit-4.11.jar:h2-1.4.178.jar:. javac -d classes src/*.java
	CLASSPATH=.:classes:lib/junit-4.11.jar:h2-1.4.178.jar:. javac -d classes test/unit/*.java

all:	compile
	
testdoc:

	CLASSPATH=.:classes:docs:lib/junit-4.11.jar:lib/h2-1.4.178.jar:lib/hamcrest-core-1.3.jar  java org.junit.runner.JUnitCore DocumentoTest


testuser:

	CLASSPATH=.:classes:docs:lib/junit-4.11.jar:lib/h2-1.4.178.jar:lib/hamcrest-core-1.3.jar  java org.junit.runner.JUnitCore UserTest

testsearch:

	CLASSPATH=.:classes:docs:lib/junit-4.11.jar:lib/h2-1.4.178.jar:lib/hamcrest-core-1.3.jar  java org.junit.runner.JUnitCore SearchTest


testmain:

	CLASSPATH=.:classes:docs:lib/junit-4.11.jar:lib/h2-1.4.178.jar:lib/hamcrest-core-1.3.jar java Main

run: testdoc testuser testsearch testmain

runmain: testmain

t: compile testdoc testuser testsearch testmain

clean:
	rm -rf classes/* *~ 
