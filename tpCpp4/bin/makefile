#--------------- SET DIR AND TARGET NAME HERE -------
DIRSRC = ./
DIROBJ = ./
DIRBIN = ./
DIRHEADERS = ./
REP_TEST = ../Tests/
EXEC_MKTEST = ./mktest.sh

TARGET = analog

#--------------- Linux commands
MKDIR = @mkdir -p
RM = @rm -f
ECHO = @echo
TOUCH = @touch

#--------------- COMPILATOR
COMP = @g++ -c
COMPFLAGS = -ansi -pedantic -Wall -std=c++11 #-D MAP -g

#--------------- LINKER
LINKER = @g++
LINKERFLAGS =
LIBS =

#--------------- FILES
SRC = $(wildcard $(DIRSRC)*.cpp)
OBJ = $(SRC:$(DIRSRC)%.cpp=$(DIROBJ)%.o)

.PHONY: clean, cleanall


all: mkdir my_all

mkdir:
	$(MKDIR) $(DIROBJ) $(DIRBIN)

my_all: $(OBJ)
	$(ECHO) "Linker de $(OBJ) dans $(DIRBIN)$(TARGET)"
	$(LINKER) -o $(DIRBIN)$(TARGET) $(OBJ)
	$(ECHO) "Done"

$(DIROBJ)%.o: $(DIRSRC)%.cpp
	$(ECHO) "Compilation de $<"
	$(COMP) $(COMPFLAGS) -I $(DIRHEADERS) -o $@ $<
	$(ECHO) "Done"

clean:
	$(ECHO) "Suppression des binaires translatables $(DIROBJ)*.o."
	$(RM) $(DIROBJ)*.o
	$(ECHO) "Done"

cleanall:
	$(ECHO) "Suppression de $(DIRBIN)$(TARGET) et de $(DIROBJ)*.o"
	$(RM) $(DIRBIN)$(TARGET) $(OBJ)
	$(ECHO) "Done"
	
test:	
	$(MAKE)
	@$(ECHO) "Lancement des tests:"
	@$(ECHO) ""
	@($(CD) $(REP_TEST) && bash $(EXEC_MKTEST) )
