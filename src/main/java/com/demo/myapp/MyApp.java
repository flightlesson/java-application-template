package com.demo.myapp;

import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Replace this with an overview of the application that answers the questions
 * "what is this?" and "why would I want to use it?".
 */
public class MyApp implements Runnable {
    static final Logger LOGGER = LogManager.getLogger(MyApp.class.getName());
    
    static private final String USAGE = "replace this with stuff that will appear after 'usage:'";
    static private final String HEADER = "Replace this with a brief summary describing what the application does.\nOptions are:";
    static private final String FOOTER = "\n"
            +"Replace this with a longer description of the application "
            +"that answers 'what does this do?' and 'why should I use it?'.\n"
            +"\n"
            +"I like to use a blank line to separate paragraphs.\n"
            +"\n";
    static private final Options OPTIONS;
    static {
        OPTIONS = new Options();
        OPTIONS.addOption("h","help",false,"Print this message.");
	OPTIONS.addOption("v","verbose",false,"Turn on verbose output.");
        OPTIONS.addOption(null,"debug",false,"force log4j's level to DEBUG");
        OPTIONS.addOption(null,"l4jconfig",true,"Path to the log4j configuration file [./log4j2.xml]");
        // Add application specific options here.
    }
    
    static public void main(String[] args) {
        try {
            CommandLine cmdline = (new DefaultParser()).parse(OPTIONS,args);
            if (cmdline.hasOption("help")) {
                (new HelpFormatter()).printHelp(USAGE,HEADER,OPTIONS,FOOTER,false);
                System.exit(1);
            }
            configureLog4j(cmdline.getOptionValue("l4jconfig","log4j2.xml"),cmdline.hasOption("debug"));
        
            MyApp application = new MyApp(cmdline.hasOption("verbose"), cmdline.getArgs());
            application.run();
        } catch (ParseException ex) {
            // can't use logger; it's not configured
            System.err.println(ex.getMessage());
            (new HelpFormatter()).printHelp(USAGE,HEADER,OPTIONS,FOOTER,false);
        }
    }
    
    /**
     * Configures log4j2. From  https://logging.apache.org/log4j/2.x/faq.html#config_location:
     * 
     * How do I specify the configuration file location?
     *
     * By default, Log4j looks for a configuration file named log4j2.xml (not log4j.xml) in the classpath.
     * 
     * You can also specify the full path of the configuration file with this system property: 
     * -Dlog4j.configurationFile=path/to/log4j2.xml
     * 
     * That property can also be included in a classpath resource file named log4j2.component.properties.
     * 
     * Web applications can specify the Log4j configuration file location with a servlet context parameter. 
     * See this section of the Using Log4j 2 in Web Applications manual page.
     * 
     * 
     * How do I configure log4j2 in code without a configuration file?
     * 
     * Starting with version 2.4, Log4j 2 provides an API for programmatic configuration 
     * The new ConfigurationBuilder API allows you to create Configurations in code by constructing 
     * component definitions without requiring you to know about the internals of actual 
     * configuration objects like Loggers and Appenders.
     * 
     * 
     * @param l4jconfig null or path to the configuration file.
     * @param debug if true, forces Level.DEBUG.
     */
    static void configureLog4j(String l4jconfig, boolean debug) {
        if (l4jconfig != null && (new File(l4jconfig)).canRead()) {
            System.setProperty("log4j.configurationFile", l4jconfig);
        }
        if (debug) {
            // LOGGER.setLevel(Level.DEBUG);
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
            loggerConfig.setLevel(Level.DEBUG);
            ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.
        }
    }

    private final boolean verbose;
    private final String[] args;
    
    public MyApp(boolean verbose, String[] args) {
        this.verbose = verbose;
        this.args = args;
    }

    @Override
    public void run() {
	LOGGER.info("running myapp");
        LOGGER.info("... verbose is " + verbose);
        LOGGER.info("... with " + args.length + " args");
        for (int i=0; i < args.length; ++i) {
            LOGGER.info("... arg["+i+"] is \""+args[i]+"\"");
        }
    }
}
