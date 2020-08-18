#-----------------------------------------------------------------------------
# Name: Anthony Wong
# CruzID: 1652596
# Class: 12B
# Date: 24 Jan 2019
# Desc: Makefile
# File Name: Makefile
#-----------------------------------------------------------------------------

JAVASRC = Search.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = Search
CLASSES = Search.class
JARFILE = Search
PA = pa1
SUBMIT = submit cmps012b-pt.w19 $(PA)

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	chmod +x $(JARFILE)

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)

submit: $(SOURCES)
	$(SUBMIT) $(SOURCES)
