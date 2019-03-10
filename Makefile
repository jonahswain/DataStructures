# DataStructures (Java implementation of certain data structures)
# Makefile
# Author: Jonah Swain

JCOM = javac # Java compiler
JFLAGS = -g -classpath $(BINDIR) -sourcepath $(SRCDIR) -d $(BINDIR) # Java compiler flags

# Directories
SRCDIR = source
BINDIR = bin

# Sources
SOURCES = BinarySearchTree.java AVLTree.java

# File extensions/suffixes
.SUFFIXES: .java .class

# Dependency paths
vpath %.java $(SRCDIR)
vpath %.class $(BINDIR)

# Default rule (for compiling .java files)
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JCOM) $(JFLAGS) $<

# Build all rule
all: $(addprefix $(BINDIR)/,$(SOURCES:%.java=%.class))