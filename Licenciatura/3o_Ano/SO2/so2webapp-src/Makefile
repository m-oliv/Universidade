CP=../so2webapp/WEB-INF/classes:/usr/local/TOMCAT/common/lib/servlet-api.jar

all:
	CLASSPATH=$(CP):../so2webapp/WEB-INF/lib/postgresql.jar javac -d ../so2webapp/WEB-INF/classes src/so2/*.java

# criar o keystore
runCKS:
	java -classpath $(CP) so2.CryptoKS


stubs:
	CLASSPATH=../so2webapp/WEB-INF/classes:../so2webapp/WEB-INF/lib/postgresql.jar rmic -vcompat -d ../so2webapp/WEB-INF/classes so2.LojaImpl


# activar o servico de nomes:
reg1:
	 rmiregistry -J-classpath -J$(CP):../so2webapp/WEB-INF/lib/postgresql.jar 9020

reg2:
	rmiregistry -J-classpath -J$(CP):../so2webapp/WEB-INF/lib/postgresql.jar 9021

reg3:
	rmiregistry -J-classpath -J$(CP):../so2webapp/WEB-INF/lib/postgresql.jar 9022
rs1:
	CLASSPATH=../so2webapp/WEB-INF/classes:../so2webapp/WEB-INF/lib/postgresql.jar java so2.ViagensServer 9020

rs2:
	CLASSPATH=../so2webapp/WEB-INF/classes:../so2webapp/WEB-INF/lib/postgresql.jar java so2.ViagensServer 9021

rs3:
	CLASSPATH=../so2webapp/WEB-INF/classes:../so2webapp/WEB-INF/lib/postgresql.jar java so2.ViagensServer 9022

testerep:
	CLASSPATH=../so2webapp/WEB-INF/classes:../so2webapp/WEB-INF/lib/postgresql.jar java so2.Replicacao

regb:
	rmiregistry -J-classpath -J$(CP):../so2webapp/WEB-INF/lib/postgresql.jar 9050

backup:
	CLASSPATH=../so2webapp/WEB-INF/classes:../so2webapp/WEB-INF/lib/postgresql.jar java so2.ViagensServer 9050

tomcat:
	touch  ../so2webapp/WEB-INF/web.xml

clean:
	rm -fr *~ *class ../so2webapp/WEB-INF/classes/*
	rm -rf classes/* *~ src/*~ src/so2/*~
	rm -fr ../so2webapp-src/KStore
	rm -fr ../so2webapp-src/Repositorio/*
