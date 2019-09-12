 
JAVA_COMP=javac

all:
	$(JAVA_COMP) *.java

clean:
	$(RM) *.class src/ast/*.class src/comp/*.class src/lexer/*.class 