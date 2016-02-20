# This uses GNU Make syntax and extensions.

MYAPP=myapp
JARFILE=../maven-build-artifacts/$(MYAPP)/$(MYAPP)-1.0-SNAPSHOT-jar-with-dependencies.jar
STAGING=../staging

.PHONY: usage
usage:
	@echo "Usage: make [{target}] [{variable}={value} ...]"
	@echo "Targets include:"
	@echo "  usage - [the default target] this message"
	@echo "  all - assemble and compile, but do not install."
	@echo "  install - installs \$$STAGING/bin/$(MYAPP) and \$$STAGING/bin/$(MYAPP).jar"
	@echo "  clean - delete build artifacts."
	@echo "Variables include:"
	@echo "  STAGING - currently $(STAGING)"
	@echo "  JARFILE - the jar that gets installed as \$$STAGING/bin/$(MYAPP).jar,"
	@echo "            currently $(JARFILE)"


.PHONY: all
all: $(JARFILE)

.PHONY: FORCE
$(JARFILE): FORCE
	mvn package

.PHONY: clean
clean:
	mvn clean

.PHONY: install
install: $(STAGING)/bin $(STAGING)/bin/$(MYAPP) $(STAGING)/bin/$(MYAPP).jar

$(STAGING)/bin:
	[ -d "$@" ] || mkdir -p $@

$(STAGING)/bin/$(MYAPP): $(MYAPP)
	cp $< $@

$(STAGING)/bin/$(MYAPP).jar: $(JARFILE)
	cp $< $@
