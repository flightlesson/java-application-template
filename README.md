This is a template for starting a new Java application.

Customization starts by:

- Under src/main, rename "com/demo/myapp" to your package and MyApp.java to your main class.
- Edit and rename the "myapp" application wrapper script. Change TRUNK, jarfilebasename, and mainclass.
- Edit "pom.xml"; change &lt;project>&lt;groupId>, &lt;project>&lt;artifactId>, &lt;properties>&lt;trunk> and &lt;manifest>&lt;mainClass>.
- Edit "Makefile"; change default values for MYAPP and JARFILE.nnn
- Edit the renamed "MyApp.java" and add your application code.

To build the application using maven run "mvn package". 
Note that build artifacts get placed in "../maven-build-artifacts/myapp"
instead of "target", which is maven's default: this is to avoid polluting 
the git-managed project directory with build artifacts. 
See pom.xml to change this behavior.

To build the application using make, run "make all" or "make install". "make all"
simply runs 'mvn package'; "make install" creates the ../sandbox directory and
installs there. The sandbox can be useful in distributing your application.

Use the renamed "myapp" script to start the application. 
Try running with the "+h" command line arg, then with the "-h" command line arg.

Enjoy!
