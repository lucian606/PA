JAVAFLAGS=-Xmx128m -Xss128m
build: p1 p2 p3

p1: src/P1.java
	javac -d . src/P1.java

p2: src/P2.java
	javac -d . src/P2.java

p3: src/P3.java
	javac -d . src/P3.java

run-p1:
	java ${JAVAFLAGS} P1

run-p2:
	java ${JAVAFLAGS} P2

run-p3:
	java ${JAVAFLAGS} P3
clean:
	rm -f *.class
