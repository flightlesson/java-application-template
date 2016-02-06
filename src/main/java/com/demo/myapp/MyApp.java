package com.demo.myapp;

import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Overview of the application.
 */
public class MyApp implements Runnable {
    static Logger logger = Logger.getLogger("myapp");
    
    static private final String USAGE = "stuff on usage line after 'usage:'";
    static private final String HEADER = "Brief summary of what the application does.\nOptions are:";
    static private final Options OPTIONS;
    static {
        OPTIONS = new Options();
        OPTIONS.addOption("h","help",false,"Print this message.");
	OPTIONS.addOption("v","verbose",false,"Turn on verbose output.");
        OPTIONS.addOption(null,"l4jconfig",true,"Path to the log4j configuration file [./l4j.lcf]");
    }
    static private final String FOOTER = "\nLonger description of the application. "
            +"Apache Commons CLI is pretty good about splitting long paragraphs.\n"
            +"\n"
            +"I like to use a blank line to separate paragraphs.\n";
    
    static public void main(String[] args) {
        try {
            CommandLine cmdline = (new DefaultParser()).parse(OPTIONS,args);
            if (cmdline.hasOption("help")) {
                (new HelpFormatter()).printHelp(USAGE,HEADER,OPTIONS,FOOTER,false);
                System.exit(1);
            }
            configureLogger(cmdline.getOptionValue("l4jconfig","l4j.lcf"));
        
            MyApp myapp = new MyApp(cmdline.hasOption("verbose"));
            myapp.run();
        } catch (ParseException ex) {
            // can't use logger; it's not configured
            System.err.println(ex.getMessage());
        }
    }
    
    static void configureLogger(String l4jconfig) {
        if ((new File(l4jconfig)).canRead()) {
            if (l4jconfig.matches(".*\\.xml$")) {
                DOMConfigurator.configureAndWatch(l4jconfig);
            } else {
                PropertyConfigurator.configureAndWatch(l4jconfig);
            }
        } else {
            BasicConfigurator.configure();
        }
    }

    private final boolean verbose;
    
    public MyApp(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public void run() {
	logger.info("running myapp, verbose="+verbose);
    }
}
