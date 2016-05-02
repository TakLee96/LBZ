SHELL = bash

STYLEPROG = style61b

JFLAGS = -g -Xlint:unchecked -encoding utf8

SRCS = $(wildcard *.java)

CLASSES = $(SRCS:.java= classes/.class)

# Tell make that these are not really files.
.PHONY: clean default style check

default: compile

compile: $(CLASSES)

style:
	$(STYLEPROG) $(SRCS)

$(CLASSES): sentinel

sentinel: $(SRCS)
	mkdir -p classes
	javac $(JFLAGS) $(SRCS) -d classes
	touch $@

# Run Tests.
check: clean compile
	java -classpath classes/ Main 0

cc:	clean
	mkdir -p classes
	javac $(JFLAGS) $(SRCS) -d classes

# Find and remove all *~ and *.class files.
clean:
	$(RM) sentinel *.class *~
	$(RM) lbz/*.class
allclean:
	$(RM) sentinel *.class *~
	$(RM) out/*.out
	$(RM) lbz/*.class

new:
	$(RM) *.class lbz/*.class
	javac *.java -Xlint:unchecked
