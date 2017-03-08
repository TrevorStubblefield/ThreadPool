
PACKAGE = cs455/scaling/server/
PACKAGE2 = cs455/scaling/client/
PACKAGE3 = cs455/scaling/protocol/

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = ${PACKAGE2}*.java ${PACKAGE}*.java ${PACKAGE3}*.java

default: classes

all: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) ${PACKAGE2}*.class ${PACKAGE}*.class ${PACKAGE3}*.class

