# This presumes GNU Make; it uses GNU Make syntax and extensions.

MYAPP=myapp
JARFILE=../maven-build-artifacts/myapp/myapp-1.0-SNAPSHOT-jar-with-dependencies.jar
SANDBOX=../sandbox

.PHONY: usage
usage:
	@echo "Usage: make [{target}] [{variable}={value} ...]"
	@echo "Targets include:"
	@echo "  usage - [the default target] this message"
	@echo "  all - assemble and compile, but do not install."
	@echo "  install - install in \$$SANDBOX."
	@echo "  clean - delete build artifacts."
	@echo "Variables include:"
	@echo "  SANDBOX - currently $(SANDBOX)"
	@echo "  JARFILE - the jar that gets installed, currently $(JARFILE)"

.PHONY: all
all: $(JARFILE)

.PHONY: FORCE
$(JARFILE): FORCE
	mvn package

.PHONY: clean
clean:
	mvn clean

.PHONY: install
install: $(SANDBOX)/bin $(SANDBOX)/bin/$(MYAPP) $(SANDBOX)/bin/$(MYAPP).jar

$(SANDBOX)/bin:
	[ -d "$@" ] || mkdir -p $@ || false

$(SANDBOX)/bin/$(MYAPP): $(MYAPP)
	cp $< $@

$(SANDBOX)/bin/$(MYAPP).jar: $(JARFILE)
	cp $< $@
