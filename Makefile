# This uses GNU Make syntax and extensions.

MYAPP=myapp
# TRUNK is usually set to the parent of the scm-managed directory.
# TRUNK must either be empty or end with a slash.
# Should have same value as TRUNK in any application wrapper scripts and <trunk> in pom.xml.
TRUNK=../
JARFILE=$(TRUNK)maven-build-artifacts/$(MYAPP)/$(MYAPP)-1.0-SNAPSHOT-jar-with-dependencies.jar
STAGING=$(TRUNK)staging

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
