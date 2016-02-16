# This uses GNU Make syntax and extensions.

MYAPP=myapp
JARFILE=../maven-build-artifacts/$(_MYAPP)/$(_MYAPP)-1.0-SNAPSHOT-jar-with-dependencies.jar
SANDBOX=../sandbox

.PHONY: usage
usage:
	@echo "Usage: make [{target}] [{variable}={value} ...]"
	@echo "Targets include:"
	@echo "  usage - [the default target] this message"
	@echo "  all - assemble and compile, but do not install."
	@echo "  install - installs \$$SANDBOX/bin/$(_MYAPP) and \$$SANDBOX/bin/$(_MYAPP).jar"
	@echo "  clean - delete build artifacts."
	@echo "Variables include:"
	@echo "  SANDBOX - currently $(SANDBOX)"
	@echo "  JARFILE - the jar that gets installed as \$$SANDBOX/bin/$(_MYAPP).jar,"
	@echo "            currently $(JARFILE)"


# Don't use variables directly; they might not be set
override _SANDBOX = $(or $(SANDBOX),"SANDBOX") 
override _MYAPP   = $(or $(MYAPP),"MYAPP")
override _JARFILE = $(or $(JARFILE),"JARFILE")

.PHONY: all
all: $(_JARFILE)

.PHONY: FORCE
$(JARFILE): FORCE
	mvn package

.PHONY: clean
clean:
	mvn clean

.PHONY: install
install: $(_SANDBOX)/bin $(_SANDBOX)/bin/$(_MYAPP) $(_SANDBOX)/bin/$(_MYAPP).jar

$(_SANDBOX)/bin:
	[ -d "$@" ] || mkdir -p $@

$(_SANDBOX)/bin/$(MYAPP): $(_MYAPP)
	cp $< $@

$(_SANDBOX)/bin/$(MYAPP).jar: $(_JARFILE)
	cp $< $@
