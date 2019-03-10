# DataStructures (Java implementation of certain data structures)
# Makefile
# Author: Jonah Swain

JCOM = javac # Java compiler
JFLAGS = -g -classpath $(BINDIR) -sourcepath $(SRCDIR) -d $(BINDIR) # Java compiler flags

JDOC = javadoc # JavaDoc
JDOCFLAGS = -classpath $(BINDIR) -d $(DOCDIR) # JavaDoc flags

# Directories
SRCDIR = source
BINDIR = bin
DOCDIR = docs

# Sources
SOURCES = BinarySearchTree.java AVLTree.java

# File extensions/suffixes
.SUFFIXES: .java .class

# Phony rules (rules without file dependencies)
.PHONY: clean docs clean_docs

# Dependency paths
vpath %.java $(SRCDIR)
vpath %.class $(BINDIR)

# Default rule (for compiling .java files)
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JCOM) $(JFLAGS) $<

# Build all rule (default run)
all: $(addprefix $(BINDIR)/,$(SOURCES:%.java=%.class))

# Explicit rules
# None (for now)

# Other rules

# Remove all compiled class files
clean:
	rm -f $(BINDIR)/*.class

# Generate documentation using JavaDoc
docs:
	$(JDOC) $(JDOCFLAGS) $(addprefix $(SRCDIR)/,$(SOURCES))

# Remove all doc files
clean_docs:
	rm -rf $(DOCDIR)/*