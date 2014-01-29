package com.roanis.tdd.base;

import java.io.IOException;
import java.util.List;

import atg.nucleus.Nucleus;
import atg.nucleus.NucleusTestUtils;
import atg.nucleus.NucleusTestUtils.NucleusStartupOptions;
import atg.nucleus.ServiceException;
import atg.nucleus.logging.ApplicationLogging;
import atg.nucleus.logging.ClassLoggingFactory;
import atg.test.AtgDustCase;

public class RoanisTestCase extends AtgDustCase {
	
	// The running DUST Nucleus - can also be accessed with
    // Nucleus.getGlobalNucleus()
    protected static Nucleus baseNucleus = null;
    private static final ApplicationLogging log = ClassLoggingFactory.getFactory().getLoggerForClass(RoanisTestCase.class);   
    
	public static void startNucleus (List<String> moduleList) throws Exception {   	
        if ((null == moduleList) || (0 == moduleList.size())) {
            throw new Exception("A module list must be specfied when using BaseTest.");
        }

        // Fire up Nucleus - make sure DYNAMO_HOME and DUST_HOME are set.
        String initialComponent = "/atg/dynamo/Configuration";
        NucleusStartupOptions startupOptions = new NucleusStartupOptions(moduleList.toArray(new String[0]), RoanisTestCase.class, initialComponent);

        baseNucleus = NucleusTestUtils.startNucleusWithModules(startupOptions.getModules(), startupOptions.getClassRelativeTo(),
                startupOptions.getBaseConfigDirectory(), startupOptions.getInitialService());

        if (null == baseNucleus) {
            throw new Exception("Unable to start Nucleus for unit tests.");
        }
    }
	
	public static void shutdownNucleus () {
        if (baseNucleus != null) {
            try {
                NucleusTestUtils.shutdownNucleus(baseNucleus);
            } catch (ServiceException e) {
            	if (log.isLoggingError()) {
					log.logError(e);
				}
            } catch (IOException e) {
            	if (log.isLoggingError()) {
					log.logError(e);
				}
            }
        }
    }  
}
